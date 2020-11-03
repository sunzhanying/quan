/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.bright.setfocus.entity.tag.Tag;
import com.jeesite.modules.bright.sp.entity.sptype.SpType;
import com.jeesite.modules.qyjg.entity.Qyjg;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 商品信息Entity
 * @author 马晓亮
 * @version 2019-06-25
 */
@Table(name="t_sp_xx", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true, isUpdate = false),
		@Column(name="ishyqy", attrName="ishyqy", label="会员权益商品", isQuery=false),
		@Column(name="spdm", attrName="spdm", label="商品代码", isQuery=false),
		@Column(name="splx", attrName="splx", label="类型 1课程，2关注专业，3关注高校，4活动，5生涯，6志愿，7优惠券，8知识，9其它，10账号分享"),
		@Column(name="spmc", attrName="spmc", label="商品名称", queryType=QueryType.LIKE),
		@Column(name="spjg", attrName="spjg", label="商品价格", isQuery=false),
		@Column(name="hyjg", attrName="hyjg", label="会员价格", isQuery=false),
		@Column(name="jldw", attrName="jldw", label="计量单位", isQuery=false),
		@Column(name="use_yhq", attrName="useYhq", label="是否允许使用优惠", comment="是否允许使用优惠：0是|1否", isQuery=false),
		@Column(name="use_jf", attrName="useJf", label="是否允许积分兑换", comment="是否允许积分兑换：积分价格为0表示不可兑换", isQuery=false),
		@Column(name="expiry_date", attrName="expiryDate", label="失效时间", isQuery=false),
		@Column(name="description", attrName="description", label="商品描述", isQuery=false),
		@Column(name="img", attrName="img", label="图片", comment="图片：缩略图", isQuery=false),
		@Column(name="img_big", attrName="imgBig", label="图片", comment="图片：大图", isQuery=false),
		@Column(name="remarks", attrName="remarks", label="备注", isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="sp_sxlx", attrName="spSxlx", label="失效性质  1固定日期 2 日"),
		@Column(name="sp_rsx", attrName="spRsx", label="购买后多少日失效"),
		@Column(name="sp_sxrq", attrName="spSxrq", label="固定失效日期"),
		@Column(name="qrcode", attrName="qrcode", label="二维码地址"),
		@Column(name="qxw", attrName="qxw", label="该商品在权限qx_data中是第几位"),
		@Column(name="yhq_id", attrName="yhqId", label="对应优惠券ID"),
		@Column(name="kc", attrName="kc", label="库存 计量单位为数量时有库存"),
		@Column(name="spfmc", attrName="spfmc", label="商品副名称"),
		@Column(name="nf", attrName="nf", label="年份"),
		@Column(name="bq", attrName="bq", label="标签"),
		@Column(name="type", attrName="type", label="类型"),
		@Column(name="source", attrName="source", label="来源"),
		@Column(name="maxCount", attrName="maxCount", label="最大数量"),
		/*@Column(name="maxCountFlag", attrName="maxCountFlag", label="最大数量"),*/
		@Column(name="inCheck", attrName="inCheck", label="校验"),
		@Column(name="inLength", attrName="inLength", label="输入长度"),
		@Column(name="inExpire", attrName="inExpire", label="限制有效期"),
		@Column(name="income_type_one", attrName="incomeTypeOne", label="一级收益方式"),
		@Column(name="money_one", attrName="moneyOne", label="一级固定金额值"),
		@Column(name="ratio_one", attrName="ratioOne", label="一级百分比"),
		@Column(name="income_type_two", attrName="incomeTypeTwo", label="二级收益方式"),
		@Column(name="money_two", attrName="moneyTwo", label="二级固定金额值"),
		@Column(name="ratio_two", attrName="ratioTwo", label="二级百分比"),
		@Column(name="sysm", attrName="sysm", label="使用说明"),
		@Column(name = "status", attrName = "status", label = "状态", isUpdate = false,
				comment = "（推荐状态：0：正常；1：删除；2：停用；3：冻结；4：审核、待审核；5：审核驳回；9：草稿）"
		),
	},joinTable={
		@JoinTable(type=Type.LEFT_JOIN, entity=SpType.class, attrName="spType", alias="u12",
				on="u12.id = a.splx", columns={
				@Column(name="id", label="商品类型id", isPK=true),
				@Column(name="name", label="商品类型名称", isQuery=false),
				@Column(name="delivery_way", attrName="deliveryWay", label="配送方式  1 全部 2 自提 3 自取"),
		}),
	}, orderBy="a.id DESC"
)
public class SpXx extends DataEntity<SpXx> {
	
