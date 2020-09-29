/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.entity.khxx;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.common.mybatis.annotation.Table;

import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.bright.setfocus.entity.source.Source;
import com.jeesite.modules.bright.wx.entity.WxConfig;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 客户信息Entity
 * @author 李金辉
 * @version 2019-06-24
 */
@Table(name="t_kh_xx", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="open_id", attrName="openId", label="open_id"),
		@Column(name="union_id", attrName="unionId", label="union_id"),
		@Column(name="child_uid", attrName="childUid", label="子账号id"),
		@Column(name="share_uid", attrName="shareUid", label="分享人id"),
		@Column(name="wx_config_id", attrName="wxConfigId", label="wx项目"),
		@Column(name="propagate", attrName="propagate", label="传播权限"),
		@Column(name="khh", attrName="khh", label="客户号"),
		@Column(name="xm", attrName="xm", label="姓名", queryType=QueryType.LIKE),
		@Column(name="xb", attrName="xb", label="性别"),
		@Column(name="wxtx", attrName="wxtx", label="微信头像"),
		@Column(name="wxnc", attrName="wxnc", label="微信昵称"),
		@Column(name="email", attrName="email", label="邮箱地址"),
		@Column(name="qq", attrName="qq", label="qq号"),
		@Column(name="mima", attrName="mima", label="密码"),
		@Column(name="sj", attrName="sj", label="手机"),
		@Column(name="jf", attrName="jf", label="积分"),
		@Column(name="jf_1", attrName="jf1", label="积分备用字段，用于冻结等"),
		@Column(name="vipid", attrName="vipid", label="vip的id"),
		@Column(name="sx_date", attrName="sxDate", label="会员失效时间"),
		@Column(name="hy_date", attrName="hyDate", label="成为会员时间"),
		@Column(name="rec_id", attrName="recId", label="推荐人ID"),
		@Column(name="status", attrName="status", label="状态", isUpdate=false),
		@Column(name="type", attrName="type", label="用户来源"),
		@Column(name="qdrq", attrName="qdrq", label="签到日期"),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="update_date", attrName="updateDate", label="更新时间", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注信息", queryType=QueryType.LIKE),
		@Column(name="code", attrName="code", label="邀请码"),
		@Column(name="parentid", attrName="parentid", label="上家邀请码"),
		@Column(name="qr", attrName="qr", label="邀请码二维码"),
		@Column(name="bind_date", attrName="bindDate", label="绑定上级时间"),
		@Column(name="salesroom", attrName="salesroom", label="传播渠道/门店"),
	},joinTable={
		@JoinTable(type= Type.LEFT_JOIN, entity=WxConfig.class, alias="o",
				on="o.id = a.wx_config_id",
				columns={@Column(name="project", attrName="project", label="项目")}),
		@JoinTable(type= Type.LEFT_JOIN, entity=Source.class, alias="s",
				on="s.id = a.type",
				columns={@Column(name="name", attrName="name", label="来源")})
},extWhereKeys = "group",orderBy="a.update_date DESC"
)
public class KhXx extends DataEntity<KhXx> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// open_id
	private String unionId;		// union_id
	private String childUid;		// 子账号id
	private String shareUid;		// 分享人id
	private String wxConfigId;		// 传播权限
	private String propagate;		// 传播权限
	private String khh;		// 客户号
	private String xm;		// 姓名
	private Integer xb;		// 性别
	private String wxtx;		// 微信头像
	private String wxnc;		// 微信昵称
	private String email;		// 邮箱地址
	private String qq;		// qq号
	private String mima;		// 密码
	private String sj;		// 手机
	private Long jf;		// 积分
	private Long jf1;		// 积分备用字段，用于冻结等
	private String vipid;		// vip的id
	private Date sxDate;		// 会员失效时间
	private Date hyDate;		// 成为会员时间
	private String recId;		// 推荐人ID
	private String type;		// 用户来源
	private Date qdrq;		// 签到日期
	private Integer yhqsl;    //优惠券数量
	private Integer gwcsl;    //购物车数量
	private Integer hyksl;    //会员卡数量
	private Long crmid;      //CRM用户id

	private List<XsXx> xsXxList = ListUtils.newArrayList();		// 子表列表

	private String salesroom;   //传播渠道/门店
	private String checked;  //是否选中
	private WxConfig wxConfig;		//
	private Source source;		//
	private String code;		//邀请码
	private String parentid;
	private KhXx parentInfo;
	private String qr;
	private Date bindDate;		// 绑定上级的时间

	public String getWxConfigId() {
		return wxConfigId;
	}

	public void setWxConfigId(String wxConfigId) {
		this.wxConfigId = wxConfigId;
	}

	public WxConfig getWxConfig() {
		return wxConfig;
	}

	public void setWxConfig(WxConfig wxConfig) {
		this.wxConfig = wxConfig;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	private String token;		//

	public String getSalesroom() {
		return salesroom;
	}

	public void setSalesroom(String salesroom) {
		this.salesroom = salesroom;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public KhXx() {
		this(null);
	}

	public KhXx(String id){
		super(id);
	}

	public String getPropagate() {
		return propagate;
	}

	public void setPropagate(String propagate) {
		this.propagate = propagate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Length(min=0, max=225, message="open_id长度不能超过 225 个字符")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=255, message="union_id长度不能超过 255 个字符")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	@Length(min=0, max=64, message="子账号id长度不能超过 64 个字符")
	public String getChildUid() {
		return childUid;
	}

	public void setChildUid(String childUid) {
		this.childUid = childUid;
	}
	
	@Length(min=0, max=64, message="分享人id长度不能超过 64 个字符")
	public String getShareUid() {
		return shareUid;
	}

	public void setShareUid(String shareUid) {
		this.shareUid = shareUid;
	}
	
	@Length(min=0, max=255, message="客户号长度不能超过 255 个字符")
	public String getKhh() {
		return khh;
	}

	public void setKhh(String khh) {
		this.khh = khh;
	}
	
	@Length(min=0, max=255, message="姓名长度不能超过 255 个字符")
	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}
	
	public Integer getXb() {
		return xb;
	}

	public void setXb(Integer xb) {
		this.xb = xb;
	}
	
	@Length(min=0, max=500, message="微信头像长度不能超过 500 个字符")
	public String getWxtx() {
		return wxtx;
	}

	public void setWxtx(String wxtx) {
		this.wxtx = wxtx;
	}
	
	@Length(min=0, max=255, message="微信昵称长度不能超过 255 个字符")
	public String getWxnc() {
		return wxnc;
	}

	public void setWxnc(String wxnc) {
		this.wxnc = wxnc;
	}
	
	@Length(min=0, max=255, message="邮箱地址长度不能超过 255 个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=255, message="qq号长度不能超过 255 个字符")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@Length(min=0, max=64, message="密码长度不能超过 64 个字符")
	public String getMima() {
		return mima;
	}

	public void setMima(String mima) {
		this.mima = mima;
	}
	
	@Length(min=0, max=255, message="手机长度不能超过 255 个字符")
	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}
	
	public Long getJf() {
		return jf;
	}

	public void setJf(Long jf) {
		this.jf = jf;
	}
	
	public Long getJf1() {
		return jf1;
	}

	public void setJf1(Long jf1) {
		this.jf1 = jf1;
	}
	
	@Length(min=0, max=64, message="vip的id长度不能超过 64 个字符")
	public String getVipid() {
		return vipid;
	}

	public void setVipid(String vipid) {
		this.vipid = vipid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSxDate() {
		return sxDate;
	}

	public void setSxDate(Date sxDate) {
		this.sxDate = sxDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHyDate() {
		return hyDate;
	}

	public void setHyDate(Date hyDate) {
		this.hyDate = hyDate;
	}
	
	@Length(min=0, max=64, message="推荐人ID长度不能超过 64 个字符")
	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}
	
	@Length(min=0, max=1, message="用户来源长度不能超过 1 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getQdrq() {
		return qdrq;
	}

	public void setQdrq(Date qdrq) {
		this.qdrq = qdrq;
	}

	public Integer getYhqsl() {
		return yhqsl;
	}

	public void setYhqsl(Integer yhqsl) {
		this.yhqsl = yhqsl;
	}

	public List<XsXx> getXsXxList() {
		return xsXxList;
	}

	public void setXsXxList(List<XsXx> xsXxList) {
		this.xsXxList = xsXxList;
	}

	public Integer getGwcsl() {
		return gwcsl;
	}

	public void setGwcsl(Integer gwcsl) {
		this.gwcsl = gwcsl;
	}

	public Long getCrmid() {
		return crmid;
	}

	public void setCrmid(Long crmid) {
		this.crmid = crmid;
	}

	public Integer getHyksl() {
		return hyksl;
	}

	public void setHyksl(Integer hyksl) {
		this.hyksl = hyksl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public KhXx getParentInfo() {
		return parentInfo;
	}

	public void setParentInfo(KhXx parentInfo) {
		this.parentInfo = parentInfo;
	}

	public String getQr() {
		return qr;
	}

	public void setQr(String qr) {
		this.qr = qr;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}
}