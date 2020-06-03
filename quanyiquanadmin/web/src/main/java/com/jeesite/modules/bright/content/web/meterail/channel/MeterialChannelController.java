/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.web.meterail.channel;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.content.entity.meterail.channel.MeterialChannel;
import com.jeesite.modules.bright.content.service.meterail.channel.MeterialChannelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 渠道表Controller
 * @author liqingfeng
 * @version 2019-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/content/meterail/channel/meterialChannel")
public class MeterialChannelController extends BaseController {

	@Value("${file.baseDir}")
	private String file_baseDir;
	@Autowired
	private MeterialChannelService meterialChannelService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public MeterialChannel get(String id, boolean isNewRecord) {
		return meterialChannelService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("content:meterail:channel:meterialChannel:view")
	@RequestMapping(value = {"list", ""})
	public String list(MeterialChannel meterialChannel, Model model) {
		model.addAttribute("meterialChannel", meterialChannel);
		return "modules/bright/content/meterail/channel/meterialChannelList";
	}
	
	/**
	 * 查询列表数据
	 */
	/*@RequiresPermissions("content:meterail:channel:meterialChannel:view")*/
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<MeterialChannel> listData(MeterialChannel meterialChannel, HttpServletRequest request, HttpServletResponse response) {
		meterialChannel.setPage(new Page<>(request, response));
		Page<MeterialChannel> page = meterialChannelService.findPage(meterialChannel);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("content:meterail:channel:meterialChannel:view")
	@RequestMapping(value = "form")
	public String form(MeterialChannel meterialChannel, Model model) {
		model.addAttribute("meterialChannel", meterialChannel);
		return "modules/bright/content/meterail/channel/meterialChannelForm";
	}

	/**
	 * 保存渠道表
	 */

	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@Validated MeterialChannel meterialChannel) {
		meterialChannelService.save(meterialChannel);
		return renderResult(Global.TRUE, text("success"));

	}

	/**
	 * 删除渠道表
	 */
	/*@RequiresPermissions("content:meterail:channel:meterialChannel:edit")*/
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(MeterialChannel meterialChannel) {
		meterialChannelService.delete(meterialChannel);
		return renderResult(Global.TRUE, text("删除渠道表成功！"));
	}
	
}