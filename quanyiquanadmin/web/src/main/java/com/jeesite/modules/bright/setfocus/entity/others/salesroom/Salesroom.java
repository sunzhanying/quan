/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.entity.others.salesroom;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 门店设置/传播渠道设置Entity
 * @author liqingfeng
 * @version 2019-08-23
 */
@Table(name="salesroom", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="门店名称", queryType=QueryType.LIKE),
		@Column(name="business_hours", attrName="businessHours", label="营业时间", queryType=QueryType.LIKE),
		@Column(name="address", attrName="address", label="餐厅地址", queryType=QueryType.LIKE),
		@Column(name="phone", attrName="phone", label="phone"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记", isQuery=false),
		@Column(name="isdefault", attrName="isdefault", label="是否默认", isQuery=false),
	}, orderBy="a.update_date DESC"
)
public class Salesroom extends DataEntity<Salesroom> {

	public static final String ISDEFAULT_YES  = "1";
	public static final String ISDEFAULT_NO = "0";
	private static final long serialVersionUID = 1L;
	private String name;		// 门店名称
	private String businessHours;		// 营业时间
	private String address;		// 餐厅地址
	private String phone;		// phone
	private String delFlag;		// 删除标记
	private String isdefault;      //是否默认
	public Salesroom() {
		this(null);
	}

	public Salesroom(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="门店名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="营业时间长度不能超过 255 个字符")
	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}
	
	@Length(min=0, max=255, message="餐厅地址长度不能超过 255 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=255, message="phone长度不能超过 255 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}
}