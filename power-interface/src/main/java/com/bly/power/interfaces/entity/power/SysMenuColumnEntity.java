package com.bly.power.interfaces.entity.power;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SysMenuColumnEntity  implements Serializable{
	
	private static final long serialVersionUID = 8139298530241019803L;
	
	@ApiModelProperty(value = "系统ID" ,name="id" ,dataType="Long" ,required=true)
	private Long  id;
	@ApiModelProperty(value = "系统编码" ,name="sysCode" ,dataType="String" ,required=true)
	private String sysCode;
	@ApiModelProperty(value = "系统名称" ,name="sysName" ,dataType="String" ,required=true)
	private String sysName;
	@ApiModelProperty(value = "菜单List" ,name="menuList" ,dataType="String" ,required=true)
	private List<MenuEntity> menuList = new ArrayList<MenuEntity>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public List<MenuEntity> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<MenuEntity> menuList) {
		this.menuList = menuList;
	}
	
	
}
