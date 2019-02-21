package com.bly.power.interfaces.entity.power.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SystemMenuVo implements Serializable{
	private static final long serialVersionUID = 760599779815284576L;
	@ApiModelProperty(value="系统平台ID" ,name="sysId" ,required=true)
	private Long sysId;
	@ApiModelProperty(value="菜单ID列表" ,name="menuIds" ,required=true)
	private List<MenuColumnVo> menuIds = new ArrayList<MenuColumnVo>();
	public Long getSysId() {
		return sysId;
	}
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
	public List<MenuColumnVo> getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(List<MenuColumnVo> menuIds) {
		this.menuIds = menuIds;
	}
	
}
