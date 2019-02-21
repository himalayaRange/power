package com.bly.power.service.mapper;

import org.apache.ibatis.annotations.Param;

import com.bly.common.service.mapper.BaseMapper;

/**
 * 
 * @Desc   : 系统管理
 * @author : Administrator
 * @date   : 2017年8月24日
 */
public interface SystemMapper extends BaseMapper {
	
	/**
	 * 根据ID删除平台系统
	 * @param id
	 * @return
	 */
	public int deleteById(@Param("id")Long id);
	
	/**
	 * 启用冻结平台系统
	 * @param id
	 * @param status
	 * @return
	 */
	public int enableOrFreeze(@Param("id")Long id, @Param("status")Long status);
	
}
