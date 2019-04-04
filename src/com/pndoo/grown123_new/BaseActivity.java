package com.pndoo.grown123_new;

import android.app.ActivityGroup;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.umeng.analytics.MobclickAgent;
@SuppressWarnings("deprecation")
public class BaseActivity extends ActivityGroup {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	int MSG_FLAG = -1;
	Handler handler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			MSG_FLAG = msg.what;
			doNetResult(MSG_FLAG);
			return false;
		}
	});

	protected void getWaitDialog(Context context, String showContent){
		if(!TextUtils.isEmpty(showContent)){
			DialogUtils.showMyDialog(context, MyPreferences.SHOW_PROGRESS_DIALOG, null, showContent, null);
		}else{
			DialogUtils.showMyDialog(context, MyPreferences.SHOW_PROGRESS_DIALOG, null, "加载中...", null);
		}
	}

	protected void dismissDialog(){
		DialogUtils.dismissMyDialog();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public MyApplication getApplicationContext() {
		return (MyApplication) super.getApplicationContext();
	}
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onDestroy() {
		TaskManager tm = getApplicationContext().getTaskManager();
		Log.i("other", "取消所有线程");
		tm.cancelAll();
		super.onDestroy();
	}
	/**
	 * 访问网络回来需要执行的方法
	 */
	public void doNetResult(int MSG_FLAG) {

	}

}
