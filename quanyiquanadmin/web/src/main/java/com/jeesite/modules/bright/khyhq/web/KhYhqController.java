/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khyhq.web;

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
import com.jeesite.modules.bright.khyhq.entity.KhYhq;
import com.jeesite.modules.bright.khyhq.service.KhYhqService;

/**
 * 客户优惠券Controller
 * @author 马晓亮
 * @version 2019-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/khyhq/khYhq")
public class KhYhqController extends BaseController {

	@Autowired
	private KhYhqService khYhqService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public KhYhq get(String id, boolean isNewRecord) {
		return khYhqService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("khyhq:khYhq:view")
	@RequestMapping(value = {"list", ""})
	public String list(KhYhq khYhq, Model model) {
		model.addAttribute("khYhq", khYhq);
		return "modules/bright/khyhq/khYhqList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("khyhq:khYhq:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<KhYhq> listData(KhYhq khYhq, HttpServletRequest request, HttpServletResponse response) {
		khYhq.setPage(new Page<>(request, response));
		Page<KhYhq> page = khYhqService.findPage(khYhq);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("khyhq:khYhq:view")
	@RequestMapping(value = "form")
	public String form(KhYhq khYhq, Model model) {
		model.addAttribute("khYhq", khYhq);
		return "modules/bright/khyhq/khYhqForm";
	}

	/**
	 * 保存客户优惠券
	 */
	@RequiresPermissions("khyhq:khYhq:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated KhYhq khYhq) {
		khYhqService.save(khYhq);
		return renderResult(Global.TRUE, text("保存客户优惠券成功！"));
	}
	
	/**
	 * 删除客户优惠券
	 */
	@RequiresPermissions("khyhq:khYhq:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(KhYhq khYhq) {
		khYhqService.delete(khYhq);
		return renderResult(Global.TRUE, text("删除客户优惠券成功！"));
	}
	
}