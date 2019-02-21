package com.bly.common.utils;

/**
 * 
 * @Desc : 通用常量
 * @author : Administrator
 * @date : 2017年8月15日
 */
public class CommonConstants {
	public interface CONTENT_TYPE {
		public static final String TEXT_TYPE = "text/plain";
		public static final String JSON_TYPE = "application/json";
		public static final String XML_TYPE = "text/xml";
		public static final String HTML_TYPE = "text/html";
		public static final String JS_TYPE = "text/javascript";
		public static final String EXCEL_TYPE = "application/vnd.ms-excel";
	}

	public interface ENCODE {
		public static final String UTF_8 = "UTF-8";
	}

	/**
	 * 全局常量-文件路径等适用
	 */
	public interface FilePath {
		// 系统文件上传分类
		public static final String SEPARATOR = "/";
	}

	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
}
