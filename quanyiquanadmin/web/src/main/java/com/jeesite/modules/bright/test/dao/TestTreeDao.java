/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.test.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.test.entity.TestTree;

/**
 * 测试树表DAO接口
 * @author ThinkGem
 * @version 2019-06-24
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}