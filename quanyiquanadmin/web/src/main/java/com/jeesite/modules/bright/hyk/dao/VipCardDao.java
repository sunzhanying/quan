/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.hyk.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.hyk.entity.VipCard;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 会员卡DAO接口
 * @author 马晓亮
 * @version 2019-08-15
 */
@MyBatisDao
public interface VipCardDao extends CrudDao<VipCard> {

    //更新会员卡上下架
    @Update("update t_vip_card set sxj = #{sxj} where id = #{id}")
    int updateSxj(@Param("sxj") String sxj, @Param("id") String id);
	
}