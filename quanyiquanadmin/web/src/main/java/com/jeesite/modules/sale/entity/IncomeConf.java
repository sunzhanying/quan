/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sale.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 二级分销配置
 */
@Table(name="t_income_conf", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="parent_level", attrName="parentLevel", label="父级数"),
		@Column(name="income_type", attrName="incomeType", label="收益方式"),
		@Column(name="money", attrName="money", label="固定金额值"),
		@Column(name="ratio", attrName="ratio", label="百分比"),
		@Column(includeEntity=DataEntity.class),
	}
)
public class IncomeConf extends DataEntity<IncomeConf> {

	private static final long serialVersionUID = 1L;
	private String parentLevel;
	private String incomeType;
	private Double money;		// 体现金额
	private Double ratio;		// 比例

	public static final String CONF_PARENT_ONE = "1";
	public static final String CONF_PARENT_TWO = "2";

	public IncomeConf() {
		this(null);
	}

	public IncomeConf(String id){
		super(id);
	}

	public String getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(String parentLevel) {
		this.parentLevel = parentLevel;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}
}