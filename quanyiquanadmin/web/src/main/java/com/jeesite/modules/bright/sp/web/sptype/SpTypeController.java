/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.web.sptype;

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
import com.jeesite.modules.bright.sp.entity.sptype.SpType;
import com.jeesite.modules.bright.sp.service.sptype.SpTypeService;

/**
 * 商品类型Controller
 * @author 马晓亮
 * @version 2019-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sp/sptype/spType")
public class SpTypeController extends BaseController {

	@Autowired
	private SpTypeService spTypeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SpType get(String id, boolean isNewRecord) {
		return spTypeService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sp:sptype:spType:view")
	@RequestMapping(value = {"list", ""})
	public String list(SpType spType, Model model) {
		model.addAttribute("spType", spType);
		return "modules/bright/sp/sptype/spTypeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sp:sptype:spType:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SpType> listData(SpType spType, HttpServletRequest request, HttpServletResponse response) {
		spType.setPage(new Page<>(request, response));
		Page<SpType> page = spTypeService.findPage(spType);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sp:sptype:spType:view")
	@RequestMapping(value = "form")
	public String form(SpType spType, Model model) {
		if (spType.getIsNewRecord()){
			spType.setSxj(1L);
			spType.setDeliveryWay("1");
		}
		model.addAttribute("spType", spType);
		return "modules/bright/sp/sptype/spTypeForm";
	}

	/**
	 * 保存商品类型
	 */
	@RequiresPermissions("sp:sptype:spType:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SpType spType) {
		spTypeService.save(spType);
		return renderResult(Global.TRUE, text("保存商品类型成功！"));
	}
	
	/**
	 * 删除商品类型
	 */
	@RequiresPermissions("sp:sptype:spType:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SpType spType) {
		spTypeService.delete(spType);
		return renderResult(Global.TRUE, text("删除商品类型成功！"));
	}
	
}