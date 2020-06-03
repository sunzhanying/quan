/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyhs.web;

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
import com.jeesite.modules.qyhs.entity.Qyhs;
import com.jeesite.modules.qyhs.service.QyhsService;

/**
 * 权益回收Controller
 * @author 马晓亮
 * @version 2020-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/qyhs/qyhs")
public class QyhsController extends BaseController {

	@Autowired
	private QyhsService qyhsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Qyhs get(String id, boolean isNewRecord) {
		return qyhsService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("qyhs:qyhs:view")
	@RequestMapping(value = {"list", ""})
	public String list(Qyhs qyhs, Model model) {
		model.addAttribute("qyhs", qyhs);
		//待审核
		if ("1".equals(qyhs.getZt())){
			return "modules/qyhs/qyhsList";
		}else { //已审核
			return "modules/qyhs/qyhsListYwc";
		}
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("qyhs:qyhs:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Qyhs> listData(Qyhs qyhs, HttpServletRequest request, HttpServletResponse response) {
		qyhs.setPage(new Page<>(request, response));
		Page<Qyhs> page = qyhsService.findPage(qyhs);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("qyhs:qyhs:view")
	@RequestMapping(value = "form")
	public String form(Qyhs qyhs, Model model) {
		model.addAttribute("qyhs", qyhs);
		return "modules/qyhs/qyhsForm";
	}

	/**
	 * 保存权益回收
	 */
	@RequiresPermissions("qyhs:qyhs:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Qyhs qyhs) {
		qyhsService.save(qyhs);
		return renderResult(Global.TRUE, text("保存权益回收成功！"));
	}
	
	/**
	 * 删除权益回收
	 */
	@RequiresPermissions("qyhs:qyhs:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Qyhs qyhs) {
		qyhsService.delete(qyhs);
		return renderResult(Global.TRUE, text("删除权益回收成功！"));
	}
	
}