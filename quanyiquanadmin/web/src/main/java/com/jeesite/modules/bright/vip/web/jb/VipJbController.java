/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.vip.web.jb;

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
import com.jeesite.modules.bright.vip.entity.jb.VipJb;
import com.jeesite.modules.bright.vip.service.jb.VipJbService;

/**
 * 会员级别Controller
 * @author 马晓亮
 * @version 2019-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/vip/jb/vipJb")
public class VipJbController extends BaseController {

	@Autowired
	private VipJbService vipJbService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public VipJb get(String id, boolean isNewRecord) {
		return vipJbService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("vip:jb:vipJb:view")
	@RequestMapping(value = {"list", ""})
	public String list(VipJb vipJb, Model model) {
		model.addAttribute("vipJb", vipJb);
		return "modules/bright/vip/jb/vipJbList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("vip:jb:vipJb:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<VipJb> listData(VipJb vipJb, HttpServletRequest request, HttpServletResponse response) {
		vipJb.setPage(new Page<>(request, response));
		Page<VipJb> page = vipJbService.findPage(vipJb);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("vip:jb:vipJb:view")
	@RequestMapping(value = "form")
	public String form(VipJb vipJb, Model model) {
		model.addAttribute("vipJb", vipJb);
		return "modules/bright/vip/jb/vipJbForm";
	}

	/**
	 * 保存会员级别
	 */
	@RequiresPermissions("vip:jb:vipJb:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated VipJb vipJb) {
		vipJbService.save(vipJb);
		return renderResult(Global.TRUE, text("保存会员级别成功！"));
	}
	
	/**
	 * 删除会员级别
	 */
	@RequiresPermissions("vip:jb:vipJb:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(VipJb vipJb) {
		vipJbService.delete(vipJb);
		return renderResult(Global.TRUE, text("删除会员级别成功！"));
	}
	
}