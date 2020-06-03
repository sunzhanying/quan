/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.vip.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.bright.sp.entity.spb.Spbdy;
import com.jeesite.modules.bright.sp.service.spb.SpbdyService;
import com.jeesite.modules.bright.vip.entity.jb.VipJb;
import com.jeesite.modules.bright.vip.service.jb.VipJbService;
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
import com.jeesite.modules.bright.vip.entity.VipXx;
import com.jeesite.modules.bright.vip.service.VipXxService;

/**
 * 会员信息表，定义会员级别名称Controller
 * @author 马晓亮
 * @version 2019-07-02
 */
@Controller
@RequestMapping(value = "${adminPath}/vip/vipXx")
public class VipXxController extends BaseController {

	@Autowired
	private VipXxService vipXxService;
	@Autowired
	private VipJbService vipJbService;
	@Autowired
	private SpbdyService spbdyService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public VipXx get(String id, boolean isNewRecord) {
		return vipXxService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("vip:vipXx:view")
	@RequestMapping(value = {"list", ""})
	public String list(VipXx vipXx, Model model) {
		model.addAttribute("vipXx", vipXx);
		return "modules/bright/vip/vipXxList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("vip:vipXx:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<VipXx> listData(VipXx vipXx, HttpServletRequest request, HttpServletResponse response) {
		vipXx.setPage(new Page<>(request, response));
		Page<VipXx> page = vipXxService.findPage(vipXx);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("vip:vipXx:view")
	@RequestMapping(value = "form")
	public String form(VipXx vipXx, Model model) {
		if (vipXx.getIsNewRecord()) {
			vipXx.setSxj("1");
			vipXx.setSxxz("1");
		}
		model.addAttribute("vipXx", vipXx);
		model.addAttribute("vipJbList", vipJbService.findList(new VipJb()));
		model.addAttribute("spbList", spbdyService.findList(new Spbdy()));
		return "modules/bright/vip/vipXxForm";
	}

	/**
	 * 保存会员信息表，定义会员级别名称
	 */
	@RequiresPermissions("vip:vipXx:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated VipXx vipXx) {
		vipXxService.save(vipXx);
		return renderResult(Global.TRUE, text("保存会员信息表，定义会员级别名称成功！"));
	}
	
	/**
	 * 删除会员信息表，定义会员级别名称
	 */
	@RequiresPermissions("vip:vipXx:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(VipXx vipXx) {
		vipXxService.delete(vipXx);
		return renderResult(Global.TRUE, text("删除会员信息表，定义会员级别名称成功！"));
	}
	
}