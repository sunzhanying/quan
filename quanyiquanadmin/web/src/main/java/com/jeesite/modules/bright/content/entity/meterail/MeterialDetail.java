/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.entity.meterail;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 素材表Entity
 * @author liqingfeng
 * @version 2019-07-08
 */
@Table(name="meterial_detail", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="meterial_id", attrName="meterialId.id", label="素材id"),
		@Column(name="attribute_name", attrName="attributeName", label="属性名", queryType=QueryType.LIKE),
		@Column(name="content_url", attrName="contentUrl", label="图片或视频的文件路径"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.create_date ASC"
)
public class MeterialDetail extends DataEntity<MeterialDetail> {
	
	private static final long serialVersionUID = 1L;
	private Meterial meterialId;		// 素材id 父类
	private String attributeName;		// 属性名
	private String contentUrl;		// 图片或视频的文件路径
	private String delFlag;		// 删除标记
	
	public MeterialDetail() {
		this(null);
	}


	public MeterialDetail(Meterial meterialId){
		this.meterialId = meterialId;
	}
	
	@Length(min=0, max=64, message="素材id长度不能超过 64 个字符")
	public Meterial getMeterialId() {
		return meterialId;
	}

	public void setMeterialId(Meterial meterialId) {
		this.meterialId = meterialId;
	}
	
	@NotBlank(message="属性名不能为空")
	@Length(min=0, max=255, message="属性名长度不能超过 255 个字符")
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	@Length(min=0, max=200, message="图片或视频的文件路径长度不能超过 200 个字符")
	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}