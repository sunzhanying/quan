/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.web.meterail.visited;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.content.entity.meterail.visited.MeterialVisitedLog;
import com.jeesite.modules.bright.content.service.meterail.visited.MeterialVisitedLogService;

import java.util.HashMap;
import java.util.Map;

/**
 * 访问日志表Controller
 * @author liqingfeng
 * @version 2019-07-17
 */
@Controller
@RequestMapping(value = "${adminPath}/content/meterail/visited/meterialVisitedLog")
public class MeterialVisitedLogController extends BaseController {

	@Autowired
	private MeterialVisitedLogService meterialVisitedLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public MeterialVisitedLog get(String id, boolean isNewRecord) {
		return meterialVisitedLogService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("content:meterail:visited:meterialVisitedLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(MeterialVisitedLog meterialVisitedLog, Model model) {
		model.addAttribute("meterialVisitedLog", meterialVisitedLog);
		return "modules/bright/content/meterail/visited/meterialVisitedLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("content:meterail:visited:meterialVisitedLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<MeterialVisitedLog> listData(MeterialVisitedLog meterialVisitedLog, HttpServletRequest request, HttpServletResponse response) {
		meterialVisitedLog.setPage(new Page<>(request, response));
		Page<MeterialVisitedLog> page = meterialVisitedLogService.findPage(meterialVisitedLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("content:meterail:visited:meterialVisitedLog:view")
	@RequestMapping(value = "form")
	public String form(MeterialVisitedLog meterialVisitedLog, Model model) {
		model.addAttribute("meterialVisitedLog", meterialVisitedLog);
		return "modules/bright/content/meterail/visited/meterialVisitedLogForm";
	}

	/**
	 * 保存访问日志表
	 */
	@RequiresPermissions("content:meterail:visited:meterialVisitedLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated MeterialVisitedLog meterialVisitedLog) {
		meterialVisitedLogService.save(meterialVisitedLog);
		return renderResult(Global.TRUE, text("保存访问日志表成功！"));
	}
	
	/**
	 * 删除访问日志表
	 */
	@RequiresPermissions("content:meterail:visited:meterialVisitedLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(MeterialVisitedLog meterialVisitedLog) {
		meterialVisitedLogService.delete(meterialVisitedLog);
		return renderResult(Global.TRUE, text("删除访问日志表成功！"));
	}


	@PostMapping(value = "getChartData")
	@ResponseBody
	public Map<String, Object> getChartData(String day, String startTime, String endTime, String type){
		return meterialVisitedLogService.getChartData(day,type,startTime,endTime);
	}
	@GetMapping(value = "getSum")
	@ResponseBody
	public HashMap getSum(){
		return meterialVisitedLogService.getSum();
	}
	
}