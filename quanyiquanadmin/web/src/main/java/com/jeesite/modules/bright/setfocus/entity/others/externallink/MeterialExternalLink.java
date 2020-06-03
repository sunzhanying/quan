/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.entity.others.externallink;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 外部链接配置Entity
 * @author liqingfeng
 * @version 2019-07-12
 */
@Table(name="meterial_external_link", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="target_link", attrName="targetLink", label="跳转链接"),
		@Column(name="source_id", attrName="sourceId", label="来源id"),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.update_date DESC"
)
public class MeterialExternalLink extends DataEntity<MeterialExternalLink> {
	
	private static final long serialVersionUID = 1L;
	private String targetLink;		// 跳转链接
	private String sourceId;		// 来源id
	private String name;		// 名称
	private String delFlag;		// 删除标记
	
	public MeterialExternalLink() {
		this(null);
	}

	public MeterialExternalLink(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="跳转链接长度不能超过 255 个字符")
	public String getTargetLink() {
		return targetLink;
	}

	public void setTargetLink(String targetLink) {
		this.targetLink = targetLink;
	}
	
	@Length(min=0, max=64, message="来源id长度不能超过 64 个字符")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	@Length(min=0, max=255, message="名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}