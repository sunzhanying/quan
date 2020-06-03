/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.entity.group;

import javax.validation.constraints.NotNull;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;

import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 分组类型表Entity
 * @author 李金辉
 * @version 2019-06-24
 */
@Table(name="t_group_kh", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="group_id", attrName="groupId.id", label="组id"),
		@Column(name="khid", attrName="khid", label="客户id"),
		@Column(name="sqls", attrName="sqls", label="sql"),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="del_flag", attrName="delFlag", label="删除标记")
	},
		// 支持联合查询，如左右连接查询，支持设置查询自定义关联表的返回字段列
		joinTable={
				@JoinTable(type= Type.JOIN, entity=KhXx.class, alias="o",
						on="o.id = a.khid",
						columns={@Column(includeEntity=KhXx.class)})
		},

		orderBy="a.create_date ASC"
)
public class GroupKh extends DataEntity<GroupKh> {
	
	private static final long serialVersionUID = 1L;
	private Group groupId;		// 组id 父类
	private String khid;		// 客户id
	private KhXx khXx;		// 客户id
	private String sqls;		// sql
	private String delFlag;		// 删除标记

	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}

	public GroupKh() {
		this(null);
	}


	public GroupKh(Group groupId){
		this.groupId = groupId;
	}
	
	@NotNull(message="组id不能为空")
	public Group getGroupId() {
		return groupId;
	}

	public void setGroupId(Group groupId) {
		this.groupId = groupId;
	}
	
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	public String getSqls() {
		return sqls;
	}

	public void setSqls(String sqls) {
		this.sqls = sqls;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}