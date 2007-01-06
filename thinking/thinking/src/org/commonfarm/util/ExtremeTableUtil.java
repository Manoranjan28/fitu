package org.commonfarm.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.commonfarm.Constant;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.limit.Limit;
import org.extremecomponents.table.limit.LimitFactory;
import org.extremecomponents.table.limit.Sort;
import org.extremecomponents.table.limit.TableLimit;
import org.extremecomponents.table.limit.TableLimitFactory;

/**
 * Help ExtremeTable to get pagination information
 *
 * @author calvin&David
 */
public class ExtremeTableUtil {
	public static Limit getLimit(HttpServletRequest request) {
        return getLimit(request, Constant.DEFAULT_PAGE_SIZE);
    }

    /**
     * Construct Limit instance by request.
     */
    public static Limit getLimit(HttpServletRequest request, int defautPageSize) {
        Context context = new HttpServletRequestContext(request);
        LimitFactory limitFactory = new TableLimitFactory(context);
        TableLimit limit = new TableLimit(limitFactory);
        limit.setRowAttributes(1000000000, defautPageSize);
        return limit;
    }

    /**
     * Get sort information
     */
    public static Map getSort(Limit limit) {
        Map sortMap = new HashMap();
        if (limit != null) {
            Sort sort = limit.getSort();
            if (sort != null && sort.isSorted()) {
                sortMap.put(sort.getProperty(), sort.getSortOrder());
            }
        }
        return sortMap;
    }
    /**
     * Get sort information
     */
    public static Map getSort(HttpServletRequest request) {
    	Limit limit = getLimit(request);
        Map sortMap = new HashMap();
        if (limit != null) {
            Sort sort = limit.getSort();
            if (sort != null && sort.isSorted()) {
                sortMap.put(sort.getProperty(), sort.getSortOrder());
            }
        }
        return sortMap;
    }
    /**
     * Get current rows
     */
    public static int getCurrentRows(HttpServletRequest request) {
    	Limit limit = getLimit(request);
        return limit.getCurrentRowsDisplayed();
    }
    /**
     * Get page number
     */
    public static int getPage(HttpServletRequest request) {
    	Limit limit = getLimit(request);
        return limit.getPage();
    }
}
