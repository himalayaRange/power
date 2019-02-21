package com.bly.power.interfaces.service;

import java.util.List;
import com.bly.common.interfaces.service.BaseService;
import com.bly.power.interfaces.entity.power.MenuColumnEntity;

/**
 * 
 * @Desc   : 菜单列service
 * @author : Administrator
 * @date   : 2017年8月25日
 */
public interface MenuColumnService extends BaseService {

	/**
	 * 根据menuId删除菜单列
	 */
	public int deleteByMenuId(Long menuId);
	
	/**
	 * 根据菜单功能ID删除菜单列
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 启用冻结菜单功能
	 * @param id
	 * @param status
	 * @return
	 */
	public int enableOrFreeze(Long id, Long status);
	
	/**
	 * 根据菜单ID查询菜单功能列表
	 * @param menuId
	 * @return
	 */
	public List<MenuColumnEntity> selectByMenuId(Long menuId);
	
}
