package org.commonfarm.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.commonfarm.search.SearchProcessor;
import org.commonfarm.service.ThinkingService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class StrutsAction extends ActionSupport implements ServletRequestAware, ModelDriven  {
	private static final Log logger = LogFactory.getLog(StrutsAction.class);
	
	/** Request Common Parameters Start */
	private Long modelId;
	/** Request Common Parameters End */
	
	/** Available items */
	protected Collection items;
	/** To delete items*/
	protected String[] delItems;
	
	protected Object model;
	protected HttpServletRequest request;
	
	protected ThinkingService thinkingService;
	public StrutsAction() {}
	public StrutsAction(ThinkingService thinkingService) {
		this.thinkingService = thinkingService;
	}
	
	public String search(String searchName) throws Exception {
		logger.info("Execute search: " + searchName);
		SearchProcessor.processExtreme(this.request, searchName, this);
		
		////////////============================================/////////////////////	
        return SUCCESS;
    }
	
	/**
	 * Edit Data
	 * @return
	 */
	public String edit() throws Exception {
		model = thinkingService.getObject(model.getClass(), modelId);
		return SUCCESS;
	}
	
	/**
	 * View Detail Data
	 * @return
	 */
	public String view() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * Create Operation
	 * @return
	 */
	public String create() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * Save Operation
	 * @return
	 */
	public String save() throws Exception {
		thinkingService.saveObject(model);
		return SUCCESS;
	}
	
	/**
	 * Update Operation
	 * @return
	 */
	public String update() throws Exception {
		thinkingService.updateObject(model);
		return SUCCESS;
	}
	
	/**
	 * Delete Operation
	 * @return
	 */
	public String delete() throws Exception {
		return SUCCESS;
	}

	/**
	 * @return the delItems
	 */
	public String[] getDelItems() {
		return delItems;
	}

	/**
	 * @param delItems the delItems to set
	 */
	public void setDelItems(String[] delItems) {
		this.delItems = delItems;
	}

	/**
	 * @return the items
	 */
	public Collection getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(Collection items) {
		this.items = items;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public Object getModel() {
		return model;     
	}
	/**
	 * @return the modelId
	 */
	public Long getModelId() {
		return modelId;
	}
	/**
	 * @param modelId the modelId to set
	 */
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
}
