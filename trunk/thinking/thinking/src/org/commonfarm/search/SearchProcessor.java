/*
 * Created on 2005-10-14
 */
package org.commonfarm.search;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	    commonSearch.addSortCriterias((Map) request.getAttribute(SearchConstants.SORT_CRITERIAS));
	    
	    int totalRows = commonSearch.getTotalRows();
	    if (logger.isInfoEnabled()) {
	    	logger.info("Total Rows:" + new Integer(totalRows));
		}

        List currentPageList = commonSearch.getCurrentPageList(ExtremeTableUtil.getPage(request));
		
		request.setAttribute("list", currentPageList);
		request.setAttribute("totalRows", new Integer(totalRows));
    }
}
