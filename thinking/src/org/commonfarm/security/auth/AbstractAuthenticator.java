package org.commonfarm.security.auth;

import org.commonfarm.security.config.SecurityConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.Serializable;
import java.util.Map;
import java.security.Principal;

/**
 * An abstract implementation of Authenticator that implements a lot of base methods 
 */
public abstract class AbstractAuthenticator implements Authenticator, Serializable {
    private Map params;
    private SecurityConfig config;
    private ApplicationContext ctx;
    public Object getBean(String name, HttpServletRequest request) {
	    if (ctx == null) {
			ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(request.getSession().getServletContext());
		}
	
		return ctx.getBean(name);
	}
    public void init(Map params, SecurityConfig config) {
        this.params = params;
        this.config = config;
    }

    public void destroy() {}

    public String getRemoteUser(HttpServletRequest request) {
        Principal user = getUser(request);
        if (user == null) return null;
        return user.getName();
    }

    public Principal getUser(HttpServletRequest request) {
        return getUser(request, null);
    }

    public abstract Principal getUser(HttpServletRequest request, HttpServletResponse response);

    public boolean login(HttpServletRequest request, HttpServletResponse response, String username, String password) throws AuthenticatorException {
        return login(request, response, username, password, false);
    }

    public abstract boolean login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean cookie) throws AuthenticatorException;

    public abstract boolean logout(HttpServletRequest request, HttpServletResponse response) throws AuthenticatorException;

    protected SecurityConfig getConfig() {
        return config;
    }

	/**
	 * @return Returns the params.
	 */
	public Map getParams() {
		return params;
	}

	/**
	 * @param params The params to set.
	 */
	public void setParams(Map params) {
		this.params = params;
	}
}
