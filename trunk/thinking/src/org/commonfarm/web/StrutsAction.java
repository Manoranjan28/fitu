package org.commonfarm.web;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.servlet.ServletRequestAware;
import org.commonfarm.search.Search;
import org.commonfarm.search.SearchConstant;
import org.commonfarm.search.SearchProcessor;
import org.commonfarm.search.SearchUtil;
import org.commonfarm.service.ThinkingService;
import org.commonfarm.util.BeanUtil;
import org.commonfarm.util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class StrutsAction extends ActionSupport 
		implements ModelDriven, ServletRequestAware {
	private static final Log logger = LogFactory.getLog(StrutsAction.class);
	
	protected static final String LIST = "list";
	protected static final String EDIT = "edit";
	protected static final String VIEW = "view";
	protected static final String CREATE = "create";
	protected static final String DELETE = "delete";
	
	/** Available items */
	protected Collection items;
	/** To delete items*/
	protected String[] delItems;
	
	protected Object model;
	protected HttpServletRequest request;
	
	protected ThinkingService service;
	
	public String execute() throws Exception {
		String page = request.getParameter("page");
		String searchName = SearchUtil.getSearchName(request);
		Map searchMap = SearchUtil.getSearchMap(request);
		
		if (StringUtil.isEmpty(searchName)) {
			if (logger.isInfoEnabled()) {
				logger.info("forward to error page");
			}
		}
		if (searchMap == null) {
		    String exception = "search map is null";
		    if (logger.isInfoEnabled()) {
				logger.info(exception);
			}
		}
		
		Map criterias = null;
		if (page == null) {
			String method = request.getParameter("method");
			if (method != null && method.equals("delete")) {
				BeanUtil.copyProperties(model, SearchUtil.getSearchModel(request));
			    criterias = SearchUtil.processCriterias(model);
			}
		    criterias = SearchUtil.processCriterias(model);
		    request.getSession().setAttribute(SearchConstant.SEARCH_MODEL, model);
		} else {
		    BeanUtil.copyProperties(model, request.getSession().getAttribute(SearchConstant.SEARCH_MODEL));
		    criterias = SearchUtil.processCriterias(model);
		}
		if (request.getAttribute(SearchConstant.COMPOSITE_ID) != null) {
			criterias.put(SearchConstant.ID, request.getAttribute(SearchConstant.COMPOSITE_ID));
		}
		Search search = (Search) searchMap.get(searchName);
		String fixedParams = (String) request.getAttribute(SearchConstant.FIXED_PARAMETERS);
		if (fixedParams != null) {
			search.setFixedParams(fixedParams);
		}
		
		Map fixedCriterias = (Map) request.getAttribute(SearchConstant.FIXED_CRITERIAS);
		if (fixedCriterias != null && !fixedCriterias.isEmpty()) {
			for (Iterator it = fixedCriterias.keySet().iterator(); it.hasNext();) {
				Object key = it.next();
				criterias.put(key, fixedCriterias.get(key));
			}
		}
		SearchProcessor.processExtreme(request, search, criterias);
		////////////============================================/////////////////////	
		String forward = (String) request.getAttribute(SearchConstant.FORWARD);
		if (forward == null) {
			forward = "list";
		}
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
		service.saveObject(model);
		return CREATE;
	}
	
	/**
	 * Save Operation
	 * @return
	 */
	public String save() throws Exception {
		service.saveObject(model);
		return DELETE;
	}
	
	/**
	 * Update Operation
	 * @return
	 */
	public String update() throws Exception {
		service.updateObject(model);
		return DELETE;
	}
	
	/**
	 * Delete Operation
	 * @return
	 */
	public String delete() throws Exception {
		return DELETE;
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
}
