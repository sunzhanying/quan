/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.collect.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 收藏权益Entity
 * @author 收藏
 * @version 2020-03-30
 */
@Table(name="a_collect", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="sp_id", attrName="spId", label="权益id"),
		@Column(name="khid", attrName="khid", label="客户id"),
		@Column(includeEntity=DataEntity.class),
	}, joinTable={
		@JoinTable(type= Type.LEFT_JOIN, entity=SpXx.class, attrName="spXx", alias="s",
				on="s.id = a.sp_id",
				columns={
						@Column(name="id", attrName="id", label="id", isQuery=false),
						@Column(name="spmc", attrName="spmc", label="商品名称", isQuery=false),
						@Column(name="type", attrName="type", label="商品名称", isQuery=false),
						@Column(name="img", attrName="img", label="图片", comment="图片：缩略图", isQuery=false),
						@Column(name="img_big", attrName="imgBig", label="图片", comment="图片：大图", isQuery=false),
				}),
	}, orderBy="a.update_date DESC"
)
public class Collect extends DataEntity<Collect> {
	
	private static final long serialVersionUID = 1L;
	private String spId;		// 权益id
	private String khid;		// 客户id
	private SpXx spXx;      //商品
	
	public Collect() {
		this(null);
	}

	public Collect(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="权益id长度不能超过 64 个字符")
	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}
	
	@Length(min=0, max=64, message="客户id长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}

	public SpXx getSpXx() {
		return spXx;
	}

	public void setSpXx(SpXx spXx) {
		this.spXx = spXx;
	}
}