/**
 * 
 */
package org.commonfarm.app.model;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author david
 */
@Entity
@Table (name = "SYS_O_USERS")
public class User implements Principal{
	private Long id;
	private String userId;
	private String password;
	private String secondName;
	private String firstName;
	private String address;
	private String tel;
	private String fax;
	private String zcode;
	private String email;
	private String imid;
	private Date bornDate;
	
	private boolean selected = false;
	
	private Set groups;
	
	public User() {}

	public User(String userId) {
		this.userId = userId;
	}

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
	public String getSecondName() {
		return secondName;
	}

	/**
	 * @param name the name to set
	 */
	public void setSecondName(String secondName) {
		this.secondName = secondName;
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
		targetEntity = UserGroup.class
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
	/**
	 * Implement Principal --> getName()
	 */
	@Transient
	public String getName() {
		return userId;
	}
	
	/**
    * Verify that the supplied password matches the stored password for the user.
    */
	public boolean authenticate(String password) {
		if (password == null) {
            return false;
        }
		if (this.password.equals(password)) return true;
		return false;
	}
	/**
	 * load user group and role
	 */
	public void init() {
		if (groups != null) {
			for (Iterator it = groups.iterator(); it.hasNext();) {
				UserGroup userGroup = (UserGroup) it.next();
				userGroup.getRole();
			}
		}
	}
	/**
	 * Judge role exist
	 * @param roleName
	 * @return
	 */
	public boolean contains(String roleName) {
		if (groups != null) {
			for (Iterator it = groups.iterator(); it.hasNext();) {
				UserGroup userGroup = (UserGroup) it.next();
				Role role = userGroup.getRole();
				if (role.getName().equals(roleName)) return true;
			}
		}
		return false;
	}

	/**
	 * @return the selected
	 */
	@Transient
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
