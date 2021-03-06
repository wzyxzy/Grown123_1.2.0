package com.pndoo.grown123_new;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pndoo.grown123_new.controller.BookController;
import com.pndoo.grown123_new.download.DownloadProgressListener;
import com.pndoo.grown123_new.download.FileDownloader;
import com.pndoo.grown123_new.dto.base.Update;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.UpdateInfo;

public class CastUpdateActivity extends BaseActivity {
	UpdateInfo info;
	String client_version;
	LinearLayout ll_container;
	LinearLayout ll_content;
	ProgressBar progressBar;
	ProgressBar pb_syn;
	TextView tv_errorMsg;
	String errorMsg;
	TextView tv_updateInfo;
	Button btn_update;
	Update update;
	BookController bookController;
	BroadcastReceiver mHomeKeyEventReceiver;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0 :
					addContent();
					tv_updateInfo.setText("当前已是最新版本:加阅" + client_version);
					btn_update.setVisibility(View.GONE);
					break;
				case 1 :
					addContent();
					tv_updateInfo.setText("已有新版本：加阅" + "");
					btn_update.setVisibility(View.VISIBLE);
					break;
				case 2 :
					addTextViewForError("未连上服务器");
					break;
				case 3 :
					pb_syn.setProgress(msg.getData().getInt("size"));
					float result = (float) pb_syn.getProgress()
							/ (float) pb_syn.getMax();
					 int num = (int)(result * 100);
					 btn_plan.setText("下载中..."+num+"%");
					Log.i("progress", ""+num);
					if (pb_syn.getProgress() == pb_syn.getMax()) {
//						pb_syn.setVisibility(View.GONE);
						btn_plan.setText("下载完成");
						File file = new File(ActivityUtils.getSDPath(),"jiayue.apk");
						installApk(file);
					}
					break;

