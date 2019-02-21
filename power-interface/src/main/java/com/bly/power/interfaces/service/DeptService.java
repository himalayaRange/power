package com.bly.power.interfaces.service;

import java.util.List;
import java.util.Map;

import com.bly.common.interfaces.service.BaseService;
import com.bly.power.interfaces.entity.system.DeptEntity;

/**
 * 
 * @Desc   : 部门管理service
 * @author : Administrator
 * @date   : 2017年8月21日
 */
public interface DeptService extends BaseService {
	/**
	 * 删除冻结部门信息
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 启用、冻结部门
	 */
	public int enableOrFreeze(Long id,Long status);
	
	/**
	 * 根据ID查询部门
	 * @param id
	 * @return
	 */
	public DeptEntity selectById(Long id);
	
	/**
	 * 根据公司ID查询公司下所有的部门
	 * @param comId
	 * @return
	 */
	public List<Map<String,Object>> selectDeptByCompanyId(Long comId);
	
	/**
	 * 根据ID查询部门的下级部门
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> selectSubordinateDeptById(Long id);
}
