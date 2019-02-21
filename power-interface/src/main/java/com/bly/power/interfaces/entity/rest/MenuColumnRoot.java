package com.bly.power.interfaces.entity.rest;

import java.io.Serializable;

public class MenuColumnRoot implements  Serializable{

	private static final long serialVersionUID = 390803078567614858L;
	/** 列信息 */
	private Long columnId;
	private String colCode;
	private String colName;
	private String target;
	private String describe;
	private Integer colSort;
	private Integer columnStatus;
	private Integer columnIsDeleted;
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public String getColCode() {
		return colCode;
	}
	public void setColCode(String colCode) {
		this.colCode = colCode;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Integer getColSort() {
		return colSort;
	}
	public void setColSort(Integer colSort) {
		this.colSort = colSort;
	}
	public Integer getColumnStatus() {
		return columnStatus;
	}
	public void setColumnStatus(Integer columnStatus) {
		this.columnStatus = columnStatus;
	}
	public Integer getColumnIsDeleted() {
		return columnIsDeleted;
	}
	public void setColumnIsDeleted(Integer columnIsDeleted) {
		this.columnIsDeleted = columnIsDeleted;
	}
	
}
