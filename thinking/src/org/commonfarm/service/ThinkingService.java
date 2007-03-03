package org.commonfarm.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.commonfarm.dao.HibernateDAO;
import org.commonfarm.util.BeanUtil;
import org.springframework.dao.DataIntegrityViolationException;

public class ThinkingService {
	//private static final Log logger = LogFactory.getLog(ThinkingService.class);
	
	protected HibernateDAO hibernateDAO;
	
	public ThinkingService() {}

	/**
	 * @return the hibernateDAO
	 */
	public HibernateDAO getHibernateDAO() {
		return hibernateDAO;
	}

	/**
	 * @param hibernateDAO the hibernateDAO to set
	 */
	public void setHibernateDAO(HibernateDAO hibernateDAO) {
		this.hibernateDAO = hibernateDAO;
	}
	
	/**
     * Generic method to get an object based on class and identifier. 
     * 
     * @param clazz model class to lookup
     * @param id the identifier (primary key) of the class
     * @return a populated object 
     */
    public Object getObject(Class clazz, Serializable id) {
        return hibernateDAO.getObject(clazz, id);
    }
    /*public Object getObject(Object object, String[] parameters) {
        return lookupObject.getObject(object, parameters);
    }*/
    /**
     * Generic method used to get a all objects of a particular type. 
     * @param clazz the type of objects 
     * @return List of populated objects
     */
    public List getObjects(Class clazz) {
        return hibernateDAO.getObjects(clazz);
    }
    /*public List getObjects(Object object, String[] parameters) {
        return hibernateDAO.getObjects(object, parameters);
    }
    public List getObjects(Class clazz, Map criterias) {
        return lookupObject.getObjects(clazz, criterias);
    }*/

    /**
     * Generic method to save an object.
     * @param o the object to save
     */
    public void saveObject(Object o) throws BusinessException {
    	try {
    		hibernateDAO.saveObject(o);
    	} catch (DataIntegrityViolationException e) {
        	throw new BusinessException("This Object already exists!");
        }
    }
    
    /**
     * Generic method to update an object.
     * @param o the object to save
     */
    public void updateObject(Object o) throws BusinessException {
    	try {
    		hibernateDAO.updateObject(o);
	    } catch (DataIntegrityViolationException e) {
	    	throw new BusinessException("Update failure!");
	    }
    }
    
    /**
     * Generic method to delete an object based on class and id
     * @param clazz model class to lookup
     * @param id the identifier of the class
     */
    public void removeObject(Class clazz, Serializable id) throws BusinessException {       
        try {
        	hibernateDAO.removeObject(clazz, id);
		} catch (DataIntegrityViolationException e) {
	    	throw new BusinessException("A certain object associats this " + clazz.getName() + "!" );
	    }
    }
    
    public void removeObject(Object model, Serializable id, String[] cascadeNames) throws BusinessException {
		Collection collection = (Collection) BeanUtil.getPropertyValue(getObject(model.getClass(), id), cascadeNames[0]);
		for (Iterator it = collection.iterator(); it.hasNext();) {
			BeanUtil.setProperty(it.next(), cascadeNames[1], null);
		}
		removeObject(model.getClass(), id);
    }
   
    public void removeObjects(Class clazz , Serializable[] ids) throws BusinessException {
    	
    }
    
    //////////////////////////////////////////////////////
    
    public Object getObject(Object model, String... parameters) {
    	return hibernateDAO.getObject(model, parameters);
    }
    public List getObjects(Object model, String... parameters) {
    	return hibernateDAO.getObjects(model, parameters);
    }
    public List getObjects(Class clazz, Map criterias) {
    	return hibernateDAO.getObjects(clazz, criterias);
    }
    
    /**
     * implement one-to-many or many-to-many method
     * @param models   one side object
     * @param selectModels many side object
     * @param cancelModels many side object
     */
	public void selectObjects(Collection models, List selectModels, List cancelModels) {
    	for (Iterator it = selectModels.iterator(); it.hasNext();) {
			Object selectModel = it.next();
			if (!models.contains(selectModel)) models.add(selectModel);
		}
    	for (Iterator it = cancelModels.iterator(); it.hasNext();) {
			Object cancelModel = it.next();
			if (models.contains(cancelModel)) models.remove(cancelModel);
		}
	}
}
