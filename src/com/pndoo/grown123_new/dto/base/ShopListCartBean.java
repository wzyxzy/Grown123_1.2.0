package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;
import java.util.List;

public class ShopListCartBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ShopListAddTimeBean addTime;
	private String cartCode;
	private int id;
	private List<ShopListBean> items;
	private ShopListUpdateTimeBean updateTime;
	private int userId;
	
	
	public ShopListCartBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ShopListCartBean(ShopListAddTimeBean addTime, String cartCode, int id, List<ShopListBean> items, ShopListUpdateTimeBean updateTime, int userId) {
		super();
		this.addTime = addTime;
		this.cartCode = cartCode;
		this.id = id;
		this.items = items;
		this.updateTime = updateTime;
		this.userId = userId;
	}
	public ShopListAddTimeBean getAddTime() {
		return addTime;
	}
	public void setAddTime(ShopListAddTimeBean addTime) {
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
	public List<ShopListBean> getItems() {
		return items;
	}
	public void setItems(List<ShopListBean> items) {
		this.items = items;
	}
	public ShopListUpdateTimeBean getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(ShopListUpdateTimeBean updateTime) {
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
