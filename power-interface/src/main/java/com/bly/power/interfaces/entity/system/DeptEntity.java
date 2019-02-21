package com.bly.power.interfaces.entity.system;

import com.bly.common.interfaces.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Desc : 部门管理
 * @author : Administrator
 * @date : 2017年8月21日
 */
@ApiModel(value="部门实体", description="部门对象")
public class DeptEntity extends BaseEntity {

	private static final long serialVersionUID = -9008325689390113577L;

	@ApiModelProperty(value = "部门编码" ,name="deptCode" ,required=true)
	private String deptCode;
	@ApiModelProperty(value = "部门名称" ,name="deptName" ,required=true)
	private String deptName;
	@ApiModelProperty(value = "部门联系人" ,name="deptMan" ,required=true)
	private String deptMan;
	@ApiModelProperty(value = "部门地址" ,name="deptAddress")
	private String deptAddress;
	@ApiModelProperty(value = "所属公司" ,name="comId" ,required=true)
	private Long comId;
	@ApiModelProperty(value = "状态：0,启用；1，冻结" ,name="status" ,required=true)
	private Integer status = 0; 
	@ApiModelProperty(value = "上级部门ID" ,name="parentId")
	private Long parentId = 0L;
	@ApiModelProperty(value = "备注" ,name="remark")
	private String remark; 
	
	@ApiModelProperty(value = "公司编码" ,name="comCode",hidden=true)
	private String comCode;
	@ApiModelProperty(value = "公司名称" ,name="comName",hidden=true)
	private String comName;
	@ApiModelProperty(value = "上级编码" ,name="parentCode",hidden=true)
	private String parentCode;
	@ApiModelProperty(value = "上级部门" ,name="parentName" ,hidden=true)
	private String parentName;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptMan() {
		return deptMan;
	}

	public void setDeptMan(String deptMan) {
		this.deptMan = deptMan;
	}

	public String getDeptAddress() {
		return deptAddress;
	}

	public void setDeptAddress(String deptAddress) {
		this.deptAddress = deptAddress;
	}

	public Long getComId() {
		return comId;
	}

	public void setComId(Long comId) {
		this.comId = comId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
