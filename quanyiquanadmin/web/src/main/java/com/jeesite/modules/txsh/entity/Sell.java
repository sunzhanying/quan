/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.txsh.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.hibernate.validator.constraints.Length;

/**
 * 分销收益
 */
@Table(name="a_sell", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="buyid", attrName="buyid", label="买家客户id"),
		@Column(name="khid", attrName="khid", label="收益客户id"),
		@Column(name="type", attrName="type", label="1:1级上家收益  2:2级上家收益"),
		@Column(name="khid_show_one", attrName="khidShowOne", label="如果是2级收益，改字段存1级上家id"),
		@Column(name="zt", attrName="zt", label="审核状态 1 结算中  2 已结算 3 批量审核不通过 4 已中止 5 程序打款失败"),
		@Column(name="txje", attrName="txje", label="体现金额"),
		@Column(name="order_id", attrName="orderId", label="相关订单"),
		@Column(includeEntity=DataEntity.class),
	},joinTable={
		@JoinTable(type= Type.LEFT_JOIN, entity=KhXx.class, attrName="khXx", alias="k",
				on="k.id = a.khid",
				columns={
						@Column(name="wxtx", attrName="wxtx", label="微信头像"),
						@Column(name="wxnc", attrName="wxnc", label="微信昵称"),
						@Column(name="xm", attrName="xm", label="姓名", queryType = QueryType.LIKE),
						@Column(name="sj", attrName="sj", label="手机", queryType = QueryType.LIKE),
				}),
		@JoinTable(type= Type.LEFT_JOIN, entity=KhXx.class, attrName="khBuy", alias="m",
				on="m.id = a.buyid",
				columns={
						@Column(name="wxtx", attrName="wxtx", label="微信头像"),
						@Column(name="wxnc", attrName="wxnc", label="微信昵称"),
						@Column(name="xm", attrName="xm", label="姓名", queryType = QueryType.LIKE),
						@Column(name="sj", attrName="sj", label="手机", queryType = QueryType.LIKE),
				}),
		@JoinTable(type= Type.LEFT_JOIN, entity=KhXx.class, attrName="khParent", alias="n",
				on="n.id = a.khid_show_one",
				columns={
						@Column(name="wxtx", attrName="wxtx", label="微信头像"),
						@Column(name="wxnc", attrName="wxnc", label="微信昵称"),
						@Column(name="xm", attrName="xm", label="姓名", queryType = QueryType.LIKE),
						@Column(name="sj", attrName="sj", label="手机", queryType = QueryType.LIKE),
				}),
	}, orderBy="a.update_date DESC"
)
public class Sell extends DataEntity<Sell> {
	public static final String SELL_STATUS_SQZ = "1";   //结算中
	public static final String SELL_STATUS_TG = "2";   //已结算
	public static final String SELL_STATUS_SB = "3";   //批量审核不通过（收益扣除）卖方收益查询页面有操作
	public static final String SELL_STATUS_ZZ = "4";   //已中止（人为操作）
	public static final String SELL_STATUS_FAIL = "5";   //程序打款失败

	public static final String SELL_TYPE_ONE = "1";   //1级收益
	public static final String SELL_TYPE_TWO = "2";   //2级收益

	private static final long serialVersionUID = 1L;
	private String buyid;		// 买家客户id
	private String khid;		// 收益客户id
	private String type;   //1:1级上家收益  2:2级上家收益
	private String khidShowOne;
	private String zt;		// 审核状态 1 审核中  2 审核通过 3 审核不通过
	private Double txje;		// 体现金额
	private String orderId;   //相关订单
	private String startDate;   //开始时间
	private String endDate;   //结束时间


	private KhXx khXx;
	private KhXx khBuy;
	private KhXx khParent;
	public Sell() {
		this(null);
	}

	public Sell(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="客户id长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=1, message="审核状态 1 审核中  2 审核通过 3 审核不通过长度不能超过 1 个字符")
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
	
	public Double getTxje() {
		return txje;
	}

	public void setTxje(Double txje) {
		this.txje = txje;
	}

	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBuyid() {
		return buyid;
	}

	public void setBuyid(String buyid) {
		this.buyid = buyid;
	}

	public String getKhidShowOne() {
		return khidShowOne;
	}

	public void setKhidShowOne(String khidShowOne) {
		this.khidShowOne = khidShowOne;
	}

	public KhXx getKhBuy() {
		return khBuy;
	}

	public void setKhBuy(KhXx khBuy) {
		this.khBuy = khBuy;
	}

	public KhXx getKhParent() {
		return khParent;
	}

	public void setKhParent(KhXx khParent) {
		this.khParent = khParent;
	}
}