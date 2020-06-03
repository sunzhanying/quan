/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.active.dao.sign;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.active.entity.sign.ActiveSign;

/**
 * 活动 课程 报名表DAO接口
 * @author 李金辉
 * @version 2019-08-06
 */
@MyBatisDao
public interface ActiveSignDao extends CrudDao<ActiveSign> {
	
}