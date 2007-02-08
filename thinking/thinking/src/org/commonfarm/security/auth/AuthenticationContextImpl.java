package org.commonfarm.security.auth;

import java.security.Principal;

public class AuthenticationContextImpl implements AuthenticationContext {
	private static ThreadLocal threadLocal = new ThreadLocal();

	public Principal getUser() {
		return (Principal) threadLocal.get();
	}

	public void setUser(Principal user) {
		threadLocal.set(user);
	}

	public void clearUser() {
		setUser(null);
	}
}
