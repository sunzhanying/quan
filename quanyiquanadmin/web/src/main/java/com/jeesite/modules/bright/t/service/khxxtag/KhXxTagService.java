/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.service.khxxtag;

import com.jeesite.modules.bright.setfocus.entity.tag.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.t.entity.khxxtag.KhXxTag;
import com.jeesite.modules.bright.t.dao.khxxtag.KhXxTagDao;

import java.util.List;

/**
 * 客户阅读标签Service
 * @author 李金辉
 * @version 2019-07-15
 */
@Service
@Transactional(readOnly=true)
public class KhXxTagService extends CrudService<KhXxTagDao, KhXxTag> {
	
	/**
	 * 获取单条数据
	 * @param khXxTag
	 * @return
	 */
	@Override
	public KhXxTag get(KhXxTag khXxTag) {
		return super.get(khXxTag);
	}
	
	/**
	 * 查询分页数据
	 * @param khXxTag 查询条件

	 * @return
	 */
	@Override
	public Page<KhXxTag> findPage(KhXxTag khXxTag) {
		return super.findPage(khXxTag);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param khXxTag
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(KhXxTag khXxTag) {
		super.save(khXxTag);
	}
	
	/**
	 * 更新状态
	 * @param khXxTag
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(KhXxTag khXxTag) {
		super.updateStatus(khXxTag);
	}
	
	/**
	 * 删除数据
	 * @param khXxTag
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(KhXxTag khXxTag) {
		super.delete(khXxTag);
	}

	/**
	 * 用户添加标签
	 */
	@Transactional(readOnly=false)
	public void addTags(String khId,List<Tag> tags) {
		KhXxTag khXxTag=new KhXxTag();
		khXxTag.setKhid(khId);
		for (Tag tag:tags) {
			khXxTag.setTagId(tag.getId());
			KhXxTag k =dao.getByEntity(khXxTag);
			if (k != null) {
				k.setTimes(k.getTimes()+1);
				dao.update(k);
			}else{
				k =new KhXxTag();
				k.setKhid(khId);
				k.setTagId(tag.getId());
				k.setTagName(tag.getName());
				k.setTimes(1);
				k.preInsert();
				dao.insert(k);
			}
		}
	}

}