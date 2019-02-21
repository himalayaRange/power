package com.bly.common.exception;

import com.bly.common.support.HttpCode;

public class InstanceException extends BaseException {
	private static final long serialVersionUID = -152717955167880006L;

	public InstanceException() {
        super();
    }

    public InstanceException(Throwable t) {
        super(t);
    }

    protected HttpCode getHttpCode() {
        return HttpCode.INTERNAL_SERVER_ERROR;
    }
}
