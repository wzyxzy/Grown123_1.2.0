package com.pndoo.grown123_new;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pndoo.grown123_new.controller.base.CrashHandler;
import com.pndoo.grown123_new.task.TaskManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;

import org.xutils.x;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * 默认参数
 * 
 */
public class MyApplication extends Application {

	// 任务管理
	public TaskManager tm;
	ArrayList<Activity> activitylist = new ArrayList<Activity>();
	int ADD_FLAG = -1;
	String notice = "";
	public static boolean isLogin = true;

	// @Override
	// protected void attachBaseContext(Context base) {
	// super.attachBaseContext(base);
	// MultiDex.install(this);
	// }

	@Override
	public void onCreate() {

		super.onCreate();
		context = getApplicationContext();
		x.Ext.init(this);
		x.Ext.setDebug(true); //是否输出debug日志，开启debug会影响性能。
		tm = new TaskManager();
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		initImageLoader(getApplicationContext());
//		/**
//		 * 以下是skyTest的内容
//		 */
//		sd = new SkyDatabase(this);
//		reloadBookInformations();
//
//		loadSetting();

		JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志 
		JPushInterface.init(this);
		
		MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        // MobclickAgent.setAutoLocation(true);
        // MobclickAgent.setSessionContinueMillis(1000);
        // MobclickAgent.startWithConfigure(
        // new UMAnalyticsConfig(mContext, "4f83c5d852701564c0000011", "Umeng", EScenarioType.E_UM_NORMAL));
        MobclickAgent.setScenarioType(this, EScenarioType.E_UM_NORMAL);

	}

	// public native void init(String string);
	public TaskManager getTaskManager() {
		return tm;
	}

	public void addActivity(Activity activity) {
		activitylist.add(activity);
	}

	public void finishAllActivity() {
		while (activitylist.size() > 0) {
			activitylist.get(0).finish();
			activitylist.remove(0);
		}
	}

	public void removeActivity(Activity activity) {
		activitylist.remove(activity);
	}

	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs().discCacheSize(50 * 1024 * 1024)
				.memoryCache(new WeakMemoryCache()).memoryCacheSize((int) (2 * 1024 * 1024)).memoryCacheSizePercentage(13).build();
		ImageLoader.getInstance().init(config);
	}

	private static Context context;

	public static Context getContext() {
		return context;
	}
	

//	/**
//	 * 以下是skyTest的内容
//	 */
//	public String message = "We are the world.";
//	public ArrayList<BookInformation> bis;
//	public ArrayList<CustomFont> customFonts = new ArrayList<CustomFont>();
//	public SkySetting setting;
//	public SkyDatabase sd = null;
//	public int sortType = 0;
//
//	public void reloadBookInformations() {
//		this.bis = sd.fetchBookInformations(sortType, "");
//	}
//
//	public void reloadBookInformations(String key) {
//		this.bis = sd.fetchBookInformations(sortType, key);
//	}
//
//	public void loadSetting() {
//		this.setting = sd.fetchSetting();
//	}
//
//	public void saveSetting() {
//		sd.updateSetting(this.setting);
//	}

}
