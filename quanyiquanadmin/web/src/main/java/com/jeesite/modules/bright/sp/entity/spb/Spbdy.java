/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.entity.spb;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 商品包定义Entity
 * @author 马晓亮
 * @version 2019-07-01
 */
@Table(name="t_sp_spbdy", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="spbmc", attrName="spbmc", label="商品包名称", queryType=QueryType.LIKE),
		@Column(name="sxj", attrName="sxj", label="1上架 2 下架"),
		@Column(name="spbpic", attrName="spbpic", label="商品包图片"),
		@Column(name="ishyqy", attrName="ishyqy", label="是否是会员权益商品 1 是  2 否"),
		@Column(name="spbdm", attrName="spbdm", label="商品包代码"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="fhyj", attrName="fhyj", label="非会员价"),
		@Column(name="hyj", attrName="hyj", label="会员价"),
		@Column(name="jfj", attrName="jfj", label="积分价"),
		@Column(name="qrcode", attrName="qrcode", label="二维码地址"),
	}, orderBy="a.update_date DESC"
)
public class Spbdy extends DataEntity<Spbdy> {
	
	private static final long serialVersionUID = 1L;
	private String spbmc;		// 商品包名称
	private String sxj;		// 1上架 2 下架
	private String spbpic;		// 商品包图片
	private String ishyqy;		// 是否是会员权益商品 1 是  2 否
	private String spbdm;		// 商品包代码
	private Double fhyj;		// 非会员价
	private Double hyj;		// 会员价
	private Double jfj;		// 积分价
	private String qrcode;		// 二维码地址
	private List<SpbYhq> spbYhqList = ListUtils.newArrayList();		// 子表列表
	private List<Spbmx> spbmxList = ListUtils.newArrayList();		// 子表列表
	
	public Spbdy() {
		this(null);
	}

	public Spbdy(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="商品包名称长度不能超过 255 个字符")
	public String getSpbmc() {
		return spbmc;
	}

	public void setSpbmc(String spbmc) {
		this.spbmc = spbmc;
	}
	
	@NotBlank(message="1上架 2 下架不能为空")
	@Length(min=0, max=1, message="1上架 2 下架长度不能超过 1 个字符")
	public String getSxj() {
		return sxj;
	}

	public void setSxj(String sxj) {
		this.sxj = sxj;
	}
	
	@Length(min=0, max=255, message="商品包图片长度不能超过 255 个字符")
	public String getSpbpic() {
		return spbpic;
	}

	public void setSpbpic(String spbpic) {
		this.spbpic = spbpic;
	}
	
	@Length(min=0, max=1, message="是否是会员权益商品 1 是  2 否长度不能超过 1 个字符")
	public String getIshyqy() {
		return ishyqy;
	}

	public void setIshyqy(String ishyqy) {
		this.ishyqy = ishyqy;
	}
	
	@Length(min=0, max=255, message="商品包代码长度不能超过 255 个字符")
	public String getSpbdm() {
		return spbdm;
	}

	public void setSpbdm(String spbdm) {
		this.spbdm = spbdm;
	}
	
	public Double getFhyj() {
		return fhyj;
	}

	public void setFhyj(Double fhyj) {
		this.fhyj = fhyj;
	}
	
	public Double getHyj() {
		return hyj;
	}

	public void setHyj(Double hyj) {
		this.hyj = hyj;
	}
	
	public Double getJfj() {
		return jfj;
	}

	public void setJfj(Double jfj) {
		this.jfj = jfj;
	}
	
	@Length(min=0, max=900, message="二维码地址长度不能超过 900 个字符")
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	public List<SpbYhq> getSpbYhqList() {
		return spbYhqList;
	}

	public void setSpbYhqList(List<SpbYhq> spbYhqList) {
		this.spbYhqList = spbYhqList;
	}
	
	public List<Spbmx> getSpbmxList() {
		return spbmxList;
	}

	public void setSpbmxList(List<Spbmx> spbmxList) {
		this.spbmxList = spbmxList;
	}
	
}