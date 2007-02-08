package org.commonfarm.security.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.commonfarm.security.Initable;
import org.commonfarm.security.SecurityService;
import org.commonfarm.security.auth.AuthenticationContext;
import org.commonfarm.security.auth.AuthenticationContextImpl;
import org.commonfarm.security.auth.Authenticator;
import org.commonfarm.security.auth.RoleMapper;
import org.commonfarm.security.controller.SecurityController;
import org.commonfarm.security.interceptor.Interceptor;
import org.commonfarm.util.ResourceUtil;
import org.commonfarm.util.BeanUtil;
import org.commonfarm.util.XMLUtil;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

/**
 * The main class of Seraph's configuration.
 * <p>
 * This class is a Singleton, access it using SecurityConfigFactory.getInstance().
 */
public class SecurityConfigImpl implements Serializable, SecurityConfig {
    private static final Log log = LogFactory.getLog(SecurityConfigImpl.class);
    private static SecurityConfigImpl instance = null;

    public static final String DEFAULT_CONFIG_LOCATION = "security-config.xml";

    private String configFileLocation = "security-config.xml";
    protected Authenticator authenticator = null;
    protected RoleMapper roleMapper = null;
    protected SecurityController controller;
    protected List services = null;
    protected List interceptors = null;

    private String loginURL;
    private String denyURL;
    private String logoutURL;
    private String originalURLKey = "seraph_originalurl";
    private String cookieEncoding;
    private String loginCookieKey = "seraph.os.cookie";
    private String linkLoginURL;

    public SecurityConfigImpl(String configFileLocation) throws ConfigurationException {
        if (configFileLocation != null) {
            this.configFileLocation = configFileLocation;
            log.debug("Config file location passed.  Location: " + this.configFileLocation);
        }
        else {
            log.debug("Initialising securityConfig using default configFile: " + this.configFileLocation);
        }
        init();
    }

    private void init() throws ConfigurationException {
        services = new ArrayList();
        interceptors = new ArrayList();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            URL fileUrl = ResourceUtil.getURL(configFileLocation);

            if (fileUrl == null)
                throw new IllegalArgumentException("No such XML file: " + configFileLocation);

            // Parse document
            org.w3c.dom.Document doc = factory.newDocumentBuilder().parse(fileUrl.toString());
            Element rootEl = doc.getDocumentElement();

            configureParameters(rootEl);
            configureAuthenticator(rootEl);
            configureController(rootEl);
            configureRoleMapper(rootEl);
            configureServices(rootEl);
            configureInterceptors(rootEl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConfigurationException("Exception configuring from '"+configFileLocation+"': " + e);
        }
    }

    protected void configureAuthenticator(Element rootEl) throws ConfigurationException {
        authenticator = (Authenticator) configureClass(rootEl, "authenticator");

        try {
            if (authenticator == null) {
                authenticator = (Authenticator) BeanUtil.getObject(Authenticator.DEFAULT_AUTHENTICATOR);
                authenticator.init(new HashMap(), this);
            }
        } catch (Exception e) {
            throw new ConfigurationException("Could not lookup class: " + Authenticator.DEFAULT_AUTHENTICATOR + ":" + e);
        }
    }

    protected void configureController(Element rootEl) throws ConfigurationException {
        controller = (SecurityController) configureClass(rootEl, "controller");

        try {
            if (controller == null)
                controller = (SecurityController) BeanUtil.getObject(SecurityController.NULL_CONTROLLER);
        } catch (Exception e) {
            throw new ConfigurationException("Could not lookup class: " + SecurityController.NULL_CONTROLLER + ":" + e);
        }
    }

    protected void configureRoleMapper(Element rootEl) throws ConfigurationException {
        roleMapper = (RoleMapper) configureClass(rootEl, "rolemapper");
    }

    private Initable configureClass(Element rootEl, String tagname) throws ConfigurationException {
        try {
            NodeList elementList = rootEl.getElementsByTagName(tagname);

            for (int i = 0; i < elementList.getLength(); i++) {
                Element authEl = (Element) elementList.item(i);
                String clazz = authEl.getAttribute("class");

                Initable initable = (Initable) BeanUtil.getObject(clazz);

                Map params = getInitParameters(authEl);

                initable.init(params, this);
                return initable;
            }
        } catch (Exception e) {
            throw new ConfigurationException("Could not create: " + tagname + ": " + e);
        }

        return null;
    }

    private void configureParameters(Element rootEl) {
        NodeList nl = rootEl.getElementsByTagName("parameters");
        Element parametersEl = (Element) nl.item(0);
        Map globalParams = getInitParameters(parametersEl);

        loginURL = (String) globalParams.get("login.url");
        denyURL = (String) globalParams.get("deny.url");
        linkLoginURL = (String) globalParams.get("link.login.url");
        logoutURL = (String) globalParams.get("logout.url");

        if (globalParams.get("original.url.key") != null)
            originalURLKey = (String) globalParams.get("original.url.key");

        if (globalParams.get("cookie.encoding") != null)
            cookieEncoding = (String) globalParams.get("cookie.encoding");

        if (globalParams.get("login.cookie.key") != null)
            loginCookieKey = (String) globalParams.get("login.cookie.key");
    }

