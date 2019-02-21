package com.bly.power.interfaces.entity.power.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MenuColumnVo implements Serializable{

	private static final long serialVersionUID = 8925936740055685344L;
	@ApiModelProperty(value="菜单ID",name="menuId",required=true)
	private Long menuId;
	@ApiModelProperty(value="菜单功能ID列表",name="columnIds",required=true)
	private List<ColumnVo> columnIds = new ArrayList<ColumnVo>();
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public List<ColumnVo> getColumnIds() {
		return columnIds;
	}
	public void setColumnIds(List<ColumnVo> columnIds) {
		this.columnIds = columnIds;
	}
	
}
