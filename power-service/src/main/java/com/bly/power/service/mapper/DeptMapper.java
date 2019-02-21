package com.bly.power.service.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bly.common.service.mapper.BaseMapper;
import com.bly.power.interfaces.entity.system.DeptEntity;

/**
 * 
 * @Desc   : 部门管理mapper
 * @author : Administrator
 * @date   : 2017年8月21日
 */
public interface DeptMapper extends BaseMapper {
	/**
	 * 删除冻结的部门
	 */
	public int deleteById(@Param("id")Long id);
	
	/**
	 * 启用、冻结部门
	 */
	public int enableOrFreeze(@Param("id")Long id,@Param("status")Long status);
	
	/**
	 * 根据ID查询部门
	 * @param id
	 * @return
	 */
	public DeptEntity selectById(@Param("id")Long id);
	
	/**
	 * 根据公司ID查询公司下的部门
	 * @param comId
	 * @return
	 */
	public List<Map<String,Object>> selectDeptByCompanyId(@Param("comId") Long comId);
	
	/**
	 * 根据ID查询下级部门
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> selectSubordinateDeptById(@Param("id")Long id);
}
