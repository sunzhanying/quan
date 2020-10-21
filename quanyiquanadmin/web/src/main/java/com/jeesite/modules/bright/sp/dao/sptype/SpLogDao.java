/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.dao.sptype;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.sp.entity.sptype.SpLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品日志
 */
@MyBatisDao
public interface SpLogDao extends CrudDao<SpLog> {
    @Select("<script>" +
            "SELECT id,khid,spid,TYPE,NAME,ms,create_date FROM t_sp_log where khid = #{khid} ORDER BY create_date DESC LIMIT 8" +
            "</script>")
    List<SpLog> getLogs(@Param("khid") String khid );


    @Select("<script>" +
            "SELECT id,khid,spid,TYPE,NAME,ms,create_date FROM t_sp_log where khid = #{khid} and  spid = #{spid} LIMIT 1" +
            "</script>")
    SpLog getLogsByKhidAndSpid(@Param("khid") String khid ,@Param("spid") String spid);

}