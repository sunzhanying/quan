/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.dao.others.salesroom;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.setfocus.entity.others.salesroom.Salesroom;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 门店设置/传播渠道设置DAO接口
 * @author liqingfeng
 * @version 2019-08-23
 */
@MyBatisDao
public interface SalesroomDao extends CrudDao<Salesroom> {
	@Update("UPDATE salesroom set isdefault = "+Salesroom.ISDEFAULT_NO+" where `status` != 1")
     void updateDefaultAll();

    @Select("select *from salesroom where isdefault ="+Salesroom.ISDEFAULT_YES)
    Salesroom getDefaultRoom();
}