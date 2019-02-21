package com.bly.power.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.service.serviceimpl.BaseServiceImpl;
import com.bly.power.interfaces.service.MenuService;
import com.bly.power.service.mapper.MenuMapper;

/**
 * 
 * @Desc   : 菜单管理
 * @author : Administrator
 * @date   : 2017年8月24日
 */
@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	protected <T extends BaseEntity> BaseMapper getBaseMapper() {
		return menuMapper;
	}

	/**
	 * 删除冻结菜单信息
	 */
	@Override
	public int deleteById(Long id) {
		return menuMapper.deleteById(id);
	}

	/**
	 * 启用、冻结菜单
	 */
	@Override
	public int enableOrFreeze(Long id, Long status) {
		return menuMapper.enableOrFreeze(id,status);
	}

}
