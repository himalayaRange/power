package com.bly.power.service.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.power.interfaces.entity.system.WarehouseEntity;

/**
 * 
 * @Desc   : 仓库管理mapper
 * @author : Administrator
 * @date   : 2017年8月23日
 */
public interface WarehouseMapper extends BaseMapper {
	/**
	 * 删除冻结的仓库
	 */
	public int deleteById(@Param("id")Long id);
	
	/**
	 * 启用、冻结仓库
	 */
	public int enableOrFreeze(@Param("id")Long id,@Param("status")Long status);
	
	/**
	 * 根据ID查询仓库信息
	 * @param id
	 * @return
	 */
	public WarehouseEntity selectById(@Param("id")Long id);
	
	/**
	 * 初始化仓库树形结构
	 * @return
	 */
	public List<Map<String,Object>> selectWarehouseParentsInfo();

}
