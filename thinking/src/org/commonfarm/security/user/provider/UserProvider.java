/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package org.commonfarm.security.user.provider;

import java.io.Serializable;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.commonfarm.security.user.Entity;
import org.commonfarm.security.user.LoginUser;

/**
 * A UserProvider is a plug-in implementation that allows a UserManager to
 * access data in the back-end store.
 * 
 * <p>
 * A UserProvider implementation should always contain a public default
 * constructor. The init() method shall always be called before any other
 * methods.
 * </p>
 * 
 * <p>
 * Almost all methods return a boolean. This is to signify whether the operation
 * was successful.
 * </p>
 * 
 * @author <a href="mailto:joe@truemesh.com">Joe Walnes</a>
 * @version $Revision: 1.3 $
 * 
 * @see com.opensymphony.user.provider.CredentialsProvider
 * @see com.opensymphony.user.provider.AccessProvider
 * @see com.opensymphony.user.provider.ProfileProvider
 */
public interface UserProvider extends Serializable {
	// ~ Methods
	// ////////////////////////////////////////////////////////////////

	/**
	 * Create new Entity with given name.
	 * 
	 * @return Whether entity was successfully created.
	 */
	boolean create(String name);

	/**
	 * Flush the providers caches - if it is caching.
	 * 
	 * Providers may implement their own caching strategies. This method merely
	 * indicates to the provider that it should flush it's caches now.
	 */
	void flushCaches();

	/**
	 * Determine whether this UserProvider implementation is responsible for
	 * handling this Entity.
	 */
	boolean handles(String name);
	boolean handles(LoginUser user);

	/**
	 * Called by UserManager before any other method. Allows for UserProvider
	 * specific initialization.
	 * 
	 * @param properties
	 *            Extra properties passed across by UserManager.
	 */
	boolean init(Properties properties);

	/**
	 * Returns List of names (Strings) of all Entities that can be accessed by
	 * this UserProvider If this UserProvider cannot retrieve a list of names,
	 * null is to be returned. If there are no current Entities stored by this
	 * provider, an empty List is to be returned. The order of names returned
	 * can be determined by the UserProvider (it may or may not be relevant).
	 * 
	 * This List should be immutable.
	 */
	List list();

	/**
	 * Load Entity.
	 * 
	 * @return Whether entity was successfully loaded.
	 */
	boolean load(String name, Entity.Accessor accessor);

	/**
	 * Remove Entity with given name.
	 * 
	 * @return Whether entity was successfully removed.
	 */
	boolean remove(String name);

	/**
	 * Stores changes to Entity.
	 * 
	 * @return Whether changes to entity were successfully stored.
	 */
	boolean store(String name, Entity.Accessor accessor);

	/**
	 * Check password supplied matches that of LoginUser.
	 */
	boolean authenticate(String name, String password);

	/**
	 * Change password of user.
	 */
	boolean changePassword(String name, String password);
	/**
	 * Get roles of user
	 */
	public Set getRoles(String name);
}
