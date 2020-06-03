/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.web.propagate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.bright.t.entity.propagate.KhPropagate;
import com.jeesite.modules.bright.t.service.propagate.KhPropagateService;

/**
 * 客户传播Controller
 * @author 李金辉
 * @version 2019-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/t/propagate/khPropagate")
public class KhPropagateController extends BaseController {

	@Autowired
	private KhPropagateService khPropagateService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public KhPropagate get(String id, boolean isNewRecord) {
		return khPropagateService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */

	@RequestMapping(value = {"list", ""})
	public String list(KhPropagate khPropagate, Model model) {
		model.addAttribute("khPropagate", khPropagate);
		return "modules/bright/t/propagate/khPropagateList";
	}
	
	/**
	 * 查询列表数据
	 */

	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<KhPropagate> listData(KhPropagate khPropagate, HttpServletRequest request, HttpServletResponse response) {
		khPropagate.setPage(new Page<>(request, response));
		Page<KhPropagate> page = khPropagateService.findPage(khPropagate);
		return page;
	}

	/**
	 * 查看编辑表单
	 */

	@RequestMapping(value = "form")
	public String form(KhPropagate khPropagate, Model model) {
		model.addAttribute("khPropagate", khPropagate);
		return "modules/bright/t/propagate/khPropagateForm";
	}

	/**
	 * 保存客户传播
	 */

	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated KhPropagate khPropagate) {
		khPropagateService.save(khPropagate);
		return renderResult(Global.TRUE, text("保存客户传播成功！"));
	}
	
	/**
	 * 删除客户传播
	 */

	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(KhPropagate khPropagate) {
		khPropagateService.delete(khPropagate);
		return renderResult(Global.TRUE, text("删除客户传播成功！"));
	}
	
}