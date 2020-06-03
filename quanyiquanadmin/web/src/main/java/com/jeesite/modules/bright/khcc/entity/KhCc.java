/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khcc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 客户持仓Entity
 * @author 马晓亮
 * @version 2019-07-16
 */
@Table(name="t_kh_cc", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="会员id"),
		@Column(name="spid", attrName="spid", label="对应商品ID"),
		@Column(name="zsl", attrName="zsl", label="总数量"),
		@Column(name="kysl", attrName="kysl", label="可用数量  单位为4时    0 关闭 1 开启"),
		@Column(name="ddid", attrName="ddid", label="购买时的订单ID"),
		@Column(name="hdfs", attrName="hdfs", label="获取方式", comment="获取方式: 购买|赠送|其它"),
		@Column(name="hdrq", attrName="hdrq", label="获得日期"),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
		@Column(name="splx", attrName="splx", label="冗余", comment="冗余：商品类型，：1|数量，2|优惠券，3|开通类"),
	}, orderBy="a.update_date DESC"
)
public class KhCc extends DataEntity<KhCc> {
	
	private static final long serialVersionUID = 1L;
	private String khid;		// 会员id
	private String spid;		// 对应商品ID
	private Long zsl;		// 总数量
	private Integer kysl;		// 可用数量  单位为4时    0 关闭 1 开启
	private String ddid;		// 购买时的订单ID
	private String hdfs;		// 获取方式: 购买|赠送|其它
	private Date hdrq;		// 获得日期
	private String delFlag;		// 删除标记
	private String splx;		// 冗余：商品类型，：1|数量，2|优惠券，3|开通类

	private String hxid;  //核销id
	private String hxsm; //核销说明
	//存储过程返回
	private Integer ret;     //返回码
	private String retcode;   //返回信息
	
	public KhCc() {
		this(null);
	}

	public KhCc(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="会员id长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=64, message="对应商品ID长度不能超过 64 个字符")
	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}
	
	public Long getZsl() {
		return zsl;
	}

	public void setZsl(Long zsl) {
		this.zsl = zsl;
	}
	
	public Integer getKysl() {
		return kysl;
	}

	public void setKysl(Integer kysl) {
		this.kysl = kysl;
	}
	
	@Length(min=0, max=64, message="购买时的订单ID长度不能超过 64 个字符")
	public String getDdid() {
		return ddid;
	}

	public void setDdid(String ddid) {
		this.ddid = ddid;
	}
	
	@Length(min=0, max=1, message="获取方式长度不能超过 1 个字符")
	public String getHdfs() {
		return hdfs;
	}

	public void setHdfs(String hdfs) {
		this.hdfs = hdfs;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHdrq() {
		return hdrq;
	}

	public void setHdrq(Date hdrq) {
		this.hdrq = hdrq;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	@Length(min=0, max=1, message="冗余长度不能超过 1 个字符")
	public String getSplx() {
		return splx;
	}

	public void setSplx(String splx) {
		this.splx = splx;
	}

	public String getHxid() {
		return hxid;
	}

	public void setHxid(String hxid) {
		this.hxid = hxid;
	}

	public String getHxsm() {
		return hxsm;
	}

	public void setHxsm(String hxsm) {
		this.hxsm = hxsm;
	}

	public Integer getRet() {
		return ret;
	}

	public void setRet(Integer ret) {
		this.ret = ret;
	}

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
}