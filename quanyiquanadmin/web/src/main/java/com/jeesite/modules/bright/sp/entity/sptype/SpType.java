/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.entity.sptype;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 商品类型Entity
 * @author 马晓亮
 * @version 2019-06-25
 */
@Table(name="t_sp_type", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="lx", attrName="lx", label="类型", isQuery=false),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="ms", attrName="ms", label="描述", isQuery=false),
		@Column(name="px", attrName="px", label="排序", isQuery=false),
		@Column(name="create_date", attrName="createDate", label="创建时间", isUpdate=false, isQuery=false),
		@Column(name="sxj", attrName="sxj", label=" 1是0否"),
		@Column(name="parent", attrName="parent", label="父id"),
		@Column(name="img", attrName="img", label=" 图片地址"),
		@Column(name="delivery_way", attrName="deliveryWay", label="配送方式  1 全部 2 自提 3 自取"),
	}, orderBy="a.px ASC"
)
public class SpType extends DataEntity<SpType> {
	
	private static final long serialVersionUID = 1L;

	//前台是否显示
	public static final long SPTYPE_DISPLAY_YES = 1;   //是
    public static final long SPTYPE_DISPLAY_NO = 0;   //否

	private Integer lx;		// 类型
	private String name;		// 名称
	private String ms;		// 描述
	private Integer px;          //排序
	private Long sxj;        //  1是0否
	private String deliveryWay;  //配送方式  1 全部 2 自提 3 自取

	private String parent;
	private String img;
	private List<SpType> innerList;
	
	public SpType() {
		this(null);
	}

	public SpType(String id){
		super(id);
	}

	public Integer getLx() {
		return lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}
	
	@NotBlank(message="名称不能为空")
	@Length(min=0, max=255, message="名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="描述长度不能超过 255 个字符")
	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public Integer getPx() {
		return px;
	}

	public void setPx(Integer px) {
		this.px = px;
	}

	public Long getSxj() {
		return sxj;
	}

	public void setSxj(Long sxj) {
		this.sxj = sxj;
	}

	public String getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<SpType> getInnerList() {
		return innerList;
	}

	public void setInnerList(List<SpType> innerList) {
		this.innerList = innerList;
	}
}