/**
 * Create date: Oct 18, 2005
 */
package org.commonfarm.search;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.commonfarm.Constant;
import org.commonfarm.util.BeanUtil;
import org.commonfarm.util.ResourceUtil;
import org.commonfarm.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SearchUtil {
	
	/**
	 * 
	 * @param form
	 * @return query criterias
	 */
	public static Map processCriterias(Object form) {
		Map criterias = new HashMap();
	
		try {
			Map map = BeanUtil.describe(form);
			for (Iterator it = map.keySet().iterator(); it.hasNext();) {
				String key = it.next().toString();
				if (key.startsWith(SearchConstant.FORM_PREFIX)) {
					String newString = key;
					if (key.indexOf("$") != -1) {
						newString = StringUtil.replace(key, "$", ".");
					}
					String value = (String) map.get(key);				
					criterias.put(newString.substring(2), value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return criterias;
	}

	public static Map convertSort(String sort) {
		Map sortCriterias = new HashMap();
		
		if (!StringUtil.notEmpty(sort)) return sortCriterias;
		StringTokenizer st = new StringTokenizer(sort, ",");
		Set tmpSet = new HashSet();
		while(st.hasMoreTokens()) {
			tmpSet.add(st.nextToken().trim());
		}
		for (Iterator iter = tmpSet.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			if (element.indexOf(":") != -1) {
				String[] criterias = StringUtil.getStringArray(element, ":");
				sortCriterias.put(criterias[0].trim(), criterias[1].trim());
			} else {
				sortCriterias.put(element.trim(), "asc");
			}
		}
		return sortCriterias;
	}
	public static Set convertGroup(String group) {
		Set set = new HashSet();
		
		if (!StringUtil.notEmpty(group)) return set;
		
		StringTokenizer st = new StringTokenizer(group, ",");
		while(st.hasMoreTokens()) {
			set.add(st.nextToken().trim());
		}
		return set;
	}
	/**
	 * Get search name by HttpServletRequest
	 * @param request
	 * @return
	 */
	public static String getSearchName(HttpServletRequest request) {
		Object searchName = request.getAttribute(SearchConstant.SEARCH_NAME);
		if (searchName == null) return "";
		return (String) searchName;
	}
	/**
	 * Get search configuration information
	 * @param request
	 * @return
	 */
	public static Map getSearchInfo(HttpServletRequest request) {
		Object searchInfo = request.getSession().getServletContext().getAttribute(Constant.CONFIG_SEARCH);
		if (searchInfo == null) return null;
		return (Map) searchInfo;
	}
	/**
	 * Get search model with session. remember search criterias of user
	 * @param request
	 * @return
	 */
	public static Object getSearchModel(HttpServletRequest request) {
		return request.getSession().getAttribute(SearchConstant.SEARCH_MODEL);
	}
	
	
	////////////////////////////////Common Search////////////////////////////////////////////
	/**
	 * Assemble criterias
	 */
	public static Map getCriterias(String[] keys, String[] values) {
		Map criterias = new HashMap();
		
		for (int i = 0; i < keys.length; i++) {
			criterias.put(keys[i], values[i]);
		}
		return criterias;
	}
	/**
	 * get search information by xml config file
	 * @param context
	 * @return
	 */
	public static Map getSearchInfo() throws IOException, FileNotFoundException, DocumentException{
		Map searchMap = new HashMap();
		
		InputStream inputStream = ResourceUtil.getResourceAsStream("classpath:search.xml");
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Iterator rootIt = document.getRootElement().elementIterator();
        while(rootIt.hasNext()) {
            Search search = new Search();
            Element searchElement = (Element) rootIt.next();
            String name = searchElement.attributeValue("name");
            String clazz = searchElement.attributeValue("class");
            String url = searchElement.attributeValue("url");
            String countColumn = searchElement.attributeValue("countColumn");
            String order = searchElement.attributeValue("order");
            String group = searchElement.attributeValue("group");
            search.setName(name);
            search.setClazz(clazz);
            search.setUrl(url);
            search.setCountColumn(countColumn);
            search.setSort(order);
            search.setGroup(group);
            Iterator searchIt = searchElement.elementIterator();
            while(searchIt.hasNext()) {
                SearchItem item = new SearchItem();
                Element itemElement = (Element) searchIt.next();
                item.setName(itemElement.attributeValue("name"));
                item.setType(itemElement.attributeValue("type"));
                item.setMode(itemElement.attributeValue("mode"));
                item.setTitle(itemElement.attributeValue("title"));
                item.setAssociation(itemElement.attributeValue("association"));
                search.addItem(item);
            }
            searchMap.put(name, search);
        }
        if (inputStream != null) {
	        inputStream.close();
	        inputStream = null;
        }
        return searchMap;
	}
	public static void main(String[] args) {
		try {
			SearchUtil.getSearchInfo();
		} catch(Exception ex) {
			System.out.print(ex);
		}
	}
}
