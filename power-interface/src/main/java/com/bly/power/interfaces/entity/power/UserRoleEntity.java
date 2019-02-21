package com.bly.power.interfaces.entity.power;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class UserRoleEntity implements Serializable {

	private static final long serialVersionUID = 2209493476356247679L;
	@ApiModelProperty(value = "用户ID" ,name="userId" ,dataType="Long" ,required=true)
	private Long userId;
	@ApiModelProperty(value="角色ID",name="roleId",dataType="Long",required=true)
	private Long roleId;// 角色id

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
