package com.jeesite.modules.sale.entity;

import java.io.Serializable;

/**
 * 二级分销
 */
public class SaleDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String khid;		// 客户id
	private String parentOne;
	private String parentTwo;
	private String childOne;
	private String childTwo;

	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}

	public String getParentOne() {
		return parentOne;
	}

	public void setParentOne(String parentOne) {
		this.parentOne = parentOne;
	}

	public String getParentTwo() {
		return parentTwo;
	}

	public void setParentTwo(String parentTwo) {
		this.parentTwo = parentTwo;
	}

	public String getChildOne() {
		return childOne;
	}

	public void setChildOne(String childOne) {
		this.childOne = childOne;
	}

	public String getChildTwo() {
		return childTwo;
	}

	public void setChildTwo(String childTwo) {
		this.childTwo = childTwo;
	}

}