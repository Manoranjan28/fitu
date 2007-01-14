/**
 * 
 */
package org.commonfarm.app.model;

import java.util.Set;

/**
 * @author david
 *
 */
public class UserGroup {
	private Long id;
	private String name;
	private String desc;
	private Set users;
	
	public UserGroup() {}

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
	 * @return the users
	 */
	public Set getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set users) {
		this.users = users;
	}
}
