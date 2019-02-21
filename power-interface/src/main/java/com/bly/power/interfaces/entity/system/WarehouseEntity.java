package com.bly.power.interfaces.entity.system;

import com.bly.common.interfaces.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Desc : 仓库实体类
 * @author : Administrator
 * @date : 2017年8月23日
 */
@ApiModel
public class WarehouseEntity extends BaseEntity {

	private static final long serialVersionUID = 4794286315934357090L;
	@ApiModelProperty(value="仓库编码" ,name="wareCode" ,required=true)
	private String wareCode;
	@ApiModelProperty(value="仓库名称/仓库片区" ,name="wareName" , required=true)
	private String wareName;
	@ApiModelProperty(value="所属公司" ,name="comId" , required=true)
	private Long comId;
	@ApiModelProperty(value="仓库联系人" ,name="wareMan" ,required=true)
	private String wareMan;
	@ApiModelProperty(value="仓库地址" ,name="wareAddress")
	private String wareAddress;
	@ApiModelProperty(value="仓库联系电话",name="warePhone" ,required=true)
	private String warePhone;
	@ApiModelProperty(value="状态",name="status",required=true)
	private Integer status = 0;
	@ApiModelProperty(value="父仓库id" , name="parentId" ,required=true)
	private Long parentId;
	@ApiModelProperty(value="备注",name="remark")
	private String remark;
	@ApiModelProperty(hidden=true)
	private String comCode;// 公司编码
	@ApiModelProperty(hidden=true)
	private String comName;// 公司名称

	public String getWareCode() {
		return wareCode;
	}

	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}

	public String getWareName() {
		return wareName;
	}

	public void setWareName(String wareName) {
		this.wareName = wareName;
	}

	public Long getComId() {
		return comId;
	}

	public void setComId(Long comId) {
		this.comId = comId;
	}

	public String getWareMan() {
		return wareMan;
	}

	public void setWareMan(String wareMan) {
		this.wareMan = wareMan;
	}

	public String getWareAddress() {
		return wareAddress;
	}

	public void setWareAddress(String wareAddress) {
		this.wareAddress = wareAddress;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public String getWarePhone() {
		return warePhone;
	}

	public void setWarePhone(String warePhone) {
		this.warePhone = warePhone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
}
