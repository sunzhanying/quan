/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.dao.sptype;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.sp.entity.sptype.SpLog;

/**
 * 商品日志
 */
@MyBatisDao
public interface SpLogDao extends CrudDao<SpLog> {
	
}