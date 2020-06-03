/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.exam.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 提目表Entity
 * @author 马晓亮
 * @version 2019-08-05
 */
@Table(name="exam_option", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="title_id", attrName="titleId.id", label="题目id"),
		@Column(name="options", attrName="options", label="选项 ABCD"),
		@Column(name="is_check", attrName="isCheck", label="是否正确选项 1 是"),
		@Column(name="score", attrName="score", label="分数"),
		@Column(name="content", attrName="content", label="选项内容"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="status", attrName="status", label="状态", comment="状态（0正常 1删除 2停用）", isUpdate=false),
	}, orderBy="a.options ASC"
)
public class ExamOption extends DataEntity<ExamOption> {
	
	private static final long serialVersionUID = 1L;
	private ExamTitle titleId;		// 题目id 父类
	private String options;		// 选项 ABCD
	private String isCheck;		// 是否正确选项 1 是
	private Long score;		// 分数
	private String content;		// 选项内容
	
	public ExamOption() {
		this(null);
	}


	public ExamOption(ExamTitle titleId){
		this.titleId = titleId;
	}
	
	@Length(min=0, max=64, message="题目id长度不能超过 64 个字符")
	public ExamTitle getTitleId() {
		return titleId;
	}

	public void setTitleId(ExamTitle titleId) {
		this.titleId = titleId;
	}
	
	@Length(min=0, max=255, message="选项 ABCD长度不能超过 255 个字符")
	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
	
	@Length(min=0, max=1, message="是否正确选项 1 是长度不能超过 1 个字符")
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
	
	@Length(min=0, max=1000, message="选项内容长度不能超过 1000 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}