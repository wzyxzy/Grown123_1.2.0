package com.pndoo.grown123_new;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.controller.LoginController;
import com.pndoo.grown123_new.download2.entity.DocInfo;
import com.pndoo.grown123_new.download2.utils.DownloadManager;
import com.pndoo.grown123_new.main.IoC;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.soap.LoginJson;
import com.pndoo.grown123_new.task.BaseTask;
import com.pndoo.grown123_new.task.TaskListener;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.OperationFileHelper;
import com.pndoo.grown123_new.util.SPUtility;

import org.springframework.util.MultiValueMap;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingActivity extends BaseActivity {

    Button btn_header_left;
    ImageButton btn_header_right;
    // TextView tv_header_title;
    // TextView tv_cacheSize;
    // ImageView iv_update;
    private ToggleButton toggleButton;
    private TextView tv_username;
    private TextView tv_publishName;
    private TextView tv_version;
    LoginController loginController;
    private DownloadManager manager;
    private List<DocInfo> infos;
    private TaskManager tm;
    Map<String, String> params;
    private LinearLayout layout_jihua;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationContext().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);
        initTitle();
        initViews();
        manager = DownloadManager.getInstance(this);
        tm = getApplicationContext().getTaskManager();
        loginController = IoC.getInstance(LoginController.class);
        infos = manager.getListDone();

    }

    private void initViews() {
        tv_publishName = (TextView) findViewById(R.id.tv_publishName);
        tv_publishName.setText("设置");
        toggleButton = (ToggleButton) findViewById(R.id.online);
        toggleButton.setChecked(Boolean.parseBoolean(SPUtility.getSPString(this, "switchKey")));
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_username.setText(UserUtil.getInstance(this).getUserName());
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtility.putSPString(SettingActivity.this, "switchKey", String.valueOf(isChecked));
            }
        });
        tv_version = (TextView) findViewById(R.id.version);
        tv_version.setText("当前版本:" + getClientVersion());

        layout_jihua = (LinearLayout) findViewById(R.id.layout_jihua);
