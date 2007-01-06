package org.commonfarm.security.auth;

import org.commonfarm.security.Initable;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Determines whether an authenticated user has a "role" (permission) within the system, and specifically, whether
 * they have permission to log in to the system.
 * <p>
 * In applications using Seraph, role assignment is typically done by checking for membership of certain groups, eg:
 * <ul>
 *   <li>hasRole() implementation will map between group membership (eg. 'administrators' group) and roles (eg. 'delete_user', 'see_admin_pages').
 *   <li>canLogin() implementation checks for membership of a global "users" group, thus allowing existing users' access to a
 * site to be revoked by removal from the "users" group.
 * </ul>
 */
public interface RoleMapper extends Initable {
    boolean hasRole(Principal user, HttpServletRequest request, String role);
    boolean canLogin(Principal user, HttpServletRequest request);
}
