package org.commonfarm.security.auth;

import java.security.Principal;

/**
 * Allow the user to be retrieved at any time throughout the application.  Typically implementations
 * of this class will use a Thread Local variable to store state.
 * <p>
 * If the client has access to the request, it is preferrable to get this information from the
 * {@link Authenticator} instead.
 */
public interface AuthenticationContext
{
    public Principal getUser();

    public void setUser(Principal user);

    public void clearUser();
}
