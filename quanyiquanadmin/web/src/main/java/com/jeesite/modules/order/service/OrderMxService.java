/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.order.entity.OrderMx;
import com.jeesite.modules.order.dao.OrderMxDao;

/**
 * 订单明细表Service
 * @author 马晓亮
 * @version 2020-03-26
 */
@Service
@Transactional(readOnly=true)
public class OrderMxService extends CrudService<OrderMxDao, OrderMx> {
	
	/**
	 * 获取单条数据
	 * @param orderMx
	 * @return
	 */
	@Override
	public OrderMx get(OrderMx orderMx) {
		return super.get(orderMx);
	}
	
	/**
	 * 查询分页数据
	 * @param orderMx 查询条件
	 * @param orderMx.page 分页对象
	 * @return
	 */
	@Override
	public Page<OrderMx> findPage(OrderMx orderMx) {
		return super.findPage(orderMx);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param orderMx
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(OrderMx orderMx) {
		super.save(orderMx);
	}
	
	/**
	 * 更新状态
	 * @param orderMx
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(OrderMx orderMx) {
		super.updateStatus(orderMx);
	}
	
	/**
	 * 删除数据
	 * @param orderMx
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(OrderMx orderMx) {
		super.delete(orderMx);
	}
	
}