/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sale.service;

import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.API.weixin.api.PayMchAPI;
import com.jeesite.API.weixin.api.TwoPayMchAPI;
import com.jeesite.API.weixin.bean.paymch.Transfers;
import com.jeesite.API.weixin.bean.paymch.TransfersResult;
import com.jeesite.API.weixin.util.IdGen;
import com.jeesite.API.weixin.util.PayUtil;
import com.jeesite.modules.bright.t.dao.khxx.KhXxDao;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.qyhsmx.dao.QyhsMxDao;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import com.jeesite.modules.sale.dao.SaleDao;
import com.jeesite.modules.sale.entity.Sale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.txsh.entity.Txsh;
import com.jeesite.modules.txsh.dao.TxshDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提现审核Service
 * @author 马晓亮
 * @version 2020-03-31
 */
@Service
@Transactional(readOnly=true)
public class SaleService extends CrudService<SaleDao, Sale> {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private SaleDao saleDao;


	/**
	 * 获取单条数据
	 * @return
	 */
	@Override
	public Sale get(Sale sale) {
		return super.get(sale);
	}
	
	/**
	 * 查询分页数据
	 * @return
	 */
	@Override
	public Page<Sale> findPage(Sale sale) {
		return super.findPage(sale);
	}


	/**
	 * 保存数据（插入或更新）
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Sale sale) {
		super.save(sale);
	}
	
	/**
	 * 更新状态
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Sale sale) {
		super.updateStatus(sale);
	}
	
	/**
	 * 删除数据
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Sale sale) {
		super.delete(sale);
	}


	/**
	 * 查询分页数据
	 * @param
	 * @return
	 */
	public List<Map<String,String>> findPayPage(Map<String,String> param) {
		String orderId = param.get("orderId");
		String id = param.get("id");
		String wxnc = param.get("wxnc");
		String zt = param.get("zt");
		String startDate = param.get("startDate");
		String endDate = param.get("endDate");
		List<Map<String,String>> list = dao.findAllList(orderId,id,wxnc,zt,startDate,endDate);
		return list;
	}


}