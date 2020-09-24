/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.order.service;

import java.util.List;

import com.google.common.base.Strings;
import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.modules.bright.khyhq.entity.KhYhq;
import com.jeesite.modules.bright.util.OrderManager;
import com.jeesite.modules.order.dao.OrderMxDao;
import com.jeesite.modules.order.entity.OrderMx;
import com.jeesite.modules.qyhsmx.dao.QyhsMxDao;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.order.entity.Order;
import com.jeesite.modules.order.dao.OrderDao;

/**
 * 订单Service
 * @author 马晓亮
 * @version 2020-03-26
 */
@Service
@Transactional(readOnly=true)
public class OrderService extends CrudService<OrderDao, Order> {

	@Autowired
	private QyhsMxDao qyhsMxDao;
	@Autowired
	private OrderMxDao orderMxDao;

	/**
	 * 订单取消 创建订单
	 * @param isAdd  是否添加库存
	 * @param order
	 * @return
	 */
	@Transactional(readOnly=false)
	public Response qxOrderLjSpKc(Boolean isAdd, Order order){
		try {
			//修改订单
			if (isAdd){   //订单未支付, 过期
				//修改订单为已取消
				order.setZt(Order.PAY_STATUS_YQX);
				dao.update(order);
				//修改权益为出售中
				QyhsMx qyhsMx = new QyhsMx();
				qyhsMx.setOrderId(order.getId());
				qyhsMx.setQyqId(order.getSpId());
				qyhsMx.setZt(QyhsMx.STATUS_DFK);
				List<QyhsMx> qyhsMxList = qyhsMxDao.findList(qyhsMx);
				qyhsMxList.forEach(item ->{
					item.setOrderId("");
					item.setZt(QyhsMx.STATUS_CSZ);
					qyhsMxDao.update(item);
				});
			}else{
				//生成订单
				dao.insert(order);
				//修改权益明细为待支付状态
				QyhsMx qyhsMx = new QyhsMx();
				qyhsMx.setQyqId(order.getSpId());
				qyhsMx.setZt(QyhsMx.STATUS_CSZ);
				//qyhsMx.setOrderBy("a.create_date ASC");
				//快到期的先卖
				qyhsMx.setOrderBy("a.yxq_date ASC");
				List<QyhsMx> qyhsMxList = qyhsMxDao.findList(qyhsMx);
				for(QyhsMx item :qyhsMxList){
					item.setOrderId(order.getId());
					item.setZt(QyhsMx.STATUS_DFK);
					qyhsMxDao.update(item);
					//添加订单明细
					OrderMx orderMx = new OrderMx();
					orderMx.setOrderId(order.getId());
					orderMx.setQymxId(item.getId());
					orderMx.preInsert();
					orderMxDao.insert(orderMx);
				}
			}
		}catch (Exception e){
			return new Response(Code.API_ORDER_ERROR);
		}
		return new Response(Code.SUCCESS);
	}
	
	/**
	 * 获取单条数据
	 * @param order
	 * @return
	 */
	@Override
	public Order get(Order order) {
		return super.get(order);
	}
	
	/**
	 * 查询分页数据
	 * @param order 查询条件
	 * @return
	 */
	@Override
	public Page<Order> findPage(Order order) {
		return super.findPage(order);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param order
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Order order) {
		super.save(order);
	}
	
	/**
	 * 更新状态
	 * @param order
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Order order) {
		super.updateStatus(order);
	}
	
	/**
	 * 删除数据
	 * @param order
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Order order) {
		super.delete(order);
	}
	
}