/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.ctyd.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.ctyd.entity.Ctyd;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 餐厅预订表DAO接口
 * @author 马晓亮
 * @version 2019-07-24
 */
@MyBatisDao
public interface CtydDao extends CrudDao<Ctyd> {

    //返回餐厅预订列表
    @Select("<script>" +
            "select a.*,b.wxnc as 'khXx.wxnc',b.wxtx as 'khXx.wxtx' from t_ctyd a left join t_kh_xx b on a.khid = b.id " +
            " where a.id is not null " +
            "<if test='zt != null and zt != &quot;&quot;'>" +
            " and zt = #{zt} " +
            "</if>" +
            "<if test='gjz != null and gjz != &quot;&quot;'>" +
            " and (rs = #{gjz} or yddf like concat('%',#{gjz},'%') or name like concat('%',#{gjz},'%') " +
            " or phone like concat('%',#{gjz},'%'))" +
            "</if>" +
            " order by a.create_date desc " +
            "</script>")
    List<Ctyd> queryCtydList(Ctyd ctyd);

    //修改订单状态
    @Update("update t_ctyd set zt = #{zt} where id = #{id}")
    int updateZt(@Param("id") String id, @Param("zt") int zt);

}