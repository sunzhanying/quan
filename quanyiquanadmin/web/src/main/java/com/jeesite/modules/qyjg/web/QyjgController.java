/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyjg.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.bright.sp.service.SpXxService;
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
import com.jeesite.modules.qyjg.entity.Qyjg;
import com.jeesite.modules.qyjg.service.QyjgService;

/**
 * 权益券价格Controller
 * @author 马晓亮
 * @version 2020-03-24
 */
@Controller
@RequestMapping(value = "${adminPath}/qyjg/qyjg")
public class QyjgController extends BaseController {

	@Autowired
	private QyjgService qyjgService;
	@Autowired
	private SpXxService spXxService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Qyjg get(String id, boolean isNewRecord) {
		return qyjgService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	/*@RequiresPermissions("qyjg:qyjg:view")*/
	@RequestMapping(value = {"list", ""})
	public String list(Qyjg qyjg, Model model) {
		qyjg.setSpXx(spXxService.get(qyjg.getQyqId()));
		model.addAttribute("qyjg", qyjg);
		return "modules/qyjg/qyjgList";
	}
	
	/**
	 * 查询列表数据
	 */
	/*@RequiresPermissions("qyjg:qyjg:view")*/
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Qyjg> listData(Qyjg qyjg, HttpServletRequest request, HttpServletResponse response) {
		qyjg.setPage(new Page<>(request, response));
		Page<Qyjg> page = qyjgService.findPage(qyjg);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("qyjg:qyjg:view")
	@RequestMapping(value = "form")
	public String form(Qyjg qyjg, Model model) {
		model.addAttribute("qyjg", qyjg);
		return "modules/qyjg/qyjgForm";
	}

	/**
	 * 保存权益券价格
	 */
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Qyjg qyjg) {
		qyjgService.save(qyjg);
		return renderResult(Global.TRUE, text("保存权益券价格成功！"));
	}
	
	/**
	 * 删除权益券价格
	 */
	@RequiresPermissions("qyjg:qyjg:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Qyjg qyjg) {
		qyjgService.delete(qyjg);
		return renderResult(Global.TRUE, text("删除权益券价格成功！"));
	}
	
}