/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.khyhq.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.khyhq.entity.KhYhq;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 客户优惠券DAO接口
 * @author 马晓亮
 * @version 2019-07-25
 */
@MyBatisDao
public interface KhYhqDao extends CrudDao<KhYhq> {

    //显示个人优惠券
    @Select("<script>" +
            "select * from t_kh_yhq " +
            "where khid = #{userid} " +
            "<if test='zt == 1'>" +
            " AND zt = #{zt} AND  DATE_ADD(end_date,INTERVAL 1 DAY)  &gt; NOW() " +
            "</if>" +
            "<if test='zt == 2'>" +
            " AND zt = #{zt} " +
            "</if>" +
            "<if test='zt == 3'>" +
            " AND (DATE_ADD(end_date,INTERVAL 1 DAY) &lt; NOW() OR zt = 3) " +
            "</if>" +
            "<if test='je != null and je != &quot;&quot;'>" +
            " AND  ((yh_je &lt;= #{je} AND type = 1) OR (yh_mj &lt;= #{je} AND type = 2)) " +
            "</if>" +
            " order by yh_je desc " +
            "</script>")
    List<KhYhq> findAllList(@Param("userid") String userid, @Param("zt") int zt, @Param("je") String je);

    //未过期优惠券数量
    @Select("<script>" +
            "select count(*) from t_kh_yhq " +
            "where khid = #{userid} " +
            " AND zt = 1 AND  DATE_ADD(end_date,INTERVAL 1 DAY)  &gt; NOW() " +
            "<if test='je != null and je != &quot;&quot;'>" +
            " AND  ((yh_je &lt;= #{je} AND type = 1) OR (yh_mj &lt;= #{je} AND type = 2)) " +
            "</if>" +
            "</script>")
    int countYhq(@Param("userid") String userid, @Param("je") String je);

    //根据优惠券id及用户id查询优惠券数量
    @Select("select count(*) from t_kh_yhq where khid = #{khid} and yhqid = #{yhqid}")
    int countYhqByYhqId(@Param("khid") String khid, @Param("yhqid") String yhqid);

    //修改优惠券状态
    @Update("update t_kh_yhq set zt = #{zt} where id = #{id}")
    void updateYhqZt(@Param("id") String id, @Param("zt") int zt);
	
}