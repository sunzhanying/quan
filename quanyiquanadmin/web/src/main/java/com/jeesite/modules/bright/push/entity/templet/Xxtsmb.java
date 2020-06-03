/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.entity.templet;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 消息推送模板Entity
 * @author 李金辉
 * @version 2019-07-18
 */
@Table(name="t_xxtsmb", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="mbmc", attrName="mbmc", label="模板名称"),
		@Column(name="mbrc", attrName="mbrc", label="模板入参"),
		@Column(name="mbnr", attrName="mbnr", label="模板内容"),
		@Column(name="qdmc", attrName="qdmc", label="推送渠道"),
		@Column(name="mbid", attrName="mbid", label="小程序或订阅号模板ID"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="bz", attrName="bz", label="模板描述"),
		@Column(name="gz_id", attrName="gzId", label="公众模板"),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.update_date DESC"
)
public class Xxtsmb extends DataEntity<Xxtsmb> {
	
	private static final long serialVersionUID = 1L;
	private String mbmc;		// 模板名称
	private String mbrc;		// 模板入参
	private String mbnr;		// 模板内容
	private Long qdmc;		// 推送渠道
	private String mbid;		// 小程序或订阅号模板ID
	private String bz;		// 模板描述
	private String gzId;		// 公众模板
	private String delFlag;		// 删除标记
	
	public Xxtsmb() {
		this(null);
	}

	public Xxtsmb(String id){
		super(id);
	}
	
	@Length(min=0, max=50, message="模板名称长度不能超过 50 个字符")
	public String getMbmc() {
		return mbmc;
	}

	public void setMbmc(String mbmc) {
		this.mbmc = mbmc;
	}
	
	@Length(min=0, max=255, message="模板入参长度不能超过 255 个字符")
	public String getMbrc() {
		return mbrc;
	}

	public void setMbrc(String mbrc) {
		this.mbrc = mbrc;
	}
	
	@Length(min=0, max=255, message="模板内容长度不能超过 255 个字符")
	public String getMbnr() {
		return mbnr;
	}

	public void setMbnr(String mbnr) {
		this.mbnr = mbnr;
	}
	
	public Long getQdmc() {
		return qdmc;
	}

	public void setQdmc(Long qdmc) {
		this.qdmc = qdmc;
	}
	
	@Length(min=0, max=200, message="小程序或订阅号模板ID长度不能超过 200 个字符")
	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}
	
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
	@Length(min=0, max=200, message="公众模板长度不能超过 200 个字符")
	public String getGzId() {
		return gzId;
	}

	public void setGzId(String gzId) {
		this.gzId = gzId;
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