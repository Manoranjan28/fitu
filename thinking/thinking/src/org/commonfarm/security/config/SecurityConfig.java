package org.commonfarm.security.config;

import org.commonfarm.security.auth.Authenticator;
import org.commonfarm.security.auth.RoleMapper;
import org.commonfarm.security.auth.AuthenticationContext;
import org.commonfarm.security.controller.SecurityController;

import java.util.List;

public interface SecurityConfig {
	public String STORAGE_KEY = "IVOSecurity_config";

	public List getServices();

	public String getLoginURL();
	
	public String getDenyURL();

	public String getLinkLoginURL();

	public String getLogoutURL();

	public String getOriginalURLKey();

	public Authenticator getAuthenticator();

	public AuthenticationContext getAuthenticationContext();

	public SecurityController getController();

	public RoleMapper getRoleMapper();

	public List getInterceptors(Class desiredInterceptorClass);

	public String getCookieEncoding();

	public String getLoginCookieKey();

	public void destroy();
}
