/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.entity.rwmx;

import com.jeesite.modules.bright.push.entity.templet.SyMb;
import com.jeesite.modules.bright.push.entity.templet.SyMbmx;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 生涯任务明细Entity
 * @author 李金辉
 * @version 2019-07-18
 */
@Table(name="t_sy_rwmx", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="客户id"),
		@Column(name="xsid", attrName="xsid", label="学生id"),
		@Column(name="mbid", attrName="mbid", label="模板ID"),
		@Column(name="rwid", attrName="rwid", label="任务ID"),
		@Column(name="zt", attrName="zt", label="状态"),
		@Column(name="ksrq", attrName="ksrq", label="任务开始日期"),
		@Column(name="jsrq", attrName="jsrq", label="任务失效日期"),
		@Column(name="wcrq", attrName="wcrq", label="任务完成日期"),
		@Column(name="bz", attrName="bz", label="备忘"),
		@Column(name="pcid", attrName="pcid", label="推送流水批次号"),
		@Column(name="jfjsbz", attrName="jfjsbz", label="积分计算标志", comment="积分计算标志：1|已经计算，0|未计算"),
	},

		// 支持联合查询，如左右连接查询，支持设置查询自定义关联表的返回字段列
		joinTable={
				@JoinTable(type= Type.JOIN, entity=KhXx.class, alias="o",
						on="o.id = a.khid",
						columns={@Column(includeEntity=KhXx.class)}),
				@JoinTable(type= Type.JOIN, entity=SyMb.class, alias="s",
						on="s.id = a.mbid",
						columns={@Column(includeEntity=SyMb.class)}),
				@JoinTable(type= Type.JOIN, entity=SyMbmx.class, alias="m",
						on="m.id = a.rwid",
						columns={@Column(includeEntity=SyMbmx.class)})
		},
		orderBy="a.id DESC"
)
public class SyRwmx extends DataEntity<SyRwmx> {
	
	private static final long serialVersionUID = 1L;
	private String khid;		// 客户id
	private String xsid;		// 学生id
	private String mbid;		// 模板ID
	private String rwid;		// 任务ID
	private String zt;		// 状态
	private Date ksrq;		// 任务开始日期
	private Date jsrq;		// 任务失效日期
	private Date wcrq;		// 任务完成日期
	private String bz;		// 备忘
	private String pcid;		// 推送流水批次号
	private Long jfjsbz;		// 积分计算标志：1|已经计算，0|未计算

	private KhXx khXx;		// 备忘
	private SyMb syMb;		// 备忘
	private SyMbmx syMbmx;		// 备忘


	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}

	public SyMb getSyMb() {
		return syMb;
	}

	public void setSyMb(SyMb syMb) {
		this.syMb = syMb;
	}

	public SyRwmx() {
		this(null);
	}

	public SyRwmx(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="客户id长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=64, message="学生id长度不能超过 64 个字符")
	public String getXsid() {
		return xsid;
	}

	public void setXsid(String xsid) {
		this.xsid = xsid;
	}
	
	@Length(min=0, max=64, message="模板ID长度不能超过 64 个字符")
	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}
	
	@Length(min=0, max=64, message="任务ID长度不能超过 64 个字符")
	public String getRwid() {
		return rwid;
	}

	public void setRwid(String rwid) {
		this.rwid = rwid;
	}
	
	@Length(min=0, max=1, message="状态长度不能超过 1 个字符")
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getKsrq() {
		return ksrq;
	}

	public void setKsrq(Date ksrq) {
		this.ksrq = ksrq;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJsrq() {
		return jsrq;
	}

	public void setJsrq(Date jsrq) {
		this.jsrq = jsrq;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWcrq() {
		return wcrq;
	}

	public void setWcrq(Date wcrq) {
		this.wcrq = wcrq;
	}
	
	@Length(min=0, max=255, message="备忘长度不能超过 255 个字符")
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
	@Length(min=0, max=64, message="推送流水批次号长度不能超过 64 个字符")
	public String getPcid() {
		return pcid;
	}

	public void setPcid(String pcid) {
		this.pcid = pcid;
	}
	
	public Long getJfjsbz() {
		return jfjsbz;
	}

	public void setJfjsbz(Long jfjsbz) {
		this.jfjsbz = jfjsbz;
	}
	
}