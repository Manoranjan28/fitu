package org.commonfarm.sap;

import org.springframework.dao.DataAccessException;

import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Repository;

/**
 * Helper class that simplifies JCO data access code.
 *
 * @author David Yang
 */
public class SapJCOTemplate {

	private boolean alwaysUseNewSession = false;
	
	protected JcoSource jcoSource;

	/**
	 * Create a new SapJCOTemplate instance.
	 */
	public SapJCOTemplate() {}

	/**
	 * Create a new SapJCOTemplate instance.
	 * @param jcoSource JCOSource
	 */
	public SapJCOTemplate(JcoSource jcoSource) {
		this.jcoSource = jcoSource;
		afterPropertiesSet();
	}

	/**
	 * 
	 * @param action
	 * @return
	 * @throws DataAccessException
	 */
	public Object execute(String funName, Object paraObj, SapJCOCallback action) throws DataAccessException {
		Client client = getClient();
		Repository repository = new Repository("SAPJCO", client);
		Function function = repository.getFunctionTemplate(funName).getFunction();

		Object result = null;
		
		try {
			result = action.doInJCO(client, function, paraObj);
			return result;
		} catch (JcoException ex) {
			//throw convertHibernateAccessException(ex);
		} finally {
			JcoUtil.release(client, jcoSource);
		}
		return result;
	}
	
	/**
	 * 
	 * @param action
	 * @return
	 * @throws DataAccessException
	 */
	public Object find(String funName, Object paraObj, SapJCOCallback action) throws DataAccessException {
		Client client = getClient();
		Repository repository = new Repository(jcoSource.getRepository(), client);
		Function function = repository.getFunctionTemplate(funName).getFunction();

		Object result = null;
		
		try {
			result = action.doInJCO(client, function, paraObj);
			return result;
		} catch (JcoException ex) {
			//throw convertHibernateAccessException(ex);
		} finally {
			JcoUtil.release(client, jcoSource);
		}
		return result;
	}

	/**
	 * Return a Session for use by this template.
	 * <p>Returns a new Session in case of "alwaysUseNewSession" (using the same
	 * JDBC Connection as a transactional Session, if applicable), a pre-bound
	 * Session in case of "allowCreate" turned off, and a pre-bound or new Session
	 * else (new only if no transactional or otherwise pre-bound Session exists).
	 * @see ClientFactoryUtils#getSession
	 * @see ClientFactoryUtils#getNewSession
	 * @see #setAlwaysUseNewSession
	 * @see #setAllowCreate
	 */
	protected Client getClient() {
		return jcoSource.createClient();
	}

	/**
	 * @return the alwaysUseNewSession
	 */
	public boolean isAlwaysUseNewSession() {
		return alwaysUseNewSession;
	}

	/**
	 * @param alwaysUseNewSession the alwaysUseNewSession to set
	 */
	public void setAlwaysUseNewSession(boolean alwaysUseNewSession) {
		this.alwaysUseNewSession = alwaysUseNewSession;
	}
	public void afterPropertiesSet() {
		if (jcoSource == null) {
			throw new IllegalArgumentException("jcoSource is required");
		}
	}

	/**
	 * @return the jcoSource
	 */
	public JcoSource getJcoSource() {
		return jcoSource;
	}

	/**
	 * @param jcoSource the jcoSource to set
	 */
	public void setJcoSource(JcoSource jcoSource) {
		this.jcoSource = jcoSource;
	}
}
