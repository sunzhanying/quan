/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.dao.group;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.t.entity.group.Group;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分组类型表DAO接口
 * @author 李金辉
 * @version 2019-06-24
 */
@MyBatisDao
public interface GroupDao extends CrudDao<Group> {

    //查询用户分组
    List<Group> selectGroupByKhXx(@Param("group") Group group, @Param("khXx") KhXx khXx);
}