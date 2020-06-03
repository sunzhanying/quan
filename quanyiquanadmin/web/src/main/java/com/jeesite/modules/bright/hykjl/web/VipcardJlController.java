/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.hykjl.web;

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
import com.jeesite.modules.bright.hykjl.entity.VipcardJl;
import com.jeesite.modules.bright.hykjl.service.VipcardJlService;

/**
 * 会员卡消费记录Controller
 * @author 马晓亮
 * @version 2019-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/hykjl/vipcardJl")
public class VipcardJlController extends BaseController {

	@Autowired
	private VipcardJlService vipcardJlService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public VipcardJl get(String id, boolean isNewRecord) {
		return vipcardJlService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequestMapping(value = {"list", ""})
	public String list(VipcardJl vipcardJl, Model model) {
		model.addAttribute("vipcardJl", vipcardJl);
		return "modules/bright/hykjl/vipcardJlList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<VipcardJl> listData(VipcardJl vipcardJl, HttpServletRequest request, HttpServletResponse response) {
		vipcardJl.setPage(new Page<>(request, response));
		Page<VipcardJl> page = vipcardJlService.findPage(vipcardJl);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequestMapping(value = "form")
	public String form(VipcardJl vipcardJl, Model model) {
		model.addAttribute("vipcardJl", vipcardJl);
		return "modules/bright/hykjl/vipcardJlForm";
	}

	/**
	 * 保存会员卡消费记录
	 */
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated VipcardJl vipcardJl) {
		vipcardJlService.save(vipcardJl);
		return renderResult(Global.TRUE, text("保存会员卡消费记录成功！"));
	}
	
	/**
	 * 删除会员卡消费记录
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(VipcardJl vipcardJl) {
		vipcardJlService.delete(vipcardJl);
		return renderResult(Global.TRUE, text("删除会员卡消费记录成功！"));
	}
	
}