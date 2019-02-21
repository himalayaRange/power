package com.bly.power.service.mapper;

import org.apache.ibatis.annotations.Param;

import com.bly.common.service.mapper.BaseMapper;
import com.bly.power.interfaces.entity.system.BrandEntity;

/**
 * 
 * @Desc   : 品牌管理service
 * @author : Administrator
 * @date   : 2017年8月22日
 */
public interface BrandMapper extends BaseMapper{
	/**
	 * 删除冻结的品牌
	 */
	public int deleteById(@Param("id")Long id);
	
	/**
	 * 启用、冻结品牌
	 */
	public int enableOrFreeze(@Param("id")Long id,@Param("status")Long status);
	
	/**
	 * 根据ID查询品牌信息
	 * @param id
	 * @return
	 */
	public BrandEntity selectById(@Param("id")Long id);
}
