/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.entity.sptype;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 商品查询日志
 */
@Table(name="t_sp_log", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="客户id", isQuery=false),
		@Column(name="spid", attrName="spid", label="商品id", isQuery=false),
		@Column(name="type", attrName="type", label="商品id", isQuery=false),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="ms", attrName="ms", label="描述", isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=true, isQuery=false),
	}, orderBy="a.create_date ASC"
)
public class SpLog extends DataEntity<SpLog> {

	private static final long serialVersionUID = 1L;

	private String khid;
	private String spid;
	private String type;
	private String name;		// 名称
	private String ms;		// 描述

	public SpLog() {
		this(null);
	}

	public SpLog(String id){
		super(id);
	}


	@NotBlank(message="名称不能为空")
	@Length(min=0, max=255, message="名称长度不能超过 255 个字符")
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

	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}

	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}