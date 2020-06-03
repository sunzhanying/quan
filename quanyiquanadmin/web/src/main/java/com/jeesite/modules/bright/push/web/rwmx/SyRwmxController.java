/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.web.rwmx;

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
import com.jeesite.modules.bright.push.entity.rwmx.SyRwmx;
import com.jeesite.modules.bright.push.service.rwmx.SyRwmxService;

/**
 * 生涯任务明细Controller
 * @author 李金辉
 * @version 2019-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/push/rwmx/syRwmx")
public class SyRwmxController extends BaseController {

	@Autowired
	private SyRwmxService syRwmxService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SyRwmx get(String id, boolean isNewRecord) {
		return syRwmxService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("push:rwmx:syRwmx:view")
	@RequestMapping(value = {"list", ""})
	public String list(SyRwmx syRwmx, Model model) {
		model.addAttribute("syRwmx", syRwmx);
		return "modules/bright/push/rwmx/syRwmxList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("push:rwmx:syRwmx:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SyRwmx> listData(SyRwmx syRwmx, HttpServletRequest request, HttpServletResponse response) {
		syRwmx.setPage(new Page<>(request, response));
		Page<SyRwmx> page = syRwmxService.findPage(syRwmx);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("push:rwmx:syRwmx:view")
	@RequestMapping(value = "form")
	public String form(SyRwmx syRwmx, Model model) {
		model.addAttribute("syRwmx", syRwmx);
		return "modules/bright/push/rwmx/syRwmxForm";
	}

	/**
	 * 保存生涯任务明细
	 */
	@RequiresPermissions("push:rwmx:syRwmx:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SyRwmx syRwmx) {
		syRwmxService.save(syRwmx);
		return renderResult(Global.TRUE, text("保存生涯任务明细成功！"));
	}
	
	/**
	 * 停用生涯任务明细
	 */
	@RequiresPermissions("push:rwmx:syRwmx:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(SyRwmx syRwmx) {
		syRwmx.setStatus(SyRwmx.STATUS_DISABLE);
		syRwmxService.updateStatus(syRwmx);
		return renderResult(Global.TRUE, text("停用生涯任务明细成功"));
	}
	
	/**
	 * 启用生涯任务明细
	 */
	@RequiresPermissions("push:rwmx:syRwmx:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(SyRwmx syRwmx) {
		syRwmx.setStatus(SyRwmx.STATUS_NORMAL);
		syRwmxService.updateStatus(syRwmx);
		return renderResult(Global.TRUE, text("启用生涯任务明细成功"));
	}
	
	/**
	 * 删除生涯任务明细
	 */
	@RequiresPermissions("push:rwmx:syRwmx:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SyRwmx syRwmx) {
		syRwmxService.delete(syRwmx);
		return renderResult(Global.TRUE, text("删除生涯任务明细成功！"));
	}
	
}