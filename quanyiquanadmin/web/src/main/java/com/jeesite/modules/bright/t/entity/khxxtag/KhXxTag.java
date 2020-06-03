/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.entity.khxxtag;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 客户阅读标签Entity
 * @author 李金辉
 * @version 2019-07-15
 */
@Table(name="t_kh_xx_tag", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="客户id"),
		@Column(name="tag_id", attrName="tagId", label="标签id"),
		@Column(name="tag_name", attrName="tagName", label="标签名称", queryType=QueryType.LIKE),
		@Column(name="times", attrName="times", label="次数"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class KhXxTag extends DataEntity<KhXxTag> {
	
	private static final long serialVersionUID = 1L;
	private String khid;		// 客户id
	private String tagId;		// 标签id
	private String tagName;		// 标签名称
	private Integer times;		// 次数
	
	public KhXxTag() {
		this(null);
	}

	public KhXxTag(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="客户id长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=64, message="标签id长度不能超过 64 个字符")
	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	@Length(min=0, max=64, message="标签名称长度不能超过 64 个字符")
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
	
}