//        if (UserUtil.getInstance(this).getSubscibed() == 1)
//            layout_jihua.setVisibility(View.VISIBLE);
//        else
            layout_jihua.setVisibility(View.GONE);


        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img)
                // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_img)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_img)
                // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();
        ImageView iv_photo = (ImageView)findViewById(R.id.photo);
        String url = "file://"+ActivityUtils.getSDPath()+"/photo.png";
        ImageLoader.getInstance().displayImage(url, iv_photo, options);
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
//		LinearLayout ll_header = (LinearLayout) this.getParent().findViewById(R.id.layout_header);
//		// getParent().findViewById(R.id.prent).setBackgroundColor(getResources().getColor(R.color.setting_background));
//		if (null != ll_header) {
//			ll_header.setVisibility(View.GONE);
//		}
    }

    public void teacherBind(View v) {
        Intent intent = new Intent(this, TeacherBindLoginActivity.class);
        startActivity(intent);

//		Intent intent = new Intent(this, JDWebViewActivity.class);
//		startActivity(intent);

    }

    public void examSystem(View v) {
        Intent intent = new Intent(this, TeacherQuizActivity.class);
        startActivity(intent);
    }

    public void onGoodsClick(View v) {
        Intent intent = new Intent(this, GoodsListActivity.class);
        startActivity(intent);
    }

    public void onVipClick(View v){
        Intent intent = new Intent(this, VipActivity.class);
        startActivity(intent);
    }

    public void btnCallPhone(View v){
        Uri uri1 = Uri.parse("tel:010-51686028");
        Intent intent = new Intent(Intent.ACTION_CALL, uri1);
        startActivity(intent);
    }



    @SuppressWarnings("deprecation")
    public void btnExitClick(View v) {
        DialogUtils.showMyDialog(this, MyPreferences.SHOW_BUTTON_DIALOG, "退出登录", "取        消", new OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogUtils.dismissMyDialog();
                cancel();
                SPUtility.clear(SettingActivity.this);
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                SettingActivity.this.finish();
            }

        });
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = DialogUtils.dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        lp.height = (int) (display.getHeight()); // 设置高度
        DialogUtils.dialog.getWindow().setAttributes(lp);
    }


    private String getClientVersion() {
        try {
            PackageManager packageManager = this.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "100";
        }
    }

    public void cancel() {
        String userId = UserUtil.getInstance(SettingActivity.this).getUserId();
        params = new HashMap<String, String>();
        params.put("userId", userId);
        Log.i("MMMM================", "jll" + userId + params);
        tm.createNewTask(new TaskListener() {

            @Override
            public void onProgressUpdate(BaseTask task, Object param) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPreExecute(BaseTask task) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPostExecute(BaseTask task, String errorMsg) {
                DialogUtils.dismissMyDialog();
                if (errorMsg != null) {// 获取数据出现异常
                    DialogUtils.showMyDialog(SettingActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "退出失败", errorMsg, null);
                } else {
                    LoginJson loginJson = loginController.getModel();

                    Log.i("OOOOO=====", "loginJson ： " + loginJson + "errorMsg" + errorMsg);

                    Log.i("RRRRR-------", loginJson.getCode() + loginJson.getCodeInfo());
                }

            }

            @Override
            public String onDoInBackground(BaseTask task, MultiValueMap<String, String> param) {
                String errorMsg = null;
                try {
                    Log.i("EEEE------", "param:   " + param);
                    errorMsg = loginController.logout(param);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return errorMsg;
            }

            @Override
            public void onCancelled(BaseTask task) {
                // TODO Auto-generated method stub

            }

            @Override
            public String getName() {
                // TODO Auto-generated method stub
                return null;
            }
        }).execute(params);
    }

    /**
     * 清除数据
     *
     * @param v
     */
    public void clearCache(View v) {
        DialogUtils.showMyDialog(this, MyPreferences.SHOW_CONFIRM_DIALOG, "清除数据", "<font color='red'>" + "清除后将无法恢复，确定要清除所有数据？" + "</font>", new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < infos.size(); i++) {
                    manager.cancel(infos.get(i));
                }
                deleteFilesByDirectory(getCacheDir());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OperationFileHelper.RecursionDeleteFile(new File(Environment.getExternalStorageDirectory().getPath() + "/jiayue"));
                        Looper.prepare();
                        ActivityUtils.showToastForSuccess(SettingActivity.this, "数据清除完毕");
                        Looper.loop();
                    }
                }).start();
                DialogUtils.dismissMyDialog();
            }
        });
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                boolean delete = item.delete();
                Log.i("flag", item.getPath() + ":" + delete);
            }
        }
    }

    /**
     * baby信息
     *
     * @param view
     */
    public void onbabyinfoClick(View view) {
        startActivity(new Intent(this, BabyInfoActivity.class));
    }
    /**
     * 班级信息
     *
     * @param view
     */
    public void onClassDetailClick(View view) {
        startActivity(new Intent(this, ClassDetailActivity.class));
    }



    /**
     * 同步管理
     *
     * @param v
     */
    public void synManage(View v) {
        startActivity(new Intent(this, SynManageActivity.class));
    }

    /**
     * 我的信息
     *
     * @param v
     */
    public void userManage(View v) {
        startActivity(new Intent(this, UserInfoActivity.class));
    }

//	/**
//	 * 版本更新
//	 * 
//	 * @param v
//	 */
//	public void updateApp(View v) {
//
//		startActivity(new Intent(this, UpdateActivity.class));
//	}

    // private String getClientVersion() {
    // try {
    // PackageManager packageManager = this.getPackageManager();
    // PackageInfo packInfo = packageManager.getPackageInfo(
    // getPackageName(), 0);
    // return packInfo.versionName;
    // } catch (NameNotFoundException e) {
    // return "100";
    // }
    // }

    /**
     * 帮助与反馈
     *
     * @param v
     */
    public void helpAndFeedback(View v) {
        startActivity(new Intent(this, FeedbackActivity.class));
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        getApplicationContext().removeActivity(this);
        super.onDestroy();
    }

	/*
     * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if
	 * (keyCode == KeyEvent.KEYCODE_BACK) { new AlertDialog.Builder(this)
	 * .setMessage("确认退出吗？") .setTitle("提示") .setPositiveButton("确认", new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) {
	 * dialog.dismiss(); SettingActivity.this.getParent().finish(); } })
	 * .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) {
	 * dialog.dismiss(); } }).create().show(); return true; } else return
	 * super.onKeyDown(keyCode, event); }
	 */
}
