/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.web.templet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.bright.push.dao.templet.SyMbmxDao;
import com.jeesite.modules.bright.push.entity.templet.SyMbmx;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.push.entity.templet.SyMb;
import com.jeesite.modules.bright.push.service.templet.SyMbService;

import java.util.List;

/**
 * 生涯模板Controller
 * @author 李金辉
 * @version 2019-07-17
 */
@Controller
@RequestMapping(value = "${adminPath}/push/templet/syMb")
public class SyMbController extends BaseController {

	@Autowired
	private SyMbService syMbService;
	@Autowired
	private SyMbmxDao syMbmxDao;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SyMb get(String id, boolean isNewRecord) {
		return syMbService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("push:templet:syMb:view")
	@RequestMapping(value = {"list", ""})
	public String list(SyMb syMb, Model model) {
		model.addAttribute("syMb", syMb);
		return "modules/bright/push/templet/syMbList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("push:templet:syMb:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SyMb> listData(SyMb syMb, HttpServletRequest request, HttpServletResponse response) {
		syMb.setPage(new Page<>(request, response));
		Page<SyMb> page = syMbService.findPage(syMb);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("push:templet:syMb:view")
	@RequestMapping(value = "form")
	public String form(SyMb syMb, Model model) {
		model.addAttribute("syMb", syMb);
		return "modules/bright/push/templet/syMbForm";
	}

	/**
	 * 保存生涯模板
	 */
	@RequiresPermissions("push:templet:syMb:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SyMb syMb) {
		syMbService.save(syMb);
		return renderResult(Global.TRUE, text("保存生涯模板成功！"));
	}
	
	/**
	 * 停用生涯模板
	 */
	@RequiresPermissions("push:templet:syMb:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(SyMb syMb) {
		syMb.setStatus(SyMb.STATUS_DISABLE);
		syMbService.updateStatus(syMb);
		return renderResult(Global.TRUE, text("停用生涯模板成功"));
	}
	
	/**
	 * 启用生涯模板
	 */
	@RequiresPermissions("push:templet:syMb:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(SyMb syMb) {
		syMb.setStatus(SyMb.STATUS_NORMAL);
		syMbService.updateStatus(syMb);
		return renderResult(Global.TRUE, text("启用生涯模板成功"));
	}
	
	/**
	 * 删除生涯模板
	 */
	@RequiresPermissions("push:templet:syMb:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SyMb syMb) {
		syMbService.delete(syMb);
		return renderResult(Global.TRUE, text("删除生涯模板成功！"));
	}

	@RequestMapping(value = "mbmx",method = RequestMethod.GET)
	@ResponseBody
	public List<SyMbmx> mbmx(SyMb syMb) {
		return syMbmxDao.findList(new SyMbmx(syMb));
	}
	
}