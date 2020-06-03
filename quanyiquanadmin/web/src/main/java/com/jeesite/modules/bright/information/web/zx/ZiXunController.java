/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.information.web.zx;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.information.entity.zx.ZiXun;
import com.jeesite.modules.bright.information.service.zx.ZiXunService;
import com.jeesite.modules.bright.test.entity.TestData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 内容资讯Controller
 * @author liqingfeng
 * @version 2019-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/bright/information/zx/ziXun")
public class ZiXunController extends BaseController {

	@Autowired
	private ZiXunService ziXunService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZiXun get(String id, boolean isNewRecord) {
		return ziXunService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("bright:information:zx:ziXun:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZiXun ziXun, Model model) {
		model.addAttribute("ziXun", ziXun);
		return "modules/bright/information/zx/ziXunList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bright:information:zx:ziXun:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZiXun> listData(ZiXun ziXun, HttpServletRequest request, HttpServletResponse response) {
		ziXun.setPage(new Page<>(request, response));
		Page<ZiXun> page = ziXunService.findPage(ziXun);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bright:information:zx:ziXun:view")
	@RequestMapping(value = "form")
	public String form(ZiXun ziXun, Model model) {
		if(ziXun.getLjType()==null){
			ziXun.setLjType((Integer.valueOf(ZiXun.LGTYPE_INSIDE)));
		}
		model.addAttribute("ziXun", ziXun);
		return "modules/bright/information/zx/ziXunForm";
	}

	/**
	 * 保存内容资讯
	 */
	@RequiresPermissions("bright:information:zx:ziXun:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZiXun ziXun) {
		ziXunService.save(ziXun);
		return renderResult(Global.TRUE, text("保存内容资讯成功！"));
	}
	
	/**
	 * 删除内容资讯
	 */
	@RequiresPermissions("bright:information:zx:ziXun:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZiXun ziXun) {
		ziXunService.delete(ziXun);
		return renderResult(Global.TRUE, text("删除内容资讯成功！"));
	}

	/**
	 * 停用数据
	 */
	@RequiresPermissions("bright:information:zx:ziXun:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZiXun ziXun) {
		ziXun.setStatus(TestData.STATUS_DISABLE);
		ziXunService.updateStatus(ziXun);
		return renderResult(Global.TRUE, text("停用资讯成功"));
	}

	/**
	 * 启用数据
	 */
	@RequiresPermissions("bright:information:zx:ziXun:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZiXun ziXun) {
		ziXun.setStatus(TestData.STATUS_NORMAL);
		ziXunService.updateStatus(ziXun);
		return renderResult(Global.TRUE, text("启用资讯成功"));
	}
	
}