package org.commonfarm.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implement this interface to intercept logout methods.
 */
public interface LogoutInterceptor extends Interceptor {
    public void beforeLogout(HttpServletRequest request, HttpServletResponse response);

    public void afterLogout(HttpServletRequest request, HttpServletResponse response);
}
