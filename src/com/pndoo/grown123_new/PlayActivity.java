package com.pndoo.grown123_new;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.adapter.GoodsSettingAdapter;
import com.pndoo.grown123_new.adapter.MyViewPagerAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.dto.base.GiftBean;
import com.pndoo.grown123_new.dto.base.GoodsAddressAllBean;
import com.pndoo.grown123_new.dto.base.GoodsDetailBean;
import com.pndoo.grown123_new.dto.base.GoodsFapiaoAllBean;
import com.pndoo.grown123_new.dto.base.GoodsSettingListBean;
import com.pndoo.grown123_new.dto.base.OrderBean;
import com.pndoo.grown123_new.dto.base.ZhiboInfoBean;
import com.pndoo.grown123_new.fragment.Fragment_Good1;
import com.pndoo.grown123_new.fragment.Fragment_Good2;
import com.pndoo.grown123_new.fragment.Fragment_Good3;
import com.pndoo.grown123_new.fragment.Fragment_Good4;
import com.pndoo.grown123_new.fragment.Fragment_Good5;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.rest.LastOrNextListener;
import com.pndoo.grown123_new.rest.OnRefreshAdapterListener;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DensityUtil;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.GoodsDetailUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.SPUtility;
import com.pndoo.grown123_new.view.CustomViewpager;
import com.pndoo.grown123_new.view.MyRatingBar;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static com.pndoo.grown123_new.util.DialogUtils.dismissMyDialog;

public class PlayActivity extends FragmentActivity implements OnClickListener, LastOrNextListener, OnRefreshAdapterListener, ITXLivePlayListener {
    private final String TAG = "PlayActivity";
    private TextView title;
    private LinearLayout layout_title, layout, layout_viewpager, layout_webchat;
    private RelativeLayout layout_listview;
    private ZhiboInfoBean bean;
    private WebView mWebView;

    // private SwipeRefreshLayout refresh_view;
    private Button btn_mine, btn_all, btn_goods, btn_setting;
    private boolean isMine = false;
    private Context context = this;
    private String userId;
    private EditText et_content;
    private final int PLUS = 6;
    private int sum = PLUS;

    private final int CHOOSE_CHAT = 0;
    private final int CHOOSE_GOODS = 1;
    private final int CHOOSE_SETTING = 2;

    private int choose = CHOOSE_CHAT;

    private CustomViewpager viewpager;
    private MyViewPagerAdapter myAdapter;

    private GoodsSettingAdapter settingAdapter;
    private LinearLayout headview;
    private Fragment f1, f2, f3, f4, f5;
    private ListView listView;
    private String gift = null;
    private int giftId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 界面唤醒状态
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
        setContentView(R.layout.activity_play);

