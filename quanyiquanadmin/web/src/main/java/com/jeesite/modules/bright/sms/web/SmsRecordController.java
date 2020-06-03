/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sms.web;

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
import com.jeesite.modules.bright.sms.entity.SmsRecord;
import com.jeesite.modules.bright.sms.service.SmsRecordService;

/**
 * sms_recordController
 * @author 马晓亮
 * @version 2019-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsRecord")
public class SmsRecordController extends BaseController {

	@Autowired
	private SmsRecordService smsRecordService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SmsRecord get(String id, boolean isNewRecord) {
		return smsRecordService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sms:smsRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsRecord smsRecord, Model model) {
		model.addAttribute("smsRecord", smsRecord);
		return "modules/bright/sms/smsRecordList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sms:smsRecord:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SmsRecord> listData(SmsRecord smsRecord, HttpServletRequest request, HttpServletResponse response) {
		smsRecord.setPage(new Page<>(request, response));
		Page<SmsRecord> page = smsRecordService.findPage(smsRecord);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sms:smsRecord:view")
	@RequestMapping(value = "form")
	public String form(SmsRecord smsRecord, Model model) {
		model.addAttribute("smsRecord", smsRecord);
		return "modules/bright/sms/smsRecordForm";
	}

	/**
	 * 保存sms_record
	 */
	@RequiresPermissions("sms:smsRecord:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SmsRecord smsRecord) {
		smsRecordService.save(smsRecord);
		return renderResult(Global.TRUE, text("保存sms_record成功！"));
	}
	
	/**
	 * 删除sms_record
	 */
	@RequiresPermissions("sms:smsRecord:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SmsRecord smsRecord) {
		smsRecordService.delete(smsRecord);
		return renderResult(Global.TRUE, text("删除sms_record成功！"));
	}
	
}