/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sale.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;

/**
 * 二级分销
 */
@Table(name="t_kh_sale", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="客户id"),
		@Column(name="parent_one", attrName="parentOne", label="父1级"),
		//@Column(name="parent_two", attrName="parentTwo", label="父2级"),
		@Column(name="child_one", attrName="childOne", label="子1级"),
		//@Column(name="child_two", attrName="childTwo", label="子2级"),
		@Column(includeEntity=DataEntity.class),
	},joinTable={
		@JoinTable(type= Type.LEFT_JOIN, entity=KhXx.class, attrName="khXx", alias="k",
				on="k.id = a.khid",
				columns={
						@Column(name="wxtx", attrName="wxtx", label="微信头像"),
						@Column(name="wxnc", attrName="wxnc", label="微信昵称"),
						@Column(name="xm", attrName="xm", label="姓名", queryType = QueryType.LIKE),
						@Column(name="sj", attrName="sj", label="手机", queryType = QueryType.LIKE),
				}),
	}, orderBy="a.update_date DESC"
)
public class Sale extends DataEntity<Sale> {

	private static final long serialVersionUID = 1L;
	private String khid;		// 客户id
	private String parentOne;
	//private String parentTwo;
	private String childOne;
	//private String childTwo;

	private KhXx khXx;
	
	public Sale() {
		this(null);
	}

	public Sale(String id){
		super(id);
	}

	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}

	public String getParentOne() {
		return parentOne;
	}

	public void setParentOne(String parentOne) {
		this.parentOne = parentOne;
	}

	/*public String getParentTwo() {
		return parentTwo;
	}

	public void setParentTwo(String parentTwo) {
		this.parentTwo = parentTwo;
	}*/

	public String getChildOne() {
		return childOne;
	}

	public void setChildOne(String childOne) {
		this.childOne = childOne;
	}

	/*public String getChildTwo() {
		return childTwo;
	}

	public void setChildTwo(String childTwo) {
		this.childTwo = childTwo;
	}*/

	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}
}