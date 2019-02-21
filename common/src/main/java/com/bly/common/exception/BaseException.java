package com.bly.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import com.bly.common.support.HttpCode;

public abstract class BaseException extends RuntimeException{

	private static final long serialVersionUID = -5799315438299573249L;

	public BaseException() {
	}

	public BaseException(Throwable ex) {
		super(ex);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable ex) {
		super(message, ex);
	}
	
	/**
	 * Exception Handler
	 * @param modelMap
	 */
	public void handler(ModelMap modelMap) {
		modelMap.put("httpCode", getHttpCode().value());
		if (StringUtils.isNotBlank(getMessage())) {
			modelMap.put("msg", getMessage());
		} else {
			modelMap.put("msg", getHttpCode().msg());
		}
		modelMap.put("timestamp", System.currentTimeMillis());
	}

	protected abstract HttpCode getHttpCode();
}
