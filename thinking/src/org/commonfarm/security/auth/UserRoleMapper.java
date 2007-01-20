package org.commonfarm.security.auth;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.commonfarm.app.model.User;
import org.commonfarm.security.config.SecurityConfig;

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
    	return ((User) user).contains(role);
    }

    /**
     * Users can login if they exist.
     */
    public boolean canLogin(Principal user, HttpServletRequest request) {
        return user != null;
    }
}
