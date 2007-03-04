package org.commonfarm.community.web;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.commonfarm.app.model.User;
import org.commonfarm.app.model.UserGroup;
import org.commonfarm.service.BusinessException;
import org.commonfarm.service.ThinkingService;
import org.commonfarm.util.StringUtil;
import org.commonfarm.web.StrutsAction;

import com.opensymphony.xwork2.Preparable;

public class ArticleAction extends StrutsAction implements Preparable {
	//private static final Log logger = LogFactory.getLog(UserAction.class);
	private long actionId;
	
	/** Request Parameters Start */
	private Long userId;
	/** Request Parameters End */
	
	/** Search Criterias Start **/
	private String s_userId;
	private String s_secondName;
	//select group criterias
	private String s_name;
	/** Search Criterias End **/
	
	public ArticleAction(ThinkingService thinkingService) {
		super(thinkingService);
	}
	
	/**
	 * Select groups for user
	 * @return
	 */
	public String select() throws Exception {
		User user = (User) thinkingService.getObject(User.class, new Long(modelId));
		
		String[] ids = request.getParameterValues("items");
		if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                UserGroup group = (UserGroup) thinkingService.getObject(UserGroup.class, new Long(ids[i].split("_")[0]));
                if (ids[i].split("_")[1].equals("false")) {
                	group.addUser(user);
                } else {
                	group.removeUser(user);
                }
                thinkingService.updateObject(group);
            }
		}
		
		request.setAttribute("USER", user);
		
		search("userGroup");
		List groups = (List) request.getAttribute("list");
        Set selectedGroups = user.getGroups();
        for (Iterator iter = groups.iterator(); iter.hasNext();) {
			UserGroup group = (UserGroup) iter.next();
			if (selectedGroups.contains(group)) group.setSelected(true);
		}
		return SUCCESS;
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
