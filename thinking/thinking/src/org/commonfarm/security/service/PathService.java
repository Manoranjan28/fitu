package org.commonfarm.security.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.security.SecurityService;
import org.commonfarm.security.config.SecurityConfig;
import org.commonfarm.security.util.CachedPathMapper;
import org.commonfarm.security.util.PathMapper;
import org.commonfarm.util.ResourceUtil;
import org.commonfarm.util.SpringUtil;
import org.commonfarm.util.XMLUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Configures Seraph to require certain roles to access certain URL paths.
 * <p>
 * Single init-param 'config.file' which is the location of the XML config file.
 * Default value is '/seraph-paths.xml' (loaded from classpath - usually in /WEB-INF/classes)
 * <p>
 * Here's a sample of the XML config file. Path names must be unique
 *<p>
 * <pre>
 * &lt;seraph-paths>
 *	&lt;path name="admin">
 * 		&lt;url-pattern>/secure/admin/*&lt;/url-pattern>
 * 		&lt;role-name>administrators&lt;/role-name>
 * 	&lt;/path>
 * 	&lt;path name="secured">
 *		&lt;url-pattern>/secure/*&lt;/url-pattern>
 *		&lt;role-name>users&lt;/role-name>
 *	&lt;/path>
 * &lt;/seraph-paths>
 * </pre>
 *
 */
public class PathService implements SecurityService {
	private Log log = LogFactory.getLog(PathService.class);
    static String CONFIG_FILE_PARAM_KEY = "config.file";

    String configFileLocation = "classpath:security-paths.xml";

    // maps url patterns to path names
    private PathMapper pathMapper = new CachedPathMapper(new HashMap(), new HashMap());

    // maps roles to path names
    private Map paths = new HashMap();


    /**
     * Init the service - configure it from the config file
     */
    public void init(Map params, SecurityConfig config) {
        try {
            if (params.get(CONFIG_FILE_PARAM_KEY) != null) {
                configFileLocation = (String) params.get(CONFIG_FILE_PARAM_KEY);
            }

            configurePathMapper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Configure the path mapper
     */
    private void configurePathMapper() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            URL fileUrl = ResourceUtil.getURL(configFileLocation);

            if (fileUrl == null)
                fileUrl = ResourceUtil.getURL("/" + configFileLocation);


            if (fileUrl == null)
                return;

            // Parse document
            org.w3c.dom.Document doc = factory.newDocumentBuilder().parse(fileUrl.toString());

            // Get list of actions
            Element root = doc.getDocumentElement();
            NodeList pathNodes = root.getElementsByTagName("path");

            // Build list of views
            for (int i = 0; i < pathNodes.getLength(); i++) {
                Element path = (Element) pathNodes.item(i);

                String pathName = path.getAttribute("name");
                String roleNames = XMLUtil.getContainedText(path, "role-name");
                String urlPattern = XMLUtil.getContainedText(path, "url-pattern");

                if (roleNames != null && urlPattern != null) {
                    List rolesArr = parseRoles(roleNames);
                    paths.put(pathName, rolesArr);
                    pathMapper.put(pathName, urlPattern);
                }
            }
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    protected List parseRoles(String roleNames) {
        StringTokenizer st = new StringTokenizer(roleNames, ",; \t\n", false);
        List roles = new ArrayList();
        while (st.hasMoreTokens()) {
        	roles.add(st.nextToken());
        }
        return roles;
    }

    public void destroy() {}

    public Set getRequiredRolesFromConfig(HttpServletRequest request) {
    	String servletPath = request.getServletPath();
        return getRequiredRoles(servletPath);
    }
    
    public Set getRequiredRoles(String servletPath) {
        Set requiredRoles = new HashSet();

        // then check path mappings first and add any required roles
        Collection constraintMatches = pathMapper.getAll(servletPath);

        for (Iterator iterator = constraintMatches.iterator(); iterator.hasNext();) {
            String constraint = (String) iterator.next();

            /*String[] rolesForPath = (List) paths.get(constraint);
            for (int i = 0; i < rolesForPath.length; i++) {
                if (!requiredRoles.contains(rolesForPath[i])) {// since requiredRoles is a set, isn't this useless?
                    requiredRoles.add(rolesForPath[i]);
                }
            }*/
            List rolesForPath = (List) paths.get(constraint);
            for (Iterator it = rolesForPath.iterator(); it.hasNext();) {
            	Object role = it.next();
                if (!requiredRoles.contains(role)) {// since requiredRoles is a set, isn't this useless?
                    requiredRoles.add(role);
                }
            }
        }

        return requiredRoles;
    }
    /**
     * DB setting
     */
    public Set getRequiredRoles(HttpServletRequest request) {
    	AuthService authService = (AuthService) SpringUtil.getBean(request, "authService");
    	if (authService != null) authService.configURLAuth(paths, pathMapper);
    	
        String servletPath = request.getServletPath();
        return getRequiredRoles(servletPath);
    }

}
