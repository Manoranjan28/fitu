package org.commonfarm.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springside.core.dao.support.Page;
import org.springside.core.utils.GenericsUtils;

/**
 * 负责为单个Entity 提供CRUD操作的IBatis DAO基类.
 * <p/>
 * 子类只要在类定义时指定所管理Entity的Class, 即拥有对单个Entity对象的CRUD操作. 
 * <pre>
 * public class UserManagerIbatis extends IBatisEntityDao&lt;User&gt; {
 * }
 * </pre>
 *
 * @author suwei
 * @see IBatisGenericDAO
 */
public class IBatisDAO<T> extends IBatisGenericDAO implements EntityDAO<T> {

	/**
	 * DAO�?管理的Entity类型.
	 */
	protected Class<T> entityClass;

	protected String primaryKeyName;

	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 */
	@SuppressWarnings("unchecked")
	public IBatisDAO() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 根据属�?�名和属性�?�查询对�?.
	 *
	 * @return 符合条件的对象列�?
	 */
	public List<T> findBy(String name, Object value) {
		return findBy(getEntityClass(), name, value);
	}

	/**
	 * 根据属�?�名和属性�?�以Like AnyWhere方式查询对象.
	 */
	public List<T> findByLike(String name, String value) {
		return findByLike(getEntityClass(), name, value);
	}

	/**
	 * 根据属�?�名和属性�?�查询单个对�?.
	 *
	 * @return 符合条件的唯�?对象
	 */
	public T findUniqueBy(String name, Object value) {
		return findUniqueBy(getEntityClass(), name, value);
	}

	/**
	 * 根据ID获取对象.
	 */
	public T get(Serializable id) {
		return get(getEntityClass(), id);
	}

	/**
	 * 获取全部对象.
	 */
	public List<T> getAll() {
		return getAll(getEntityClass());
	}

	/**
	 * 取得entityClass.
	 * 
	 * JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果�??
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
	 * 分页查询.
	 */
	public Page pagedQuery(int pageNo, int pageSize) {
		return pagedQuery(getEntityClass(), null, pageNo, pageSize);
	}

	/**
	 * 分页查询.
	 */
	public Page pagedQuery(Object parameterObject, int pageNo, int pageSize) {
		return pagedQuery(getEntityClass(), parameterObject, pageNo, pageSize);
	}

	/**
	 * 根据ID移除对象.
	 */
	public void removeById(Serializable id) {
		removeById(getEntityClass(), id);
	}

	/**
	 * 保存对象. 为了实现IEntityDao 我在内部使用了insert和upate 2个方�?.
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
