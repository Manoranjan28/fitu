package org.commonfarm.app.web;

import org.commonfarm.app.model.User;
import org.commonfarm.service.BusinessException;
import org.commonfarm.service.ThinkingService;
import org.commonfarm.util.StringUtil;
import org.commonfarm.web.StrutsAction;

import com.opensymphony.xwork2.Preparable;

public class UserAction extends StrutsAction implements Preparable {
	//private static final Log logger = LogFactory.getLog(UserAction.class);
	private long actionId;
	
	/** Request Parameters Start */
	private Long userId;
	/** Request Parameters End */
	
	/** Search Criterias Start **/
	private String s_userId;
	private String s_secondName;
	/** Search Criterias End **/
	
	public UserAction(ThinkingService thinkingService) {
		super(thinkingService);
	}
	/**
	 * if you master this framework, you must not implement this method and you can use invention
	 */
	public void remove(String id) throws BusinessException {
		thinkingService.removeObject(model, new Long(id), new String[] {"groups", "users"});
	}
	
	public void prepare() throws Exception {
		if (StringUtil.isEmpty(getSearchName())) setSearchName("user");
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
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
