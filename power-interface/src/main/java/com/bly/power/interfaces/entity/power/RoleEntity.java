package com.bly.power.interfaces.entity.power;

import com.bly.common.interfaces.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Desc : 角色实体类
 * @author : Administrator
 * @date : 2017年8月23日
 */
@ApiModel
public class RoleEntity extends BaseEntity {

	private static final long serialVersionUID = -5168768796417336867L;
	@ApiModelProperty(value = "角色编码" ,name="roleCode" ,dataType="String" ,required=true)
	private String roleCode;
	@ApiModelProperty(value = "角色名称" ,name="roleName" ,dataType="String" ,required=true)
	private String roleName;
	@ApiModelProperty(value = "系统id" ,name="sysId" ,dataType="Integer" ,required=true)
	private Long sysId;
	@ApiModelProperty(value="状态：0,启用；1，冻结",name="status",dataType="Integer",required=true)
	private Integer status = 0;
	@ApiModelProperty(value = "备注" ,name="remark" ,dataType="String")
	private String remark;

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

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
