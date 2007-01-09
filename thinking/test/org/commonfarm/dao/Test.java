package org.commonfarm.dao;

import javax.persistence.*;

@Entity
@Table (name = "THK_TEST")
public class Test {
	private Long id;
	private String name;
	private String remark;
	private String desc;
	
	public Test() {}
	
	public Test(Long id, String name, String remark) {
		this.id = id;
		this.name = name;
		this.remark = remark;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "ATTR1")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
