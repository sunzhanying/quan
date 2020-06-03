/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.service.meterail.visited;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeesite.modules.bright.setfocus.service.source.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.content.entity.meterail.visited.MeterialVisitedLog;
import com.jeesite.modules.bright.content.dao.meterail.visited.MeterialVisitedLogDao;

/**
 * 访问日志表Service
 * @author liqingfeng
 * @version 2019-07-17
 */
@Service
@Transactional(readOnly=true)
public class MeterialVisitedLogService extends CrudService<MeterialVisitedLogDao, MeterialVisitedLog> {
	@Autowired
    private MeterialVisitedLogDao meterialVisitedLogDao;
	@Autowired
	private SourceService sourceService;

	/**
	 * 获取单条数据
	 * @param meterialVisitedLog
	 * @return
	 */
	@Override
	public MeterialVisitedLog get(MeterialVisitedLog meterialVisitedLog) {
		return super.get(meterialVisitedLog);
	}
	
	/**
	 * 查询分页数据
	 * @param meterialVisitedLog 查询条件
	 * @return
	 */
	@Override
	public Page<MeterialVisitedLog> findPage(MeterialVisitedLog meterialVisitedLog) {
		return super.findPage(meterialVisitedLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param meterialVisitedLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(MeterialVisitedLog meterialVisitedLog) {
		super.save(meterialVisitedLog);
	}
	
	/**
	 * 更新状态
	 * @param meterialVisitedLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(MeterialVisitedLog meterialVisitedLog) {
		super.updateStatus(meterialVisitedLog);
	}
	
	/**
	 * 删除数据
	 * @param meterialVisitedLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(MeterialVisitedLog meterialVisitedLog) {
		super.delete(meterialVisitedLog);
	}


	/**
	 * 获取echarts数据
	 *
	 */
	public Map<String,Object> getChartData(String day, String type, String startTime, String endTime) {
		Map<String, Object> map = new HashMap<>();
		startTime = startTime==null || startTime.length()<=0?"2018-02-09":startTime;
		endTime = endTime==null || endTime.length()<=0?"2018-02-09":endTime;
		List channel = meterialVisitedLogDao.getSource(day,startTime,endTime);
		map.put("channel",channel);                                         //"channel":["微信朋友圈","海报二维码","电子邮件","百度搜索"]}
		List data = meterialVisitedLogDao.getData(day,type,startTime,endTime);
		map.put("data",data);   //data":[{"name":"微信朋友圈","id":"1143741054283849728","value":33.0},{"name":"海报二维码","id":"1143740907919417344","value":54.0},{"name":"电子邮件","id":"1143740946205024256","value":44.0},{"name":"百度搜索","id":"1143740863736619008","value":21.0}]

		List date = meterialVisitedLogDao.getDate(day,type,startTime,endTime);
		map.put("date",date);   //"date":["2019-05-01","2019-06-01","2019-06-06","2019-07-01"]

		Map<String,Object> condition = new HashMap<>();
		condition.put("dateList",date.toArray(new String[date.size()]));
		condition.put("type",type);
		List data2 = new ArrayList();
		for (Object ob:channel
			 ) {
			HashMap<String,Object> data2Map = new HashMap<>();
			String sourceId = ((Map<String,Object>)ob).get("id").toString();//获取channel已经找到的sourceId
			condition.put("sourceId",sourceId);
			List<String> a = meterialVisitedLogDao.getEchartsData2(condition);
			data2Map.put("name",sourceService.get(sourceId).getName());
			data2Map.put("data",a);
			data2.add(data2Map);
		}
		map.put("data2",data2);    //"data2":[{"sourceId":"1143740863736619008","data":["0","0","0","21"],"name":"百度搜索"},{"sourceId":"1143740907919417344","data":["0","32","0","22"],"name":"海报二维码"},{"sourceId":"1143740946205024256","data":["44","0","0","0"],"name":"电子邮件"},{"sourceId":"1143741054283849728","data":["0","0","33","0"],"name":"微信朋友圈"}],"channel":["微信朋友圈","海报二维码","电子邮件","百度搜索"]}
		ObjectMapper json = new ObjectMapper();
		String writeValueAsString = "";
		try {
			writeValueAsString = json.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(writeValueAsString);
		return map;
	}


	public HashMap getSum(){
        return meterialVisitedLogDao.getSum();
    }
}