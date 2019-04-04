package com.pndoo.grown123_new.rest;

/**
 * Created by IntelliJ IDEA. To change this template use File | Settings | File
 * Templates.
 */
public class RestFault extends RestException {
	/**
	 *
	 */
	private static final long serialVersionUID = 5676562456612956519L;
	private String faultString;
	private String faultCode;

	public RestFault(String faultString, String faultCode) {
		// super("Rset Fault: " + faultString + " [code " + faultCode + "]");
		super(faultString);
		this.faultString = faultString;
		this.faultCode = faultCode;
	}

	public String getFaultString() {
		return faultString;
	}

	public String getFaultCode() {
		return faultCode;
	}
}
