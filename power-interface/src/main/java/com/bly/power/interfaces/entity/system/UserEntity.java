package com.bly.power.interfaces.entity.system;

import java.util.Date;

import com.bly.common.interfaces.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Desc : 用户管理
 * @author : Administrator
 * @date : 2017年8月22日
 */
@ApiModel(value="user实体" , description="用户实体")
public class UserEntity extends BaseEntity {

	private static final long serialVersionUID = -5729388795612132445L;
	@ApiModelProperty(value = "用户编码" ,name="userCode" ,dataType="String" ,required=true)
	private String userCode;
	@ApiModelProperty(value = "用户名" ,name="userName"  , dataType="String",required=true)
	private String userName;
	@ApiModelProperty(value = "密码" ,name="password"    , dataType="String",required=true)
	private String password;
	@ApiModelProperty(value = "真实姓名" ,name="realName" ,dataType="String",required=true)
	private String realName;
	@ApiModelProperty(value="性别:0,女；1，男；2，其他" ,name="sex",dataType="Integer")
	private Integer sex;
	@ApiModelProperty(value="出生日期" ,name="birthday" ,dataType="Date")
	private Date birthday; 
	@ApiModelProperty(value="联系电话" ,name="phone" ,dataType="String")
	private String phone;
	@ApiModelProperty(value="联系地址" ,name="address" ,dataType="String")
	private String address;
	@ApiModelProperty(value="所属部门" ,name="deptId" ,dataType="Long",required=true)
	private Long deptId;
	@ApiModelProperty(value="状态：0,启用；1，冻结'" ,name="status" ,dataType="Integer",required=true)
	private Integer status = 0; 
	@ApiModelProperty(value="所属公司" ,name="comId" ,dataType="Long",required=true)
	private Long comId;
	@ApiModelProperty(value="备注" ,name="remark" ,dataType="String")
	private String remark; 

	@ApiModelProperty(value = "部门编码" ,name="deptCode" ,dataType="String",hidden=true)
	private String deptCode;
	@ApiModelProperty(value = "部门名称" ,name="deptName" ,dataType="String",hidden=true)
	private String deptName;
	@ApiModelProperty(value = "公司编码" ,name="comCode" ,dataType="String",hidden=true)
	private String comCode;
	@ApiModelProperty(value = "公司名称" ,name="comName" ,dataType="String",hidden=true)
	private String comName;
	@ApiModelProperty(value="所属角色ID",name="roleId",dataType="Long",hidden=true)
	private Long roleId;
	@ApiModelProperty(value="所属角色Code",name="roleCode",dataType="String",hidden=true)
	private String roleCode;
	@ApiModelProperty(value="角色名称",name="roleName",dataType="String",hidden=true)
	private String roleName;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getComId() {
		return comId;
	}
	public void setComId(Long comId) {
		this.comId = comId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	@Override
	public String toString() {
		return "UserEntity [userCode=" + userCode + ", userName=" + userName + ", password=" + password + ", realName="
				+ realName + ", sex=" + sex + ", birthday=" + birthday + ", phone=" + phone + ", address=" + address
				+ ", deptId=" + deptId + ", status=" + status + ", comId=" + comId + ", remark=" + remark
				+ ", deptCode=" + deptCode + ", deptName=" + deptName + ", comCode=" + comCode + ", comName=" + comName
				+ ", roleId=" + roleId + ", roleCode=" + roleCode + ", roleName=" + roleName + "]";
	}
	

}
