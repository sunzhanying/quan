/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.web.group;

import com.google.common.base.Strings;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.t.entity.group.Group;
import com.jeesite.modules.bright.t.entity.group.GroupKh;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import com.jeesite.modules.bright.t.service.group.GroupService;
import com.jeesite.modules.bright.t.service.khxx.KhXxService;
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
import java.util.List;

/**
 * 分组类型表Controller
 * @author 李金辉
 * @version 2019-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/t/group/group")
public class GroupController extends BaseController {

	@Autowired
	private GroupService groupService;
	@Autowired
	private KhXxService khXxService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Group get(String id, boolean isNewRecord) {
		return groupService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("t:group:group:view")
	@RequestMapping(value = {"list", ""})
	public String list(Group group, Model model) {
		model.addAttribute("group", group);
		return "modules/bright/t/group/groupList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("t:group:group:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Group> listData(Group group, HttpServletRequest request, HttpServletResponse response) {
		group.setPage(new Page<>(request, response));
		Page<Group> page = groupService.findPage(group);
		return page;
	}



	@RequestMapping(value = "sqls")
	@ResponseBody
	public Object sqls(String searchSql, KhXx khXx, HttpServletRequest request, HttpServletResponse response) {
		khXx.setPage(new Page<>(request, response));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < searchSql.length(); i++) {
			char c = searchSql.charAt(i);
			switch (c) {
				case '＞':
					sb.append('>');
					break;
				case '＜':
					sb.append("<");
					break;
				case '＇':
					sb.append("\'");
					break;
				case '＂':
					sb.append("\"");
					break;
				default:
					sb.append(c);
					break;
			}
		}
		if(!Strings.isNullOrEmpty(sb.toString())){
			khXx.getSqlMap().put("group"," and "+sb);
		}
		List list=new ArrayList();
		try {
			for (KhXx k:khXxService.findList(khXx)) {
				GroupKh groupKh=new GroupKh();

				groupKh.setKhid(k.getId());
				groupKh.setKhXx(k);
				list.add(groupKh);
			}
			return list;
		}catch (Exception e){
			e.printStackTrace();
			return "请检查sql";
		}
	}


	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("t:group:group:view")
	@RequestMapping(value = "form")
	public String form(Group group, Model model) {
		model.addAttribute("group", group);
		model.addAttribute("khXx", new KhXx());
		return "modules/bright/t/group/groupForm";
	}

	/**
	 * 保存分组类型表
	 */
	@RequiresPermissions("t:group:group:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Group group) {
		groupService.save(group);
		return renderResult(Global.TRUE, text("保存分组类型表成功！"));
	}
	
	/**
	 * 删除分组类型表
	 */
	@RequiresPermissions("t:group:group:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Group group) {
		groupService.delete(group);
		return renderResult(Global.TRUE, text("删除分组类型表成功！"));
	}
	
}