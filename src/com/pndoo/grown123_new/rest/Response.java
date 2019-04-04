package com.pndoo.grown123_new.rest;

/**
 * Created by IntelliJ IDEA. To change this template use File | Settings | File
 * Templates.
 */
public class Response {
	private Object data;
	private String code;
	private String codeInfo;
	private String notice;

	@Override
	public String toString() {
		return "Response [data=" + data + ", code=" + code + ", codeInfo="
				+ codeInfo + ", notice=" + notice + "]";
	}

	public Response() {
	}

	public Response(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

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
}
