/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.wx.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 微信配置Entity
 * @author 李金辉
 * @version 2019-07-26
 */
@Table(name="a_wx_config", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="project", attrName="project", label="项目"),
		@Column(name="appid", attrName="appid", label="appid"),
		@Column(name="secret", attrName="secret", label="secret"),
		@Column(name="mch_id", attrName="mchId", label="商户id"),
		@Column(name="mch_key", attrName="mchKey", label="商户秘钥"),
		@Column(name="notify_url", attrName="notifyUrl", label="回调地址"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class WxConfig extends DataEntity<WxConfig> {
	
	private static final long serialVersionUID = 1L;
	private String project;		// 项目
	private String appid;		// appid
	private String secret;		// secret
	private String mchId;		// 商户id
	private String mchKey;		// 商户秘钥
	private String notifyUrl;		// 回调地址
	
	public WxConfig() {
		this(null);
	}

	public WxConfig(String id){
		super(id);
	}
	
	@NotBlank(message="项目不能为空")
	@Length(min=0, max=100, message="项目长度不能超过 100 个字符")
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
	
	@NotBlank(message="appid不能为空")
	@Length(min=0, max=100, message="appid长度不能超过 100 个字符")
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	@NotBlank(message="secret不能为空")
	@Length(min=0, max=100, message="secret长度不能超过 100 个字符")
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	@NotBlank(message="商户id不能为空")
	@Length(min=0, max=255, message="商户id长度不能超过 255 个字符")
	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	
	@NotBlank(message="商户秘钥不能为空")
	@Length(min=0, max=255, message="商户秘钥长度不能超过 255 个字符")
	public String getMchKey() {
		return mchKey;
	}

	public void setMchKey(String mchKey) {
		this.mchKey = mchKey;
	}
	
	@Length(min=0, max=255, message="回调地址长度不能超过 255 个字符")
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
}