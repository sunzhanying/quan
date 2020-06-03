/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.dao.tag;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.setfocus.entity.tag.Tag;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 标签DAO接口
 * @author liqingfeng
 * @version 2019-07-05
 */
@MyBatisDao
public interface TagDao extends CrudDao<Tag> {
    @Select("select coalesce(MAX(sort),0) from tags")
	Integer getLastBySort();
    @Select("select *from tags where sort = #{sort}")
    List<Tag> getListByTag(String sort);

    @Select("select * from tags where name = #{0}")
    Tag getTagByName(String name);
}