/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.exam.entity;

import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 题目类型表Entity
 * @author 马晓亮
 * @version 2019-08-02
 */
@Table(name="exam_type", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="type_name", attrName="typeName", label="type_name", queryType=QueryType.LIKE),
		@Column(name="answer_way", attrName="answerWay", label="题⽬选项类型 1 对错 2 分数权重"),
		@Column(name="content", attrName="content", label="内容"),
		@Column(name="img", attrName="img", label="背景图"),
		@Column(name="mscore", attrName="mscore", label="每题分数"),
		@Column(name="random_exam", attrName="randomExam", label="随机题数"),
		@Column(name="total_score", attrName="totalScore", label="总分"),
		@Column(name="is_active", attrName="isActive", label="题库激活 2 未激活 1 激活"),
		@Column(name="create_date", attrName="createDate", label="create_date", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="update_date", isQuery=false),
		@Column(name="status", attrName="status", label="状态", comment="状态（0正常 1删除 2停用）", isUpdate=false),
	}, orderBy="a.update_date DESC"
)
public class ExamType extends DataEntity<ExamType> {
	
	private static final long serialVersionUID = 1L;
	private String typeName;		// type_name
	private String answerWay;		// 题⽬选项类型 1 对错 2 分数权重
	private String content;		// 内容
	private String img;		// 背景图
	private Integer mscore;		// 每题分数
	private Long randomExam;		// 随机题数
	private Integer totalScore;		// 总分
	private String isActive;		// 题库激活 2 未激活 1 激活
	private List<ExamConfig> examConfigList = ListUtils.newArrayList();		// 子表列表
	
	public ExamType() {
		this(null);
	}

	public ExamType(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="type_name长度不能超过 255 个字符")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Length(min=0, max=1, message="题⽬选项类型 1 对错 2 分数权重长度不能超过 1 个字符")
	public String getAnswerWay() {
		return answerWay;
	}

	public void setAnswerWay(String answerWay) {
		this.answerWay = answerWay;
	}
	
	@Length(min=0, max=2000, message="内容长度不能超过 2000 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=900, message="背景图长度不能超过 900 个字符")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public Integer getMscore() {
		return mscore;
	}

	public void setMscore(Integer mscore) {
		this.mscore = mscore;
	}
	
	public Long getRandomExam() {
		return randomExam;
	}

	public void setRandomExam(Long randomExam) {
		this.randomExam = randomExam;
	}
	
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	
	@Length(min=0, max=1, message="题库激活 2 未激活 1 激活长度不能超过 1 个字符")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	public List<ExamConfig> getExamConfigList() {
		return examConfigList;
	}

	public void setExamConfigList(List<ExamConfig> examConfigList) {
		this.examConfigList = examConfigList;
	}
	
}