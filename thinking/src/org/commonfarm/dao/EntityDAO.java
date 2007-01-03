package org.commonfarm.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 针对单个Entity对象的操作定�?.不依赖于具体ORM实现方案.
 *
 * @author calvin
 */
public interface EntityDAO<T> {

	T get(Serializable id);

	List<T> getAll();

	void save(Object o);

	void remove(Object o);

	void removeById(Serializable id);

	/**
	 * 获取Entity对象的主键名.
	 */
	String getIdName(Class clazz);
}