        bean = (ZhiboInfoBean) getIntent().getSerializableExtra("bean");
        if (bean == null || bean.getCode().equals("FAIL") || bean.getData() == null) {
            ActivityUtils.showToastForFail(this, "直播信息获取失败！");
            finish();
        }
        getDefaultData();
        initChat();
        initVideo();
        initViewPager();
        getGiftName();
        record(LIVE_ENTER);
    }

    //获取退出时评价 礼物
    private void getGiftName() {
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.GET_VIDEO_GIFT);
        params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());
        params.addQueryStringParameter("courseName", bean.getData().getCourseName());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<GiftBean>() {
                }.getType();
                GiftBean bean = gson.fromJson(s, type);
                if (bean != null && bean.getCode().equals("SUCCESS") && bean.getData() != null) {
                    gift = bean.getData().getPresentName();
                    giftId = bean.getData().getPresentId();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                DialogUtils.dismissMyDialog();
                ActivityUtils.showToast(context, throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private final int CMD_REFRESH = 0x01;
    private final int CMD_TISHI = 0x02;
    private final int CMD_XIADAN = 0x03;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CMD_TISHI:
                    dismissMyDialog();
                    ActivityUtils.showToastForFail(PlayActivity.this, "抱歉，未获取到资源，请退出后重试，谢谢");
                    break;
                case CMD_XIADAN:
                    viewpager.setCurrentItem(0);
                    break;
                default:
                    break;
            }
        }
    };

    private void initChat() {
        // TODO Auto-generated method stub
        userId = UserUtil.getInstance(this).getUserId();
        btn_mine = (Button) findViewById(R.id.button1);
        btn_all = (Button) findViewById(R.id.button2);
        btn_mine.setOnClickListener(this);
        btn_all.setOnClickListener(this);
        et_content = (EditText) findViewById(R.id.editText1);

        layout_webchat = (LinearLayout) findViewById(R.id.layout_webchat);
        mWebView = (WebView) findViewById(R.id.webchat);

        listView = (ListView) findViewById(R.id.listview);

        settingAdapter = new GoodsSettingAdapter(this, null, bean.getData().getCourseId(), this);
        listView.setAdapter(settingAdapter);

        initWebChat();

    }

    private void initWebChat() {
        WebSettings ws = mWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // 是否允许缩放
        ws.setBuiltInZoomControls(true);
        ws.setSupportZoom(true);
        mWebView.setWebChromeClient(new MyWebChromeClient());

        mWebView.setWebViewClient(new MyWebViewClient(this));

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (url != null && url.startsWith("http://"))
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        String groudId = getIntent().getStringExtra("groudId");
        Log.d(TAG, "groudId===" + groudId);
        groudId = groudId.replace("#", "%23");
        String url = "http://101.200.179.47:8080/AVChatRoom/AVChatRoomAction?UserId=" + UserUtil.getInstance(this).getSurname() + "&groupId=" + groudId + "&expert=" + getIntent().getStringExtra("expert");
        //http:
        // 182.92.103.51:8080/AVChatRoom/AVChatRoomAction?UserId=王亮&groupId=@TGS%23aF7XTEEEI&expert=成长123
        // for(int i = 0;i<40;i++){
        mWebView.loadUrl(url);
        // }
        Log.d(TAG, url);
    }

    private void initViewPager() {
        // refresh_view = (SwipeRefreshLayout)
        // findViewById(R.id.swipeRefreshLayout);
        // refresh_view.setOnRefreshListener(this);
        // refresh_view.setColorSchemeResources(android.R.color.holo_blue_light,
        // android.R.color.holo_red_light, android.R.color.holo_orange_light,
        // android.R.color.holo_green_light);
        btn_goods = (Button) findViewById(R.id.button3);
        btn_goods.setOnClickListener(this);
        btn_setting = (Button) findViewById(R.id.button4);
        btn_setting.setOnClickListener(this);
        layout_viewpager = (LinearLayout) findViewById(R.id.layout_viewpager);
        layout_listview = (RelativeLayout) findViewById(R.id.layout_listview);
        viewpager = (CustomViewpager) findViewById(R.id.viewpager);

        f1 = new Fragment_Good1();
        ((Fragment_Good1) f1).setLastOrNextListener(this);
        Bundle b = new Bundle();
        b.putInt("courseId", bean.getData().getCourseId());
        f1.setArguments(b);
        f2 = new Fragment_Good2();
        ((Fragment_Good2) f2).setLastOrNextListener(this);
        f3 = new Fragment_Good3();
        ((Fragment_Good3) f3).setLastOrNextListener(this);
        f4 = new Fragment_Good4();
        ((Fragment_Good4) f4).setLastOrNextListener(this);
        f5 = new Fragment_Good5();
        ((Fragment_Good5) f5).setLastOrNextListener(this);
        List<Fragment> list = new LinkedList<>();
        list.add(f1);
        list.add(f2);
        list.add(f3);
        list.add(f4);
        list.add(f5);
        myAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(myAdapter);

        headview = (LinearLayout) findViewById(R.id.head);

        Log.d(TAG, "getUserStatus===" + UserUtil.getInstance(this).getUserStatus());
        if (UserUtil.getInstance(this).getUserStatus() != 2)
            btn_setting.setVisibility(View.GONE);
        else
            btn_setting.setVisibility(View.VISIBLE);
    }

    // 获取默认数据并保存
    private void getDefaultData() {
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.ZHIBO_GOODS_GETADDRESS);
        params.addQueryStringParameter("user.userId", UserUtil.getInstance(this).getUserId());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<GoodsAddressAllBean>() {
                }.getType();
                GoodsAddressAllBean bean = gson.fromJson(s, type);
                if (bean != null && bean.getCode().equals("SUCCESS")) {
                    GoodsDetailBean detail = GoodsDetailUtils.getInstance(PlayActivity.this).getBean();
                    if (!TextUtils.isEmpty(bean.getData().getAddress()))
                        detail.setAddress(bean.getData().getAddress());
                    // else
                    // detail.setAddress(UserUtil.getInstance(PlayActivity.this).getAddress1());
                    if (!TextUtils.isEmpty(bean.getData().getName()))
                        detail.setName(bean.getData().getName());
                    // else
                    // detail.setName(UserUtil.getInstance(PlayActivity.this).getParents());
                    if (!TextUtils.isEmpty(bean.getData().getTelephone()))
                        detail.setTelephone(bean.getData().getTelephone());
                    else
                        detail.setTelephone(UserUtil.getInstance(PlayActivity.this).getUserName());
                    if (!TextUtils.isEmpty(bean.getData().getId() + ""))
                        detail.setReceiverId(bean.getData().getId());

                    GoodsDetailUtils.getInstance(PlayActivity.this).setBean(detail);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                DialogUtils.dismissMyDialog();
                ActivityUtils.showToast(context, throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
        org.xutils.http.RequestParams params1 = new org.xutils.http.RequestParams(Preferences.ZHIBO_GOODS_GETFAPIAO);
        params1.addQueryStringParameter("user.userId", UserUtil.getInstance(this).getUserId());

        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<GoodsFapiaoAllBean>() {
                }.getType();
                GoodsFapiaoAllBean bean = gson.fromJson(s, type);

                if (bean != null && bean.getCode().equals("SUCCESS")) {
                    GoodsDetailBean detail = GoodsDetailUtils.getInstance(PlayActivity.this).getBean();
                    if (!TextUtils.isEmpty(bean.getData().getId() + ""))
                        detail.setFapiaoId(bean.getData().getId());
                    if (!TextUtils.isEmpty(bean.getData().getInvoiceTitle()))
                        detail.setFapiao(bean.getData().getInvoiceTitle());

                    GoodsDetailUtils.getInstance(PlayActivity.this).setBean(detail);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                DialogUtils.dismissMyDialog();
                ActivityUtils.showToast(context, throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    boolean isLoadMore = false;

    // time=0为全部数据
    // private void loadData(long time) {
    // isLoadMore = false;
    // RequestParams params = new RequestParams();
    // params.put("courseId", bean.getData().getCourseId());
    // // params.put("userId", userId);
    // if (time != 0) {
    // isLoadMore = true;
    // params.put("time", list_all.get(list_all.size() -
    // 1).getAddTime().getTime());
    // Log.d(TAG, "time==========" + list_all.get(list_all.size() -
    // 1).getAddTime().getTime());
    // }
    //
    // // Log.d(TAG, Preferences.GET_CHATLIST + "&courseId=" +
    // bean.getData().getCourseId());
    //
    // HttpUtil.post(Preferences.GET_CHATLIST, params, new
    // AsyncHttpResponseHandler() {
    //
    // @Override
    // public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
    // // TODO Auto-generated method stub
    // String s = new String(arg2);
    // Log.d(TAG, s);
    // Gson gson = new Gson();
    // java.lang.reflect.Type type = new TypeToken<ChatAllBean>() {
    // }.getType();
    // chatbean = gson.fromJson(s, type);
    //
    // mHandler.sendMessage(mHandler.obtainMessage(CMD_REFRESH, isLoadMore));
    // isRefresh = false;
    // }
    //
    // @Override
    // public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable
    // arg3) {
    // // TODO Auto-generated method stub
    // ActivityUtils.showToastForFail(context, "获取信息失败");
    // isRefresh = false;
    // // if(refresh_view.isRefreshing())
    // // refresh_view.setRefreshing(false);
    // }
    // });
    // }

    private void loadSettingData() {

        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.ZHIBO_GOODS_SETTINGLIST);
        params.addQueryStringParameter("user.userId", UserUtil.getInstance(this).getUserId());
        params.addQueryStringParameter("courseId", bean.getData().getCourseId() + "");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<GoodsSettingListBean>() {
                }.getType();
                GoodsSettingListBean bean = gson.fromJson(s, type);

                if (bean != null && bean.getCode().equals("SUCCESS")) {
                    settingAdapter.setList(bean.getData());
                    settingAdapter.notifyDataSetChanged();
                } else {
                    ActivityUtils.showToastForFail(context, "获取信息失败");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ActivityUtils.showToastForFail(context, "获取信息失败");
                isRefresh = false;
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /***********************************************
     * video
     ********************************************************************/

    private String mRtmpUrl;

    private TXLivePlayer mLivePlayer = null;
    private boolean mVideoPlay;
    private TXCloudVideoView mPlayerView;
    private ImageView mLoadingView;
    private Button mBtnPlay;
    private Button mBtnRenderRotation;
    private SeekBar mSeekBar;
    private TextView mTextDuration;
    private TextView mTextStart;
    static final int ACTIVITY_TYPE_PUBLISH = 1;
    static final int ACTIVITY_TYPE_LIVE_PLAY = 2;
    static final int ACTIVITY_TYPE_VOD_PLAY = 3;

    protected int mActivityType = ACTIVITY_TYPE_LIVE_PLAY;

    private static final int CACHE_STRATEGY_FAST = 1; // 极速
    private static final int CACHE_STRATEGY_SMOOTH = 2; // 流畅
    private static final int CACHE_STRATEGY_AUTO = 3; // 自动

    private static final int CACHE_TIME_FAST = 1;
    private static final int CACHE_TIME_SMOOTH = 5;

    private static final int CACHE_TIME_AUTO_MIN = 5;
    private static final int CACHE_TIME_AUTO_MAX = 10;

    private int mCacheStrategy = 0;

    private int mCurrentRenderMode;
    private int mCurrentRenderRotation;

    private long mTrackingTouchTS = 0;
    private boolean mStartSeek = false;
    private boolean mVideoPause = false;
    private int mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
    private TXLivePlayConfig mPlayConfig;
    private long mStartPlayTS = 0;

    private LinearLayout layout_hidden;

    private void initVideo() {
        // TODO Auto-generated method stub
        title = (TextView) findViewById(R.id.title);
        layout_title = (LinearLayout) findViewById(R.id.layout_title);
        layout = (LinearLayout) findViewById(R.id.layout);
        title.setText(bean.getData().getCourseName());
        layout_hidden = (LinearLayout) findViewById(R.id.layout_hidden);
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_view);
        mLoadingView = (ImageView) findViewById(R.id.loadingImageView);
        layout = (LinearLayout) findViewById(R.id.layout);

        mVideoPlay = false;

        if (mLivePlayer == null) {
            mLivePlayer = new TXLivePlayer(this);
        }

        mBtnPlay = (Button) findViewById(R.id.btnPlay);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);

        mCurrentRenderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
        mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;

        mPlayConfig = new TXLivePlayConfig();

        mRtmpUrl = "";

        mTextDuration = (TextView) findViewById(R.id.duration);
        mTextStart = (TextView) findViewById(R.id.play_start);
        mTextDuration.setTextColor(Color.rgb(255, 255, 255));
        mTextStart.setTextColor(Color.rgb(255, 255, 255));

        this.setCacheStrategy(CACHE_STRATEGY_AUTO);

        View progressGroup = findViewById(R.id.play_progress);

        if (ActivityUtils.isNetworkAvailable(this)) {
            if (ActivityUtils.is3rd(this) && Boolean.parseBoolean(SPUtility.getSPString(this, "switchKey"))) {

            } else if (ActivityUtils.is3rd(this) && !Boolean.parseBoolean(SPUtility.getSPString(this, "switchKey"))) {
                DialogUtils.showMyDialog(this, MyPreferences.SHOW_ERROR_DIALOG, "网络未连接", "请在wifi状态下观看，或在设置中设置3G/4G网络", new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                });
            } else {

            }
        } else {
            DialogUtils.showMyDialog(this, MyPreferences.SHOW_ERROR_DIALOG, "网络不给力", "请检查网络！", new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
        }


        Log.e(TAG, "getRtmp_downstream_address====" + bean.getData().getRtmp_downstream_address());
        if (bean.getData() != null && !TextUtils.isEmpty(bean.getData().getRtmp_downstream_address())) {
            mRtmpUrl = bean.getData().getRtmp_downstream_address();
        } else {
            ActivityUtils.showToastForFail(PlayActivity.this, "抱歉，未获取到资源，请退出后重试，谢谢");
            return;
        }


        mBtnPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click playbtn isplay:" + mVideoPlay + " ispause:" + mVideoPause + " playtype:" + mPlayType);
                if (mVideoPlay) {
                    if (mPlayType == TXLivePlayer.PLAY_TYPE_VOD_FLV || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_HLS || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_MP4) {
                        if (mVideoPause) {
                            mLivePlayer.resume();
                            mBtnPlay.setBackgroundResource(R.drawable.play_pause);
                        } else {
                            mLivePlayer.pause();
                            mBtnPlay.setBackgroundResource(R.drawable.play_start);
                        }
                        mVideoPause = !mVideoPause;

                    } else {
                        stopPlayRtmp();
                        mVideoPlay = !mVideoPlay;
                    }

                } else {
                    if (startPlayRtmp()) {
                        mVideoPlay = !mVideoPlay;
                    }
                }
            }
        });

        // 横屏|竖屏
        // mBtnRenderRotation = (Button) findViewById(R.id.btnOrientation);
        // mBtnRenderRotation.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // if (mLivePlayer == null) {
        // return;
        // }
        //
        // if (mCurrentRenderRotation ==
        // TXLiveConstants.RENDER_ROTATION_PORTRAIT) {
        // mBtnRenderRotation.setBackgroundResource(R.drawable.portrait);
        // mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;
        // mPlayerView.setLayoutParams(new
        // RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
        // } else if (mCurrentRenderRotation ==
        // TXLiveConstants.RENDER_ROTATION_LANDSCAPE) {
        // mBtnRenderRotation.setBackgroundResource(R.drawable.landscape);
        // mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
        // mPlayerView.setLayoutParams(new
        // RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,260*3));
        // }
        //
        // mLivePlayer.setRenderRotation(mCurrentRenderRotation);
        // }
        // });

        // //平铺模式
        // mBtnRenderMode = (Button) view.findViewById(R.id.btnRenderMode);
        // mBtnRenderMode.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // if (mLivePlayer == null) {
        // return;
        // }
        //
        // if (mCurrentRenderMode ==
        // TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN) {
        // mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        // mBtnRenderMode.setBackgroundResource(R.drawable.fill_mode);
        // mCurrentRenderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
        // } else if (mCurrentRenderMode ==
        // TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION) {
        // mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        // mBtnRenderMode.setBackgroundResource(R.drawable.adjust_mode);
        // mCurrentRenderMode = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;
        // }
        // }
        // });


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean bFromUser) {
                mTextStart.setText(String.format("%02d:%02d", progress / 60, progress % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mStartSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mLivePlayer != null) {
                    mLivePlayer.seek(seekBar.getProgress());
                }
                mTrackingTouchTS = System.currentTimeMillis();
                mStartSeek = false;
            }
        });


        // 直播不需要进度条，点播不需要缓存策略
        if (mActivityType == ACTIVITY_TYPE_LIVE_PLAY) {
            progressGroup.setVisibility(View.GONE);
        } else if (mActivityType == ACTIVITY_TYPE_VOD_PLAY) {
        }

        if (mVideoPlay) {
            if (mPlayType == TXLivePlayer.PLAY_TYPE_VOD_FLV || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_HLS || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_MP4) {
                if (mVideoPause) {
                    mLivePlayer.resume();
                    mBtnPlay.setBackgroundResource(R.drawable.play_pause);
                } else {
                    mLivePlayer.pause();
                    mBtnPlay.setBackgroundResource(R.drawable.play_start);
                }
                mVideoPause = !mVideoPause;

            } else {
                stopPlayRtmp();
                mVideoPlay = !mVideoPlay;
            }

        } else {
            if (startPlayRtmp()) {
                mVideoPlay = !mVideoPlay;
            }
        }
    }

    public void btnBack(View view) {
        exit();
    }

    private void exit() {
        if (TextUtils.isEmpty(gift) || giftId == 0) {
            finish();
        } else {
            showFeedDialog();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                layout_title.setVisibility(View.VISIBLE);
                layout.setPadding(DensityUtil.dip2px(PlayActivity.this, 13), DensityUtil.dip2px(PlayActivity.this, 7), DensityUtil.dip2px(PlayActivity.this, 13), DensityUtil
                        .dip2px(PlayActivity.this, 7));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    public void setCacheStrategy(int nCacheStrategy) {
        if (mCacheStrategy == nCacheStrategy)
            return;
        mCacheStrategy = nCacheStrategy;

        switch (nCacheStrategy) {
            case CACHE_STRATEGY_FAST:
                mPlayConfig.setAutoAdjustCacheTime(true);
                mPlayConfig.setMaxAutoAdjustCacheTime(CACHE_TIME_FAST);
                mPlayConfig.setMinAutoAdjustCacheTime(CACHE_TIME_FAST);
                mLivePlayer.setConfig(mPlayConfig);
                break;

            case CACHE_STRATEGY_SMOOTH:
                mPlayConfig.setAutoAdjustCacheTime(false);
                mPlayConfig.setCacheTime(CACHE_TIME_SMOOTH);
                mLivePlayer.setConfig(mPlayConfig);
                break;

            case CACHE_STRATEGY_AUTO:
                mPlayConfig.setAutoAdjustCacheTime(true);
                mPlayConfig.setMaxAutoAdjustCacheTime(CACHE_TIME_AUTO_MAX);
                mPlayConfig.setMinAutoAdjustCacheTime(CACHE_TIME_AUTO_MIN);
                mLivePlayer.setConfig(mPlayConfig);
                break;

            default:
                break;
        }
    }

    public void onConfigurationChanged(Configuration paramConfiguration) { // 响应系统方向改变
        if (paramConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE) { // 横屏
            layout_title.setVisibility(View.GONE);
            layout_hidden.setVisibility(View.GONE);
            layout.setPadding(0, 0, 0, 0);
            mPlayerView.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        } else {
            layout_title.setVisibility(View.VISIBLE);
            layout_hidden.setVisibility(View.VISIBLE);
            layout.setPadding(DensityUtil.dip2px(PlayActivity.this, 13), DensityUtil.dip2px(PlayActivity.this, 7), DensityUtil.dip2px(PlayActivity.this, 13), DensityUtil.dip2px(PlayActivity.this, 7));
            mPlayerView.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this, 180)));
        }
        super.onConfigurationChanged(paramConfiguration); // 必有，否则运行会出现异常
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
        }
        if (mPlayerView != null) {
            mPlayerView.onDestroy();
        }
        record(LIVE_EXIT);
    }

    // 记录直播时长
    // enter 0是进入 1是退出
    private final int LIVE_ENTER = 0;
    private final int LIVE_EXIT = 1;

    private void record(int enter) {
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.LIVE_RECORD);
        params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());
        params.addQueryStringParameter("courseId", bean.getData().getCourseId() + "");
        params.addQueryStringParameter("playType", enter + "");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<Bean>() {
                }.getType();
                Bean bean = gson.fromJson(s, type);

                if (bean != null && bean.getCode().equals("SUCCESS")) {
                    Log.d(TAG, "LIVE_RECORD SUCCESS");
                } else {
                    Log.d(TAG, "LIVE_RECORD FAIL" + bean.getCodeInfo());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ActivityUtils.showToastForFail(context, "获取信息失败");
                isRefresh = false;
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    int count = 5;
    private Dialog dialog;

    //展示反馈dialog
    private void showFeedDialog() {
        dialog = new Dialog(context, R.style.my_dialog);
        dialog.setContentView(R.layout.dialog_video_feedback);

        int rb = context.getResources().getIdentifier("rb", "id", context.getPackageName());
        MyRatingBar ratingBar = (MyRatingBar) dialog.findViewById(rb);
        ratingBar.setClickable(true);//设置可否点击
        ratingBar.setStar(5f);//设置显示的星星个数
        ratingBar.setStepSize(MyRatingBar.StepSize.Full);//设置每次点击增加一颗星还是半颗星
        ratingBar.setOnRatingChangeListener(new MyRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {//点击星星变化后选中的个数
                Log.d("RatingBar", "RatingBar-Count=" + ratingCount);
                count = (int) ratingCount;
            }
        });
        RelativeLayout button12 = (RelativeLayout) dialog.findViewById(R.id.cancel);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final EditText feedback = (EditText) dialog.findViewById(R.id.feedback);
        final TextView content = (TextView) dialog.findViewById(R.id.content);
        content.setText(String.format(getResources().getString(R.string.gift), gift));
        Button button1 = (Button) dialog.findViewById(R.id.btn_submit);
        Button button2 = (Button) dialog.findViewById(R.id.btn_cancel);

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(feedback.getText().toString())) {
                    ActivityUtils.showToast(PlayActivity.this, "请将信息填写完整");
                    return;
                }
                submitFeedback(count, feedback.getText().toString());
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void submitFeedback(int level, String suggestion) {
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.SUBMIT_VIDEO_FEEDBACK);
        params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());
        params.addQueryStringParameter("presentId", giftId + "");
        params.addQueryStringParameter("level", level + "");
        params.addQueryStringParameter("suggestion", suggestion);
        params.addQueryStringParameter("courseName", bean.getData().getCourseName());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<Bean>() {
                }.getType();
                Bean bean = gson.fromJson(s, type);

                if (bean != null && bean.getCode().equals("SUCCESS")) {
                    ActivityUtils.showToast(context, "提交信息成功");
                    finish();
                } else {
                    ActivityUtils.showToastForFail(context, "提交信息失败");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ActivityUtils.showToastForFail(context, "提交信息失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPlayType == TXLivePlayer.PLAY_TYPE_VOD_FLV || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_HLS || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_MP4) {
            if (mLivePlayer != null) {
                mLivePlayer.pause();
            }
        } else {
            stopPlayRtmp();
        }

        if (mPlayerView != null) {
            mPlayerView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);

        if (mVideoPlay && !mVideoPause) {
            if (mPlayType == TXLivePlayer.PLAY_TYPE_VOD_FLV || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_HLS || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_MP4) {
                if (mLivePlayer != null) {
                    mLivePlayer.resume();
                }
            } else {
                startPlayRtmp();
            }
        }

        if (mPlayerView != null) {
            mPlayerView.onResume();
        }
    }

    private boolean checkPlayUrl(final String playUrl) {
        Log.d(TAG, "playUrl===" + playUrl);
        if (TextUtils.isEmpty(playUrl) || (!playUrl.startsWith("http://") && !playUrl.startsWith("https://") && !playUrl.startsWith("rtmp://"))) {
            Toast.makeText(getApplicationContext(), "播放地址不合法，目前仅支持rtmp,flv,hls,mp4播放方式!", Toast.LENGTH_SHORT).show();
            return false;
        }

        switch (mActivityType) {
            case ACTIVITY_TYPE_LIVE_PLAY: {
                if (playUrl.startsWith("rtmp://")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
                } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://")) && playUrl.contains(".flv")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
                } else {
                    Toast.makeText(getApplicationContext(), "播放地址不合法，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            break;
            case ACTIVITY_TYPE_VOD_PLAY: {
                if (playUrl.startsWith("http://") || playUrl.startsWith("https://")) {
                    if (playUrl.contains(".flv")) {
                        mPlayType = TXLivePlayer.PLAY_TYPE_VOD_FLV;
                    } else if (playUrl.contains(".m3u8")) {
                        mPlayType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                    } else if (playUrl.toLowerCase().contains(".mp4")) {
                        mPlayType = TXLivePlayer.PLAY_TYPE_VOD_MP4;
                    } else {
                        Toast.makeText(getApplicationContext(), "播放地址不合法，点播目前仅支持flv,hls,mp4播放方式!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "播放地址不合法，点播目前仅支持flv,hls,mp4播放方式!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            break;
            default:
                Toast.makeText(getApplicationContext(), "播放地址不合法，目前仅支持rtmp,flv,hls,mp4播放方式!", Toast.LENGTH_SHORT).show();
                return false;
        }
        return true;
    }

    private boolean startPlayRtmp() {
        String playUrl = mRtmpUrl;

        if (!checkPlayUrl(playUrl)) {
            return false;
        }

        int[] ver = TXLivePlayer.getSDKVersion();
        // if (ver != null && ver.length >= 3) {
        // mLogMsg.append(String.format("rtmp sdk version:%d.%d.%d ", ver[0],
        // ver[1], ver[2]));
        // mLogViewEvent.setText(mLogMsg);
        // }
        mBtnPlay.setBackgroundResource(R.drawable.play_pause);

        mLivePlayer.setPlayerView(mPlayerView);
        mLivePlayer.setPlayListener(this);

        // 硬件加速在1080p解码场景下效果显著，但细节之处并不如想象的那么美好：
        // (1) 只有 4.3 以上android系统才支持
        // (2) 兼容性我们目前还仅过了小米华为等常见机型，故这里的返回值您先不要太当真
        // mLivePlayer.enableHardwareDecode(mHWDecode);
        mLivePlayer.setRenderRotation(mCurrentRenderRotation);
        mLivePlayer.setRenderMode(mCurrentRenderMode);
        // 设置播放器缓存策略
        // 这里将播放器的策略设置为自动调整，调整的范围设定为1到4s，您也可以通过setCacheTime将播放器策略设置为采用
        // 固定缓存时间。如果您什么都不调用，播放器将采用默认的策略（默认策略为自动调整，调整范围为1到4s）
        // mLivePlayer.setCacheTime(5);
        mLivePlayer.setConfig(mPlayConfig);

        int result = mLivePlayer.startPlay(playUrl, mPlayType); // result返回值：0
        // success; -1
        // empty url; -2
        // invalid url;
        // -3 invalid
        // playType;
        if (result == -2) {
            Toast.makeText(getApplicationContext(), "非腾讯云链接地址，若要放开限制，请联系腾讯云商务团队", Toast.LENGTH_SHORT).show();
        }
        if (result != 0) {
            mBtnPlay.setBackgroundResource(R.drawable.play_start);
            return false;
        }

        // mLivePlayer.setLogLevel(TXLiveConstants.LOG_LEVEL_DEBUG);

        startLoadingAnimation();

        mStartPlayTS = System.currentTimeMillis();
        return true;
    }

    private void stopPlayRtmp() {
        mBtnPlay.setBackgroundResource(R.drawable.play_start);
        stopLoadingAnimation();
        if (mLivePlayer != null) {
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
        }
    }

    @Override
    public void onNetStatus(Bundle status) {
        Log.d(TAG, "Current status: " + status.toString());
        // if (mLivePlayer != null){
        // mLivePlayer.onLogRecord("[net state]:\n"+str+"\n");
        // }
    }

    @Override
    public void onPlayEvent(int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            stopLoadingAnimation();
            Log.d("AutoMonitor", "PlayFirstRender,cost=" + (System.currentTimeMillis() - mStartPlayTS));
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            if (mStartSeek) {
                return;
            }
            int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
            int duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION);
            long curTS = System.currentTimeMillis();

            // 避免滑动进度条松开的瞬间可能出现滑动条瞬间跳到上一个位置
            if (Math.abs(curTS - mTrackingTouchTS) < 500) {
                return;
            }
            mTrackingTouchTS = curTS;

            if (mSeekBar != null) {
                mSeekBar.setProgress(progress);
            }
            if (mTextStart != null) {
                mTextStart.setText(String.format("%02d:%02d", progress / 60, progress % 60));
            }
            if (mTextDuration != null) {
                mTextDuration.setText(String.format("%02d:%02d", duration / 60, duration % 60));
            }
            if (mSeekBar != null) {
                mSeekBar.setMax(duration);
            }
            return;
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT || event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            stopPlayRtmp();
            mVideoPlay = false;
            mVideoPause = false;
            if (mTextStart != null) {
                mTextStart.setText("00:00");
            }
            if (mSeekBar != null) {
                mSeekBar.setProgress(0);
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {
            startLoadingAnimation();
        }

        if (event < 0) {
            Toast.makeText(getApplicationContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            stopLoadingAnimation();
        }
    }

    private void startLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
            ((AnimationDrawable) mLoadingView.getDrawable()).start();
        }
    }

    private void stopLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
            ((AnimationDrawable) mLoadingView.getDrawable()).stop();
        }
    }

    /**********************************************************************************************************************/

    long lastClick = 0;
    boolean isRefresh = false;
    boolean isFirstFragment1 = true;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.button1:
                // if (choose == )
                // return;
                // isMine = true;
                // changeView(isMine);
                // adapter.notifyDataSetChanged();
                // ((XListViewOfRead) listView).setPullLoadEnable(false);
                break;
            case R.id.button2:
                if (choose == CHOOSE_CHAT)
                    return;
                choose = CHOOSE_CHAT;
                changeView(choose);
                // loadData(0);
                // listView.setAdapter(adapter);
                // listView.setSelection(sum);
                // ((XListViewOfRead) listView).setPullLoadEnable(true);
                break;
            // case R.id.refresh:
            // if (System.currentTimeMillis() - lastClick <= 1000)
            // return;
            // if (isRefresh)
            // return;
            // lastClick = System.currentTimeMillis();
            // isRefresh = true;
            // Animation animator = AnimationUtils.loadAnimation(context,
            // R.anim.rotate);
            // btn_refresh.startAnimation(animator);
            // loadData(0);
            // break;
            case R.id.button3:
                if (choose == CHOOSE_GOODS)
                    return;
                choose = CHOOSE_GOODS;
                changeView(choose);
                if (isFirstFragment1) {
                    isFirstFragment1 = false;
                } else {
                    ((Fragment_Good1) f1).refreshFragment1();
                }
                back();
                break;
            case R.id.button4:
                if (choose == CHOOSE_SETTING)
                    return;
                choose = CHOOSE_SETTING;
                changeView(choose);
                loadSettingData();
                listView.setAdapter(settingAdapter);
                break;
            default:
                break;
        }
    }

    @SuppressLint("NewApi")
    private void changeView(int choose) {
        btn_all.setBackground(getResources().getDrawable(choose == CHOOSE_CHAT ? R.drawable.green_btn : R.color.transparent));
        btn_goods.setBackground(getResources().getDrawable(choose == CHOOSE_GOODS ? R.drawable.green_btn : R.color.transparent));
        btn_setting.setBackground(getResources().getDrawable(choose == CHOOSE_SETTING ? R.drawable.green_btn : R.color.transparent));

        btn_all.setTextColor(getResources().getColor(choose == CHOOSE_CHAT ? R.color.white : R.color.zhibo_black2));
        btn_goods.setTextColor(getResources().getColor(choose == CHOOSE_GOODS ? R.color.white : R.color.zhibo_black2));
        btn_setting.setTextColor(getResources().getColor(choose == CHOOSE_SETTING ? R.color.white : R.color.zhibo_black2));

        layout_webchat.setVisibility(choose == CHOOSE_CHAT ? View.VISIBLE : View.GONE);
        layout_listview.setVisibility(choose == CHOOSE_SETTING ? View.VISIBLE : View.GONE);
        layout_viewpager.setVisibility(choose == CHOOSE_GOODS ? View.VISIBLE : View.GONE);

        headview.setVisibility(choose == CHOOSE_SETTING ? View.VISIBLE : View.GONE);
    }

    // private ArrayList<ChatBean> list_all = new ArrayList<>();
    // private ArrayList<ChatBean> list_mine = new ArrayList<>();

    // 商品上下步选择 支付接口
    @Override
    public void last() {
        if (viewpager.getCurrentItem() - 1 == 1)
            ((Fragment_Good2) f2).refreshFragment2();
        viewpager.setCurrentItem(viewpager.getCurrentItem() - 1);
    }

    @Override
    public void next() {
        if (viewpager.getCurrentItem() + 1 == 1)
            ((Fragment_Good2) f2).refreshFragment2();
        if (viewpager.getCurrentItem() + 1 == 3)
            ((Fragment_Good4) f4).refreshFragment4();
        viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
    }

    @Override
    public void charge() {
        final GoodsDetailBean bean = GoodsDetailUtils.getInstance(this).getBean();

        RequestParams params = new RequestParams(Preferences.ZHIBO_GOODS_PAY);
        params.addQueryStringParameter("code", bean.getCode());
        if (bean.isFapiao()) {
            params.addQueryStringParameter("invoice.id", bean.getFapiaoId() + "");
            params.addQueryStringParameter("invoice.invoiceTitle", bean.getFapiao());
        } else {
            params.addQueryStringParameter("invoice.id", "");
            params.addQueryStringParameter("invoice.invoiceTitle", "");
        }
        params.addQueryStringParameter("receiverInfo.id", bean.getReceiverId() + "");
        params.addQueryStringParameter("receiverInfo.name", bean.getName());
        params.addQueryStringParameter("receiverInfo.address", bean.getAddress());
        params.addQueryStringParameter("receiverInfo.telephone", bean.getTelephone());
        params.addQueryStringParameter("comment", bean.getBeizhu());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<OrderBean>() {
                }.getType();
                OrderBean bean1 = gson.fromJson(s, type);

                if (bean1 != null && bean1.getCode().equals("SUCCESS")) {
                    // Intent intent = new Intent(PlayActivity.this,
                    // PayChooseActivity.class);
                    // Bundle b = new Bundle();
                    // b.putString(PaySubmitActivity.ORDER_PRICE,
                    // bean1.getData().getTotalPrice());
                    // b.putString(PaySubmitActivity.ORDER_CODE,
                    // bean1.getData().getOrderCode());
                    // b.putString(PaySubmitActivity.ORDER_URL,
                    // bean1.getData().getNotify_url());
                    // b.putString(PaySubmitActivity.ORDER_NAME,
                    // bean.getTitle());
                    // b.putString(PaySubmitActivity.ORDER_BODY, "益智玩具");
                    // intent.putExtra(PaySubmitActivity.ORDER, b);
                    // startActivityForResult(intent, 99);
                    // ActivityUtils.showToastForSuccess(context,"已成功下单");
                    // mHandler.sendEmptyMessageDelayed(CMD_XIADAN,1000);
                    viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                } else {
                    ActivityUtils.showToastForFail(context, "订单提交失败");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ActivityUtils.showToastForFail(PlayActivity.this, "订单提交失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void back() {
        viewpager.setCurrentItem(0);
        GoodsDetailBean bean = GoodsDetailUtils.getInstance(this).getBean();
        bean.setBeizhu("");
        GoodsDetailUtils.getInstance(this).setBean(bean);
    }

    @Override
    public void onRefreshAdapter() {
        loadSettingData();
    }

    // ///////////////////////////////////////////////////////聊天室

    ValueCallback<Uri> mUploadMessage;
    ValueCallback<Uri[]> mFilePathCallback;

    private class MyWebViewClient extends WebViewClient {
        private Context mContext;

        public MyWebViewClient(Context context) {
            super();
            mContext = context;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "URL地址:" + url);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.i(TAG, "onPageFinished");
            super.onPageFinished(view, url);
        }
    }

    public static final int FILECHOOSER_RESULTCODE = 1;
    private static final int REQ_CAMERA = FILECHOOSER_RESULTCODE + 1;
    private static final int REQ_CHOOSE = REQ_CAMERA + 1;

    private class MyWebChromeClient extends WebChromeClient {

        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            Log.i(TAG, "5.0+");
            if (mFilePathCallback != null)
                return true;
            mFilePathCallback = filePathCallback;
            selectImage();
            return true;
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            Log.i(TAG, "3.0+");
            if (mUploadMessage != null)
                return;
            mUploadMessage = uploadMsg;
            selectImage();
            // Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            // i.addCategory(Intent.CATEGORY_OPENABLE);
            // i.setType("*/*");
            // startActivityForResult( Intent.createChooser( i, "File Chooser"
            // ), FILECHOOSER_RESULTCODE );
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            Log.i(TAG, "< 3.0");
            openFileChooser(uploadMsg, "");
        }

        // For Android > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Log.i(TAG, "> 4.1.1");
            openFileChooser(uploadMsg, acceptType);
        }

    }

    /**
     * 检查SD卡是否存在
     *
     * @return
     */
    public final boolean checkSDcard() {
        boolean flag = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!flag) {
            Toast.makeText(this, "请插入手机存储卡再使用本功能", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }

    String compressPath = "";

    protected final void selectImage() {
        if (!checkSDcard())
            return;
        Log.i(TAG, "checkSDcard");
        // String[] selectPicTypeStr = {"拍照", "从相册中选择"};
        // AlertDialog alertDialog = new AlertDialog.Builder(this)
        // .setItems(selectPicTypeStr,
        // new DialogInterface.OnClickListener() {
        // @Override
        // public void onClick(DialogInterface dialog,
        // int which) {
        // switch (which) {
        // // 相机拍摄
        // case 0:
        // openCarcme();
        // break;
        // // 手机相册
        // case 1:
        chosePic();
        // break;
        // default:
        // break;
        // }
        // compressPath = Environment
        // .getExternalStorageDirectory()
        // .getPath()
        // + "/fuiou_wmp/temp";
        // new File(compressPath).mkdirs();
        // compressPath = compressPath + File.separator
        // + "compress.jpg";
        // }
        // }).setOnCancelListener(new DialogInterface.OnCancelListener() {
        // @Override
        // public void onCancel(DialogInterface dialogInterface) {
        // if (mFilePathCallback != null) {
        // Uri[] uris = new Uri[1];
        // uris[0] = Uri.parse("");
        // mFilePathCallback.onReceiveValue(uris);
        // mFilePathCallback = null;
        // } else {
        // mUploadMessage.onReceiveValue(Uri.parse(""));
        // mUploadMessage = null;
        // }
        // }
        // }).show();
    }

    String imagePaths;
    Uri cameraUri;

    /**
     * 打开照相机
     */
    private void openCarcme() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (!sdCardExist) {
            ActivityUtils.showToastForFail(PlayActivity.this, "设备未装载sd卡");
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        imagePaths = Environment.getExternalStorageDirectory().getPath() + "/jiayue/temp/" + (System.currentTimeMillis() + ".jpg");
        // 必须确保文件夹路径存在，否则拍照后无法完成回调
        File vFile = new File(imagePaths);
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            vFile.delete();
        }
        cameraUri = Uri.fromFile(vFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(intent, REQ_CAMERA);
    }

    /**
     * 拍照结束后
     */
    private void afterOpenCamera() {
        File f = new File(imagePaths);
        addImageGallery(f);
    }

    /**
     * 解决拍照后在相册中找不到的问题
     */
    private void addImageGallery(File file) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * 本地相册选择图片
     */
    private void chosePic() {
        // delFile(new File(compressPath));
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
        String IMAGE_UNSPECIFIED = "image/*";
        innerIntent.setType(IMAGE_UNSPECIFIED); // 查看类型
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQ_CHOOSE);
    }

    public boolean delFile(File file) {
        if (file == null)
            return false;
        if (!isFileExists(file)) {
            // 如果文件不存在，直接返回true
            return true;
        }
        if (file.isDirectory())
            // 如果是文件夹，返回false
            return false;
        return file.delete();
    }

    public boolean isFileExists(File file) {
        if (file == null)
            return false;
        return file.exists();
    }

    /**
     * 选择照片后结束
     *
     * @param data
     */
    private Uri afterChosePic(Intent data) {

        // 获取图片的路径：
        String[] proj = {MediaStore.Images.Media.DATA};
        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = managedQuery(data.getData(), proj, null, null, null);
        if (cursor == null) {
            Toast.makeText(this, "上传的图片仅支持png或jpg格式", Toast.LENGTH_SHORT).show();
            return null;
        }
        // 按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        // 将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        // 最后根据索引值获取图片路径
        String path = cursor.getString(column_index);
        if (path != null && (path.endsWith(".png") || path.endsWith(".PNG") || path.endsWith(".jpg") || path.endsWith(".JPG"))) {
            // File newFile = FileUtils.compressFile(path, compressPath);
            File newFile = new File(path);
            return Uri.fromFile(newFile);
        } else {
            Toast.makeText(this, "上传的图片仅支持png或jpg格式", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (resultCode) {
            case 999:
                viewpager.setCurrentItem(0);
                break;
            default:
                if (requestCode == REQ_CAMERA) {
                    Uri uri = null;
                    File file = null;
                    if (cameraUri != null) {
                        file = new File(cameraUri.getPath());
                    } else {
                        mFilePathCallback = null;
                        mUploadMessage = null;
                        return;
                    }
                    if (!file.exists()) {
                        cameraUri = Uri.parse("");
                    }
                    afterOpenCamera();
                    uri = cameraUri;
                    if (mFilePathCallback != null) {
                        Uri[] uris = new Uri[1];
                        uris[0] = uri;
                        mFilePathCallback.onReceiveValue(uris);
                    } else {
                        mUploadMessage.onReceiveValue(uri);
                    }
                    mFilePathCallback = null;
                    mUploadMessage = null;
                } else if (requestCode == REQ_CHOOSE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (mFilePathCallback == null)
                            return;
                        mFilePathCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                    } else {
                        if (null == mUploadMessage)
                            return;
                        // Use MainActivity.RESULT_OK if you're implementing
                        // WebView inside Fragment
                        // Use RESULT_OK only if you're implementing WebView
                        // inside an Activity
                        Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null : intent.getData();
                        mUploadMessage.onReceiveValue(result);
                    }
                    mFilePathCallback = null;
                    mUploadMessage = null;
                }
                break;
        }
    }
}
