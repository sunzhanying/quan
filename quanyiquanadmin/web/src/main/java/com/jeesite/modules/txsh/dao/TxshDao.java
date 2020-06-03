/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.txsh.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.txsh.entity.Txsh;

/**
 * 提现审核DAO接口
 * @author 马晓亮
 * @version 2020-03-31
 */
@MyBatisDao
public interface TxshDao extends CrudDao<Txsh> {
	
}