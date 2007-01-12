package org.commonfarm.service;

import java.io.Serializable;
import java.util.List;

import org.commonfarm.dao.HibernateDAO;
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
    		hibernateDAO.saveObject(o);
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
    public void removeObjects(Class clazz , Serializable[] ids) throws BusinessException {
    	
    }
}
