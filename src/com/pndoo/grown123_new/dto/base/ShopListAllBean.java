package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

public class ShopListAllBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String codeInfo;
	private ShopListDataBean data;

	
	
	public ShopListAllBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShopListAllBean(String code, String codeInfo, ShopListDataBean data) {
		super();
		this.code = code;
		this.codeInfo = codeInfo;
		this.data = data;
	}

	// public String getNotice() {
	// return notice;
	// }
	// public void setNotice(String notice) {
	// this.notice = notice;
	// }
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

	public ShopListDataBean getData() {
		return data;
	}

	public void setData(ShopListDataBean data) {
		this.data = data;
	}

}
