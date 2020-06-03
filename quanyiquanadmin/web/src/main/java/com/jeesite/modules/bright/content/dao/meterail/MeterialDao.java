/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.dao.meterail;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.content.entity.meterail.Meterial;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 素材表DAO接口
 * @author liqingfeng
 * @version 2019-07-08
 */
@MyBatisDao
public interface MeterialDao extends CrudDao<Meterial> {
    //显示商品列表

    List<Meterial> findAllList(Map<String,String> conditions);

    //显示商品列表

    @Select("select * from meterial where status != \"1\" and material_status = \"1\" and FIND_IN_SET(#{tag},tags_id)")
    List<Meterial> findByTag(String tag);
    /**
     * 根据属性推荐
     * @param meterial
     * @return
     */
    @Select("select *from meterial where id != #{id} and attribute_name = #{attributeName} and `status` =0")
    List<Meterial> getRecommendByAttribute(Meterial meterial);

    /**
     * 获取所有属性
     * @return
     */
    @Select("select DISTINCT(attribute_name) from meterial where attribute_name != '视频'")
    List<String> getMeterialAttrAll();
    /**
     * 根据热度推荐
     * @return
     */
    @Select("SELECT *from meterial where id in (SELECT meterial_id from meterial_like GROUP BY meterial_id ORDER BY COUNT(*))")
    List<Meterial> getRecommendByLike();

}