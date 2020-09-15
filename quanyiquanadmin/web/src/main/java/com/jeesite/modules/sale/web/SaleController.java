/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sale.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sale.entity.Sale;
import com.jeesite.modules.sale.service.SaleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 二级分销后台controller
 */
@Controller
@RequestMapping(value = "${adminPath}/sale/sale")
public class SaleController extends BaseController {

	@Autowired
	private SaleService saleService;


	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Sale get(String id, boolean isNewRecord) {
		return saleService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sale:sale:view")
	@RequestMapping(value = {"list", ""})
	public String list(Sale sale, Model model) {
		model.addAttribute("sale", sale);
		return "modules/sale/saleList";
	}

}