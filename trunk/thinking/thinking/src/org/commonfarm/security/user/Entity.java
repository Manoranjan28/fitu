/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package org.commonfarm.security.user;

import org.commonfarm.security.user.provider.UserProvider;

import java.io.Serializable;


/**
 * Superclass for LoginUser and Group.
 *
 * <p>Methods common to both LoginUser and Group are defined here.</p>
 *
 * <p>When an entity is modified the store() method has to be called to force the
 * provider to persist changes. This is a convenience for Providers and they may choose
 * to write data before then.</p>
 *
 * @author <a href="mailto:joe@truemesh.com">Joe Walnes</a>
 * @version $Revision: 1.2 $
 */
public abstract class Entity implements Serializable {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected Accessor accessor;

    /**
     * Name of entity (unique).
     */
    protected String name;

    /**
     * Whether this entity is mutable (i.e. can be modifed).
     */
    protected boolean mutable;
    private UserProvider userProvider;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Constructor to be called by LoginUser or Group. Should pass across name of Entity
     * and UserManager.Accessor for priveleged access to the UserManager.
     */
    protected Entity(String name, UserProvider userProvider) {
        this.name = name;
        this.userProvider = userProvider;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Retrieve pluggable CredentialsProvider for this entity.
     */
    public UserProvider getUserProvider() {
        return userProvider;
    }

    /**
     * Name (unique identifier) of entity.
     * This cannot be changed once the entity has been created.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieve pluggable ProfileProvider for this entity.
     */
    /*public ProfileProvider getProfileProvider() {
        return providerAccessor.getProfileProvider(name);
    }*/

    /**
     * Remove this entity from existence.
     * If a provider does not allow removal, ImmutableException shall be thrown.
     */
    public abstract void remove() throws ImmutableException;

    /**
     * Retrieve pluggable AccessProvider for this entity.
     */
    /*public AccessProvider getAccessProvider() {
        return providerAccessor.getAccessProvider(name);
    }*/

    /**
     * Determine if entity is mutable. If entity is read-only, false is returned.
     */
    public boolean isMutable() {
        return mutable;
    }

    /**
     * Compare name.
     */
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj.getClass().equals(this.getClass()))) {
            return false;
        } else {
            return name.equals(((Entity) obj).getName());
        }
    }

    /**
     * Hashcode of name.
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Force update to underlying data-stores.
     * This allows providers that do not update persistent data on the fly to store changes.
     * If any of the providers are immutable and fields that cannot be updated have changed,
     * ImmutableException shall be thrown.
     */
    public void store() throws ImmutableException {
        if (!mutable) {
            throw new ImmutableException();
        }
    }

    /**
      * String representation returns name.
      */
    public String toString() {
        return name;
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    /**
     * This inner class can be passed by Entity to a Accessor to allow private fields
     * to be accessed from an external object. Passed specifically to UserProvider
     * implementations, while still encapsulating data from client code.
     */
    public abstract class Accessor implements Serializable {
        /**
         * Return underlying Entity that this Accessor represents.
         */
        public Entity getEntity() {
            return Entity.this;
        }

        /**
         * Set whether Entity is mutable.
         */
        public void setMutable(boolean mutable) {
            Entity.this.mutable = mutable;
        }

        /**
         * Set name of Entity.
         */
        public void setName(String name) {
            Entity.this.name = name;
        }
    }
}
