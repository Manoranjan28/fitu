package org.commonfarm.community.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * @author david
 *
 */
@Entity
@Table(name = "CM_O_ARTICLES")
public class Article {
	private Long id;
	private String title;
	private String summary;
	private String label;
	private String level;//5 star gived by reader
	private Boolean goodFlg;//aissign by administrator
	private Boolean stickyFlg;
	private Integer viewCnt;
	private Set attachments; 
	private Set comments;
	private String createUser;
	private Date createDate;
	private Date updateDate;
	
	private Set topics;
	
	private boolean selected = false;
	
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
	@OneToMany(
		targetEntity = Comment.class,
		cascade = {CascadeType.PERSIST, CascadeType.MERGE},
		mappedBy = "article"
	)
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
	public Integer getViewCnt() {
		return viewCnt;
	}

	/**
	 * @param readCnt the readCnt to set
	 */
	public void setViewCnt(Integer readCnt) {
		this.viewCnt = readCnt;
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

	/**
	 * @return the attachs
	 */
	@OneToMany(
		targetEntity = Attachment.class,
		cascade = {CascadeType.PERSIST, CascadeType.MERGE},
		mappedBy = "article"
	)
	public Set getAttachments() {
		return attachments;
	}

	/**
	 * @param attachs the attachs to set
	 */
	public void setAttachments(Set attachs) {
		this.attachments = attachs;
	}

	/**
	 * @return the topics
	 */
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "TOPIC_ID")
	public Set getTopics() {
		return topics;
	}

	/**
	 * @param topics the topics to set
	 */
	public void setTopics(Set topics) {
		this.topics = topics;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the selected
	 */
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
