/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.entity.khxx;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 客户信息Entity
 * @author 李金辉
 * @version 2019-06-24
 */
@Table(name="t_xs_xx", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="xm", attrName="xm", label="姓名"),
		@Column(name="tx", attrName="tx", label="头像"),
		@Column(name="xb", attrName="xb", label="性别"),
		@Column(name="schid", attrName="schid", label="就读学校"),
		@Column(name="rxnf", attrName="rxnf", label="入学年份"),
		@Column(name="lxfs", attrName="lxfs", label="联系方式"),
		@Column(name="khid", attrName="khid.id", label="客户id"),
		@Column(name="xxmc", attrName="xxmc", label="学校名称"),
		@Column(name="xxlx", attrName="xxlx", label="学校类型"),
		@Column(name="isjs", attrName="isjs", label="是否竞赛 1是0否"),
		@Column(name="jskm", attrName="jskm", label="竞赛课目"),
		@Column(name="jsk", attrName="jsk", label="加三科，"),
		@Column(name="qx_data", attrName="qxData", label="权限，第一个代表生涯权限，第二个代表天眼智能0是未开通，1是开通"),
		@Column(name="gksf", attrName="gksf", label="高考省份"),
		@Column(name="phcs", attrName="phcs", label="偏好就读城市   ，分割"),
		@Column(name="phzy", attrName="phzy", label="偏好专业    ， 分割"),
		@Column(name="xkkm", attrName="xkkm", label="选考科目  ， 分割"),
		@Column(name="sr", attrName="sr", label="色弱", comment="色弱：有、无、未知"),
		@Column(name="sm", attrName="sm", label="色盲", comment="色盲：有、无、未知"),
		@Column(name="kjsxf", attrName="kjsxf", label="可接受的学费", comment="可接受的学费：10000元以内、10000-25000元 不限"),
		@Column(name="sg", attrName="sg", label="身高"),
		@Column(name="tz", attrName="tz", label="体重"),
		@Column(name="ysyz", attrName="ysyz", label="应试语种"),
	}, orderBy="a.id ASC"
)
public class XsXx extends DataEntity<XsXx> {
	
	private static final long serialVersionUID = 1L;
	private String xm;		// 姓名
	private String tx;		// 头像
	private Integer xb;		// 性别
	private String schid;		// 就读学校
	private String rxnf;		// 入学年份
	private String lxfs;		// 联系方式
	private KhXx khid;		// 客户id 父类
	private String xxmc;		// 学校名称
	private String xxlx;		// 学校类型
	private String isjs;		// 是否竞赛 1是0否
	private String jskm;		// 竞赛课目
	private String jsk;		// 加三科，
	private String qxData;		// 权限，第一个代表生涯权限，第二个代表天眼智能0是未开通，1是开通
	private String gksf;		// 高考省份
	private String phcs;		// 偏好就读城市   ，分割
	private String phzy;		// 偏好专业    ， 分割
	private String xkkm;		// 选考科目  ， 分割
	private String sr;		// 色弱：有、无、未知
	private String sm;		// 色盲：有、无、未知
	private String kjsxf;		// 可接受的学费：10000元以内、10000-25000元 不限
	private Long sg;		// 身高
	private Long tz;		// 体重
	private String ysyz;		// 应试语种
	
	public XsXx() {
		this(null);
	}


	public XsXx(KhXx khid){
		this.khid = khid;
	}
	
