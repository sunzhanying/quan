/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.banner.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.banner.entity.Banner;

/**
 * 推荐位DAO接口
 * @author 李金辉
 * @version 2019-07-25
 */
@MyBatisDao
public interface BannerDao extends CrudDao<Banner> {
	
}