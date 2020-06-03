/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.web.khxxtag;

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
import com.jeesite.modules.bright.t.entity.khxxtag.KhXxTag;
import com.jeesite.modules.bright.t.service.khxxtag.KhXxTagService;

/**
 * 客户阅读标签Controller
 * @author 李金辉
 * @version 2019-07-15
 */
@Controller
@RequestMapping(value = "${adminPath}/t/khxxtag/khXxTag")
public class KhXxTagController extends BaseController {

	@Autowired
	private KhXxTagService khXxTagService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public KhXxTag get(String id, boolean isNewRecord) {
		return khXxTagService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("t:khxxtag:khXxTag:view")
	@RequestMapping(value = {"list", ""})
	public String list(KhXxTag khXxTag, Model model) {
		model.addAttribute("khXxTag", khXxTag);
		return "modules/bright/t/khxxtag/khXxTagList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("t:khxxtag:khXxTag:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<KhXxTag> listData(KhXxTag khXxTag, HttpServletRequest request, HttpServletResponse response) {
		khXxTag.setPage(new Page<>(request, response));
		Page<KhXxTag> page = khXxTagService.findPage(khXxTag);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("t:khxxtag:khXxTag:view")
	@RequestMapping(value = "form")
	public String form(KhXxTag khXxTag, Model model) {
		model.addAttribute("khXxTag", khXxTag);
		return "modules/bright/t/khxxtag/khXxTagForm";
	}

	/**
	 * 保存客户阅读标签
	 */
	@RequiresPermissions("t:khxxtag:khXxTag:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated KhXxTag khXxTag) {
		khXxTagService.save(khXxTag);
		return renderResult(Global.TRUE, text("保存客户阅读标签成功！"));
	}
	
	/**
	 * 停用客户阅读标签
	 */
	@RequiresPermissions("t:khxxtag:khXxTag:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(KhXxTag khXxTag) {
		khXxTag.setStatus(KhXxTag.STATUS_DISABLE);
		khXxTagService.updateStatus(khXxTag);
		return renderResult(Global.TRUE, text("停用客户阅读标签成功"));
	}
	
	/**
	 * 启用客户阅读标签
	 */
	@RequiresPermissions("t:khxxtag:khXxTag:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(KhXxTag khXxTag) {
		khXxTag.setStatus(KhXxTag.STATUS_NORMAL);
		khXxTagService.updateStatus(khXxTag);
		return renderResult(Global.TRUE, text("启用客户阅读标签成功"));
	}
	
	/**
	 * 删除客户阅读标签
	 */
	@RequiresPermissions("t:khxxtag:khXxTag:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(KhXxTag khXxTag) {
		khXxTagService.delete(khXxTag);
		return renderResult(Global.TRUE, text("删除客户阅读标签成功！"));
	}
	
}