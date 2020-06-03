/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.wx.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.wx.entity.WxConfig;

/**
 * 微信配置DAO接口
 * @author 李金辉
 * @version 2019-07-26
 */
@MyBatisDao
public interface WxConfigDao extends CrudDao<WxConfig> {
	
}