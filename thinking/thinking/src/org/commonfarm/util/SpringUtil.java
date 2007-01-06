package org.commonfarm.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 
 * @author david
 */
public class SpringUtil {

	public static Object getBean(HttpServletRequest request, String beanName) {
		XmlWebApplicationContext xmlContext =
            (XmlWebApplicationContext) request.getSession().getServletContext()
                .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return xmlContext.getBean(beanName);
	}

}
