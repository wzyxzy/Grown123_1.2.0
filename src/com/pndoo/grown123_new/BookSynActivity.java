package com.pndoo.grown123_new;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.controller.BookController;
import com.pndoo.grown123_new.download.TestService;
import com.pndoo.grown123_new.download2.db.DataBaseFiledParams;
import com.pndoo.grown123_new.download2.db.DataBaseHelper;
import com.pndoo.grown123_new.download2.entity.DocInfo;
import com.pndoo.grown123_new.download2.utils.DownloadManager;
import com.pndoo.grown123_new.download2.utils.DownloadManager.DownloadListener;
import com.pndoo.grown123_new.dto.base.AttachOne;
import com.pndoo.grown123_new.dto.base.AttachTwo;
import com.pndoo.grown123_new.dto.base.BookVO;
import com.pndoo.grown123_new.main.IoC;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.service.ZipService;
import com.pndoo.grown123_new.soap.BookJson;
import com.pndoo.grown123_new.task.BaseTask;
import com.pndoo.grown123_new.task.TaskListener;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyDbUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.SPUtility;
import com.pndoo.grown123_new.util.SerializableUtil;
import com.pndoo.grown123_new.util.StringUtil;
import com.umeng.analytics.MobclickAgent;

import org.springframework.util.MultiValueMap;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;

