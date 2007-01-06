/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package org.commonfarm.security.user.authenticator;


/**
 * This is thrown if there is a problem with authentication.
 *
 * NOTE: This is NOT thrown if Authentication failed - in that case login(x,y) returns false.
 */
public class AuthenticationException extends Exception {
    //~ Constructors ///////////////////////////////////////////////////////////

    public AuthenticationException() {
    }

    public AuthenticationException(String s) {
        super(s);
    }
}
