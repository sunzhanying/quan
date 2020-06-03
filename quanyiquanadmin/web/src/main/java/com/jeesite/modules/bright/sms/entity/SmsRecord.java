/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sms.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * sms_recordEntity
 * @author 马晓亮
 * @version 2019-07-25
 */
@Table(name="sms_record", alias="a", columns={
		@Column(name="id", attrName="id", label="主键id", isPK=true),
		@Column(name="platform_no", attrName="platformNo", label="平台记录号"),
		@Column(name="phone", attrName="phone", label="手机号"),
		@Column(name="code", attrName="code", label="验证码"),
		@Column(name="zt", attrName="zt", label="验证状态", comment="验证状态: 0: 待验证, 1: 已验证, 2: 已作废"),
		@Column(name="send_time", attrName="sendTime", label="发送时间"),
		@Column(name="verify_time", attrName="verifyTime", label="验证时间"),
	}, orderBy="a.id DESC"
)
public class SmsRecord extends DataEntity<SmsRecord> {

	public static final long PHONE_ZT_DYZ = 0; //待验证
	public static final long PHONE_ZT_YYZ = 1; //已验证
	public static final long PHONE_ZT_YZF = 2; //已作废

	private static final long serialVersionUID = 1L;
	private String platformNo;		// 平台记录号
	private String phone;		// 手机号
	private String code;		// 验证码
	private Long zt;		// 验证状态: 0: 待验证, 1: 已验证, 2: 已作废
	private Date sendTime;		// 发送时间
	private Date verifyTime;		// 验证时间
	
	public SmsRecord() {
		this(null);
	}

	public SmsRecord(String id){
		super(id);
	}
	
	@NotBlank(message="平台记录号不能为空")
	@Length(min=0, max=100, message="平台记录号长度不能超过 100 个字符")
	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	
	@NotBlank(message="手机号不能为空")
	@Length(min=0, max=50, message="手机号长度不能超过 50 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@NotBlank(message="验证码不能为空")
	@Length(min=0, max=10, message="验证码长度不能超过 10 个字符")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull(message="验证状态不能为空")
	public Long getZt() {
		return zt;
	}

	public void setZt(Long zt) {
		this.zt = zt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="发送时间不能为空")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}
	
}