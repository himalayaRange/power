package com.bly.power.interfaces.entity.system;

import java.math.BigDecimal;
import com.bly.common.interfaces.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Desc : 公司管理实体类
 * @author : Administrator
 * @date : 2017年8月21日
 */
@ApiModel(value = "company对象" ,description = "公司对象")
public class CompanyEntity extends BaseEntity {

	private static final long serialVersionUID = -8658857187063565077L;
	@ApiModelProperty(value = "公司编码" ,name="comCode" ,required=true)
	private String comCode; 
	@ApiModelProperty(value = "公司名称" ,name="comName" ,required=true)
	private String comName; 
	@ApiModelProperty(value = "简称" ,name="comShortName" ,required=true)
	private String comShortName;
	@ApiModelProperty(value = "注册名称" ,name="registerName" ,required=true)
	private String registerName;
	@ApiModelProperty(value = "公司性质:贸易型，工程型" ,name="property")
	private Integer property;
	@ApiModelProperty(value = "注册资金" ,name="registerCapital")
	private BigDecimal registerCapital;
	@ApiModelProperty(value = "付款方式：0，现金；1，月结等" ,name="payType")
	private Integer payType; 
	@ApiModelProperty(value = "生成规模" ,name="makeSize")
	private String makeSize; 
	@ApiModelProperty(value = "邮箱" ,name="mail")
	private String mail;
	@ApiModelProperty(value = "联系人" ,name="linkMan" ,required=true)
	private String linkMan; 
	@ApiModelProperty(value = "联系方式" ,name="contactWay" ,required=true)
	private String contactWay; 
	@ApiModelProperty(value = "联系地址" ,name="address")
	private String address; 
	@ApiModelProperty(value = "级别：1，一；2，二；3，三" ,name="level")
	private Integer level; 
	@ApiModelProperty(value = "区域" ,name="area")
	private String area;
	@ApiModelProperty(value = "经营范围：多个id之间用“,”分开" ,name="scope")
	private String scope; 
	@ApiModelProperty(value = "备注" ,name="remark")
	private String remark; 
	@ApiModelProperty(value = " 父公司id" ,name="parentId")
	private Long parentId; 
	@ApiModelProperty(value = "状态：0,启用；1，冻结'" ,name="status" ,required=true)
	private Integer status = 0; 
	

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

	public String getComShortName() {
		return comShortName;
	}

	public void setComShortName(String comShortName) {
		this.comShortName = comShortName;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Integer getProperty() {
		return property;
	}

	public void setProperty(Integer property) {
		this.property = property;
	}

	public BigDecimal getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(BigDecimal registerCapital) {
		this.registerCapital = registerCapital;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getMakeSize() {
		return makeSize;
	}

	public void setMakeSize(String makeSize) {
		this.makeSize = makeSize;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CompanyEntity [comCode=" + comCode + ", comName=" + comName + ", comShortName=" + comShortName
				+ ", registerName=" + registerName + ", property=" + property + ", registerCapital=" + registerCapital
				+ ", payType=" + payType + ", makeSize=" + makeSize + ", mail=" + mail + ", linkMan=" + linkMan
				+ ", contactWay=" + contactWay + ", address=" + address + ", level=" + level + ", area=" + area
				+ ", scope=" + scope + ", remark=" + remark + ", parentId=" + parentId + ", status=" + status + "]";
	}

	
}
