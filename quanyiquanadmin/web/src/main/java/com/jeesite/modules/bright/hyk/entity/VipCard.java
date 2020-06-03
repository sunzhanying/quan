/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.hyk.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 会员卡Entity
 * @author 马晓亮
 * @version 2019-08-15
 */
@Table(name="t_vip_card", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="会员卡名称", queryType=QueryType.LIKE),
		@Column(name="fname", attrName="fname", label="副名称"),
		@Column(name="img", attrName="img", label="图片"),
		@Column(name="qimg", attrName="qimg", label="图片"),
		@Column(name="dimg", attrName="dimg", label="图片"),
		@Column(name="mimg", attrName="mimg", label="图片"),
		@Column(name="je", attrName="je", label="金额"),
		@Column(name="zje", attrName="zje", label="金额"),
		@Column(name="sxj", attrName="sxj", label="状态"),
		@Column(name="status", attrName="status", label="删除标记", isUpdate=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
	}, orderBy="a.je DESC"
)
public class VipCard extends DataEntity<VipCard> {

	public static final String VIP_CARD_SJ = "1";       //上架
	public static final String VIP_CARD_XJ = "2";        //下架

	private static final long serialVersionUID = 1L;
	private String name;		// 会员卡名称
	private String fname;		// 副名称
	private String img;		// 图片
	private String qimg;		// 图片
	private String dimg;		// 图片
	private String mimg;		// 图片
	private Double je;		// 金额
	private String zje;		// 金额
	private String sxj;		// 状态
	
	public VipCard() {
		this(null);
	}

	public VipCard(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="会员卡名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="副名称长度不能超过 255 个字符")
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	
	@Length(min=0, max=300, message="图片长度不能超过 300 个字符")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public Double getJe() {
		return je;
	}

	public void setJe(Double je) {
		this.je = je;
	}
	
	@Length(min=0, max=1, message="状态长度不能超过 1 个字符")
	public String getSxj() {
		return sxj;
	}

	public void setSxj(String sxj) {
		this.sxj = sxj;
	}

	public String getQimg() {
		return qimg;
	}

	public void setQimg(String qimg) {
		this.qimg = qimg;
	}

	public String getDimg() {
		return dimg;
	}

	public void setDimg(String dimg) {
		this.dimg = dimg;
	}

	public String getMimg() {
		return mimg;
	}

	public void setMimg(String mimg) {
		this.mimg = mimg;
	}

	public String getZje() {
		return zje;
	}

	public void setZje(String zje) {
		this.zje = zje;
	}
}