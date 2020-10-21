/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sale.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sale.entity.IncomeConf;
import org.apache.ibatis.annotations.Select;

@MyBatisDao
public interface IncomeConfDao extends CrudDao<IncomeConf> {

    @Select("id,parent_level AS parentLevel,income_type AS incomeType,money,ratio FROM t_income_conf where parent_level = #{0} limit 1")
    IncomeConf getConf(String parentLevel);

}