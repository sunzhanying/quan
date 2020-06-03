/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.service.others.authority;

import com.jeesite.modules.bright.setfocus.dao.others.authority.PermissionAuthDao;
import com.jeesite.modules.sys.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class PermissionAuthService {
	@Autowired
	PermissionAuthDao permissionAuthDao;
	/**
	 * 保存数据（插入或更新）
	 * @param
	 */
	@Transactional(readOnly=false)
	public void save(Config config) {
		permissionAuthDao.save(config);
	}

}