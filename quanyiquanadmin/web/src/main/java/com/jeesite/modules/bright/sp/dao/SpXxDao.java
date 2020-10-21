/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.sp.entity.SpXx;
import com.jeesite.modules.qyhsmx.entity.QyhsMx;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 商品信息DAO接口
 * @author 马晓亮
 * @version 2019-06-25
 */
@MyBatisDao
public interface SpXxDao extends CrudDao<SpXx> {

    //查询可以积分兑换的商品
    @Select("SELECT id,img,spmc,spfmc,use_jf from t_sp_xx where `status`=0 and use_jf IS NOT NULL and use_jf !='' and use_jf !=0 and kc >0")
    List<SpXx> findJfDhSp();

    //根据商品id查询库存
    @Select("select kc from t_sp_xx where id = #{0}")
    Long getKc(String id);

    //修改库存
    @Update("update t_sp_xx set kc = #{kc} where id = #{id}")
    int updateKc(@Param("kc") Long kc, @Param("id") String id);

    //显示商品列表
    @Select("<script>" +
            "select id,spmc,spfmc,spjg,img,img_BIG from t_sp_xx " +
            "where status = 0 " +
            "<if test='splx != null and splx != &quot;&quot;'>" +
            " AND splx = #{splx} " +
            "</if>" +
            "<if test='name != null and name != &quot;&quot;'>" +
            " AND (spmc like concat('%',#{name},'%') OR spfmc like concat('%',#{name},'%')) " +
            "</if>" +
            " order by create_date desc " +
            "</script>")
    List<SpXx> findAllList(@Param("splx") String typeid, @Param("name") String name);

    //根据商品类型id更新商品的类型
    @Update("update t_sp_xx set splx = '0' where splx = #{0}")
    int updateBySpTypeId(String typeid);

    //显示商品列表
    @Select("<script>" +
            "select id, qyhs_id, qyq_id, khid, type, kh, km, img, yxq_date, zt, status, create_by, create_date, " +
            "    update_by, update_date, remarks, order_id, jszt, sy, sqdh from a_qyhs_mx " +
            "where " +
            "<if test='str != null and str != &quot;&quot;'>" +
            " id in ( #{str} )" +
            "</if>" +
            "</script>")
    List<QyhsMx> findListForDownload(@Param("str") String str);

}