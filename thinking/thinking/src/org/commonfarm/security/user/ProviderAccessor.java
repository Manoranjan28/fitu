/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package org.commonfarm.security.user;

import java.io.Serializable;

import org.commonfarm.security.user.provider.UserProvider;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public interface ProviderAccessor extends Serializable {
    //~ Methods ////////////////////////////////////////////////////////////////

    public UserManager getUserManager();

    /**
     * Return appropriate AccessProvider for entity.
     */
    //AccessProvider getAccessProvider(String name);

    /**
     * Return appropriate CredentialsProvider for entity.
     */
    UserProvider getUserProvider(String name);

    /**
     * Return appropriate ProfileProvider for entity.
     */
    //ProfileProvider getProfileProvider(String name);
}
