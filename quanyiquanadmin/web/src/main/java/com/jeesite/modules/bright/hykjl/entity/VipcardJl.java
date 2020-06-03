/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.hykjl.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 会员卡消费记录Entity
 * @author 马晓亮
 * @version 2019-08-20
 */
@Table(name="t_vipcard_jl", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="客户id"),
		@Column(name="vip_card_id", attrName="vipCardId", label="会员卡id"),
		@Column(name="dkje", attrName="dkje", label="抵扣金额"),
		@Column(name="ye", attrName="ye", label="余额"),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="kcfs", attrName="kcfs", label="扣除方式 1 线上 2 线下"),
	},orderBy="a.id DESC"
)
public class VipcardJl extends DataEntity<VipcardJl> {

	public static final String 	VIP_CARD_KCFS_XS = "1";   //线上
	public static final String 	VIP_CARD_KCFS_XX = "2";   //线下

	private static final long serialVersionUID = 1L;
	private String khid;		// 客户id
	private String vipCardId;		// 会员卡id
	private Double dkje;		// 抵扣金额
	private Double ye;		// 余额
	private String kcfs;		// 扣除方式 1 线上 2 线下

	private KhXx khXx;             //用户信息
	
	public VipcardJl() {
		this(null);
	}

	public VipcardJl(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="客户id长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=64, message="会员卡id长度不能超过 64 个字符")
	public String getVipCardId() {
		return vipCardId;
	}

	public void setVipCardId(String vipCardId) {
		this.vipCardId = vipCardId;
	}
	
	public Double getDkje() {
		return dkje;
	}

	public void setDkje(Double dkje) {
		this.dkje = dkje;
	}
	
	public Double getYe() {
		return ye;
	}

	public void setYe(Double ye) {
		this.ye = ye;
	}
	
	@Length(min=0, max=1, message="扣除方式 1 线上 2 线下长度不能超过 1 个字符")
	public String getKcfs() {
		return kcfs;
	}

	public void setKcfs(String kcfs) {
		this.kcfs = kcfs;
	}

	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}
}