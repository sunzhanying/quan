/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.points.web.pointslog;

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
import com.jeesite.modules.bright.points.entity.pointslog.PointsLog;
import com.jeesite.modules.bright.points.service.pointslog.PointsLogService;

/**
 * 客户积分流水表Controller
 * @author 李金辉
 * @version 2019-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/bright/points/pointslog/pointsLog")
public class PointsLogController extends BaseController {

	@Autowired
	private PointsLogService pointsLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public PointsLog get(String id, boolean isNewRecord) {
		return pointsLogService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("bright:points:pointslog:pointsLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(PointsLog pointsLog, Model model) {
		model.addAttribute("pointsLog", pointsLog);
		return "modules/bright/points/pointslog/pointsLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bright:points:pointslog:pointsLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<PointsLog> listData(PointsLog pointsLog, HttpServletRequest request, HttpServletResponse response) {
		pointsLog.setPage(new Page<>(request, response));
		Page<PointsLog> page = pointsLogService.findPage(pointsLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bright:points:pointslog:pointsLog:view")
	@RequestMapping(value = "form")
	public String form(PointsLog pointsLog, Model model) {
		model.addAttribute("pointsLog", pointsLog);
		return "modules/bright/points/pointslog/pointsLogForm";
	}

	/**
	 * 保存客户积分流水表
	 */
	@RequiresPermissions("bright:points:pointslog:pointsLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated PointsLog pointsLog) {
		pointsLogService.save(pointsLog);
		return renderResult(Global.TRUE, text("保存客户积分流水表成功！"));
	}
	
}