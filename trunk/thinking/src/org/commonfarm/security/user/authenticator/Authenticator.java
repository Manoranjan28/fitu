/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package org.commonfarm.security.user.authenticator;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;


/**
 * Interface describing how to authenicate a user.
 *
 * @author <a href="mailto:mike@atlassian.com">Mike Cannon-Brookes</a>
 */
public interface Authenticator {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
    * Login a user via a username and password
    *
    * @return Whether or not login was successful.
    */
    public boolean login(String username, String password) throws AuthenticationException;

    /**
    * Called by UserManager before any other method.
    * Allows for Authenticator specific initialization.
    *
    * @param properties Extra properties passed across by UserManager.
    */
    boolean init(Properties properties);

    boolean login(String username, String password, HttpServletRequest req) throws AuthenticationException;
}
