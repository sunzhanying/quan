/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.entity.tag;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.NotBlank;

/**
 * 标签Entity
 * @author liqingfeng
 * @version 2019-07-05
 */
@Table(name="tags", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="标签名", queryType=QueryType.LIKE),
		@Column(name="sort", attrName="sort", label="排序"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.update_date DESC"
)
public class Tag extends DataEntity<Tag> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 标签名
	private Integer sort;		// 排序
	private String delFlag;		// 删除标记
	
	public Tag() {
		this(null);
	}

	public Tag(String id){
		super(id);
	}

	@NotBlank(message="标签名不能为空")
	@Length(min=0, max=5, message="标签名长度不能超过 5 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}