package com.pndoo.grown123_new.main;

import com.google.inject.Singleton;
import com.pndoo.grown123_new.dto.base.UserVO;

/**
 * Created by IntelliJ IDEA. User: yanwt Date: 11-5-25 Time: 上午11:30 To change
 * this template use File | Settings | File Templates.
 */
@Singleton
public class ShopinApplication implements IShopinApplication {
	private UserVO user;

	@Override
	public void setApplicationModel(UserVO u) {
		this.user = u;
	}

	@Override
	public UserVO getApplicationModel() {
		return user;
	}
}
