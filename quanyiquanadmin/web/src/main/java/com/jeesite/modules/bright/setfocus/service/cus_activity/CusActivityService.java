/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.setfocus.service.cus_activity;

import java.util.List;

import com.jeesite.common.service.ServiceException;
import com.jeesite.common.utils.excel.ExcelImport;
import com.jeesite.common.validator.ValidatorUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.setfocus.entity.cus_activity.CusActivity;
import com.jeesite.modules.bright.setfocus.dao.cus_activity.CusActivityDao;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;

/**
 * 客户活跃度事件Service
 * @author liqingfeng
 * @version 2019-07-22
 */
@Service
@Transactional(readOnly=true)
public class CusActivityService extends CrudService<CusActivityDao, CusActivity> {
	
	/**
	 * 获取单条数据
	 * @param cusActivity
	 * @return
	 */
	@Override
	public CusActivity get(CusActivity cusActivity) {
		return super.get(cusActivity);
	}
	
	/**
	 * 查询分页数据
	 * @param cusActivity 查询条件
	 * @param cusActivity.page 分页对象
	 * @return
	 */
	@Override
	public Page<CusActivity> findPage(CusActivity cusActivity) {
		return super.findPage(cusActivity);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param cusActivity
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(CusActivity cusActivity) {
		super.save(cusActivity);
	}
	
	/**
	 * 更新状态
	 * @param cusActivity
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(CusActivity cusActivity) {
		super.updateStatus(cusActivity);
	}
	
	/**
	 * 删除数据
	 * @param cusActivity
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(CusActivity cusActivity) {
		super.delete(cusActivity);
	}



	public String importData(MultipartFile file, Boolean isUpdateSupport){
		if (file == null){
			throw new ServiceException("请选择导入的数据文件！");
		}
		int successNum = 0; int failureNum = 0;
		StringBuilder successMsg = new StringBuilder();
		StringBuilder failureMsg = new StringBuilder();
		try(ExcelImport ei = new ExcelImport(file, 2, 0)){
			List<CusActivity> list = ei.getDataList(CusActivity.class);
			for (CusActivity cusActivity : list) {
				try{
					// 验证数据文件
					ValidatorUtils.validateWithException(cusActivity);
					// 验证是否存在这个用户
					CusActivity cus = dao.get(cusActivity);
					if (cus == null){
						this.save(cusActivity);
						successNum++;
						successMsg.append("<br/>" + successNum + "、事件名称 " +cusActivity.getEvent() + " 导入成功");
					} else if (isUpdateSupport){
						cusActivity.setId(cus.getId());
						this.save(cusActivity);
						successNum++;
						successMsg.append("<br/>" + successNum + "、事件名称 " + cusActivity.getEvent() + " 更新成功");
					} else {
						failureNum++;
						failureMsg.append("<br/>" + failureNum + "、事件名称 " + cusActivity.getEvent() + " 已存在");
					}
				} catch (Exception e) {
					failureNum++;
					String msg = "<br/>" + failureNum + "、账号 " + cusActivity.getEvent() + " 导入失败：";
					if (e instanceof ConstraintViolationException){
						List<String> messageList = ValidatorUtils.extractPropertyAndMessageAsList((ConstraintViolationException)e, ": ");
						for (String message : messageList) {
							msg += message + "; ";
						}
					}else{
						msg += e.getMessage();
					}
					failureMsg.append(msg);
					logger.error(msg, e);
				}
			}
		} catch (Exception e) {
			failureMsg.append(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		if (failureNum > 0) {
			failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
			throw new ServiceException(failureMsg.toString());
		}else{
			successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
		}
		return successMsg.toString();
	};
}