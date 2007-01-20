package org.commonfarm.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.util.BeanUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

/**
 *
 * @author David Yang
 * @see HibernateGenericDAO
 */
@SuppressWarnings("unchecked")
public class HibernateDAO extends HibernateDaoSupport {
	protected static Log logger = LogFactory.getLog(HibernateDAO.class);
	
	public HibernateDAO() {}
	
	public void flush() {
		getHibernateTemplate().flush();
	}

	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * create Query object
	 * Example:
	 * 	dao.createQuery(hql)
	 * 	dao.createQuery(hql,arg0);
	 * 	dao.createQuery(hql,arg0,arg1);
	 * 	dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * @param values valiable parameter
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * Create Criteria Object
	 * @param criterions valiable parameter
	 */
	public Criteria createCriteria(Class entityClass, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 *
	 * @see #createCriteria(Class,Criterion[])
	 */
	public Criteria createCriteria(Class entityClass, String orderBy, boolean isAsc, Criterion... criterions) {
		Assert.hasText(orderBy);

		Criteria criteria = createCriteria(entityClass, criterions);

		if (isAsc)
			criteria.addOrder(Order.asc(orderBy));
		else
			criteria.addOrder(Order.desc(orderBy));

		return criteria;
	}
	/**
	 * search by hql
	 * @param values valiable parameter
	 */
	public List find(String hql, Object... values) {
		Assert.hasText(hql);
		return getHibernateTemplate().find(hql, values);
	}

    /**
     * Get object by id
     */
    public Object getObject(Class clazz, Serializable id) {
        Object o = getHibernateTemplate().get(clazz, id);

        if (o == null) {
            throw new ObjectRetrievalFailureException(clazz, id);
        }

        return o;
    }
    /**
     * Get unique Object
     */
    public Object getObject(Class clazz, String columnName, String clumnValue) {
        Object o = getSession().createCriteria(clazz)
        				.add(Restrictions.like(columnName, clumnValue))
        				.uniqueResult();
        if (o == null) {
            throw new ObjectRetrievalFailureException(clazz, columnName);
        }
        return o;
    }
    /**
     * Get objects
     */
    public List getObjects(Class clazz) {
        return getHibernateTemplate().loadAll(clazz);
    }
    /**
     * Save Object
     */
    public void saveObject(Object o) {
        getHibernateTemplate().saveOrUpdate(o);
        getHibernateTemplate().flush();
    }
    /**
     * Remove object by id
     */
    public void removeObject(Class clazz, Serializable id) {
        getHibernateTemplate().delete(getObject(clazz, id));
        this.flush();
    }
    public void removeObject(Object obj) {
    	getHibernateTemplate().delete(obj);
    	this.flush();
    }
    //////////////////// Look up object//////////////////////////////////
    
    public Object getObject(Object object, String... parameters) {
        Criteria criteria = getSession().createCriteria(object.getClass());
        for (int i = 0; i < parameters.length; i++) {
            criteria.add(Restrictions.eq(parameters[i], BeanUtil.getPropertyValue(object, parameters[i])));
        }
        return criteria.uniqueResult();
    }

    public List getObjects(Object object, String... parameters) {
        Criteria criteria = getSession().createCriteria(object.getClass());
        for (int i = 0; i < parameters.length; i++) {
            criteria.add(Restrictions.eq(parameters[i], BeanUtil.getPropertyValue(object, parameters[i])));
        }
        return criteria.list();
    }
    /**
     * Get results by criterias
     * @param clazz     Class
     * @param criterias Map
     * @return
     */
    public List getObjects(Class clazz, Map criterias) {
        Criteria criteria = getSession().createCriteria(clazz);
        for (Iterator iter = criterias.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			criteria.add(Restrictions.eq(key, criterias.get(key)));
		}
    	return criteria.list();
    }
}
