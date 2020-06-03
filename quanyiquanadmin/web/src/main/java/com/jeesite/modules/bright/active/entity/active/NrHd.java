/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.active.entity.active;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 活动Entity
 * @author 李金辉
 * @version 2019-08-02
 */
@Table(name="t_nr_hd", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="title", attrName="title", label="主题", queryType=QueryType.LIKE),
		@Column(name="start_date", attrName="startDate", label="开始时间"),
		@Column(name="end_date", attrName="endDate", label="结束时间"),
		@Column(name="addree", attrName="addree", label="地址"),
		@Column(name="img", attrName="img", label="图片"),
		@Column(name="hdms", attrName="hdms", label="活动描述"),
		@Column(name="spid", attrName="spid", label="商品id"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
		@Column(name="qrcode", attrName="qrcode", label="二维码地址"),
		@Column(name="is_kdh", attrName="isKdh", label="是否允许兑换"),
		@Column(name="is_kyy", attrName="isKyy", label="是否可预约"),
		@Column(name="fwlx", attrName="fwlx", label="服务类型"),
		@Column(name="qdqrcode", attrName="qdqrcode", label="签到二维码地址"),
		@Column(name="is_free", attrName="isFree", label="是否免费"),
		@Column(name="bmrs", attrName="bmrs", label="已经报名人数"),
		@Column(name="rsxz", attrName="rsxz", label="名额限制 0|不限 其它为总数限制，报名不得超限"),
	}, orderBy="a.update_date DESC"
)
public class NrHd extends DataEntity<NrHd> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 主题
	private Date startDate;		// 开始时间
	private Date endDate;		// 结束时间
	private String addree;		// 地址
	private String img;		// 图片
	private String hdms;		// 活动描述
	private String spid;		// 商品id
	private String delFlag;		// 删除标记
	private String qrcode;		// 二维码地址
	private Integer isKdh;		// 是否允许兑换
	private Integer isKyy;		// 是否可预约
	private String fwlx;		// 服务类型
	private String qdqrcode;		// 签到二维码地址
	private String isFree;		// 是否免费
	private Long bmrs;		// 已经报名人数
	private Integer rsxz;		// 名额限制 0|不限 其它为总数限制，报名不得超限
	
	public NrHd() {
		this(null);
	}

	public NrHd(String id){
		super(id);
	}
	
	@NotBlank(message="主题不能为空")
	@Length(min=0, max=400, message="主题长度不能超过 400 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始时间不能为空")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束时间不能为空")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=400, message="地址长度不能超过 400 个字符")
	public String getAddree() {
		return addree;
	}

	public void setAddree(String addree) {
		this.addree = addree;
	}
	
	@Length(min=0, max=400, message="图片长度不能超过 400 个字符")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public String getHdms() {
		return hdms;
	}

	public void setHdms(String hdms) {
		this.hdms = hdms;
	}
	
	@NotBlank(message="商品id不能为空")
	@Length(min=0, max=64, message="商品id长度不能超过 64 个字符")
	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}
	

	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	@Length(min=0, max=900, message="二维码地址长度不能超过 900 个字符")
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	public Integer getIsKdh() {
		return isKdh;
	}

	public void setIsKdh(Integer isKdh) {
		this.isKdh = isKdh;
	}
	
	public Integer getIsKyy() {
		return isKyy;
	}

	public void setIsKyy(Integer isKyy) {
		this.isKyy = isKyy;
	}
	
	@Length(min=0, max=50, message="服务类型长度不能超过 50 个字符")
	public String getFwlx() {
		return fwlx;
	}

	public void setFwlx(String fwlx) {
		this.fwlx = fwlx;
	}
	
	@Length(min=0, max=900, message="签到二维码地址长度不能超过 900 个字符")
	public String getQdqrcode() {
		return qdqrcode;
	}

	public void setQdqrcode(String qdqrcode) {
		this.qdqrcode = qdqrcode;
	}
	
	@Length(min=0, max=1, message="是否免费长度不能超过 1 个字符")
	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}
	
	public Long getBmrs() {
		return bmrs;
	}

	public void setBmrs(Long bmrs) {
		this.bmrs = bmrs;
	}
	
	public Integer getRsxz() {
		return rsxz;
	}

	public void setRsxz(Integer rsxz) {
		this.rsxz = rsxz;
	}
	
}