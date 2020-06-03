/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.dao.others.authority;


import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sys.entity.Config;
import org.apache.ibatis.annotations.Update;

/**
 * 传播人权限Controller
 * @author liqingfeng
 * @version 2019-07-05
 */
@MyBatisDao
public interface PermissionAuthDao extends CrudDao<Config> {
	@Update("update js_sys_config set config_value=#{configValue} where id='1150627636220055552'")
	void save(Config config);

}