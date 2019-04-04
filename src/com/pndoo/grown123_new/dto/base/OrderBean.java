package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

public class OrderBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String codeInfo;
	private OrderDataBean data;
	
	
	public OrderBean() {
		super();
		// TODO Auto-generated constructor stub
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


	public OrderDataBean getData() {
		return data;
	}


	public void setData(OrderDataBean data) {
		this.data = data;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
