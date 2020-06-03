/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.web.pushlog;

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
import com.jeesite.modules.bright.push.entity.pushlog.PushLs;
import com.jeesite.modules.bright.push.service.pushlog.PushLsService;

/**
 * 推送批次记录流水表用于记录每次生成的推送批次Controller
 * @author 李金辉
 * @version 2019-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/push/pushlog/pushLs")
public class PushLsController extends BaseController {

	@Autowired
	private PushLsService pushLsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public PushLs get(String id, boolean isNewRecord) {
		return pushLsService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("push:pushlog:pushLs:view")
	@RequestMapping(value = {"list", ""})
	public String list(PushLs pushLs, Model model) {
		model.addAttribute("pushLs", pushLs);
		return "modules/bright/push/pushlog/pushLsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("push:pushlog:pushLs:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<PushLs> listData(PushLs pushLs, HttpServletRequest request, HttpServletResponse response) {
		pushLs.setPage(new Page<>(request, response));
		Page<PushLs> page = pushLsService.findPage(pushLs);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("push:pushlog:pushLs:view")
	@RequestMapping(value = "form")
	public String form(PushLs pushLs, Model model) {
		model.addAttribute("pushLs", pushLs);
		return "modules/bright/push/pushlog/pushLsForm";
	}

	/**
	 * 保存推送批次记录流水表用于记录每次生成的推送批次
	 */
	@RequiresPermissions("push:pushlog:pushLs:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated PushLs pushLs) {
		pushLsService.save(pushLs);
		return renderResult(Global.TRUE, text("保存推送批次记录流水表用于记录每次生成的推送批次成功！"));
	}
	
	/**
	 * 停用推送批次记录流水表用于记录每次生成的推送批次
	 */
	@RequiresPermissions("push:pushlog:pushLs:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(PushLs pushLs) {
		pushLs.setStatus(PushLs.STATUS_DISABLE);
		pushLsService.updateStatus(pushLs);
		return renderResult(Global.TRUE, text("停用推送批次记录流水表用于记录每次生成的推送批次成功"));
	}
	
	/**
	 * 启用推送批次记录流水表用于记录每次生成的推送批次
	 */
	@RequiresPermissions("push:pushlog:pushLs:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(PushLs pushLs) {
		pushLs.setStatus(PushLs.STATUS_NORMAL);
		pushLsService.updateStatus(pushLs);
		return renderResult(Global.TRUE, text("启用推送批次记录流水表用于记录每次生成的推送批次成功"));
	}
	
	/**
	 * 删除推送批次记录流水表用于记录每次生成的推送批次
	 */
	@RequiresPermissions("push:pushlog:pushLs:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(PushLs pushLs) {
		pushLsService.delete(pushLs);
		return renderResult(Global.TRUE, text("删除推送批次记录流水表用于记录每次生成的推送批次成功！"));
	}
	
}