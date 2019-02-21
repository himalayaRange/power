package com.bly.power.service.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.power.interfaces.entity.power.MenuColumnEntity;

/**
 * 
 * @Desc   : 菜单列mapper
 * @author : Administrator
 * @date   : 2017年8月25日
 */
public interface MenuColumnMapper extends BaseMapper {

	/**
	 * 根据菜单id删除菜单列
	 * @param menuId 菜单id
	 * @return
	 */
	public int deleteByMenuId(@Param("menuId")Long menuId);	
	
	/**
	 * 
	 * @param menuId
	 * @return
	 */
	public int deleteById(@Param("id")Long id);
	/**
	 * 启用冻结菜单功能
	 * @param id
	 * @param status
	 * @return
	 */
	public int enableOrFreeze(@Param("id")Long id, @Param("status")Long status);
	
	/**
	 * 根据菜单ID查询菜单列
	 * @param menuId
	 * @return
	 */
	public List<MenuColumnEntity> selectByMenuId(@Param("menuId")Long menuId);
	
}
