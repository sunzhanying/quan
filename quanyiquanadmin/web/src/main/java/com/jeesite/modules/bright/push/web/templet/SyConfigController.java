/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.web.templet;

import com.google.common.base.Strings;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.push.entity.templet.SyConfig;
import com.jeesite.modules.bright.push.entity.templet.SyMb;
import com.jeesite.modules.bright.push.service.templet.SyConfigService;
import com.jeesite.modules.bright.push.service.templet.SyMbService;
import com.jeesite.modules.bright.t.entity.group.Group;
import com.jeesite.modules.bright.t.service.group.GroupService;
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
import java.util.ArrayList;

/**
 * 生涯配置表，将模板配置给客户（学生）、群组。（配置二选一，即一般配置给群组，亦可配置给客户）流程：1、配置模板2、配置模板适用对象：群组、客户、学生3、生涯任务生成：读此表，结合生涯模板表。生成t_sy_rwmx，同是根据推送渠道生成等推送记录写入push表界面：生涯生成界面要定制，Controller
 * @author 李金辉
 * @version 2019-07-17
 */
@Controller
@RequestMapping(value = "${adminPath}/push/templet/syConfig")
public class SyConfigController extends BaseController {

	@Autowired
	private SyConfigService syConfigService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private SyMbService syMbService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SyConfig get(String id, boolean isNewRecord) {
		return syConfigService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("push:templet:syConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(SyConfig syConfig, Model model) {
		model.addAttribute("syConfig", syConfig);
		return "modules/bright/push/templet/syConfigList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("push:templet:syConfig:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SyConfig> listData(SyConfig syConfig, HttpServletRequest request, HttpServletResponse response) {
		syConfig.setPage(new Page<>(request, response));

		Page<SyConfig> page = syConfigService.findPage(syConfig);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("push:templet:syConfig:view")
	@RequestMapping(value = "form")
	public String form(SyConfig syConfig, Model model) {
		model.addAttribute("syConfig", syConfig);
		//非新增
		if (syConfig.getGroupid() != null) {
			model.addAttribute("groupKh", groupService.get(syConfig.getGroupid()).getGroupKhList());
			model.addAttribute("symbmx", syMbService.get(syConfig.getMbid()).getSyMbmxList());
		}else{
			model.addAttribute("groupKh",new ArrayList<>());
			model.addAttribute("symbmx", new ArrayList<>());

		}
		model.addAttribute("group", groupService.findList(new Group()));
		model.addAttribute("symb", syMbService.findList(new SyMb()));
		return "modules/bright/push/templet/syConfigForm";
	}

	/**
	 * 保存生涯配置表，将模板配置给客户（学生）、群组。
	 */
	@RequiresPermissions("push:templet:syConfig:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SyConfig syConfig) {
		syConfigService.save(syConfig);
		return renderResult(Global.TRUE, text("保存生涯配置表，将模板配置给客户（学生）、群组。成功！"));
	}
	
	/**
	 * 停用生涯配置表，将模板配置给客户（学生）、群组。
	 */
	@RequiresPermissions("push:templet:syConfig:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(SyConfig syConfig) {
		syConfig.setStatus(SyConfig.STATUS_DISABLE);
		syConfigService.updateStatus(syConfig);
		return renderResult(Global.TRUE, text("停用生涯配置表，将模板配置给客户（学生）、群组。成功"));
	}
	
	/**
	 * 启用生涯配置表，将模板配置给客户（学生）、群组。
	 */
	@RequiresPermissions("push:templet:syConfig:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(SyConfig syConfig) {
		syConfig.setStatus(SyConfig.STATUS_NORMAL);
		syConfigService.updateStatus(syConfig);
		return renderResult(Global.TRUE, text("启用生涯配置表，将模板配置给客户（学生）、群组。成功"));
	}
	
	/**
	 * 删除生涯配置表，将模板配置给客户（学生）、群组。
	 */
	@RequiresPermissions("push:templet:syConfig:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SyConfig syConfig) {
		syConfigService.delete(syConfig);
		return renderResult(Global.TRUE, text("删除生涯配置表，将模板配置给客户（学生）、群组。成功！"));
	}

	/**
	 * 获取分组人
	 */

	@RequestMapping(value = "group")
	@ResponseBody
	public Object group(String groupId) {
		Group group=groupService.get(groupId);
		if (group != null) {
			return  group.getGroupKhList();
		}
		return null;
	}
	/**
	 * 获取模板mx
	 */

	@RequestMapping(value = "symb")
	@ResponseBody
	public Object symb(String mbid) {
		SyMb syMb=syMbService.get(mbid);
		if (syMb != null) {
			return  syMb.getSyMbmxList();
		}
		return null;
	}


	@RequestMapping(value = "pushlist")
	@ResponseBody
	public String pushlist(SyConfig syConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		String  text="";
		try {
			if (!Strings.isNullOrEmpty(syConfig.getId())){

				if (Strings.isNullOrEmpty(syConfig.getGroupdz())){
					syConfig.setGroupdz("0");
				}
				if (Strings.isNullOrEmpty(syConfig.getXsBz())){
					syConfig.setXsBz("0");
				}
				System.out.println("是否动态"+syConfig.getGroupdz());
				syConfigService.push(syConfig);
				if ("1".equals(syConfig.getRet())){
					//改变配置表状态
					syConfig.setPushZt("1");
					syConfigService.save(syConfig);
					text="推送成功";
				}else{
					text="推送失败";

				}


			}
		}catch (Exception e){
			text="推送失败";
		}

		return renderResult(Global.TRUE, text(text));
		/*model.addAttribute("syConfig", syConfig);
		return "modules/bright/push/templet/syConfigList";*/


	}

}