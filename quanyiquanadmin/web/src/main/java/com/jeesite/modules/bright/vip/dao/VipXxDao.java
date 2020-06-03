/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.vip.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.vip.entity.VipXx;

/**
 * 会员信息表，定义会员级别名称DAO接口
 * @author 马晓亮
 * @version 2019-07-02
 */
@MyBatisDao
public interface VipXxDao extends CrudDao<VipXx> {
	
}