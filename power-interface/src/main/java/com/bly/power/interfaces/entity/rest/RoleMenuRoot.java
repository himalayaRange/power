package com.bly.power.interfaces.entity.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoleMenuRoot implements Serializable {

	private static final long serialVersionUID = 3063002872833556654L;
	/** 菜单信息 */
	private Long menuId;
	private String menuCode;
	private String menuName;
	private Long parentId;
	private String url;
	private Integer menuSort;
	private Integer menuType;
	private Integer menuStatus;
	private Integer menuIsDeleted;
	/** 菜单列信息 */
	private List<MenuColumnRoot> columns = new ArrayList<MenuColumnRoot>();
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getMenuSort() {
		return menuSort;
	}
	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}
	public Integer getMenuType() {
		return menuType;
	}
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}
	public Integer getMenuStatus() {
		return menuStatus;
	}
	public void setMenuStatus(Integer menuStatus) {
		this.menuStatus = menuStatus;
	}
	public Integer getMenuIsDeleted() {
		return menuIsDeleted;
	}
	public void setMenuIsDeleted(Integer menuIsDeleted) {
		this.menuIsDeleted = menuIsDeleted;
	}
	public List<MenuColumnRoot> getColumns() {
		return columns;
	}
	public void setColumns(List<MenuColumnRoot> columns) {
		this.columns = columns;
	}
	
}
