/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khcc.web;

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
import com.jeesite.modules.bright.khcc.entity.KhCc;
import com.jeesite.modules.bright.khcc.service.KhCcService;

/**
 * 客户持仓Controller
 * @author 马晓亮
 * @version 2019-07-16
 */
@Controller
@RequestMapping(value = "${adminPath}/khcc/khCc")
public class KhCcController extends BaseController {

	@Autowired
	private KhCcService khCcService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public KhCc get(String id, boolean isNewRecord) {
		return khCcService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("khcc:khCc:view")
	@RequestMapping(value = {"list", ""})
	public String list(KhCc khCc, Model model) {
		model.addAttribute("khCc", khCc);
		return "modules/bright/khcc/khCcList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("khcc:khCc:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<KhCc> listData(KhCc khCc, HttpServletRequest request, HttpServletResponse response) {
		khCc.setPage(new Page<>(request, response));
		Page<KhCc> page = khCcService.findPage(khCc);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("khcc:khCc:view")
	@RequestMapping(value = "form")
	public String form(KhCc khCc, Model model) {
		model.addAttribute("khCc", khCc);
		return "modules/bright/khcc/khCcForm";
	}

	/**
	 * 保存客户持仓
	 */
	@RequiresPermissions("khcc:khCc:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated KhCc khCc) {
		khCcService.save(khCc);
		return renderResult(Global.TRUE, text("保存客户持仓成功！"));
	}
	
	/**
	 * 删除客户持仓
	 */
	@RequiresPermissions("khcc:khCc:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(KhCc khCc) {
		khCcService.delete(khCc);
		return renderResult(Global.TRUE, text("删除客户持仓成功！"));
	}
	
}