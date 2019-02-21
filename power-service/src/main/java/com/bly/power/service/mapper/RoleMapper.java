package com.bly.power.service.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.power.interfaces.entity.power.RoleEntity;
import com.bly.power.interfaces.entity.power.RoleMenuColumnEntity;
import com.bly.power.interfaces.entity.power.RoleMenuEntity;

/**
 * 
 * @Desc   : 角色管理
 * @author : Administrator
 * @date   : 2017年8月23日
 */
public interface RoleMapper extends BaseMapper {
	/**
	 * 根据ID删除角色
	 * @param id
	 * @return
	 */
	public int deleteById(@Param("id")Long id);
	
	/**
	 * 启用冻结角色
	 * @param id
	 * @param status
	 * @return
	 */
	public int enableOrFreeze(@Param("id")Long id, @Param("status")Long status);
	
	/**
	 * 获取所有的资源信息
	 * @return
	 */
	public List<Map<String,Object>> initResourcesMaps();
	
	/**
	 * 插入菜单菜单列关联信息
	 * @param model
	 * @return
	 */
	public int insertRoleMenuColumn(RoleMenuColumnEntity model);
	
	/**
	 * 更新菜单菜单列关联信息
	 * @param model
	 * @return
	 */
	public int updateRoleMenuColumn(RoleMenuColumnEntity model);
	
	/**
	 * 插入角色菜单信息
	 * @param model
	 * @return
	 */
	public int insertRoleMenu(RoleMenuEntity model);
	
	/**
	 * 插入角色菜单信息
	 * @param model
	 * @return
	 */
	public int updateRoleMenu(RoleMenuEntity model);
	
	/**
	 * 删除角色的级联信息
	 * @param id
	 * @return
	 */
	public Integer deleteRoleReferenceInfo(@Param("id")Long id);
	
	/**
	 * 通过roleCode获取
	 * @param roleCode
	 * @return
	 */
	public List<RoleEntity> selectSysRoleByRoleCode(@Param("roleCode")String roleCode);
	
	/**
	 * 初始化所有的角色列表
	 * @return
	 */
	public List<Map<String,Object>> initTotalRoles();
}
