/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.order.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 订单明细表Entity
 * @author 马晓亮
 * @version 2020-03-26
 */
@Table(name="t_order_mx", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="order_id", attrName="orderId", label="订单表ID"),
		@Column(name="qymx_id", attrName="qymxId", label="权益明细id"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class OrderMx extends DataEntity<OrderMx> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单表ID
	private String qymxId;		// 权益明细id
	
	public OrderMx() {
		this(null);
	}

	public OrderMx(String id){
		super(id);
	}
	
	@NotBlank(message="订单表ID不能为空")
	@Length(min=0, max=64, message="订单表ID长度不能超过 64 个字符")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=64, message="权益明细id长度不能超过 64 个字符")
	public String getQymxId() {
		return qymxId;
	}

	public void setQymxId(String qymxId) {
		this.qymxId = qymxId;
	}
	
}