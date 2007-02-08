package org.commonfarm.web;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.Constant;
import org.commonfarm.search.SearchUtil;


/**
 * StartupListener class used to read config file
 *
 * @author David Yang
 *
 */
public class StartupListener implements ServletContextListener {
    private static Log logger = LogFactory.getLog(StartupListener.class);
    private ServletContext context;

    public void contextInitialized(ServletContextEvent sce) {
        context = sce.getServletContext();

        if (logger.isDebugEnabled()) {
        	logger.debug("contextInitialized...");
        }


        setupContext(context);
        
        // output the retrieved values for the Init and Context Parameters
        if (logger.isDebugEnabled()) {
        	logger.debug("Initialization complete [OK]");
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        if (logger.isDebugEnabled()) {
        	logger.debug("contextDestroyed...");
        }

        context = null;
    }
    
    public static void setupContext(ServletContext context) {
        // populate drop-downs and stuff in servlet context
        try {
        	Map searchInfo = SearchUtil.getSearchInfo();
        	context.setAttribute(Constant.CONFIG_SEARCH, searchInfo);
        } catch (Exception e) {
        	logger.error("Error Read Search Config file!" + e.getMessage());
            e.printStackTrace();
        }   
    }
}
