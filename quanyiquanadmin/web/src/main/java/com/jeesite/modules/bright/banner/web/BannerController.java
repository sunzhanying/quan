/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.banner.web;

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
import com.jeesite.modules.bright.banner.entity.Banner;
import com.jeesite.modules.bright.banner.service.BannerService;

/**
 * 推荐位Controller
 * @author 李金辉
 * @version 2019-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/banner/banner")
public class BannerController extends BaseController {

	@Autowired
	private BannerService bannerService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Banner get(String id, boolean isNewRecord) {
		return bannerService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("banner:banner:view")
	@RequestMapping(value = {"list", ""})
	public String list(Banner banner, Model model) {
		model.addAttribute("banner", banner);
		return "modules/bright/banner/bannerList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("banner:banner:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Banner> listData(Banner banner, HttpServletRequest request, HttpServletResponse response) {
		banner.setPage(new Page<>(request, response));
		Page<Banner> page = bannerService.findPage(banner);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("banner:banner:view")
	@RequestMapping(value = "form")
	public String form(Banner banner, Model model) {
		model.addAttribute("banner", banner);
		return "modules/bright/banner/bannerForm";
	}

	/**
	 * 保存推荐位
	 */
	@RequiresPermissions("banner:banner:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Banner banner) {
		bannerService.save(banner);
		return renderResult(Global.TRUE, text("保存推荐位成功！"));
	}
	
	/**
	 * 停用推荐位
	 */
	@RequiresPermissions("banner:banner:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(Banner banner) {
		banner.setStatus(Banner.STATUS_DISABLE);
		bannerService.updateStatus(banner);
		return renderResult(Global.TRUE, text("停用推荐位成功"));
	}
	
	/**
	 * 启用推荐位
	 */
	@RequiresPermissions("banner:banner:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(Banner banner) {
		banner.setStatus(Banner.STATUS_NORMAL);
		bannerService.updateStatus(banner);
		return renderResult(Global.TRUE, text("启用推荐位成功"));
	}
	
	/**
	 * 删除推荐位
	 */
	@RequiresPermissions("banner:banner:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Banner banner) {
		bannerService.delete(banner);
		return renderResult(Global.TRUE, text("删除推荐位成功！"));
	}
	
}