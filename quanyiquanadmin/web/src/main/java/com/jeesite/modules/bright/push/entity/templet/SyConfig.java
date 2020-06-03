/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.entity.templet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.modules.bright.t.entity.group.Group;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 生涯配置表，将模板配置给客户（学生）、群组。（配置二选一，即一般配置给群组，亦可配置给客户）流程：1、配置模板2、配置模板适用对象：群组、客户、学生3、生涯任务生成：读此表，结合生涯模板表。生成t_sy_rwmx，同是根据推送渠道生成等推送记录写入push表界面：生涯生成界面要定制，Entity
 * @author 李金辉
 * @version 2019-07-17
 */
@Table(name="t_sy_config", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="groupid", attrName="groupid", label="群组ID"),
		@Column(name="khid", attrName="khid", label="客户ID"),
		@Column(name="xsid", attrName="xsid", label="学生ID"),
		@Column(name="lx", attrName="lx", label="类型"),
		@Column(name="mbid", attrName="mbid", label="生涯模板ID"),
		@Column(name="zt", attrName="zt", label="状态"),
		@Column(name="syqx", attrName="syqx", label="是否需要生涯权限"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
		@Column(name="push_zt", attrName="pushZt", label="状态"),
		@Column(name="push_sj", attrName="pushSj", label="推送时间"),
		@Column(name="push_cs", attrName="pushCs", label="推送次数"),
		@Column(name="xs_bz", attrName="xsBz", label="是否配置给学生 1|是0|否"),
	},
		// 支持联合查询，如左右连接查询，支持设置查询自定义关联表的返回字段列
		joinTable={
				@JoinTable(type= Type.JOIN, entity=Group.class, alias="o",
						on="o.id = a.groupid",
						columns={@Column(includeEntity=Group.class)}),
				@JoinTable(type= Type.JOIN, entity=SyMb.class, alias="s",
						on="s.id = a.mbid",
						columns={@Column(includeEntity=SyMb.class)})
		},

		orderBy="a.update_date DESC"
)
public class SyConfig extends DataEntity<SyConfig> {
	
	private static final long serialVersionUID = 1L;
	private String groupid;		// 群组ID
	private String khid;		// 客户ID
	private String xsid;		// 学生ID
	private Long lx;		// 类型
	private String mbid;		// 生涯模板ID
	private Integer zt;		// 状态
	private Integer syqx;		// 是否需要生涯权限
	private String delFlag;		// 删除标记
	private String pushZt;		// 状态
	private Date pushSj;		// 推送时间
	private Long pushCs;		// 推送次数
	private String xsBz;		// 是否配置给学生 1|是0|否

	private String groupdz;   //是否是动态群组 1 是 0 否

	private String ret;    //
	private String retcode;

	private Group group;		// 群组ID
	private SyMb syMb;		// 群组ID



	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getRetcode() {
		return retcode;
	}

	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	public String getGroupdz() {
		return groupdz;
	}

	public void setGroupdz(String groupdz) {
		this.groupdz = groupdz;
	}

	public SyMb getSyMb() {
		return syMb;
	}

	public void setSyMb(SyMb syMb) {
		this.syMb = syMb;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public SyConfig() {
		this(null);
	}

	public SyConfig(String id){
		super(id);
	}
	
	@Length(min=0, max=11, message="群组ID长度不能超过 11 个字符")
	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
	@Length(min=0, max=64, message="客户ID长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=64, message="学生ID长度不能超过 64 个字符")
	public String getXsid() {
		return xsid;
	}

	public void setXsid(String xsid) {
		this.xsid = xsid;
	}
	
	public Long getLx() {
		return lx;
	}

	public void setLx(Long lx) {
		this.lx = lx;
	}
	
	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}
	
	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}
	
	public Integer getSyqx() {
		return syqx;
	}

	public void setSyqx(Integer syqx) {
		this.syqx = syqx;
	}
	

	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public String getPushZt() {
		return pushZt;
	}

	public void setPushZt(String pushZt) {
		this.pushZt = pushZt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPushSj() {
		return pushSj;
	}

	public void setPushSj(Date pushSj) {
		this.pushSj = pushSj;
	}
	
	public Long getPushCs() {
		return pushCs;
	}

	public void setPushCs(Long pushCs) {
		this.pushCs = pushCs;
	}
	
	public String getXsBz() {
		return xsBz;
	}

	public void setXsBz(String xsBz) {
		this.xsBz = xsBz;
	}
	
}