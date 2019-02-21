package com.bly.power.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bly.common.interfaces.entity.BaseEntity;
import com.bly.common.service.mapper.BaseMapper;
import com.bly.common.service.serviceimpl.BaseServiceImpl;
import com.bly.power.interfaces.entity.power.RoleEntity;
import com.bly.power.interfaces.entity.power.RoleMenuColumnEntity;
import com.bly.power.interfaces.entity.power.RoleMenuEntity;
import com.bly.power.interfaces.entity.power.vo.ColumnVo;
import com.bly.power.interfaces.entity.power.vo.MenuColumnVo;
import com.bly.power.interfaces.entity.power.vo.SystemMenuVo;
import com.bly.power.interfaces.service.RoleService;
import com.bly.power.service.mapper.RoleMapper;

/**
 * 
 * @Desc   : 角色管理
 * @author : Administrator
 * @date   : 2017年8月23日
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	protected <T extends BaseEntity> BaseMapper getBaseMapper() {
		return roleMapper;
	}

	@Override
	public int deleteById(Long id) {
		return roleMapper.deleteById(id);
	}

	@Override
	public int enableOrFreeze(Long id, Long status) {
		return roleMapper.enableOrFreeze(id,status);
	}

	@Override
	public List<Map<String,Object>> initResourcesMaps() {
		return roleMapper.initResourcesMaps();
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertRoleInfo(RoleEntity role, List<SystemMenuVo> smc) throws Exception {
		try {
			//查询原角色信息
			System.out.println("------- START ---------");
			if(smc != null && smc.size() >0 ){
				System.out.println("----------size------ : " + smc.size());
				for(SystemMenuVo sv : smc){
					Long sysId = sv.getSysId();
					System.out.println("-------------sysId---- : " +sysId);
					role.setSysId(sysId);
					//角色主表2
					roleMapper.insert(role);
					Long roleId = role.getId();
					//角色菜单表
					for(MenuColumnVo mv : sv.getMenuIds()){
						Long menuId = mv.getMenuId();
						System.out.println("----------menuId----:" + menuId);
						List<ColumnVo> columnIds = mv.getColumnIds();
						//生成【角色-菜单】关联信息
						RoleMenuEntity rm = new RoleMenuEntity();
						rm.setRoleId(Long.valueOf(roleId));
						rm.setMenuId(menuId);
						roleMapper.insertRoleMenu(rm);
						Long roleMenuId = rm.getId();
						for(ColumnVo cv : columnIds){
							Long columnId = cv.getColumnId();
							System.out.println("-----------columnId---- : " + columnId);
							//生成 【菜单-菜单列】关联信息
							RoleMenuColumnEntity rmc = new RoleMenuColumnEntity();
							rmc.setRoleMenuId(roleMenuId);
							rmc.setColumnId(columnId);
							roleMapper.insertRoleMenuColumn(rmc);
						}
					}
				}
			}
			System.out.println("----------- END  --------------");
		} catch (Exception e) {
			logger.error("添加角色错误！", e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateRoleInfo(RoleEntity role, List<SystemMenuVo> smc , boolean flag) throws Exception {
		try {
			RoleEntity baseBean = roleMapper.find(role.getId());
			if(baseBean != null){
				role.setCreaterName(baseBean.getCreaterName());
				role.setCreateTime(baseBean.getCreateTime() == null ? new Date() : baseBean.getCreateTime());
			}
			if(flag){
				//删除该角色下面级联信息
				String roleCode = role.getRoleCode();  // roloCode[1] : id[n]
				if(StringUtils.isEmpty(roleCode)){
					logger.error("未获取到roleCode！");
					throw new Exception("未获取到roleCode！");
				}
				List<RoleEntity> list = roleMapper.selectSysRoleByRoleCode(roleCode);
				if(list == null || list.size() == 0){
					logger.error("错误的参数roleCode！");
					throw new Exception("错误的参数roleCode！");
				}
				for(RoleEntity se : list){
					Long id = se.getId();
					roleMapper.deleteRoleReferenceInfo(id);
					logger.info("Delete INFO : roleCode :" + roleCode + "  ,ID :" + id);
				}
			}
			//插入角色及级联信息
			if(smc != null && smc.size() >0 ){
				for(SystemMenuVo sv : smc){
					Long sysId = sv.getSysId();
					role.setSysId(sysId);
					//角色主表
					role.setId(null);
					roleMapper.insert(role);
					Long roleId = role.getId();
					//角色菜单表
					for(MenuColumnVo mv : sv.getMenuIds()){
						Long menuId = mv.getMenuId();
						List<ColumnVo> columnIds = mv.getColumnIds();
						//生成【角色-菜单】关联信息
						RoleMenuEntity rm = new RoleMenuEntity();
						rm.setRoleId(Long.valueOf(roleId));
						rm.setMenuId(menuId);
						roleMapper.insertRoleMenu(rm);
						Long roleMenuId = rm.getId();
						for(ColumnVo cv : columnIds){
							Long columnId = cv.getColumnId();
							//生成 【菜单-菜单列】关联信息
							RoleMenuColumnEntity rmc = new RoleMenuColumnEntity();
							rmc.setRoleMenuId(roleMenuId);
							rmc.setColumnId(columnId);
							roleMapper.insertRoleMenuColumn(rmc);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("修改角色错误！", e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> initTotalRoles() {
		return roleMapper.initTotalRoles();
	}

}
