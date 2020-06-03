/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.entity.pushlog;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 推送批次记录流水表用于记录每次生成的推送批次Entity
 * @author 李金辉
 * @version 2019-07-18
 */
@Table(name="t_push_ls", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="pcid", attrName="pcid", label="推送批次号"),
		@Column(name="groupid", attrName="groupid", label="本次推送的群组ID"),
		@Column(name="type", attrName="type", label="类型"),
		@Column(name="sy_id", attrName="syId", label="生涯推送记录ID"),
		@Column(name="note", attrName="note", label="备注"),
		@Column(name="push_sj", attrName="pushSj", label="推送日间"),
		@Column(name="status", attrName="status", label="状态", comment="状态（0正常 1删除 2停用）", isUpdate=false),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
	}, orderBy="a.update_date DESC"
)
public class PushLs extends DataEntity<PushLs> {
	
	private static final long serialVersionUID = 1L;
	private String pcid;		// 推送批次号
	private Long groupid;		// 本次推送的群组ID
	private Long type;		// 类型
	private Long syId;		// 生涯推送记录ID
	private String note;		// 备注
	private Date pushSj;		// 推送日间
	
	public PushLs() {
		this(null);
	}

	public PushLs(String id){
		super(id);
	}
	
	@NotBlank(message="推送批次号不能为空")
	@Length(min=0, max=64, message="推送批次号长度不能超过 64 个字符")
	public String getPcid() {
		return pcid;
	}

	public void setPcid(String pcid) {
		this.pcid = pcid;
	}
	
	public Long getGroupid() {
		return groupid;
	}

	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}
	
	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
	
	public Long getSyId() {
		return syId;
	}

	public void setSyId(Long syId) {
		this.syId = syId;
	}
	
	@Length(min=0, max=100, message="备注长度不能超过 100 个字符")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPushSj() {
		return pushSj;
	}

	public void setPushSj(Date pushSj) {
		this.pushSj = pushSj;
	}
	
}