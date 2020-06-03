/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.active.web.active;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;

import com.google.zxing.qrcode.encoder.QRCode;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.bright.sp.service.SpXxService;

import com.jeesite.modules.bright.util.QRCodeUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.jeesite.modules.bright.active.entity.active.NrHd;
import com.jeesite.modules.bright.active.service.active.NrHdService;

/**
 * 活动Controller
 * @author 李金辉
 * @version 2019-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/bright/active/active/nrHd")
public class NrHdController extends BaseController {

	@Autowired
	private NrHdService nrHdService;
	@Autowired
	private SpXxService spXxService;
	@Value("${file.baseDir}")
	private String fileBaseDir;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public NrHd get(String id, boolean isNewRecord) {
		return nrHdService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("bright:active:active:nrHd:view")
	@RequestMapping(value = {"list", ""})
	public String list(NrHd nrHd, Model model) {
		model.addAttribute("nrHd", nrHd);

		return "modules/bright/active/active/nrHdList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bright:active:active:nrHd:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<NrHd> listData(NrHd nrHd, HttpServletRequest request, HttpServletResponse response) {
		nrHd.setPage(new Page<>(request, response));
		Page<NrHd> page = nrHdService.findPage(nrHd);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bright:active:active:nrHd:view")
	@RequestMapping(value = "form")
	public String form(NrHd nrHd, Model model) {
		model.addAttribute("nrHd", nrHd);
		model.addAttribute("sp", spXxService.findList(new SpXx()));
		return "modules/bright/active/active/nrHdForm";
	}

	/**
	 * 保存活动
	 */
	@RequiresPermissions("bright:active:active:nrHd:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated NrHd nrHd) throws Exception {
		if(Strings.isNullOrEmpty(nrHd.getQrcode())){

			nrHd.setQrcode(QRCodeUtil.qrCode(nrHd.getId(),"active",nrHd.getId()));
		}
		nrHdService.save(nrHd);
		return renderResult(Global.TRUE, text("保存活动成功！"));
	}
	
	/**
	 * 停用活动
	 */
	@RequiresPermissions("bright:active:active:nrHd:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(NrHd nrHd) {
		nrHd.setStatus(NrHd.STATUS_DISABLE);
		nrHdService.updateStatus(nrHd);
		return renderResult(Global.TRUE, text("停用活动成功"));
	}
	
	/**
	 * 启用活动
	 */
	@RequiresPermissions("bright:active:active:nrHd:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(NrHd nrHd) {
		nrHd.setStatus(NrHd.STATUS_NORMAL);
		nrHdService.updateStatus(nrHd);
		return renderResult(Global.TRUE, text("启用活动成功"));
	}
	
	/**
	 * 删除活动
	 */
	@RequiresPermissions("bright:active:active:nrHd:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(NrHd nrHd) {
		nrHdService.delete(nrHd);
		return renderResult(Global.TRUE, text("删除活动成功！"));
	}
	
}