/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sale.web;

import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sale.entity.Sale;
import com.jeesite.modules.sale.entity.SaleDto;
import com.jeesite.modules.sale.service.SaleService;
import com.jeesite.utils.Paper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

	@RequestMapping(value = "listSaleData")
	@ResponseBody
	public Page<SaleDto> listSaleData(HttpServletRequest request, HttpServletResponse response) {
		Page pageFront = new Page<>(request, response);
		Page<SaleDto> page = new Page<SaleDto>();
		//PageHelper.startPage(pageFront.getPageNo(),pageFront.getPageSize());
		List<SaleDto> list = saleService.getSaleListAll("");
		Paper<SaleDto> paper = new Paper<SaleDto>(pageFront.getPageNo(),pageFront.getPageSize(),list);//paper.getDataList()就是子数组数据
		page.setCount(list.size());
		page.setList(paper.getDataList());
		page.setPageNo(pageFront.getPageNo());
		page.setPageSize(pageFront.getPageSize());
		return page;
	}

}