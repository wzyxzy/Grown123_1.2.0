package com.pndoo.grown123_new.main;

import com.pndoo.grown123_new.dto.base.UserVO;

/**
 * Created by IntelliJ IDEA. User: yanwt Date: 11-5-25 Time: 上午11:31 To change
 * this template use File | Settings | File Templates.
 */
public interface IShopinApplication {

	public void setApplicationModel(UserVO user);

	public UserVO getApplicationModel();
}
