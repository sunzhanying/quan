/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.web.others.authority;


import com.jeesite.common.web.BaseController;
import com.jeesite.modules.bright.setfocus.service.others.authority.PermissionAuthService;
import com.jeesite.modules.sys.entity.Config;
import com.jeesite.modules.sys.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 传播人权限Controller
 * @author liqingfeng
 * @version 2019-07-05
 */
@Controller
@RequestMapping(value = "${adminPath}/setfocus/others/authority")
public class permissionAuthController extends BaseController {

	@Autowired
	private ConfigService configService;
	@Autowired
	private PermissionAuthService permissionAuthService;
	@RequestMapping(value = "permissionAuth")
	public String permissionAuth(Config config, Model model) {
		Config config1 = configService.get(new Config("1150627636220055552"));
		config.setConfigValue(config1.getConfigValue());
		model.addAttribute("config", config);
		return "modules/bright/setfocus/others/authority/permissionAuth";
	}

	@RequestMapping(value = "permissionAuth/save")
	public String save(Config config) {
		permissionAuthService.save(config);
		return "modules/bright/setfocus/others/authority/permissionAuth";
	}

}