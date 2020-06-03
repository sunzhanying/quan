/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.web.others.salesroom;

import com.google.common.base.Strings;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.content.web.meterail.sortByDefault;
import com.jeesite.modules.bright.setfocus.entity.others.salesroom.Salesroom;
import com.jeesite.modules.bright.setfocus.service.others.salesroom.SalesroomService;
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
import java.util.Collections;
import java.util.List;

/**
 * 门店设置/传播渠道设置Controller
 * @author liqingfeng
 * @version 2019-08-23
 */
@Controller
@RequestMapping(value = "${adminPath}/bright/setfocus/others/salesroom/salesroom")
public class SalesroomController extends BaseController {

	@Autowired
	private SalesroomService salesroomService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Salesroom get(String id, boolean isNewRecord) {
		return salesroomService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("bright:setfocus:others:salesroom:salesroom:view")
	@RequestMapping(value = {"list", ""})
	public String list(Salesroom salesroom, Model model) {
		model.addAttribute("salesroom", salesroom);
		List<Salesroom> salesroomsList = salesroomService.findList(new Salesroom());
		Collections.sort(salesroomsList,new sortByDefault()); //默认的放前面
		Salesroom salesroom2 = salesroomsList.get(0);
		salesroom2.setName(salesroom2.getName()+"【默认】");

		model.addAttribute("salesroomList",salesroomsList);

		return "modules/bright/setfocus/others/salesroom/salesroomList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("bright:setfocus:others:salesroom:salesroom:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Salesroom> listData(Salesroom salesroom, HttpServletRequest request, HttpServletResponse response) {
		salesroom.setPage(new Page<>(request, response));
		Page<Salesroom> page = salesroomService.findPage(salesroom);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("bright:setfocus:others:salesroom:salesroom:view")
	@RequestMapping(value = "form")
	public String form(Salesroom salesroom, Model model) {
		model.addAttribute("salesroom", salesroom);
		if(Strings.isNullOrEmpty(salesroom.getIsdefault())){
			salesroom.setIsdefault(Salesroom.ISDEFAULT_NO);
		}
		return "modules/bright/setfocus/others/salesroom/salesroomForm";
	}

	/**
	 * 保存门店设置/传播渠道设置
	 */
	@RequiresPermissions("bright:setfocus:others:salesroom:salesroom:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Salesroom salesroom) {
		salesroomService.save(salesroom);
		return renderResult(Global.TRUE, text("保存传播渠道成功！"));
	}
	
	/**
	 * 删除门店设置/传播渠道设置
	 */
	@RequiresPermissions("bright:setfocus:others:salesroom:salesroom:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Salesroom salesroom) {
		salesroomService.delete(salesroom);
		return renderResult(Global.TRUE, text("删除传播渠道成功！"));
	}
	/**
	 * 删除门店设置/传播渠道设置
	 */
	@RequestMapping(value = "getSalesroom")
	@ResponseBody
	public List<Salesroom> getSalesroom() {
		List<Salesroom> salesroomsList = salesroomService.findList(new Salesroom());
		Collections.sort(salesroomsList,new sortByDefault()); //默认的放前面
		Salesroom salesroom = salesroomsList.get(0);
		salesroom.setName(salesroom.getName()+"【默认】");
		return salesroomsList;
	}

	
}