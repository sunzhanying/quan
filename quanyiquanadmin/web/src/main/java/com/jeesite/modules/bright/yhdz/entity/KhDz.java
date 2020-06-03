/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.yhdz.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;

/**
 * 用户地址表Entity
 * @author 马晓亮
 * @version 2019-07-12
 */
@Table(name="t_kh_dz", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="user_id", attrName="userId", label="user_id"),
		@Column(name="name", attrName="name", label="名字", isQuery=false),
		@Column(name="phone", attrName="phone", label="电话", isQuery=false),
		@Column(name="sex", attrName="sex", label="1男2女", isQuery=false),
		@Column(name="province", attrName="province", label="省份", isQuery=false),
		@Column(name="city", attrName="city", label="市", isQuery=false),
		@Column(name="area", attrName="area", label="省市区域", isQuery=false),
		@Column(name="address", attrName="address", label="详细地址", isQuery=false),
		@Column(name="is_default", attrName="isDefault", label="是否默认地址1是，0否"),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="bq", attrName="bq", label="标签"),
		@Column(name="status", attrName="status", label="状态", comment="状态（0正常 1删除 2停用）", isUpdate=false),
	}, orderBy="a.is_default DESC"
)
public class KhDz extends DataEntity<KhDz> {
	
	private static final long serialVersionUID = 1L;

	//默认地址
	public static final String ISDEFAULF_YES = "1";
	public static final String ISDEFAULF_NO = "0";

	private String userId;		// user_id
	private String name;		// 名字
	private String phone;		// 电话
	private Integer sex;		// 1男2女
	private String province;		// 省份
	private String city;		// 市
	private String area;		// 省市区域
	private String address;		// 详细地址
	private String isDefault;		// 是否默认地址1是，0否
	private String bq;		// 标签
	
	public KhDz() {
		this(null);
	}

	public KhDz(String id){
		super(id);
	}
	
	@Length(min=0, max=100, message="user_id长度不能超过 100 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=255, message="名字长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="省份长度不能超过 255 个字符")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=255, message="市长度不能超过 255 个字符")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=255, message="省市区域长度不能超过 255 个字符")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=255, message="详细地址长度不能超过 255 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	@Length(min=0, max=50, message="标签长度不能超过 50 个字符")
	public String getBq() {
		return bq;
	}

	public void setBq(String bq) {
		this.bq = bq;
	}
	
}