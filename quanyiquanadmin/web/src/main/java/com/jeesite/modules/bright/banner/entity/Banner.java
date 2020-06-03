/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.banner.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 推荐位Entity
 * @author 李金辉
 * @version 2019-07-25
 */
@Table(name="a_banner", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="img", attrName="img", label="图片"),
		@Column(name="type", attrName="type", label="类型"),
		@Column(name="link_type", attrName="linkType", label="链接类型"),
		@Column(name="link", attrName="link", label="外链链接"),
		@Column(name="relate_type", attrName="relateType", label="内链相关类型"),
		@Column(name="relate_id", attrName="relateId", label="内链相关id"),
		@Column(name="sort", attrName="sort", label="排序"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.sort, a.update_date DESC"
)
public class Banner extends DataEntity<Banner> {
	
	private static final long serialVersionUID = 1L;
	private String img;		// 图片
	private String type;		// 类型
	private String linkType;		// 链接类型
	private String link;		// 外链链接
	private String relateType;		// 内链相关类型
	private String relateId;		// 内链相关id
	private Integer sort;		// 排序

	public String getRelateType() {
		return relateType;
	}

	public void setRelateType(String relateType) {
		this.relateType = relateType;
	}

	public Banner() {
		this(null);
	}

	public Banner(String id){
		super(id);
	}
	
	@NotBlank(message="图片不能为空")
	@Length(min=0, max=255, message="图片长度不能超过 255 个字符")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@NotBlank(message="类型不能为空")
	@Length(min=0, max=1, message="类型长度不能超过 1 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@NotBlank(message="链接类型不能为空")
	@Length(min=0, max=100, message="链接类型长度不能超过 100 个字符")
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@Length(min=0, max=64, message="内链相关id长度不能超过 64 个字符")
	public String getRelateId() {
		return relateId;
	}

	public void setRelateId(String relateId) {
		this.relateId = relateId;
	}
	
	@NotNull(message="排序不能为空")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}