/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.dao.sptype;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.sp.entity.sptype.SpType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 商品类型DAO接口
 * @author 马晓亮
 * @version 2019-06-25
 */
@MyBatisDao
public interface SpTypeDao extends CrudDao<SpType> {

    //显示商品列表
    @Select("<script>" +
            "select id,name,parent,img FROM t_sp_type a " +
            "where parent IS NOT NULL  and parent != ''" +
            "<if test='parent != null and parent != &quot;&quot;'>" +
            " AND parent = #{parent} " +
            "</if>" +
            "</script>")
    List<Map<String,String>> findTwoSpList(@Param("parent") String parent);
}