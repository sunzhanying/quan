/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.ctyd.entity;

import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 餐厅预订表Entity
 * @author 马晓亮
 * @version 2019-07-24
 */
@Table(name="t_ctyd", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="ctid", attrName="ctid", label="餐厅id"),
		@Column(name="khid", attrName="khid", label="客户id"),
		@Column(name="rs", attrName="rs", label="预订人数"),
		@Column(name="yd_date", attrName="ydDate", label="预订日期"),
		@Column(name="yd_time", attrName="ydTime", label="预订时间"),
		@Column(name="yddf", attrName="yddf", label="预订地方 大厅 包间"),
		@Column(name="name", attrName="name", label="姓名", queryType=QueryType.LIKE),
		@Column(name="sex", attrName="sex", label="性别 1 男 2 女"),
		@Column(name="phone", attrName="phone", label="电话"),
		@Column(name="zt", attrName="zt", label="状态 1 预定中 2 已取消 3 已确认 4 已用餐"),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
	}, orderBy="a.update_date DESC"
)
public class Ctyd extends DataEntity<Ctyd> {

	//餐厅预订状态
	public static final int CTYD_ZT_YDZ = 1;   //预定中
	public static final int CTYD_ZT_YQX = 2;   //已取消
	public static final int CTYD_ZT_YQR = 3;   //已确认
	public static final int CTYD_ZT_YYC = 4;   //已用餐

	private static final long serialVersionUID = 1L;
	private String ctid;		// 餐厅id
	private String khid;		// 客户id
	private Long rs;		// 预订人数
	private Date ydDate;		// 预订日期
	private Date ydTime;		// 预订时间
	private String yddf;		// 预订地方 大厅 包间
	private String name;		// 姓名
	private Integer sex;		// 性别 1 男 2 女
	private String phone;		// 电话
	private Integer zt;		// 状态 1 预定中 2 已取消 3 已确认 4 已用餐

	private KhXx khXx;     //客户信息
	private String gjz;    //关键字查询
	
	public Ctyd() {
		this(null);
	}

	public Ctyd(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="餐厅id长度不能超过 64 个字符")
	public String getCtid() {
		return ctid;
	}

	public void setCtid(String ctid) {
		this.ctid = ctid;
	}
	
	@Length(min=0, max=64, message="客户id长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	public Long getRs() {
		return rs;
	}

	public void setRs(Long rs) {
		this.rs = rs;
	}
	
	@JsonFormat(pattern = "MM月dd日")
	public Date getYdDate() {
		return ydDate;
	}

	public void setYdDate(Date ydDate) {
		this.ydDate = ydDate;
	}

	@JsonFormat(pattern = "HH:mm")
	public Date getYdTime() {
		return ydTime;
	}

	public void setYdTime(Date ydTime) {
		this.ydTime = ydTime;
	}
	
	@Length(min=0, max=10, message="预订地方 大厅 包间长度不能超过 10 个字符")
	public String getYddf() {
		return yddf;
	}

	public void setYddf(String yddf) {
		this.yddf = yddf;
	}
	
	@Length(min=0, max=20, message="姓名长度不能超过 20 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=22, message="电话长度不能超过 22 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}

	public String getGjz() {
		return gjz;
	}

	public void setGjz(String gjz) {
		this.gjz = gjz;
	}
}