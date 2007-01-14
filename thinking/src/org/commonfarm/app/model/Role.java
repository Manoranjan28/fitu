/**
 * 
 */
package org.commonfarm.app.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author david
 *
 */
@Entity
@Table (name = "SYS_O_ROLES")
public class Role {
	private Long id;
	private String name;
	private String desc;
	private Set groups;
	
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
	 * @return the groups
	 */
	@OneToMany(mappedBy = "role")
	public Set getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setUserGroups(Set groups) {
		this.groups = groups;
	}
}
