package com.bly.common.jwt.security;

/**
 * 加密内容常量配置.
 * 
 * Created by wangyi.
 */
public interface ContentSecurityConstants {
	/**
	 * 加密内容字符集
	 */
	public static final String CONTENT_CHARSET = "UTF-8";
	/**
	 * DES方式请求的REQUEST KEY
	 */
	public static final String DES_PARAMTER_NAME = "desString";
	/**
	 * DES解密KEY
	 */
	public static final String DES_KEY = "DE76E3EC39801CEEE0F40025";
	/**
	 * 传入的ATTRIBUTE前缀
	 */
	public static final String ATTRIBUTE_PREFFIX = "security_";
	
}
