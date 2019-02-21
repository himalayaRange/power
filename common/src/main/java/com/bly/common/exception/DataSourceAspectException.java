package com.bly.common.exception;

public class DataSourceAspectException extends RuntimeException {

	private static final long serialVersionUID = 7002906387190790392L;

	public DataSourceAspectException(String message) {
		super(message);
	}

	public DataSourceAspectException(Throwable e) {
		super(new StringBuffer().append("数据源切换遇到异常，FOLLOWS MSG:").append(e.getMessage()).toString());
	}
}
