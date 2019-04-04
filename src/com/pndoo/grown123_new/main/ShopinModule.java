package com.pndoo.grown123_new.main;

import com.google.inject.AbstractModule;
import com.pndoo.grown123_new.annotations.ConnectURL;
import com.pndoo.grown123_new.rest.IRestService;
import com.pndoo.grown123_new.rest.RestService;

/**
 * Created by IntelliJ IDEA. User: yanwt Date: 11-5-25 Time: 上午11:26 To change
 * this template use File | Settings | File Templates.
 */
public class ShopinModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IShopinApplication.class).to(ShopinApplication.class);
		bind(IRestService.class).to(RestService.class);
		// ConnectURL: 0公网测试 1内网测试 2正式生产环境 3陆
		bind(String.class).annotatedWith(ConnectURL.class).toInstance(
				"http://172.16.103.131:8080/");

	}
}
