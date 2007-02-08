package org.commonfarm.security.controller;

import org.commonfarm.security.config.SecurityConfig;

import java.util.Map;

/**
 * The default Seraph controller implementation will always enable security.
 *
 * @see SecurityController
 */
public class NullSecurityController implements SecurityController {
    public boolean isSecurityEnabled() {
        return true;
    }

    public void init(Map params, SecurityConfig config) {
    }
}
