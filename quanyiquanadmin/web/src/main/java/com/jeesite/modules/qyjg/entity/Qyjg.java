/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyjg.entity;

import javax.validation.constraints.NotNull;

import com.jeesite.modules.bright.sp.entity.SpXx;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 权益券价格Entity
 * @author 马晓亮
 * @version 2020-03-24
 */
@Table(name="a_qyjg", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(includeEntity=DataEntity.class),
		@Column(name="hsj", attrName="hsj", label="回收价"),
		@Column(name="csj", attrName="csj", label="出售价"),
		@Column(name="qyq_id", attrName="qyqId", label="权益券id"),
	}, orderBy="a.create_date DESC"
)
public class Qyjg extends DataEntity<Qyjg> {
	
	private static final long serialVersionUID = 1L;
	private Double hsj;		// 回收价
	private Double csj;		// 出售价
	private String qyqId;		// 权益券id
	private SpXx spXx;     //权益券
	
	public Qyjg() {
		this(null);
	}

	public Qyjg(String id){
		super(id);
	}
	
	@NotNull(message="回收价不能为空")
	public Double getHsj() {
		return hsj;
	}

	public void setHsj(Double hsj) {
		this.hsj = hsj;
	}
	
	@NotNull(message="出售价不能为空")
	public Double getCsj() {
		return csj;
	}

	public void setCsj(Double csj) {
		this.csj = csj;
	}
	
	@Length(min=0, max=64, message="权益券id长度不能超过 64 个字符")
	public String getQyqId() {
		return qyqId;
	}

	public void setQyqId(String qyqId) {
		this.qyqId = qyqId;
	}

	public SpXx getSpXx() {
		return spXx;
	}

	public void setSpXx(SpXx spXx) {
		this.spXx = spXx;
	}
}