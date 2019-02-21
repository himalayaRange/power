package com.bly.common.exception;

import com.bly.common.support.HttpCode;

public class BusinessException extends BaseException {
	private static final long serialVersionUID = -244663491232931931L;

	public BusinessException() {
	}

	public BusinessException(Throwable ex) {
		super(ex);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable ex) {
		super(message, ex);
	}

	@Override
	protected HttpCode getHttpCode() {
		return HttpCode.CONFLICT;
	}

}
