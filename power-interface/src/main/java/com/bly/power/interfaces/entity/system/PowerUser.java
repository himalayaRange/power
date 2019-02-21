package com.bly.power.interfaces.entity.system;

import com.bly.common.interfaces.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PowerUser extends BaseEntity {

	private static final long serialVersionUID = 4680403998325681741L;
	@ApiModelProperty(value = "管理员用户编码" ,name="userCode" ,dataType="String" ,required=true)
	private String userCode;
	@ApiModelProperty(value = "管理员用户名" ,name="userName" ,dataType="String" ,required=true)
    private String userName;
	@ApiModelProperty(value = "管理员密码" ,name="passWord" ,dataType="String" ,required=true)
    private String passWord;
	@ApiModelProperty(value = "真实姓名" ,name="realName" ,dataType="String" ,required=true)
    private String realName;
	@ApiModelProperty(value = "手机号" ,name="phone" ,dataType="String")
    private String phone;
	@ApiModelProperty(value = "状态" ,name="status" ,dataType="Integer" ,required=true)
    private Integer status=0;
	@ApiModelProperty(value = "管理员用户编码" ,name="remark" ,dataType="String")
    private String remark;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
