/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.web.templet;

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
import com.jeesite.modules.bright.push.entity.templet.Xxtsmb;
import com.jeesite.modules.bright.push.service.templet.XxtsmbService;

/**
 * 消息推送模板Controller
 * @author 李金辉
 * @version 2019-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/push/templet/xxtsmb")
public class XxtsmbController extends BaseController {

	@Autowired
	private XxtsmbService xxtsmbService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Xxtsmb get(String id, boolean isNewRecord) {
		return xxtsmbService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("push:templet:xxtsmb:view")
	@RequestMapping(value = {"list", ""})
	public String list(Xxtsmb xxtsmb, Model model) {
		model.addAttribute("xxtsmb", xxtsmb);
		return "modules/bright/push/templet/xxtsmbList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("push:templet:xxtsmb:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Xxtsmb> listData(Xxtsmb xxtsmb, HttpServletRequest request, HttpServletResponse response) {
		xxtsmb.setPage(new Page<>(request, response));
		Page<Xxtsmb> page = xxtsmbService.findPage(xxtsmb);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("push:templet:xxtsmb:view")
	@RequestMapping(value = "form")
	public String form(Xxtsmb xxtsmb, Model model) {
		model.addAttribute("xxtsmb", xxtsmb);
		return "modules/bright/push/templet/xxtsmbForm";
	}

	/**
	 * 保存消息推送模板
	 */
	@RequiresPermissions("push:templet:xxtsmb:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Xxtsmb xxtsmb) {
		xxtsmbService.save(xxtsmb);
		return renderResult(Global.TRUE, text("保存消息推送模板成功！"));
	}
	
	/**
	 * 停用消息推送模板
	 */
	@RequiresPermissions("push:templet:xxtsmb:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(Xxtsmb xxtsmb) {
		xxtsmb.setStatus(Xxtsmb.STATUS_DISABLE);
		xxtsmbService.updateStatus(xxtsmb);
		return renderResult(Global.TRUE, text("停用消息推送模板成功"));
	}
	
	/**
	 * 启用消息推送模板
	 */
	@RequiresPermissions("push:templet:xxtsmb:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(Xxtsmb xxtsmb) {
		xxtsmb.setStatus(Xxtsmb.STATUS_NORMAL);
		xxtsmbService.updateStatus(xxtsmb);
		return renderResult(Global.TRUE, text("启用消息推送模板成功"));
	}
	
	/**
	 * 删除消息推送模板
	 */
	@RequiresPermissions("push:templet:xxtsmb:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Xxtsmb xxtsmb) {
		xxtsmbService.delete(xxtsmb);
		return renderResult(Global.TRUE, text("删除消息推送模板成功！"));
	}

	/**
	 * 单挑获取推送模板
	 */

	@RequestMapping(value = "get")
	@ResponseBody
	public Xxtsmb get(String mbid) {

		return xxtsmbService.get(mbid);

	}
	
}