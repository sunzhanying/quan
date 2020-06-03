/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.order.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.order.entity.OrderMx;
import com.jeesite.modules.order.service.OrderMxService;

/**
 * 订单明细表Controller
 * @author 马晓亮
 * @version 2020-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/order/orderMx")
public class OrderMxController extends BaseController {

	@Autowired
	private OrderMxService orderMxService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public OrderMx get(String id, boolean isNewRecord) {
		return orderMxService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("order:orderMx:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderMx orderMx, Model model) {
		model.addAttribute("orderMx", orderMx);
		return "modules/order/orderMxList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("order:orderMx:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<OrderMx> listData(OrderMx orderMx, HttpServletRequest request, HttpServletResponse response) {
		orderMx.setPage(new Page<>(request, response));
		Page<OrderMx> page = orderMxService.findPage(orderMx);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("order:orderMx:view")
	@RequestMapping(value = "form")
	public String form(OrderMx orderMx, Model model) {
		model.addAttribute("orderMx", orderMx);
		return "modules/order/orderMxForm";
	}

	/**
	 * 保存订单明细表
	 */
	@RequiresPermissions("order:orderMx:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated OrderMx orderMx) {
		orderMxService.save(orderMx);
		return renderResult(Global.TRUE, text("保存订单明细表成功！"));
	}
	
	/**
	 * 删除订单明细表
	 */
	@RequiresPermissions("order:orderMx:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(OrderMx orderMx) {
		orderMxService.delete(orderMx);
		return renderResult(Global.TRUE, text("删除订单明细表成功！"));
	}
	
}