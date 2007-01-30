/*
 * Created on 2005-10-14
 */
package org.commonfarm.search;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.util.BeanUtil;
import org.commonfarm.util.ExtremeTableUtil;
import org.commonfarm.util.SpringUtil;


/**
 * struts
 *
 * @author Junhao Yang
 */
public class SearchProcessor {
    protected final static Log logger = LogFactory.getLog(SearchProcessor.class);
    
    public static void processExtreme(HttpServletRequest request, Search search, Map criterias) {
        /* extreme component*/
        Map sortCriterias = ExtremeTableUtil.getSort(request);
        CommonSearch commonSearch = (CommonSearch) SpringUtil.getBean(request, "commonSearch");
	    commonSearch.setClassName(search.getClazz());
	    commonSearch.setCriterias(criterias);
	    commonSearch.setSearch(search);
	    commonSearch.setRowsPerPage(ExtremeTableUtil.getCurrentRows(request));
	    commonSearch.setSortCriterias(SearchUtil.convertSort(search.getSort()));
	    commonSearch.addSortCriterias(sortCriterias);
	    commonSearch.addSortCriterias((Map) request.getAttribute(SearchConstant.SORT_CRITERIAS));
	    
	    int totalRows = commonSearch.getTotalRows();
	    if (logger.isInfoEnabled()) {
	    	logger.info("Total Rows:" + new Integer(totalRows));
		}

        List currentPageList = commonSearch.getCurrentPageList(ExtremeTableUtil.getPage(request));
		
		request.setAttribute("list", currentPageList);
		request.setAttribute("totalRows", new Integer(totalRows));
    }

	public static void processExtreme(HttpServletRequest request, String searchName, Object criteriaObject) {
		String page = request.getParameter("page");
		//String searchName = SearchUtil.getSearchName(request);
		Map searchInfo = SearchUtil.getSearchInfo(request);
		
		Map criterias = null;
		if (page == null) {
			String method = request.getParameter("method");
			if (method != null && method.equals("delete")) {
				BeanUtil.copyProperties(criteriaObject, SearchUtil.getSearchModel(request));
			    criterias = SearchUtil.processCriterias(criteriaObject);
			}
		    criterias = SearchUtil.processCriterias(criteriaObject);
		    request.getSession().setAttribute(SearchConstant.CRITERIA_OBJECT, criteriaObject);
		} else {
		    BeanUtil.copyProperties(criteriaObject, request.getSession().getAttribute(SearchConstant.CRITERIA_OBJECT));
		    criterias = SearchUtil.processCriterias(criteriaObject);
		}
		if (request.getAttribute(SearchConstant.COMPOSITE_ID) != null) {
			criterias.put(SearchConstant.ID, request.getAttribute(SearchConstant.COMPOSITE_ID));
		}
		Search search = (Search) searchInfo.get(searchName);
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
		processExtreme(request, search, criterias);
	}
}
