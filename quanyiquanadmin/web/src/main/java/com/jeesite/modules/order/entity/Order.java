/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.order.entity;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 订单Entity
 * @author 马晓亮
 * @version 2020-03-26
 */
@Table(name="t_order", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true, queryType = QueryType.LIKE),
		@Column(name="sp_id", attrName="spId", label="权益id"),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="transaction_id", attrName="transactionId", label="transaction_id"),
		@Column(name="actual_payment", attrName="actualPayment", label="实付金额"),
		@Column(name="payment", attrName="payment", label="订单金额,单位分"),
		@Column(name="pay_time", attrName="payTime", label="支付时间"),
		@Column(name="zt", attrName="zt", label="支付状态 1，待支付 2 已取消 3已支付 4已完成 5已入仓"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="sl", attrName="sl", label="数量"),
		@Column(name="jgid", attrName="jgid", label="价格id"),
		@Column(name="hsj", attrName="hsj", label="回收价"),
		@Column(name="scj", attrName="scj", label="售出价"),
	}, joinTable={
		@JoinTable(type= Type.LEFT_JOIN, entity=KhXx.class, attrName="khXx", alias="k",
				on="k.id = a.user_id",
				columns={
						@Column(name="wxtx", attrName="wxtx", label="微信头像"),
						@Column(name="wxnc", attrName="wxnc", label="微信昵称"),
						@Column(name="xm", attrName="xm", label="姓名", queryType = QueryType.LIKE),
						@Column(name="sj", attrName="sj", label="手机", queryType = QueryType.LIKE),
				}),
},orderBy="a.update_date DESC"
)
public class Order extends DataEntity<Order> {

	public static final String PAY_STATUS_DZF = "1";   //待支付
	public static final String PAY_STATUS_YQX = "2";   //已取消
	public static final String PAY_STATUS_YZF = "3";   //已支付

	private static final long serialVersionUID = 1L;
	private String spId;		// 权益id
	private String userId;		// 用户id
	private String transactionId;		// transaction_id
	private Double actualPayment;		// 实付金额
	private Double payment;		// 订单金额,单位分
	private Date payTime;		// 支付时间
	private String zt;		// 支付状态 1，待支付 2 已取消 3已支付 4已完成 5已入仓
	private Long sl;		// 数量
	private String jgid;		// 价格id
	private Double hsj;		// 回收价
	private Double scj;		// 售出价

	private KhXx khXx;   //客户信息
	private SpXx spXx;   //商品信息

	List<QyhsMx> qyhsMxList = ListUtils.newArrayList();   //权益明细
	
	public Order() {
		this(null);
	}

	public Order(String id){
		super(id);
	}
	
	@Length(min=0, max=100, message="权益id长度不能超过 100 个字符")
	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}
	
	@Length(min=0, max=100, message="用户id长度不能超过 100 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=100, message="transaction_id长度不能超过 100 个字符")
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public Double getActualPayment() {
		return actualPayment;
	}

	public void setActualPayment(Double actualPayment) {
		this.actualPayment = actualPayment;
	}
	
	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@Length(min=0, max=1, message="支付状态 1，待支付 2 已取消 3已支付 4已完成 5已入仓长度不能超过 1 个字符")
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
	
	public Long getSl() {
		return sl;
	}

	public void setSl(Long sl) {
		this.sl = sl;
	}
	
	@Length(min=0, max=64, message="价格id长度不能超过 64 个字符")
	public String getJgid() {
		return jgid;
	}

	public void setJgid(String jgid) {
		this.jgid = jgid;
	}
	
	public Double getHsj() {
		return hsj;
	}

	public void setHsj(Double hsj) {
		this.hsj = hsj;
	}
	
	public Double getScj() {
		return scj;
	}

	public void setScj(Double scj) {
		this.scj = scj;
	}

	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}

	public SpXx getSpXx() {
		return spXx;
	}

	public void setSpXx(SpXx spXx) {
		this.spXx = spXx;
	}

	public List<QyhsMx> getQyhsMxList() {
		return qyhsMxList;
	}

	public void setQyhsMxList(List<QyhsMx> qyhsMxList) {
		this.qyhsMxList = qyhsMxList;
	}
}