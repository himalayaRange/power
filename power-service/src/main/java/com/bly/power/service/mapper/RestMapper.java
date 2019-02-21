package com.bly.power.service.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.bly.common.service.mapper.BaseMapper;

public interface RestMapper extends BaseMapper {
	
	/**
	 * 根据条件查询菜单想吗
	 * @param paramter
	 * @return
	 */
	public List<Serializable> getRoleMenuPermissions(Map<String,Object> paramter);
	
	/**
	 * 查询所有系统角色
	 * @return
	 */
	public List<Map<String,Object>> queryTotalSysRole();
	
}
