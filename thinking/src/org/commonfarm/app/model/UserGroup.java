/**
 * 
 */
package org.commonfarm.app.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author david
 *
 */
@Entity
@Table (name = "SYS_O_USER-GROUPS")
public class UserGroup {
	private Long id;
	private String name;
	private String desc;
	private Set users;	
	private Role role;
	
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
	@ManyToMany(
		targetEntity = org.commonfarm.app.model.User.class,
		cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(
		name = "SYS_R_USER_GROUP",
		joinColumns = {@JoinColumn(name = "GROUP_ID")},
		inverseJoinColumns = {@JoinColumn(name = "USER_ID")}
	)
	public Set getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set users) {
		this.users = users;
	}
	/**
	 * @return the role
	 */
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "ROLE_ID")
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}
}
