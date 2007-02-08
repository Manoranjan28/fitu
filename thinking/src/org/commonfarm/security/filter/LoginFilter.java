package org.commonfarm.security.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.commonfarm.security.auth.Authenticator;
import org.commonfarm.security.auth.AuthenticatorException;
import org.commonfarm.security.config.SecurityConfig;
import org.commonfarm.security.config.SecurityConfigFactory;
import org.commonfarm.security.interceptor.LoginInterceptor;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;

/**
 * This is a filter that logs the user in. It works a little like J2EE
 * form-based seraph, except it looks for the parameters 'os_username' and
 * 'os_password' instead of j_username and j_password.
 * <p>
 * The form post/get action should be the URL of the login servlet/JSP/action -
 * given by SecurityFilter.LOGIN_URL.
 * <p>
 * If the parameters exist and authentication is successful, the user will be
 * redirected by the filter to the URL given by the session attribute at
 * SecurityFilter.ORIGINAL_URL_KEY.
 * <p>
 * If this URL doesn't exist, it will look for a parameter 'os_destination' to
 * use as the redirected URL instead.
 * <p>
 * If neither is found, it is assumed that the page will check the authorisation
 * status and handle redirection itself.
 * <p>
 * From the any other filter in the request, or the servlet/JSP/action which
 * processes the request, you can look up the status of the authorisation
 * attempt. The status is a String request attribute, with the key
 * 'os_authstatus'.
 * <p>
 * The possible statuses are:
 * <ul>
 * <li> LoginFilter.LOGIN_SUCCESS - the login was processed, and user was logged
 * in
 * <li> LoginFilter.LOGIN_FAILURE - the login was processed, the user gave a bad
 * username or password
 * <li> LoginFilter.LOGIN_ERROR - the login was processed, an exception occurred
 * trying to log the user in
 * <li> LoginFilter.LOGIN_NOATTEMPT - the login was no processed, no form
 * parameters existed
 * </ul>
 */
public class LoginFilter implements Filter {
	private Log log = LogFactory.getLog(LoginFilter.class);

	public static final String ALREADY_FILTERED = "loginfilter.already.filtered";

	public static final String LOGIN_SUCCESS = "success";
	public static final String LOGIN_FAILED = "username or password is error!";
	public static final String LOGIN_ERROR = "Login exception!";
	public static final String LOGIN_NOATTEMPT = null;
	public static final String OS_AUTHSTATUS_KEY = "os_authstatus";
	
	private SecurityConfig securityConfig = null;
	
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
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// wrap the request with one that returns the LoginUser as the Principal
		req = new SecurityHttpRequestWrapper((HttpServletRequest) req);
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (req.getAttribute(ALREADY_FILTERED) != null || !getSecurityConfig(request).getController().isSecurityEnabled()) {
			chain.doFilter(req, res);
			return;
		} else {
			req.setAttribute(ALREADY_FILTERED, Boolean.TRUE);
		}

		//req.setAttribute(OS_AUTHSTATUS_KEY, LOGIN_NOATTEMPT);

		// check for parameters
		String username = request.getParameter("os_username");
		String password = request.getParameter("os_password");
		boolean persistentLogin = "true".equals(request.getParameter("os_cookie"));

		boolean loggedIn = false;

		// try to login the user if possible
		if (username != null && password != null) {
			List interceptors = getSecurityConfig(request).getInterceptors(LoginInterceptor.class);

			log.debug("Username and Password are not null - processing login request");
			try {
				for (Iterator iterator = interceptors.iterator(); iterator.hasNext();) {
					LoginInterceptor loginInterceptor = (LoginInterceptor) iterator.next();
					loginInterceptor.beforeLogin(request, response, username, password, persistentLogin);
				}

				loggedIn = getAuthenticator(request).login(request, response, username, password, persistentLogin);

				if (loggedIn) {
					log.debug("Login was successful - setting attribute to \"Success\"");
					request.setAttribute(OS_AUTHSTATUS_KEY, LOGIN_SUCCESS);
				} else {
					log.debug("Login was not successful - setting attribute to \"Failed\"");
					request.getSession().setAttribute(OS_AUTHSTATUS_KEY, LOGIN_FAILED);
				}
			} catch (AuthenticatorException e) {
				log.debug("Login was not successful, and exception was thrown - setting attribute to \"Error\"");
				request.getSession().setAttribute(OS_AUTHSTATUS_KEY, LOGIN_ERROR);
				e.printStackTrace();
				log.warn("Exception was thrown whilst logging in: " + e.getMessage(), e);
			}
			
			for (Iterator iterator = interceptors.iterator(); iterator.hasNext();) {
				LoginInterceptor loginInterceptor = (LoginInterceptor) iterator.next();
				loginInterceptor.afterLogin(request, response, username, password, persistentLogin, (String) request.getAttribute(OS_AUTHSTATUS_KEY));
			}
		}

		// if we successfully logged in - look for an original URL to forward to
		if (loggedIn) {
			String originalURL = (String) request.getSession().getAttribute(getSecurityConfig(request).getOriginalURLKey());

			if (originalURL != null) {
				if (log.isDebugEnabled())
					log.debug("Logged In - Redirecting to Original URL: " + request.getContextPath() + originalURL);

				request.getSession().setAttribute(getSecurityConfig(request).getOriginalURLKey(), null);
				((HttpServletResponse) res).sendRedirect(request.getContextPath() + originalURL);
				return;
			} else if (request.getParameter("os_destination") != null) {
				if (log.isDebugEnabled())
					log.debug("Logged In - redirecting to os_destination: "
							+ request.getContextPath()
							+ request.getParameter("os_destination"));

				((HttpServletResponse) res).sendRedirect(request.getContextPath() + request.getParameter("os_destination"));
				return;
			}
		}

		chain.doFilter(req, res);
	}

	protected Authenticator getAuthenticator(HttpServletRequest request) {
		return getSecurityConfig(request).getAuthenticator();
	}

	protected SecurityConfig getSecurityConfig(HttpServletRequest request) {
		return securityConfig;
	}

	public void destroy() {
		log.debug("SecurityFilter.destroy");
		securityConfig.destroy();
		securityConfig = null;
	}
}
