/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.web.khxx;

import com.jeesite.API.service.Code;
import com.jeesite.API.service.Response;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.content.web.meterail.sortByDefault;
import com.jeesite.modules.bright.setfocus.entity.others.salesroom.Salesroom;
import com.jeesite.modules.bright.setfocus.entity.source.Source;
import com.jeesite.modules.bright.setfocus.service.others.salesroom.SalesroomService;
import com.jeesite.modules.bright.setfocus.service.source.SourceService;
import com.jeesite.modules.bright.t.entity.group.Group;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.bright.t.entity.khxxtag.KhXxTag;
import com.jeesite.modules.bright.t.service.group.GroupService;
import com.jeesite.modules.bright.t.service.khxx.KhXxService;
import com.jeesite.modules.bright.t.service.khxxtag.KhXxTagService;
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
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户信息Controller
 * @author 李金辉
 * @version 2019-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/t/khxx/khXx")
public class KhXxController extends BaseController {

	@Autowired
	private KhXxService khXxService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private SourceService urlSourceService;
	@Autowired
	private KhXxTagService khXxTagService;

	@Autowired
	private SalesroomService salesroomService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public KhXx get(String id, boolean isNewRecord) {
		return khXxService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("t:khxx:khXx:view")
	@RequestMapping(value = {"list", ""})
	public String list(KhXx khXx, Model model,Salesroom salesroom) {
		model.addAttribute("source", urlSourceService.findList(new Source()));
		model.addAttribute("khXx", khXx);
		List<Salesroom> salesroomsList = salesroomService.findList(new Salesroom());
		Collections.sort(salesroomsList,new sortByDefault()); //默认的放前面
		Salesroom salesroom2 = salesroomsList.get(0);
		salesroom2.setName(salesroom2.getName()+"【默认】");

		model.addAttribute("salesroomList",salesroomsList);
		model.addAttribute("salesroom", salesroom);
		return "modules/bright/t/khxx/khXxList";
	}


	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("t:khxx:khXx:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<KhXx> listData(KhXx khXx, HttpServletRequest request, HttpServletResponse response) {
		khXx.setPage(new Page<>(request, response));
		Page<KhXx> page = khXxService.findPage(khXx);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("t:khxx:khXx:view")
	@RequestMapping(value = "form")
	public String form(KhXx khXx, Model model) {
		model.addAttribute("khXx", khXx);
		model.addAttribute("group", groupService.selectGroupByKhXx(new Group(),khXx));
		model.addAttribute("source", urlSourceService.findList(new Source()));
		KhXxTag khXxTag=new KhXxTag();
		khXxTag.setKhid(khXx.getId());
		List<KhXxTag> list=khXxTagService.findList(khXxTag);
		for (KhXxTag t:list) {
			t.setTagName(t.getTagName()+"("+t.getTimes()+")");
		}
		model.addAttribute("tags", list);


		return "modules/bright/t/khxx/khXxForm";
	}

	/**
	 * 保存客户信息
	 */
	@RequiresPermissions("t:khxx:khXx:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated KhXx khXx) {
		khXxService.save(khXx);
		return renderResult(Global.TRUE, text("保存客户信息成功！"));
	}


	/**
	 * 停用数据
	 */

	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable( KhXx khXx) {

		khXxService.updateStatus(khXx);
		return renderResult(Global.TRUE, text("停用数据成功"));
	}

	/**
	 * 启用数据
	 */

	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable( KhXx khXx) {

		khXxService.updateStatus(khXx);
		return renderResult(Global.TRUE, text("启用数据成功"));
	}

	/**
	 * 停用传播
	 */

	@RequestMapping(value = "disablePro")
	@ResponseBody
	public String disablePro(KhXx khXx) {
		khXx.setPropagate("0");
		khXxService.update(khXx);
		return renderResult(Global.TRUE, text("关闭传播权限成功"));
	}

	/**
	 * 启用传播
	 */

	@RequestMapping(value = "enablePro")
	@ResponseBody
	public Response enable(String id, String salesroomId) {
		KhXx khXx = khXxService.get(id);
		if(khXx!=null){
			khXx.setSalesroom(salesroomId);
			khXx.setPropagate("1");
			khXxService.save(khXx);
			return new Response(Code.SUCCESS);
		}
		return new Response(Code.API_NULL_ERROR);


	}

	/**
	 * 权益记录
	 */
	@RequestMapping(value = "khqy")
	public String khqy(KhXx khXx, Model model) {
		model.addAttribute("khXx", khXx);
		return "modules/bright/t/khxx/khqyList";
	}


	@RequestMapping(value = "qylist")
	@ResponseBody
	public Object qyjllist (KhXx khXx) throws ParseException {
		Map map=new HashMap<>();
		/*//购买的会员会员
		map.put("qyjlList",khXxService.findOrderList(khXx.getId()));

		//拥有的权益
		map.put("tqmxList",khXxService.findKhCcList(khXx.getId()));*/


		return map;
	}
}