/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.exam.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.bright.exam.entity.ExamTitle;
import com.jeesite.modules.bright.exam.service.ExamTitleService;
import com.jeesite.modules.bright.exam.service.ExamTypeService;
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

/**
 * 提目表Controller
 * @author 马晓亮
 * @version 2019-08-05
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examTitle")
public class ExamTitleController extends BaseController {

	@Autowired
	private ExamTitleService examTitleService;
	@Autowired
	private ExamTypeService examTypeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ExamTitle get(String id, boolean isNewRecord) {
		return examTitleService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequestMapping(value = {"list", ""})
	public String list(ExamTitle examTitle, Model model) {
		model.addAttribute("examTitle", examTitle);
		return "modules/bright/exam/examTitleList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ExamTitle> listData(ExamTitle examTitle, HttpServletRequest request, HttpServletResponse response) {
		examTitle.setPage(new Page<>(request, response));
		Page<ExamTitle> page = examTitleService.findPage(examTitle);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequestMapping(value = "form")
	public String form(ExamTitle examTitle, Model model) {
		model.addAttribute("examTitle", examTitle);
		model.addAttribute("answerWay",examTypeService.get(examTitle.getTypeId()).getAnswerWay());
		return "modules/bright/exam/examTitleForm";
	}

	/**
	 * 保存提目表
	 */
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ExamTitle examTitle) {
		examTitleService.save(examTitle);
		return renderResult(Global.TRUE, text("保存提目表成功！"));
	}
	
	/**
	 * 删除提目表
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ExamTitle examTitle) {
		examTitleService.delete(examTitle);
		return renderResult(Global.TRUE, text("删除提目表成功！"));
	}
	
}