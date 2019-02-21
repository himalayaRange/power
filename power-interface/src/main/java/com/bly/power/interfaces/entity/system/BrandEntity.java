package com.bly.power.interfaces.entity.system;

import com.bly.common.interfaces.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Desc : 品牌管理
 * @author : Administrator
 * @date : 2017年8月22日
 */
@ApiModel
public class BrandEntity extends BaseEntity {

	private static final long serialVersionUID = -5880594990410404850L;
	
	@ApiModelProperty(value="商品编码" , name="brandCode" , required=true)
	private String brandCode;
	@ApiModelProperty(value="品牌名称" ,name="brandName" , required=true)
	private String brandName;
	@ApiModelProperty(value="所属公司" ,name="comId" , required=true)
	private Long comId;
	@ApiModelProperty(value = "状态：0,启用；1,冻结" ,name="status" ,required=true)
	private Integer status = 0; 
	@ApiModelProperty(value="remark" ,name="remark" ,required=true)
	private String remark;
	@ApiModelProperty(hidden=true)
	private String comCode;// 公司编码
	@ApiModelProperty(hidden=true)
	private String comName;// 公司名称

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
}
