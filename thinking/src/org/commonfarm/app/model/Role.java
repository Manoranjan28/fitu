package org.commonfarm.app.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author david
 */
@Entity
@Table(name = "SYS_O_ROLES")
public class Role {
	private Long id;
	private String name;
	private String descn;
	
	private Set groups;
	
	public Role() {}
	
	public Role(String name) {
		this.name = name;
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
	 * @return the groups
	 */
	@OneToMany(
		targetEntity = UserGroup.class,
		cascade = {CascadeType.PERSIST, CascadeType.MERGE},
		mappedBy = "role"
	)
	public Set getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(Set groups) {
		this.groups = groups;
	}

	public void addGroup(UserGroup group) {
		if (groups == null) groups = new HashSet();
		groups.add(group);
	}
	
}
