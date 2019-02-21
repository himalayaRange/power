package com.bly.common.exception;

import com.bly.common.support.HttpCode;
/**
 * 数据转换异常
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年1月22日 下午2:20:57 
 * Created by Wang.Yi
 */
public class DataParseException extends BaseException {
	private static final long serialVersionUID = 67972725242387158L;

	public DataParseException() {
	}

	public DataParseException(Throwable ex) {
		super(ex);
	}

	public DataParseException(String message) {
		super(message);
	}

	public DataParseException(String message, Throwable ex) {
		super(message, ex);
	}

	protected HttpCode getHttpCode() {
		return HttpCode.INTERNAL_SERVER_ERROR;
	}

}
