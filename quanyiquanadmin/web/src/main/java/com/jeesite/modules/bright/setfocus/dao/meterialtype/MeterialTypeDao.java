/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.dao.meterialtype;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.setfocus.entity.meterialtype.MeterialType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 产品类型DAO接口
 * @author liqingfeng
 * @version 2019-07-05
 */
@MyBatisDao
public interface MeterialTypeDao extends TreeDao<MeterialType> {
    //查出所有子类
    @Select("select *from meterial_type where code = #{code} or parent_code = #{code}")
	public List<MeterialType> findAllByCode(String code);

    //通过父节点查出最大的一个子节点
    @Select("select *from meterial_type where parent_code = #{code} ORDER BY `code` DESC LIMIT 1")
    List<MeterialType> getLastByParentCode(String code);
}