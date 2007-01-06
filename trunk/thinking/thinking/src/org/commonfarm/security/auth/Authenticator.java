package org.commonfarm.security.auth;

import org.commonfarm.security.Initable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * An Authenticator is used to authenticate users, log them in, log them out and check their roles.
 */
public interface Authenticator extends Initable {
    public final String DEFAULT_AUTHENTICATOR = "com.atlassian.seraph.auth.DefaultAuthenticator";

    public void destroy();

    public String getRemoteUser(HttpServletRequest request);

    public Principal getUser(HttpServletRequest request);

    public Principal getUser(HttpServletRequest request, HttpServletResponse response);

    public boolean login(HttpServletRequest request, HttpServletResponse response, String username, String password) throws AuthenticatorException;

    public boolean login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean storeCookie) throws AuthenticatorException;

    public boolean logout(HttpServletRequest request, HttpServletResponse response) throws AuthenticatorException;
}
