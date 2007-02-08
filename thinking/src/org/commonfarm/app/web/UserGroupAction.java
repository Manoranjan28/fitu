package org.commonfarm.app.web;

import org.commonfarm.app.model.Role;
import org.commonfarm.app.model.UserGroup;
import org.commonfarm.service.ThinkingService;
import org.commonfarm.util.StringUtil;
import org.commonfarm.web.StrutsAction;

import com.opensymphony.xwork2.Preparable;

public class UserGroupAction extends StrutsAction implements Preparable {
	//private static final Log logger = LogFactory.getLog(UserAction.class);
	private long actionId;
	
	/** Request Parameters Start */
	/** Request Parameters End */
	
	/** Search Criterias Start **/
	private String s_name;
	/** Search Criterias End **/
	
	public UserGroupAction(ThinkingService thinkingService) {
		super(thinkingService);
	}
	
	public void prepare() throws Exception {
		if (StringUtil.isEmpty(getSearchName())) setSearchName("userGroup");
		if(actionId == 0) {
            model = new UserGroup();
        } else {
            model = thinkingService.getObject(Role.class, new Long(actionId));
        }

	}

	/**
	 * @return the s_name
	 */
	public String getS_name() {
		return s_name;
	}

	/**
	 * @param s_name the s_name to set
	 */
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
}
