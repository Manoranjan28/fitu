package org.commonfarm.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.commonfarm.search.SearchProcessor;
import org.commonfarm.service.BusinessException;
import org.commonfarm.service.ThinkingService;
import org.commonfarm.util.BeanUtil;

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
	
	/** Domain Model **/
	protected Object model;
	
	protected HttpServletRequest request;
	
	/** Business Logic Facade Object **/
	protected ThinkingService thinkingService;
	
	/** Page I18N message **/
	protected String message;
	protected boolean messageChar;//message characteristic
	
	/** Search Name **/
	private String searchName;
	
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
	 * List Data
	 * @return
	 */
	public String list() throws Exception {
		if (logger.isDebugEnabled()) {
        	logger.debug("items found");
        }
		
		return search(searchName);
	}
	
	/**
	 * Edit Data
	 * @return
	 */
	public String edit() throws Exception {
		String[] ids = request.getParameterValues("items");
		if (ids.length >= 1) modelId = new Long(ids[0]);
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
		Object id = BeanUtil.getPropertyValue(model, "id");
		try {
			thinkingService.saveObject(model);
			if (id == null) {
				BeanUtil.setProperty(model, "id", null);
				setMessage(getText("saved"));
			} else {
				setMessage(getText("updated"));
			}
		} catch(BusinessException be) {
			logger.info(be.getMessage());
			setMessage(be.getMessage(), false);
		}
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
	 * Remove Operation
	 * @return
	 */
	public String remove() throws Exception {
		String[] ids = request.getParameterValues("items");
		int count = ids.length;
		int delCount = 0;
		for (int i = 0; i < count; i++) {
			try {
				thinkingService.removeObject(model.getClass(), new Long(ids[i]));
				delCount++;
			} catch(BusinessException be) {
				setMessage(be.getMessage(), false);
				logger.info("Delete " + delCount + " records!");
				return search(searchName);
			}
		}
		logger.info("Delete " + delCount + " records!");
		return search(searchName);
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
	
	/**
	 * @return the messages
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Default message characteristic is "message"
	 * @param messages the messages to set
	 */
	public void setMessage(String message) {
		this.setMessage(message, true);
	}
	/**
	 * @param messages the messages to set
	 */
	public void setMessage(String message, boolean messageChar) {
		this.message = message;
		this.messageChar = messageChar;
	}
	/**
	 * @return the searchName
	 */
	public String getSearchName() {
		return searchName;
	}
	/**
	 * @param searchName the searchName to set
	 */
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
}
