/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.information.entity.zx;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 内容资讯Entity
 * @author liqingfeng
 * @version 2019-08-07
 */
@Table(name="t_nr_zx", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="type", attrName="type", label="类别"),
		@Column(name="title", attrName="title", label="标题", queryType=QueryType.LIKE),
		@Column(name="ftitle", attrName="ftitle", label="副标题"),
		@Column(name="source", attrName="source", label="来源"),
		@Column(name="img", attrName="img", label="图片"),
		@Column(name="ydl", attrName="ydl", label="阅读数量"),
		@Column(name="sy", attrName="sy", label="适用"),
		@Column(name="sy_nj", attrName="syNj", label="适用年级id"),
		@Column(name="sy_zy", attrName="syZy", label="适用专业id"),
		@Column(name="sy_yx", attrName="syYx", label="适用院校"),
		@Column(name="details", attrName="details", label="详情"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="auditing_id", attrName="auditingId", label="审核人ID"),
		@Column(name="auditing_rq", attrName="auditingRq", label="auditing_rq"),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
		@Column(name="qrcode", attrName="qrcode", label="二维码地址"),
		@Column(name="lj_type", attrName="ljType", label="链接类型 1 内链 2 外链"),
		@Column(name="id_order", attrName="idOrder", label="是", comment="是：1 否：0"),
		@Column(name="lj_url", attrName="ljUrl", label="外链链接地址"),
		@Column(name="sy_zx", attrName="syZx", label="适用中学"),
	}, orderBy="a.update_date DESC"
)
public class ZiXun extends DataEntity<ZiXun> {

	public static final String LGTYPE_INSIDE = "1";
	public static final String LGTYPE_OUTSIDE= "2";
	private static final long serialVersionUID = 1L;
	private String type;		// 类别
	private String title;		// 标题
	private String ftitle;		// 副标题
	private String source;		// 来源
	private String img;		// 图片
	private Long ydl;		// 阅读数量
	private String sy;		// 适用
	private String syNj;		// 适用年级id
	private String syZy;		// 适用专业id
	private String syYx;		// 适用院校
	private String details;		// 详情
	private String auditingId;		// 审核人ID
	private Date auditingRq;		// auditing_rq
	private String delFlag;		// 删除标记
	private String qrcode;		// 二维码地址
	private Integer ljType;		// 链接类型 1 内链 2 外链
	private Integer idOrder;		// 是：1 否：0
	private String ljUrl;		// 外链链接地址
	private String syZx;		// 适用中学
	
	public ZiXun() {
		this(null);
	}

	public ZiXun(String id){
		super(id);
	}
	
	@Length(min=0, max=2, message="类别长度不能超过 2 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NotBlank(message="标题不能为空")
	@Length(min=0, max=300, message="标题长度不能超过 300 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank(message="副标题不能为空")
	@Length(min=0, max=300, message="副标题长度不能超过 300 个字符")
	public String getFtitle() {
		return ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}
	
	@Length(min=0, max=300, message="来源长度不能超过 300 个字符")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	@NotBlank(message="图片不能为空")
	@Length(min=0, max=500, message="图片长度不能超过 500 个字符")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public Long getYdl() {
		return ydl;
	}

	public void setYdl(Long ydl) {
		this.ydl = ydl;
	}
	
	@Length(min=0, max=255, message="适用长度不能超过 255 个字符")
	public String getSy() {
		return sy;
	}

	public void setSy(String sy) {
		this.sy = sy;
	}
	
	public String getSyNj() {
		return syNj;
	}

	public void setSyNj(String syNj) {
		this.syNj = syNj;
	}
	
	public String getSyZy() {
		return syZy;
	}

	public void setSyZy(String syZy) {
		this.syZy = syZy;
	}
	
	public String getSyYx() {
		return syYx;
	}

	public void setSyYx(String syYx) {
		this.syYx = syYx;
	}
	
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	@Length(min=0, max=64, message="审核人ID长度不能超过 64 个字符")
	public String getAuditingId() {
		return auditingId;
	}

	public void setAuditingId(String auditingId) {
		this.auditingId = auditingId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuditingRq() {
		return auditingRq;
	}

	public void setAuditingRq(Date auditingRq) {
		this.auditingRq = auditingRq;
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
	
	public Integer getLjType() {
		return ljType;
	}

	public void setLjType(Integer ljType) {
		this.ljType = ljType;
	}
	
	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}
	
	@Length(min=0, max=300, message="外链链接地址长度不能超过 300 个字符")
	public String getLjUrl() {
		return ljUrl;
	}

	public void setLjUrl(String ljUrl) {
		this.ljUrl = ljUrl;
	}
	
	public String getSyZx() {
		return syZx;
	}

	public void setSyZx(String syZx) {
		this.syZx = syZx;
	}
	
}