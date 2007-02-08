package org.commonfarm.security.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.security.Principal;

import org.commonfarm.security.SecurityService;
import org.commonfarm.security.auth.AuthenticationContext;
import org.commonfarm.security.auth.AuthenticatorException;
import org.commonfarm.security.config.SecurityConfig;
import org.commonfarm.security.config.SecurityConfigFactory;
import org.commonfarm.security.util.RedirectUtils;
import org.commonfarm.util.StringUtil;

/**
 * The SecurityFilter determines which roles are required for a given request by
 * querying all of the Services.
 * 
 * @see SecurityService
 */
public class SecurityFilter implements Filter {
	private Log log = LogFactory.getLog(SecurityFilter.class);

	private SecurityConfig securityConfig = null;

	private static final String ALREADY_FILTERED = "os_securityfilter_already_filtered";
	public static final String ORIGINAL_URL = "atlassian.core.seraph.original.url";
	public static final String ROLES_CURRENT = "ROLES_CURRENT";

	public void init(FilterConfig config) {
		String configFileLocation = null;
		
		if (config.getInitParameter("config.file") != null) {
			configFileLocation = config.getInitParameter("config.file");
			log.debug("Security config file location: " + configFileLocation);
		}
		Object obj = config.getServletContext().getAttribute(SecurityConfig.STORAGE_KEY);
		if (obj instanceof SecurityConfig) {
			securityConfig = (SecurityConfig) obj;
		} else {
			securityConfig = SecurityConfigFactory.getInstance(configFileLocation);
			config.getServletContext().setAttribute(SecurityConfig.STORAGE_KEY, securityConfig);
		}
		//securityConfig = SecurityConfigFactory.getInstance(configFileLocation);
		//config.getServletContext().setAttribute(SecurityConfig.STORAGE_KEY, securityConfig);
	}

	public void destroy() {
		log.debug("SecurityFilter.destroy");
		securityConfig.destroy();
		securityConfig = null;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (req.getAttribute(ALREADY_FILTERED) != null || !getSecurityConfig(request).getController().isSecurityEnabled()) {
			chain.doFilter(req, res);
			return;
		} else {
			req.setAttribute(ALREADY_FILTERED, Boolean.TRUE);
		}

		// Try and get around Orion's bug when redeploying
		// it seems that filters are not called in the correct order
		if (req.getAttribute(LoginFilter.ALREADY_FILTERED) == null) {
			log.warn("LoginFilter not yet applied to this request - terminating filter chain");
			return;
		}

		String originalURL = request.getServletPath()
				+ (request.getPathInfo() == null ? "" : request.getPathInfo())
				+ (request.getQueryString() == null ? "" : "?"
				+ request.getQueryString());
		
		// store the original URL as a request attribute anyway - often useful
		// for pages to access it (ie login links)
		request.setAttribute(SecurityFilter.ORIGINAL_URL, originalURL);
		boolean needAuth = isNeedAuth(request, response, originalURL, ROLES_CURRENT);
		//if (needAuth) needAuth = isNeedAuth(request, response, originalURL, "");//#####DB Auth
		
		Principal user = getSecurityConfig(request).getAuthenticator().getUser(request, response);
		//Exception after logout, input context path for address and "Enter". We can address main page
		if (user == null && originalURL.indexOf("index.jsp") != -1
				&& originalURL.indexOf("login.jsp") == -1) {
			needAuth = true;
		}
		// if we need to authenticate, store current URL and forward
		if (needAuth) {
			if (user != null) {
				response.sendRedirect(RedirectUtils.getDenyUrl(request));
				return;
			}
			if (log.isDebugEnabled())
				log.debug("Need Authentication: Redirecting to: "
						+ getSecurityConfig(request).getLoginURL() + " from: "
						+ originalURL);
			request.getSession().setAttribute(getSecurityConfig(request).getOriginalURLKey(), originalURL);
			response.sendRedirect(RedirectUtils.getLoginUrl(request));
			return;
		} else {
			try {
				chain.doFilter(req, res);
			} finally {
				// clear the user from the context
				getAuthenticationContext(request).clearUser();
			}
		}
	}
	/**
	 * 
	 * @param request     HttpServletRequest
	 * @param response    HttpServletResponse
	 * @param originalURL
	 * @return
	 */
	private boolean isNeedAuth(HttpServletRequest request, HttpServletResponse response, String originalURL, String status) {
		Set requiredRoles = new HashSet();
		
		// loop through loaded services and get required roles
		for (Iterator iterator = getSecurityConfig(request).getServices().iterator(); iterator.hasNext();) {
			SecurityService securityService = (SecurityService) iterator.next();
			//Get required roles by this request
			Set serviceRoles = null;
			if (status.equals(ROLES_CURRENT)) {
				serviceRoles = securityService.getRequiredRolesFromConfig(request);
			} else {
				serviceRoles = securityService.getRequiredRoles(request);
			}
			requiredRoles.addAll(serviceRoles);
		}

		if (log.isDebugEnabled()) {
			log.debug("requiredRoles = " + requiredRoles);
		}

		// whether this URL needs authorisation
		boolean needAuth = false;

		// try to get the user (for cookie logins)
		Principal user = getSecurityConfig(request).getAuthenticator().getUser(request, response);

		// set the user in the context
		getAuthenticationContext(request).setUser(user);

		// check if the current user has all required permissions
		// if there is no current user, request.isUserInRole() always returns false so this works
		for (Iterator iterator = requiredRoles.iterator(); iterator.hasNext();) {
			String role = (String) iterator.next();
			if (!StringUtil.notEmpty(role)) role = "all";
			if (role.equalsIgnoreCase("all") && user != null) {
				break;
			}
			// this isUserInRole method is only used here and 'd be better off
			// replaced by getRoleMapper().hasRole(user, request, role)) since
			// we have the user already
			// was : if
			// (!securityConfig.getAuthenticator().isUserInRole(request, role))
			if (getSecurityConfig(request).getRoleMapper().hasRole(user, request, role)) {
				needAuth = false;
				break;
			} else {
				log.info("Login User '" + user + "' needs (and lacks) role '" + role + "' to access " + originalURL);
				needAuth = true;
			}
		}

		// check if we're at the signon page, in which case do not auth
		if (request.getServletPath() != null && request.getServletPath().equals(getSecurityConfig(request).getLoginURL())) {
			needAuth = false;
		}
		// Logout
		if (originalURL.indexOf("logout.jsp") != -1) {
			try {
				log.info("Logout!!!!!!!!");
				getSecurityConfig(request).getAuthenticator().logout(request, response);
				needAuth = false;
			} catch (AuthenticatorException e) {
				log.debug("Logout was not successful, and exception was thrown - setting attribute to \"Error\"");
				e.printStackTrace();
				log.warn("Exception was thrown whilst logging in: " + e.getMessage(), e);
			}
		}
		return needAuth;
	}

	protected SecurityConfig getSecurityConfig(HttpServletRequest request) {
		if (securityConfig == null) {
			securityConfig = (SecurityConfig) request.getSession().getServletContext().getAttribute(SecurityConfig.STORAGE_KEY);
		}
		return securityConfig;
	}

	protected AuthenticationContext getAuthenticationContext(HttpServletRequest request) {
		return getSecurityConfig(request).getAuthenticationContext();
	}

}
