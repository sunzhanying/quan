/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.entity.meterialtype;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 产品类型Entity
 * @author liqingfeng
 * @version 2019-07-05
 */
@Table(name="meterial_type", alias="a", columns={
		@Column(name="code", attrName="code", label="编码", isPK=true),
		@Column(includeEntity=TreeEntity.class),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE, isTreeName=true),
		@Column(name="type", attrName="type", label="类型"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.tree_sorts, a.code"
)
public class MeterialType extends TreeEntity<MeterialType> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 编码
	private String name;		// 名称
	private String type;		// 类型
	
	public MeterialType() {
		this(null);
	}

	public MeterialType(String id){
		super(id);
	}
	
	@Override
	public MeterialType getParent() {
		return parent;
	}

	@Override
	public void setParent(MeterialType parent) {
		this.parent = parent;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotBlank(message="名称不能为空")
	@Length(min=0, max=100, message="名称长度不能超过 100 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="类型长度不能超过 1 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}