	@Length(min=0, max=50, message="姓名长度不能超过 50 个字符")
	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}
	
	@Length(min=0, max=500, message="头像长度不能超过 500 个字符")
	public String getTx() {
		return tx;
	}

	public void setTx(String tx) {
		this.tx = tx;
	}
	
	public Integer getXb() {
		return xb;
	}

	public void setXb(Integer xb) {
		this.xb = xb;
	}
	
	@Length(min=0, max=64, message="就读学校长度不能超过 64 个字符")
	public String getSchid() {
		return schid;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}
	
	@Length(min=0, max=4, message="入学年份长度不能超过 4 个字符")
	public String getRxnf() {
		return rxnf;
	}

	public void setRxnf(String rxnf) {
		this.rxnf = rxnf;
	}
	
	@Length(min=0, max=50, message="联系方式长度不能超过 50 个字符")
	public String getLxfs() {
		return lxfs;
	}

	public void setLxfs(String lxfs) {
		this.lxfs = lxfs;
	}
	
	@Length(min=0, max=64, message="客户id长度不能超过 64 个字符")
	public KhXx getKhid() {
		return khid;
	}

	public void setKhid(KhXx khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=255, message="学校名称长度不能超过 255 个字符")
	public String getXxmc() {
		return xxmc;
	}

	public void setXxmc(String xxmc) {
		this.xxmc = xxmc;
	}
	
	@Length(min=0, max=2, message="学校类型长度不能超过 2 个字符")
	public String getXxlx() {
		return xxlx;
	}

	public void setXxlx(String xxlx) {
		this.xxlx = xxlx;
	}
	
	@Length(min=0, max=1, message="是否竞赛 1是0否长度不能超过 1 个字符")
	public String getIsjs() {
		return isjs;
	}

	public void setIsjs(String isjs) {
		this.isjs = isjs;
	}
	
	@Length(min=0, max=255, message="竞赛课目长度不能超过 255 个字符")
	public String getJskm() {
		return jskm;
	}

	public void setJskm(String jskm) {
		this.jskm = jskm;
	}
	
	@Length(min=0, max=255, message="加三科，长度不能超过 255 个字符")
	public String getJsk() {
		return jsk;
	}

	public void setJsk(String jsk) {
		this.jsk = jsk;
	}
	
	@NotBlank(message="权限，第一个代表生涯权限，第二个代表天眼智能0是未开通，1是开通不能为空")
	@Length(min=0, max=10, message="权限，第一个代表生涯权限，第二个代表天眼智能0是未开通，1是开通长度不能超过 10 个字符")
	public String getQxData() {
		return qxData;
	}

	public void setQxData(String qxData) {
		this.qxData = qxData;
	}
	
	@Length(min=0, max=100, message="高考省份长度不能超过 100 个字符")
	public String getGksf() {
		return gksf;
	}

	public void setGksf(String gksf) {
		this.gksf = gksf;
	}
	
	@Length(min=0, max=255, message="偏好就读城市   ，分割长度不能超过 255 个字符")
	public String getPhcs() {
		return phcs;
	}

	public void setPhcs(String phcs) {
		this.phcs = phcs;
	}
	
	@Length(min=0, max=512, message="偏好专业    ， 分割长度不能超过 512 个字符")
	public String getPhzy() {
		return phzy;
	}

	public void setPhzy(String phzy) {
		this.phzy = phzy;
	}
	
	@Length(min=0, max=100, message="选考科目  ， 分割长度不能超过 100 个字符")
	public String getXkkm() {
		return xkkm;
	}

	public void setXkkm(String xkkm) {
		this.xkkm = xkkm;
	}
	
	@Length(min=0, max=10, message="色弱长度不能超过 10 个字符")
	public String getSr() {
		return sr;
	}

	public void setSr(String sr) {
		this.sr = sr;
	}
	
	@Length(min=0, max=10, message="色盲长度不能超过 10 个字符")
	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}
	
	@Length(min=0, max=50, message="可接受的学费长度不能超过 50 个字符")
	public String getKjsxf() {
		return kjsxf;
	}

	public void setKjsxf(String kjsxf) {
		this.kjsxf = kjsxf;
	}
	
	public Long getSg() {
		return sg;
	}

	public void setSg(Long sg) {
		this.sg = sg;
	}
	
	public Long getTz() {
		return tz;
	}

	public void setTz(Long tz) {
		this.tz = tz;
	}
	
	@Length(min=0, max=20, message="应试语种长度不能超过 20 个字符")
	public String getYsyz() {
		return ysyz;
	}

	public void setYsyz(String ysyz) {
		this.ysyz = ysyz;
	}
	
}