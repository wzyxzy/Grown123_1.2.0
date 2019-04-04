package com.pndoo.grown123_new.rest;

public class RestExcessiveInvocationException extends RestException {

	private static final long serialVersionUID = 1643122132833200438L;
	private String exceptionCodeString;
	private String exceptionCodeCode;

	public RestExcessiveInvocationException(String exceptionString,
			String exceptionCode) {
		/*
		 * super("Rest Excessive Invocation Exception: " + exceptionString +
		 * " [code " + exceptionCode + "]");
		 */
		super(exceptionString);
		this.exceptionCodeString = exceptionString;
		this.exceptionCodeCode = exceptionCode;
	}

	public String getExceptionString() {
		return exceptionCodeString;
	}

	public String getExceptionCode() {
		return exceptionCodeCode;
	}
}
