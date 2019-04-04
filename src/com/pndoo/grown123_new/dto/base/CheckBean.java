package com.pndoo.grown123_new.dto.base;

public class CheckBean {
	private String code;
	private String codeInfo;
	private CheckDataBean data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeInfo() {
		return codeInfo;
	}
	public void setCodeInfo(String codeInfo) {
		this.codeInfo = codeInfo;
	}

	public CheckDataBean getData() {
		return data;
	}

	public void setData(CheckDataBean data) {
		this.data = data;
	}
}
