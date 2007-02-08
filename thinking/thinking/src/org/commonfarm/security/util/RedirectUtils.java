package org.commonfarm.security.util;

import org.commonfarm.security.config.SecurityConfig;
import org.commonfarm.security.config.SecurityConfigFactory;
import org.commonfarm.security.filter.SecurityFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Utilities for login link redirection.
 */
public class RedirectUtils {

    /**
     * Returns a login URL that would log the user in to access resource indicated by <code>request</code>.
     * <p>
     * For instance, if <code>request</code> is for protected path "/browse/JRA-123" and the user must login before
     * accessing this resource, this method might return "/login.jsp?os_destination=%2Fbrowse%2FJRA-123". Presumably the
     * login.jsp page will redirect back to 'os_destination' once logged in.
     * <p>
     * The returned path is derived from the <code>login.url</code> parameter in seraph-config.xml, which in the example above would be
     * "/login.jsp?os_destination={originalurl}". The '${originalurl}' token is replaced at runtime with a relative
     * or absolute path to the original resource requested by <code>request</code> ('/browse/JRA-123').
     * <p>
     * Both the returned URL and the ${originalurl} replacement URL may be absolute or root-relative, depending on whether
     * the seraph-config.xml <code>login.url</code> parameter is.  This allows for redirection to external <acronym title="Single Sign-on">SSO</acronym>
     * apps, which are passed an absolute path to the originally requested resource.
     * <p>
     * No actual permission checks are performed to determine whether the user needs to log in to access the resource. The
     * caller is assumed to have done this before calling this method.
     *
     * @param request The original request made by the user for a resource.
     * @return A root-relative or absolute URL of a login link that would log the user in to access the resource.
     */
    public static String getLoginUrl(HttpServletRequest request) {
        SecurityConfig securityConfig = SecurityConfigFactory.getInstance();
        String loginURL = securityConfig.getLoginURL();
        return getLoginURL(loginURL, request);
    }
    public static String getDenyUrl(HttpServletRequest request) {
    	SecurityConfig securityConfig = SecurityConfigFactory.getInstance();
    	String denyURL = request.getContextPath() + securityConfig.getDenyURL();
    	return denyURL;
    }

    /**
     * Returns a login URL that would log the user in to access resource indicated by <code>request</code>.
     * Identical to {@link #getLoginUrl(javax.servlet.http.HttpServletRequest)}, except uses the 'link.login.url'
     * parameter in seraph-config.xml instead of 'login.url', which allows for different login pages depending on whether
     * invoked from a link ("link.login.url") or from a servlet filter that intercepted a request ("login.url").
     * @see #getLoginUrl(javax.servlet.http.HttpServletRequest) for parameters, etc
     */
    public static String getLinkLoginURL(HttpServletRequest request) {
        SecurityConfig securityConfig = SecurityConfigFactory.getInstance();
        String loginURL = securityConfig.getLinkLoginURL();
        return getLoginURL(loginURL, request);
    }


    private static String getLoginURL(String loginURL, HttpServletRequest request) {
        boolean externalLoginLink = isExternalLoginLink(loginURL);
        loginURL = replaceOriginalURL(loginURL, request, externalLoginLink);
        if (externalLoginLink) {
            return loginURL;
        } else {
            return request.getContextPath() + loginURL;
        }
    }

    private static boolean isExternalLoginLink(String loginURL) {
        return (loginURL.indexOf("://") != -1);
    }

    /**
     * Replace ${originalurl} token in a string with a URL for a Request.
     */
    private static String replaceOriginalURL(final String loginURL, final HttpServletRequest request, boolean external) {
        final int i = loginURL.indexOf("${originalurl}");
        if (i != -1) {
            final String originalURL = getOriginalURL(request, external);
            //return loginURL.substring(0, i) + URLEncoder.encode(originalURL) + loginURL.substring(i + "${originalurl}".length());
            return loginURL.substring(0, i) + originalURL + loginURL.substring(i + "${originalurl}".length());
        }
        return loginURL;
    }

    /**
     * Recreate a URL from a Request.
     */
    private static String getOriginalURL(HttpServletRequest request, boolean external) {
        String originalURL = (String) request.getAttribute(SecurityFilter.ORIGINAL_URL);
        if (originalURL != null) {
            if (external)
                return getServerNameAndPath(request) + originalURL;
            else
                return originalURL;
        }

        if (external)
            return request.getRequestURL() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
        else
            return request.getServletPath() +
                (request.getPathInfo() == null ? "" : request.getPathInfo()) +
                (request.getQueryString() == null ? "" : "?" + request.getQueryString());

    }

    public static String getServerNameAndPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
