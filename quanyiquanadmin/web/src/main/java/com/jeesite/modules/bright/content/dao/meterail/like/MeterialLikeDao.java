/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.dao.meterail.like;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.content.entity.meterail.like.MeterialLike;
import org.apache.ibatis.annotations.Select;

/**
 * 关注素材表DAO接口
 * @author liqingfeng
 * @version 2019-07-18
 */
@MyBatisDao
public interface MeterialLikeDao extends CrudDao<MeterialLike> {
    @Select("select *from meterial_like where kh_id = #{khId} and meterial_id = #{meterialId}")
    MeterialLike getMeterialLike(MeterialLike meterialLike);
}