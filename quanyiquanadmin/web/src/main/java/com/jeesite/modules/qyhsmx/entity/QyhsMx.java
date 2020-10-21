/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyhsmx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.bright.wx.entity.WxConfig;
import com.jeesite.modules.order.entity.Order;
import com.jeesite.modules.qyhs.entity.Qyhs;
import com.jeesite.modules.qyjg.entity.Qyjg;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;

/**
 * 权益回收明细Entity
 * @author 马晓亮
 * @version 2020-03-25
 */
@Table(name="a_qyhs_mx", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="qyhs_id", attrName="qyhsId", label="权益回收id", queryType = QueryType.LIKE),
		@Column(name="qyq_id", attrName="qyqId", label="权益券id"),
		@Column(name="khid", attrName="khid", label="客户id"),
		@Column(name="type", attrName="type", label="卡类型"),
		@Column(name="kh", attrName="kh", label="卡号", queryType=QueryType.LIKE),
		@Column(name="km", attrName="km", label="卡密", queryType=QueryType.LIKE),
		@Column(name="img", attrName="img", label="卡图"),
		@Column(name="yxq_date", attrName="yxqDate", label="有效期"),
		@Column(name="order_id", attrName="orderId", label="订单id"),
		@Column(name="jszt", attrName="jszt", label="结算状态 1 未结算 2 已结算"),
		@Column(name="sy", attrName="sy", label="sy"),
		@Column(name="sell_price", attrName="sellPrice", label="sy"),
		@Column(name="sqdh", attrName="sqdh", label="申请单号"),
		@Column(name="zt", attrName="zt", label="状态 1 待审核 2 审核失败 3 出售中 4 待付款 5 已出售"),
		@Column(includeEntity=DataEntity.class),
	},joinTable={
		@JoinTable(type= Type.LEFT_JOIN, entity=KhXx.class, attrName="khXx", alias="k",
				on="k.id = a.khid",
				columns={
					@Column(name="wxtx", attrName="wxtx", label="微信头像"),
					@Column(name="wxnc", attrName="wxnc", label="微信昵称", queryType = QueryType.LIKE),
					@Column(name="sj", attrName="sj", label="微信昵称", queryType = QueryType.LIKE),
				}),
	}, extColumnKeys="extColumn", extWhereKeys = "extWhere",
		orderBy="a.type,a.qyhs_id DESC"
)
public class QyhsMx extends DataEntity<QyhsMx> {

	//卖家权益券状态
	public static final String STATUS_DSH = "1";   //待审核
	public static final String STATUS_TH = "2";   //退回，对应无效券
	public static final String STATUS_CSZ = "3";   //出售中
	public static final String STATUS_DFK = "4";   //待付款
	public static final String STATUS_YFK = "5";   //已付款

	public static final String STATUS_JS_WJS = "1";   //未结算
	public static final String STATUS_JS_JSZ = "2";   //结算中
	public static final String STATUS_JS_YJS = "3";   //已结算
	public static final String STATUS_JS_SHBTG = "4";   //审核不通过，对应表提现审核表a_txsh，zt=3，审核不通过，页面也可操作

	private static final long serialVersionUID = 1L;
	private String qyhsId;		// 权益回收id
	private String qyqId;		// 权益券id
	private String khid;		// 客户id
	private String type;		// 卡类型
	private String kh;		// 卡号
	private String km;		// 卡密
	private String img;		// 卡图
	private Date yxqDate;    //有效期
	private String zt;		// 状态 1 待审核 2 审核失败 3 出售中 4 待付款 5 已出售
	private Long count;     //入库数
	private Long ycs;      //已出售
	private Long sykc;      //剩余库存
	private String orderId;  //订单id
	private String jszt;   //结算状态 1 未结算 2 已结算
	private Double sy;   //收益
	private Double sellPrice;   //售出价

	private String sqdh;   //申请单号
	private Double sum;    //收益

	private KhXx khXx;      //客户
	private SpXx spXx;       //权益
	private Qyhs qyhs;      //权益回收
	private Qyjg qyjg;    //权益价格
	private Order order;   //订单

	public QyhsMx() {
		this(null);
	}

	public QyhsMx(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="权益回收id长度不能超过 64 个字符")
	public String getQyhsId() {
		return qyhsId;
	}

	public void setQyhsId(String qyhsId) {
		this.qyhsId = qyhsId;
	}
	
	@Length(min=0, max=64, message="客户id长度不能超过 64 个字符")
	public String getKhid() {
		return khid;
	}

	public void setKhid(String khid) {
		this.khid = khid;
	}
	
	@Length(min=0, max=1, message="卡类型长度不能超过 1 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="卡号长度不能超过 255 个字符")
	public String getKh() {
		return kh;
	}

	public void setKh(String kh) {
		this.kh = kh;
	}
	
	@Length(min=0, max=255, message="卡密长度不能超过 255 个字符")
	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}
	
	@Length(min=0, max=300, message="卡图长度不能超过 300 个字符")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@Length(min=0, max=1, message="状态 1 待审核 2 审核失败 3 出售中 4 待付款 5 已出售长度不能超过 1 个字符")
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getYxqDate() {
		return yxqDate;
	}

	public void setYxqDate(Date yxqDate) {
		this.yxqDate = yxqDate;
	}

	public KhXx getKhXx() {
		return khXx;
	}

	public void setKhXx(KhXx khXx) {
		this.khXx = khXx;
	}

	public SpXx getSpXx() {
		return spXx;
	}

	public void setSpXx(SpXx spXx) {
		this.spXx = spXx;
	}

	public String getQyqId() {
		return qyqId;
	}

	public void setQyqId(String qyqId) {
		this.qyqId = qyqId;
	}

	public Qyhs getQyhs() {
		return qyhs;
	}

	public void setQyhs(Qyhs qyhs) {
		this.qyhs = qyhs;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getYcs() {
		return ycs;
	}

	public void setYcs(Long ycs) {
		this.ycs = ycs;
	}

	public Long getSykc() {
		return sykc;
	}

	public void setSykc(Long sykc) {
		this.sykc = sykc;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Qyjg getQyjg() {
		return qyjg;
	}

	public void setQyjg(Qyjg qyjg) {
		this.qyjg = qyjg;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getJszt() {
		return jszt;
	}

	public void setJszt(String jszt) {
		this.jszt = jszt;
	}

	public Double getSy() {
		return sy;
	}

	public void setSy(Double sy) {
		this.sy = sy;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getSqdh() {
		return sqdh;
	}

	public void setSqdh(String sqdh) {
		this.sqdh = sqdh;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public void setYxqDate_gte(Date yxqDate){
		yxqDate = DateUtils.getOfDayLast(yxqDate);
		this.getSqlMap().getWhere().and("yxq_date", QueryType.GTE, yxqDate);
	}

	public void setYxqDate_lte(Date yxqDate){
		yxqDate = DateUtils.getOfDayLast(yxqDate);
		this.getSqlMap().getWhere().and("yxq_date", QueryType.LTE, yxqDate);
	}
}