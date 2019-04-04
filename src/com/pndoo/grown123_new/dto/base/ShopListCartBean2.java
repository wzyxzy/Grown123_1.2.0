package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;
import java.util.List;

public class ShopListCartBean2 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object addTime;
	private String cartCode;
	private int id;
	private Object items;
	private Object updateTime;
	private int userId;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "list="+items;
	}
	
	public Object getAddTime() {
		return addTime;
	}
	public void setAddTime(Object addTime) {
		this.addTime = addTime;
	}
	public String getCartCode() {
		return cartCode;
	}
	public void setCartCode(String cartCode) {
		this.cartCode = cartCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Object getItems() {
		return items;
	}
	public void setItems(Object items) {
		this.items = items;
	}
	public Object getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Object updateTime) {
		this.updateTime = updateTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
