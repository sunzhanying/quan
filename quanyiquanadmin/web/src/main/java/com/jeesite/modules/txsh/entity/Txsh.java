/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.txsh.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 提现审核Entity
 * @author 马晓亮
 * @version 2020-03-31
 */
@Table(name="a_txsh", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="客户id"),
		@Column(name="zt", attrName="zt", label="审核状态 1 结算中  2 已结算 3 批量审核不通过 4 已中止 5 程序打款失败"),
		@Column(name="txje", attrName="txje", label="体现金额"),
		@Column(name="order_id", attrName="orderId", label="相关订单"),
		@Column(name="type", attrName="type", label="收益类型"),
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
	}, orderBy="a.update_date DESC"
)
public class Txsh extends DataEntity<Txsh> {
	public static final String TX_STATUS_SQZ = "1";   //结算中
	public static final String TX_STATUS_TG = "2";   //已结算
	public static final String TX_STATUS_SB = "3";   //批量审核不通过（收益扣除）卖方收益查询页面有操作
	public static final String TX_STATUS_ZZ = "4";   //已中止（人为操作）
	public static final String TX_STATUS_FAIL = "5";   //程序打款失败

	public static final String TX_TYPE_SELL = "1";   //卖家收益
	public static final String TX_TYPE_BUY = "2";   //买家收益

	private static final long serialVersionUID = 1L;
	private String khid;		// 客户id
	private String zt;		// 审核状态 1 审核中  2 审核通过 3 审核不通过
	private Double txje;		// 体现金额
	private String orderId;   //相关订单
	private String startDate;   //开始时间
	private String endDate;   //结束时间
	private String type;   //收益类型

	private KhXx khXx;
	
	public Txsh() {
		this(null);
	}

	public Txsh(String id){
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
}