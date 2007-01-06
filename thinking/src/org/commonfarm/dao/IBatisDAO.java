package org.commonfarm.dao;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * 
 * @author Xufeng
 */
public class IBatisDAO extends SqlMapClientDaoSupport { 
    /**
     *  get List date
     *      select
     * @param statement
     * @param obj
     * @return
     */
	public List getObjects(String statement, Object obj) {
        List list = this.getSqlMapClientTemplate().queryForList(statement, obj);
        return list;
	}

    /**
     *  get a row data
     *      select
     * @param statement
     * @param obj
     * @return
     */
	public Object getObject(String statement, Object obj) {
        Object result = this.getSqlMapClientTemplate().queryForObject(statement, obj);
        return result;
	}
	
    /**
     *  save object
     *      insert
     * @param statement
     * @param obj
     * @return
     */
    public int saveObject(String statement, Object obj) {
        return this.getSqlMapClientTemplate().update(statement, obj);
    }
    
    /**
     * update data
     *      update
     * @param statement
     * @param obj
     * @return
     */
	public int updateObject(String statement, Object obj) {
		return this.getSqlMapClientTemplate().update(statement, obj);
	}
    
    /**
     * remove object
     *      delete
     * @param statement
     * @param obj
     * @return
     */
    public int removeObject(String statement, Object obj) {
        return this.getSqlMapClientTemplate().delete(statement, obj);
    }
    
    /**
     * Batch insert data 
     *      
     * @param statement
     * @param list
     */
    public void saveObjects(final String statement, final List list){
        if (!list.isEmpty()) {
            getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                    executor.startBatch();
                    Iterator ite = list.iterator();
                    while (ite.hasNext()) {
                        executor.insert(statement, ite.next());
                    }
                    executor.executeBatch();
                    return null;
                }
            });
        }
    }

}
