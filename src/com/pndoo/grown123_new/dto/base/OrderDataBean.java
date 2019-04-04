package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

public class OrderDataBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderCode;
	private String totalPrice;
	private String notify_url;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "orderCode====="+orderCode+"-----totalPrice====="+totalPrice+"----notify_url==="+notify_url;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public OrderDataBean(String orderCode, String totalPrice, String notify_url) {
		super();
		this.orderCode = orderCode;
		this.totalPrice = totalPrice;
		this.notify_url = notify_url;
	}
	public OrderDataBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
