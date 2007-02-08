package org.commonfarm.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.commonfarm.security.config.SecurityConfig;

import java.security.Principal;

/**
 * A simple request wrapper used to intercept default Servlet API security
 * methods.
 */
public class SecurityHttpRequestWrapper extends HttpServletRequestWrapper {
	private HttpServletRequest request;

	private SecurityConfig securityConfig;

	public SecurityHttpRequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	public String getRemoteUser() {
		Principal user = getUserPrincipal();
		if (user == null) {
			return null;
		} else {
			return user.getName();
		}
	}

	public Principal getUserPrincipal() {
		if (securityConfig == null) {
			securityConfig = (SecurityConfig) request.getSession().getServletContext().getAttribute(SecurityConfig.STORAGE_KEY);
		}

		Principal user = securityConfig.getAuthenticator().getUser(request);
		return user;
	}
}