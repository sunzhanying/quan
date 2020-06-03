/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.yhdz.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.yhdz.entity.KhDz;
import com.jeesite.modules.bright.yhdz.service.KhDzService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户地址表Controller
 * @author 马晓亮
 * @version 2019-07-12
 */
@Controller
@RequestMapping(value = "${adminPath}/yhdz/khDz")
public class KhDzController extends BaseController {

	@Autowired
	private KhDzService khDzService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public KhDz get(String id, boolean isNewRecord) {
		return khDzService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("yhdz:khDz:view")
	@RequestMapping(value = {"list", ""})
	public String list(KhDz khDz, Model model) {
		model.addAttribute("khDz", khDz);
		return "modules/bright/yhdz/khDzList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("yhdz:khDz:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<KhDz> listData(KhDz khDz, HttpServletRequest request, HttpServletResponse response) {
		khDz.setPage(new Page<>(request, response));
		Page<KhDz> page = khDzService.findPage(khDz);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("yhdz:khDz:view")
	@RequestMapping(value = "form")
	public String form(KhDz khDz, Model model) {
		model.addAttribute("khDz", khDz);
		return "modules/bright/yhdz/khDzForm";
	}

	/**
	 * 保存用户地址表
	 */
	@RequiresPermissions("yhdz:khDz:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated KhDz khDz) {
		khDzService.save(khDz);
		return renderResult(Global.TRUE, text("保存用户地址表成功！"));
	}
	
	/**
	 * 删除用户地址表
	 */
	@RequiresPermissions("yhdz:khDz:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(KhDz khDz) {
		khDzService.delete(khDz);
		return renderResult(Global.TRUE, text("删除用户地址表成功！"));
	}
	
}