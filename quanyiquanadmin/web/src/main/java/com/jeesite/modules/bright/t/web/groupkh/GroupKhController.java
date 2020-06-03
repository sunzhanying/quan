/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.web.groupkh;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.jeesite.modules.bright.t.dao.khxx.KhXxDao;
import com.jeesite.modules.bright.t.entity.group.GroupKh;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.bright.t.service.group.GroupService;
import com.jeesite.modules.bright.util.message.Message;
import com.jeesite.modules.bright.util.message.MessageEnum;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;

import com.jeesite.modules.bright.t.service.groupkh.GroupKhService;

import java.util.List;

/**
 * 分组用户表Controller
 * @author 李金辉
 * @version 2019-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/t/groupkh/groupKh")
public class GroupKhController extends BaseController {

	@Autowired
	private GroupKhService groupKhService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private KhXxDao khXxDao;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public GroupKh get(String id, boolean isNewRecord) {
		return groupKhService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("t:groupkh:groupKh:view")
	@RequestMapping(value = {"list", ""})
	public String list(GroupKh groupKh, Model model) {
		model.addAttribute("groupKh", groupKh);
		return "modules/bright/t/groupkh/groupKhList";
	}
	
	/**
	 * 查询列表数据
	 */

	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<GroupKh> listData(GroupKh groupKh, HttpServletRequest request, HttpServletResponse response) {
		groupKh.setPage(new Page<>(request, response));
		Page<GroupKh> page = groupKhService.findPage(groupKh);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("t:groupkh:groupKh:view")
	@RequestMapping(value = "form")
	public String form(GroupKh groupKh, Model model) {
		model.addAttribute("groupKh", groupKh);
		return "modules/bright/t/groupkh/groupKhForm";
	}

	/**
	 * 保存分组用户表
	 */
	@RequiresPermissions("t:groupkh:groupKh:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated GroupKh groupKh) {
		groupKhService.save(groupKh);
		return renderResult(Global.TRUE, text("保存分组用户表成功！"));
	}
	
	/**
	 * 停用分组用户表
	 */
	@RequiresPermissions("t:groupkh:groupKh:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(GroupKh groupKh) {
		groupKh.setStatus(GroupKh.STATUS_DISABLE);
		groupKhService.updateStatus(groupKh);
		return renderResult(Global.TRUE, text("停用分组用户表成功"));
	}
	
	/**
	 * 启用分组用户表
	 */
	@RequiresPermissions("t:groupkh:groupKh:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(GroupKh groupKh) {
		groupKh.setStatus(GroupKh.STATUS_NORMAL);
		groupKhService.updateStatus(groupKh);
		return renderResult(Global.TRUE, text("启用分组用户表成功"));
	}
	
	/**
	 * 删除分组用户表
	 */
	@RequiresPermissions("t:groupkh:groupKh:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(GroupKh groupKh) {
		groupKhService.delete(groupKh);
		return renderResult(Global.TRUE, text("删除分组用户表成功！"));
	}


	@RequestMapping(value = "getKh")
	public @ResponseBody Message getKh(Message message,String val,GroupKh groupkh) {
		List<KhXx> list = null;
		try{
			val = val.replace("&gt;",">");
			val = val.replace("&lt;","<");
			list=khXxDao.queryBySql(val);
			if (!Strings.isNullOrEmpty(groupkh.getGroupId().getId())){
				List<GroupKh> groupkhs =groupKhService.findList(groupkh);
				if (!groupkhs.isEmpty()){
					for (KhXx khXx:list){
						for (GroupKh groupkh1:groupkhs){
							if (khXx.getId().equals(groupkh1.getKhid())){
								khXx.setChecked("checked");
							}
						}
					}
				}
			}
			MessageEnum.Success.toMessage(message);
			message.setDatas(list);
		}catch (Exception e){
			e.printStackTrace();
			System.out.print("ssssssssss"+e.getMessage());
			MessageEnum.ADMIN_SQL_ERROR.toMessage(message);
		}
		return message;
	}

	//客户分组与推送
	@RequestMapping(value = "groupkhList")
	public String groupkhList(GroupKh groupkh, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*groupkh.setPage(new Page<>(request, response));
		Page<GroupKh> page = groupKhService.findPage(groupkh);
		model.addAttribute("group", groupService.findList(new Group()));
		model.addAttribute("page", page);*/
		model.addAttribute("groupkh", groupkh);
		return "modules/bright/t/groupkh/groupKhList";

	}

	//客户分组模板适用对象是使用
	@RequestMapping(value = "groupkhList1",method = RequestMethod.GET)
	@ResponseBody
	public List<GroupKh> groupkhList1(GroupKh groupkh, HttpServletRequest request, HttpServletResponse response, Model model) {
		return groupKhService.findList(groupkh);
	}
	
}