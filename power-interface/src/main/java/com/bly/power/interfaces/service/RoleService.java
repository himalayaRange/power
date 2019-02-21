package com.bly.power.interfaces.service;

import java.util.List;
import java.util.Map;
import com.bly.common.interfaces.service.BaseService;
import com.bly.power.interfaces.entity.power.RoleEntity;
import com.bly.power.interfaces.entity.power.vo.SystemMenuVo;

/**
 * 
 * @Desc   : 角色管理
 * @author : Administrator
 * @date   : 2017年8月23日
 */
public interface RoleService extends BaseService {

	/**
	 * 删除冻结角色信息
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	
	/**
	 * 启用、冻结角色
	 */
	public int enableOrFreeze(Long id,Long status);
	
	/**
	 * 获取所有的资源信息
	 * @return
	 */
	public List<Map<String,Object>> initResourcesMaps();
	
	/**
	 * 插入角色并维护系统菜单以及菜单功能列
	 * @throws Exception 
	 */
	public void insertRoleInfo(RoleEntity role , List<SystemMenuVo> smc) throws Exception;
	
	/**
	 * 更新角色并更新系统菜单以及菜单功能列
	 * @param role
	 * @param smc
	 * @throws Exception
	 */
	public void updateRoleInfo(RoleEntity role , List<SystemMenuVo> smc , boolean flag) throws Exception;
	
	/**
	 * 初始化所有的角色列表
	 * @return
	 */
	public List<Map<String,Object>> initTotalRoles();
	
}
