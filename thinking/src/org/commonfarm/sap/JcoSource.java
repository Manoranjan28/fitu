/*
 * 
 */

package org.commonfarm.sap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.commonfarm.util.StringUtil;

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;

/**
 * 
 */
public class JcoSource {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private String repository;
	private String host;
	private String username;
	private String password;
	private String encode;
	private String port;
	private String catalog;
	
	public JcoSource() {}
	public JcoSource(String host, String username, String password, 
			String encode, String port, String catalog) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.encode = encode;
		this.port = port;
		this.catalog = catalog;
	}
	
	public JcoSource(String repository, String host, String username, String password, 
			String encode, String port, String catalog) {
		this.repository = repository;
		this.host = host;
		this.username = username;
		this.password = password;
		this.encode = encode;
		this.port = port;
		this.catalog = catalog;
	}
	public Client getClient() {
		return JCO.createClient(catalog, username, password, encode, host, port);
	}
	/**
	 * @return the catalog
	 */
	public String getCatalog() {
		return catalog;
	}
	/**
	 * @param catalog the catalog to set
	 */
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the encode
	 */
	public String getEncode() {
		return encode;
	}
	/**
	 * @param encode the encode to set
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the repository
	 */
	public String getRepository() {
		return repository;
	}
	/**
	 * @param repository the repository to set
	 */
	public void setRepository(String repository) {
		this.repository = repository;
	}
	/**
	 * Create Client
	 * @return
	 */
	public Client createClient() {
		if (!StringUtil.notEmpty(encode)) {
			encode = "EN";
		}
		Client client = JCO.createClient(catalog, username, password, encode, host, port);
		client.connect();
		return client;
	}

}
