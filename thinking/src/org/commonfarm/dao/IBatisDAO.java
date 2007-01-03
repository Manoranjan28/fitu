package org.commonfarm.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.commonfarm.dao.support.Page;
import org.commonfarm.util.GenericUtil;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * 
 * @author David Yang
 * @see IBatisGenericDAO
 */
public class IBatisDAO<T> extends IBatisGenericDAO implements EntityDAO<T> {

	/**
	 * 
	 */
	protected Class<T> entityClass;

	protected String primaryKeyName;

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public IBatisDAO() {
		entityClass = GenericUtil.getSuperClassGenricType(getClass());
	}

	/**
	 * 
	 */
	public List<T> findBy(String name, Object value) {
		return findBy(getEntityClass(), name, value);
	}

	/**
	 * 
	 */
	public List<T> findByLike(String name, String value) {
		return findByLike(getEntityClass(), name, value);
	}

	/**
	 * 
	 */
	public T findUniqueBy(String name, Object value) {
		return findUniqueBy(getEntityClass(), name, value);
	}

	/**
	 * 
	 */
	public T getObject(Serializable id) {
		return getObject(getEntityClass(), id);
	}

	/**
	 * 
	 */
	public List<T> getObjects() {
		return getObjects(getEntityClass());
	}

	/**
	 * 
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	public String getIdName(Class clazz) {
		return "id";
	}

	public String getPrimaryKeyName() {
		if (StringUtils.isEmpty(primaryKeyName))
			primaryKeyName = "id";
		return primaryKeyName;
	}

	protected Object getPrimaryKeyValue(Object o) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		return PropertyUtils.getProperty(entityClass.newInstance(), getPrimaryKeyName());
	}

	/**
	 * 
	 */
	public Page pagedQuery(int pageNo, int pageSize) {
		return pagedQuery(getEntityClass(), null, pageNo, pageSize);
	}

	/**
	 * 
	 */
	public Page pagedQuery(Object parameterObject, int pageNo, int pageSize) {
		return pagedQuery(getEntityClass(), parameterObject, pageNo, pageSize);
	}

	/**
	 * 
	 */
	public void remove(Serializable id) {
		remove(getEntityClass(), id);
	}

	/**
	 * 
	 */
	public void save(Object o) {
		Object primaryKey;
		try {
			primaryKey = getPrimaryKeyValue(o);
		} catch (Exception e) {
			throw new ObjectRetrievalFailureException(entityClass, e);
		}

		if (primaryKey == null)
			insert(o);
		else
			update(o);
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}
}
