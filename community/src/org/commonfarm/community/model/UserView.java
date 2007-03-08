/**
 * 
 */
package org.commonfarm.community.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Record user view
 * 
 * @author David
 */
@Entity
@Table(name = "CM_R_USER_VIEW")
public class UserView {
	private Long id;
	private String userId;
	private String type;//Space; Topic; Article
	private Long whatId;
	
	public UserView() {}

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
	 * @return the whatId
	 */
	public Long getWhatId() {
		return whatId;
	}

	/**
	 * @param whatId the whatId to set
	 */
	public void setWhatId(Long whatId) {
		this.whatId = whatId;
	}
}
