package org.commonfarm.dao;

import java.io.Serializable;
import java.util.List;

import org.commonfarm.util.GenericUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author David Yang
 * @see HibernateGenericDAO
 */
@SuppressWarnings("unchecked")
public class HibernateDAO<T> extends HibernateGenericDAO implements EntityDAO<T> {

	protected Class<T> entityClass;

	/**
	 * 
	 */
	public HibernateDAO() {
		entityClass = GenericUtil.getSuperClassGenricType(getClass());
	}

	/**
	 * 
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 
	 *
	 * @see HibernateGenericDAO#getId(Class,Object)
	 */
	public T getObject(Serializable id) {
		return getObject(getEntityClass(), id);
	}

	/**
	 * 
	 *
	 * @see HibernateGenericDAO#getAll(Class)
	 */
	public List<T> getObjects() {
		return getObjects(getEntityClass());
	}

	/**
	 * 
	 *
	 * @see HibernateGenericDAO#getAll(Class,String,boolean)
	 */
	public List<T> getObjects(String orderBy, boolean isAsc) {
		return getObjects(getEntityClass(), orderBy, isAsc);
	}

	/**
	 * 
	 *
	 * @see HibernateGenericDAO#removeById(Class,Serializable)
	 */
	public void remove(Serializable id) {
		removeById(getEntityClass(), id);
	}

	/**
	 * 
	 *
	 * @see HibernateGenericDAO#createCriteria(Class,Criterion[])
	 */
	public Criteria createCriteria(Criterion... criterions) {
		return createCriteria(getEntityClass(), criterions);
	}

	/**
	 * 
	 *
	 * @see HibernateGenericDAO#createCriteria(Class,String,boolean,Criterion[])
	 */
	public Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) {
		return createCriteria(getEntityClass(), orderBy, isAsc, criterions);
	}

	/**
	 * 
	 *
	 * @return 
	 * @see HibernateGenericDAO#findBy(Class,String,Object)
	 */
	public List<T> findBy(String propertyName, Object value) {
		return findBy(getEntityClass(), propertyName, value);
	}

	/**
	 * 
	 *
	 * @return 
	 * @see HibernateGenericDAO#findBy(Class,String,Object,String,boolean)
	 */
	public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
		return findBy(getEntityClass(), propertyName, value, orderBy, isAsc);
	}

	/**
	 * 
	 * @see HibernateGenericDAO#findUniqueBy(Class,String,Object)
	 */
	public T findUniqueBy(String propertyName, Object value) {
		return findUniqueBy(getEntityClass(), propertyName, value);
	}

	/**
	 *
	 * @see HibernateGenericDAO#isUnique(Class,Object,String)
	 */
	public boolean isUnique(Object entity, String uniquePropertyNames) {
		return isUnique(getEntityClass(), entity, uniquePropertyNames);
	}
}
