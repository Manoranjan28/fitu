package org.commonfarm.app.web;

import org.commonfarm.app.model.Role;
import org.commonfarm.service.BusinessException;
import org.commonfarm.service.ThinkingService;
import org.commonfarm.util.StringUtil;
import org.commonfarm.web.StrutsAction;

import com.opensymphony.xwork2.Preparable;

public class RoleAction extends StrutsAction implements Preparable {
	//private static final Log logger = LogFactory.getLog(UserAction.class);
	private long actionId;
	
	/** Request Parameters Start */
	/** Request Parameters End */
	
	/** Search Criterias Start **/
	private String s_name;
	/** Search Criterias End **/
	
	public RoleAction(ThinkingService thinkingService) {
		super(thinkingService);
	}
	
	public void remove(String id) throws BusinessException {
		thinkingService.removeObject(model, new Long(id), new String[] {"groups", "role"});
	}
	public void prepare() throws Exception {
		if (StringUtil.isEmpty(getSearchName())) setSearchName("role");
		if(actionId == 0) {
            model = new Role();
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
