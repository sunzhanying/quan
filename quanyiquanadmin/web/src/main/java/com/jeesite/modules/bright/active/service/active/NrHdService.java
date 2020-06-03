/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.active.service.active;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.active.entity.active.NrHd;
import com.jeesite.modules.bright.active.dao.active.NrHdDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 活动Service
 * @author 李金辉
 * @version 2019-08-02
 */
@Service
@Transactional(readOnly=true)
public class NrHdService extends CrudService<NrHdDao, NrHd> {
	
	/**
	 * 获取单条数据
	 * @param nrHd
	 * @return
	 */
	@Override
	public NrHd get(NrHd nrHd) {
		return super.get(nrHd);
	}
	
	/**
	 * 查询分页数据
	 * @param nrHd 查询条件
	 * @param nrHd.page 分页对象
	 * @return
	 */
	@Override
	public Page<NrHd> findPage(NrHd nrHd) {
		return super.findPage(nrHd);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param nrHd
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(NrHd nrHd) {
		super.save(nrHd);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(nrHd.getId(), "nrHd_image");
	}
	
	/**
	 * 更新状态
	 * @param nrHd
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(NrHd nrHd) {
		super.updateStatus(nrHd);
	}
	
	/**
	 * 删除数据
	 * @param nrHd
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(NrHd nrHd) {
		super.delete(nrHd);
	}
	
}