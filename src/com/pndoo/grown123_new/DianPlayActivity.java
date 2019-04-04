package com.pndoo.grown123_new;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.dto.base.GiftBean;
import com.pndoo.grown123_new.dto.base.WangQiDataBean;
import com.pndoo.grown123_new.dto.base.WangqiUrlBean;
import com.pndoo.grown123_new.dto.base.WangqiUrlDataBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DensityUtil;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.SPUtility;
import com.pndoo.grown123_new.view.MyRatingBar;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.x;

public class DianPlayActivity extends Activity implements ITXLivePlayListener {
	private final String TAG = getClass().getSimpleName();
	private TextView title;
	private LinearLayout layout_title, layout;
	private WangQiDataBean bean;
	private WangqiUrlBean urlbean;
	
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

	protected int mActivityType = ACTIVITY_TYPE_VOD_PLAY;

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
	private int mPlayType = TXLivePlayer.PLAY_TYPE_VOD_MP4;
	private TXLivePlayConfig mPlayConfig;
	private long mStartPlayTS = 0;

	private LinearLayout layout_hidden;
	private String gift = null;
	private int giftId = 0;
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_dianplay);

		bean = (WangQiDataBean) getIntent().getSerializableExtra("bean");
		urlbean = (WangqiUrlBean) getIntent().getSerializableExtra("urlbean");
