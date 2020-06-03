/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.web.others.externallink;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.setfocus.entity.others.externallink.MeterialExternalLink;
import com.jeesite.modules.bright.setfocus.service.others.externallink.MeterialExternalLinkService;
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
import java.util.List;

/**
 * 外部链接配置Controller
 * @author liqingfeng
 * @version 2019-07-12
 */
@Controller
@RequestMapping(value = "${adminPath}/setfocus/others/externallink/meterialExternalLink")
public class MeterialExternalLinkController extends BaseController {

	@Autowired
	private MeterialExternalLinkService meterialExternalLinkService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public MeterialExternalLink get(String id, boolean isNewRecord) {
		return meterialExternalLinkService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	/*@RequiresPermissions("setfocus:others:externallink:meterialExternalLink:view")*/
	@RequestMapping(value = {"list", ""})
	public String list(MeterialExternalLink meterialExternalLink, Model model) {
		model.addAttribute("meterialExternalLink", meterialExternalLink);
		return "modules/bright/setfocus/others/externallink/meterialExternalLinkList";
	}


	@RequestMapping(value = "getAllLink")
	@ResponseBody
	public List<MeterialExternalLink> getAllLink(Model model) {
		return  meterialExternalLinkService.findList(new MeterialExternalLink());
	}

	/**
	 * 查询列表数据
	 */
	/*@RequiresPermissions("setfocus:others:externallink:meterialExternalLink:view")*/
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<MeterialExternalLink> listData(MeterialExternalLink meterialExternalLink, HttpServletRequest request, HttpServletResponse response) {
		meterialExternalLink.setPage(new Page<>(request, response));
		Page<MeterialExternalLink> page = meterialExternalLinkService.findPage(meterialExternalLink);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	/*@RequiresPermissions("setfocus:others:externallink:meterialExternalLink:view")*/
	@RequestMapping(value = "form")
	public String form(MeterialExternalLink meterialExternalLink, Model model) {
		model.addAttribute("meterialExternalLink", meterialExternalLink);
		return "modules/bright/setfocus/others/externallink/meterialExternalLinkForm";
	}

	/**
	 * 保存外部链接配置
	 */
	/*@RequiresPermissions("setfocus:others:externallink:meterialExternalLink:edit")*/
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated MeterialExternalLink meterialExternalLink) {
		meterialExternalLinkService.save(meterialExternalLink);
		return renderResult(Global.TRUE, text("保存外部链接配置成功！"));
	}
	
	/**
	 * 删除外部链接配置
	 */
	/*@RequiresPermissions("setfocus:others:externallink:meterialExternalLink:edit")*/
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(MeterialExternalLink meterialExternalLink) {
		meterialExternalLinkService.delete(meterialExternalLink);
		return renderResult(Global.TRUE, text("删除外部链接配置成功！"));
	}

	/**
	 * 停用数据
	 */

	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(MeterialExternalLink meterialExternalLink) {
		meterialExternalLink.setStatus("2");
		meterialExternalLinkService.updateStatus(meterialExternalLink);
		return renderResult(Global.TRUE, text("停用链接成功"));
	}

	/**
	 * 启用数据
	 */

	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(MeterialExternalLink meterialExternalLink) {
		meterialExternalLink.setStatus("0");
		meterialExternalLinkService.updateStatus(meterialExternalLink);
		return renderResult(Global.TRUE, text("启用链接成功"));
	}
	
}