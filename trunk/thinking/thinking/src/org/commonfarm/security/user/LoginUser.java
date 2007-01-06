/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package org.commonfarm.security.user;

import java.io.Serializable;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.commonfarm.security.user.provider.UserProvider;


/**
 * Entity to represent an actual LoginUser of the system.
 *
 * <p>A user's profile is stored in the PropertySet of the Entity
 * - getFullName() and getEmail() are provided for convenient
 * access to the underlying PropertySet.</p>
 *
 * <p>Refer to Entity for more information.</p>
 *
 * @author <a href="mailto:joe@truemesh.com">Joe Walnes</a>
 * @version $Revision: 1.5 $
 *
 * @see com.opensymphony.user.Entity
 */
public final class LoginUser extends Entity implements Principal {
    //~ Static fields/initializers /////////////////////////////////////////////
	private Long id;
	private String fullName;
	private String email;
	private String password;
	private String empId;
	private String status;
	private Set roles = new HashSet();

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
    * Constructor is only called by UserManager.
    */
    public LoginUser(String name, UserProvider userProvider) {
        super(name, userProvider);
        this.accessor = new Accessor();
        roles = getUserProvider().getRoles(name);
        getUserProvider().load(name, accessor);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
    * Change the LoginUser's password.
    */
    public void setPassword(String password) throws ImmutableException {
        if (mutable && getUserProvider().changePassword(name, password)) {
            return;
        }

        throw new ImmutableException();
    }

    /**
    * Verify that the supplied password matches the stored password for the user.
    */
    public boolean authenticate(String password) {
        if (password == null) {
            return false;
        }

        return getUserProvider().authenticate(name, password);
    }

    /**
    * Remove LoginUser from CredentialsProvider (providing it is mutable).
    */
    public void remove() throws ImmutableException {
        UserProvider userProvider = getUserProvider();

        if (!mutable) {
            throw new ImmutableException("LoginUser is not mutable");
        }

        if (userProvider == null) {
            throw new ImmutableException("No credentials provider for user");
        }

        if (!userProvider.remove(name)) {
            throw new ImmutableException("Credentials provider failed to remove user");
        }

        /*ProfileProvider profileProvider = getProfileProvider();

        if (profileProvider != null) {
            profileProvider.remove(name);
        }*/
    }

    /**
    * Force update to underlying data-stores.
    * This allows providers that do not update persistent data on the fly to store changes.
    * If any of the providers are immutable and fields that cannot be updated have changed,
    * ImmutableException shall be thrown.
    */
    public void store() throws ImmutableException {
        super.store();
        //getProfileProvider().store(name, accessor);
        getUserProvider().store(name, accessor);
    }
    /**
     * Is role include this user?
     * @param role
     * @return
     */
    public boolean contains(String role) {
    	return getRoles().contains(role);
	}
    //~ Inner Classes //////////////////////////////////////////////////////////

    /**
    * LoginUser specific Accessor.
    *
    * @see com.opensymphony.user.Entity.Accessor
    */
    public final class Accessor extends Entity.Accessor implements Serializable {
        /**
        * Return underlying LoginUser this Accessor represents.
        */
        public LoginUser getUser() {
            return (LoginUser) getEntity();
        }
    }
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the empId.
	 */
	public String getEmpId() {
		return empId;
	}

	/**
	 * @param empId The empId to set.
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/**
	 * @return Returns the fullName.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName The fullName to set.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return Returns the roles.
	 */
	public Set getRoles() {
		return roles;
	}

	/**
	 * @param roles The roles to set.
	 */
	public void setRoles(Set roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
