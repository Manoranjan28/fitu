package org.commonfarm.app.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author david
 */
@Entity
@Table(name = "SYS_O_USERGROUPS")
public class UserGroup {
	private Long id;
	private String name;
	private String descn;
	
	private Set users;
	private Role role;
	
	public UserGroup() {}
	public UserGroup(String name) {
		this.name = name;
	}

	/**
	 * @return the descn
	 */
	public String getDescn() {
		return descn;
	}

	/**
	 * @param descn the descn to set
	 */
	public void setDescn(String descn) {
		this.descn = descn;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	/**
	 * @return the users
	 */
	@ManyToMany(
		targetEntity = User.class,
		cascade = {CascadeType.ALL}
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
	public void addUser(User user) {
		if (users == null) users = new HashSet();
		users.add(user);
	}
	
	/**
	 * select or repeal users
	 * @param selectUsers
	 * @param cancelUsers
	 */
	public void selectUsers(List selectUsers, List cancelUsers) {
		Collection users = this.getUsers();
    	for (Iterator it = selectUsers.iterator(); it.hasNext();) {
			User user = (User) it.next();
			if (!users.contains(user)) users.add(user);
		}
    	for (Iterator it = cancelUsers.iterator(); it.hasNext();) {
			User user = (User) it.next();
			if (users.contains(user)) users.remove(user);
		}
	}
}
