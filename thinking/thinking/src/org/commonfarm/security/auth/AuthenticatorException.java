package org.commonfarm.security.auth;

/**
 * An exception for all Authenticator related error conditions
 *
 * @see Authenticator
 */
public class AuthenticatorException extends Exception
{
    public AuthenticatorException()
    {
    }

    public AuthenticatorException(String s)
    {
        super(s);
    }
}
