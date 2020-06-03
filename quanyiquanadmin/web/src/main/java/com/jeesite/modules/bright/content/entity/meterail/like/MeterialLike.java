/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.entity.meterail.like;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 关注素材表Entity
 * @author liqingfeng
 * @version 2019-07-18
 */
@Table(name="meterial_like", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="meterial_id", attrName="meterialId", label="meterial_id"),
		@Column(name="kh_id", attrName="khId", label="kh_id"),
		@Column(name="status", attrName="status", label="状态", comment="状态（0正常 1删除 2停用）", isUpdate=false),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
	}, orderBy="a.update_date DESC"
)
public class MeterialLike extends DataEntity<MeterialLike> {

	public static final String  STATUS_DISLIKE = "0"; //不喜欢状态
	public static final String  STATUS_LIKE = "1"; //喜欢状态
	private static final long serialVersionUID = 1L;
	private String meterialId;		// meterial_id
	private String khId;		// kh_id
	
	public MeterialLike() {
		this(null);
	}

	public MeterialLike(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="meterial_id长度不能超过 64 个字符")
	public String getMeterialId() {
		return meterialId;
	}

	public void setMeterialId(String meterialId) {
		this.meterialId = meterialId;
	}
	
	@Length(min=0, max=64, message="kh_id长度不能超过 64 个字符")
	public String getKhId() {
		return khId;
	}

	public void setKhId(String khId) {
		this.khId = khId;
	}
	
}