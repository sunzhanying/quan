/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.entity.source;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 来源表Entity
 * @author liqingfeng
 * @version 2019-07-11
 */
@Table(name="source", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="来源渠道", queryType=QueryType.LIKE),
		@Column(name="content", attrName="content", label="来源内容"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.update_date DESC"
)
public class Source extends DataEntity<Source> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 来源渠道
	private String content;		// 来源内容
	private String delFlag;		// 删除标记
	
	public Source() {
		this(null);
	}

	public Source(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="来源渠道长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1000, message="来源内容长度不能超过 1000 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}