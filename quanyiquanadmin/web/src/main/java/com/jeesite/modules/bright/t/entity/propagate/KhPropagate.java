/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.entity.propagate;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.modules.bright.content.entity.meterail.Meterial;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.hibernate.validator.constraints.Length;
/**
 * 客户传播Entity
 * @author 李金辉
 * @version 2019-07-08
 */
@Table(name="t_kh_propagate", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="pro_khid", attrName="proKhid", label="传播人"),
		@Column(name="be_khid", attrName="beKhid", label="被邀请人"),
		@Column(name="meterial_id", attrName="meterialId", label="内容"),
		@Column(name="title", attrName="title", label="标题"),
		@Column(includeEntity=DataEntity.class),
	},joinTable = {
		@JoinTable(type = Type.JOIN,entity = KhXx.class,attrName="beKhxx",alias = "b",on ="a.be_khid=b.id",
				columns = {
				@Column(includeEntity = KhXx.class)
		})/*,
		@JoinTable(type = Type.LEFT_JOIN,entity = Meterial.class,alias = "c",on ="a.meterial_id=c.id",
				columns = {
						@Column(name="title", attrName="title"),
				})*/
},
		extWhereKeys = "khxx",
		orderBy="a.update_date DESC"
)
public class KhPropagate extends DataEntity<KhPropagate> {
	
	private static final long serialVersionUID = 1L;
	private String proKhid;		// 传播人
	private String beKhid;		// 被邀请人
	private KhXx beKhxx;		// 被邀请人
	private String meterialId;		// 内容
	private String title;		// 内容

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public KhPropagate() {
		this(null);
	}

	public KhPropagate(String id){
		super(id);
	}

	public KhXx getBeKhxx() {
		return beKhxx;
	}

	public void setBeKhxx(KhXx beKhxx) {
		this.beKhxx = beKhxx;
	}


	@Length(min=0, max=64, message="传播人长度不能超过 64 个字符")
	public String getProKhid() {
		return proKhid;
	}

	public void setProKhid(String proKhid) {
		this.proKhid = proKhid;
	}
	
	@Length(min=0, max=64, message="被邀请人长度不能超过 64 个字符")
	public String getBeKhid() {
		return beKhid;
	}

	public void setBeKhid(String beKhid) {
		this.beKhid = beKhid;
	}
	
	@Length(min=0, max=64, message="内容长度不能超过 64 个字符")
	public String getMeterialId() {
		return meterialId;
	}

	public void setMeterialId(String meterialId) {
		this.meterialId = meterialId;
	}
}