//		if (bean == null || bean.getCode().equals("FAIL") || bean.getData() == null) {
//			ActivityUtils.showToastForFail(this, "直播信息获取失败！");
//			finish();
//		}

        title = (TextView) findViewById(R.id.title);
        title.setText(bean.getFileName());
        
		initData();
		initVideo();
		getGiftName();

		record(LIVE_ENTER);
	}

	private void initData() {
		// TODO Auto-generated method stub
		TextView title = (TextView) findViewById(R.id.textView8);
		TextView describe = (TextView)findViewById(R.id.textView3);
		TextView teacher = (TextView) findViewById(R.id.textView4);
		TextView creat_time = (TextView) findViewById(R.id.textView5);
		TextView keyword = (TextView) findViewById(R.id.textView6);
		TextView level = (TextView) findViewById(R.id.textView7);
		
		title.setText(getString(R.string.biaoti)+bean.getFileName());
		describe.setText(getString(R.string.jianjie)+bean.getContent());
		teacher.setText(getString(R.string.teacher)+bean.getAuthor());
		creat_time.setText(getString(R.string.shijian)+bean.getCreateTime());
		keyword.setText(getString(R.string.guanjianci)+bean.getKeyword());
		level.setText(getString(R.string.jieduan)+bean.getVipType());
	}

	//获取退出时评价 礼物
	private void getGiftName() {
		org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.GET_VIDEO_GIFT);
		params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());
		params.addQueryStringParameter("courseName", bean.getFileName());

		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String s) {
				Gson gson = new Gson();
				java.lang.reflect.Type type = new TypeToken<GiftBean>() {
				}.getType();
				GiftBean bean = gson.fromJson(s, type);
				if (bean != null && bean.getCode().equals("SUCCESS")&&bean.getData()!=null) {
					if(!TextUtils.isEmpty(bean.getData().getPresentName())){
						gift = bean.getData().getPresentName();
						giftId = bean.getData().getPresentId();
					}
				}
			}

			@Override
			public void onError(Throwable throwable, boolean b) {
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
	private void showFeedDialog(){
		dialog = new Dialog(this, R.style.my_dialog);
		dialog.setContentView(R.layout.dialog_video_feedback);

		int rb = context.getResources().getIdentifier("rb", "id", context.getPackageName());
		MyRatingBar ratingBar= (MyRatingBar) dialog.findViewById(rb);
		ratingBar.setClickable(true);//设置可否点击
		ratingBar.setStar(5f);//设置显示的星星个数
		ratingBar.setStepSize(MyRatingBar.StepSize.Full);//设置每次点击增加一颗星还是半颗星
		ratingBar.setOnRatingChangeListener(new MyRatingBar.OnRatingChangeListener() {
			@Override
			public void onRatingChange(float ratingCount) {//点击星星变化后选中的个数
				Log.d("RatingBar","RatingBar-Count="+ratingCount);
				count = (int)ratingCount;
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
		content.setText(String.format(getResources().getString(R.string.gift),gift));
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
				if(TextUtils.isEmpty(feedback.getText().toString())){
					ActivityUtils.showToast(DianPlayActivity.this,"请将信息填写完整");
					return;
				}
				submitFeedback(count,feedback.getText().toString());
			}
		});

		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(false);
		dialog.show();
	}

	private void submitFeedback(int level,String suggestion) {
		org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.SUBMIT_VIDEO_FEEDBACK);
		params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());
		params.addQueryStringParameter("presentId", giftId+"");
		params.addQueryStringParameter("level",level+"");
		params.addQueryStringParameter("suggestion",suggestion);
		params.addQueryStringParameter("courseName", bean.getFileName());

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

	private void initVideo() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.title);
		layout_title = (LinearLayout) findViewById(R.id.layout_title);
		layout = (LinearLayout) findViewById(R.id.layout);
		layout_hidden = (LinearLayout) findViewById(R.id.layout_hidden);
		
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

		mCurrentRenderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
		mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;

		mPlayConfig = new TXLivePlayConfig();
		
		mRtmpUrl = "";
		
		if(urlbean.getData()!=null&&urlbean.getData().size()!=0){
			for(WangqiUrlDataBean bean:urlbean.getData()){
				if(bean.getDefinition() == 20){
					mRtmpUrl = bean.getUrl();
				}
			}
		}else {
			ActivityUtils.showToastForFail(DianPlayActivity.this, "抱歉，未获取到资源，请退出后重试，谢谢");
			return;
		}
		

		if (mLivePlayer == null) {
			mLivePlayer = new TXLivePlayer(this);
		}

		mPlayerView = (TXCloudVideoView) findViewById(R.id.video_view);
		mLoadingView = (ImageView) findViewById(R.id.loadingImageView);

		layout = (LinearLayout) findViewById(R.id.layout);

		mVideoPlay = false;

		mBtnPlay = (Button) findViewById(R.id.btnPlay);
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

		mSeekBar = (SeekBar) findViewById(R.id.seekbar);
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

		mTextDuration = (TextView) findViewById(R.id.duration);
		mTextStart = (TextView) findViewById(R.id.play_start);
		mTextDuration.setTextColor(Color.rgb(255, 255, 255));
		mTextStart.setTextColor(Color.rgb(255, 255, 255));

		this.setCacheStrategy(CACHE_STRATEGY_FAST);

		View progressGroup = findViewById(R.id.play_progress);

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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK) {
			if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
				layout_title.setVisibility(View.VISIBLE);
				layout.setPadding(DensityUtil.dip2px(DianPlayActivity.this, 13), DensityUtil.dip2px(DianPlayActivity.this, 7), DensityUtil.dip2px(DianPlayActivity.this, 13), DensityUtil
						.dip2px(DianPlayActivity.this, 7));
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
			layout.setPadding(0,0,0,0);
			mPlayerView.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		} else {
			layout_title.setVisibility(View.VISIBLE);
			layout_hidden.setVisibility(View.VISIBLE);
			layout.setPadding(DensityUtil.dip2px(DianPlayActivity.this, 13), DensityUtil.dip2px(DianPlayActivity.this, 7), DensityUtil.dip2px(DianPlayActivity.this, 13), DensityUtil
					.dip2px(DianPlayActivity.this, 7));
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
		params.addQueryStringParameter("fileId", bean.getFileId());
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
					Log.d(TAG, "LIVE_RECORD FAIL"+bean.getCodeInfo());
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
		} else if (Build.VERSION.SDK_INT >= 23) { // 目前android6.0以上暂不支持后台播放
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
			} else if (Build.VERSION.SDK_INT >= 23) { // 目前android6.0以上暂不支持后台播放
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


	private void exit(){
		if(TextUtils.isEmpty(gift)||giftId == 0){
			finish();
		}else{
			showFeedDialog();
		}
	}
}