	private static final long serialVersionUID = 1L;
	private String ishyqy;		// 会员权益商品
	private String spdm;		// 商品代码
	private String splx;		// 类型 1课程，2关注专业，3关注高校，4活动，5生涯，6志愿，7优惠券，8知识，9其它，10账号分享
	private String spmc;		// 商品名称
	private Double spjg;		// 商品价格
	private Double hyjg;		// 会员价格
	private String jldw;		// 计量单位
	private String useYhq;		// 是否允许使用优惠：0是|1否
	private Long useJf;		// 是否允许积分兑换：积分价格为0表示不可兑换
	private Date expiryDate;		// 失效时间
	private String description;		// 商品描述
	private String img;		// 图片：缩略图
	private String imgBig;		// 图片：大图
	private String spSxlx;		// 失效性质  1固定日期 2 日
	private Long spRsx;		// 购买后多少日失效
	private Date spSxrq;		// 固定失效日期
	private String qrcode;		// 二维码地址
	private String qxw;		// 该商品在权限qx_data中是第几位
	private String yhqId;		// 对应优惠券ID
	private Integer kc;               //库存   计量单位为数量时有库存
	private String spfmc;             //商品副名称
	private Long nf;                      //年份
	private String bq;                      //标签
	private Integer cacheNo;                      //数量
	private String type;     //类型
	private Qyjg qyjg;     //权益价格
	private String source;   //来源
	private Long cjl;         //成交量
	private Boolean issc;     //是否收藏
	private String sysm;      //使用说明
	private Long maxCount;      //最大回收数量
	private Long maxCountFlag; //最大数量，1 不可继续上传
	private String inCheck;      //校验
	private Long inLength;      //长度
	private Long inExpire; //限制有效期天数
	private String incomeTypeOne;
	private Double moneyOne;		// 体现金额
	private Double ratioOne;		// 比例
	private String incomeTypeTwo;
	private Double moneyTwo;		// 体现金额
	private Double ratioTwo;		// 比例

	private List<Tag> tagList = Lists.newArrayList(); //标签列表

	private SpType spType;     //商品类型对象
	
	public SpXx() {
		this(null);
	}

	public SpXx(String id){
		super(id);
	}

	public Integer getCacheNo() {
		return cacheNo;
	}

	public void setCacheNo(Integer cacheNo) {
		this.cacheNo = cacheNo;
	}

	@Length(min=0, max=1, message="会员权益商品长度不能超过 1 个字符")
	public String getIshyqy() {
		return ishyqy;
	}

	public void setIshyqy(String ishyqy) {
		this.ishyqy = ishyqy;
	}
	
	@Length(min=0, max=50, message="商品代码长度不能超过 50 个字符")
	public String getSpdm() {
		return spdm;
	}

	public void setSpdm(String spdm) {
		this.spdm = spdm;
	}

	@Length(min=0, max=64, message="类型 1课程，2关注专业，3关注高校，4活动，5生涯，6志愿，7优惠券，8知识，9其它，10账号分享长度不能超过 64 个字符")
	public String getSplx() {
		return splx;
	}

	public void setSplx(String splx) {
		this.splx = splx;
	}

	@Length(min=0, max=50, message="商品名称长度不能超过 50 个字符")
	public String getSpmc() {
		return spmc;
	}

	public void setSpmc(String spmc) {
		this.spmc = spmc;
	}

	public Double getSpjg() {
		return spjg;
	}

	public void setSpjg(Double spjg) {
		this.spjg = spjg;
	}

	public Double getHyjg() {
		return hyjg;
	}

	public void setHyjg(Double hyjg) {
		this.hyjg = hyjg;
	}

	@Length(min=0, max=1, message="计量单位长度不能超过 1 个字符")
	public String getJldw() {
		return jldw;
	}

	public void setJldw(String jldw) {
		this.jldw = jldw;
	}
	
	@Length(min=0, max=1, message="是否允许使用优惠长度不能超过 1 个字符")
	public String getUseYhq() {
		return useYhq;
	}

	public void setUseYhq(String useYhq) {
		this.useYhq = useYhq;
	}
	
	public Long getUseJf() {
		return useJf;
	}

