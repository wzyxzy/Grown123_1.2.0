package com.pndoo.grown123_new;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.controller.BookController;
import com.pndoo.grown123_new.download.TestService;
import com.pndoo.grown123_new.download2.db.DataBaseFiledParams;
import com.pndoo.grown123_new.download2.db.DataBaseHelper;
import com.pndoo.grown123_new.download2.entity.DocInfo;
import com.pndoo.grown123_new.download2.utils.DownloadManager;
import com.pndoo.grown123_new.download2.utils.DownloadManager.DownloadListener;
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

import org.springframework.util.MultiValueMap;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class BookAttachActivity extends BaseActivity implements OnRefreshListener {
	ImageButton btn_lookjd;
	LinearLayout sv_container;
	LinearLayout ll_container;
	LinearLayout ll_content;
	// ProgressBar progressBar;
	TextView tv_errorMsg;
	String bookId;
	String fileId;
	String fileName;
	GridView gv;
	AttachTwoAdapter adapter;
	String attachOneId;
	AttachTwo attachTwo;
	TaskManager tm;
	BookController bookController;
	String errorMsg;
	DownloadManager downloadManager;
	List<AttachTwo> attachTwos;
	private BookVO bookVO;
	private final String tag = getClass().getSimpleName();

	BroadcastReceiver broadcastReceiver;
	Handler mHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					DialogUtils.dismissMyDialog();
					ActivityUtils.readFile(BookAttachActivity.this, bookId, msg.getData().getString("fileName"), msg.getData().getString("fileType"), msg.getData().getString("bookName"));
					break;

				default:
					break;
			}
			return false;
		}
	});

	private TextView tv_header_title;
	private String attachName;
	private DbManager db;
	private SwipeRefreshLayout refresh_view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getApplicationContext().addActivity(this);
		tm = getApplicationContext().getTaskManager();
		bookController = IoC.getInstance(BookController.class);
		downloadManager = DownloadManager.getInstance(this);
		setContentView(R.layout.book_attach);
		db = MyDbUtils.getTwoAttachDb(BookAttachActivity.this);
		initView();
		initDownload(this);
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
				// Log.d(TAG,
				// "----callback--------progress=="+info.getDownloadProgress());
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
//					ActivityUtils.showToastForSuccess(BookAttachActivity.this, info.getBookName() + "下载完成");
//				}

				if(info.getName().endsWith(".zip")){
					new Thread() {
						public void run() {
							try {
								unZip(docInfo);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						};
					}.start();
				}
			}
		});
	}

	private DocInfo info;

	public void initView() {
		refresh_view = (SwipeRefreshLayout) findViewById(R.id.refresh_view);
		refresh_view.setOnRefreshListener(this);
		refresh_view.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		bookId = getIntent().getStringExtra("bookId");
		attachOneId = getIntent().getStringExtra("attachOneId");
		fileName = getIntent().getStringExtra("fileName");
		attachName = getIntent().getStringExtra("attachName");
		bookVO = (BookVO) getIntent().getSerializableExtra("bookVO");
		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
		tv_header_title.setText(attachName);
		sv_container = (LinearLayout) findViewById(R.id.sv_container);
		ll_container = (LinearLayout) findViewById(R.id.ll_container);
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		// progressBar = new ProgressBar(this);
		// progressBar.setLayoutParams(new LayoutParams(100, 100));
		tv_errorMsg = new TextView(this);
		ll_container.removeAllViews();
		// ll_container.addView(progressBar);
		btn_lookjd = (ImageButton) findViewById(R.id.btn_header_right);
		btn_lookjd.setVisibility(View.VISIBLE);
		btn_lookjd.setBackgroundResource(getResources().getIdentifier("booksyn_syn", "drawable", getPackageName()));
		btn_lookjd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BookAttachActivity.this, SynManageActivity.class);
				startActivity(intent);

			}
		});
		Log.i("attachOneId", attachOneId);
		try {
			attachTwos = db.selector(AttachTwo.class).where("attachOneId", "=", attachOneId).findAll();
		} catch (DbException e) {
			e.printStackTrace();
		}
		// BookJson bookJson = SerializableUtil.unSerializeBookJson(this,
		// bookId);
		// attachTwos = bookJson.getAttachTwos();

		gv = (GridView) ll_content.findViewById(R.id.gv_fujian);
		adapter = new AttachTwoAdapter(this);
		if (attachTwos == null || attachTwos.size() == 0) {
			getAttachTwo();
		} else {
			updataContent();
			addContent();
		}

		broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals("android.intent.action.test")) {
					String flag = intent.getStringExtra("flag");
					Bundle bundle = intent.getBundleExtra("bundle");
					DocInfo info = (DocInfo) bundle.getSerializable("info");
					if (flag.equals("success") && isFileDownloaded(info.getName()) && null != adapter && null != gv) {
						Log.i("info", info.getName());
//						if (!ActivityUtils.isExistByName(bookId, info.getBookName())) {
//							try {
//								unZip(info);
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
						ActivityUtils.showToastForSuccess(BookAttachActivity.this, info.getBookName() + "下载完成");
						adapter.notifyDataSetChanged();
						gv.invalidateViews();
					}

					if (flag.equals("update")) {
						// Log.d(TAG,
						// "----broadcast--------progress=="+info.getDownloadProgress());
						adapter.notifyDataSetChanged();
						System.out.println("info.getDownloadProgress" + info.getDownloadProgress());
						System.out.println("info.getDownloadProgress finish");
						return;
					}

				} else if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
					if (null != intent.getStringExtra("reason") && intent.getStringExtra("reason").equals("homekey")) {
						deleteAttachOne();
						return;
					}
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.test");
		filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		registerReceiver(broadcastReceiver, filter);
	}
	
	public void unZip(DocInfo info) throws ZipException, IOException {
		String bookName = info.getName();
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

	public void btnBack(View v) {
		this.finish();
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
			ActivityUtils.showToastForFail(BookAttachActivity.this, "未检测到SD卡");
		}
	}

	private String getPath(String url) {
		String path = url + "&userId=" + UserUtil.getInstance(BookAttachActivity.this).getUserId() + "&bookId=" + bookId;
		return path;
	}

	@Override
	protected void onResume() {
		DialogUtils.dismissMyDialog();
		super.onResume();
	}

	private void getAttachTwo() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("attachOneId", attachOneId);
		params.put("systemType", "android");
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
					ActivityUtils.showToast(BookAttachActivity.this, "加载失败," + errorMsg);
				} else {
					boolean networkAvailable = ActivityUtils.isNetworkAvailable(BookAttachActivity.this);
					BookJson bookJson = null;
					if (networkAvailable) {
						bookJson = bookController.getModel();
						SerializableUtil.serializeBookJson(BookAttachActivity.this, bookId, bookJson);

					}
					/*
					 * else { bookJson = SerializableUtil.unSerializeBookJson(
					 * BookAttachActivity.this, bookId);
					 * 
					 * if (bookJson != null && bookJson.getCode() == null) {
					 * bookJson.setCode(MyPreferences.SUCCESS); } }
					 */

					if (null == bookJson) {
						ActivityUtils.showToast(BookAttachActivity.this, "加载失败," + "返回空数据");
						return;
					} else {
						if (bookJson.getCode().equals(MyPreferences.SUCCESS) && null != bookJson.getAttachTwos()) {
							// file = bookJson.getBookVO();
							attachTwos = bookJson.getAttachTwos();
							if (attachTwos != null) {
								try {
									List<AttachTwo> list = db.selector(AttachTwo.class).where("attachOneId", "=", attachTwos.get(0).getAttachOneId()).and("bookId", "=", bookId).findAll();
									if (list != null && list.size() > 0) {
										db.delete(list);
										db.save(attachTwos);
									} else {
										db.save(attachTwos);
									}
								} catch (DbException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							Log.i("BookAttachActivity", attachTwos.toString());
							updataContent();
							addContent();

							return;
						} else if (bookJson.getCode().equals(MyPreferences.FAIL)) {
							ActivityUtils.showToast(BookAttachActivity.this, "加载失败," + bookJson.getCodeInfo());
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
				String errorMsg = null;
				try {
					errorMsg = bookController.getBookAttachTwo(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return errorMsg;
			}
		}).execute(params);

	}

	/**
	 * 更新界面
	 */
	public void updataContent() {
		gv.setAdapter(adapter);
	}

	/**
	 * 显示错误信息
	 * 
	 * @param errorMsg
	 */
	public void addTextViewForError(String errorMsg) {
		tv_errorMsg.setText(errorMsg);
		tv_errorMsg.setGravity(Gravity.CENTER);
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
	public void addProgressBar(String errorMsg) {
		if (ll_container.getChildCount() > 0) {
			ll_container.removeAllViews();
		}
		// ll_container.addView(progressBar);
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
	protected void onDestroy() {
		if (null != broadcastReceiver) {
			this.unregisterReceiver(broadcastReceiver);
		}
		deleteAttachOne();
		btn_lookjd.setVisibility(View.GONE);
		getApplicationContext().removeActivity(this);
		super.onDestroy();
	}

	/**
	 * 删除解密后的文件
	 */
	public void deleteAttachOne() {
		if (null != attachTwo && attachTwos.size() > 0) {
			for (AttachTwo attachTwo : attachTwos) {
				ActivityUtils.deleteBookFormSD(bookId, attachTwo.getAttachTwoId() + "_copy." + attachTwo.getAttachTwoType());
			}
		}
	}

	abstract class MyOnLongClickListener implements OnLongClickListener, OnTouchListener {
	};

	/**
	 * Title: AttachTwoAdapter Description: 附件2适配器 Company: btpd
	 * 
	 * @author Ping Wang
	 * @date 2015-8-24
	 */
	class AttachTwoAdapter extends BaseAdapter {
		LayoutInflater inflater;
		View view;

		public AttachTwoAdapter(Context context) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		public int getCount() {
			return attachTwos.size();
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
			if (convertView != null) {
				view = convertView;
			} else {
				view = inflater.inflate(R.layout.fujian_item, parent, false);
			}

			
			final String fujianName = attachTwos.get(position).getAttachTwoName() + "." + attachTwos
					.get(position).getAttachTwoType();
			
			RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl_fujian);
			MyOnLongClickListener longClick = new MyOnLongClickListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP) {
						tv_header_title.setText(attachName);
					}
					return false;
				}

				@Override
				public boolean onLongClick(View v) {
					tv_header_title.setText(attachTwos.get(position).getAttachTwoName());
					if (TextUtils.isEmpty(attachTwos.get(position).getShareUrl()) || attachTwos.get(position).getIsSendAtta2().equals("0")) {
						ActivityUtils.showToastForFail(BookAttachActivity.this, "此文件不能分享！");

					} else {
						DialogUtils.showMyDialog(BookAttachActivity.this, MyPreferences.SHOW_CONFIRM_DIALOG, attachTwos.get(position).getAttachTwoName(), "您要分享此文件给好友吗？", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								DialogUtils.dismissMyDialog();
								// if
								// (TextUtils.isEmpty(attachTwos.get(position).getShareUrl()))
								// {
								// ActivityUtils.showToastForFail(BookAttachActivity.this,"此文件不能分享！");
								// return;
								// }
								ActivityUtils.ShareLink(BookAttachActivity.this, attachTwos.get(position).getShareUrl());
							}
						});
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
					if (System.currentTimeMillis() - lastClick <= 1000) {
						Log.i("onclick", "您点击的太快了");
						return;
					}
					lastClick = System.currentTimeMillis();
					btn_lookjd.setVisibility(View.VISIBLE);
					// tv_baifen = (TextView)v.findViewById(R.id.tv_baifen);
					// 文件是否存在
					if (attachTwos.get(position).getAttachTwoIspackage() == 0) {

						// 确认购买
						// if (bookVO.getIsPaided().equals("0")) {
						// DialogUtils.showMyDialog(BookAttachActivity.this,
						// MyPreferences.SHOW_SUCCESS_DIALOG,
						// getString(R.string.tishi),
						// getString(R.string.fufeitishi), new OnClickListener()
						// {
						// @Override
						// public void onClick(View v) {
						// DialogUtils.dismissMyDialog();
						// }
						// });
						// return;
						// }

						if (!ActivityUtils.isExistByName(bookId, attachTwos.get(position).getAttachTwoSaveName() + "." + attachTwos.get(position).getAttachTwoType()) && !ActivityUtils
								.isExistByName(bookId, attachTwos.get(position).getAttachTwoName())) {
							if (!ActivityUtils.NetSwitch(BookAttachActivity.this, Boolean.parseBoolean(SPUtility.getSPString(BookAttachActivity.this, "switchKey")))) {
								ActivityUtils.showToastForFail(BookAttachActivity.this, "请在有WIFI的情况下下载");
								return;
							}
							if (TextUtils.isEmpty(attachTwos.get(position).getShareUrl()) || attachTwos.get(position).getIsSendAtta2().equals("0")) {
								DialogUtils.showMyDialog(BookAttachActivity.this, MyPreferences.SHOW_CONFIRM_DIALOG, "文件同步", "该文件未同步，是否开始同步？", new OnClickListener() {
									@Override
									public void onClick(View v) {
										ActivityUtils.showToast(BookAttachActivity.this, "加入下载列表");
										downLoadFile(attachTwos.get(position).getAttachTwoPath() + attachTwos.get(position).getAttachTwoSaveName(), attachTwos.get(position).getAttachTwoSaveName() + "." + attachTwos.get(position).getAttachTwoType(), fujianName);
										DialogUtils.dismissMyDialog();
									}
								});
							} else {
								String s1 = "暂不支持%s格式文件，";
								// String s2 = "可下载用其他应用打开或返回长按文件分享至电脑打开。";
								String s3 = String.format(s1, attachTwos.get(position).getAttachTwoType());
								SpannableString s4 = new SpannableString("可下载用其他应用打开或返回长按文件分享至电脑打开。");
								s4.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

								String content = s3 + s4;
								DialogUtils.showMyDialog(BookAttachActivity.this, MyPreferences.SHOW_DOWNLOAD_DIALOG, "分享&下载", content, new OnClickListener() {
									@Override
									public void onClick(View v) {
										ActivityUtils.showToast(BookAttachActivity.this, "加入下载列表");
										downLoadFile(attachTwos.get(position).getAttachTwoPath() + attachTwos.get(position).getAttachTwoSaveName(), attachTwos.get(position).getAttachTwoSaveName() + "." + attachTwos.get(position).getAttachTwoType(),fujianName);
										DialogUtils.dismissMyDialog();
									}
								});
							}

						} else {
							DataBaseHelper helper = new DataBaseHelper(getBaseContext());
							List<DocInfo> infos = helper.getInfo2(bookId);
							if (info != null) {

								if (fujianName.equals(info.getBookName())) {
									if (info.getStatus() == DataBaseFiledParams.LOADING) {
										downloadManager.pause(info);
										info.setStatus(DataBaseFiledParams.PAUSING);
									} else if (info.getStatus() == DataBaseFiledParams.PAUSING) {
										downloadManager.startForActivity(info);
										info.setStatus(DataBaseFiledParams.LOADING);
									}
								}

							}
							for (DocInfo docInfo : infos) {
								if (fujianName.equals(docInfo.getBookName())) {

									if (docInfo.getStatus() == DataBaseFiledParams.PAUSING) {
										downloadManager.startForActivity(docInfo);
										docInfo.setStatus(DataBaseFiledParams.LOADING);
									} else if (docInfo.getStatus() == DataBaseFiledParams.WAITING) {

									} else if (docInfo.getStatus() == DataBaseFiledParams.FAILED) {

									}

								}
							}

							adapter.notifyDataSetChanged();

							// 文件是否正在同步
							if (downloadManager.isDownloading(attachTwos.get(position))) {
								DialogUtils.showMyDialog(BookAttachActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "文件同步", "该文件正在同步请稍后。", null);
							} else {
								if (ActivityUtils.isExistByName(bookId, attachTwos.get(position).getAttachTwoName())) {
									String fileName = attachTwos.get(position).getAttachTwoName() + "/Index.html";
									ActivityUtils.deleteBookFormSD(bookId, attachTwos.get(position).getAttachTwoSaveName());
									ActivityUtils.deleteBookFormSD(bookId, attachTwos.get(position).getAttachTwoSaveName() + ".zip");
									ActivityUtils.readFile(BookAttachActivity.this, bookId, fileName, "html", attachTwos.get(position).getAttachTwoName());
								} else {
									DialogUtils.showMyDialog(BookAttachActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在加载中，请稍后...", null);
									unLockFile(attachTwos.get(position).getAttachTwoSaveName() + "." + attachTwos.get(position).getAttachTwoType(), attachTwos.get(position).getAttachTwoSaveName() + "_copy." + attachTwos
											.get(position).getAttachTwoType(), attachTwos.get(position).getAttachTwoType(), attachTwos.get(position).getAttachTwoName());
								}
							}
						}

					}else if (attachTwos.get(position).getAttachTwoIspackage() == 2) {
						String url = attachTwos.get(position).getAttachTwoSaveName();
						ActivityUtils.readURL(BookAttachActivity.this, attachTwos.get(position).getAttachTwoName(), url);
					}
				}
			});
			String type = attachTwos.get(position).getAttachTwoType();
			String saveName = attachTwos.get(position).getAttachTwoSaveName();
			// 设置书的封面
			ImageView iv = (ImageView) view.findViewById(R.id.iv_fujian);

			TextView tv_download = (TextView) view.findViewById(R.id.tv_baifen);

			if (info != null) {

				if (fujianName.equals(info.getBookName())) {
					getStateSetView(tv_download, info);
				}

			} else {
				DataBaseHelper helper = new DataBaseHelper(getBaseContext());
				List<DocInfo> infos = helper.getInfo2(bookId);
				for (DocInfo docInfo : infos) {
					if (fujianName.equals(docInfo.getBookName())) {
						getStateSetView(tv_download, docInfo);
					}
				}
			}
			if (type != null && (type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("png") || type.equalsIgnoreCase("gif"))) {

				if (ActivityUtils.isExistByName(bookId, saveName + "." + type)) {
					ActivityUtils.unLock(bookId, saveName + "." + type, saveName + "_copy." + type);
					iv.setBackground(Drawable.createFromPath(ActivityUtils.getSDPath(bookId) + File.separator + saveName + "_copy." + type));
					// android.view.ViewGroup.LayoutParams layoutParams =
					// iv.getLayoutParams();
					// layoutParams.width =
					// DensityUtil.dip2px(BookAttachActivity.this, 37);
					// layoutParams.height =
					// DensityUtil.dip2px(BookAttachActivity.this, 37);
					// iv.setLayoutParams(layoutParams);

				} else {
					iv.setBackgroundResource(ActivityUtils
							.getFilePackageImageId(BookAttachActivity.this, attachTwos.get(position).getAttachTwoIspackage(), attachTwos.get(position).getAttachTwoType()));
				}
			} else {
				if (attachTwos.get(position).getAttachTwoType() != null && attachTwos.get(position).getAttachTwoType().equals("zip")) {
					iv.setBackgroundResource(ActivityUtils.getFileImageId(BookAttachActivity.this, "html"));
				} else {
					// iv.setBackgroundResource(ActivityUtils.getFileImageId(BookAttachActivity.this,
					// attachTwos.get(position).getAttachTwoType()));
					Log.d(tag, "attachTwos.get(position).getAttachTwoIspackage()=" + attachTwos.get(position).getAttachTwoIspackage() + "------attachTwos.get(position).getAttachTwoType()=" + attachTwos
							.get(position).getAttachTwoType());
					iv.setBackgroundResource(ActivityUtils
							.getFilePackageImageId(BookAttachActivity.this, attachTwos.get(position).getAttachTwoIspackage(), attachTwos.get(position).getAttachTwoType(), attachTwos.get(position)
									.getAttachTwoName()));
					// android.view.ViewGroup.LayoutParams layoutParams =
					// iv.getLayoutParams();
					// layoutParams.width =
					// DensityUtil.dip2px(BookAttachActivity.this, 37);
					// layoutParams.height =
					// DensityUtil.dip2px(BookAttachActivity.this, 37);
					// iv.setLayoutParams(layoutParams);

				}
			}
			// 修饰文件包
			ImageView iv_point_fujian = (ImageView) view.findViewById(R.id.iv_point_fujian);
			if (attachTwos.get(position).getAttachTwoIspackage() == 0) {
				if (ActivityUtils.isExistByName(bookId, attachTwos.get(position).getAttachTwoSaveName() + "." + attachTwos.get(position).getAttachTwoType()) || ActivityUtils
						.isExistByName(bookId, attachTwos.get(position).getAttachTwoName())) {
					iv_point_fujian.setVisibility(View.GONE);
				} else {
					iv_point_fujian.setVisibility(View.VISIBLE);
				}
			} else {
				iv_point_fujian.setVisibility(View.GONE);
			}
			// 附件名称
			TextView tv_fujian = (TextView) view.findViewById(R.id.tv_fujian);
			tv_fujian.setSelected(true);
			tv_fujian.setText(StringUtil.subString(attachTwos.get(position).getAttachTwoName(), 10, "..."));

			// 设置是否可以分享
			ImageView iv_share = (ImageView) view.findViewById(R.id.imageView1);
			if (TextUtils.isEmpty(attachTwos.get(position).getShareUrl()) || attachTwos.get(position).getIsSendAtta2().equals("0"))
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
			tv_download.setText(docInfo.getDownloadProgress() + "%");
			if (tv_download.getText().toString().trim().equals("100%")) {
				tv_download.setText("");
			}
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
		if (attachTwos != null && attachTwos.size() > 0) {
			for (AttachTwo attachTwo : attachTwos) {
				String fileName = attachTwo.getAttachTwoSaveName() + "." + attachTwo.getAttachTwoType();
				if (fileName.equals(fileSDName)) {
					return true;
				}
			}
		}
		return false;
	}

	public void unLockFile(final String soureFileName, final String saveFileName, final String fileType, final String bookName) {
		DialogUtils.showMyDialog(BookAttachActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在加载资源", null);
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

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				getAttachTwo();
				adapter.notifyDataSetChanged();
				refresh_view.setRefreshing(false);
			}
		}, 2000);

	}
}
