package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

public class ShopListDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ShopListCartBean cart;

	
	
	public ShopListDataBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShopListDataBean(ShopListCartBean cart) {
		super();
		this.cart = cart;
	}

	public ShopListCartBean getCart() {
		return cart;
	}

	public void setCart(ShopListCartBean cart) {
		this.cart = cart;
	}
	
}
