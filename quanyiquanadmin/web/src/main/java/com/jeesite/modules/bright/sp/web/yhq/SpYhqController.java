/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.web.yhq;

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
import com.jeesite.modules.bright.sp.entity.yhq.SpYhq;
import com.jeesite.modules.bright.sp.service.yhq.SpYhqService;

/**
 * 优惠券定义表Controller
 * @author 马晓亮
 * @version 2019-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sp/yhq/spYhq")
public class SpYhqController extends BaseController {

	@Autowired
	private SpYhqService spYhqService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SpYhq get(String id, boolean isNewRecord) {
		return spYhqService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sp:yhq:spYhq:view")
	@RequestMapping(value = {"list", ""})
	public String list(SpYhq spYhq, Model model) {
		model.addAttribute("spYhq", spYhq);
		return "modules/bright/sp/yhq/spYhqList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sp:yhq:spYhq:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SpYhq> listData(SpYhq spYhq, HttpServletRequest request, HttpServletResponse response) {
		spYhq.setPage(new Page<>(request, response));
		Page<SpYhq> page = spYhqService.findPage(spYhq);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sp:yhq:spYhq:view")
	@RequestMapping(value = "form")
	public String form(SpYhq spYhq, Model model) {
		if (spYhq.getIsNewRecord()) {
			spYhq.setSxj("1");
			spYhq.setYhSxlx("1");
			spYhq.setType(SpYhq.YHQ_TYPE_DJQ);
		}
		model.addAttribute("spYhq", spYhq);
		return "modules/bright/sp/yhq/spYhqForm";
	}

	/**
	 * 保存优惠券定义表
	 */
	@RequiresPermissions("sp:yhq:spYhq:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SpYhq spYhq) {
		spYhqService.save(spYhq);
		return renderResult(Global.TRUE, text("保存优惠券定义表成功！"));
	}
	
	/**
	 * 删除优惠券定义表
	 */
	@RequiresPermissions("sp:yhq:spYhq:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SpYhq spYhq) {
		spYhqService.delete(spYhq);
		return renderResult(Global.TRUE, text("删除优惠券定义表成功！"));
	}
	
}