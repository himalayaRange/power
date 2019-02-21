package com.bly.power.interfaces.entity.power.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ColumnVo implements Serializable{
	private static final long serialVersionUID = -1637041082863569650L;
	@ApiModelProperty(value="菜单功能ID",name="columnId",required=true)
	private Long columnId;
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	
}
