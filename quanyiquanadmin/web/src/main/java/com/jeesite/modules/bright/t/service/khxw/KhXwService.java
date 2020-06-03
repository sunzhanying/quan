/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.service.khxw;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.t.entity.khxw.KhXw;
import com.jeesite.modules.bright.t.dao.khxw.KhXwDao;

/**
 * 客户行为Service
 * @author 李金辉
 * @version 2019-06-26
 */
@Service
@Transactional(readOnly=true)
public class KhXwService extends CrudService<KhXwDao, KhXw> {
	
	/**
	 * 获取单条数据
	 * @param khXw
	 * @return
	 */
	@Override
	public KhXw get(KhXw khXw) {
		return super.get(khXw);
	}
	
	/**
	 * 查询分页数据
	 * @param khXw 查询条件
	 * @param khXw.page 分页对象
	 * @return
	 */
	@Override
	public Page<KhXw> findPage(KhXw khXw) {
		Page<KhXw> page=super.findPage(khXw);
		for (KhXw khXw1:page.getList()){
			String str = "";
			//修改
			if (khXw1.getXw()!=null&&!"".equals(khXw1.getXwms()) && khXw1.getXwms() != null &&
					!"".equals(khXw1.getXw().getMs()) && khXw1.getXw().getMs() != null){
				String[] str1 = khXw1.getXwms().split(",");
				String[] str2 = khXw1.getXw().getMs().split(",");
				for (int i=0;i<str1.length;i++){
					for (int j=0;j<str2.length;j++){
						if (i==j){
							str = str + "、" + str2[j] + str1[i] ;
						}
					}
				}
				khXw1.setXwms(str.substring(1));
			}
		}
		return page;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param khXw
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(KhXw khXw) {
		super.save(khXw);
	}
	
	/**
	 * 更新状态
	 * @param khXw
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(KhXw khXw) {
		super.updateStatus(khXw);
	}
	
	/**
	 * 删除数据
	 * @param khXw
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(KhXw khXw) {
		super.delete(khXw);
	}
	
}