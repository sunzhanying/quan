/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.entity.templet;

import org.hibernate.validator.constraints.Length;

import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 生涯模板Entity
 * @author 李金辉
 * @version 2019-07-17
 */
@Table(name="t_sy_mb", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="mbmc", attrName="mbmc", label="模板名称"),
		@Column(name="mblx", attrName="mblx", label="1|生活类、2|学习类1|生活类、2|学习类模板类型", comment="1|生活类、2|学习类1|生活类、2|学习类模板类型：1|学习，2|生活。。。字典"),
		@Column(name="sydx", attrName="sydx", label="适用对象", comment="适用对象：初中|高中|竞赛……"),
		@Column(name="zt", attrName="zt", label="状态", comment="状态：1 未推送 2 已推送"),
		@Column(name="authority", attrName="authority", label="是否需要拥有生涯任务权限 1 是 2 否"),
		@Column(name="spid", attrName="spid", label="关联商品", comment="关联商品：用于控制在积分商城中的显示"),
		@Column(name="jfjg", attrName="jfjg", label="积分价格", comment="积分价格：为0表示不可兑换"),
		@Column(name="img", attrName="img", label="图片", comment="图片：生涯示例"),
		@Column(name="bbh", attrName="bbh", label="版本号"),
		@Column(name="dez_id", attrName="dezId", label="设计者ID"),
		@Column(name="status", attrName="status", label="状态"),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.update_date DESC"
)
public class SyMb extends DataEntity<SyMb> {
	
	private static final long serialVersionUID = 1L;
	private String mbmc;		// 模板名称
	private String mblx;		// 1|生活类、2|学习类1|生活类、2|学习类模板类型：1|学习，2|生活。。。字典
	private String sydx;		// 适用对象：初中|高中|竞赛……
	private String zt;		// 状态：1 未推送 2 已推送
	private String authority;		// 是否需要拥有生涯任务权限 1 是 2 否
	private String spid;		// 关联商品：用于控制在积分商城中的显示
	private Long jfjg;		// 积分价格：为0表示不可兑换
	private String img;		// 图片：生涯示例
	private String bbh;		// 版本号
	private String dezId;		// 设计者ID

	private String delFlag;		// 删除标记
	private List<SyMbmx> syMbmxList = ListUtils.newArrayList();		// 子表列表
	
	public SyMb() {
		this(null);
	}

	public SyMb(String id){
		super(id);
	}


	@Length(min=0, max=50, message="模板名称长度不能超过 50 个字符")
	public String getMbmc() {
		return mbmc;
	}

	public void setMbmc(String mbmc) {
		this.mbmc = mbmc;
	}
	
	@Length(min=0, max=2, message="1|生活类、2|学习类1|生活类、2|学习类模板类型长度不能超过 2 个字符")
	public String getMblx() {
		return mblx;
	}

	public void setMblx(String mblx) {
		this.mblx = mblx;
	}
	
	@Length(min=0, max=64, message="适用对象长度不能超过 64 个字符")
	public String getSydx() {
		return sydx;
	}

	public void setSydx(String sydx) {
		this.sydx = sydx;
	}
	
	@Length(min=0, max=1, message="状态长度不能超过 1 个字符")
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
	
	@Length(min=0, max=1, message="是否需要拥有生涯任务权限 1 是 2 否长度不能超过 1 个字符")
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	@Length(min=0, max=64, message="关联商品长度不能超过 64 个字符")
	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}
	
	public Long getJfjg() {
		return jfjg;
	}

	public void setJfjg(Long jfjg) {
		this.jfjg = jfjg;
	}
	
	@Length(min=0, max=50, message="图片长度不能超过 50 个字符")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@Length(min=0, max=64, message="版本号长度不能超过 64 个字符")
	public String getBbh() {
		return bbh;
	}

	public void setBbh(String bbh) {
		this.bbh = bbh;
	}
	
	@Length(min=0, max=64, message="设计者ID长度不能超过 64 个字符")
	public String getDezId() {
		return dezId;
	}

	public void setDezId(String dezId) {
		this.dezId = dezId;
	}
	

	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public List<SyMbmx> getSyMbmxList() {
		return syMbmxList;
	}

	public void setSyMbmxList(List<SyMbmx> syMbmxList) {
		this.syMbmxList = syMbmxList;
	}
	
}