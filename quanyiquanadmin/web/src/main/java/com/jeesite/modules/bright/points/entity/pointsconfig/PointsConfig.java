/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.points.entity.pointsconfig;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * t_jf_configEntity
 * @author 李金辉
 * @version 2019-08-14
 */
@Table(name="t_jf_config", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="mc", attrName="mc", label="名称"),
		@Column(name="lx", attrName="lx", label="方式"),
		@Column(name="sl", attrName="sl", label="数量"),
		@Column(name="bz", attrName="bz", label="说明"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class PointsConfig extends DataEntity<PointsConfig> {

	public static final String POINTSCONFIG_PAY = "4";    //支付
	
	private static final long serialVersionUID = 1L;
	private String mc;		// 名称
	private Long lx;		// 方式
	private Integer sl;		// 数量
	private String bz;		// 说明
	
	public PointsConfig() {
		this(null);
	}

	public PointsConfig(String id){
		super(id);
	}
	
	@Length(min=0, max=20, message="名称长度不能超过 20 个字符")
	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}
	
	public Long getLx() {
		return lx;
	}

	public void setLx(Long lx) {
		this.lx = lx;
	}
	
	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}
	
	@Length(min=0, max=255, message="说明长度不能超过 255 个字符")
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
}