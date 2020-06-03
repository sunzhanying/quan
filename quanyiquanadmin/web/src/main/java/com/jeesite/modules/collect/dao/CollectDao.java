/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.collect.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.collect.entity.Collect;

/**
 * 收藏权益DAO接口
 * @author 收藏
 * @version 2020-03-30
 */
@MyBatisDao
public interface CollectDao extends CrudDao<Collect> {
	
}