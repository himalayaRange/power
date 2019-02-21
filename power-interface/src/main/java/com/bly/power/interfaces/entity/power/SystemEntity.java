package com.bly.power.interfaces.entity.power;

import com.bly.common.interfaces.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Desc : 系统管理
 * @author : Administrator
 * @date : 2017年8月24日
 */
@ApiModel
public class SystemEntity extends BaseEntity {

	private static final long serialVersionUID = 3881891146480807324L;

	@ApiModelProperty(value = "系统编码" ,name="sysCode" ,dataType="String" ,required=true)
	private String sysCode; 
	@ApiModelProperty(value = "系统名称" ,name="sysName" ,dataType="String" ,required=true)
	private String sysName; 
	@ApiModelProperty(value = "状态：0,启用；1，冻结'" ,name="status" ,required=true)
	private Integer status = 0; 
	@ApiModelProperty(value = "备注" ,name="remark" ,dataType="String")
	private String remark; 

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
