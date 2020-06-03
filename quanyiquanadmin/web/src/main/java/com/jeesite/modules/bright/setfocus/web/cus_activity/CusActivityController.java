/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.web.cus_activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.common.utils.excel.annotation.ExcelField;
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
import com.jeesite.modules.bright.setfocus.entity.cus_activity.CusActivity;
import com.jeesite.modules.bright.setfocus.service.cus_activity.CusActivityService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 客户活跃度事件Controller
 * @author liqingfeng
 * @version 2019-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/setfocus/cus_activity/cusActivity")
public class CusActivityController extends BaseController {

	@Autowired
	private CusActivityService cusActivityService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public CusActivity get(String id, boolean isNewRecord) {
		return cusActivityService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("setfocus:cus_activity:cusActivity:view")
	@RequestMapping(value = {"list", ""})
	public String list(CusActivity cusActivity, Model model) {
		model.addAttribute("cusActivity", cusActivity);
		return "modules/bright/setfocus/cus_activity/cusActivityList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("setfocus:cus_activity:cusActivity:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<CusActivity> listData(CusActivity cusActivity, HttpServletRequest request, HttpServletResponse response) {
		cusActivity.setPage(new Page<>(request, response));
		Page<CusActivity> page = cusActivityService.findPage(cusActivity);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("setfocus:cus_activity:cusActivity:view")
	@RequestMapping(value = "form")
	public String form(CusActivity cusActivity, Model model) {
		model.addAttribute("cusActivity", cusActivity);
		return "modules/bright/setfocus/cus_activity/cusActivityForm";
	}

	/**
	 * 保存客户活跃度事件
	 */
	@RequiresPermissions("setfocus:cus_activity:cusActivity:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated CusActivity cusActivity) {
		cusActivityService.save(cusActivity);
		return renderResult(Global.TRUE, text("保存客户活跃度事件成功！"));
	}
	
	/**
	 * 删除客户活跃度事件
	 */
	@RequiresPermissions("setfocus:cus_activity:cusActivity:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(CusActivity cusActivity) {
		cusActivityService.delete(cusActivity);
		return renderResult(Global.TRUE, text("删除客户活跃度事件成功！"));
	}

	/**
	 * 导出用户数据
	 */
	@RequiresPermissions("setfocus:cus_activity:cusActivity:view")
	@RequestMapping(value = "exportData")
	public void exportData(CusActivity cusActivity, Boolean isAll, String ctrlPermi, HttpServletResponse response) {

		if (!(isAll != null && isAll) || Global.isStrictMode()){
			cusActivityService.addDataScopeFilter(cusActivity, ctrlPermi);
		}
		List<CusActivity> list = cusActivityService.findList(cusActivity);
		String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		try(ExcelExport ee = new ExcelExport("用户数据", CusActivity.class)){
			ee.setDataList(list).write(response, fileName);
		}
	}

	/**
	 * 下载导入用户数据模板
	 */
	@RequiresPermissions("setfocus:cus_activity:cusActivity:view")
	@RequestMapping(value = "importTemplate")
	public void importTemplate(HttpServletResponse response) {
		CusActivity cusActivity = new CusActivity();
		List<CusActivity> list = ListUtils.newArrayList(cusActivity);
		String fileName = "用户数据模板.xlsx";
		try(ExcelExport ee = new ExcelExport("用户数据", CusActivity.class, ExcelField.Type.IMPORT)){
			ee.setDataList(list).write(response, fileName);
		}
	}

	/**
	 * 导入用户数据
	 */
	@ResponseBody
	@RequiresPermissions("setfocus:cus_activity:cusActivity:edit")
	@PostMapping(value = "importData")
	public String importData(MultipartFile file, String updateSupport) {
		try {
			boolean isUpdateSupport = Global.YES.equals(updateSupport);
			String message = cusActivityService.importData(file, isUpdateSupport);
			return renderResult(Global.TRUE, "posfull:"+message);
		} catch (Exception ex) {
			return renderResult(Global.FALSE, "posfull:"+ex.getMessage());
		}
	}
	
}