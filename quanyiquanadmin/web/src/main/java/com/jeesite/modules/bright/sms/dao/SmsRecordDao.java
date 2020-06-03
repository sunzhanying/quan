/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sms.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.sms.entity.SmsRecord;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * sms_recordDAO接口
 * @author 马晓亮
 * @version 2019-07-25
 */
@MyBatisDao
public interface SmsRecordDao extends CrudDao<SmsRecord> {

    //旧验证码作废
    @Update("UPDATE sms_record SET zt = 2 WHERE phone = #{0} and zt = 0")
    long cancelOldVerifyCode(String phone);

    @Select("select * from sms_record where zt = #{zt} and phone = #{phone}")
    SmsRecord getSmsRecord(SmsRecord smsRecord);
}