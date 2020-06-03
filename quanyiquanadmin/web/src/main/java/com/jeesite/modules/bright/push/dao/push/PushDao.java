/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.push.dao.push;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.push.entity.push.Push;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * t_pushDAO接口
 * @author 李金辉
 * @version 2019-07-18
 */
@MyBatisDao
public interface PushDao extends CrudDao<Push> {

    List<Push> push(Push push);

    //推送渠道推送数记录
    @Select("SELECT COUNT(id) FROM t_push where tsqd = #{0}")
    int queryQdTsjls(String tsqd);

    //已推送
    @Select("SELECT COUNT(id) FROM t_push WHERE jhsj < NOW()")
    int ytsl();

    //待推送
    @Select("SELECT COUNT(id) FROM t_push WHERE jhsj > NOW()")
    int dtsl();

    //成功
    @Select("SELECT COUNT(id) FROM t_push WHERE statu = 1")
    int success();

    //失败
    @Select("SELECT COUNT(id) FROM t_push WHERE statu = 2")
    int error();
}