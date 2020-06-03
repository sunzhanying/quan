/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.sp.service.yhq;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import com.jeesite.API.service.Response;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.bright.khyhq.dao.KhYhqDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.sp.entity.yhq.SpYhq;
import com.jeesite.modules.bright.sp.dao.yhq.SpYhqDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 优惠券定义表Service
 * @author 马晓亮
 * @version 2019-06-25
 */
@Service
@Transactional(readOnly=true)
public class SpYhqService extends CrudService<SpYhqDao, SpYhq> {

	@Autowired
	private KhYhqDao khYhqDao;

	/**
	 * 弹出优惠券
	 * @param spYhq
	 * @param khid
	 * @return
	 */
	public Response findListByUserType(SpYhq spYhq, String khid){
		List<SpYhq> spYhqs = dao.findList(spYhq);
		Iterator<SpYhq> it = spYhqs.iterator();
		while (it.hasNext()){
			SpYhq spYhq1 = it.next();
			System.out.println("数量"+khYhqDao.countYhqByYhqId(khid, spYhq1.getId()));
			if (khYhqDao.countYhqByYhqId(khid, spYhq1.getId()) > 0){
				it.remove();
			}else {
				//日
				if (spYhq1.YHQ_SXLX_RSX.equals(spYhq1.getYhSxlx())){
					spYhq1.setYhStart(new Date());
					spYhq1.setYhEnd(DateUtils.parseDate(DateUtils.getDate("yyyy-MM-dd", Integer.parseInt(spYhq1.getYhRsx()), Calendar.DATE)));
				}
			}
		}
		return new Response(spYhqs);
	}
	
	/**
	 * 获取单条数据
	 * @param spYhq
	 * @return
	 */
	@Override
	public SpYhq get(SpYhq spYhq) {
		return super.get(spYhq);
	}
	
	/**
	 * 查询分页数据
	 * @param spYhq 查询条件
	 * @param spYhq.page 分页对象
	 * @return
	 */
	@Override
	public Page<SpYhq> findPage(SpYhq spYhq) {
		return super.findPage(spYhq);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param spYhq
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SpYhq spYhq) {
		super.save(spYhq);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(spYhq.getId(), "spYhq_image");
	}
	
	/**
	 * 更新状态
	 * @param spYhq
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SpYhq spYhq) {
		super.updateStatus(spYhq);
	}
	
	/**
	 * 删除数据
	 * @param spYhq
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SpYhq spYhq) {
		super.delete(spYhq);
	}
	
}