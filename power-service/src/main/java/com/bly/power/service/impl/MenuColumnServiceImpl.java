package com.bly.power.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.service.serviceimpl.BaseServiceImpl;
import com.bly.power.interfaces.entity.power.MenuColumnEntity;
import com.bly.power.interfaces.service.MenuColumnService;
import com.bly.power.service.mapper.MenuColumnMapper;

/**
 * 
 * @Desc : 菜单列实现类
 * @author : Administrator
 * @date : 2017年8月25日
 */
@Service("menuColumnService")
public class MenuColumnServiceImpl extends BaseServiceImpl implements MenuColumnService {

	@Autowired
	private MenuColumnMapper menuColumnMapper;

	@Override
	protected <T extends BaseEntity> BaseMapper getBaseMapper() {
		return menuColumnMapper;
	}

	/**
	 * 根据菜单id删除菜单列
	 */
	@Override
	public int deleteByMenuId(Long menuId) {
		return menuColumnMapper.deleteByMenuId(menuId);
	}

	@Override
	public int deleteById(Long id) {
		return menuColumnMapper.deleteById(id);
	}

	@Override
	public int enableOrFreeze(Long id, Long status) {
		return menuColumnMapper.enableOrFreeze(id, status);
	}

	@Override
	public List<MenuColumnEntity> selectByMenuId(Long menuId) {
		return menuColumnMapper.selectByMenuId(menuId);
	}

}
