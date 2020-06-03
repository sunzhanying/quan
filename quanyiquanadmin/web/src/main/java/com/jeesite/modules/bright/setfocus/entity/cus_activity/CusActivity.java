/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.entity.cus_activity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import com.jeesite.common.utils.excel.annotation.ExcelField.Align;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

/**
 * 客户活跃度事件Entity
 * @author liqingfeng
 * @version 2019-07-22
 */
@Table(name="cus_activity", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="event", attrName="event", label="event"),
		@Column(name="event_id", attrName="eventId", label="event_id"),
		@Column(name="score_type", attrName="scoreType", label="评分类型 1 增加 -1减少"),
		@Column(name="score", attrName="score", label="score"),
		@Column(includeEntity= DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
	}, orderBy="a.update_date DESC"
)
public class CusActivity extends DataEntity<CusActivity> {
	
	private static final long serialVersionUID = 1L;
	private String event;		// event
	private String eventId;		// event_id
	private String scoreType;		// 评分类型 1 增加 -1减少
	private String score;		// score
	private String delFlag;		// 删除标记


	public CusActivity() {
		this(null);
	}

	public CusActivity(String id){
		super(id);
	}

	@Valid
	@ExcelFields({@ExcelField(
			title = "事件",
			attrName = "event",
			align =Align.CENTER,
			sort = 10
	), @ExcelField(
			title = "事件ID",
			attrName = "eventId",
			align =Align.CENTER,
			sort = 20
	), @ExcelField(
			title = "评分",
			attrName = "score",
			align = Align.LEFT,
			sort = 30
	)})
	@Length(min=0, max=255, message="event长度不能超过 255 个字符")
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	@Length(min=0, max=255, message="event_id长度不能超过 255 个字符")
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	@Length(min=0, max=255, message="评分类型 1 增加 -1减少长度不能超过 255 个字符")
	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}
	
	@Length(min=0, max=255, message="score长度不能超过 255 个字符")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}