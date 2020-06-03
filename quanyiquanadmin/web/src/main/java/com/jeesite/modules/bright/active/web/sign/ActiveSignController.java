/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.active.web.sign;

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
import com.jeesite.modules.bright.active.entity.sign.ActiveSign;
import com.jeesite.modules.bright.active.service.sign.ActiveSignService;

/**
 * 活动 课程 报名表Controller
 * @author 李金辉
 * @version 2019-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/bright/active/sign/activeSign")
public class ActiveSignController extends BaseController {

	@Autowired
	private ActiveSignService activeSignService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ActiveSign get(String id, boolean isNewRecord) {
		return activeSignService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */

	@RequestMapping(value = {"list", ""})
	public String list(ActiveSign activeSign, Model model) {
		model.addAttribute("activeSign", activeSign);
		return "modules/bright/active/sign/activeSignList";
	}
	
	/**
	 * 查询列表数据
	 */

	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ActiveSign> listData(ActiveSign activeSign, HttpServletRequest request, HttpServletResponse response) {
		activeSign.setPage(new Page<>(request, response));
		Page<ActiveSign> page = activeSignService.findPage(activeSign);
		return page;
	}

	/**
	 * 查看编辑表单
	 */

	@RequestMapping(value = "form")
	public String form(ActiveSign activeSign, Model model) {
		model.addAttribute("activeSign", activeSign);
		return "modules/bright/active/sign/activeSignForm";
	}

	/**
	 * 保存活动 课程 报名表
	 */

	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ActiveSign activeSign) {
		activeSignService.save(activeSign);
		return renderResult(Global.TRUE, text("保存活动 课程 报名表成功！"));
	}
	
	/**
	 * 停用活动 课程 报名表
	 */

	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ActiveSign activeSign) {
		activeSign.setStatus(ActiveSign.STATUS_DISABLE);
		activeSignService.updateStatus(activeSign);
		return renderResult(Global.TRUE, text("停用活动 课程 报名表成功"));
	}
	
	/**
	 * 启用活动 课程 报名表
	 */

	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ActiveSign activeSign) {
		activeSign.setStatus(ActiveSign.STATUS_NORMAL);
		activeSignService.updateStatus(activeSign);
		return renderResult(Global.TRUE, text("启用活动 课程 报名表成功"));
	}
	
	/**
	 * 删除活动 课程 报名表
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ActiveSign activeSign) {
		activeSignService.delete(activeSign);
		return renderResult(Global.TRUE, text("删除活动 课程 报名表成功！"));
	}
	
}