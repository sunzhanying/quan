/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.content.service.meterail;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.content.dao.meterail.MeterialDao;
import com.jeesite.modules.bright.content.dao.meterail.MeterialDetailDao;
import com.jeesite.modules.bright.content.dao.meterail.visited.MeterialVisitedLogDao;
import com.jeesite.modules.bright.content.entity.meterail.Meterial;
import com.jeesite.modules.bright.content.entity.meterail.MeterialDetail;
import com.jeesite.modules.file.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 素材表Service
 * @author liqingfeng
 * @version 2019-07-08
 */
@Service
@Transactional(readOnly=true)
public class MeterialService extends CrudService<MeterialDao, Meterial> {
	
	@Autowired
	private MeterialDetailDao meterialDetailDao;

	@Autowired
	private MeterialVisitedLogDao meterialVisitedLogDao;

	public List<String> getMeterialAttrAll(){
		return dao.getMeterialAttrAll();
	};

	/**
	 * 同属性推荐
	 * @param meterial
	 * @return
	 */
	public  List<Meterial> getRecommendByAttribute(Meterial meterial){
		return dao.getRecommendByAttribute(meterial);
	}
	public  List<Meterial> getRecommendByLike(){
		return dao.getRecommendByLike();
	}

	/**
	 * 查询素材列表
	 * @param page
	 * @param size
	 * @return
	 */
	public PageInfo<Meterial> findAllList(Integer page, Integer size, String typeid, String attributeName,String sort,String name){
		PageHelper.startPage(page,size);
		Map<String,String> conditionMap = new HashMap<>();
		conditionMap.put("typeid",typeid);
		conditionMap.put("attributeName",attributeName);
		conditionMap.put("sort",sort);
		conditionMap.put("name",name);
		List<Meterial> list = dao.findAllList(conditionMap);
		return new PageInfo<>(list);
	}
	/**
	 * 通过标签查询
	 * @return
	 */
	public List<Meterial> findByTag(String tag){
		return dao.findByTag(tag);
	}

	/**
	 * 获取单条数据
	 * @param meterial
	 * @return
	 */
	@Override
	public Meterial get(Meterial meterial) {
		Meterial entity = super.get(meterial);
		if (entity != null){
			MeterialDetail meterialDetail = new MeterialDetail(entity);
			meterialDetail.setStatus(MeterialDetail.STATUS_NORMAL);
			entity.setMeterialDetailList(meterialDetailDao.findList(meterialDetail));
			entity.setPersonTime(meterialVisitedLogDao.getPersonTime(entity.getId()));
			entity.setVisitor(meterialVisitedLogDao.getVisitor(entity.getId()));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param meterial 查询条件
	 * @return
	 */
	@Override
	public Page<Meterial> findPage(Meterial meterial) {
		Page<Meterial> pages = super.findPage(meterial);
		return pages;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param meterial
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Meterial meterial) {
		super.save(meterial);
		FileUploadUtils.saveFileUpload(meterial.getId(), "meterial_image");
		FileUploadUtils.saveFileUpload(meterial.getId(), "meterial_images");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(meterial.getId(), "meterial_file");
		//删除之前的子表
		meterialDetailDao.deleteByMeterialId(meterial.getId());
		// 保存 Meterial子表
		for (MeterialDetail meterialDetail : meterial.getMeterialDetailList()){

			if (!MeterialDetail.STATUS_DELETE.equals(meterialDetail.getStatus())){
				meterialDetail.setMeterialId(meterial);
				if (meterialDetail.getIsNewRecord()){
					meterialDetailDao.insert(meterialDetail);
				}else{
					meterialDetailDao.update(meterialDetail);
				}
			}else{
				meterialDetailDao.delete(meterialDetail);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param meterial
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Meterial meterial) {
		super.updateStatus(meterial);
	}
	
	/**
	 * 删除数据
	 * @param meterial
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Meterial meterial) {
		super.delete(meterial);
		MeterialDetail meterialDetail = new MeterialDetail();
		meterialDetail.setMeterialId(meterial);
		meterialDetailDao.deleteByEntity(meterialDetail);
	}
	
}