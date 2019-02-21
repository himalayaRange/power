package com.bly.power.interfaces.entity.power;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Desc : 角色菜单
 * @author : Administrator
 * @date : 2017年8月25日
 */
@ApiModel
public class RoleMenuEntity implements Serializable {

	private static final long serialVersionUID = 356497236183996669L;
	@ApiModelProperty(value = "ID" ,name="id" ,dataType="Long" ,required=true)
	private Long id;
	@ApiModelProperty(value = "角色id" ,name="roleId" ,dataType="Long" ,required=true)
	private Long roleId;
	@ApiModelProperty(value = "菜单id" ,name="menuId" ,dataType="Long" ,required=true)
	private Long menuId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	
}
