/*
 * Created on 2005-11-2 By CommonFarm.org
 */
package org.commonfarm.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.commonfarm.util.DateUtil;
import org.commonfarm.util.BeanUtil;
import org.commonfarm.util.StringUtil;

/**
 * 
 *
 * @author Junhao Yang
 */
public class CommonSearch extends HibernateDaoSupport {
    //public List getCurrentPageList();
    //public String getPaginationTemplate();
    //public List getCurrentPageList(Map criterias);
    private static Log logger = LogFactory.getLog(CommonSearch.class);
    private String className;
    private Map criterias;
    private Map sortCriterias = new HashMap();
    private Set groupCriterias;
    private int rowsPerPage;
    private boolean reBetween = false;
    
    private Search search;
    
    public CommonSearch() {}
    
    public CommonSearch(String className, Map criterias) {
        this.className = className;
        this.criterias = criterias;
    }
    /**
     * @return total rows
     */
    public int getTotalRows() {
        Session session = this.getSession();
        try {
            Class domainClass = Class.forName(className);  
            Criteria criteria = session.createCriteria(domainClass);
            if (search.getCountColumn() == null) {
            	criteria.setProjection(Projections.count("id"));
            } else {
            	criteria.setProjection(Projections.count(search.getCountColumn()));
            }
            process(criteria);
            Integer count = (Integer)criteria.uniqueResult();
            if (count == null) count = new Integer(0);
            return count.intValue();
        } catch (ClassNotFoundException cnfe) {
            if (logger.isInfoEnabled()) {
            	logger.info(cnfe);
            }
        } catch (HibernateException he) {
            if (logger.isInfoEnabled()) {
            	logger.info(he);
            }
        }
        return 0;
    }
    /**
     * @param currentPage Current page number
     * @return
     */
    public List getCurrentPageList(int currentPage) {
    	if (logger.isInfoEnabled()) {
	    	logger.info("Page Num:" + new Integer(currentPage));
		}
        Session session = this.getSession();
        try {
            Class domainClass = Class.forName(className);  
            Criteria criteria = session.createCriteria(domainClass);
            criteria.setFirstResult((currentPage - 1) * rowsPerPage); 
            criteria.setMaxResults(rowsPerPage);
            process(criteria);
            addOrder(criteria);
            return criteria.list();
        } catch (ClassNotFoundException cnfe) {
            if (logger.isInfoEnabled()) {
            	logger.info(cnfe);
            }
        } catch (HibernateException he) {
            if (logger.isInfoEnabled()) {
            	logger.info(he);
            }
        }
		return null;
    }
    /**
     * @param criterias Criteria
     */
    private void process(Criteria criteria) { 
        //create assosiation query -- criteria.createCriteria("orderItem");
    	Map associationCriterias = new HashMap();
        for(Iterator it = criterias.keySet().iterator(); it.hasNext();) {
            String key = it.next().toString();            
            Object value = criterias.get(key);
            if ("".equals(value)) value = null;
            if (!BeanUtil.isNull(value) || !StringUtils.isEmpty((String) value)) {
                if (key.equals(SearchConstant.ID)) {
                    criteria.add(Restrictions.eq(key, value));
                } else {
	                String association = search.getAssociation(key);
	                if (key.indexOf(".") != -1) {
	                	association = key.substring(0, key.indexOf("."));
	                }
	                if (association != null) {
	                	Criteria associationCriteria = null;
	                	//if (association.indexOf(".") != -1) {//Is it necessary to carry on so judges!
	            		if (associationCriterias.containsKey(association)) {
	            			associationCriteria = (Criteria) associationCriterias.get(association);
	            		} else {
	            			associationCriteria = criteria.createCriteria(association);
	            			associationCriterias.put(association, associationCriteria);
	            		}
	                	process(key, associationCriteria);
	                } else {
	                	process(key, criteria);  
	                }
                }
            }
        }
    }
    /**
     * Add order criteria
     * @param criteria Criteria
     */
    private void addOrder(Criteria criteria) {
        for (Iterator iter = sortCriterias.keySet().iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			String orderMode = (String) sortCriterias.get(element);
			if (orderMode.equals("asc")) {
				criteria.addOrder(Order.asc(element));
			} else {
				criteria.addOrder(Order.desc(element));
			}
		}
    }
    private void process(String key, Criteria criteria) {
    	Object value = criterias.get(key);
    	///Between mode
    	Object value1 = null;
    	Object value2 = null;
    	if (StringUtils.isNumeric(StringUtils.right(key, 1))) {
    	    String num1 = StringUtils.right(key, 1);
    	    key = StringUtil.trim(key, StringUtils.right(key, 1));
    	    if (reBetween) {
    	        reBetween = false;
    	        return;
    	    }
    	    reBetween = true;
    	    String num2 = "2";
    	    if (num1.equals("2")) {
    	        num2 = "1";
    	        value1 = criterias.get(key + num2);
    	        value2 = criterias.get(key + num1);
    	    } else {
    	        value1 = criterias.get(key + num1);
    	        value2 = criterias.get(key + num2);
    	    }    
    	}
    	
    	String mode = search.getMode(key);
        String type = search.getType(key);
    	if (type == null || type.equals(SearchConstant.SEARCH_TYPE_STRING)) {
            value = (String) value;
        } else if (type.equals(SearchConstant.SEARCH_TYPE_INTEGER)) {
            value = new Integer(value.toString());
        } else if (type.equals(SearchConstant.SEARCH_TYPE_LONG)) {
            value = new Long(value.toString());
        } else if (type.equals(SearchConstant.SEARCH_TYPE_DATE)) {
            value = DateUtil.convert(value.toString());
        }
    	String newKey = key;
    	if (key.indexOf("_") != -1) {
    		newKey = StringUtils.split(key, "_")[1];
    	}
    	if (newKey.indexOf("$") != -1) {
    	    newKey = StringUtils.replace(newKey, "$", ".");
    	}
    	if (newKey.indexOf(".") != -1) {
    		newKey = newKey.substring(newKey.indexOf(".") + 1);
    	}
        if (mode == null || mode.equals(SearchConstant.SEARCH_MODE_LIKE)) {
            criteria.add(Restrictions.like(newKey, value));
        } else if (mode.equals(SearchConstant.SEARCH_MODE_GT)) {
            criteria.add(Restrictions.gt(newKey, value));
        } else if (mode.equals(SearchConstant.SEARCH_MODE_EQ)) {
            criteria.add(Restrictions.eq(newKey, value));
        } else if (mode.equals(SearchConstant.SEARCH_MODE_BETWEEN)) {
            if (type.equals(SearchConstant.SEARCH_TYPE_DATE)) {
                value1 = DateUtil.convert(value1.toString());
                value2 = DateUtil.convert(value2.toString());
            }
            criteria.add(Restrictions.between(newKey, value1, value2));
        }
    }
    /**
     * @return Returns the criterias.
     */
    public Map getCriterias() {
        return criterias;
    }
    /**
     * @param criterias The criterias to set.
     */
    public void setCriterias(Map criterias) {
        this.criterias = criterias;
    }
    /**
     * @return Returns the className.
     */
    public String getClassName() {
        return className;
    }
    /**
     * @param className The className to set.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return Returns the rowsPerPage.
     */
    public int getRowsPerPage() {
        return rowsPerPage;
    }
    /**
     * @param rowsPerPage The rowsPerPage to set.
     */
    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    /**
     * @return Returns the search.
     */
    public Search getSearch() {
        return search;
    }
    /**
     * @param search The search to set.
     */
    public void setSearch(Search search) {
        this.search = search;
    }

	/**
	 * @return Returns the sortCriterias.
	 */
	public Map getSortCriterias() {
		return sortCriterias;
	}

	/**
	 * @param sortCriterias The sortCriterias to set.
	 */
	public void setSortCriterias(Map sortCriterias) {
		this.sortCriterias = sortCriterias;
	}

	/**
	 * @return Returns the groupCriterias.
	 */
	public Set getGroupCriterias() {
		return groupCriterias;
	}

	/**
	 * @param groupCriterias The groupCriterias to set.
	 */
	public void setGroupCriterias(Set groupCriterias) {
		this.groupCriterias = groupCriterias;
	}

	public void addSortCriterias(Map sorts) {
		if (sorts != null) {
			for (Iterator it = sorts.keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				sortCriterias.put(key, sorts.get(key));
			}
		}
	}
}
