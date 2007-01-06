/**
 * Create date: Oct 23, 2005
 */
package org.commonfarm.search;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author David Yang
 *
 */
public class Search {
	private String name;
	private String clazz;
	private String url;
	private String countColumn;
	private String fixedParams;
	private String sort;
	private String group;
	private Set items = new HashSet();
	
	/**
	 * @return Returns the clazz.
	 */
	public String getClazz() {
		return clazz;
	}
	/**
	 * @param clazz The clazz to set.
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMode(String conditionName) {
		for (Iterator it = items.iterator(); it.hasNext();) {
		    SearchItem item = (SearchItem) it.next();
		    if (item.getName().equals(conditionName)) {
		        return item.getMode();
		    }
		}
		return null;
	}
	public String getType(String conditionName) {
	    for (Iterator it = items.iterator(); it.hasNext();) {
		    SearchItem item = (SearchItem) it.next();
		    if (item.getName().equals(conditionName)) {
		        return item.getType();
		    }
		}
	    return null;
	}
	public String getAssociation(String conditionName) {
		for (Iterator it = items.iterator(); it.hasNext();) {
		    SearchItem item = (SearchItem) it.next();
		    if (item.getName().equals(conditionName)) {
		        return item.getAssociation();
		    }
		}
	    return null;
	}
	/**
     * @return Returns the items.
     */
    public Set getItems() {
        return items;
    }
    /**
     * @param items The items to set.
     */
    public void setItems(Set items) {
        this.items = items;
    }
    public void addItem(SearchItem item) {
        items.add(item);
    }

    /**
     * @return Returns the group.
     */
    public String getGroup() {
        return group;
    }
    /**
     * @param group The group to set.
     */
    public void setGroup(String group) {
        this.group = group;
    }
    /**
     * @return Returns the order.
     */
    public String getSort() {
        return sort;
    }
    /**
     * @param order The order to set.
     */
    public void setSort(String order) {
        this.sort = order;
    }
	/**
	 * @return Returns the fixedParams.
	 */
	public String getFixedParams() {
		return fixedParams;
	}
	/**
	 * @param fixedParams The fixedParams to set.
	 */
	public void setFixedParams(String fixedParams) {
		this.fixedParams = fixedParams;
	}
	/**
	 * @return Returns the countColumn.
	 */
	public String getCountColumn() {
		return countColumn;
	}
	/**
	 * @param countColumn The countColumn to set.
	 */
	public void setCountColumn(String countColumn) {
		this.countColumn = countColumn;
	}
}
