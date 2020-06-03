/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.entity.group;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 分组类型表Entity
 * @author 李金辉
 * @version 2019-06-24
 */
@Table(name="t_group", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="type", attrName="type", label="分组类型"),
		@Column(name="number", attrName="number", label="人数"),
		@Column(name="sqls", attrName="sqls", label="sql"),
		@Column(name="ver", attrName="ver", label="版本号"),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.update_date DESC"
)
public class Group extends DataEntity<Group> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String type;		// 分组类型
	private Integer number;		// 人数
	private String sqls;		// sql
	private String ver;		// 版本号
	private String delFlag;		// 删除标记

	private List<GroupKh> groupKhList = ListUtils.newArrayList();		// 子表列表

	private List<GroupKh> khList;		//后台接收

	public List<GroupKh> getKhList() {
		return khList;
	}

	public void setKhList(List<GroupKh> khList) {
		this.khList = khList;
	}

	public Group() {
		this(null);
	}

	public Group(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="分组类型长度不能超过 1 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public String getSqls() {
		return sqls;
	}

	public void setSqls(String sqls) {
		this.sqls = sqls;
	}
	
	@Length(min=0, max=64, message="版本号长度不能超过 64 个字符")
	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public List<GroupKh> getGroupKhList() {
		return groupKhList;
	}

	public void setGroupKhList(List<GroupKh> groupKhList) {
		this.groupKhList = groupKhList;
	}
	
}