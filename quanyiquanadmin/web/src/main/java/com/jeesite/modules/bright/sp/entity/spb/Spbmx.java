/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.entity.spb;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.modules.bright.sp.entity.SpXx;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;


/**
 * 商品包定义Entity
 * @author 马晓亮
 * @version 2019-07-01
 */
@Table(name="t_sp_spbmx", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="spbid", attrName="spbid.id", label="商品包id"),
		@Column(name="spid", attrName="spid", label="商品ID"),
		@Column(name="sl", attrName="sl", label="商品数量"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用 3冻结", isUpdate=false),
	},
		joinTable={
		@JoinTable(type=Type.LEFT_JOIN, entity=SpXx.class, attrName="spXx", alias="u10",
				on="u10.id = a.spid", columns={
				@Column(name="spmc", label="商品名称", isQuery=false),
		}),
	}, orderBy="a.id ASC"
)
public class Spbmx extends DataEntity<Spbmx> {
	
	private static final long serialVersionUID = 1L;
	private Spbdy spbid;		// 商品包id 父类
	private String spid;		// 商品ID
	private Long sl;		// 商品数量

	private SpXx spXx;     //商品信息
	
	public Spbmx() {
		this(null);
	}


	public Spbmx(Spbdy spbid){
		this.spbid = spbid;
	}
	
	@Length(min=0, max=64, message="商品包id长度不能超过 64 个字符")
	public Spbdy getSpbid() {
		return spbid;
	}

	public void setSpbid(Spbdy spbid) {
		this.spbid = spbid;
	}
	
	@Length(min=0, max=100, message="商品ID长度不能超过 100 个字符")
	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}
	
	public Long getSl() {
		return sl;
	}

	public void setSl(Long sl) {
		this.sl = sl;
	}

	public SpXx getSpXx() {
		return spXx;
	}

	public void setSpXx(SpXx spXx) {
		this.spXx = spXx;
	}

}