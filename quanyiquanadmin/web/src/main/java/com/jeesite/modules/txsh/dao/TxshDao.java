/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.txsh.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.txsh.entity.Txsh;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 提现审核DAO接口
 * @author 马晓亮
 * @version 2020-03-31
 */
@MyBatisDao
public interface TxshDao extends CrudDao<Txsh> {

    //显示个人优惠券
    @Select("<script>" +
            "SELECT a.id,DATE_FORMAT(a.`create_date`,\"%Y-%m-%d %T\") as create_date,a.`order_id`,c.`spmc`,	" +
            "b.`user_id`,b.buyName,a.`khid`,a.sellName,a.`txje`,a.`zt`                  " +
            "FROM (                                                                     " +
            "SELECT m.id,m.create_date,m.order_id,m.txje,m.khid,m.zt,n.`wxnc` AS sellName    " +
            "FROM a_txsh m INNER JOIN t_kh_xx n ON m.khid = n.id                        " +
            ") a INNER JOIN                                                             " +
            "(                                                                          " +
            "SELECT p.id,p.`sp_id`,p.`user_id`,q.`wxnc` AS buyName                      " +
            "FROM t_order p INNER JOIN t_kh_xx q ON p.user_id = q.id                    " +
            ") b ON a.`order_id` = b.`id` INNER JOIN t_sp_xx c ON b.`sp_id` = c.`id`    " +
            " where 1 = 1" +
            "<if test='orderId != null and orderId != &quot;&quot;'> " +
            "  AND  a.`order_id` = #{orderId} " +
            "  </if> " +
            "<if test='id != null and id != &quot;&quot;'> " +
            "  AND  a.`id` = #{id} " +
            "  </if> " +
            "<if test='wxnc != null and wxnc != &quot;&quot;'> " +
            "  AND  a.`sellName` like concat('%',#{wxnc},'%') " +
            "  </if> " +
            "</script>")
    List<Map<String,String>> findAllList(@Param("orderId") String orderId,@Param("id") String id,@Param("wxnc") String wxnc);


    @Select("<script>" +
            "SELECT SUM(txje) FROM a_txsh "+
            " where 1 = 1" +
            "<if test='khid != null and khid != &quot;&quot;'> " +
            "  AND  khid = #{khid} " +
            "  </if> " +
            "<if test='zt != null and zt != &quot;&quot;'> " +
            "  AND  zt = #{zt} " +
            "  </if> " +
            "</script>")
    Double findYdz(@Param("khid") String khid,@Param("zt") String zt);


	
}