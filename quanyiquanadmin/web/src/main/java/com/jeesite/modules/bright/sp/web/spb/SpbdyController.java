/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.web.spb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.sp.entity.yhq.SpYhq;
import com.jeesite.modules.bright.sp.service.SpXxService;
import com.jeesite.modules.bright.sp.service.yhq.SpYhqService;
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
import com.jeesite.modules.bright.sp.entity.spb.Spbdy;
import com.jeesite.modules.bright.sp.service.spb.SpbdyService;

/**
 * 商品包定义Controller
 * @author 马晓亮
 * @version 2019-07-01
 */
@Controller
@RequestMapping(value = "${adminPath}/sp/spb/spbdy")
public class SpbdyController extends BaseController {

	@Autowired
	private SpbdyService spbdyService;
	@Autowired
	private SpXxService spXxService;
	@Autowired
	private SpYhqService spYhqService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Spbdy get(String id, boolean isNewRecord) {
		return spbdyService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sp:spb:spbdy:view")
	@RequestMapping(value = {"list", ""})
	public String list(Spbdy spbdy, Model model) {
		model.addAttribute("spbdy", spbdy);
		return "modules/bright/sp/spb/spbdyList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sp:spb:spbdy:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Spbdy> listData(Spbdy spbdy, HttpServletRequest request, HttpServletResponse response) {
		spbdy.setPage(new Page<>(request, response));
		Page<Spbdy> page = spbdyService.findPage(spbdy);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sp:spb:spbdy:view")
	@RequestMapping(value = "form")
	public String form(Spbdy spbdy, Model model) {
		if (spbdy.getIsNewRecord()) {
			spbdy.setSxj("1");
			spbdy.setIshyqy("2");
		}
		model.addAttribute("spbdy", spbdy);
		model.addAttribute("spList", spXxService.findList(new SpXx()));
		model.addAttribute("yhqList", spYhqService.findList(new SpYhq()));
		return "modules/bright/sp/spb/spbdyForm";
	}

	/**
	 * 保存商品包定义
	 */
	@RequiresPermissions("sp:spb:spbdy:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Spbdy spbdy) {
		spbdyService.save(spbdy);
		return renderResult(Global.TRUE, text("保存商品包定义成功！"));
	}
	
	/**
	 * 删除商品包定义
	 */
	@RequiresPermissions("sp:spb:spbdy:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Spbdy spbdy) {
		spbdyService.delete(spbdy);
		return renderResult(Global.TRUE, text("删除商品包定义成功！"));
	}
	
}