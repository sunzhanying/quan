/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khvipcard.web;

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
import com.jeesite.modules.bright.khvipcard.entity.KhVipcard;
import com.jeesite.modules.bright.khvipcard.service.KhVipcardService;

/**
 * 用户会员卡Controller
 * @author 马晓亮
 * @version 2019-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/khvipcard/khVipcard")
public class KhVipcardController extends BaseController {

	@Autowired
	private KhVipcardService khVipcardService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public KhVipcard get(String id, boolean isNewRecord) {
		return khVipcardService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequestMapping(value = {"list", ""})
	public String list(KhVipcard khVipcard, Model model) {
		model.addAttribute("khVipcard", khVipcard);
		return "modules/bright/khvipcard/khVipcardList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<KhVipcard> listData(KhVipcard khVipcard, HttpServletRequest request, HttpServletResponse response) {
		khVipcard.setZt(KhVipcard.VIP_CARD_PAY_WC);
		khVipcard.setPage(new Page<>(request, response));
		Page<KhVipcard> page = khVipcardService.findPage(khVipcard);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequestMapping(value = "form")
	public String form(KhVipcard khVipcard, Model model) {
		model.addAttribute("khVipcard", khVipcard);
		return "modules/bright/khvipcard/khVipcardForm";
	}

	/**
	 * 保存用户会员卡
	 */
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated KhVipcard khVipcard) {
		khVipcardService.save(khVipcard);
		return renderResult(Global.TRUE, text("保存用户会员卡成功！"));
	}
	
	/**
	 * 删除用户会员卡
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(KhVipcard khVipcard) {
		khVipcardService.delete(khVipcard);
		return renderResult(Global.TRUE, text("删除用户会员卡成功！"));
	}
	
}