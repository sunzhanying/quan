/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sale.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.sale.dao.SaleDao;
import com.jeesite.modules.sale.entity.Sale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


	public String getParentOne(String khid) {
		return saleDao.getParentOne(khid);
	}

	public List<Sale> getSaleListByKhid(String khid) {
		return saleDao.getSaleListByKhid(khid);
	}

}