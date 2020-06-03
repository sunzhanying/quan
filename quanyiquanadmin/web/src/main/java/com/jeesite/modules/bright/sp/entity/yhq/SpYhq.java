/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.entity.yhq;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 优惠券定义表Entity
 * @author 马晓亮
 * @version 2019-06-25
 */
@Table(name="t_sp_yhq", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="yh_bh", attrName="yhBh", label="优惠编号"),
		@Column(name="type", attrName="type", label="类型"),
		@Column(name="yh_name", attrName="yhName", label="优惠名称", queryType=QueryType.LIKE),
		@Column(name="yh_fname", attrName="yhFname", label="优惠副名称"),
		@Column(name="yh_img", attrName="yhImg", label="优惠图片"),
		@Column(name="yh_mj", attrName="yhMj", label="满多少减多少"),
		@Column(name="yh_je", attrName="yhJe", label="优惠金额"),
		@Column(name="yh_zk", attrName="yhZk", label="优惠折扣"),
		@Column(name="yh_fw", attrName="yhFw", label="优惠范围"),
		@Column(name="yh_sxlx", attrName="yhSxlx", label="失效性质  1固定日期 2 日"),
		@Column(name="yh_rsx", attrName="yhRsx", label="日失效"),
		@Column(name="yh_start", attrName="yhStart", label="优惠开始"),
		@Column(name="yh_end", attrName="yhEnd", label="优惠结束"),
		@Column(name = "sxj", attrName = "sxj", label = "状态 1 上架 2 下架"),
		@Column(name = "status", attrName = "status", label = "状态 1 上架 2 下架"),
		@Column(name = "user_type", attrName = "userType", label = "用户类型"),
	}, orderBy="a.id DESC"
)
public class SpYhq extends DataEntity<SpYhq> {

	public static final String YHQ_SXLX_GD = "1";     //固定日期失效
	public static final String YHQ_SXLX_RSX = "2";    // 多少日失效

	public static final String YHQ_TYPE_DJQ = "1";    //代金券
	public static final String YHQ_TYPE_MJQ = "2";    //满减券

	public static final String YHQ_SJ = "1";    // 上架

	private static final long serialVersionUID = 1L;
	private String yhBh;		// 优惠编号
	private String type;		// 类型
	private String yhName;		// 优惠名称
	private String yhFname;		// 优惠副名称
	private String yhImg;		// 优惠图片
	private Double yhMj;		// 满多少减多少
	private Double yhJe;		// 优惠金额
	private Integer yhZk;		// 优惠折扣
	private String yhFw;		// 优惠范围
	private String yhSxlx;		// 失效性质  1固定日期 2 日
	private String yhRsx;		// 日失效
	private Date yhStart;		// 优惠开始
	private Date yhEnd;		// 优惠结束
	private String sxj;    //
	private String userType; //用户类型
	
	public SpYhq() {
		this(null);
	}

	public SpYhq(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="优惠编号长度不能超过 255 个字符")
	public String getYhBh() {
		return yhBh;
	}

	public void setYhBh(String yhBh) {
		this.yhBh = yhBh;
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

	public Double getYhMj() {
		return yhMj;
	}

	public void setYhMj(Double yhMj) {
		this.yhMj = yhMj;
	}

	public Double getYhJe() {
		return yhJe;
	}

	public void setYhJe(Double yhJe) {
		this.yhJe = yhJe;
	}
	
	public Integer getYhZk() {
		return yhZk;
	}

	public void setYhZk(Integer yhZk) {
		this.yhZk = yhZk;
	}
	
	@Length(min=0, max=1, message="优惠范围长度不能超过 1 个字符")
	public String getYhFw() {
		return yhFw;
	}

	public void setYhFw(String yhFw) {
		this.yhFw = yhFw;
	}
	
	@Length(min=0, max=1, message="失效性质  1固定日期 2 日长度不能超过 1 个字符")
	public String getYhSxlx() {
		return yhSxlx;
	}

	public void setYhSxlx(String yhSxlx) {
		this.yhSxlx = yhSxlx;
	}
	
	@Length(min=0, max=255, message="日失效长度不能超过 255 个字符")
	public String getYhRsx() {
		return yhRsx;
	}

	public void setYhRsx(String yhRsx) {
		this.yhRsx = yhRsx;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getYhStart() {
		return yhStart;
	}

	public void setYhStart(Date yhStart) {
		this.yhStart = yhStart;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getYhEnd() {
		return yhEnd;
	}

	public void setYhEnd(Date yhEnd) {
		this.yhEnd = yhEnd;
	}

	public String getSxj() {
		return sxj;
	}

	public void setSxj(String sxj) {
		this.sxj = sxj;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}