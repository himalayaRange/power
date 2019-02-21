package com.bly.common.web.controller;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bly.common.utils.AjaxUtils;
import com.bly.common.utils.Result;

/**
 * 
 * @Desc   : 基础controller类
 * @author : Administrator
 * @date   : 2017年8月15日
 */
public abstract class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 返回通用方法
	 * @param response
	 * @param isTrue：true表示成功；false表示失败
	 * @param message：消息提示
	 * @return 
	 */
	public <T> void doResult(HttpServletResponse response, boolean isTrue, T data,String message) {
		if(isTrue){
			AjaxUtils.renderJson(response, Result.resultSuccess(data, message));
		}
		else{
			AjaxUtils.renderJson(response, Result.resultError(message));
		}
	}
	
	/**
	 * 没有权限登录方法
	 * @param response
	 * @param isTrue
	 * @param data
	 * @param message
	 */
	public <T> void doPassort(HttpServletResponse response , boolean isTrue ,T data ,String message){
		AjaxUtils.renderJson(response, Result.resultPassport(data, message));
	}
}
