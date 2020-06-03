/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.entity.xw;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 客户行为配置Entity
 * @author 李金辉
 * @version 2019-06-27
 */
@Table(name="t_xw", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="行为名称", queryType=QueryType.LIKE),
		@Column(name="ms", attrName="ms", label="描述"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.update_date DESC"
)
public class Xw extends DataEntity<Xw> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 行为名称
	private String ms;		// 描述
	private String delFlag;		// 删除标记
	
	public Xw() {
		this(null);
	}

	public Xw(String id){
		super(id);
	}
	
	@Length(min=0, max=500, message="行为名称长度不能超过 500 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="描述长度不能超过 255 个字符")
	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}