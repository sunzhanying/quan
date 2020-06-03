/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.entity.micro;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 微页面Entity
 * @author liqingfeng
 * @version 2019-06-18
 */
@Table(name="a_micro_page", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="title", attrName="title", label="标题", queryType=QueryType.LIKE),
		@Column(name="material_status", attrName="materialStatus", label="状态"),
		@Column(name="popularize", attrName="popularize", label="推广"),
		@Column(name="people", attrName="people", label="访问人次/人数"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.update_date DESC"
)
public class MicroPage extends DataEntity<MicroPage> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String materialStatus;		// 状态
	private String popularize;		// 推广
	private String people;		// 访问人次/人数
	private String delFlag;		// 删除标记
	
	public MicroPage() {
		this(null);
	}

	public MicroPage(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="标题长度不能超过 255 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="状态长度不能超过 255 个字符")
	public String getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(String materialStatus) {
		this.materialStatus = materialStatus;
	}
	
	@Length(min=0, max=255, message="推广长度不能超过 255 个字符")
	public String getPopularize() {
		return popularize;
	}

	public void setPopularize(String popularize) {
		this.popularize = popularize;
	}
	
	@Length(min=0, max=255, message="访问人次/人数长度不能超过 255 个字符")
	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}