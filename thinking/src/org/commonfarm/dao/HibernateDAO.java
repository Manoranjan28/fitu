package org.commonfarm.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springside.core.utils.GenericsUtils;

/**
 * 负责为单个Entity对象提供CRUD操作的Hibernate DAO基类.
 * <p/>
 * 子类只要在类定义时指定所管理Entity的Class, 即拥有对单个Entity对象的CRUD操作.
 * <pre>
 * public class UserManager extends HibernateEntityDao<User> {
 * }
 * </pre>
 *
 * @author calvin
 * @see HibernateGenericDAO
 */
@SuppressWarnings("unchecked")
public class HibernateDAO<T> extends HibernateGenericDAO implements EntityDAO<T> {

	protected Class<T> entityClass;// DAO�?管理的Entity类型.

	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 */
	public HibernateDAO() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 取得entityClass.JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果�??
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 根据ID获取对象.
	 *
	 * @see HibernateGenericDAO#getId(Class,Object)
	 */
	public T get(Serializable id) {
		return get(getEntityClass(), id);
	}

	/**
	 * 获取全部对象
	 *
	 * @see HibernateGenericDAO#getAll(Class)
	 */
	public List<T> getAll() {
		return getAll(getEntityClass());
	}

	/**
	 * 获取全部对象,带排序参�?.
	 *
	 * @see HibernateGenericDAO#getAll(Class,String,boolean)
	 */
	public List<T> getAll(String orderBy, boolean isAsc) {
		return getAll(getEntityClass(), orderBy, isAsc);
	}

	/**
	 * 根据ID移除对象.
	 *
	 * @see HibernateGenericDAO#removeById(Class,Serializable)
	 */
	public void removeById(Serializable id) {
		removeById(getEntityClass(), id);
	}

	/**
	 * 取得Entity的Criteria.
	 *
	 * @see HibernateGenericDAO#createCriteria(Class,Criterion[])
	 */
	public Criteria createCriteria(Criterion... criterions) {
		return createCriteria(getEntityClass(), criterions);
	}

	/**
	 * 取得Entity的Criteria,带排序参�?.
	 *
	 * @see HibernateGenericDAO#createCriteria(Class,String,boolean,Criterion[])
	 */
	public Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) {
		return createCriteria(getEntityClass(), orderBy, isAsc, criterions);
	}

	/**
	 * 根据属�?�名和属性�?�查询对�?.
	 *
	 * @return 符合条件的对象列�?
	 * @see HibernateGenericDAO#findBy(Class,String,Object)
	 */
	public List<T> findBy(String propertyName, Object value) {
		return findBy(getEntityClass(), propertyName, value);
	}

	/**
	 * 根据属�?�名和属性�?�查询对�?,带排序参�?.
	 *
	 * @return 符合条件的对象列�?
	 * @see HibernateGenericDAO#findBy(Class,String,Object,String,boolean)
	 */
	public List<T> findBy(String propertyName, Object value, String orderBy, boolean isAsc) {
		return findBy(getEntityClass(), propertyName, value, orderBy, isAsc);
	}

	/**
	 * 根据属�?�名和属性�?�查询单个对�?.
	 *
	 * @return 符合条件的唯�?对象 or null
	 * @see HibernateGenericDAO#findUniqueBy(Class,String,Object)
	 */
	public T findUniqueBy(String propertyName, Object value) {
		return findUniqueBy(getEntityClass(), propertyName, value);
	}

	/**
	 * 判断对象某些属�?�的值在数据库中唯一.
	 *
	 * @param uniquePropertyNames 在POJO里不能重复的属�?�列�?,以�?�号分割 �?"name,loginid,password"
	 * @see HibernateGenericDAO#isUnique(Class,Object,String)
	 */
	public boolean isUnique(Object entity, String uniquePropertyNames) {
		return isUnique(getEntityClass(), entity, uniquePropertyNames);
	}
}
