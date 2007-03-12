/**
 * 
 */
package org.commonfarm.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.search.SearchProcessor;
import org.commonfarm.service.BusinessException;
import org.commonfarm.service.ThinkingService;
import org.commonfarm.util.BeanUtil;

import com.opensymphony.webwork.interceptor.ServletRequestAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author david
 *
 */
public class WebWorkAction extends ActionSupport implements ServletRequestAware, ModelDriven  {
	private static final Log logger = LogFactory.getLog(StrutsAction.class);
	
	/** Request Common Parameters Start */
	protected Long modelId;
	/** Request Common Parameters End */
	
	/** Available items 
	protected Collection items;*/
	/** To delete items
	protected String[] delItems;*/
	
	/** Domain Model **/
	protected Object model;
	
	protected HttpServletRequest request;
	
	/** Business Logic Facade Object **/
	protected ThinkingService thinkingService;
	
	/** Search Name **/
	private String searchName;
	
	public WebWorkAction() {}
	public WebWorkAction(ThinkingService thinkingService) {
		this.thinkingService = thinkingService;
	}
	
	protected void saveMessage(String msg) {
        List messages = (List) getSession().getAttribute("messages");
        if (messages == null) {
            messages = new ArrayList();
        }
        messages.add(msg);
        getSession().setAttribute("messages", messages);
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
		if (ids != null && ids.length >= 1) modelId = new Long(ids[0]);
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
	public String saveOrUpdate() throws Exception {
		Object id = BeanUtil.getPropertyValue(model, "id");
		try {
			if (id == null) {
				if (processSave(model)) {
					BeanUtil.setProperty(model, "id", null);
					saveMessage(getText("saved"));
				}
			} else {
				if (processUpdate(model)) {
					saveMessage(getText("updated"));
				}
			}
		} catch(BusinessException be) {
			logger.info(be.getMessage());
			addActionError(be.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * Process Save Operation
	 * @return
	 */
	protected boolean processSave(Object model) throws BusinessException {
		try {
			thinkingService.saveObject(model);
		} catch (Exception e) {
			addActionError("Save failure! " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Process Update Operation
	 * @return
	 */
	protected boolean processUpdate(Object model) throws BusinessException {
		try {
			thinkingService.updateObject(model);
		} catch (Exception e) {
			addActionError("Update failure! " + e.getMessage());
			return false;
		}
		return true;
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
				this.remove(ids[i]);//thinkingService.removeObject(model.getClass(), new Long(ids[i]));
				delCount++;
			} catch(BusinessException be) {
				addActionError(be.getMessage());
				logger.info("Delete " + delCount + " records!");
				return search(searchName);
			}
		}
		saveMessage("Delete " + delCount + " records!");
		logger.info("Delete " + delCount + " records!");
		return search(searchName);
	}
	/**
	 * When to happen cascade delete problem in many-to-many or one-to-many, we need re-implement this method
	 * We need cut relation between A(Object) and B(Object), then remove object
	 * Example: thinkingService.removeObject(model, new Long(id), new String[] {"groups", "users"});
	 * @param id
	 * @throws BusinessException
	 */
	public void remove(String id) throws BusinessException {
		thinkingService.removeObject(model.getClass(), new Long(id));
	}
	/**
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpSession getSession() {
		if (request == null) return null;
		return request.getSession();
	}
	/**
	 * Domain Model
	 */
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