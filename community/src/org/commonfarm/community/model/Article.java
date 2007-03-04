package org.commonfarm.community.model;

import java.util.Date;
import java.util.Set;
/**
 * @author david
 *
 */
public class Article {
	private Long id;
	private String title;
	private String summary;
	private String label;
	private String level;//5 star gived by reader
	private Boolean goodFlg;//aissign by administrator
	private Boolean stickyFlg;
	private Integer readCnt;
	private Set comments;
	private String createUser;
	private Date createDate;
	
	public Article() {}

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
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the comments
	 */
	public Set getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(Set comments) {
		this.comments = comments;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the readCnt
	 */
	public Integer getReadCnt() {
		return readCnt;
	}

	/**
	 * @param readCnt the readCnt to set
	 */
	public void setReadCnt(Integer readCnt) {
		this.readCnt = readCnt;
	}

	/**
	 * @return the goodFlg
	 */
	public Boolean getGoodFlg() {
		return goodFlg;
	}

	/**
	 * @param goodFlg the goodFlg to set
	 */
	public void setGoodFlg(Boolean goodFlg) {
		this.goodFlg = goodFlg;
	}

	/**
	 * @return the stickyFlg
	 */
	public Boolean getStickyFlg() {
		return stickyFlg;
	}

	/**
	 * @param stickyFlg the stickyFlg to set
	 */
	public void setStickyFlg(Boolean stickyFlg) {
		this.stickyFlg = stickyFlg;
	}
}
