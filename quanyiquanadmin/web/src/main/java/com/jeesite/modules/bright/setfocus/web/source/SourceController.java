/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.web.source;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.setfocus.entity.source.Source;
import com.jeesite.modules.bright.setfocus.service.source.SourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 来源表Controller
 * @author liqingfeng
 * @version 2019-07-11
 */
@Controller
@RequestMapping(value = "${adminPath}/setfocus/source/source")
public class SourceController extends BaseController {

	@Autowired
	private SourceService sourceService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Source get(String id, boolean isNewRecord) {
		return sourceService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("setfocus:source:source:view")
	@RequestMapping(value = {"list", ""})
	public String list(Source source, Model model) {
		model.addAttribute("source", source);
		return "modules/bright/setfocus/source/sourceList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("setfocus:source:source:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Source> listData(Source source, HttpServletRequest request, HttpServletResponse response) {
		source.setPage(new Page<>(request, response));
		Page<Source> page = sourceService.findPage(source);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("setfocus:source:source:view")
	@RequestMapping(value = "form")
	public String form(Source source, Model model) {
		model.addAttribute("source", source);
		return "modules/bright/setfocus/source/sourceForm";
	}

	/**
	 * 保存来源表
	 */
	@RequiresPermissions("setfocus:source:source:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Source source) {
		sourceService.save(source);
		return renderResult(Global.TRUE, text("保存来源表成功！"));
	}
	
	/**
	 * 删除来源表
	 */
	@RequiresPermissions("setfocus:source:source:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Source source) {
		if("1143741427839394733".equals(source.getId())){
			return renderResult(Global.TRUE, text("直接访问来源不可删除！"));
		}
		sourceService.delete(source);
		return renderResult(Global.TRUE, text("删除来源表成功！"));
	}



	/**
	 * 停用数据
	 */

	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(Source source) {
		source.setStatus("2");
		sourceService.updateStatus(source);
		return renderResult(Global.TRUE, text("停用来源成功"));
	}

	/**
	 * 启用数据
	 */

	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(Source source) {
		source.setStatus("0");
		sourceService.updateStatus(source);
		return renderResult(Global.TRUE, text("启用来源成功"));
	}
	
}