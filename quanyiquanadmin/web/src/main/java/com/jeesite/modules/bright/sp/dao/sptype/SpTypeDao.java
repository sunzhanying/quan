/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.dao.sptype;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.sp.entity.sptype.SpType;

/**
 * 商品类型DAO接口
 * @author 马晓亮
 * @version 2019-06-25
 */
@MyBatisDao
public interface SpTypeDao extends CrudDao<SpType> {
	
}