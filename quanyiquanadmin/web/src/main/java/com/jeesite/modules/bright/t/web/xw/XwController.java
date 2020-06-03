/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.web.xw;

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
import com.jeesite.modules.bright.t.entity.xw.Xw;
import com.jeesite.modules.bright.t.service.xw.XwService;

/**
 * 客户行为配置Controller
 * @author 李金辉
 * @version 2019-06-27
 */
@Controller
@RequestMapping(value = "${adminPath}/t/xw/xw")
public class XwController extends BaseController {

	@Autowired
	private XwService xwService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Xw get(String id, boolean isNewRecord) {
		return xwService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("t:xw:xw:view")
	@RequestMapping(value = {"list", ""})
	public String list(Xw xw, Model model) {
		model.addAttribute("xw", xw);
		return "modules/bright/t/xw/xwList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("t:xw:xw:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Xw> listData(Xw xw, HttpServletRequest request, HttpServletResponse response) {
		xw.setPage(new Page<>(request, response));
		Page<Xw> page = xwService.findPage(xw);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("t:xw:xw:view")
	@RequestMapping(value = "form")
	public String form(Xw xw, Model model) {
		model.addAttribute("xw", xw);
		return "modules/bright/t/xw/xwForm";
	}

	/**
	 * 保存客户行为配置
	 */
	@RequiresPermissions("t:xw:xw:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Xw xw) {
		xwService.save(xw);
		return renderResult(Global.TRUE, text("保存客户行为配置成功！"));
	}
	
}