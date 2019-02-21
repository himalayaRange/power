package com.bly.power.interfaces.entity.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuRoot implements Serializable{

	private static final long serialVersionUID = -3164134820244852125L;
	/** 角色信息 */
	private Long roleId;
	private String roleCode;
	private String roleName;
	private Integer roleStatus;
	private Integer roleIsDeleted;
	/** 角色系统信息 */
	private  Long sysId;
	private  String sysCode;
	private  String sysName;
	private  Integer systemStatus;
	private  Integer systemIsDeleted;
	/** 角色菜单信息 */
	private List<RoleMenuRoot> roleMenus = new ArrayList<RoleMenuRoot>();
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(Integer roleStatus) {
		this.roleStatus = roleStatus;
	}
	public Integer getRoleIsDeleted() {
		return roleIsDeleted;
	}
	public void setRoleIsDeleted(Integer roleIsDeleted) {
		this.roleIsDeleted = roleIsDeleted;
	}
	public Long getSysId() {
		return sysId;
	}
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public Integer getSystemStatus() {
		return systemStatus;
	}
	public void setSystemStatus(Integer systemStatus) {
		this.systemStatus = systemStatus;
	}
	public Integer getSystemIsDeleted() {
		return systemIsDeleted;
	}
	public void setSystemIsDeleted(Integer systemIsDeleted) {
		this.systemIsDeleted = systemIsDeleted;
	}
	public List<RoleMenuRoot> getRoleMenus() {
		return roleMenus;
	}
	public void setRoleMenus(List<RoleMenuRoot> roleMenus) {
		this.roleMenus = roleMenus;
	}
	
	
}
