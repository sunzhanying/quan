/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.exam.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 题目类型表Entity
 * @author 马晓亮
 * @version 2019-08-02
 */
@Table(name="exam_config", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="typeid", attrName="typeid.id", label="题库类型id"),
		@Column(name="maxf", attrName="maxf", label="最高分"),
		@Column(name="minf", attrName="minf", label="最低分"),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.create_date ASC"
)
public class ExamConfig extends DataEntity<ExamConfig> {
	
	private static final long serialVersionUID = 1L;
	private ExamType typeid;		// 题库类型id 父类
	private Integer maxf;		// 最高分
	private Long minf;		// 最低分
	private String name;		// name
	
	public ExamConfig() {
		this(null);
	}


	public ExamConfig(ExamType typeid){
		this.typeid = typeid;
	}
	
	@Length(min=0, max=64, message="题库类型id长度不能超过 64 个字符")
	public ExamType getTypeid() {
		return typeid;
	}

	public void setTypeid(ExamType typeid) {
		this.typeid = typeid;
	}
	
	public Integer getMaxf() {
		return maxf;
	}

	public void setMaxf(Integer maxf) {
		this.maxf = maxf;
	}
	
	public Long getMinf() {
		return minf;
	}

	public void setMinf(Long minf) {
		this.minf = minf;
	}
	
	@Length(min=0, max=255, message="name长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}