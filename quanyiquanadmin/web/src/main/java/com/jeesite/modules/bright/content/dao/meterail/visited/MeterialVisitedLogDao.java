/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.dao.meterail.visited;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.bright.content.entity.meterail.visited.MeterialVisitedLog;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 访问日志表DAO接口
 * @author liqingfeng
 * @version 2019-07-17
 */
@MyBatisDao
public interface MeterialVisitedLogDao extends CrudDao<MeterialVisitedLog> {
    /**
     * 统计素材访问人次
     * @param meterial_id
     * @return
     */
    @Select("SELECT count(*) from meterial_visited_log where meterial_id=#{meterial_id}")
    String getPersonTime(String meterial_id);

    /**
     * 统计素材访问人数
     * @param meterial_id
     * @return
     */
    @Select("SELECT COUNT(DISTINCT kh_id) from meterial_visited_log where meterial_id=#{meterial_id}")
    String getVisitor(String meterial_id);

    //统计总访问人数 总访问人次
    @Select("SELECT COUNT(*) person_time,COUNT(DISTINCT kh_id) visitor from meterial_visited_log")
    HashMap getSum();


    //流量统计数据

    /**
     *查询来源
     * @param day
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select s.id,s.name from meterial_visited_log l LEFT JOIN source s on l.source_id = s.id \n" +
            "where CASE WHEN #{arg0}=0 THEN l.create_date BETWEEN #{arg1} AND #{arg2}\n" +
            "\tWHEN #{arg0}=1 THEN DATE_FORMAT(l.create_date,'%Y-%m-%d') = DATE_FORMAT( CURDATE( ) ,'%Y-%m-%d' )\n" +
            "\tELSE DATE_SUB(CURDATE(), INTERVAL #{arg0} DAY) <= DATE_FORMAT(l.create_date,'%Y-%m-%d')\n" +
            "\tEND GROUP BY l.source_id")
    List<HashMap<String,String>> getSource(@RequestParam String day, @RequestParam String startTime, @RequestParam String endTime);

    @MapKey("name")
    @Select("select s.`name`,(CASE WHEN #{arg1}='pv' then COUNT(*) ELSE COUNT(DISTINCT l.kh_id) END)as value from meterial_visited_log l LEFT JOIN source s on l.source_id = s.id \n" +
            "where CASE WHEN #{arg0}=0 THEN l.create_date BETWEEN #{arg2} AND #{arg3}\n" +
            "\tWHEN #{arg0}=1 THEN DATE_FORMAT(l.create_date,'%Y-%m-%d') = DATE_FORMAT( CURDATE( ) ,'%Y-%m-%d' )\n" +
            "\tELSE DATE_SUB(CURDATE(), INTERVAL #{arg0} DAY) <= DATE_FORMAT(l.create_date,'%Y-%m-%d')\n" +
            "\tEND GROUP BY l.source_id")
    List<HashMap<String,String>> getData(@RequestParam String day, @RequestParam String type, @RequestParam String startTime, @RequestParam String endTime);

    @Select("select DISTINCT(DATE_FORMAT(l.create_date,'%Y-%m-%d')) from meterial_visited_log l LEFT JOIN source s on l.source_id = s.id \n" +
            "where CASE WHEN #{arg0}=0 THEN l.create_date BETWEEN #{arg2} AND #{arg3}\n" +
            "\tWHEN #{arg0}=1 THEN DATE_FORMAT(l.create_date,'%Y-%m-%d') = DATE_FORMAT( CURDATE() ,'%Y-%m-%d' )\n" +
            "\tELSE DATE_SUB(CURDATE(), INTERVAL #{arg0} DAY) <= DATE_FORMAT(l.create_date,'%Y-%m-%d')\n" +
            "\tEND\n")
    List<String> getDate(@RequestParam("day") String day, @RequestParam("type") String type, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime);

    List<String> getEchartsData2(Map<String,Object> map);

}