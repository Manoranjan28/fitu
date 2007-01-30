package org.commonfarm.app.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.app.model.User;
import org.commonfarm.service.ThinkingService;
import org.commonfarm.web.StrutsAction;

import com.opensymphony.xwork2.Preparable;

public class UserAction extends StrutsAction implements Preparable {
	private static final Log logger = LogFactory.getLog(UserAction.class);
	private long actionId;
	
	/** Search Criterias Start **/
	private String s_userId;
	private String s_secondName;
	/** Search Criterias End **/
	
	public UserAction(ThinkingService thinkingService) {
		super(thinkingService);
	}
	/**
	 * List Data
	 * @return
	 */
	public String list() throws Exception {
		if (logger.isDebugEnabled()) {
        	logger.debug("items found");
        }
		
		return search("user");
	}
	public void prepare() throws Exception {
		if(actionId == 0) {
            model = new User();
        } else {
            model = thinkingService.getObject(User.class, new Long(actionId));
        }

	}
	/**
	 * @return the s_userId
	 */
	public String getS_userId() {
		return s_userId;
	}
	/**
	 * @param id the s_userId to set
	 */
	public void setS_userId(String id) {
		s_userId = id;
	}
	/**
	 * @return the s_secondName
	 */
	public String getS_secondName() {
		return s_secondName;
	}
	/**
	 * @param name the s_secondName to set
	 */
	public void setS_secondName(String name) {
		s_secondName = name;
	}
}
