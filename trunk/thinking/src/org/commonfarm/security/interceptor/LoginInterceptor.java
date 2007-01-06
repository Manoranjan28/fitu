package org.commonfarm.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Allows interception of logins.
 * Useful for doing things like running code after a user logs in (ie date of last login),
 * cleaning up resources when a user logs out,
 * or tracking the number of failed authentication attempts against a particular user.
 */
public interface LoginInterceptor extends Interceptor {
    public void beforeLogin(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean cookieLogin);

    public void afterLogin(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean cookieLogin, String loginStatus);
}
