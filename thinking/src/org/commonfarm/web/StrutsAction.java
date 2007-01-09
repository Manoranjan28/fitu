package org.commonfarm.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

public class StrutsAction extends ActionSupport {
	private static final Log logger = LogFactory.getLog(StrutsAction.class);
	
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
	public String list() throws Exception {
		if (logger.isDebugEnabled()) {
        	logger.debug("items found");
        }
		return LIST;
	}
	
	/**
	 * Edit Data
	 * @return
	 */
	public String edit() throws Exception {
		return EDIT;
	}
	
	/**
	 * View Detail Data
	 * @return
	 */
	public String view() throws Exception {
		return VIEW;
	}
	
	/**
	 * Create Operation
	 * @return
	 */
	public String create() throws Exception {
		return CREATE;
	}
	
	/**
	 * Save Operation
	 * @return
	 */
	public String save() throws Exception {
		return DELETE;
	}
	
	/**
	 * Update Operation
	 * @return
	 */
	public String update() throws Exception {
		return DELETE;
	}
	
	/**
	 * Delete Operation
	 * @return
	 */
	public String delete() throws Exception {
		return DELETE;
	}
}
