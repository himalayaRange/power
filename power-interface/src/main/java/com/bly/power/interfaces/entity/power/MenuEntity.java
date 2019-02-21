package com.bly.power.interfaces.entity.power;

import java.util.ArrayList;
import java.util.List;
import com.bly.common.interfaces.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Desc : 菜单管理
 * @author : Administrator
 * @date : 2017年8月24日
 */
@ApiModel
public class MenuEntity extends BaseEntity {

	private static final long serialVersionUID = 6560743023723352880L;
	@ApiModelProperty(value = "菜单编码" ,name="menuCode" ,dataType="String" ,required=true)
	private String menuCode; 
	@ApiModelProperty(value = "菜单名称" ,name="menuName" ,dataType="String" ,required=true)
	private String menuName; 
	@ApiModelProperty(value = "URL" ,name="url" ,dataType="String" ,required=true)
	private String url;
	@ApiModelProperty(value = "上级菜单" ,name="parentId" ,dataType="Long" ,required=true)
	private Long parentId; 
	@ApiModelProperty(value = "类型：0，父子节点；1，叶子节点" ,name="menuType" ,dataType="Integer" ,required=true)
	private Integer menuType;
	@ApiModelProperty(value = "项目或平台id" ,name="sysId" ,dataType="Long" ,required=true)
	private Long sysId; 
	@ApiModelProperty(value = "启用状态" ,name="status" ,dataType="Integer" ,required=true)
	private Integer status = 0;
	@ApiModelProperty(value = "排序" ,name="menuSort" ,dataType="Integer" ,required=true)
	private Integer menuSort;
	@ApiModelProperty(value = "备注" ,name="remark" ,dataType="String")
	private String remark; 
	
	@ApiModelProperty(value="所属系统编码",name="sysCode",dataType="String",hidden=true)
	private String sysCode;
	@ApiModelProperty(value="所属系统名称",name="sysName",dataType="String",hidden=true)
	private String sysName;
	@ApiModelProperty(value="菜单下所有的列",name="columns",dataType="List" ,hidden=true)
	private List<MenuColumnEntity> columns = new ArrayList<MenuColumnEntity>();
	/** 扩展属性 */
	@ApiModelProperty(value="主键",name="menuId",hidden = true)
	protected Long menuId; 
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public Integer getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
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

	public List<MenuColumnEntity> getColumns() {
		return columns;
	}

	public void setColumns(List<MenuColumnEntity> columns) {
		this.columns = columns;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	
}
