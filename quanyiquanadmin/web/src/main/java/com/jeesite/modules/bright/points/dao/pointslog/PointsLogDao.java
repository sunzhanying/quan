/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.points.dao.pointslog;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.points.entity.pointslog.PointsLog;

/**
 * 客户积分流水表DAO接口
 * @author 李金辉
 * @version 2019-08-14
 */
@MyBatisDao
public interface PointsLogDao extends CrudDao<PointsLog> {
	
}