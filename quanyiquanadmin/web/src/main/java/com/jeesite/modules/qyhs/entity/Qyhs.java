/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyhs.entity;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.List;

/**
 * 权益回收Entity
 * @author 马晓亮
 * @version 2020-03-25
 */
@Table(name="a_qyhs", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="用户id"),
		@Column(name="qyq_id", attrName="qyqId", label="权益券id"),
		@Column(name="zt", attrName="zt", label="状态 1 待审核 2 审核完成"),
		@Column(name="type", attrName="type", label="卡类型"),
		@Column(name="source", attrName="source", label="来源"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date ASC"
)
public class Qyhs extends DataEntity<Qyhs> {

	public static final String STATUS_DSH = "1";   //待审核
	public static final String STATUS_SHWC = "2";   //审核完成

	private static final long serialVersionUID = 1L;
	private String khid;		// 用户id
	private String qyqId;		// 权益券id
	private String zt;		// 状态 1 待审核 2 审核完成
	private String type;     //卡类型

	private Integer dsh;     //待审核
	private Integer tg;      //通过
	private Integer th;      //退回
	private String source;   //来源

	private KhXx khXx;      //客户
	private SpXx spXx;       //权益

	List<QyhsMx> qyhsMxes = ListUtils.newArrayList();   //权益明细列表
	
	public Qyhs() {
		this(null);
	}

	public Qyhs(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="用户id长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=64, message="权益券id长度不能超过 64 个字符")
	public String getQyqId() {
		return qyqId;
	}

	public void setQyqId(String qyqId) {
		this.qyqId = qyqId;
	}
	
	@Length(min=0, max=1, message="状态 1 待审核 2 审核完成长度不能超过 1 个字符")
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public List<QyhsMx> getQyhsMxes() {
		return qyhsMxes;
	}

	public void setQyhsMxes(List<QyhsMx> qyhsMxes) {
		this.qyhsMxes = qyhsMxes;
	}

	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}

	public SpXx getSpXx() {
		return spXx;
	}

	public void setSpXx(SpXx spXx) {
		this.spXx = spXx;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDsh() {
		return dsh;
	}

	public void setDsh(Integer dsh) {
		this.dsh = dsh;
	}

	public Integer getTg() {
		return tg;
	}

	public void setTg(Integer tg) {
		this.tg = tg;
	}

	public Integer getTh() {
		return th;
	}

	public void setTh(Integer th) {
		this.th = th;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}