/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyjg.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.qyjg.entity.Qyjg;

/**
 * 权益券价格DAO接口
 * @author 马晓亮
 * @version 2020-03-24
 */
@MyBatisDao
public interface QyjgDao extends CrudDao<Qyjg> {
	
}