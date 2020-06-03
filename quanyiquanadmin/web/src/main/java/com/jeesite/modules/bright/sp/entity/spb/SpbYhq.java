/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.entity.spb;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.modules.bright.sp.entity.yhq.SpYhq;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 商品包定义Entity
 * @author 马晓亮
 * @version 2019-07-01
 */
@Table(name="t_spb_yhq", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="spbid", attrName="spbid.id", label="商品包id"),
		@Column(name="yhqid", attrName="yhqid", label="会员对应优惠券ID"),
		@Column(name="sl", attrName="sl", label="赠送数量"),
		@Column(name="status", attrName="status", label="0正常 1删除 2停用 3冻结", isUpdate=false),
	},
		joinTable={
		@JoinTable(type=Type.LEFT_JOIN, entity=SpYhq.class, attrName="spYhq", alias="u11",
				on="u11.id = a.yhqid", columns={
				@Column(name="yh_name", attrName="yhName", label="优惠券名称", isQuery=false),
		}),
	},  orderBy="a.id ASC"
)
public class SpbYhq extends DataEntity<SpbYhq> {
	
	private static final long serialVersionUID = 1L;
	private Spbdy spbid;		// 商品包id 父类
	private String yhqid;		// 会员对应优惠券ID
	private Integer sl;		// 赠送数量

	private SpYhq spYhq;    //优惠券
	
	public SpbYhq() {
		this(null);
	}


	public SpbYhq(Spbdy spbid){
		this.spbid = spbid;
	}
	
	@Length(min=0, max=64, message="商品包id长度不能超过 64 个字符")
	public Spbdy getSpbid() {
		return spbid;
	}

	public void setSpbid(Spbdy spbid) {
		this.spbid = spbid;
	}
	
	@NotBlank(message="会员对应优惠券ID不能为空")
	@Length(min=0, max=64, message="会员对应优惠券ID长度不能超过 64 个字符")
	public String getYhqid() {
		return yhqid;
	}

	public void setYhqid(String yhqid) {
		this.yhqid = yhqid;
	}
	
	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public SpYhq getSpYhq() {
		return spYhq;
	}

	public void setSpYhq(SpYhq spYhq) {
		this.spYhq = spYhq;
	}
	
}