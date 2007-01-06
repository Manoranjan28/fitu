package org.commonfarm.web;

import org.commonfarm.util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;

public class StrutsAction extends ActionSupport {
	protected static final String LIST = "list";
	protected static final String EDIT = "edit";
	protected static final String VIEW = "view";
	protected static final String CREATE = "create";
	protected static final String DELETE = "delete";
	
	protected String action = "";
	
	public String execute() throws Exception {
		String forward = SUCCESS;
        if (StringUtil.isEmpty(action)) {
        	forward = list();
        } else if (action.equals(EDIT)) {
        	forward = edit();
        } else if (action.equals(VIEW)) {
        	forward = view();
        } else if (action.equals(CREATE)) {
        	forward = create();
        } else if (action.equals(DELETE)) {
        	forward = delete();
        }
        return forward;
    }
	
	/**
	 * List Data
	 * @return
	 */
	public String list() {
		return LIST;
	}
	
	/**
	 * Edit Data
	 * @return
	 */
	public String edit() {
		return EDIT;
	}
	
	/**
	 * View Detail Data
	 * @return
	 */
	public String view() {
		return VIEW;
	}
	
	/**
	 * Create Operation
	 * @return
	 */
	public String create() {
		return CREATE;
	}
	
	/**
	 * Delete Operation
	 * @return
	 */
	public String delete() {
		return DELETE;
	}
}
