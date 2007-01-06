package org.commonfarm.security.auth;

import org.commonfarm.security.config.SecurityConfig;
import org.commonfarm.security.config.SecurityConfigFactory;
import org.commonfarm.security.interceptor.LogoutInterceptor;
import org.commonfarm.security.user.LoginUser;
import org.commonfarm.security.user.UserManager;
import org.commonfarm.security.util.CookieUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.security.Principal;

/**
 * This authenticator stores the currently logged in user in the session as OSUser LoginUser objects.
 *
 * It also provides for cookie logins and creates cookies if needed.
 *
 * Includes code from Jive 1.2.4 (released under the Apache license)
 */
public class DefaultAuthenticator extends AbstractAuthenticator {
	private Log log = LogFactory.getLog(DefaultAuthenticator.class);
    private String loginCookieKey;

    /**
     * The key used to store the user object in the session
     */
    public static String LOGGED_IN_KEY = "LOGIN_USER";

    /**
     * The key used to indicate that the user has logged out and session regarding of it containing a cookie is not
     * logged in.
     */
    public static String LOGGED_OUT_KEY = "seraph_defaultauthenticator_logged_out_user";

    // the age of the autologin cookie - default = 1 year (in seconds)
    private static int AUTOLOGIN_COOKIE_AGE = 365 * 24 * 60 * 60;
    private static ApplicationContext appContext = null;

    public void init(Map params, SecurityConfig config) {
        log.debug(this.getClass().getName()+" $Revision: 1.11 $ initializing");
        super.init(params, config);
        this.loginCookieKey = config.getLoginCookieKey();
    }

    /**
     * Tries to authenticate a user (via OSUser). If successful, sets a session attribute and cookie indicating
     *  their logged-in status.
     * @return Whether the user was authenticated.  This base implementation returns false if any errors occur, rather
     * than throw an exception.
     */
    public boolean login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean cookie) throws AuthenticatorException {
    	if (appContext == null) {
    		appContext = WebApplicationContextUtils
							.getRequiredWebApplicationContext(request.getSession().getServletContext());
		}
    	UserManager userManager = (UserManager) appContext.getBean("userManager");
    	Principal user = null;
    	try {
    		user = userManager.getUser(username);
    	} catch (Exception e) {
            log.debug("Could not find user who tried to login: " + e);
        }
        // check that they can login (they have the USE permission or ADMINISTER permission)
        if (user == null) {
            log.info("Cannot login user '" + username + "' as they do not exist.");
        } else {
            boolean authenticated = authenticate(user, password);
            if (authenticated) {
                request.getSession().setAttribute(LOGGED_IN_KEY, user);
                request.getSession().setAttribute(LOGGED_OUT_KEY, null);

                if (getRoleMapper().canLogin(user, request)) {
                    if (cookie && response != null) {
                        CookieUtils.setCookie(request, response, getLoginCookieKey(), CookieUtils.encodePasswordCookie(username, password, getConfig().getCookieEncoding()), AUTOLOGIN_COOKIE_AGE, getCookiePath(request));
                    }
                    return true;
                } else {
                    request.getSession().removeAttribute(LOGGED_IN_KEY);
                }
            } else {
                log.info("Cannot login user '" + username + "' as they used an incorrect password");
            }
        }


        if (response != null && CookieUtils.getCookie(request, getLoginCookieKey()) != null) {
            log.warn("LoginUser: " + username + " tried to login but they do not have USE permission or weren't found. Deleting cookie.");

            try {
                CookieUtils.invalidateCookie(request, response, getLoginCookieKey(), getCookiePath(request));
            } catch (Exception e) {
                log.error("Could not invalidate cookie: " + e, e);
            }
        }

        return false;
    }

    // override this method if you need to retrieve the role mapper from elsewhere than the singleton-factory (injected depency for instance)
    protected RoleMapper getRoleMapper() {
        return SecurityConfigFactory.getInstance().getRoleMapper();
    }

    // The following two methods are the only OSUser-specific parts of this class

    /** Uses OSUser to retrieve a Principal for a given username. Returns null if no user exists. */
    protected Principal getUser(String username) {
    	UserManager userManager = UserManager.getInstance();
        try {
            return userManager.getUser(username);
        } catch (Exception e) {
            log.debug("Could not find user who tried to login: " + e);
        }
        return null;
    }

    /** Uses OSUser's authenticate() to authenticate a user. */
    protected boolean authenticate(Principal user, String password) {
        return ((LoginUser)user).authenticate(password);
    }

    public boolean logout(HttpServletRequest request, HttpServletResponse response) throws AuthenticatorException {
        List interceptors = getLogoutInterceptors();

        for (Iterator iterator = interceptors.iterator(); iterator.hasNext();) {
            LogoutInterceptor interceptor = (LogoutInterceptor) iterator.next();
            interceptor.beforeLogout(request, response);
        }

        request.getSession().setAttribute(LOGGED_IN_KEY, null);
        request.getSession().setAttribute(LOGGED_OUT_KEY, Boolean.TRUE);

        try {
            CookieUtils.invalidateCookie(request, response, getLoginCookieKey(), getCookiePath(request));
        } catch (Exception e) {
            log.error("Could not invalidate cookie: " + e, e);
        }

        for (Iterator iterator = interceptors.iterator(); iterator.hasNext();) {
            LogoutInterceptor interceptor = (LogoutInterceptor) iterator.next();
            interceptor.afterLogout(request, response);
        }

        return true;
    }
    
    /**
     * Returns the currently logged in user.
     *
     * Warning this method does not necessarily authenticate the user
     */
    public Principal getUser(HttpServletRequest request, HttpServletResponse response) {
        Principal user = null;

        try {
            //when running tests, a request may not neccessarily have a session
            if (request.getSession() != null && request.getSession().getAttribute(LOGGED_OUT_KEY) != null) {
                log.debug("Session found; user already logged in");
                user = null;
            } else if(request.getSession() != null && request.getSession().getAttribute(LOGGED_IN_KEY) != null) {
                log.debug("Session found; user already logged in");
                user = (Principal) request.getSession().getAttribute(LOGGED_IN_KEY);
            } else {
                // otherwise look for a cookie
                Cookie cookie = CookieUtils.getCookie(request, getLoginCookieKey());

                if (cookie != null) {
                    String[] values = CookieUtils.decodePasswordCookie(cookie.getValue(), SecurityConfigFactory.getInstance().getCookieEncoding());

                    if (values != null) {
                        String username = values[0];
                        String password = values[1];

                        if (login(request, response, username, password, false)) {
                            log.debug("Logged user in via a cookie");
                            return getUser(request);
                        }
                    }
                    log.debug("Cannot log user in via a cookie");
                }
            }
        } catch (Exception e) {
            log.warn("Exception: " + e, e);
        }

        return user;
    }

    /**
     * Root the login cookie at the same location as the webapp.
     *
     * Anyone wanting a different cookie path policy can override the authenticator and
     * provide one.
     */
    protected String getCookiePath(HttpServletRequest request) {
        String path = request.getContextPath();
        if (path == null || path.equals("")) return "/";

        // The spec says this should never happen, but just to be sure...
        if (!path.startsWith("/")) return "/" + path;

        return path;
    }

    protected String getLoginCookieKey() {
        return loginCookieKey;
    }
    protected List getLogoutInterceptors() {
        return getConfig().getInterceptors(LogoutInterceptor.class);
    }
}
