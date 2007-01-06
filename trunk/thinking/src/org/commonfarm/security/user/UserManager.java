/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package org.commonfarm.security.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.commonfarm.security.user.authenticator.Authenticator;
import org.commonfarm.security.user.provider.UserProvider;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * The UserManager is the entry point to user-management.
 * UserManager is a singleton and retrieved with getInstance();
 *
 * @author <a href="mailto:joe@truemesh.com">Joe Walnes</a>
 * @author <a href="mailto:mike@atlassian.com">Mike Cannon-Brookes</a>
 */
public final class UserManager implements Serializable {
    //~ Static fields/initializers /////////////////////////////////////////////
    private static final Log logger = LogFactory.getLog(UserManager.class);
    private static UserManager instance;
    //private static final int TYPE_USER = 0;
    //private static final int TYPE_GROUP = 1;

    //~ Instance fields ////////////////////////////////////////////////////////

    private Authenticator authenticator = null;
    //private UserProvider userProvider = null;
    private UserProvider userProvider;
    //private List accessProviders = new ArrayList();
    //private List credentialsProviders = new ArrayList();
    //private List profileProviders = new ArrayList();

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Don't use this constructor most of the time. To use OSUser as a singleton, use {@link #getInstance()} .
     */
    public UserManager() {}

    //~ Methods ////////////////////////////////////////////////////////////////
    /**
     * Entry-point to Singleton instance
     */
    public static UserManager getInstance() {
        try {
            if (instance == null) {
                instance = new UserManager();
            }
        } catch (UserManagerImplementationException e) {
            logger.error("Unable to load configuration", e);
        } catch (RuntimeException e) {
            logger.error("unexpected runtime exception during initialization", e);
        }

        return instance;
    }
    /**
     * Get the current AccessProviders
     *//*
    public Collection getAccessProviders() {
        return accessProviders;
    }*/

    /**
     * Set a new authenticator
     *
     * TODO: Should this be removed? Is it needed?
     */
    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    /**
     * Get the current authenticator
     */
    public Authenticator getAuthenticator() {
        return authenticator;
    }

    /**
     * Return user with given name. If LoginUser is not found, an EntityNotFoundException is thrown.
     */
    public LoginUser getUser(String name) throws EntityNotFoundException {
    	LoginUser user = new LoginUser(name, userProvider);
        //return (LoginUser) getEntity(name, credentialsProviders);
    	if (!userProvider.handles(user)) return null;
    	//userProvider.
    	return user;
    }

    /**
     * Return all known Users from all CredentialProviders that allow listing.
     */
    public List getUsers() {
        //return getEntities(credentialsProviders);
    	return getEntities(userProvider);
    }

    /**
     * Create a new LoginUser with given name.
     *
     * <ul>
     * <li>Firstly, all providers will be asked if a LoginUser with the given name exists
     * - if it does, DuplicateEntityException is thrown. </li>
     * <li>The providers shall be iterated through until one of them states that it
     * can create the LoginUser. </li>
     * <li>If no provider can create the LoginUser, ImmutableException shall be thrown.</li>
     */
    public LoginUser createUser(String name) throws DuplicateEntityException, ImmutableException {
        //return (LoginUser) createEntity(name, credentialsProviders);
    	return (LoginUser) createEntity(name, userProvider);
    }

    private List getEntities(UserProvider userProvider) {
        List result = new ArrayList();
        //List toCheck = credentialsProviders;
        List entities = userProvider.list();
        for (Iterator j = entities.iterator(); j.hasNext();) {
            String name = (String) j.next();
            Entity entity = createEntity(name, userProvider);
            result.add(entity);
        }

        return result;
    }
    
    /*private Entity getEntity(String name, UserProvider userProvider) throws EntityNotFoundException {
        if (userProvider == null) {
            throw new EntityNotFoundException("No user " + name + " found");
        }

        return createEntity(name, userProvider);
    }*/

    protected UserProvider getUserProvider(String name, UserProvider provider) {
        if (provider.handles(name)) {
            return provider;
        }

        return null;
    }
    
    private Entity createEntity(String name, UserProvider userProvider) {
    	return new LoginUser(name, userProvider);
    }

    /*private Entity buildEntity(String name, UserProvider userProvider) throws DuplicateEntityException, ImmutableException {
        if (userProvider != null) {
            throw new DuplicateEntityException("user " + name + " already exists");
        }

        return buildEntity(name, userProvider);

        // if we get here, none of the providers have helped
        //throw new ImmutableException("No provider successfully created entity " + name);
    }*/
    /**
	 * @return Returns the credentialsProvider.
	 */
	public UserProvider getUserProvider() {
		return userProvider;
	}

	/**
	 * @param credentialsProvider The credentialsProvider to set.
	 */
	public void setUserProvider(UserProvider userProvider) {
		this.userProvider = userProvider;
	}
}
