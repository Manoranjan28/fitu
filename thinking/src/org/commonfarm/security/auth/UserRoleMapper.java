package org.commonfarm.security.auth;

import javax.servlet.http.HttpServletRequest;

import org.commonfarm.security.config.SecurityConfig;
import org.commonfarm.security.user.LoginUser;

import java.util.Map;
import java.security.Principal;

/**
 * A simple RoleMapper which maps directly between group names and role names.
 *
 * If a user is a member of someGroup, the are in the 'someGroup' role.  Users can log in if they exist.
 */
public class UserRoleMapper implements RoleMapper {
    public void init(Map params, SecurityConfig config) {}

    /**
     * Assume that roles == groups.
     */
    public boolean hasRole(Principal user, HttpServletRequest request, String role) {
        if (user == null) return false;
    	return ((LoginUser) user).contains(role);
    }

    /**
     * Users can login if they exist.
     */
    public boolean canLogin(Principal user, HttpServletRequest request) {
        return user != null;
    }
}
