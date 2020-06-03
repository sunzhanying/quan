/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.exam.web;

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
import com.jeesite.modules.bright.exam.entity.ExamType;
import com.jeesite.modules.bright.exam.service.ExamTypeService;

/**
 * 题目类型表Controller
 * @author 马晓亮
 * @version 2019-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examType")
public class ExamTypeController extends BaseController {

	@Autowired
	private ExamTypeService examTypeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ExamType get(String id, boolean isNewRecord) {
		return examTypeService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("exam:examType:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExamType examType, Model model) {
		model.addAttribute("examType", examType);
		return "modules/bright/exam/examTypeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("exam:examType:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ExamType> listData(ExamType examType, HttpServletRequest request, HttpServletResponse response) {
		examType.setPage(new Page<>(request, response));
		Page<ExamType> page = examTypeService.findPage(examType);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("exam:examType:view")
	@RequestMapping(value = "form")
	public String form(ExamType examType, Model model) {
		if (examType.getIsNewRecord()) {
			examType.setAnswerWay("1");
			examType.setIsActive("2");
		}
		model.addAttribute("examType", examType);
		return "modules/bright/exam/examTypeForm";
	}

	/**
	 * 保存题目类型表
	 */
	@RequiresPermissions("exam:examType:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ExamType examType) {
		examTypeService.save(examType);
		return renderResult(Global.TRUE, text("保存题目类型表成功！"));
	}
	
	/**
	 * 删除题目类型表
	 */
	@RequiresPermissions("exam:examType:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ExamType examType) {
		examTypeService.delete(examType);
		return renderResult(Global.TRUE, text("删除题目类型表成功！"));
	}
	
}