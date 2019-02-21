package com.bly.power.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.service.serviceimpl.BaseServiceImpl;
import com.bly.power.interfaces.entity.system.WarehouseEntity;
import com.bly.power.interfaces.service.WarehouseService;
import com.bly.power.service.mapper.WarehouseMapper;

/**
 * 
 * @Desc   : 仓库管理实现类
 * @author : Administrator
 * @date   : 2017年8月23日
 */
@Service("warehouseService")
public class WarehouseServiceImpl extends BaseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseMapper warehouseMapper;
	
	@Override
	protected <T extends BaseEntity> BaseMapper getBaseMapper() {
		return warehouseMapper;
	}

	/**
	 * 删除冻结仓库信息
	 */
	@Override
	public int deleteById(Long id) {
		return warehouseMapper.deleteById(id);
	}

	/**
	 * 启用、冻结仓库
	 */
	@Override
	public int enableOrFreeze(Long id, Long status) {
		return warehouseMapper.enableOrFreeze(id,status);
	}

	@Override
	public WarehouseEntity selectById(Long id) {
		return warehouseMapper.selectById(id);
	}

	@Override
	public List<Map<String, Object>> selectWarehouseParentsInfo() {
		return warehouseMapper.selectWarehouseParentsInfo();
	}
}
