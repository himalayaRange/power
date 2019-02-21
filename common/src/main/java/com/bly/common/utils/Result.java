package com.bly.common.utils;

import java.io.Serializable;

/**
 * 
 * @Desc : 结果处理类
 * @author : Administrator
 * @date : 2017年8月15日
 */
public class Result<T> implements Serializable{
	private static final long serialVersionUID = 1693486784220656491L;
	
	private T data;// 数据对象
	private boolean success;
	private String message;
	private boolean passport;//是否登录，能访问的表示登录成功的

	// 返回成功结果
	public static <T> Result<T> resultSuccess(T data, String message) {
		Result<T> result = new Result<>();
		result.data = data;
		result.success = true;
		result.message = message;
		result.passport = true; 
		return result;
	}

	// 返回失败
	public static <T> Result<T> resultError(String message) {
		Result<T> result = new Result<>();
		result.success = false;
		result.message = message;
		result.passport = true; 
		return result;
	}
	
	// 返回为登录结果
	public static <T> Result<T> resultPassport(T data, String message) {
			Result<T> result = new Result<>();
			result.data = data;
			result.success = true;
			result.message = message;
			result.passport = false; 
			return result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isPassport() {
		return passport;
	}

	public void setPassport(boolean passport) {
		this.passport = passport;
	}
	
}
