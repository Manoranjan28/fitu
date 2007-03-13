/**
 * 
 */
package org.commonfarm.community.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
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
@Table(name = "CM_O_SPACES")
public class Space {
	private Long id;
	private String name;
	private String subject;
	private String descn;
	private String type;//Blog; Article category;
	private String category;//tech, lift, science....
	private String owner;
	private String createUser;
	private Date createDate;
	private Set topics;
	
	public Space() {}

	/**
	 * @return the category
	 */
	@OneToMany(
		targetEntity = Topic.class,
		cascade = {CascadeType.PERSIST, CascadeType.MERGE},
		mappedBy = "space"
	)
	public Set getTopics() {
		return topics;
	}

	/**
	 * @param category the category to set
	 */
	public void setTopics(Set category) {
		this.topics = category;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the categroy
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param categroy the categroy to set
	 */
	public void setCategory(String categroy) {
		this.category = categroy;
	}
}
