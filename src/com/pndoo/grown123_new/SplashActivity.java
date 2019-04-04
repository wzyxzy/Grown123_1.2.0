package com.pndoo.grown123_new;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.OperationFileHelper;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

public class SplashActivity extends BaseActivity {
	public final static int LOADMAIN = 1;
	public final static int GUIDE = 2;
	RelativeLayout ll_splash;
	private SharedPreferences preferences;
	String version;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case LOADMAIN :
					if (MyApplication.isLogin) {
						Bundle b = getIntent().getBundleExtra("extra");
						Intent intent = new Intent(SplashActivity.this,
								LoginActivity.class);
						if(b!=null)
							intent.putExtra("extra",b);
						startActivity(intent);
					} else {
						startActivity(new Intent(SplashActivity.this,
								BookActivity.class));
					}
					SplashActivity.this.finish();
					break;
				case GUIDE :
					if (!ActivityUtils.isNetworkAvailable(getBaseContext())) {
						Toast.makeText(getBaseContext(), "请连接网络进行操作", 0).show();
					}
					startActivity(new Intent(SplashActivity.this,
							GuideActivity.class));
					SplashActivity.this.finish();
					new Thread(new Runnable() {
						@Override
						public void run() {
							OperationFileHelper.RecursionDeleteFile(new File(
									Environment.getExternalStorageDirectory()
											.getPath() + "/jiayue"));
						}
					}).start();
					break;
				default :
					break;
			}
		}
	};

	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 窗体全屏显示
		getApplicationContext().addActivity(this);
//		setContentView(R.layout.splash);

		// 进度条对话框的初始化
//		int ll_splashId = getResources().getIdentifier("ll_splash", "id",
//				getPackageName());
//		ll_splash = (RelativeLayout) this.findViewById(ll_splashId);
//		ImageView im = (ImageView) findViewById(R.id.imageView2);
//		ObjectAnimator.ofFloat(im, "rotationY", 0.0F, 360.0F).setDuration(2000).start();
//		new Thread(new Runnable() {
//
//			@SuppressLint("NewApi") @Override
//			public void run() {
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
				boolean isFirstIn = preferences.getBoolean("isFirstIn", true);

				Message msg = new Message();
				if (isFirstIn) {
					msg.what = GUIDE;
				} else {
					msg.what = LOADMAIN;
				}
				// msg.what = GUIDE;
				handler.sendMessage(msg);
//			}
//		}).start();
		/*
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { if(!service.isExist()){ Boolean
		 * isSuccess = service.copyBigDataToSD(); if(isSuccess){ //
		 * Toast.makeText(SplashActivity.this, "加载成功", 1).show(); try {
		 * Thread.sleep(2000); } catch (InterruptedException e) {
		 * e.printStackTrace(); } Message msg = new Message(); msg.what =
		 * LOADMAIN; handler.sendMessage(msg); }else{ //
		 * Toast.makeText(SplashActivity.this, "加载失败", 1).show(); } }else{ try {
		 * Thread.sleep(3000); } catch (InterruptedException e) {
		 * e.printStackTrace(); } Message msg = new Message(); msg.what =
		 * LOADMAIN; handler.sendMessage(msg); } } }).start();
		 */}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		getApplicationContext().removeActivity(this);
		super.onDestroy();
	}

}