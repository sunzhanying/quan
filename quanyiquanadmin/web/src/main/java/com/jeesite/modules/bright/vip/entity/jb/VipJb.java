/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.vip.entity.jb;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 会员级别Entity
 * @author 马晓亮
 * @version 2019-06-26
 */
@Table(name="t_vip_jb", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="级别名称", queryType=QueryType.LIKE),
		@Column(name="ms", attrName="ms", label="描述", isQuery=false),
		@Column(name="px", attrName="px", label="排序", isQuery=false),
	}, orderBy="a.px asc"
)
public class VipJb extends DataEntity<VipJb> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 级别名称
	private String ms;		// 描述
	private Long px;		// 排序
	
	public VipJb() {
		this(null);
	}

	public VipJb(String id){
		super(id);
	}
	
	@NotBlank(message="级别名称不能为空")
	@Length(min=0, max=255, message="级别名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="描述长度不能超过 255 个字符")
	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}
	
	@NotNull(message="排序不能为空")
	public Long getPx() {
		return px;
	}

	public void setPx(Long px) {
		this.px = px;
	}
	
}