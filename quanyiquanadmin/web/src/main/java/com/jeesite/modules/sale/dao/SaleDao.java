/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sale.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sale.entity.Sale;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@MyBatisDao
public interface SaleDao extends CrudDao<Sale> {

    @Select("SELECT parent_one FROM t_kh_sale where khid = #{0} limit 1")
    String getParentOne(String khid);

    //显示个人优惠券
    @Select("SELECT id,khid,parent_one,child_one FROM t_kh_sale where khid = #{0} ")
    List<Sale> getSaleListByKhid(String khid);

}