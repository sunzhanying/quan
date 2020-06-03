/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.dao.khxx;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 客户信息DAO接口
 * @author 李金辉
 * @version 2019-06-24
 */
@MyBatisDao
public interface KhXxDao extends CrudDao<KhXx> {

    //根据手机号查询
    @Select("select count(*) from t_kh_xx where sj = #{0}")
    int countByPhone(String phone);

    List<KhXx> sql(@Param("khXx") KhXx khXx, @Param("sql") String sql);

    //根据 sql 查询客户
    @Select("<script>" +
            "select * from t_kh_xx " +
            "<if test='val!=null and val!= &quot;&quot;'>" +
            "  ${val}" +
            "</if>" +
            "</script>")
    List<KhXx> queryBySql(@Param("val") String val);

    //更新crmz账号id
    @Update("update t_kh_xx set crmid = #{crmid} where id = #{id}")
    int updateCrmid(@Param("id") String id, @Param("crmid") Long crmid);
}