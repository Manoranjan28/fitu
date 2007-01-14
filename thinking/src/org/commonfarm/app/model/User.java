/**
 * 
 */
package org.commonfarm.app.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

/**
 * @author david
 *
 */
@Entity
@Table (name = "SYS_O_USERS")
public class User {
	private Long id;
	private String userId;
	private String name;
	private String firstName;
	private String address;
	private String tel;
	private String fax;
	private String zcode;
	private String email;
	private String imid;
	private Date bornDate;
	
	private Set groups;
	
	public User() {}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the bornDate
	 */
	public Date getBornDate() {
		return bornDate;
	}

	/**
	 * @param bornDate the bornDate to set
	 */
	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	 * @return the imid
	 */
	public String getImid() {
		return imid;
	}

	/**
	 * @param imid the imid to set
	 */
	public void setImid(String imid) {
		this.imid = imid;
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
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the zcode
	 */
	public String getZcode() {
		return zcode;
	}

	/**
	 * @param zcode the zcode to set
	 */
	public void setZcode(String zcode) {
		this.zcode = zcode;
	}

	/**
	 * @return the groups
	 */
	@ManyToMany(
		cascade = {CascadeType.PERSIST, CascadeType.MERGE},
		mappedBy = "users",
		targetEntity = org.commonfarm.app.model.UserGroup.class
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
}
