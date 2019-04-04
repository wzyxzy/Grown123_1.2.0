package com.pndoo.grown123_new;

import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pndoo.grown123_new.download.TestService;
import com.pndoo.grown123_new.download2.entity.DocInfo;
import com.pndoo.grown123_new.download2.utils.DownloadManager;
import com.pndoo.grown123_new.service.MusicPlayerService;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.skytree.epubtest.SPUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

@SuppressWarnings("deprecation")
public class MainActivity extends BaseActivity {


    LocalActivityManager localActivityManager;
    LinearLayout container;
    protected LayoutInflater inflater;
    int current_activity = 1;
    BroadcastReceiver broadcastReciver;
    List<Class<?>> activitys = new ArrayList<Class<?>>();
    private final String JPUSH_KEY = "type";
    private final String JPUSH_VALUE = "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getApplicationContext().addActivity(this);
        setContentView(R.layout.main);
        initView();

    }
    // 初始化界面

    public void initView() {
        activitys.add(BookActivity.class);
//		activitys.add(VipActivity.class);
//		activitys.add(DigitalCommunityActivity.class);
        activitys.add(SettingActivity.class);
//		activitys.add(PayTotalActivity.class);
        activitys.add(ZhiboActivity.class);
        activitys.add(JobActivity.class);
        activitys.add(SourceActivity.class);
        inflater = LayoutInflater.from(this);
        localActivityManager = getLocalActivityManager();
        int layout_container = getResources().getIdentifier("layout_container",
                "id", getPackageName());
        // container = (LinearLayout) findViewById(R.id.layout_container);
        container = (LinearLayout) findViewById(layout_container);
        broadcastReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(MyPreferences.MAIN_BROADCAST)) {
                    changeClickToNormal(current_activity);
                    current_activity = intent.getIntExtra("menu", 1);
                    setContent(current_activity);
                }
            }
        };
        registerReceiver(broadcastReciver, new IntentFilter(
                MyPreferences.MAIN_BROADCAST));
        setContent(current_activity);
        // restartDownload();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getBundleExtra("extra");
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                    if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                        Log.i("MainActivity", "This message has no Extra data");
                        break;
                    }
                    try {
                        JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                        Iterator<String> it = json.keys();

                        while (it.hasNext()) {
                            String myKey = it.next().toString();
                            if (myKey.equals(JPUSH_KEY)) {
                                if (json.optString(myKey).equals(JPUSH_VALUE)) {
                                    changeClickToNormal(current_activity);
                                    setContent(3);
                                    break;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        Log.e("MainActivity", "Get message extra JSON error!");
                    }
                }
            }
        }
    }

    // 点击状态变成正常状态
    private void changeClickToNormal(int current_activity) {
        LinearLayout ll = (LinearLayout) findViewById(getResources()
                .getIdentifier("nav0" + current_activity, "id", "com.pndoo.grown123_new"));
        // ll.setBackgroundResource(R.drawable.bg_tab_normal);
        ImageView iv = (ImageView) ll.getChildAt(0);
        iv.setImageResource(getResources()
                .getIdentifier("tab_normal_image" + current_activity,
                        "drawable", "com.pndoo.grown123_new"));
        TextView tv = (TextView) ll.getChildAt(1);
        tv.setTextColor(this.getResources().getColor(R.color.tab_text_normal));
    }

    // 点击状态变成正常状态
    private void changeNormalToClick(int current_activity) {
        LinearLayout ll = (LinearLayout) findViewById(getResources()
                .getIdentifier("nav0" + current_activity, "id", "com.pndoo.grown123_new"));
        // ll.setBackgroundResource(R.drawable.bg_tab_click);
        ImageView iv = (ImageView) ll.getChildAt(0);
        iv.setImageResource(getResources().getIdentifier(
                "tab_click_image" + current_activity, "drawable", "com.pndoo.grown123_new"));
        TextView tv = (TextView) ll.getChildAt(1);
        tv.setTextColor(this.getResources().getColor(R.color.background));
    }

    // 导航栏按键控制
    public void btnNavOnclick(View v) {

        int id = v.getId();
        if (id == R.id.nav01) {
            changeClickToNormal(current_activity);
            setContent(1);
//		} else if (id == R.id.nav02) {
//			changeClickToNormal(current_activity);
//		} else if (id == R.id.nav03) {
//			changeClickToNormal(current_activity);
//			setContent(3);
        } else if (id == R.id.nav02) {
            changeClickToNormal(current_activity);
            setContent(2);
        } else if (id == R.id.nav03) {
            changeClickToNormal(current_activity);
            setContent(3);
        }else if (id == R.id.nav04) {
            changeClickToNormal(current_activity);
            setContent(4);
        }else if (id == R.id.nav05) {
            changeClickToNormal(current_activity);
            setContent(5);
        }
    }


    public void setContent(int current_activity) {
        // //ActivityUtils.showToast(getBaseContext(), "----------");
        container.removeAllViews();

        container.addView(getLocalActivityManager().startActivity(
                "Module1",
                new Intent(MainActivity.this, activitys
                        .get(current_activity - 1))
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView());
        container.scrollTo(0, 0);
        changeNormalToClick(current_activity);
        this.current_activity = current_activity;
        // Intent intent=new Intent(getBaseContext(),BookActivity00.class);
        // startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if (null != broadcastReciver) {
            this.unregisterReceiver(broadcastReciver);
        }

        getApplicationContext().removeActivity(this);
        super.onDestroy();


    }

    /*
     * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if
     * (keyCode == KeyEvent.KEYCODE_BACK) { new AlertDialog.Builder(this)
     * .setMessage("确认退出吗？") .setTitle("提示") .setPositiveButton("确认", new
     * OnClickListener() {
     *
     * @Override public void onClick(DialogInterface dialog, int which) {
     * dialog.dismiss(); MainActivity.this.finish(); } })
     * .setNegativeButton("取消", new OnClickListener() {
     *
     * @Override public void onClick(DialogInterface dialog, int which) {
     * dialog.dismiss(); } }).create().show(); return false; } else return
     * super.onKeyDown(keyCode, event); }
     */
    long exitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        System.out.println(KeyEvent.KEYCODE_BACK + "--------------------"
                + event.getKeyCode() + "---------------------"
                + event.getAction());

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                SPUtility.putSPString(getApplicationContext(), "isPlay", "false");
                this.stopService(new Intent(this, MusicPlayerService.class));
                finish();
                System.exit(0);
            }
            return true;

        }

        return super.dispatchKeyEvent(event);
    }

    public void restartDownload() {
        DownloadManager m = DownloadManager.getInstance(this);
        m.resetDownloadStatus();
        List<DocInfo> docInfos = m.getListDoing();
        if (docInfos != null) {
            for (DocInfo docInfo : docInfos) {
                downLoadFile(docInfo);
            }
        }
    }

    /**
     * 下载附件
     * <p/>
     * //	 * @param url
     * //	 *            附件的服务器地址
     * //	 * @param saveName
     * //	 *            附件要保存的名字
     * //	 * @param fileName
     * //	 *            附件要现实的名字
     */
    public void downLoadFile(DocInfo d) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(this, TestService.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            d.setUrl(d.getUrl());
            d.setDirectoty(false);
            d.setName(d.getName());
            d.setBookId(d.getBookId());
            d.setBookName(d.getBookName());
            bundle.putSerializable("info", d);
            intent.putExtra("bundle", bundle);
            startService(intent);
        } else {
            ActivityUtils.showToastForFail(MainActivity.this, "未检测到SD卡");
        }
    }


}
