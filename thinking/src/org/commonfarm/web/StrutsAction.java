package org.commonfarm.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;

public class StrutsAction extends ActionSupport {
	private static Log logger = LogFactory.getLog(StrutsAction.class);
	
	protected static final String LIST = "list";
	protected static final String EDIT = "edit";
	protected static final String VIEW = "view";
	protected static final String CREATE = "create";
	protected static final String DELETE = "delete";
	
	public String execute() throws Exception {
		String forward = SUCCESS;
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
