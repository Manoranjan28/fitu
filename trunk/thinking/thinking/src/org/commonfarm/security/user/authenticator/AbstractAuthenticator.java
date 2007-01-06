/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package org.commonfarm.security.user.authenticator;

import java.io.Serializable;

import java.util.Properties;


/**
 * An abstract class which helps simplify the writing of authenticators for new app servers.
 *
 * @author <a href="mailto:mike@atlassian.com">Mike Cannon-Brookes</a>
 */
public abstract class AbstractAuthenticator implements Authenticator, Serializable {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected Properties properties;

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
    * Simple method to init and store any passed properties
    *
    * You will probably want to override this in your subclass if you need initialisation
    */
    public boolean init(Properties properties) {
        this.properties = properties;

        return true;
    }

    /**
    * Implement your server specific login method here.
    */
    public boolean login(String username, String password) throws AuthenticationException {
        return login(username, password, null);
    }
}