				case -1 :
					ActivityUtils.showToastForFail(CastUpdateActivity.this,
							"下载失败");
					break;
				default :
					break;
			}
		};
	};
	private Thread t1;
	private RelativeLayout rl_pb;
	private Button btn_plan;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getApplicationContext().addActivity(this);
		setContentView(R.layout.cast_update);
		initView();
		loadBroadCastReceiver();
	}

	 
	private void loadBroadCastReceiver() {
		mHomeKeyEventReceiver = new BroadcastReceiver() {  
	        String SYSTEM_REASON = "reason";  
	        String SYSTEM_HOME_KEY = "homekey";  
	        String SYSTEM_HOME_KEY_LONG = "recentapps";  
	           
	        @Override  
	        public void onReceive(Context context, Intent intent) {  
	            String action = intent.getAction();  
	            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {  
	                String reason = intent.getStringExtra(SYSTEM_REASON);  
	                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {  
	                     //表示按了home键,程序到了后台  
	                    Toast.makeText(getApplicationContext(), "已在后台进行下载...", 1).show();  
	                }else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){  
	                    //表示长按home键,显示最近使用的程序列表  
	                } 
	            }   
	        }  
	    };  
	    registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
	}


	public void initView() {
		ll_container = (LinearLayout) findViewById(R.id.ll_container);
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		tv_updateInfo = (TextView) findViewById(R.id.tv_updateInfo);
		btn_update = (Button) findViewById(R.id.btn_update);
		pb_syn = (ProgressBar) findViewById(R.id.pb_syn);
		rl_pb = (RelativeLayout) findViewById(R.id.rl_pb);
		btn_plan = (Button) findViewById(R.id.btn_plan);
		progressBar = new ProgressBar(this);
		progressBar.setLayoutParams(new LayoutParams(100, 100));
		tv_errorMsg = new TextView(this);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		client_version = getClientVersion();
		update = (Update) getIntent().getExtras().getSerializable("update");
		Log.i("CastUpdateActivity", "update=" + update.toString());
		addProgressBar();
		new Thread(new CheckVersionTask()).start();
		
	}

	/**
	 * 显示错误信息
	 * 
	 * @param errorMsg
	 */
	public void addTextViewForError(String errorMsg) {
		tv_errorMsg.setText(errorMsg);
		tv_errorMsg.setTextColor(this.getResources().getColor(R.color.grey01));
		tv_errorMsg.setTextSize(22);
		if (ll_container.getChildCount() > 0) {
			ll_container.removeAllViews();
		}
		ll_container.addView(tv_errorMsg);
		ll_container.setGravity(Gravity.CENTER);
	}

	/**
	 * 显示进度条对话框
	 * 
	 * @param errorMsg
	 */
	public void addProgressBar() {
		if (ll_container.getChildCount() > 0) {
			ll_container.removeAllViews();
		}
		ll_container.addView(progressBar);
		ll_container.setGravity(Gravity.CENTER);
	}

	/**
	 * 显示内容
	 * 
	 * @param errorMsg
	 */
	public void addContent() {
		if (ll_container.getChildCount() > 0) {
			ll_container.removeAllViews();
		}
		ll_container.addView(ll_content);
		ll_container.setGravity(Gravity.TOP);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			if (t1!=null) {
				t1.interrupt();
			}
			boolean deleteState = ActivityUtils.deleteBookFormSD(Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+"jiayue"+File.separator+"jiayue.apk");
			Toast.makeText(getApplicationContext(), deleteState?"当前更新已取消...":"更新失败", 1).show();
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}


	/**
	* <p>Title</p>: updateApp
	* Description: 更新app对话框
	* @param v
	*/
	public void updateApp(View v) {
		DialogUtils.showMyDialog(this, MyPreferences.SHOW_CONFIRM_DIALOG,
				"版本更新", update.getDesc().replace("$", "<br/>"),
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						rl_pb.setVisibility(View.VISIBLE);
//						btn_update.setVisibility(View.GONE);
						String path = update.getUrl();
						
						Uri uri = Uri.parse(path);
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						startActivity(intent);
						
//						if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//							download(ActivityUtils.getSDPath(), path);
//						} else {
//							ActivityUtils.showToastForFail(CastUpdateActivity.this, "未检测到SD卡");
//						}
						DialogUtils.dismissMyDialog();
						finish();
					}
				});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		getApplicationContext().removeActivity(this);
		if (mHomeKeyEventReceiver!=null) {
		this.unregisterReceiver(mHomeKeyEventReceiver);
		}
		super.onDestroy();
	}

	/**
	 * 检查服务器获取 最新的版本信息
	 * 
	 * @author Administrator
	 * 
	 */
	private class CheckVersionTask implements Runnable {

		public void run() {
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	}

	/**
	 * 获取当前程序的版本号
	 * 
	 * @return
	 */
	private String getClientVersion() {
		try {
			PackageManager packageManager = this.getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			return packInfo.versionName;
		} catch (NameNotFoundException e) {
			return "100";
		}
	}

	// 完成文件的下载
	private void download(File saveDir, String path) {
		t1 = new Thread(new DownloadFileTask(saveDir, path));
		t1.start();
	}

	private final class DownloadFileTask implements Runnable {
		private File saveDir;
		private String path;

		public DownloadFileTask(File saveDir, String path) {
			this.saveDir = saveDir;
			this.path = path;
		}

		public void run() {
			try {
				FileDownloader loader = new FileDownloader(
						getApplicationContext(), path, saveDir, 1,"jiayue.apk");
				pb_syn.setMax(loader.getFileSize());// 设置进度条的最大刻度为文件长度
				loader.download(new DownloadProgressListener() {
					public void onDownloadSize(int size) {
						Message msg = new Message();
						msg.what = 3;
						msg.getData().putInt("size", size);
						handler.sendMessage(msg);
					
					}
				});
			} catch (Exception e) {
				handler.sendMessage(handler.obtainMessage(-1));
			}
		}

	}

	/**
	 * 安装下载成功的apk
	 * 
	 * @param file
	 *            apk的文件对象
	 */
	protected void installApk(File file) {
		Intent intent = new Intent();
		// 查看的意图 (动作)
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
		finish();
		startActivity(intent);
	}

}
