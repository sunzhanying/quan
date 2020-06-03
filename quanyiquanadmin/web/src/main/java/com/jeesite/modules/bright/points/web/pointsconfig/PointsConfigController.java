/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.points.web.pointsconfig;

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
import com.jeesite.modules.bright.points.entity.pointsconfig.PointsConfig;
import com.jeesite.modules.bright.points.service.pointsconfig.PointsConfigService;

/**
 * t_jf_configController
 * @author 李金辉
 * @version 2019-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/bright/points/pointsconfig/pointsConfig")
public class PointsConfigController extends BaseController {

	@Autowired
	private PointsConfigService pointsConfigService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public PointsConfig get(String id, boolean isNewRecord) {
		return pointsConfigService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("bright:points:pointsconfig:pointsConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(PointsConfig pointsConfig, Model model) {
		model.addAttribute("pointsConfig", pointsConfig);
		return "modules/bright/points/pointsconfig/pointsConfigList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bright:points:pointsconfig:pointsConfig:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<PointsConfig> listData(PointsConfig pointsConfig, HttpServletRequest request, HttpServletResponse response) {
		pointsConfig.setPage(new Page<>(request, response));
		Page<PointsConfig> page = pointsConfigService.findPage(pointsConfig);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bright:points:pointsconfig:pointsConfig:view")
	@RequestMapping(value = "form")
	public String form(PointsConfig pointsConfig, Model model) {
		model.addAttribute("pointsConfig", pointsConfig);
		return "modules/bright/points/pointsconfig/pointsConfigForm";
	}

	/**
	 * 保存t_jf_config
	 */
	@RequiresPermissions("bright:points:pointsconfig:pointsConfig:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated PointsConfig pointsConfig) {
		pointsConfigService.save(pointsConfig);
		return renderResult(Global.TRUE, text("保存t_jf_config成功！"));
	}
	
	/**
	 * 停用t_jf_config
	 */
	@RequiresPermissions("bright:points:pointsconfig:pointsConfig:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(PointsConfig pointsConfig) {
		pointsConfig.setStatus(PointsConfig.STATUS_DISABLE);
		pointsConfigService.updateStatus(pointsConfig);
		return renderResult(Global.TRUE, text("停用t_jf_config成功"));
	}
	
	/**
	 * 启用t_jf_config
	 */
	@RequiresPermissions("bright:points:pointsconfig:pointsConfig:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(PointsConfig pointsConfig) {
		pointsConfig.setStatus(PointsConfig.STATUS_NORMAL);
		pointsConfigService.updateStatus(pointsConfig);
		return renderResult(Global.TRUE, text("启用t_jf_config成功"));
	}
	
	/**
	 * 删除t_jf_config
	 */
	@RequiresPermissions("bright:points:pointsconfig:pointsConfig:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(PointsConfig pointsConfig) {
		pointsConfigService.delete(pointsConfig);
		return renderResult(Global.TRUE, text("删除t_jf_config成功！"));
	}
	
}