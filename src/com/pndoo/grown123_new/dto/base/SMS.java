package com.pndoo.grown123_new.dto.base;

public class SMS {

	private String verifCode;

	public String getVerifCode() {
		return verifCode;
	}

	public void setVerifCode(String verifCode) {
		this.verifCode = verifCode;
	}

	@Override
	public String toString() {
		return "SMS [verifCode=" + verifCode + "]";
	}

}
