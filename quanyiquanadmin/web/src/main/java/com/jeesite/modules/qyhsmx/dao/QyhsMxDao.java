/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.qyhsmx.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import org.apache.ibatis.annotations.Select;

/**
 * 权益回收明细DAO接口
 * @author 马晓亮
 * @version 2020-03-25
 */
@MyBatisDao
public interface QyhsMxDao extends CrudDao<QyhsMx> {
    //根据手机号查询
    @Select("select count(*) from a_qyhs_mx where qyq_id = #{0} and zt in ('1','3')")
    int countByQyqAndZt(String qyqid);
}