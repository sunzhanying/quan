/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.entity.meterail.channel;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.modules.bright.setfocus.entity.source.Source;
import org.hibernate.validator.constraints.Length;

/**
 * 渠道表Entity
 * @author liqingfeng
 * @version 2019-07-09
 */
@Table(name="meterial_channel", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="meterial_id", attrName="meterialId", label="素材id"),
		@Column(name="source_id", attrName="sourceId", label="来源id"),
		@Column(name="source_content", attrName="sourceContent", label="来源内容"),
		@Column(name="link_url", attrName="linkUrl", label="可追踪url"),
		@Column(name="qrcode", attrName="qrcode", label="二维码"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	},joinTable= {
				@JoinTable(type = JoinTable.Type.LEFT_JOIN, entity = Source.class, attrName = "source", alias = "u10",
						on = "u10.id = a.source_id", columns = {
						@Column(name = "id", label = "来源编号", isPK = true),
						@Column(name = "name", label = "来源名称", isQuery = false),
				})
		}, orderBy="a.update_date DESC"
)
public class MeterialChannel extends DataEntity<MeterialChannel> {
	
	private static final long serialVersionUID = 1L;
	private String meterialId;     //素材id
	private String sourceId;		// 来源id
	private String sourceContent;		// 来源内容
	private String linkUrl;		// 可追踪url
	private String qrcode;		// 二维码
	private String delFlag;		// 删除标记

	private Source source;  //来源

	public MeterialChannel() {
		this(null);
	}

	public MeterialChannel(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="来源id长度不能超过 64 个字符")
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	@Length(min=0, max=255, message="来源内容长度不能超过 255 个字符")
	public String getSourceContent() {
		return sourceContent;
	}

	public void setSourceContent(String sourceContent) {
		this.sourceContent = sourceContent;
	}
	
	@Length(min=0, max=255, message="可追踪url长度不能超过 255 个字符")
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	@Length(min=0, max=255, message="二维码长度不能超过 255 个字符")
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getMeterialId() {
		return meterialId;
	}

	public void setMeterialId(String meterialId) {
		this.meterialId = meterialId;
	}
}