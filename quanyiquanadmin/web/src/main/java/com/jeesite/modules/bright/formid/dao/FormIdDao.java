/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.formid.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.formid.entity.FormId;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * a_form_idDAO接口
 * @author 李金辉
 * @version 2019-07-19
 */
@MyBatisDao
public interface FormIdDao extends CrudDao<FormId> {

    @Select("select * from a_form_id where open_id=#{0} and PERIOD_DIFF(date_format(now(),'%Y%m%d'),date_format(create_date,'%Y%m%d')) <=7 GROUP BY open_id,create_date ORDER BY create_date asc")
    List<FormId> selectByOpenId(String openId);
}