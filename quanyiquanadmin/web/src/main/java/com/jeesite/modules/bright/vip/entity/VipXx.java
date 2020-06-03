/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.vip.entity;

import com.jeesite.modules.bright.sp.entity.spb.Spbdy;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 会员信息表，定义会员级别名称Entity
 * @author 马晓亮
 * @version 2019-07-02
 */
@Table(name="t_vip_xx", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="hydm", attrName="hydm", label="会员代码"),
		@Column(name="hymc", attrName="hymc", label="会员名称", queryType=QueryType.LIKE),
		@Column(name="hyjg", attrName="hyjg", label="会员价格"),
		@Column(name="spbid", attrName="spbid", label="商品包id"),
		@Column(name="hysx", attrName="hysx", label="失效时间", comment="失效时间：自购买之日起多久失效"),
		@Column(name="hyjb", attrName="hyjb", label="会员级别  数越大权益越多"),
		@Column(name="sxxz", attrName="sxxz", label="失效性质 1 年 2月 3 日"),
		@Column(name="remarks", attrName="remarks", label="会员说明", queryType=QueryType.LIKE),
		@Column(name="sxj", attrName="sxj", label="1上架 2 下架"),
	}, orderBy="a.id DESC"
)
public class VipXx extends DataEntity<VipXx> {
	
	private static final long serialVersionUID = 1L;
	private String hydm;		// 会员代码
	private String hymc;		// 会员名称
	private Double hyjg;		// 会员价格
	private String spbid;		// 商品包id
	private Long hysx;		// 失效时间：自购买之日起多久失效
	private Integer hyjb;		// 会员级别  数越大权益越多
	private String sxxz;		// 失效性质 1 年 2月 3 日
	private String sxj;		// 1上架 2 下架

	private Spbdy spbdy;   //商品包
	
	public VipXx() {
		this(null);
	}

	public VipXx(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="会员代码长度不能超过 255 个字符")
	public String getHydm() {
		return hydm;
	}

	public void setHydm(String hydm) {
		this.hydm = hydm;
	}
	
	@Length(min=0, max=255, message="会员名称长度不能超过 255 个字符")
	public String getHymc() {
		return hymc;
	}

	public void setHymc(String hymc) {
		this.hymc = hymc;
	}
	
	public Double getHyjg() {
		return hyjg;
	}

	public void setHyjg(Double hyjg) {
		this.hyjg = hyjg;
	}
	
	@Length(min=0, max=64, message="商品包id长度不能超过 64 个字符")
	public String getSpbid() {
		return spbid;
	}

	public void setSpbid(String spbid) {
		this.spbid = spbid;
	}
	
	public Long getHysx() {
		return hysx;
	}

	public void setHysx(Long hysx) {
		this.hysx = hysx;
	}
	
	public Integer getHyjb() {
		return hyjb;
	}

	public void setHyjb(Integer hyjb) {
		this.hyjb = hyjb;
	}
	
	@Length(min=0, max=1, message="失效性质 1 年 2月 3 日长度不能超过 1 个字符")
	public String getSxxz() {
		return sxxz;
	}

	public void setSxxz(String sxxz) {
		this.sxxz = sxxz;
	}
	
	@Length(min=0, max=1, message="1上架 2 下架长度不能超过 1 个字符")
	public String getSxj() {
		return sxj;
	}

	public void setSxj(String sxj) {
		this.sxj = sxj;
	}

	public Spbdy getSpbdy() {
		return spbdy;
	}

	public void setSpbdy(Spbdy spbdy) {
		this.spbdy = spbdy;
	}
}