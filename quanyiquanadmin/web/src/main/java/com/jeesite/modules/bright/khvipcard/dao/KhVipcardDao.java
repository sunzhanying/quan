/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khvipcard.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.khvipcard.entity.KhVipcard;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户会员卡DAO接口
 * @author 马晓亮
 * @version 2019-08-16
 */
@MyBatisDao
public interface KhVipcardDao extends CrudDao<KhVipcard> {

    //查询用户会员卡
    @Select("select * from t_kh_vipcard where khid = #{khid} and ye > 0 and zt = #{zt}")
    List<KhVipcard> findByKhidByZt(@Param("khid") String khid, @Param("zt") String zt);

    //查询用户会员数量
    @Select("select count(*) from t_kh_vipcard where khid = #{khid} and ye > 0 and zt = #{zt}")
    int countByKhidByZt(@Param("khid")  String khid, @Param("zt") String zt);
}