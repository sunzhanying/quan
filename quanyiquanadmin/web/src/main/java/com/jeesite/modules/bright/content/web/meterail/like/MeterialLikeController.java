/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.web.meterail.like;

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
import com.jeesite.modules.bright.content.entity.meterail.like.MeterialLike;
import com.jeesite.modules.bright.content.service.meterail.like.MeterialLikeService;

/**
 * 关注素材表Controller
 * @author liqingfeng
 * @version 2019-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/content/meterail/like/meterialLike")
public class MeterialLikeController extends BaseController {

	@Autowired
	private MeterialLikeService meterialLikeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public MeterialLike get(String id, boolean isNewRecord) {
		return meterialLikeService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("content:meterail:like:meterialLike:view")
	@RequestMapping(value = {"list", ""})
	public String list(MeterialLike meterialLike, Model model) {
		model.addAttribute("meterialLike", meterialLike);
		return "modules/bright/content/meterail/like/meterialLikeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("content:meterail:like:meterialLike:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<MeterialLike> listData(MeterialLike meterialLike, HttpServletRequest request, HttpServletResponse response) {
		meterialLike.setPage(new Page<>(request, response));
		Page<MeterialLike> page = meterialLikeService.findPage(meterialLike);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("content:meterail:like:meterialLike:view")
	@RequestMapping(value = "form")
	public String form(MeterialLike meterialLike, Model model) {
		model.addAttribute("meterialLike", meterialLike);
		return "modules/bright/content/meterail/like/meterialLikeForm";
	}

	/**
	 * 保存关注素材表
	 */
	@RequiresPermissions("content:meterail:like:meterialLike:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated MeterialLike meterialLike) {
		meterialLikeService.save(meterialLike);
		return renderResult(Global.TRUE, text("保存关注素材表成功！"));
	}
	
	/**
	 * 删除关注素材表
	 */
	@RequiresPermissions("content:meterail:like:meterialLike:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(MeterialLike meterialLike) {
		meterialLikeService.delete(meterialLike);
		return renderResult(Global.TRUE, text("删除关注素材表成功！"));
	}
	
}