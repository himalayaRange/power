package com.bly.common.utils;

/**
 * 
 * @Desc   : 自定义异常.错误
 * @author : Administrator
 * @date   : 2017年8月15日
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 5965715863323012357L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
