/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.bright.sp.service.SpXxService;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.order.entity.Order;
import com.jeesite.modules.order.service.OrderService;

/**
 * 订单Controller
 * @author 马晓亮
 * @version 2020-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/order/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private SpXxService spXxService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Order get(String id, boolean isNewRecord) {
		return orderService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:order:view")
	@RequestMapping(value = {"list", ""})
	public String list(Order order, Model model) {
		model.addAttribute("order", order);
		return "modules/order/orderList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:order:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Order> listData(Order order, HttpServletRequest request, HttpServletResponse response) {
		order.setPage(new Page<>(request, response));
		Page<Order> page = orderService.findPage(order);
		page.getList().forEach(item ->{
			item.setSpXx(spXxService.get(item.getSpId()));
		});
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:order:view")
	@RequestMapping(value = "form")
	public String form(Order order, Model model) {
		order.setSpXx(spXxService.get(order.getSpId()));
		model.addAttribute("order", order);
		QyhsMx qyhsMx = new QyhsMx();
		qyhsMx.setOrderId(order.getId());
		model.addAttribute("qyhsMx", qyhsMx);
		return "modules/order/orderForm";
	}

	/**
	 * 保存订单
	 */
	@RequiresPermissions("order:order:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Order order) {
		orderService.save(order);
		return renderResult(Global.TRUE, text("保存订单成功！"));
	}
	
	/**
	 * 删除订单
	 */
	@RequiresPermissions("order:order:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Order order) {
		orderService.delete(order);
		return renderResult(Global.TRUE, text("删除订单成功！"));
	}
	
}