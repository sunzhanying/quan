/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.web.push;

import com.google.common.base.Strings;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.push.entity.push.Push;
import com.jeesite.modules.bright.push.entity.push.PushEngine;
import com.jeesite.modules.bright.push.entity.templet.Xxtsmb;
import com.jeesite.modules.bright.push.service.push.PushService;
import com.jeesite.modules.bright.push.service.templet.XxtsmbService;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.bright.t.entity.group.Group;
import com.jeesite.modules.bright.t.service.group.GroupService;
import com.jeesite.modules.bright.util.PushEngineUtil;
import com.jeesite.modules.bright.util.StartEngineUtil;
import com.jeesite.modules.bright.util.StopEngineUtil;
import com.jeesite.modules.bright.util.TimerEngineUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * t_pushController
 * @author 李金辉
 * @version 2019-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/push/push/push")
public class PushController extends BaseController {

	@Autowired
	private PushService pushService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private XxtsmbService xxtsmbService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Push get(String id, boolean isNewRecord) {
		return pushService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("push:push:push:view")
	@RequestMapping(value = {"list", ""})
	public String list(Push push, Model model) {
		model.addAttribute("push", push);
		return "modules/bright/push/push/pushList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("push:push:push:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Push> listData(Push push, HttpServletRequest request, HttpServletResponse response) {
		push.setPage(new Page<>(request, response));
		Page<Push> page = pushService.findPage(push);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("push:push:push:view")
	@RequestMapping(value = "form")
	public String form(Push push, Model model) {
		model.addAttribute("push", push);
		return "modules/bright/push/push/pushForm";
	}
	/**
	 * 查看编辑表单
	 */

	@RequestMapping(value = "pushList")
	public String pushList(Push push, Model model) {
		model.addAttribute("push", push);
		model.addAttribute("group", groupService.findList(new Group()));
		model.addAttribute("xxtsmb", xxtsmbService.findList(new Xxtsmb()));
		return "modules/bright/push/push/push";
	}

	/**
	 * 保存t_push
	 */
	@RequiresPermissions("push:push:push:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Push push) {
		pushService.save(push);
		return renderResult(Global.TRUE, text("保存t_push成功！"));
	}
	
	/**
	 * 停用t_push
	 */
	@RequiresPermissions("push:push:push:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(Push push) {
		push.setStatus(Push.STATUS_DISABLE);
		pushService.updateStatus(push);
		return renderResult(Global.TRUE, text("停用t_push成功"));
	}
	
	/**
	 * 启用t_push
	 */
	@RequiresPermissions("push:push:push:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(Push push) {
		push.setStatus(Push.STATUS_NORMAL);
		pushService.updateStatus(push);
		return renderResult(Global.TRUE, text("启用t_push成功"));
	}
	
	/**
	 * 删除t_push
	 */
	@RequiresPermissions("push:push:push:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Push push) {
		pushService.delete(push);
		return renderResult(Global.TRUE, text("删除t_push成功！"));
	}

	@RequestMapping(value = "push")
	public String push (Push push,RedirectAttributes redirectAttributes){
		if (!Strings.isNullOrEmpty(push.getGroupId())){
			push.setRet(0);
			push.setRetcode("");
			push.setTsqd("1");
			if (push.getBz() == null){
				push.setBz(0);
			}
			System.out.println("是否动态"+push.getBz());
			pushService.push(push);
		}

		addMessage(redirectAttributes, "保存推送成功");
		return "redirect:"+Global.getAdminPath()+"/push/push/?repage";
	}

	@RequestMapping(value = "pushEngine")
	public String pushEngine(HttpServletRequest request, RedirectAttributes redirectAttributes){
		request.setAttribute("status", PushEngineUtil.getPro("pushEngine.status"));
		Map map=new HashMap();
		for (Map.Entry e: PushEngineUtil.prop.entrySet()) {
			map.put(e.getKey(),e.getValue());
		}

		request.setAttribute("properties",map);

		//push_channel  推送渠道记录数
		String qdms = "";

		List<DictData> dicts = DictUtils.getDictList("pr_push_channel");
		for (DictData dict:dicts){
			qdms += dict.getDictLabel() + ": " + pushService.queryQdTsjls(dict.getDictValue()) + " ";
		}
		request.setAttribute("qdms", qdms);
		//已推待推数量4
		String  ytdt = "";
		ytdt = "已推： "+pushService.ytsl() + "  待推： " + pushService.dtsl();
		request.setAttribute("ytdt", ytdt);
		//成功失败数量
		String cgsb = "";
		cgsb = "成功： "+ pushService.success() + "  失败： " + pushService.error();
		request.setAttribute("cgsb", cgsb);

		request.setAttribute("qtsj","启停时间为："+PushEngineUtil.getPro("pushEngine.autoStart")+
				"-"+PushEngineUtil.getPro("pushEngine.autoStop")+"。间隔为："+PushEngineUtil.getPro("pushEngine.interval") +"分");
		return "modules/bright/push/push/pushEngine";
	}

	@RequestMapping("statusPushEngine")
	public String statusPushEngine(HttpServletRequest request, RedirectAttributes redirectAttributes){
		String status = request.getParameter("status");
		try {
			if (status != null) {
				if (status.equals("1")) {
					TimerEngineUtil.onEngine();
				} else if (status.equals("2")) {
					TimerEngineUtil.offEngine("0");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return "modules/bright/push/pushEngine";
	}




	@RequestMapping(value = "updatePorperties")
	public String updatePorperties(PushEngine pushEngine, HttpServletRequest request, RedirectAttributes redirectAttributes){

		if ("1".equals(pushEngine.getStatus())){  //启动时 修改配置需关闭在重启
			if (StartEngineUtil.p_ontimer != null){
				//关闭自启
				StartEngineUtil.offEngine();
			}
			//启动自启
			StartEngineUtil.onEngine();
			if (StopEngineUtil.p_offtimer != null){
				//关闭 关闭
				StopEngineUtil.offEngine();
			}
			//启动  关闭
			StopEngineUtil.onEngine();

			//关闭推送引擎
			TimerEngineUtil.offEngine("0");
			//启动推送引擎
			TimerEngineUtil.onEngine();
		}
		try {
			//PushEngineUtil.updatePro("pushEngine.status",pushEngine.getStatus());
			PushEngineUtil.updatePro("pushEngine.autoStart",pushEngine.getAutoStart());
			PushEngineUtil.updatePro("pushEngine.autoStop",pushEngine.getAutoStop());
			PushEngineUtil.updatePro("pushEngine.pushNumber",pushEngine.getPushNumber());
			PushEngineUtil.updatePro("pushEngine.interval",pushEngine.getInterval());

			PushEngineUtil.updatePro("pushEngine.offMode","0");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("属性文件更新错误");
		}
		addMessage(redirectAttributes, "推送引擎设置成功");

		return "redirect:"+Global.getAdminPath()+"/push/push/push/pushEngine?repage";
	}

}