@SuppressLint("InflateParams")
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class BookSynActivity extends BaseActivity implements OnRefreshListener {
	Context context;
	LinearLayout sv_container;
	LinearLayout ll_content;
	ProgressBar progressBar;
	TextView tv_errorMsg;
	TextView tv_fujian;
	LinearLayout ll_fujian;
	AttachOne attachOne;
	BookVO bookVO;
	String bookId;
	String imageMatchId;
	String bookName;
	ImageView btn_syn;
	TaskManager tm;
	BookController bookController;
	String errorMsg;
	GridView gv_fujian;
	GridView gv_match;
	GridView gv_releted;
	FileAttachAdapter adapte_fujian;
	DisplayImageOptions options;
	int flag_download = -1;
	ImageView iv_point;
	List<AttachOne> attachOnes = new ArrayList<AttachOne>();
	ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	BroadcastReceiver broadcastReciver;
	DownloadManager downloadManager;
	private String image_url_str;
	private TextView tv_header_title;
	private ViewPager viewPager;
	private ArrayList<View> pageview;
	private int iv_bookId;
	private ImageView iv_book;
	private String bookSaveName;
	private LinearLayout ll_communication;
	long lastClick;
	private TextView tv_bookname;
	private TextView tv_author;
	private ImageView iv_wdian;
	private ImageView iv_dian;
	private ImageButton btn_header_right;
	private TextView tv_loading;
	private TextView tv_size;
	private AttachOne attachOne2;
	private SwipeRefreshLayout refresh_view;
	private DocInfo info;
	private final String TAG = getClass().getSimpleName();
	private TextView tv_price;
	private LinearLayout mLayout_Shop, mLayout_Down;
	/**
	 * 购物车逻辑 329 338 1129 1226
	 */
	private boolean isShop = true;//是否已经付费 
	private Button mBtn_Shop;
	private TextView tv_progress;
	private DbManager db ;

	Handler mHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					DialogUtils.dismissMyDialog();
					ActivityUtils.readFile(BookSynActivity.this, bookId, msg.getData().getString("fileName"), msg.getData().getString("fileType"), msg.getData().getString("bookName"));
					break;
				default:
					break;
			}
			return false;
		}
	});

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getApplicationContext().addActivity(this);
		setContentView(R.layout.book_syn);
		context = this;
		tm = getApplicationContext().getTaskManager();
		bookController = IoC.getInstance(BookController.class);
		downloadManager = DownloadManager.getInstance(this);
		// 默认一张图片
		int cover_normal = getResources().getIdentifier("cover_normal", "drawable", getPackageName());
		options = new DisplayImageOptions.Builder().showStubImage(cover_normal)
		// 设置图片下载期间显示的图片
				.showImageForEmptyUri(cover_normal)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(cover_normal)
				// 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();
		bookId = getIntent().getStringExtra("bookId");
		bookName = getIntent().getStringExtra("bookName");
		image_url_str = getIntent().getStringExtra("image_url");
		db = MyDbUtils.getBookVoDb(this);
		// bookSaveName = getIntent().getStringExtra("bookSaveName");
		try {
			BookVO bookVO = db.selector(BookVO.class).where("book_id", "=", bookId).findFirst();
			if (bookVO != null) {
				bookSaveName = bookVO.getBookSaveName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		initView();
		initData();
		initDownload(this);
		if (bookVO != null && !ActivityUtils.isNetworkAvailable(context))
			ActivityUtils.showToast(this.context, "请联网更新后查看哦！");
		BookJson bookJson = SerializableUtil.unSerializeBookJson(context, bookId);
		if (bookJson != null)
			attachOnes = bookJson.getAttachOnes();
		
		/**
		 * 下载的文件 继续下载
		 */
		DataBaseHelper helper = new DataBaseHelper(context);
		List<DocInfo> infos = helper.getInfo2(bookId);
		for(DocInfo info:infos){
			if (info.getStatus() == DataBaseFiledParams.LOADING) {
				downloadManager.startForActivity(info);
			}
		};

	}

	private void initData() {
		attachOne = new AttachOne();
		attachOne.setAttachOneId(bookId);
		attachOne.setAttachOneType("zip");
		attachOne2 = new AttachOne();
		attachOne2.setAttachOneSaveName(bookSaveName);
		attachOne2.setAttachOneType("epub");

	}
	
	

	/**
	 * 设置download监听
	 * 
	 * @param cotext
	 */
	public void initDownload(Context cotext) {
		downloadManager.removeDownloadListener();
		downloadManager.addDownloadListener(new DownloadListener() {

			private DocInfo docInfo;

			@Override
			public void onUpdateProgress(DocInfo info) {
				Log.d(TAG, "----callback--------progress==" + info.getDownloadProgress());
				Intent intent = new Intent();
				intent.setAction("android.intent.action.test");// action与接收器相同
				Bundle bundle = new Bundle();
				bundle.putSerializable("info", info);
				intent.putExtra("bundle", bundle);
				intent.putExtra("flag", "update");
				sendBroadcast(intent);
			}

			@Override
			public void onDownloadFailed(DocInfo info) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.test");// action与接收器相同
				Bundle bundle = new Bundle();
				bundle.putSerializable("info", info);
				intent.putExtra("bundle", bundle);
				intent.putExtra("flag", "failed");
				sendBroadcast(intent);
			}

			@Override
			public void onDownloadCompleted(DocInfo info) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.test");// action与接收器相同
				Bundle bundle = new Bundle();
				bundle.putSerializable("info", info);
				intent.putExtra("bundle", bundle);
				intent.putExtra("flag", "success");
				sendBroadcast(intent);
				docInfo = info;
//				String substring = info.getName().substring(info.getName().length() - 4);
//				if (!substring.equalsIgnoreCase(".xml")) {
//					ActivityUtils.showToastForSuccess(BookSynActivity.this, info.getBookName() + "下载完成");
//				}
				if(docInfo.getName().endsWith(".zip")){
					new Thread() {
						public void run() {

							try {
								unZip(docInfo);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
//								ActivityUtils.deleteBookFormSD(docInfo.getBookId(), docInfo.getName());
							}
						};
					}.start();
				}
			}
		});
	}
	
	public void unZip(DocInfo info) throws ZipException, IOException{
		String bookName = info.getName();
		Log.d(TAG, "unZip----------bookName="+bookName);
		if (bookName.endsWith(info.getBookId() + ".zip")) {
			String file = ActivityUtils.getSDPath(info.getBookId()).getAbsolutePath() + "/" + bookName;
			ZipService.unzip(file, ActivityUtils.getSDPath(info.getBookId()).getAbsolutePath()+"/images");
			ActivityUtils.deleteBookFormSD(info.getBookId(), info.getName());
		} else if (bookName.endsWith(".zip")) {
			Log.i("BookSynActivity", "bookName=" + bookName);
			String file = ActivityUtils.getSDPath(info.getBookId()).getAbsolutePath() + "/" + bookName;
			Log.i("BookSynActivity", "file=" + file);
			ActivityUtils.unLock(info.getBookId(), bookName, bookName + "copy.zip");
			ZipService.unzip(file + "copy.zip", ActivityUtils.getSDPath(info.getBookId()).getAbsolutePath());
			ActivityUtils.deleteBookFormSD(info.getBookId(), info.getName() + "copy.zip");
		}
	}

	public void initView() {
		refresh_view = (SwipeRefreshLayout) findViewById(R.id.refresh_view);
		refresh_view.setOnRefreshListener(this);
		refresh_view.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		// btn_download = (LinearLayout) findViewById(R.id.btn_download);
		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
		tv_loading = (TextView) findViewById(R.id.tv_loading);
		tv_header_title.setText("图书详情");
		btn_header_right = (ImageButton) findViewById(R.id.btn_header_right);
		btn_header_right.setVisibility(View.VISIBLE);

		ll_fujian = (LinearLayout) findViewById(R.id.ll_fujian);
		ll_communication = (LinearLayout) findViewById(R.id.ll_communication);
		gv_fujian = (GridView) findViewById(R.id.gv_Fujian);
		gv_fujian.setSelector(new ColorDrawable(Color.TRANSPARENT));// 点击背景透明
		gv_fujian.setCacheColorHint(Color.TRANSPARENT);// 滑动时 背景透明
		adapte_fujian = new FileAttachAdapter(this);
		tv_bookname = (TextView) findViewById(R.id.tv_bookname);
		tv_bookname.setText(TextUtils.isEmpty(bookName) ? "" : bookName);
		tv_author = (TextView) findViewById(R.id.tv_author);
		tv_size = (TextView) findViewById(R.id.tv_size);

		tv_price = (TextView) findViewById(R.id.tv_price);
		mLayout_Down = (LinearLayout) findViewById(R.id.layout_download);
		mLayout_Shop = (LinearLayout) findViewById(R.id.layout_shop);
		
		btn_header_right.setBackgroundResource(getResources().getIdentifier("booksyn_syn", "drawable", getPackageName()));
		btn_header_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				if (isShop) {
					Intent intent = new Intent(BookSynActivity.this, SynManageActivity.class);
					startActivity(intent);
//				} else {
//					Intent intent = new Intent(MyPreferences.MAIN_BROADCAST);
//					intent.putExtra("menu", 3);
//					sendBroadcast(intent);
//					finish();
//				}
			}
		});
		mBtn_Shop = (Button) findViewById(R.id.btn_shop);
		tv_progress = (TextView) findViewById(R.id.tv_progress);
		

		iv_bookId = getResources().getIdentifier("iv_book", "id", getPackageName());
		iv_book = (ImageView) this.findViewById(iv_bookId);
		imageLoader.displayImage(image_url_str, iv_book, options);
		iv_point = (ImageView) findViewById(R.id.iv_point);
		iv_wdian = (ImageView) findViewById(R.id.iv_wdian);
		iv_dian = (ImageView) findViewById(R.id.iv_dian);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		loadViewPager();
		tv_errorMsg = new TextView(this);
		try {
			getBookIntroduction();
			getAttachOne();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("e========" + e.getLocalizedMessage());
		}

		broadcastReciver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals("android.intent.action.test")) {
					String flag = intent.getStringExtra("flag");
					Bundle bundle = intent.getBundleExtra("bundle");
					DocInfo info = (DocInfo) bundle.getSerializable("info");
					if (info.getName().equals(bookId + ".zip") || info.getName().equals(bookSaveName + ".epub")) {
						if (flag.equals("success")) {
							ActivityUtils.showToastForSuccess(BookSynActivity.this, info.getBookName() +"下载完成");
							flag_download = 1;
							iv_point.setVisibility(View.GONE);
							tv_loading.setText("完成");
							tv_progress.setText("");
						}
						if (flag.equals("update")) {
							tv_progress.setText(info.getDownloadProgress()+ "%");
							if (tv_progress.getText().toString().trim().equals("100%")) {
								tv_progress.setText("");
							}
						}
					}

					if (flag.equals("success") && isFileDownloaded(info.getName())) {
						ActivityUtils.showToastForSuccess(BookSynActivity.this, info.getBookName() +"下载完成");
						adapte_fujian.notifyDataSetChanged();
						gv_fujian.invalidateViews();
					}

					if (flag.equals("update")) {
						Log.d(TAG, "----broadcast--------progress==" + info.getDownloadProgress());
						adapte_fujian.notifyDataSetChanged();
						System.out.println("info.getDownloadProgress" + info.getDownloadProgress());
						System.out.println("info.getDownloadProgress finish");
						return;
					}

				} else if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
					if (null != intent.getStringExtra("reason") && intent.getStringExtra("reason").equals("homekey")) {
						deleteFileAttach();
						return;
					}
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.test");
		filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		registerReceiver(broadcastReciver, filter);
	}

	/**
	 * Title: loadViewPager Description: 加载viewpager
	 */

	private void loadViewPager() {

		// 查找布局文件用LayoutInflater.inflate
		LayoutInflater inflater = getLayoutInflater();
		View view1 = inflater.inflate(R.layout.item01, null);
		View view2 = inflater.inflate(R.layout.item02, null);
		pageview = new ArrayList<View>();
		pageview.add(view1);
		pageview.add(view2);
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			// 获取当前窗体界面数
			@Override
			public int getCount() {
				return pageview.size();
			}

			// 断是否由对象生成界面
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			// 是从ViewGroup中移出当前View
			@Override
			public void destroyItem(View arg0, int arg1, Object arg2) {
				((ViewPager) arg0).removeView(pageview.get(arg1));
			}

			// 返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
			@Override
			public Object instantiateItem(View arg0, int arg1) {
				((ViewPager) arg0).addView(pageview.get(arg1));
				return pageview.get(arg1);
			}

		};
		viewPager.setAdapter(mPagerAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switchViewpager(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void switchViewpager(int position) {
		int wdian = context.getResources().getIdentifier("wdian", "drawable", context.getPackageName());
		int dian = context.getResources().getIdentifier("dian", "drawable", context.getPackageName());
		if (position == 0) {
			ll_fujian.setVisibility(View.VISIBLE);
			ll_communication.setVisibility(View.GONE);
			iv_wdian.setImageResource(wdian);
			iv_dian.setImageResource(dian);
		} else if (position == 1) {
			ll_fujian.setVisibility(View.GONE);
			ll_communication.setVisibility(View.VISIBLE);
			iv_wdian.setImageResource(dian);
			iv_dian.setImageResource(wdian);
		}

	}

	/**
	 * 判断后台发出的下载成功广播是否是当前界面中的附件信息
	 * 
	 * @param fileSDName
	 *            保存在sd卡的名称
	 * @return true-成功 false-不成功
	 */
	public boolean isFileDownloaded(String fileSDName) {
		if (attachOnes != null && attachOnes.size() > 0) {
			for (AttachOne attachOne : attachOnes) {
				String fileName = attachOne.getAttachOneSaveName() + "." + attachOne.getAttachOneType();
				if (fileName.equals(fileSDName)) {
					return true;
				}
			}
		}
		return false;
	}

	public void btnBack(View v) {
		this.finish();
	}

	// 查看进度
	/*
	 * public void btnLookJD(View v) { Intent intent = new Intent(this,
	 * SynManageActivity.class); this.startActivity(intent); }
	 */
	// 同步书籍xml和image包

	/**
	 * <p>
	 * Title
	 * </p>
	 * : btnRead Description: 阅读
	 * 
	 * @param v
	 */
	public void btnRead(View v) {

		if (System.currentTimeMillis() - lastClick <= 1000) {
			Log.i("onclick", "您点击的太快了");
			return;
		}
		lastClick = System.currentTimeMillis();

		if (downloadManager.isDownloading(attachOne) || downloadManager.isDownloading(attachOne2)) {
			ActivityUtils.showToast(this, "文件正在下载，请稍等");
			return;
		}
//		if (ActivityUtils.epubIsExistAndRead(bookId, bookSaveName)) {
//			DialogUtils.showMyDialog(context, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在解析中...", null);
//			ActivityUtils.unLock(bookId, bookSaveName + ".epub", bookSaveName + ".epub" + "copy.epub");
//			Intent intent = new Intent(BookSynActivity.this, HomeActivity.class);
//			intent.putExtra("bookPath", bookSaveName + ".epub" + "copy.epub");
//			intent.putExtra("bookId", bookId);
//			intent.putExtra("bookName", bookName);
//			intent.putExtra("author", bookVO.getBookAuthor());
//
//			startActivity(intent);
//
//			return;
//		} else 
			if (ActivityUtils.isExistAndRead(bookId)) {
			ActivityUtils.unLock(bookId, bookId + ".xml", bookId + "copy.xml");
			Intent intent = new Intent(BookSynActivity.this, BookReadActivity.class);
			intent.putExtra("bookPath", ActivityUtils.getSDPath(bookId) + "/" + bookId + "copy.xml");
			intent.putExtra("imagePath", ActivityUtils.getSDPath(bookId) + "/images/");
			if (!TextUtils.isEmpty(bookVO.getBookName()))
				intent.putExtra("bookName", bookVO.getBookName());

			startActivity(intent);

		} else {

			ActivityUtils.showToastForFail(context, "请您下载完成后阅读");
		}

	}

	/**
	 * <p>
	 * Title
	 * </p>
	 * : btnSyn Description: 下载
	 * 
	 * @param v
	 */
	public void btnSyn(View v) { 

		if (System.currentTimeMillis() - lastClick <= 1000) {
			Log.i("onclick", "您点击的太快了");
			return;
		}
		lastClick = System.currentTimeMillis();
		if (ActivityUtils.epubIsExistAndRead(bookId, bookSaveName) || ActivityUtils.isExistAndRead(bookId)) {
			tv_loading.setText("完成");
			ActivityUtils.showToast(this, "请不要重复下载");
			return;
		}

		if (!unConnectedShowDialog())
			return;

		if (downloadManager.isDownloading(attachOne) || downloadManager.isDownloading(attachOne2)) {
			ActivityUtils.showToast(this, "文件正在下载，请稍等");
			return;
		}
		if (flag_download == -1 && ActivityUtils.isNetworkAvailable(context)) {
			if (bookVO != null && bookVO.getBookImgPath() != null && bookVO.getBookSaveName() != null) {
				if (!ActivityUtils.NetSwitch(context, Boolean.parseBoolean(SPUtility.getSPString(this, "switchKey")))) {
					ActivityUtils.showToastForFail(this, "请在有WIFI的情况下下载");
					return;
				}
				downLoadXml();
				downLoadZip();
				downLoadEpub();
			}
		}

	}

	/**
	 * <p>
	 * Title
	 * </p>
	 * : unConnectedShowDialog Description: 未连接网络对话框
	 */
	private boolean unConnectedShowDialog() {
		boolean isNet = true;
		if (!ActivityUtils.isNetworkAvailable(context)) {
			ActivityUtils.showToast(context, "无法连接网络，下载失败");
			// DialogUtils.showMyDialog(context,
			// MyPreferences.SHOW_CONFIRM_DIALOG, "网络设置提示",
			// "网络连接不可用,是否进行设置?", new OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// Intent intent = new
			// Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
			// startActivity(intent);
			// }
			// });
			isNet = false;
		}
		return isNet;
	}

	// 下载书籍的xml模块
	public void downLoadXml() {
		String path = null;

		Log.i("BookSynActivity", "BookImgPath" + bookVO.getBookImgPath());
		Log.i("BookSynActivity", "SavaName" + bookVO.getBookSaveName());
		path = getPath(Preferences.FILE_DOWNLOAD_URL + bookVO.getBookSavePath() + File.separator + bookVO.getBookSaveName() + ".xml");
		Log.i("BookSynActivity", "downLoadXml" + path);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			ActivityUtils.showToast(this, "加入同步列表,请稍候....");
			Intent intent = new Intent(this, TestService.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Bundle bundle = new Bundle();
			DocInfo d = new DocInfo();
			d.setUrl(path);
			d.setDirectoty(false);
			d.setBookId(bookId);
			d.setName(bookId + ".xml");
			d.setBookName(bookVO.getBookName());
			bundle.putSerializable("info", d);
			intent.putExtra("bundle", bundle);
			startService(intent);
			flag_download = 0;
		} else {
			ActivityUtils.showToastForFail(BookSynActivity.this, "未检测到SD卡");
		}
	}

	// 下载书籍的图片包模块
	public void downLoadZip() {
		String path = getPath(Preferences.FILE_DOWNLOAD_URL + bookVO.getBookSavePath() + File.separator + bookVO.getBookSaveName() + ".zip");
		Log.i("BookSynActivity", "downLoadZip" + path);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(this, TestService.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Bundle bundle = new Bundle();
			DocInfo d = new DocInfo();
			d.setUrl(path);
			d.setDirectoty(false);
			d.setName(bookId + ".zip");
			d.setBookId(bookId);
			d.setBookName(bookVO.getBookName() + "(图片包)");
			bundle.putSerializable("info", d);
			intent.putExtra("bundle", bundle);
			startService(intent);
			flag_download = 0;
		} else {
			ActivityUtils.showToastForFail(BookSynActivity.this, "未检测到SD卡");
		}
	}

	/**
	 * <p>
	 * Title
	 * </p>
	 * : downLoadEpub Description: 下载Epub模块
	 */
	public void downLoadEpub() {
		String path = getPath(Preferences.FILE_DOWNLOAD_URL + bookVO.getBookSavePath() + File.separator + bookVO.getBookSaveName() + ".epub");
		Log.i("BookSynActivity", "downLoadEpub" + path);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(this, TestService.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Bundle bundle = new Bundle();
			DocInfo d = new DocInfo();
			d.setUrl(path);
			d.setDirectoty(false);
			d.setName(bookSaveName + ".epub");
			d.setBookId(bookId);
			d.setBookName(bookName);
			bundle.putSerializable("info", d);
			intent.putExtra("bundle", bundle);
			startService(intent);
			flag_download = 0;
		} else {
			ActivityUtils.showToastForFail(BookSynActivity.this, "未检测到SD卡");
		}
	}

	/**
	 * 下载附件
	 * 
	 * @param url
	 *            附件的服务器地址
	 * @param saveName
	 *            附件要保存的名字
	 * @param fileName
	 *            附件要现实的名字
	 */
	public void downLoadFile(String url, String saveName, String fileName) {

		String path = getPath(Preferences.FILE_DOWNLOAD_URL + url);
		Log.i("BookSynActivity", "url=" + path);
		Log.i("BookSynActivity", "saveName=" + saveName);
		Log.i("BookSynActivity", "fileName=" + fileName);
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(this, TestService.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Bundle bundle = new Bundle();
			DocInfo d = new DocInfo();
			d.setUrl(path);
			d.setDirectoty(false);
			d.setName(saveName);
			d.setBookId(bookId);
			d.setBookName(fileName);
			bundle.putSerializable("info", d);
			intent.putExtra("bundle", bundle);
			startService(intent);
		} else {
			ActivityUtils.showToastForFail(BookSynActivity.this, "未检测到SD卡");
		}
	}

	private String getPath(String url) {
		String path = url + "&userId=" + UserUtil.getInstance(context).getUserId() + "&bookId=" + bookId;
		Log.d(TAG, "download path===========" + path);
		return path;
	}

	/**
	 * <p>
	 * Title
	 * </p>
	 * : PictureReadBook Description: 点击图片进行下载和阅读
	 * 
	 * @param paramView
	 */
	public void PictureReadBook(View paramView) {
//		if (ActivityUtils.epubIsExistAndRead(bookId, bookSaveName)) {
//			ActivityUtils.unLock(bookId, bookSaveName + ".epub", bookSaveName + ".epub" + "copy.epub");
//			Intent intent = new Intent(BookSynActivity.this, HomeActivity.class);
//			intent.putExtra("bookPath", bookSaveName + ".epub" + "copy.epub");
//			intent.putExtra("bookId", bookId);
//			intent.putExtra("bookName", bookName);
//			intent.putExtra("author", bookVO.getBookAuthor());
//			startActivity(intent);
//			return;
//		} else 
			if (ActivityUtils.isExistAndRead(bookId)) {
			ActivityUtils.unLock(bookId, bookId + ".xml", bookId + "copy.xml");
			Intent intent = new Intent(BookSynActivity.this, BookReadActivity.class);
			intent.putExtra("bookPath", ActivityUtils.getSDPath(bookId) + "/" + bookId + "copy.xml");
			intent.putExtra("imagePath", ActivityUtils.getSDPath(bookId) + "/images/");
			intent.putExtra("bookName", bookVO.getBookName());
			startActivity(intent);
		} else {
			if (!ActivityUtils.isNetworkAvailable(context)) {
				unConnectedShowDialog();
				return;
			}

			if (downloadManager.isDownloading(attachOne) || downloadManager.isDownloading(attachOne2)) {
				ActivityUtils.showToast(this, "文件正在同步，请稍等");
				return;
			}
			if (flag_download == -1) {
				if (bookVO != null && bookVO.getBookImgPath() != null && bookVO.getBookSaveName() != null) {
					downLoadXml();
					downLoadZip();
					downLoadEpub();
				}
				return;
			}
		}
	}

	/*
	 * Title: onResume Description: 为了更新小红点的标识和设置下载按钮的文字
	 * 
	 * @see com.jiayue.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		MobclickAgent.onPageStart(TAG);
		DialogUtils.dismissMyDialog(); 

		if (ActivityUtils.isExistAndRead(bookId) || ActivityUtils.epubIsExistAndRead(bookId, bookSaveName)) {
			iv_point.setVisibility(View.GONE);
			tv_loading.setText("完成");

		} else {
			iv_point.setVisibility(View.VISIBLE);
			tv_loading.setText("下载");

		}
		try {
			BookVO bookVO = db.selector(BookVO.class).where("book_id", "=", bookId).findFirst();
			if (bookVO == null) {
				Log.d(TAG, "=======================bookVO == nullbookVO == null");
			}else {
				Log.d(TAG, "=======================bookVO name"+bookVO.getBookName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		getBookIntroduction();
		
		

		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd(TAG);
	}

	/**
	 * Title: btnReadBook Description: 图书阅读按钮
	 * 
	 * @param v
	 */
	public void btnReadBook(View v) {
		if (ActivityUtils.isExistAndRead(bookId)) {
			ActivityUtils.unLock(bookId, bookId + ".xml", bookId + "copy.xml");
			Intent intent = new Intent(BookSynActivity.this, BookReadActivity.class);
			intent.putExtra("bookPath", ActivityUtils.getSDPath(bookId) + "/" + bookId + "copy.xml");
			intent.putExtra("imagePath", ActivityUtils.getSDPath(bookId) + "/images/");
			intent.putExtra("bookName", bookVO.getBookName());
			startActivity(intent);
		} else {
			ActivityUtils.showToast(BookSynActivity.this, "文件还未同步,请同步后观看!");
		}
	}

	public void btnFileClick(View v) {
		ActivityUtils.showToast(this, "已点击");
	}

	/**
	 * <p>
	 * Title
	 * </p>
	 * : getBookIntroduction Description: 网络获取BookVO对象
	 */
	private void getBookIntroduction() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", bookId);
		params.put("userId", UserUtil.getInstance(BookSynActivity.this).getUserId());
		Log.i("BookSynActivity", "bookId" + this.bookId);
		bookVO = unSerializeBookVO(this.bookId);
		if ((this.bookVO == null) && (!ActivityUtils.isNetworkAvailable(this.context))) {
			return;
		}
		if (this.bookVO != null && !ActivityUtils.isNetworkAvailable(this.context)) {
			updataBook();
			return;
		}
		tm.createNewTask(new TaskListener() {
			@Override
			public String getName() {
				return null;
			}

			@Override
			public void onPreExecute(BaseTask task) {
			}

			@Override
			public void onPostExecute(BaseTask task, String errorMsg) {

				if (errorMsg != null) {
					ActivityUtils.showToastForFail(context, "加载失败," + errorMsg);
				} else {
					BookJson bookJson = bookController.getModel();
					if (null == bookJson) {
						ActivityUtils.showToastForFail(context, "加载失败," + "返回空数据");
						return;
					} else {
						if (!TextUtils.isEmpty(bookJson.getCode()) && bookJson.getCode().equals(MyPreferences.SUCCESS)) {
							if (bookJson.getBookVO() != null) {
								bookVO = bookJson.getBookVO();
								serializeBooKVO(bookVO);
								updataBook();
							}
							return;
						} else if (bookJson.getCode().equals(MyPreferences.FAIL)) {
							ActivityUtils.showToastForFail(context, "加载失败," + bookJson.getCodeInfo());
							return;
						}
					}
				}

			}

			@Override
			public void onProgressUpdate(BaseTask task, Object param) {
			}

			@Override
			public void onCancelled(BaseTask task) {
			}

			@Override
			public String onDoInBackground(BaseTask task, MultiValueMap<String, String> param) {
				try {
					String str = BookSynActivity.this.bookController.getBookIntroduction(param);
					return str;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}).execute(params);
	}

	/**
	 * <p>
	 * Title
	 * </p>
	 * : getAttachOne Description: 获取一级附件
	 */
	private void getAttachOne() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", bookId);
		params.put("systemType", "android");
		Log.i("BookSynActivity", "bookId" + this.bookId);
		if (attachOne == null && !ActivityUtils.isNetworkAvailable(this.context)) {

			return;
		}
		if (attachOne != null) {
			updataBook();
			// return;
		}
		tm.createNewTask(new TaskListener() {
			@Override
			public String getName() {
				return null;
			}

			@Override
			public void onPreExecute(BaseTask task) {
			}

			@Override
			public void onPostExecute(BaseTask task, String errorMsg) {

				if (errorMsg != null) {// 获取数据出现异常
					ActivityUtils.showToastForFail(context, "加载失败," + errorMsg);
				} else {
					BookJson bookJson = bookController.getModel();
					SerializableUtil.serializeBookJson(context, bookId, bookJson);
					if (null == bookJson) {
						ActivityUtils.showToastForFail(context, "加载失败," + "返回空数据");
						return;
					} else {
						if (bookJson.getCode() != null && bookJson.getCode().equals(MyPreferences.SUCCESS) && null != bookJson.getAttachOnes()) {
							attachOnes = bookJson.getAttachOnes();
							if (attachOnes != null) {
								DbManager db = MyDbUtils.getOneAttachDb(context);
								try {
									List<AttachOne> list = db.selector(AttachOne.class).where("bookId", "=", bookId).findAll();
									if (list != null && list.size() > 0) {
										db.delete(list);
										db.save(attachOnes);
									} else {
										db.save(attachOnes);
									}
								} catch (DbException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							updataBook();
							return;
						} else if (!TextUtils.isEmpty(bookJson.getCode()) && bookJson.getCode().equals(MyPreferences.FAIL)) {
							ActivityUtils.showToastForFail(context, "加载失败," + bookJson.getCodeInfo());
							return;
						}
					}
				}

			}

			@Override
			public void onProgressUpdate(BaseTask task, Object param) {
			}

			@Override
			public void onCancelled(BaseTask task) {
			}

			@Override
			public String onDoInBackground(BaseTask task, MultiValueMap<String, String> param) {
				try {
					String str = BookSynActivity.this.bookController.getAttachOne(param);
					return str;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}).execute(params);
	}

	/**
	 * 将BookVO对象序列化到本地
	 */
	protected void serializeBooKVO(BookVO bookVO) {
		ObjectOutputStream oo = null;
		try {
			File target = new File(getCacheDir(), bookVO.getBookId());//
			oo = new ObjectOutputStream(new FileOutputStream(target, false));
			oo.writeObject(bookVO);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oo != null) {
				try {
					oo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 反序列化BooKVO
	 */
	protected BookVO unSerializeBookVO(String bookId) {
		BookVO bv = null;
		ObjectInputStream ois = null;
		try {
			File source = new File(getCacheDir(), bookId);
			ois = new ObjectInputStream(new FileInputStream(source));
			bv = (BookVO) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bv;
	}

	/**
	 * 加入购物车
	 * 
	 * @param v
	 */
	public void btnAddShopClick(View v) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", UserUtil.getInstance(BookSynActivity.this).getUserId());
		params.put("bookId", bookId);

		tm.createNewTask(new TaskListener() {
			@Override
			public String getName() {
				return null;
			}

			@Override
			public void onPreExecute(BaseTask task) {
			}

			@Override
			public void onPostExecute(BaseTask task, String errorMsg) {

				if (errorMsg != null) {
					ActivityUtils.showToastForFail(context, "操作失败!," + errorMsg);
				} else {
					BookJson bookJson = bookController.getModel();
					if (!TextUtils.isEmpty(bookJson.getCode())) {
						mBtn_Shop.setClickable(false);
						mBtn_Shop.setBackgroundColor(getResources().getColor(R.color.bg_grey));
						mBtn_Shop.setText(R.string.yijingjiaru);
						return;
					}
				}

			}

			@Override
			public void onProgressUpdate(BaseTask task, Object param) {
			}

			@Override
			public void onCancelled(BaseTask task) {
			}

			@Override
			public String onDoInBackground(BaseTask task, MultiValueMap<String, String> param) {
				try {
					String str = BookSynActivity.this.bookController.addShop(param);
					return str;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}).execute(params);
	}

	/**
	 * <p>
	 * Title
	 * </p>
	 * : updataBook Description: 更新界面
	 */
	public void updataBook() {
		gv_fujian.setAdapter(adapte_fujian);
		adapte_fujian.notifyDataSetChanged();
		if (bookVO == null)
			return;
		if (null != bookVO.getBookSize()) {
			tv_size.setText(ActivityUtils.bytes2kb(Long.parseLong(bookVO.getBookSize())));
		}
		if (null != bookVO.getBookAuthor()) {
			tv_author.setText(bookVO.getBookAuthor());
			SPUtility.putSPString(context, "author" + bookVO.getBookId(), bookVO.getBookAuthor());
		}
		if (bookVO.getBookPrice() != null) {
			tv_price.setText("¥" + bookVO.getBookPrice());
		}
//		if (bookVO.getIsPaided().equals("0")) {// 未支付
//			isShop = false;
//			mLayout_Shop.setVisibility(View.VISIBLE);
//			mLayout_Down.setVisibility(View.GONE);
//			btn_header_right.setBackgroundResource(getResources().getIdentifier("gouwuche", "drawable", getPackageName()));
//		} else {
			isShop = true;
			mLayout_Shop.setVisibility(View.GONE);
			mLayout_Down.setVisibility(View.VISIBLE);
			btn_header_right.setBackgroundResource(getResources().getIdentifier("booksyn_syn", "drawable", getPackageName()));
//		}
	}

	abstract class MyOnLongClickListener implements OnLongClickListener, OnTouchListener {
	};

	/**
	 * Title: FileAttachAdapter Description: 附件适配器 Company: btpd
	 * 
	 * @author Ping Wang
	 * @date 2015-11-13
	 */
	class FileAttachAdapter extends BaseAdapter {
		LayoutInflater inflater;
		View view;

		public FileAttachAdapter(Context context) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			if (attachOnes != null && attachOnes.size() > 0)
				return attachOnes.size();
			return 0;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		/**
		 * 把当前item对应的view对象返回回去
		 */
		public View getView(final int position, View convertView, ViewGroup parent) {
			// if (convertView != null) {
			// view = convertView;
			// } else {
			view = inflater.inflate(R.layout.fujian_item, parent, false);
			// }

			final String fujianName = attachOnes.get(position).getAttachOneName() + "." + attachOnes.get(position)
					.getAttachOneType();
			RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl_fujian);
			MyOnLongClickListener longClick = new MyOnLongClickListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP) {
						tv_header_title.setText("图书详情");
					}
					return false;
				}

				@Override
				public boolean onLongClick(View v) {
					tv_header_title.setText(attachOnes.get(position).getAttachOneName());
					Log.d(TAG, "------url===" + attachOnes.get(position).getShareUrl() + "--------issend===" + attachOnes.get(position).getShareUrl());
					if (TextUtils.isEmpty(attachOnes.get(position).getShareUrl()) || attachOnes.get(position).getIsSendAtta1().equals("0")) {
						ActivityUtils.showToastForFail(BookSynActivity.this, "此文件不能分享！");
					} else {
						shareAttachFile(position);
					}
					return true;
				}
			};
			rl.setOnLongClickListener(longClick);
			rl.setOnTouchListener(longClick);
			// 点击书籍阅读
			rl.setOnClickListener(new OnClickListener() {
				long lastClick;

				@Override
				public void onClick(View v) {
					if (System.currentTimeMillis() - lastClick <= 1000)
						return;
					lastClick = System.currentTimeMillis();
					Log.i("attachOnes", attachOnes.get(position).toString());

					if (attachOnes.get(position).getAttachOneIspackage() == 0) {
//						// 确认购买
//						if (bookVO.getIsPaided().equals("0")) {
//							DialogUtils.showMyDialog(BookSynActivity.this, MyPreferences.SHOW_SUCCESS_DIALOG, getString(R.string.tishi), getString(R.string.fufeitishi), new OnClickListener() {
//								@Override
//								public void onClick(View v) {
//									DialogUtils.dismissMyDialog();
//								}
//							});
//							return;
//						}

						if (!ActivityUtils.isExistByName(bookId, attachOnes.get(position).getAttachOneSaveName() + "." + attachOnes.get(position).getAttachOneType()) && !ActivityUtils
								.isExistByName(bookId, attachOnes.get(position).getAttachOneName())) {
							if (!ActivityUtils.NetSwitch(BookSynActivity.this, Boolean.parseBoolean(SPUtility.getSPString(BookSynActivity.this, "switchKey")))) {
								ActivityUtils.showToastForFail(BookSynActivity.this, "请在有WIFI的情况下下载");
								return;
							}
							unConnectedShowDialog();
							if (ActivityUtils.isNetworkAvailable(context)) {
								if (TextUtils.isEmpty(attachOnes.get(position).getShareUrl()) || attachOnes.get(position).getIsSendAtta1().equals("0")) {
									DialogUtils.showMyDialog(BookSynActivity.this, MyPreferences.SHOW_CONFIRM_DIALOG, "文件同步", "该文件未同步，是否开始同步？", new OnClickListener() {
										@Override
										public void onClick(View v) {

											ActivityUtils.showToast(BookSynActivity.this, "加入下载列表");
											downLoad(position);
											DialogUtils.dismissMyDialog();
										}
									});
								}else {
									String s1 = "暂不支持%s格式文件，";
//									String s2 = "可下载用其他应用打开或返回长按文件分享至电脑打开。";
									String s3 = String.format(s1, attachOnes.get(position).getAttachOneType());
									Spannable s4 = new SpannableString("可下载用其他应用打开或返回长按文件分享至电脑打开。");
									s4.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 14,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
									String content = s3+s4;
									DialogUtils.showMyDialog(BookSynActivity.this, MyPreferences.SHOW_DOWNLOAD_DIALOG, "分享&下载", content, new OnClickListener() {
										@Override
										public void onClick(View v) {
											ActivityUtils.showToast(BookSynActivity.this, "加入下载列表");
											downLoadFile(attachOnes.get(position).getAttachOnePath() + attachOnes.get(position).getAttachOneSaveName(), attachOnes.get(position).getAttachOneSaveName() + "." + attachOnes
													.get(position).getAttachOneType(),fujianName);
											DialogUtils.dismissMyDialog();
										}
									});
								}
							}

						} else {
							DataBaseHelper helper = new DataBaseHelper(context);
							List<DocInfo> infos = helper.getInfo2(bookId);
//							if (info != null) {
//
//								if (attachOnes.get(position).getAttachOneName().equals(info.getBookName())) {
//									if (info.getStatus() == DataBaseFiledParams.LOADING) {
//										downloadManager.pause(info);
//										info.setStatus(DataBaseFiledParams.PAUSING);
//									} else if (info.getStatus() == DataBaseFiledParams.PAUSING) {
//										downloadManager.startForActivity(info);
//										info.setStatus(DataBaseFiledParams.LOADING);
//									}
//								}
//
//							}
							if(infos!=null){
								for (DocInfo docInfo : infos) {
									if (fujianName.equals(docInfo.getBookName())) {

										if (docInfo.getStatus() == DataBaseFiledParams.PAUSING) {
											downloadManager.startForActivity(docInfo);
											docInfo.setStatus(DataBaseFiledParams.LOADING);
										} else if (docInfo.getStatus() == DataBaseFiledParams.WAITING) {

										} else if (docInfo.getStatus() == DataBaseFiledParams.FAILED) {

										}else if(docInfo.getStatus() == DataBaseFiledParams.LOADING){
											downloadManager.pause(docInfo);
											docInfo.setStatus(DataBaseFiledParams.PAUSING);
										}
									}
								}

								adapte_fujian.notifyDataSetChanged();
							}
							

							// 文件是否正在同步
							if (downloadManager.isDownloading(attachOnes.get(position))) {
//								ActivityUtils.showToast(BookSynActivity.this, "该文件正在同步请稍后。");
							} else {
								if (ActivityUtils.isExistByName(bookId, attachOnes.get(position).getAttachOneName())) {
									readFlash(position);
								} else {
									unLock(position);
								}
							}
						}
					} else if (attachOnes.get(position).getAttachOneIspackage() == 2) {
						String url = attachOnes.get(position).getAttachOneSaveName();
						ActivityUtils.readURL(context, attachOnes.get(position).getAttachOneName(), url);
					} else {
						Intent intent = new Intent(BookSynActivity.this, BookAttachActivity.class);
						intent.putExtra("bookId", bookId);
						intent.putExtra("attachOneId", attachOnes.get(position).getAttachOneId());
						intent.putExtra("fileName", attachOnes.get(position).getAttachOneSaveName());
						intent.putExtra("attachName", attachOnes.get(position).getAttachOneName());
						intent.putExtra("bookVO", bookVO);
						DbManager db = MyDbUtils.getTwoAttachDb(BookSynActivity.this);
						if (!ActivityUtils.isNetworkAvailable(context)) {
							try {
								List<AttachTwo> attachTwos = db.selector(AttachTwo.class).where("attachOneId", "=", attachOnes.get(position).getAttachOneId()).findAll();
								if (attachTwos != null && attachTwos.size() != 0)
									startActivity(intent);
							} catch (DbException e) {
								e.printStackTrace();
							}
							return;
						} else {
							startActivity(intent);
						}
					}
				}

			});
			// 设置书的封面
			ImageView iv = (ImageView) view.findViewById(R.id.iv_fujian);
			String type = attachOnes.get(position).getAttachOneType();
			String saveName = attachOnes.get(position).getAttachOneSaveName();

			TextView tv_download = (TextView) view.findViewById(R.id.tv_baifen);
			if (info != null) {
				if (fujianName.equals(info.getBookName())) {
					getStateSetView(tv_download, info);
				}

			} else {
				DataBaseHelper helper = new DataBaseHelper(context);
				List<DocInfo> infos = helper.getInfo2(bookId);
				for (DocInfo docInfo : infos) {
					if (fujianName.equals(docInfo.getBookName())) {
						getStateSetView(tv_download, docInfo);
					}
				}
			}

			if (type != null && (type.equalsIgnoreCase("jpg") | type.equalsIgnoreCase("png") || type.equalsIgnoreCase("gif"))) {
				if (ActivityUtils.isExistByName(bookId, saveName + "." + type)) {
					ActivityUtils.unLock(bookId, saveName + "." + type, saveName + "_copy." + type);
					iv.setBackground(Drawable.createFromPath(ActivityUtils.getSDPath(bookId) + File.separator + saveName + "_copy." + type));
//					LayoutParams layoutParams = iv.getLayoutParams();
//					layoutParams.width = DensityUtil.dip2px(context, 37);
//					layoutParams.height = DensityUtil.dip2px(context, 37);
//					iv.setLayoutParams(layoutParams);
				} else {
					iv.setBackgroundResource(ActivityUtils.getFilePackageImageId(context, attachOnes.get(position).getAttachOneIspackage(), attachOnes.get(position).getAttachOneType()));
				}
			} else {
				Log.d(TAG, "attachOnes.get(position).getAttachOneIspackage()========="+attachOnes.get(position).getAttachOneIspackage());
				iv.setBackgroundResource(ActivityUtils.getFilePackageImageId(context, attachOnes.get(position).getAttachOneIspackage(), attachOnes.get(position).getAttachOneType(),attachOnes.get(position).getAttachOneName()));
//				android.view.ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
//				layoutParams.width = DensityUtil.dip2px(BookSynActivity.this, 37);
//				layoutParams.height = DensityUtil.dip2px(BookSynActivity.this, 37);
//				iv.setLayoutParams(layoutParams);
			}

			// 红点
			ImageView iv_point_fujian = (ImageView) view.findViewById(R.id.iv_point_fujian);
			if (attachOnes.get(position).getAttachOneIspackage() == 0) {
				iv_point_fujian
						.setVisibility((ActivityUtils.isExistByName(bookId, attachOnes.get(position).getAttachOneSaveName() + "." + attachOnes.get(position).getAttachOneType()) || ActivityUtils
								.isExistByName(bookId, attachOnes.get(position).getAttachOneName())) ? View.GONE : View.VISIBLE);
			} else {
				iv_point_fujian.setVisibility(View.GONE);
			}
			// 附件名称
			TextView tv_fujian = (TextView) view.findViewById(R.id.tv_fujian);
			tv_fujian.setText(StringUtil.subString(attachOnes.get(position).getAttachOneName(), 7, "..."));

			// 设置是否可以分享
			ImageView iv_share = (ImageView) view.findViewById(R.id.imageView1);
			if (TextUtils.isEmpty(attachOnes.get(position).getShareUrl()) || attachOnes.get(position).getIsSendAtta1().equals("0"))
				iv_share.setVisibility(View.GONE);
			else
				iv_share.setVisibility(View.VISIBLE);

			return view;
		}
	}

	public void getStateSetView(TextView tv_download, DocInfo docInfo) {
		if (docInfo.getStatus() == DataBaseFiledParams.PAUSING) {
			tv_download.setText("暂停");
		} else if (docInfo.getStatus() == DataBaseFiledParams.WAITING) {
			tv_download.setText("等待中");
		} else if (docInfo.getStatus() == DataBaseFiledParams.FAILED) {
			tv_download.setText("服务器忙");
		} else {
			Log.d(TAG, docInfo.getBookName()+"---------------Status==="+docInfo.getStatus());
			tv_download.setText(docInfo.getDownloadProgress() + "%");
			if (tv_download.getText().toString().trim().equals("100%")) {
				tv_download.setText("");
			}
		}
	}

	/**
	 * <p>
	 * Title
	 * </p>
	 * : shareAttachFile Description: 文件分享
	 * 
	 * @param position
	 */
	private void shareAttachFile(final int position) {
		DialogUtils.showMyDialog(context, MyPreferences.SHOW_CONFIRM_DIALOG, attachOnes.get(position).getAttachOneName(), "您要分享此文件给好友吗？", new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogUtils.dismissMyDialog();
				ActivityUtils.ShareLink(BookSynActivity.this, attachOnes.get(position).getShareUrl());
			}
		});
	}

	private void unLock(final int position) {
		String path = SPUtility.getSPString(this, "mPath");
		String savePath = attachOnes.get(position).getAttachOneSaveName() + "_copy." + attachOnes.get(position).getAttachOneType();
		if (TextUtils.isEmpty(path) && path.equals(ActivityUtils.getSDPath(bookId).getAbsolutePath() + "/" + savePath)) {
			Intent intent = new Intent(this, MediaPlayerActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			return;
		}
		DialogUtils.showMyDialog(context, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在加载中，请稍后...", null);
		unLockFile(attachOnes.get(position).getAttachOneSaveName() + "." + attachOnes.get(position).getAttachOneType(), attachOnes.get(position).getAttachOneSaveName() + "_copy." + attachOnes
				.get(position).getAttachOneType(), attachOnes.get(position).getAttachOneType(), attachOnes.get(position).getAttachOneName());
	}

	private void readFlash(final int position) {
		String fileName = attachOnes.get(position).getAttachOneName() + "/Index.html";
		ActivityUtils.deleteBookFormSD(bookId, attachOnes.get(position).getAttachOneSaveName());
		ActivityUtils.deleteBookFormSD(bookId, attachOnes.get(position).getAttachOneSaveName() + ".zip");
		ActivityUtils.readFile(context, bookId, fileName, "html", bookName);
	}

	private void downLoad(final int position) {
		downLoadFile(attachOnes.get(position).getAttachOnePath() + attachOnes.get(position).getAttachOneSaveName(), attachOnes.get(position).getAttachOneSaveName() + "." + attachOnes.get(position)
				.getAttachOneType(), attachOnes.get(position).getAttachOneName() + "." + attachOnes.get(position)
				.getAttachOneType());
		Log.i("BookSynActivity", attachOnes.get(position).getAttachOnePath() + attachOnes.get(position).getAttachOneSaveName());
		Log.i("BookSynActivity", attachOnes.get(position).getAttachOneSaveName() + "." + attachOnes.get(position).getAttachOneType());
		Log.i("BookSynActivity", attachOnes.get(position).getAttachOneSaveName());
	}

	/**
	 * Title: unLockFile Description: 解密文件
	 * 
	 * @param soureFileName
	 *            接收到的原路径
	 * @param saveFileName
	 *            需要保存的路径
	 * @param fileType
	 *            文件类型
	 * @param bookName
	 *            书名
	 */
	public void unLockFile(final String soureFileName, final String saveFileName, final String fileType, final String bookName) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				ActivityUtils.unLock(bookId, soureFileName, saveFileName);
				Message msg = new Message();
				msg.what = 1;
				Bundle bundle = new Bundle();
				bundle.putString("fileName", saveFileName);
				bundle.putString("fileType", fileType);
				bundle.putString("bookName", bookName);
				msg.setData(bundle);
				mHandler.sendMessage(msg);
			}
		}).start();

	}

	/**
	 * Title: deleteFileAttach Description: 删除解密后的文件
	 */
	public void deleteFileAttach() {
		if (null != attachOnes && attachOnes.size() > 0) {
			for (AttachOne attach : attachOnes) {
				if (attach.getAttachOneIspackage() == 0) {
					ActivityUtils.deleteBookFormSD(bookId, attach.getAttachOneSaveName() + "_copy." + attach.getAttachOneType());
				}
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			// 删除解密后的文件
			deleteFileAttach();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 图片加载第一次显示监听器
	 * 
	 * @author Administrator
	 * 
	 */
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				// 是否第一次显示
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					// 图片淡入效果
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	/**
	 * Title: btnCode Description: 二维码扫描
	 * 
	 * @param v
	 */
	public void btnCode(View v) {
		Intent intent = new Intent();
		intent.setClass(this, MipcaActivityCapture.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, MyPreferences.SCANNIN_GREQUEST_CODE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case MyPreferences.SCANNIN_GREQUEST_CODE:
				if (resultCode == RESULT_OK) {
					Bundle bundle = data.getExtras();
					ActivityUtils.readFileForJiaoda(BookSynActivity.this, bundle.getString("result"));
				}
				break;
		}
	}

	public void btn_search(View v) {
		Intent intent = new Intent(this, SearchActivity.class);
		intent.putExtra("bookId", bookId);
		intent.putExtra("image_url", image_url_str);
		intent.putExtra("bookname", bookName);
		Bundle bundle = new Bundle();
		if (this.bookVO != null) {
			bundle.putSerializable("bookVO", bookVO);
			intent.putExtra("bundle", bundle);
			startActivity(intent);
		}
	}

	public void btn_bookInfo(View v) {
		Intent intent = new Intent(this, BookDescriptionActivity.class);
		intent.putExtra("bookId", bookId);
		intent.putExtra("image_url", image_url_str);
		intent.putExtra("bookname", bookName);
		Bundle bundle = new Bundle();
		if (this.bookVO != null) {
			bundle.putSerializable("bookVO", bookVO);
			intent.putExtra("bundle", bundle);
			startActivity(intent);
		}

	}

	public void author_communication(View v) {
		if (!ActivityUtils.isNetworkAvailable(context)) {
			ActivityUtils.showToast(context, "无法连接网络，加载载失败");
			return;
		}
		Intent intent = new Intent(this, AuthorCommunicationActivity.class);
		intent.putExtra("bookId", bookId);
		intent.putExtra("image_url", image_url_str);
		intent.putExtra("bookname", bookName);
		Bundle bundle = new Bundle();
		if (this.bookVO != null) {
			bundle.putSerializable("bookVO", bookVO);
			intent.putExtra("bundle", bundle);
			startActivity(intent);
		}

	}

	public void reader_communication(View v) {
		if (!ActivityUtils.isNetworkAvailable(context)) {
			ActivityUtils.showToast(context, "无法连接网络，加载载失败");
			return;
		}
		Intent intent = new Intent(this, ReaderCommunicationActivity.class);
		intent.putExtra("bookId", bookId);
		intent.putExtra("image_url", image_url_str);
		intent.putExtra("bookname", bookName);
		Bundle bundle = new Bundle();
		if (this.bookVO != null) {
			bundle.putSerializable("bookVO", bookVO);
			intent.putExtra("bundle", bundle);
			startActivity(intent);
		}
	}

	public void online_video(View v) {

	}

	@Override
	protected void onDestroy() {

		if (null != broadcastReciver) {
			this.unregisterReceiver(broadcastReciver);
		}
		deleteFileAttach();

		// ActivityUtils.deleteBookFormSD(bookId, bookId+"copy.xml");
		getApplicationContext().removeActivity(this);
		super.onDestroy();
	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				getAttachOne();
				refresh_view.setRefreshing(false);
			}
		}, 2000);

	}

}
