package org.commonfarm.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 *
 * @author calvin
 */
public interface EntityDAO<T> {

	T getObject(Serializable id);

	List<T> getObjects();

	void save(Object o);

	void remove(Object o);

	void remove(Serializable id);

	/**
	 * 
	 */
	String getIdName(Class clazz);
}
