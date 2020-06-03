/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.dao.pushlog;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.push.entity.pushlog.PushLs;

/**
 * 推送批次记录流水表用于记录每次生成的推送批次DAO接口
 * @author 李金辉
 * @version 2019-07-18
 */
@MyBatisDao
public interface PushLsDao extends CrudDao<PushLs> {
	
}