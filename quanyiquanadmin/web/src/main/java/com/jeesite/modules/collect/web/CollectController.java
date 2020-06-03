/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.collect.web;

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
import com.jeesite.modules.collect.entity.Collect;
import com.jeesite.modules.collect.service.CollectService;

/**
 * 收藏权益Controller
 * @author 收藏
 * @version 2020-03-30
 */
@Controller
@RequestMapping(value = "${adminPath}/collect/collect")
public class CollectController extends BaseController {

	@Autowired
	private CollectService collectService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Collect get(String id, boolean isNewRecord) {
		return collectService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("collect:collect:view")
	@RequestMapping(value = {"list", ""})
	public String list(Collect collect, Model model) {
		model.addAttribute("collect", collect);
		return "modules/collect/collectList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("collect:collect:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Collect> listData(Collect collect, HttpServletRequest request, HttpServletResponse response) {
		collect.setPage(new Page<>(request, response));
		Page<Collect> page = collectService.findPage(collect);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("collect:collect:view")
	@RequestMapping(value = "form")
	public String form(Collect collect, Model model) {
		model.addAttribute("collect", collect);
		return "modules/collect/collectForm";
	}

	/**
	 * 保存收藏权益
	 */
	@RequiresPermissions("collect:collect:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Collect collect) {
		collectService.save(collect);
		return renderResult(Global.TRUE, text("保存收藏权益成功！"));
	}
	
	/**
	 * 删除收藏权益
	 */
	@RequiresPermissions("collect:collect:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Collect collect) {
		collectService.delete(collect);
		return renderResult(Global.TRUE, text("删除收藏权益成功！"));
	}
	
}