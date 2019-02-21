package com.bly.power.interfaces.entity.power;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class RoleMenuColumnEntity implements Serializable{
	private static final long serialVersionUID = 5266176679721964080L;
	
	@ApiModelProperty(value = "ID" ,name="id" ,dataType="Long" ,required=true)
	private Long id;
	@ApiModelProperty(value = "角色菜单id" ,name="roleMenuId" ,dataType="Long" ,required=true)
	private Long roleMenuId;
	@ApiModelProperty(value = "菜单列id" ,name="columnId" ,dataType="Long" ,required=true)
	private Long columnId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getRoleMenuId() {
		return roleMenuId;
	}
	public void setRoleMenuId(Long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	
	
}
