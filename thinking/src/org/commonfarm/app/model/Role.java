/**
 * 
 */
package org.commonfarm.app.model;

import java.util.Set;

/**
 * @author david
 *
 */
public class Role {
	private Long id;
	private String name;
	private String desc;
	private Set userGroups;
	
	public Role() {}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the userGroups
	 */
	public Set getUserGroups() {
		return userGroups;
	}

	/**
	 * @param userGroups the userGroups to set
	 */
	public void setUserGroups(Set userGroups) {
		this.userGroups = userGroups;
	}
}
