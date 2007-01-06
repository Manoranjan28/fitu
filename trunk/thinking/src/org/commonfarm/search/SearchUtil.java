/**
 * Create date: Oct 18, 2005
 */
package org.commonfarm.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.commonfarm.util.BeanUtil;
import org.commonfarm.util.StringUtil;

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
				if (key.startsWith(SearchConstants.FORM_PREFIX)) {
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
	public static void main(String[] args) {
		SearchUtil.convertSort("hello:asc, test, light");
	}
}
