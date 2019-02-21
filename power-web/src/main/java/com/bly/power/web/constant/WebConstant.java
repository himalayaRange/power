package com.bly.power.web.constant;

public interface WebConstant {
	/** 权限系统用户session key */
	public static final String POWER_USER_SESSION = "userInfo";
	/** Swagger界面请求地址 */
	public static final String SwaggerServiceUrl = "http://192.168.12.194:10081/api/swagger-ui.html";
	/** 详情请求的接口前缀  */
	public static final String PREFIX_API = "/api";
	/** 存于redis中的角色权限前缀 */
	public static final String PREFIX_POWER_KEY = "PREFIX_POWER_KEY_";
}
