/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.entity.meterail.visited;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 访问日志表Entity
 * @author liqingfeng
 * @version 2019-07-17
 */
@Table(name="meterial_visited_log", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="meterial_id", attrName="meterialId", label="meterial_id"),
		@Column(name="kh_id", attrName="khId", label="kh_id"),
		@Column(name="source_id", attrName="sourceId", label="source_id"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.update_date DESC"
)
public class MeterialVisitedLog extends DataEntity<MeterialVisitedLog> {
	
	private static final long serialVersionUID = 1L;
	private String meterialId;		// meterial_id
	private String khId;		// kh_id
	private String sourceId;		// source_id
	private String delFlag;		// 删除标记
	
	public MeterialVisitedLog() {
		this(null);
	}

	public MeterialVisitedLog(String id){
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
	
	@Length(min=0, max=64, message="source_id长度不能超过 64 个字符")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}