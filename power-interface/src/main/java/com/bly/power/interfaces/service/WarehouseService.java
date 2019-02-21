package com.bly.power.interfaces.service;

import java.util.List;
import java.util.Map;

import com.bly.common.interfaces.service.BaseService;
import com.bly.power.interfaces.entity.system.WarehouseEntity;

/**
 * 
 * @Desc   : 仓库
 * @author : Administrator
 * @date   : 2017年8月23日
 */
public interface WarehouseService extends BaseService {
	/**
	 * 删除冻结仓库信息
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 启用、冻结仓库
	 */
	public int enableOrFreeze(Long id,Long status);
	
	/**
	 * 根据ID查询仓库信息
	 * @param id
	 * @return
	 */
	public WarehouseEntity selectById(Long id);
	
	/**
	 * 初始化仓库树形结构
	 * @return
	 */
	public List<Map<String,Object>> selectWarehouseParentsInfo();
}
