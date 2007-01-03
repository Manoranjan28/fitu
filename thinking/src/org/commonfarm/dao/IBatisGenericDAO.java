package org.commonfarm.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.commonfarm.dao.support.Page;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.util.Assert;

import com.ibatis.common.util.PaginatedList;

/**
 * 
 * @author David Yang
 * @see SqlMapClientDaoSupport
 */
@SuppressWarnings("unchecked")
public class IBatisGenericDAO extends SqlMapClientDaoSupport {

	public static final String POSTFIX_INSERT = ".insert";

	public static final String POSTFIX_UPDATE = ".update";

	public static final String POSTFIX_DELETE = ".delete";

	public static final String POSTFIX_DELETE_PRIAMARYKEY = ".deleteByPrimaryKey";

	public static final String POSTFIX_SELECT = ".select";

	public static final String POSTFIX_SELECTMAP = ".selectByMap";

	public static final String POSTFIX_SELECTSQL = ".selectBySql";

	public static final String POSTFIX_COUNT = ".count";

	/**
	 * 
	 */
	public <T> T getObject(Class<T> entityClass, Serializable id) {

		T o = (T) getSqlMapClientTemplate().queryForObject(entityClass.getName() + POSTFIX_SELECT, id);
		if (o == null)
			throw new ObjectRetrievalFailureException(entityClass, id);
		return o;
	}

	/**
	 * 
	 */
	public <T> List<T> getObjects(Class<T> entityClass) {
		return getSqlMapClientTemplate().queryForList(entityClass.getName() + POSTFIX_SELECT, null);
	}

	/**
	 * 
	 */
	public void insert(Object o) {
		getSqlMapClientTemplate().insert(o.getClass().getName() + POSTFIX_INSERT, o);
	}

	/**
	 * 
	 */
	public void update(Object o) {
		getSqlMapClientTemplate().update(o.getClass().getName() + POSTFIX_UPDATE, o);
	}

	/**
	 * 
	 */
	public void remove(Object o) {
		getSqlMapClientTemplate().delete(o.getClass().getName() + POSTFIX_DELETE, o);
	}

	/**
	 * 
	 */
	public <T> void remove(Class<T> entityClass, Serializable id) {
		getSqlMapClientTemplate().delete(entityClass.getName() + POSTFIX_DELETE_PRIAMARYKEY, id);
	}

	/**
	 * 
	 */
	public <T> List<T> find(Class<T> entityClass, Map map) {
		if (map == null)
			return this.getSqlMapClientTemplate().queryForList(entityClass.getName() + POSTFIX_SELECT, null);
		else {
			map.put("findBy", "True");
			return this.getSqlMapClientTemplate().queryForList(entityClass.getName() + POSTFIX_SELECTMAP, map);
		}
	}

	/**
	 * 
	 */
	public <T> List<T> find(Class<T> entityClass, String sql) {
		Assert.hasText(sql);
		if (StringUtils.isEmpty(sql))
			return this.getSqlMapClientTemplate().queryForList(entityClass.getName() + POSTFIX_SELECT, null);
		else
			return this.getSqlMapClientTemplate().queryForList(entityClass.getName() + POSTFIX_SELECTSQL, sql);
	}

	/**
	 * 
	 */
	public <T> List<T> findBy(Class<T> entityClass, String name, Object value) {
		Assert.hasText(name);
		Map map = new HashMap();
		map.put(name, value);
		return find(entityClass, map);
	}

	/**
	 * 
	 */
	public <T> T findUniqueBy(Class<T> entityClass, String name, Object value) {
		Assert.hasText(name);
		Map map = new HashMap();
		try {
			PropertyUtils.getProperty(entityClass.newInstance(), name);
			map.put(name, value);
			map.put("findUniqueBy", "True");
			return (T) getSqlMapClientTemplate().queryForObject(entityClass.getName() + POSTFIX_SELECTMAP, map);
		} catch (Exception e) {
			logger.error("Error when propertie on entity," + e.getMessage(), e.getCause());
			return null;
		}

	}

	/**
	 * 
	 */
	public <T> List<T> findByLike(Class<T> entityClass, String name, String value) {
		Assert.hasText(name);
		Map map = new HashMap();
		map.put(name, value);
		map.put("findLikeBy", "True");
		return getSqlMapClientTemplate().queryForList(entityClass.getName() + POSTFIX_SELECTMAP, map);

	}

	/**
	 * 
	 */
	public boolean isNotUnique(Object entity, String tableName, String names) {
		try {
			String primarykey;
			Connection con = getSqlMapClient().getCurrentConnection();
			ResultSet dbMetaData = con.getMetaData().getPrimaryKeys(con.getCatalog(), null, tableName);
			dbMetaData.next();
			if (dbMetaData.getRow() > 0) {
				primarykey = dbMetaData.getString(4);
				if (names.indexOf(primarykey) > -1)
					return false;
			} else {
				return true;
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return false;
	}

	/**
	 * 
	 */
	public Page pagedQuery(Class entityClass, Object parameterObject, int pageNo, int pageSize) {

		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Integer totalCount = (Integer) this.getSqlMapClientTemplate().queryForObject(
				entityClass.getName() + POSTFIX_COUNT, null);

		if (totalCount == null)
			return new Page();
		List list;
		if (pageSize > 0 && pageNo > 0) {
			list = getSqlMapClientTemplate().queryForPaginatedList(entityClass.getName() + POSTFIX_SELECT,
					parameterObject, pageSize);

			if (list.size() >= pageNo) {
				((PaginatedList) list).gotoPage(pageNo);

			} else {
				// FIXME is this logic correct?
				((PaginatedList) list).gotoPage(list.size());

			}
		} else {
			list = getSqlMapClientTemplate().queryForList(entityClass.getName() + POSTFIX_SELECT, null);
		}
		return new Page(startIndex, totalCount, pageSize, list);
	}

}
