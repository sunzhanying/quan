/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.entity.meterail;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.bright.setfocus.entity.meterialtype.MeterialType;
import com.jeesite.modules.bright.setfocus.entity.others.salesroom.Salesroom;
import com.jeesite.modules.bright.setfocus.entity.tag.Tag;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 素材表Entity
 * @author liqingfeng
 * @version 2019-07-08
 */
@Table(name="meterial", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="attribute_name", attrName="attributeName", label="属性", queryType=QueryType.LIKE),
		@Column(name="title", attrName="title", label="标题", queryType=QueryType.LIKE),
		@Column(name="tags_id", attrName="tagsId", label="内容标签"),
		@Column(name="type", attrName="type", label="分类"),
		@Column(name="intro", attrName="intro", label="简介"),
		@Column(name="cover_img", attrName="coverImg", label="封面缩略图"),
		@Column(name="material_status", attrName="materialStatus", label="状态 1 发布 2 草稿"),
		@Column(name="link_type", attrName="linkType", label="链接类型 1内部 2 外部"),
		@Column(name="qr_link", attrName="qrLink", label="链接"),
		@Column(name="salesroom_id", attrName="salesroomId", label="传播渠道"),
		@Column(name="price", attrName="price", label="价格"),
		@Column(name="detailIntro", attrName="detailIntro", label="详细介绍"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	},
		joinTable={
				@JoinTable(type=Type.LEFT_JOIN, entity=MeterialType.class, attrName="meterialType", alias="u10",
						on="u10.code = a.type", columns={
						@Column(name="code", label="分类code", isPK=true),
						@Column(name="name", label="分类名称名称", isQuery=false),
				}),
		},orderBy="a.update_date DESC"
)
public class Meterial extends DataEntity<Meterial> {
	public static final String  STATUS_PUBLISH = "1"; //发布状态
	public static final String  STATUS_DRAFT = "2";  //草稿状态
	public static final String  LINK_INTERNAL = "1"; //内部链接
	public static final String  LINK_EXTERNAL = "2";//外部链接

	private static final long serialVersionUID = 1L;
	private String attributeName;		// 属性
	private String title;		// 标题
	private String tagsId;		// 内容标签
	private String type;		// 分类
	private String intro;		// 简介
	private String coverImg;		// 封面缩略图
	private String materialStatus;		// 状态 1 发布 2 草稿
	private String linkType;		// 链接类型 1内部 2 外部
	private String qrLink;		// 链接
	private String personTime;		// 访问人次
	private String visitor;		// 访问人数
	private String delFlag;		// 删除标记
	private Double price;        //价格
	private String detailIntro;        //详细介绍


	private String isLike;  //是否关注 0 不关注 1 关注
	private String salesroomId;   //门店/传播渠道
	private List<MeterialDetail> meterialDetailList = ListUtils.newArrayList();		// 子表列表
	private List<Tag> tagList = ListUtils.newArrayList();
	private Salesroom salesroom;

	public String getSalesroomId() {
		return salesroomId;
	}

	public void setSalesroomId(String salesroomId) {
		this.salesroomId = salesroomId;
	}


	public Salesroom getSalesroom() {
		return salesroom;
	}

	public void setSalesroom(Salesroom salesroom) {
		this.salesroom = salesroom;
	}

	private MeterialType meterialType;

	private String upload;

	public Meterial() {
		this(null);
	}

	public Meterial(String id){
		super(id);
	}
	public String getUpload() {
		return upload;
	}

	public void setUpload(String upload) {
		this.upload = upload;
	}

	@NotBlank(message="属性不能为空")
	@Length(min=0, max=64, message="属性长度不能超过 64 个字符")
	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	@Length(min=0, max=30, message="标题长度不能超过 30 个字符")
	public String getTitle() {
		return title;
	}

	public String getDetailIntro() {
		return detailIntro;
	}

	public void setDetailIntro(String detailIntro) {
		this.detailIntro = detailIntro;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank(message="内容标签不能为空")
	public String getTagsId() {
		return tagsId;
	}

	public void setTagsId(String tagsId) {
		this.tagsId = tagsId;
	}
	
	@NotBlank(message="分类不能为空")
	@Length(min=0, max=100, message="分类长度不能超过 100 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getIntro() {
		return intro;
	}

	public String getIsLike() {
		return isLike;
	}

	public void setIsLike(String isLike) {
		this.isLike = isLike;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@Length(min=0, max=200, message="封面缩略图长度不能超过 200 个字符")
	public String getCoverImg() {
		return coverImg;
	}

	@NotNull(message="价格不能为空")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	
	@Length(min=0, max=1, message="状态 1 发布 2 草稿长度不能超过 1 个字符")
	public String getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(String materialStatus) {
		this.materialStatus = materialStatus;
	}
	
	@Length(min=0, max=1, message="链接类型 1内部 2 外部长度不能超过 1 个字符")
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	
	@Length(min=0, max=255, message="链接长度不能超过 255 个字符")
	public String getQrLink() {
		return qrLink;
	}

	public void setQrLink(String qrLink) {
		this.qrLink = qrLink;
	}

	@Length(min=0, max=255, message="访问人次长度不能超过 255 个字符")
	public String getPersonTime() {
		return personTime;
	}

	public void setPersonTime(String personTime) {
		this.personTime = personTime;
	}

	@Length(min=0, max=255, message="访问人数长度不能超过 255 个字符")
	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	public MeterialType getMeterialType() {
		return meterialType;
	}

	public void setMeterialType(MeterialType meterialType) {
		this.meterialType = meterialType;
	}

	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public List<MeterialDetail> getMeterialDetailList() {
		return meterialDetailList;
	}

	public void setMeterialDetailList(List<MeterialDetail> meterialDetailList) {
		this.meterialDetailList = meterialDetailList;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}
}