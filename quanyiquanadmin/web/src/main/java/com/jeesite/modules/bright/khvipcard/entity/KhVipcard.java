/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khvipcard.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户会员卡Entity
 * @author 马晓亮
 * @version 2019-08-16
 */
@Table(name="t_kh_vipcard", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="客户ID"),
		@Column(name="vip_card_id", attrName="vipCardId", label="会员卡id"),
		@Column(name="card_name", attrName="cardName", label="卡名称", queryType=QueryType.LIKE),
		@Column(name="card_fname", attrName="cardFname", label="卡副名称"),
		@Column(name="je", attrName="je", label="金额"),
		@Column(name="zje", attrName="zje", label="金额"),
		@Column(name="ye", attrName="ye", label="余额"),
		@Column(name="img", attrName="img", label="图片"),
		@Column(name="mimg", attrName="mimg", label="图片"),
		@Column(name="qimg", attrName="qimg", label="图片"),
		@Column(name="dimg", attrName="dimg", label="图片"),
		@Column(name="zt", attrName="zt", label="1，待支付 2 已取消 3已支付 4已完成 5已入仓"),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
	}, joinTable={
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=KhXx.class, attrName="khXx", alias="u12",
				on="u12.id = a.khid", columns={
				@Column(name="wxnc", label="微信昵称", isQuery=false),
		}),

},orderBy="a.id DESC"
)
public class KhVipcard extends DataEntity<KhVipcard> {

	//支付状态
	public static final String VIP_CARD_PAY_DF = "1";    //待支付
	public static final String VIP_CARD_PAY_qx = "2";    //取消支付
	public static final String VIP_CARD_PAY_WC = "3";    //支付完成

	private static final long serialVersionUID = 1L;
	private String khid;		// 客户ID
	private String vipCardId;		// 会员卡id
	private String cardName;		// 卡名称
	private String cardFname;		// 卡副名称
	private Double je;		// 金额
	private Double zje;		// 金额
	private Double ye;		// 余额
	private String zt;		// 支付状态 1，待支付 2 已取消 3已支付 4已完成 5已入仓
	private String img;		// 图片
	private String qimg;		// 图片
	private String dimg;		// 图片
	private String mimg;		// 图片

	private KhXx khXx;             //用户信息

	public KhVipcard() {
		this(null);
	}

	public KhVipcard(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="客户ID长度不能超过 64 个字符")
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
	
	@Length(min=0, max=255, message="卡名称长度不能超过 255 个字符")
	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
	@Length(min=0, max=255, message="卡副名称长度不能超过 255 个字符")
	public String getCardFname() {
		return cardFname;
	}

	public void setCardFname(String cardFname) {
		this.cardFname = cardFname;
	}
	
	public Double getJe() {
		return je;
	}

	public void setJe(Double je) {
		this.je = je;
	}

	public Double getYe() {
		return ye;
	}

	public void setYe(Double ye) {
		this.ye = ye;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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

	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}

	public Double getZje() {
		return zje;
	}

	public void setZje(Double zje) {
		this.zje = zje;
	}

	public String getMimg() {
		return mimg;
	}

	public void setMimg(String mimg) {
		this.mimg = mimg;
	}
}