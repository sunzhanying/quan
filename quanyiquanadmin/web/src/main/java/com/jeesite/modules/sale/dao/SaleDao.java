/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sale.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sale.entity.Sale;
import com.jeesite.modules.sale.entity.SaleDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@MyBatisDao
public interface SaleDao extends CrudDao<Sale> {

    @Select("SELECT parent_one FROM t_kh_sale where khid = #{0} limit 1")
    String getParentOne(String khid);

    //显示个人优惠券
    @Select("SELECT id,khid,parent_one,child_one FROM t_kh_sale where khid = #{0} ")
    List<Sale> getSaleListByKhid(String khid);

    @Select("<script>" +
            "SELECT DISTINCT b.`parent_one` AS parentTwo, a.parent_one AS parentOne," +
            "a.khid AS khid,a.child_one AS childOne,c.`child_one` AS childTwo " +
            "FROM t_kh_sale a " +
            "LEFT JOIN t_kh_sale b ON a.`parent_one` = b.`khid` " +
            "LEFT JOIN t_kh_sale c ON a.`child_one` = c.khid" +
            "<if test='khid != null and khid != &quot;&quot; '> " +
            "  AND  a.khid = #{khid} " +
            "  </if> " +
            "</script>")
    List<SaleDto> getSaleListAll(@Param("khid") String khid);

}