package com.bly.common.exception;

import com.bly.common.support.HttpCode;
/**
 * 不合法的参数请求
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年1月22日 下午2:22:51 
 * Created by Wang.Yi
 */
public class IllegalParameterException extends BaseException{
	
	private static final long serialVersionUID = 590736305212019711L;

	public IllegalParameterException() {
	}

	public IllegalParameterException(Throwable ex) {
		super(ex);
	}

	public IllegalParameterException(String message) {
		super(message);
	}

	public IllegalParameterException(String message, Throwable ex) {
		super(message, ex);
	}

	@Override
	protected HttpCode getHttpCode() {
		return HttpCode.BAD_REQUEST;
	}

}
