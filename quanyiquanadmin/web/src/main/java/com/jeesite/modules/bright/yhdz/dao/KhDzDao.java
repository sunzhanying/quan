/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.yhdz.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.yhdz.entity.KhDz;
import org.apache.ibatis.annotations.Update;

/**
 * 用户地址表DAO接口
 * @author 马晓亮
 * @version 2019-07-12
 */
@MyBatisDao
public interface KhDzDao extends CrudDao<KhDz> {

    //默认地址更新为不默认
    @Update("update t_kh_dz set is_default = 0 where user_id = #{0}")
    int updateIsDefault(String userid);
	
}