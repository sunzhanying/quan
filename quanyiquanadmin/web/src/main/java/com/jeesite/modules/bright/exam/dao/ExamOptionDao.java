/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.exam.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.exam.entity.ExamOption;

/**
 * 提目表DAO接口
 * @author 马晓亮
 * @version 2019-08-05
 */
@MyBatisDao
public interface ExamOptionDao extends CrudDao<ExamOption> {
	
}