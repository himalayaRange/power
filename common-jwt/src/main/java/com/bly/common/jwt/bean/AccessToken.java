package com.bly.common.jwt.bean;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccessToken implements Serializable{
	
	private static final long serialVersionUID = -3930882266182237361L;
	// token的类型
	private String tokenType;
	// 用户ID
	private String userId;
	// 用户名
	private String userName;
	// 角色ID
	private String roleId;
	// 角色名称
	private String roleName;
	// 个人签名
	private String audience;
	// 发送给谁，如xxxx@163.com
	private String issuser;
	// token失效时间，单位毫秒
	private long expire;
	// 私钥
	private String privateKey;
	// 自定义request参数，替换session中参数，线程安全
	private Map<String , Object> attributes = new ConcurrentHashMap<String , Object>();
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getAudience() {
		return audience;
	}
	public void setAudience(String audience) {
		this.audience = audience;
	}
	public String getIssuser() {
		return issuser;
	}
	public void setIssuser(String issuser) {
		this.issuser = issuser;
	}
	public long getExpire() {
		return expire;
	}
	public void setExpire(long expire) {
		this.expire = expire;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	
}
