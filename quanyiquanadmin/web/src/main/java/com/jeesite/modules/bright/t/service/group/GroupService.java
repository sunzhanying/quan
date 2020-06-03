/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.bright.t.service.group;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.bright.t.dao.group.GroupDao;
import com.jeesite.modules.bright.t.dao.group.GroupKhDao;
import com.jeesite.modules.bright.t.dao.khxx.KhXxDao;
import com.jeesite.modules.bright.t.entity.group.Group;
import com.jeesite.modules.bright.t.entity.group.GroupKh;
import com.jeesite.modules.bright.t.entity.khxx.KhXx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分组类型表Service
 * @author 李金辉
 * @version 2019-06-24
 */
@Service
@Transactional(readOnly=true)
public class GroupService extends CrudService<GroupDao, Group> {
	
	@Autowired
	private GroupKhDao groupKhDao;
	@Autowired
	private KhXxDao khXxDao;

	/**
	 * 获取单条数据
	 * @param group
	 * @return
	 */
	@Override
	public Group get(Group group) {
		Group entity = super.get(group);
		if (entity != null){
			GroupKh groupKh = new GroupKh(entity);
			groupKh.setStatus(GroupKh.STATUS_NORMAL);
			entity.setGroupKhList(groupKhDao.findList(groupKh));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param group 查询条件
	 * @return
	 */
	@Override
	public Page<Group> findPage(Group group) {
		return super.findPage(group);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param group
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Group group) {
		super.save(group);
		// 保存 Group子表
		GroupKh g=new GroupKh();
		g.setGroupId(group);

		groupKhDao.phyDeleteByEntity(g);
		group.setGroupKhList(group.getKhList());
		if (group.getGroupKhList()!= null) {
			for (GroupKh kh : group.getGroupKhList()){
				if(!"1".equals(kh.getStatus())){
					kh.setGroupId(group);
					kh.setSqls(group.getSqls());
					groupKhDao.insert(kh);
				}
			}
		}
		/*for (GroupKh groupKh : group.getGroupKhList()){
			if (!GroupKh.STATUS_DELETE.equals(groupKh.getStatus())){
				groupKh.setGroupId(group);
				if (groupKh.getIsNewRecord()){
					groupKhDao.insert(groupKh);
				}else{
					groupKhDao.update(groupKh);
				}
			}else{
				groupKhDao.delete(groupKh);
			}
		}*/
	}
	
	/**
	 * 更新状态
	 * @param group
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Group group) {
		super.updateStatus(group);
	}
	
	/**
	 * 删除数据
	 * @param group
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Group group) {
		super.delete(group);
		GroupKh groupKh = new GroupKh();
		groupKh.setGroupId(group);
		groupKhDao.deleteByEntity(groupKh);
	}



    public List<Group> selectGroupByKhXx(Group group,KhXx khXx) {
		return dao.selectGroupByKhXx(group,khXx);
    }
}