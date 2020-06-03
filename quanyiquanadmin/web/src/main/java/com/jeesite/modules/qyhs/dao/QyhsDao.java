/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyhs.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.qyhs.entity.Qyhs;

/**
 * 权益回收DAO接口
 * @author 马晓亮
 * @version 2020-03-25
 */
@MyBatisDao
public interface QyhsDao extends CrudDao<Qyhs> {
	
}