/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khyhq.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 客户优惠券Entity
 * @author 马晓亮
 * @version 2019-07-25
 */
@Table(name="t_kh_yhq", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="客户id"),
		@Column(name="yhqid", attrName="yhqid", label="优惠券id"),
		@Column(name="zt", attrName="zt", label="1 未使用 2 已使用 3 冻结 4 已过期"),
		@Column(name="end_date", attrName="endDate", label="结束时间"),
		@Column(name="start_date", attrName="startDate", label="开始时间"),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="type", attrName="type", label="类型"),
		@Column(name="yh_name", attrName="yhName", label="优惠名称", queryType=QueryType.LIKE),
		@Column(name="yh_fname", attrName="yhFname", label="优惠副名称"),
		@Column(name="yh_img", attrName="yhImg", label="优惠图片"),
		@Column(name="yh_je", attrName="yhJe", label="优惠金额"),
		@Column(name="yh_mj", attrName="yhMj", label="满多少减多少"),
	}, orderBy="a.id DESC"
)
public class KhYhq extends DataEntity<KhYhq> {

	public static final int KHYHQ_ZT_WSY = 1;     //未使用
	public static final int KHYHQ_ZT_YSY = 2;     //已使用
	public static final int KHYHQ_ZT_DJ = 3;     //冻结
	public static final int KHYHQ_ZT_YGQ = 4;     //已过期

	private static final long serialVersionUID = 1L;
	private String khid;		// 客户id
	private String yhqid;		// 优惠券id
	private Integer zt;		// 1 未使用 2 已使用 3 冻结 4 已过期
	private Date endDate;		// 结束时间
	private Date startDate;		// 开始时间
	private String type;		// 类型
	private String yhName;		// 优惠名称
	private String yhFname;		// 优惠副名称
	private String yhImg;		// 优惠图片
	private Double yhJe;		// 优惠金额
	private Double yhMj;		// 满多少减多少

	public KhYhq() {
		this(null);
	}

	public KhYhq(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="客户id长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=64, message="优惠券id长度不能超过 64 个字符")
	public String getYhqid() {
		return yhqid;
	}

	public void setYhqid(String yhqid) {
		this.yhqid = yhqid;
	}
	
	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}
	
	@JsonFormat(pattern = "yyyy.MM.dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@JsonFormat(pattern = "yyyy.MM.dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Length(min=0, max=1, message="类型长度不能超过 1 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="优惠名称长度不能超过 255 个字符")
	public String getYhName() {
		return yhName;
	}

	public void setYhName(String yhName) {
		this.yhName = yhName;
	}
	
	@Length(min=0, max=255, message="优惠副名称长度不能超过 255 个字符")
	public String getYhFname() {
		return yhFname;
	}

	public void setYhFname(String yhFname) {
		this.yhFname = yhFname;
	}
	
	@Length(min=0, max=500, message="优惠图片长度不能超过 500 个字符")
	public String getYhImg() {
		return yhImg;
	}

	public void setYhImg(String yhImg) {
		this.yhImg = yhImg;
	}
	
	public Double getYhJe() {
		return yhJe;
	}

	public void setYhJe(Double yhJe) {
		this.yhJe = yhJe;
	}

	public Double getYhMj() {
		return yhMj;
	}

	public void setYhMj(Double yhMj) {
		this.yhMj = yhMj;
	}
}