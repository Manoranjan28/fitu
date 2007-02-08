package org.commonfarm.security;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Set;

/**
 * A SecurityService determines which roles a user is required to have to access a resource.
 *
 * Two services are provided with Security by default
 */
public interface SecurityService extends Serializable, Initable {
    public void destroy();

    public Set getRequiredRoles(HttpServletRequest request);
    
    public Set getRequiredRolesFromConfig(HttpServletRequest request);
}
