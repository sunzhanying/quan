/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.order.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.order.entity.OrderMx;

/**
 * 订单明细表DAO接口
 * @author 马晓亮
 * @version 2020-03-26
 */
@MyBatisDao
public interface OrderMxDao extends CrudDao<OrderMx> {
	
}