    private void configureServices(Element rootEl) throws ConfigurationException {
        NodeList nl = rootEl.getElementsByTagName("services");

        if (nl != null && nl.getLength() > 0) {
            Element servicesEl = (Element) nl.item(0);
            NodeList serviceList = servicesEl.getElementsByTagName("service");

            for (int i = 0; i < serviceList.getLength(); i++) {
                Element serviceEl = (Element) serviceList.item(i);
                String serviceClazz = serviceEl.getAttribute("class");

                if (serviceClazz == null || "".equals(serviceClazz))
                    throw new ConfigurationException("Service element with bad class attribute");

                try {
                    log.debug("Adding seraph service of class: " + serviceClazz);
                    SecurityService service = (SecurityService) BeanUtil.getObject(serviceClazz);

                    Map serviceParams = getInitParameters(serviceEl);

                    service.init(serviceParams, this);

                    services.add(service);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ConfigurationException("Could not getRequest service: " + serviceClazz + ". Exception: " + e);
                }
            }
        }
    }

    protected void configureInterceptors(Element rootEl) throws ConfigurationException {
        NodeList nl = rootEl.getElementsByTagName("interceptors");

        if (nl != null && nl.getLength() > 0) {
            Element interceptorsEl = (Element) nl.item(0);
            NodeList interceptorList = interceptorsEl.getElementsByTagName("interceptor");

            for (int i = 0; i < interceptorList.getLength(); i++) {
                Element interceptorEl = (Element) interceptorList.item(i);
                String interceptorClazz = interceptorEl.getAttribute("class");

                if (interceptorClazz == null || "".equals(interceptorClazz))
                	throw new ConfigurationException("Interceptor element with bad class attribute");

                try {
                    log.debug("Adding interceptor of class: " + interceptorClazz);
                    Interceptor interceptor = (Interceptor) BeanUtil.getObject(interceptorClazz);

                    Map interceptorParams = getInitParameters(interceptorEl);

                    interceptor.init(interceptorParams, this);

                    interceptors.add(interceptor);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ConfigurationException("Could not getRequest service: " + interceptorClazz + ". Exception: " + e);
                }
            }
        }
    }

    protected Map getInitParameters(Element el) {
        Map params = new HashMap();

        NodeList nl = el.getElementsByTagName("init-param");

        for (int i = 0; i < nl.getLength(); i++) {
            Node initParam = nl.item(i);
            String paramName = XMLUtil.getContainedText(initParam, "param-name");
            String paramValue = XMLUtil.getContainedText(initParam, "param-value");
            params.put(paramName, paramValue);
        }

        return params;
    }

    public void destroy() {
        for (Iterator iterator = services.iterator(); iterator.hasNext();) {
            SecurityService securityService = (SecurityService) iterator.next();
            securityService.destroy();
        }

        for (Iterator iterator = interceptors.iterator(); iterator.hasNext();) {
            ((Interceptor) iterator.next()).destroy();
        }

        return;
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }
    
    public List getServices() {
        return services;
    }

    public String getLoginURL() {
        return loginURL;
    }
    
    public String getDenyURL() {
        return denyURL;
    }

    public String getLinkLoginURL() {
        return linkLoginURL;
    }

    public String getLogoutURL() {
        return logoutURL;
    }

    public String getOriginalURLKey() {
        return originalURLKey;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public AuthenticationContext getAuthenticationContext() {
        return new AuthenticationContextImpl(); //todo - load this from a config file
    }

    public SecurityController getController() {
        return controller;
    }

    /** Instantiates a SecurityConfigImpl. If you just want a {@link SecurityConfig}, use
     * {@link SecurityConfigFactory#getInstance(java.lang.String)} instead.
     */
    public static SecurityConfigImpl getInstance(String configFileLocation) throws ConfigurationException {
        instance = new SecurityConfigImpl(configFileLocation);
        return instance;
    }

    /** Instantiates a SecurityConfigImpl using the default config file.
     *  If you just want a {@link SecurityConfig}, use
     * {@link SecurityConfigFactory#getInstance()} instead.
     */
    public static SecurityConfig getInstance() {
        if (instance == null) {
            try {
                if (instance == null) {
                    instance = new SecurityConfigImpl(DEFAULT_CONFIG_LOCATION);
                }
            } catch (ConfigurationException e) {
                log.error("Could not configure SecurityConfigImpl instance: " + e, e);
            }
        }

        return instance;
    }

    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    public List getInterceptors(Class desiredInterceptorClass) {
        List result = new ArrayList();

        for (Iterator iterator = interceptors.iterator(); iterator.hasNext();) {
            Interceptor interceptor = (Interceptor) iterator.next();

            if (desiredInterceptorClass.isAssignableFrom(interceptor.getClass())) {
                result.add(interceptor);
            }
        }

        return result;
    }

    public String getCookieEncoding() {
        return cookieEncoding;
    }

    public String getLoginCookieKey() {
        return loginCookieKey;
    }

}
