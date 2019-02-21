package com.bly.power.service.mapper;

import org.apache.ibatis.annotations.Param;

import com.bly.common.service.mapper.BaseMapper;

/**
 * 	
 * @Desc   : 菜单管理mapper
 * @author : Administrator
 * @date   : 2017年8月24日
 */
public interface MenuMapper extends BaseMapper {
	/**
	 * 删除菜单的品牌
	 */
	public int deleteById(@Param("id")Long id);
	
	/**
	 * 启用、冻结菜单
	 */
	public int enableOrFreeze(@Param("id")Long id,@Param("status")Long status);
}
