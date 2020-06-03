/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.entity.push;

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
 * t_pushEntity
 * @author 李金辉
 * @version 2019-07-18
 */
@Table(name="t_push", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="客户ID"),
		@Column(name="mbid", attrName="mbid", label="消息模板ID"),
		@Column(name="tsnr", attrName="tsnr", label="待推送内容，不同渠道不同的解析方式"),
		@Column(name="tsqd", attrName="tsqd", label="推送渠道"),
		@Column(name="jhsj", attrName="jhsj", label="计划推送时间"),
		@Column(name="tssj", attrName="tssj", label="实际推送时间"),
		@Column(name="statu", attrName="statu", label="推送状态"),
		@Column(name="tscl", attrName="tscl", label="推送策略"),
		@Column(name="ydbz", attrName="ydbz", label="已读标志", comment="已读标志：1|已读；0|无效。回调用户是否已读，用户返回系统视为已读"),
		@Column(name="tsjkid", attrName="tsjkid", label="冗余", comment="冗余:小程序或订阅号模板消息ID，推送接口"),
		@Column(name="mobile", attrName="mobile", label="冗余", comment="冗余：手机号"),
		@Column(name="email", attrName="email", label="冗余", comment="冗余：邮箱地址"),
		@Column(name="fidyxq", attrName="fidyxq", label="冗余", comment="冗余：FORM ID有效期"),
		@Column(name="push_id", attrName="pushId", label="推送批次号"),
		@Column(name="oidyxq", attrName="oidyxq", label="冗余", comment="冗余：OPENID"),
		@Column(name="xsid", attrName="xsid", label="冗余", comment="冗余:学生id"),
		@Column(name="rwid", attrName="rwid", label="冗余", comment="冗余:生涯任务明细的id"),
		@Column(name="gz_id", attrName="gzId", label="冗余", comment="冗余:公众号模板id"),
		@Column(name="status", attrName="status", label="状态", comment="状态（0正常 1删除 2停用）", isUpdate=false),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
	},
		// 支持联合查询，如左右连接查询，支持设置查询自定义关联表的返回字段列
		joinTable={
				@JoinTable(type= Type.JOIN, entity=KhXx.class, alias="o",
						on="o.id = a.khid",
						columns={@Column(includeEntity=KhXx.class)})
		},
		orderBy="a.update_date DESC"
)
public class Push extends DataEntity<Push> {
	
	private static final long serialVersionUID = 1L;
	private String khid;		// 客户ID
	private String mbid;		// 消息模板ID
	private String tsnr;		// 待推送内容，不同渠道不同的解析方式
	private String tsqd;		// 推送渠道
	private Date jhsj;		// 计划推送时间
	private Date tssj;		// 实际推送时间
	private String statu;		// 推送状态
	private Long tscl;		// 推送策略
	private Long ydbz;		// 已读标志：1|已读；0|无效。回调用户是否已读，用户返回系统视为已读
	private String tsjkid;		// 冗余:小程序或订阅号模板消息ID，推送接口
	private String mobile;		// 冗余：手机号
	private String email;		// 冗余：邮箱地址
	private Date fidyxq;		// 冗余：FORM ID有效期
	private String pushId;		// 推送批次号
	private Date oidyxq;		// 冗余：OPENID
	private String xsid;		// 冗余:学生id
	private String rwid;		// 冗余:生涯任务明细的id
	private String gzId;		// 冗余:公众号模板id

	private KhXx khXx;		//
	private Integer bz; //是否是动态群组
	private String isPhone; //策略
	private String GroupId; //策略
	private Integer ret; //策略
	private String retcode; //策略

	public String getGroupId() {
		return GroupId;
	}

	public void setGroupId(String groupId) {
		GroupId = groupId;
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

	public Integer getBz() {
		return bz;
	}

	public void setBz(Integer bz) {
		this.bz = bz;
	}

	public String getIsPhone() {
		return isPhone;
	}

	public void setIsPhone(String isPhone) {
		this.isPhone = isPhone;
	}

	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}

	public Push() {
		this(null);
	}

	public Push(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="客户ID长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=11, message="消息模板ID长度不能超过 11 个字符")
	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}
	
	@Length(min=0, max=255, message="待推送内容，不同渠道不同的解析方式长度不能超过 255 个字符")
	public String getTsnr() {
		return tsnr;
	}

	public void setTsnr(String tsnr) {
		this.tsnr = tsnr;
	}
	
	public String getTsqd() {
		return tsqd;
	}

	public void setTsqd(String tsqd) {
		this.tsqd = tsqd;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getJhsj() {
		return jhsj;
	}

	public void setJhsj(Date jhsj) {
		this.jhsj = jhsj;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTssj() {
		return tssj;
	}

	public void setTssj(Date tssj) {
		this.tssj = tssj;
	}
	
	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}
	
	public Long getTscl() {
		return tscl;
	}

	public void setTscl(Long tscl) {
		this.tscl = tscl;
	}
	
	public Long getYdbz() {
		return ydbz;
	}

	public void setYdbz(Long ydbz) {
		this.ydbz = ydbz;
	}
	
	@Length(min=0, max=100, message="冗余长度不能超过 100 个字符")
	public String getTsjkid() {
		return tsjkid;
	}

	public void setTsjkid(String tsjkid) {
		this.tsjkid = tsjkid;
	}
	
	@Length(min=0, max=20, message="冗余长度不能超过 20 个字符")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=100, message="冗余长度不能超过 100 个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFidyxq() {
		return fidyxq;
	}

	public void setFidyxq(Date fidyxq) {
		this.fidyxq = fidyxq;
	}
	
	@Length(min=0, max=64, message="推送批次号长度不能超过 64 个字符")
	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOidyxq() {
		return oidyxq;
	}

	public void setOidyxq(Date oidyxq) {
		this.oidyxq = oidyxq;
	}
	
	@Length(min=0, max=64, message="冗余长度不能超过 64 个字符")
	public String getXsid() {
		return xsid;
	}

	public void setXsid(String xsid) {
		this.xsid = xsid;
	}
	
	@Length(min=0, max=64, message="冗余长度不能超过 64 个字符")
	public String getRwid() {
		return rwid;
	}

	public void setRwid(String rwid) {
		this.rwid = rwid;
	}
	
	@Length(min=0, max=200, message="冗余长度不能超过 200 个字符")
	public String getGzId() {
		return gzId;
	}

	public void setGzId(String gzId) {
		this.gzId = gzId;
	}
	
}