package com.bly.power.interfaces.entity.power;

import com.bly.common.interfaces.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Desc : 菜单列管理实体类
 * @author : Administrator
 * @date : 2017年8月25日
 */
@ApiModel
public class MenuColumnEntity extends BaseEntity {

	private static final long serialVersionUID = -6691942664407413851L;
	@ApiModelProperty(value = "所属菜单ID" ,name="menuId" ,dataType="Long" ,required=true)
	private Long menuId;
	@ApiModelProperty(value = "功能编码" ,name="colCode" ,dataType="String" ,required=true)
	private String colCode;
	@ApiModelProperty(value = "功能名称" ,name="colName" ,dataType="String" ,required=true)
	private String colName;
	@ApiModelProperty(value = "URL" ,name="target" ,dataType="String" ,required=true)
	private String target = "javascript:void(0);";
	@ApiModelProperty(value = "排序" ,name="colSort" ,dataType="Integer")
	private Integer colSort;
	@ApiModelProperty(value = "启用状态" ,name="status" ,dataType="Integer" ,required=true)
	private Integer status = 0;
	@ApiModelProperty(value="功能描述",name="describe",dataType="String")
	private String describe;
	
	public Long getMenuId() {
		return menuId;
	}
	
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getColCode() {
		return colCode;
	}

	public void setColCode(String colCode) {
		this.colCode = colCode;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public Integer getColSort() {
		return colSort;
	}

	public void setColSort(Integer colSort) {
		this.colSort = colSort;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
