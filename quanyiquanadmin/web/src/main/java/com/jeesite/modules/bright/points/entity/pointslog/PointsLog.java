/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.points.entity.pointslog;

import javax.validation.constraints.NotBlank;

import com.jeesite.modules.bright.points.entity.pointsconfig.PointsConfig;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 客户积分流水表Entity
 * @author 李金辉
 * @version 2019-08-14
 */
@Table(name="t_jf_khls", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="khid"),
		@Column(name="xsid", attrName="xsid", label="xsid"),
		@Column(name="lx", attrName="lx", label="1|积分；2|消费；3|冻结"),
		@Column(name="jflx", attrName="jflx", label="积分类型"),
		@Column(name="mc", attrName="mc", label="积分摘要，冻结时存入订单ID"),
		@Column(name="sl", attrName="sl", label="积分数量"),
		@Column(name="jfrq", attrName="jfrq", label="积分日期"),
		@Column(includeEntity=DataEntity.class),
	},
		// 支持联合查询，如左右连接查询，支持设置查询自定义关联表的返回字段列
		joinTable={
				@JoinTable(type= Type.JOIN, entity=KhXx.class, alias="o",
						on="o.id = a.khid",
						columns={@Column(includeEntity=KhXx.class)}),
				@JoinTable(type= Type.JOIN, entity=PointsConfig.class, alias="p",
						on="p.id = a.jflx",
						columns={@Column(includeEntity=PointsConfig.class)})
		},
		orderBy="a.update_date DESC"
)
public class PointsLog extends DataEntity<PointsLog> {
	
	private static final long serialVersionUID = 1L;
	private String khid;		// khid
	private KhXx khXx;		// khid
	private String xsid;		// xsid
	private Integer lx;		// 1|积分；2|消费；3|冻结
	private String jflx;		// 积分类型
	private PointsConfig pointsConfig;		// 积分类型
	private String mc;		// 积分摘要，冻结时存入订单ID
	private Long sl;		// 积分数量
	private Date jfrq;		// 积分日期

	public PointsConfig getPointsConfig() {
		return pointsConfig;
	}

	public void setPointsConfig(PointsConfig pointsConfig) {
		this.pointsConfig = pointsConfig;
	}

	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}

	public PointsLog() {
		this(null);
	}

	public PointsLog(String id){
		super(id);
	}
	
	@NotBlank(message="khid不能为空")
	@Length(min=0, max=64, message="khid长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=64, message="xsid长度不能超过 64 个字符")
	public String getXsid() {
		return xsid;
	}

	public void setXsid(String xsid) {
		this.xsid = xsid;
	}
	
	public Integer getLx() {
		return lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}
	
	@NotBlank(message="积分类型不能为空")
	@Length(min=0, max=11, message="积分类型长度不能超过 11 个字符")
	public String getJflx() {
		return jflx;
	}

	public void setJflx(String jflx) {
		this.jflx = jflx;
	}
	
	@Length(min=0, max=64, message="积分摘要，冻结时存入订单ID长度不能超过 64 个字符")
	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}
	
	@NotNull(message="积分数量不能为空")
	public Long getSl() {
		return sl;
	}

	public void setSl(Long sl) {
		this.sl = sl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJfrq() {
		return jfrq;
	}

	public void setJfrq(Date jfrq) {
		this.jfrq = jfrq;
	}
	
}