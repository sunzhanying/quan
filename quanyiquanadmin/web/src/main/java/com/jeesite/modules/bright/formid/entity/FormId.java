/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.formid.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * a_form_idEntity
 * @author 李金辉
 * @version 2019-07-19
 */
@Table(name="a_form_id", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="form_id", attrName="formId", label="form_id"),
		@Column(name="open_id", attrName="openId", label="用户open_id"),
		@Column(name="times", attrName="times", label="剩余次数"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class FormId extends DataEntity<FormId> {
	
	private static final long serialVersionUID = 1L;
	private String formId;		// form_id
	private String openId;		// 用户open_id
	private Integer times;		// 剩余次数
	
	public FormId() {
		this(null);
	}

	public FormId(String id){
		super(id);
	}
	
	@NotBlank(message="form_id不能为空")
	@Length(min=0, max=200, message="form_id长度不能超过 200 个字符")
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	@NotBlank(message="用户open_id不能为空")
	@Length(min=0, max=100, message="用户open_id长度不能超过 100 个字符")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
	
}