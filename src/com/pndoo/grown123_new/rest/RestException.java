package com.pndoo.grown123_new.rest;

/**
 */
public class RestException extends Exception {
	private static final long serialVersionUID = 7499675036625522379L;
	@SuppressWarnings("unused")
	private String faultString;
	@SuppressWarnings("unused")
	private String faultCode;

	public RestException(String faultString, String faultCode) {
		// super("Rset Exception: " + faultString + " [code " + faultCode +
		// "]");
		super(faultString);
		this.faultString = faultString;
		this.faultCode = faultCode;
	}

	public RestException(Exception e) {
		super(e);
	}

	public RestException(String string) {
		super(string);
		this.faultString = string;
	}
}
