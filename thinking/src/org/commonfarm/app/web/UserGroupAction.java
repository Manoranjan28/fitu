package org.commonfarm.app.web;

import java.util.ArrayList;
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

public class UserGroupAction extends StrutsAction implements Preparable {
	//private static final Log logger = LogFactory.getLog(UserAction.class);
	private long actionId;
	
	/** Request Parameters Start */
	/** Request Parameters End */
	
	/** Search Criterias Start **/
	private String s_name;
	//select user criterias
	private String s_userId;
	private String s_secondName;
	/** Search Criterias End **/
	
	public UserGroupAction(ThinkingService thinkingService) {
		super(thinkingService);
	}
	/**
	 * Select users for user group
	 * @return
	 */
	public String select() throws Exception {
		UserGroup group = (UserGroup) thinkingService.getObject(UserGroup.class, new Long(modelId));
		
		String[] ids = request.getParameterValues("items");
		if (ids != null) {
			List selectUsers = new ArrayList();
			List cancelUsers = new ArrayList();
            for (int i = 0; i < ids.length; i++) {
                User user = (User) thinkingService.getObject(User.class, new Long(ids[i].split("_")[0]));
                if (ids[i].split("_")[1].equals("false")) {
                	selectUsers.add(user);
                } else {
                	cancelUsers.add(user);
                }
            }
            thinkingService.selectObjects(group.getUsers(), selectUsers, cancelUsers);
		}
		
		request.setAttribute("GROUP", group);
		
		search("user");
		List users = (List) request.getAttribute("list");
        Set selectedUsers = group.getUsers();
        for (Iterator iter = users.iterator(); iter.hasNext();) {
			User user = (User) iter.next();
			if (selectedUsers.contains(user)) user.setSelected(true);
		}
		return SUCCESS;
	}
	
	/**
	 * Process Update Operation
	 * @return
	 */
	protected boolean processUpdate(Object model) throws BusinessException {
		UserGroup group = (UserGroup) model;
		UserGroup oldGroup = (UserGroup) thinkingService.getObject(UserGroup.class, group.getId());
		oldGroup.setName(group.getName());
		oldGroup.setDescn(group.getDescn());
		oldGroup.setRole(group.getRole());
		try {
			thinkingService.updateObject(oldGroup);
		} catch (Exception e) {
			addActionError("Update failure! " + e.getMessage());
			return false;
		}
		return true;
	}
	/**
	 * init: set search name and initialize model object
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		if (StringUtil.isEmpty(getSearchName())) setSearchName("userGroup");
		if(actionId == 0) {
            model = new UserGroup();
        } else {
            model = thinkingService.getObject(UserGroup.class, new Long(actionId));
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
}
