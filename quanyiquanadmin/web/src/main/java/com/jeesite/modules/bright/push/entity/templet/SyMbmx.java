/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.entity.templet;

import org.hibernate.validator.constraints.Length;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 生涯模板Entity
 * @author 李金辉
 * @version 2019-07-17
 */
@Table(name="t_sy_mbmx", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="mbid", attrName="mbid.id", label="对应模板ID"),
		@Column(name="syrwbm", attrName="syrwbm", label="生涯任务编码"),
		@Column(name="syrwmc", attrName="syrwmc", label="生涯任务名称"),
		@Column(name="type", attrName="type", label="任务类型"),
		@Column(name="jfjg", attrName="jfjg", label="完成项目可获积分，非零时表示此为积分红包任务"),
		@Column(name="qdrq", attrName="qdrq", label="任务启动日期"),
		@Column(name="yxq", attrName="yxq", label="任务有效期"),
		@Column(name="rwdz", attrName="rwdz", label="任务动作"),
		@Column(name="tstype", attrName="tstype", label="推送类型"),
		@Column(name="qdxh", attrName="qdxh", label="启动序号"),
		@Column(name="tian", attrName="tian", label="天数"),
		@Column(name="rwlj", attrName="rwlj", label="任务对应链接"),
		@Column(name="spid", attrName="spid", label="任务对应商品"),
		@Column(name="cj", attrName="cj", label="任务对应考试成绩"),
		@Column(name="rq", attrName="rq", label="指定日期启动"),
		@Column(name="ms", attrName="ms", label="描述"),
		@Column(name="ljtype", attrName="ljtype", label="链接类型"),
		@Column(name="create_by", attrName="createBy", label="创建者", isUpdate=false, isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_by", attrName="updateBy", label="更新者", isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="del_flag", attrName="delFlag", label="删除标记"),
		@Column(name="is_xs", attrName="isXs", label="绑定学生1，0不绑定"),
		@Column(name="lx_order", attrName="lxOrder", label="购买类型"),
	}, orderBy="a.create_date ASC"
)
public class SyMbmx extends DataEntity<SyMbmx> {
	
	private static final long serialVersionUID = 1L;
	private SyMb mbid;		// 对应模板ID 父类
	private String syrwbm;		// 生涯任务编码
	private String syrwmc;		// 生涯任务名称
	private String type;		// 任务类型
	private Long jfjg;		// 完成项目可获积分，非零时表示此为积分红包任务
	private Date qdrq;		// 任务启动日期
	private Long yxq;		// 任务有效期
	private String rwdz;		// 任务动作
	private String tstype;		// 推送类型
	private Integer qdxh;		// 启动序号
	private Long tian;		// 天数
	private String rwlj;		// 任务对应链接
	private String spid;		// 任务对应商品
	private Double cj;		// 任务对应考试成绩
	private Date rq;		// 指定日期启动
	private String ms;		// 描述
	private String ljtype;		// 链接类型
	private String delFlag;		// 删除标记
	private Integer isXs;		// 绑定学生1，0不绑定
	private Integer lxOrder;		// 购买类型
	
	public SyMbmx() {
		this(null);
	}


	public SyMbmx(SyMb mbid){
		this.mbid = mbid;
	}
	
	@Length(min=0, max=11, message="对应模板ID长度不能超过 11 个字符")
	public SyMb getMbid() {
		return mbid;
	}

	public void setMbid(SyMb mbid) {
		this.mbid = mbid;
	}
	
	@Length(min=0, max=50, message="生涯任务编码长度不能超过 50 个字符")
	public String getSyrwbm() {
		return syrwbm;
	}

	public void setSyrwbm(String syrwbm) {
		this.syrwbm = syrwbm;
	}
	
	@Length(min=0, max=255, message="生涯任务名称长度不能超过 255 个字符")
	public String getSyrwmc() {
		return syrwmc;
	}

	public void setSyrwmc(String syrwmc) {
		this.syrwmc = syrwmc;
	}
	
	@Length(min=0, max=1, message="任务类型长度不能超过 1 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Long getJfjg() {
		return jfjg;
	}

	public void setJfjg(Long jfjg) {
		this.jfjg = jfjg;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getQdrq() {
		return qdrq;
	}

	public void setQdrq(Date qdrq) {
		this.qdrq = qdrq;
	}
	
	public Long getYxq() {
		return yxq;
	}

	public void setYxq(Long yxq) {
		this.yxq = yxq;
	}
	
	@Length(min=0, max=255, message="任务动作长度不能超过 255 个字符")
	public String getRwdz() {
		return rwdz;
	}

	public void setRwdz(String rwdz) {
		this.rwdz = rwdz;
	}
	
	@Length(min=0, max=1, message="推送类型长度不能超过 1 个字符")
	public String getTstype() {
		return tstype;
	}

	public void setTstype(String tstype) {
		this.tstype = tstype;
	}
	
	public Integer getQdxh() {
		return qdxh;
	}

	public void setQdxh(Integer qdxh) {
		this.qdxh = qdxh;
	}
	
	public Long getTian() {
		return tian;
	}

	public void setTian(Long tian) {
		this.tian = tian;
	}
	
	@Length(min=0, max=800, message="任务对应链接长度不能超过 800 个字符")
	public String getRwlj() {
		return rwlj;
	}

	public void setRwlj(String rwlj) {
		this.rwlj = rwlj;
	}
	
	@Length(min=0, max=64, message="任务对应商品长度不能超过 64 个字符")
	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}
	
	public Double getCj() {
		return cj;
	}

	public void setCj(Double cj) {
		this.cj = cj;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRq() {
		return rq;
	}

	public void setRq(Date rq) {
		this.rq = rq;
	}
	
	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}
	
	@Length(min=0, max=1, message="链接类型长度不能超过 1 个字符")
	public String getLjtype() {
		return ljtype;
	}

	public void setLjtype(String ljtype) {
		this.ljtype = ljtype;
	}
	

	@Length(min=0, max=1, message="删除标记长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	public Integer getIsXs() {
		return isXs;
	}

	public void setIsXs(Integer isXs) {
		this.isXs = isXs;
	}
	
	public Integer getLxOrder() {
		return lxOrder;
	}

	public void setLxOrder(Integer lxOrder) {
		this.lxOrder = lxOrder;
	}
	
}