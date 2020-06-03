/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khcc.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.khcc.entity.KhCc;

/**
 * 客户持仓DAO接口
 * @author 马晓亮
 * @version 2019-07-16
 */
@MyBatisDao
public interface KhCcDao extends CrudDao<KhCc> {

    //支付成功核销
    KhCc hxZfKhCc(KhCc khCc);
	
}