	public void setUseJf(Long useJf) {
		this.useJf = useJf;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=500, message="图片长度不能超过 500 个字符")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@Length(min=0, max=500, message="图片长度不能超过 500 个字符")
	public String getImgBig() {
		return imgBig;
	}

	public void setImgBig(String imgBig) {
		this.imgBig = imgBig;
	}
	
	@Length(min=0, max=1, message="失效性质  1固定日期 2 日长度不能超过 1 个字符")
	public String getSpSxlx() {
		return spSxlx;
	}

	public void setSpSxlx(String spSxlx) {
		this.spSxlx = spSxlx;
	}
	
	public Long getSpRsx() {
		return spRsx;
	}

	public void setSpRsx(Long spRsx) {
		this.spRsx = spRsx;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSpSxrq() {
		return spSxrq;
	}

	public void setSpSxrq(Date spSxrq) {
		this.spSxrq = spSxrq;
	}
	
	@Length(min=0, max=900, message="二维码地址长度不能超过 900 个字符")
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
	@Length(min=0, max=255, message="该商品在权限qx_data中是第几位长度不能超过 255 个字符")
	public String getQxw() {
		return qxw;
	}

	public void setQxw(String qxw) {
		this.qxw = qxw;
	}
	
	@Length(min=0, max=64, message="对应优惠券ID长度不能超过 64 个字符")
	public String getYhqId() {
		return yhqId;
	}

	public void setYhqId(String yhqId) {
		this.yhqId = yhqId;
	}

	public Integer getKc() {
		return kc;
	}

	public void setKc(Integer kc) {
		this.kc = kc;
	}

	public String getSpfmc() {
		return spfmc;
	}

	public void setSpfmc(String spfmc) {
		this.spfmc = spfmc;
	}

	public Long getNf() {
		return nf;
	}

	public void setNf(Long nf) {
		this.nf = nf;
	}

	public String getBq() {
		return bq;
	}

	public void setBq(String bq) {
		this.bq = bq;
	}

	public SpType getSpType() {
		return spType;
	}

	public void setSpType(SpType spType) {
		this.spType = spType;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Qyjg getQyjg() {
		return qyjg;
	}

	public void setQyjg(Qyjg qyjg) {
		this.qyjg = qyjg;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getCjl() {
		return cjl;
	}

	public void setCjl(Long cjl) {
		this.cjl = cjl;
	}

	public Boolean getIssc() {
		return issc;
	}

	public void setIssc(Boolean issc) {
		this.issc = issc;
	}

	public String getSysm() {
		return sysm;
	}

	public void setSysm(String sysm) {
		this.sysm = sysm;
	}

	public Long getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Long maxCount) {
		this.maxCount = maxCount;
	}

	public Long getMaxCountFlag() {
		return maxCountFlag;
	}

	public void setMaxCountFlag(Long maxCountFlag) {
		this.maxCountFlag = maxCountFlag;
	}

	public String getInCheck() {
		return inCheck;
	}

	public void setInCheck(String inCheck) {
		this.inCheck = inCheck;
	}

	public Long getInLength() {
		return inLength;
	}

	public void setInLength(Long inLength) {
		this.inLength = inLength;
	}

	public Long getInExpire() {
		return inExpire;
	}

	public void setInExpire(Long inExpire) {
		this.inExpire = inExpire;
	}

	public String getIncomeTypeOne() {
		return incomeTypeOne;
	}

	public void setIncomeTypeOne(String incomeTypeOne) {
		this.incomeTypeOne = incomeTypeOne;
	}

	public Double getMoneyOne() {
		return moneyOne;
	}

	public void setMoneyOne(Double moneyOne) {
		this.moneyOne = moneyOne;
	}

	public Double getRatioOne() {
		return ratioOne;
	}

	public void setRatioOne(Double ratioOne) {
		this.ratioOne = ratioOne;
	}

	public String getIncomeTypeTwo() {
		return incomeTypeTwo;
	}

	public void setIncomeTypeTwo(String incomeTypeTwo) {
		this.incomeTypeTwo = incomeTypeTwo;
	}

	public Double getMoneyTwo() {
		return moneyTwo;
	}

	public void setMoneyTwo(Double moneyTwo) {
		this.moneyTwo = moneyTwo;
	}

	public Double getRatioTwo() {
		return ratioTwo;
	}

	public void setRatioTwo(Double ratioTwo) {
		this.ratioTwo = ratioTwo;
	}
}