/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.exam.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 提目表Entity
 * @author 马晓亮
 * @version 2019-08-05
 */
@Table(name="exam_title", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="type_id", attrName="typeId", label="题目类别id"),
		@Column(name="title", attrName="title", label="题目", queryType=QueryType.LIKE),
		@Column(name="file_type", attrName="fileType", label="文件类型 1 音频 2 图片 3 文字"),
		@Column(name="tip_id", attrName="tipId", label="提示id"),
		@Column(name="imgurl", attrName="imgurl", label="图片地址"),
		@Column(name="texturl", attrName="texturl", label="文字"),
		@Column(name="musicurl", attrName="musicurl", label="音频地址"),
		@Column(name="answer", attrName="answer", label="正确选项，多选项，隔开"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="status", attrName="status", label="状态", comment="状态（0正常 1删除 2停用）", isUpdate=false),
	},joinTable={
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=ExamType.class, attrName="examType", alias="u12",
				on="u12.id = a.type_id", columns={
				@Column(name="type_name", attrName="typeName",label="题库名称", isQuery=false),
		})}, orderBy="a.update_date DESC"
)
public class ExamTitle extends DataEntity<ExamTitle> {
	
	private static final long serialVersionUID = 1L;
	private String typeId;		// 题目类别id
	private String title;		// 题目
	private String fileType;		// 文件类型 1 音频 2 图片 3 文字
	private String tipId;		// 提示id
	private String imgurl;		// 图片地址
	private String texturl;		// 文字
	private String musicurl;		// 音频地址
	private String answer;		// 正确选项，多选项，隔开
	private List<ExamOption> examOptionList = ListUtils.newArrayList();		// 子表列表

	private ExamType examType;   //题库类型
	
	public ExamTitle() {
		this(null);
	}

	public ExamTitle(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="题目类别id长度不能超过 64 个字符")
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	@Length(min=0, max=1000, message="题目长度不能超过 1000 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=1, message="文件类型 1 音频 2 图片 3 文字长度不能超过 1 个字符")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Length(min=0, max=64, message="提示id长度不能超过 64 个字符")
	public String getTipId() {
		return tipId;
	}

	public void setTipId(String tipId) {
		this.tipId = tipId;
	}
	
	@Length(min=0, max=500, message="图片地址长度不能超过 500 个字符")
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
	@Length(min=0, max=300, message="文字长度不能超过 300 个字符")
	public String getTexturl() {
		return texturl;
	}

	public void setTexturl(String texturl) {
		this.texturl = texturl;
	}
	
	@Length(min=0, max=500, message="音频地址长度不能超过 500 个字符")
	public String getMusicurl() {
		return musicurl;
	}

	public void setMusicurl(String musicurl) {
		this.musicurl = musicurl;
	}
	
	@Length(min=0, max=1000, message="正确选项，多选项，隔开长度不能超过 1000 个字符")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public List<ExamOption> getExamOptionList() {
		return examOptionList;
	}

	public void setExamOptionList(List<ExamOption> examOptionList) {
		this.examOptionList = examOptionList;
	}

	public ExamType getExamType() {
		return examType;
	}

	public void setExamType(ExamType examType) {
		this.examType = examType;
	}
}