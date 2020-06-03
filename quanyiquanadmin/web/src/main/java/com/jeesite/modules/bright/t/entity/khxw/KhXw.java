/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.entity.khxw;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.modules.bright.t.entity.xw.Xw;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 客户行为Entity
 * @author 李金辉
 * @version 2019-06-26
 */
@Table(name="t_kh_xw", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="khid", attrName="khid", label="客户id"),
		@Column(name="xwmc", attrName="xwmc", label="行为名称"),
		@Column(name="xwms", attrName="xwms", label="行为描述", isQuery=false),
		@Column(name="diid", attrName="diid", label="订单id"),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	},
		joinTable ={
				@JoinTable(type= Type.LEFT_JOIN, entity=Xw.class, alias="o",
						on="o.id = a.xwmc",
						columns={@Column(includeEntity=Xw.class)})
		}, orderBy="a.update_date DESC"

)
public class KhXw extends DataEntity<KhXw> {
	
	private static final long serialVersionUID = 1L;
	private String khid;		// 客户id
	private String xwmc;		// 行为名称
	private String xwms;		// 行为描述
	private String diid;		// 订单id
	private String delFlag;		// 删除标记

	private Xw xw;		// 删除标记

	public Xw getXw() {
		return xw;
	}

	public void setXw(Xw xw) {
		this.xw = xw;
	}

	public KhXw() {
		this(null);
	}

	public KhXw(String id){
		super(id);
	}
	
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	public String getXwmc() {
		return xwmc;
	}

	public void setXwmc(String xwmc) {
		this.xwmc = xwmc;
	}
	
	@Length(min=0, max=1000, message="行为描述长度不能超过 1000 个字符")
	public String getXwms() {
		return xwms;
	}

	public void setXwms(String xwms) {
		this.xwms = xwms;
	}
	
	@Length(min=0, max=64, message="订单id长度不能超过 64 个字符")
	public String getDiid() {
		return diid;
	}

	public void setDiid(String diid) {
		this.diid = diid;
	}
	
	@NotBlank(message="删除标记不能为空")
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}