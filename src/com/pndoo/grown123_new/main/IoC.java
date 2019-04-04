package com.pndoo.grown123_new.main;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by IntelliJ IDEA. User: yanwt Date: 11-5-25 Time: 上午11:25 To change
 * this template use File | Settings | File Templates.
 */
public class IoC {
	private static Injector injector = Guice.createInjector(new ShopinModule());

	/**
	 * Gets an instance of the given class
	 * 
	 * @param <T>
	 *            the type to instantiate
	 * @param clazz
	 *            the class to instantiate
	 * @return
	 */
	public static <T> T getInstance(Class<T> clazz) {

		return injector.getInstance(clazz);

	}

	public static void reset() {
		injector = Guice.createInjector(new ShopinModule());
	}
}
