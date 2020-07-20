/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.order.service;

import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.order.dao.OrderDao;
import com.jeesite.modules.order.entity.Order;
import com.jeesite.modules.qyhsmx.dao.QyhsMxDao;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly=true)
public class ExpireService extends CrudService<OrderDao, Order> {

	@Autowired
	private QyhsMxDao qyhsMxDao;

	/**
	 * 失效卡券
	 * @return
	 */
	@Transactional(readOnly=false)
	public Response expireCard(){
		Response response = new Response(Code.SUCCESS);
		try {
			QyhsMx qyhsMx = new QyhsMx();
			qyhsMx.setZt(QyhsMx.STATUS_CSZ);
			List<QyhsMx> qyhsMxList = qyhsMxDao.findList(qyhsMx);
			int count = 0;
			for(QyhsMx item : qyhsMxList){
				if(item != null && item.getYxqDate() != null){
					if(item.getYxqDate().before(new Date())){//如果失效时间小于当前时间，则失效
						item.setZt(QyhsMx.STATUS_TH);
						qyhsMxDao.update(item);
						count = count + 1;
					}
				}
			}
			response.setData(count);
		}catch (Exception e){
			return new Response(Code.API_ORDER_ERROR);
		}
		return response;
	}
	

}