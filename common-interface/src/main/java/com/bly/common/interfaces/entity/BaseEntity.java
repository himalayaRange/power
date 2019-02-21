package com.bly.common.interfaces.entity;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Desc : 基础实体类
 * @author : Administrator
 * @date : 2017年8月15日
 */
/*@ApiModel(value="基类" ,description ="基类对象")*/
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -1075193261171464981L;

	@ApiModelProperty(value="主键",name="id",required=true)
	protected Long id; // 主键
	
	@ApiModelProperty(value="是否删除：0，否；1，是",name="isDeleted",required=true)
	protected int isDeleted; // 是否删除：0，否；1，是

	@ApiModelProperty(value="创建时间",name="createTime")
	protected Date createTime; // 创建时间
	@ApiModelProperty(value="修改时间",name="updateTime")
	protected Date updateTime; // 修改时间
	@JsonIgnore
	@ApiModelProperty(value="创建者",name="creator")
	private Long creator; // 创建者
	@JsonIgnore
	@ApiModelProperty(value="最后修改者",name="updater")
	private Long updater;// 最后修改者
	
	@ApiModelProperty(hidden = true)
	private String createrName;//创建者名称
	@ApiModelProperty(hidden = true)
	private String updaterName;//修改者名称
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

}
