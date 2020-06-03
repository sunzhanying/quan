/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.dao.meterail;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.content.entity.meterail.MeterialDetail;
import org.apache.ibatis.annotations.Delete;

/**
 * 素材表DAO接口
 * @author liqingfeng
 * @version 2019-07-08
 */
@MyBatisDao
public interface MeterialDetailDao extends CrudDao<MeterialDetail> {
	@Delete("delete from meterial_detail where meterial_id = #{0}")
    void deleteByMeterialId(String id);
}