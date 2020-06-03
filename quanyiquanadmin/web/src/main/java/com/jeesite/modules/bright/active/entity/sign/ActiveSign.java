/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.active.entity.sign;

import com.jeesite.modules.bright.active.entity.active.NrHd;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 活动 课程 报名表Entity
 * @author 李金辉
 * @version 2019-08-06
 */
@Table(name="t_nr_hdbm", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="hdid", attrName="hdid", label="活动id"),
		@Column(name="khid", attrName="khid", label="khid"),
		@Column(name="zt", attrName="zt", label="报名状态"),
		@Column(name="type", attrName="type", label="类型"),
		@Column(name="bm_rq", attrName="bmRq", label="报名时间"),
		@Column(name="qd_rq", attrName="qdRq", label="签到时间"),
		@Column(name="jz_rq", attrName="jzRq", label="截止时间"),
		@Column(name="yy_rq", attrName="yyRq", label="预约时间"),
		@Column(name="yy_xm", attrName="yyXm", label="服务类型"),
		@Column(name="sup_id", attrName="supId", label="预约人"),
		@Column(name="yypb_id", attrName="yypbId", label="预约排班ID"),
		@Column(name="yyzt", attrName="yyzt", label="预约状态"),
		@Column(name="yy_xq", attrName="yyXq", label="预约详情"),
		@Column(includeEntity=DataEntity.class),
	},
		// 支持联合查询，如左右连接查询，支持设置查询自定义关联表的返回字段列
		joinTable={
				@JoinTable(type= Type.JOIN, entity=KhXx.class, alias="o",
						on="o.id = a.khid",
						columns={@Column(includeEntity=KhXx.class)}),
				@JoinTable(type= Type.JOIN, entity=NrHd.class, alias="d",
						on="d.id = a.hdid",
						columns={@Column(includeEntity=NrHd.class)})
		},
		orderBy="a.update_date DESC"
)
public class ActiveSign extends DataEntity<ActiveSign> {
	
	private static final long serialVersionUID = 1L;
	private String hdid;		// 活动id
	private String khid;		// khid
	private Long zt;		// 报名状态
	private Integer type;		// 类型
	private Date bmRq;		// 报名时间
	private Date qdRq;		// 签到时间
	private Date jzRq;		// 截止时间
	private Date yyRq;		// 预约时间
	private Integer yyXm;		// 服务类型
	private Long supId;		// 预约人
	private Long yypbId;		// 预约排班ID
	private String yyzt;		// 预约状态
	private String yyXq;		// 预约详情
	private KhXx khxx;		// 预约详情
	private NrHd nrHd;		// 预约详情

	public NrHd getNrHd() {
		return nrHd;
	}

	public void setNrHd(NrHd nrHd) {
		this.nrHd = nrHd;
	}

	public KhXx getKhxx() {
		return khxx;
	}

	public void setKhxx(KhXx khxx) {
		this.khxx = khxx;
	}

	public ActiveSign() {
		this(null);
	}

	public ActiveSign(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="活动id长度不能超过 64 个字符")
	public String getHdid() {
		return hdid;
	}

	public void setHdid(String hdid) {
		this.hdid = hdid;
	}
	
	@Length(min=0, max=64, message="khid长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	public Long getZt() {
		return zt;
	}

	public void setZt(Long zt) {
		this.zt = zt;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBmRq() {
		return bmRq;
	}

	public void setBmRq(Date bmRq) {
		this.bmRq = bmRq;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getQdRq() {
		return qdRq;
	}

	public void setQdRq(Date qdRq) {
		this.qdRq = qdRq;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJzRq() {
		return jzRq;
	}

	public void setJzRq(Date jzRq) {
		this.jzRq = jzRq;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getYyRq() {
		return yyRq;
	}

	public void setYyRq(Date yyRq) {
		this.yyRq = yyRq;
	}
	
	public Integer getYyXm() {
		return yyXm;
	}

	public void setYyXm(Integer yyXm) {
		this.yyXm = yyXm;
	}
	
	public Long getSupId() {
		return supId;
	}

	public void setSupId(Long supId) {
		this.supId = supId;
	}
	
	public Long getYypbId() {
		return yypbId;
	}

	public void setYypbId(Long yypbId) {
		this.yypbId = yypbId;
	}
	
	@Length(min=0, max=1, message="预约状态长度不能超过 1 个字符")
	public String getYyzt() {
		return yyzt;
	}

	public void setYyzt(String yyzt) {
		this.yyzt = yyzt;
	}
	
	@Length(min=0, max=100, message="预约详情长度不能超过 100 个字符")
	public String getYyXq() {
		return yyXq;
	}

	public void setYyXq(String yyXq) {
		this.yyXq = yyXq;
	}
	
}