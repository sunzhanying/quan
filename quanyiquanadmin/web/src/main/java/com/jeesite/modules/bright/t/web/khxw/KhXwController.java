/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.web.khxw;

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
import com.jeesite.modules.bright.t.entity.khxw.KhXw;
import com.jeesite.modules.bright.t.service.khxw.KhXwService;

/**
 * 客户行为Controller
 * @author 李金辉
 * @version 2019-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/t/khxw/khXw")
public class KhXwController extends BaseController {

	@Autowired
	private KhXwService khXwService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public KhXw get(String id, boolean isNewRecord) {
		return khXwService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */

	@RequestMapping(value = {"list", ""})
	public String list(KhXw khXw, Model model) {
		model.addAttribute("khXw", khXw);
		return "modules/bright/t/khxw/khXwList";
	}
	
	/**
	 * 查询列表数据
	 */

	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<KhXw> listData(KhXw khXw, HttpServletRequest request, HttpServletResponse response) {
		khXw.setPage(new Page<>(request, response));
		Page<KhXw> page = khXwService.findPage(khXw);
		return page;
	}

	/**
	 * 查看编辑表单
	 */

	@RequestMapping(value = "form")
	public String form(KhXw khXw, Model model) {
		model.addAttribute("khXw", khXw);
		return "modules/bright/t/khxw/khXwForm";
	}

	/**
	 * 保存客户行为
	 */

	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated KhXw khXw) {
		khXwService.save(khXw);
		return renderResult(Global.TRUE, text("保存客户行为成功！"));
	}
	
}