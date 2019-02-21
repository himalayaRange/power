package com.bly.power.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.service.serviceimpl.BaseServiceImpl;
import com.bly.power.interfaces.entity.system.DeptEntity;
import com.bly.power.interfaces.service.DeptService;
import com.bly.power.service.mapper.DeptMapper;
/**
 * 
 * @Desc : 部门管理
 * @author : Administrator
 * @date : 2017年8月21日
 */
@Service("deptService")
public class DeptServiceImpl extends BaseServiceImpl implements DeptService {

	@Autowired
	private DeptMapper deptMapper;

	@Override
	protected <T extends BaseEntity> BaseMapper getBaseMapper() {
		return deptMapper;
	}

	/**
	 * 删除冻结部门信息
	 */
	@Override
	public int deleteById(Long id) {
		return deptMapper.deleteById(id);
	}

	/**
	 * 启用、冻结部门
	 */
	@Override
	public int enableOrFreeze(Long id, Long status) {
		return deptMapper.enableOrFreeze(id,status);
	}

	@Override
	public DeptEntity selectById(Long id) {
		
		return deptMapper.selectById(id);
	}
	
	@Override
	public List<Map<String,Object>> selectDeptByCompanyId(Long comId){
		
		return deptMapper.selectDeptByCompanyId(comId);
	}

	@Override
	public List<Map<String, Object>> selectSubordinateDeptById(Long id) {
		return deptMapper.selectSubordinateDeptById(id);
	}
}
