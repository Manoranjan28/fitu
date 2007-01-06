package org.commonfarm.security.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;

import org.commonfarm.security.auth.Authenticator;
import org.commonfarm.security.config.SecurityConfig;

public class SecurityUtils {
	private static Log log = LogFactory.getLog(SecurityUtils.class);

    public static Authenticator getAuthenticator(ServletContext servletContext) {
        SecurityConfig securityConfig = (SecurityConfig) servletContext.getAttribute(SecurityConfig.STORAGE_KEY);

        if (securityConfig.getAuthenticator() == null) {
            log.error("ack! Authenticator is null!!!");
        }
        return securityConfig.getAuthenticator();
    }
}
