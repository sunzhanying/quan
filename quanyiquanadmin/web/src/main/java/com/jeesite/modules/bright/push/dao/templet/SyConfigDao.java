/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.dao.templet;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.push.entity.templet.SyConfig;

/**
 * 生涯配置表，将模板配置给客户（学生）、群组。（配置二选一，即一般配置给群组，亦可配置给客户）流程：1、配置模板2、配置模板适用对象：群组、客户、学生3、生涯任务生成：读此表，结合生涯模板表。生成t_sy_rwmx，同是根据推送渠道生成等推送记录写入push表界面：生涯生成界面要定制，DAO接口
 * @author 李金辉
 * @version 2019-07-17
 */
@MyBatisDao
public interface SyConfigDao extends CrudDao<SyConfig> {

    SyConfig push(SyConfig syConfig);
}