package org.commonfarm.security;

import org.commonfarm.security.config.SecurityConfig;

import java.util.Map;

/**
 * A simple interface to indicate all init-able classes in IVOSecurity
 */
public interface Initable {
    public void init(Map params, SecurityConfig config);
}
