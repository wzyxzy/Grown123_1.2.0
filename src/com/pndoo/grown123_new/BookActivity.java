package com.pndoo.grown123_new;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.controller.BookController;
import com.pndoo.grown123_new.dto.base.BookVO;
import com.pndoo.grown123_new.dto.base.Update;
import com.pndoo.grown123_new.dto.base.UserVO;
import com.pndoo.grown123_new.main.IoC;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.service.UserDAO;
import com.pndoo.grown123_new.soap.BookJson;
import com.pndoo.grown123_new.task.BaseTask;
import com.pndoo.grown123_new.task.TaskListener;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DensityUtil;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyDbUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.SPUtility;
import com.umeng.analytics.MobclickAgent;

import org.springframework.util.MultiValueMap;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint({ "NewApi", "ViewHolder", "InflateParams" })
public class BookActivity extends BaseActivity implements OnRefreshListener {

	public String image_url;
	int poisition;
	MyAdapter adapter;// 书架的适配器
	private ListView lv_books;// 书架的listView
	// private TextView tv_publishName;// 出版社名称
	private List<BookVO> books = new ArrayList<BookVO>();// 书籍列表数据
	private Handler mHandler;
	private int screenWidth;// 屏幕宽
	DisplayImageOptions options;
	TaskManager tm;
	BookController bookController;
	String pageNo = "1";// 页码
	String pageSize = "10";// 每页
	String errorMsg;
	UserDAO dao;
	private ImageButton music;
	private LinearLayout ll_music;
	private final String TAG = getClass().getSimpleName();
	private SwipeRefreshLayout refresh_view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getApplicationContext().addActivity(this);
		setContentView(R.layout.book);
		tm = getApplicationContext().getTaskManager();
		bookController = IoC.getInstance(BookController.class);
		Update update = bookController.getModel().getUpdate();
		if (update != null) {
			if ("2".equals(update.getIsUpdate())) {
				if (ActivityUtils.isWifiEnabled(this)) {
					finish();
				}
			}
		}
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
//		initTitle();
		initView();
		initBook();
	}

//	/**
//	 * 初始化标题
//	 */
//	private void initTitle() {
//		LinearLayout ll_header = (LinearLayout) BookActivity.this.getParent().findViewById(R.id.layout_header);
//		if (null != ll_header) {
//			ll_header.setVisibility(View.GONE);
//		}
//	}

	/**
	 * 初始化界面
	 */
	public void initView() {
		dao = new UserDAO(this);
		screenWidth = ActivityUtils.getScreenWidth(this);
		// gv_book = (GridView) findViewById(R.id.gv_main);
		
		refresh_view = (SwipeRefreshLayout) findViewById(R.id.refresh_view);
		refresh_view.setOnRefreshListener(this);
		refresh_view.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		
		lv_books = (ListView) findViewById(R.id.xListView);
		adapter = new MyAdapter(this);
		lv_books.setAdapter(adapter);
		// tv_publishName = (TextView) findViewById(R.id.tv_publishName);
		music = (ImageButton) findViewById(R.id.music);
		ll_music = (LinearLayout) findViewById(R.id.ll_music);

		// Typeface face =
		// Typeface.createFromAsset(getAssets(),"fonts/chinese_travel_model.ttf");
		// tv_publishName.setTypeface(face);
		// }
		mHandler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
					case 1:
						break;
					case 2:
						adapter.notifyDataSetChanged();
						refresh_view.setRefreshing(false);
						break;
					default:
						break;
				}
				return false;
			}
		});
	}

	/**
	 * 初始化图书列表数据
	 */
	private void initBook() {
		String str = SPUtility.getSPString(this, "booksData");
		// FinalDb db=FinalDb.create(this, "bookvo.db");
		if (!str.equals("")) {
			try {
				String[] strBooks = str.split(";");
				for (int i = 0; i < strBooks.length; i++) {
					String[] strBook = strBooks[i].split(":");
					BookVO bookVO = new BookVO();
					bookVO.setBookId(strBook[0]);
					bookVO.setBookName(strBook[1]);
					bookVO.setBookImg(strBook[2]);
					String strSaveName = strBook[2].substring(0, strBook[2].lastIndexOf("."));
					bookVO.setBookSaveName(strSaveName);
					bookVO.setBookImgPath(strBook[3]);
					books.add(bookVO);

					// bookVO.setIspackage(Integer.parseInt(strBook[0]));
					// db.save(bookVO);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * 增加图书按钮
	 * 
	 * @param v
	 */
	public void btnAdd(View v) {
		Intent intent = new Intent(this, MipcaActivityCapture.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, MyPreferences.ADD_ACTIVITY);
	}

	@Override
	public void onRefresh() {

		new Thread(new Runnable() {
			@Override
			public void run() {

				findBooks();
			}
		}).start();

	}



	/**
	 * 书架adapter
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		View view;

		public MyAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if (books.size() <= 9) {
				return 3;
			} else {
				return (books.size() - 1) / 3 + 1;
			}
		}

		@Override
		public Object getItem(int position) {

			List<BookVO> items = new ArrayList<BookVO>();
			items.add(books.get(position * 3));
			items.add(books.get(position * 3 + 1));
			items.add(books.get(position * 3 + 2));
			return items;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			view = inflater.inflate(R.layout.book_item, null);
			LinearLayout ll_books_item = (LinearLayout) view.findViewById(R.id.ll_books);
			for (int i = position * 3; i < position * 3 + 3 && i < books.size(); i++) {
				View view_book = inflater.inflate(R.layout.a_book_list, null);
				RelativeLayout rl_book = (RelativeLayout) view_book.findViewById(R.id.rl_book1);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((screenWidth - 50) / 3,
				/* (screenWidth - 50) / 3 * 220 / 161 */DensityUtil.dip2px(BookActivity.this, 120));
				lp.setMargins(5, 0, 5, 0);
				rl_book.setLayoutParams(lp);
				// 是否显示云彩
				ImageView iv_cloud1 = (ImageView) view_book.findViewById(R.id.iv_cloud1);
				if (ActivityUtils.isExistAndRead(books.get(i).getBookId()) || ActivityUtils.epubIsExistAndRead(books.get(i).getBookId(), books.get(i).getBookSaveName())) {
					iv_cloud1.setVisibility(View.GONE);
				} else {
					iv_cloud1.setVisibility(View.VISIBLE);
				}
				// 书籍封面获取
				ImageView imageView01 = (ImageView) view_book.findViewById(R.id.iv_book1);
				image_url = Preferences.IMAGE_HTTP_LOCATION + books.get(i).getBookImgPath() + books.get(i).getBookImg();

				Log.i("图片地址", image_url);
				String bookId = books.get(i).getBookId();
				// String bookSaveName = books.get(i).getBookSaveName();
				if (ActivityUtils.isNetworkAvailable(BookActivity.this)) {
					SPUtility.putSPString(BookActivity.this, bookId, image_url);
				}

				if (ActivityUtils.isExistByName(books.get(i).getBookId(), books.get(i).getBookName() + ".jpg")) {
					imageLoader.displayImage("file://" + ActivityUtils.getSDPath(books.get(i).getBookId()) + File.separator + books.get(i).getBookName() + ".jpg", imageView01, options);
				} else {
					imageLoader.displayImage(SPUtility.getSPString(BookActivity.this, bookId), imageView01, options);

				}

				Button btn_delete = (Button) view_book.findViewById(R.id.btn_delete1);
				// Button btn_cancel = (Button) view_book
				// .findViewById(R.id.btn_cancel);
				ll_books_item.addView(view_book);

				rl_book.setOnClickListener(new MyClick(books.get(i).getBookId(), books.get(i).getBookName(), image_url, books.get(i).getBookSaveName()));

				if (ActivityUtils.isNetworkAvailable(BookActivity.this)) {

					rl_book.setOnLongClickListener(new MyLongClick(btn_delete,
					/* btn_cancel, */BookActivity.this, i));

				}
			}
			if (books.size() == 0 || (books.size() <= 6 && position > (books.size() - 1) / 3)) {
				ll_books_item.setPadding(0, /*
											 * (screenWidth - 50) / 3 * 198 /
											 * 161
											 */
						DensityUtil.dip2px(BookActivity.this, 110), 0, 0);
				return view;
			}
			return view;
		}

		/**
		 * 点击事件
		 * 
		 * @author Administrator
		 */
		class MyClick implements android.view.View.OnClickListener {
			// int ispackage;
			String bookid;
			String bookName;
			String image_url;
			String bookSaveName;

			public MyClick(String bookid, String bookName) {
				this.bookid = bookid;
				this.bookName = bookName;

			}

			public MyClick(String bookId, String bookName, String image_url, String bookSaveName) {
				this(bookId, bookName);
				this.image_url = image_url;
				this.bookSaveName = bookSaveName;
			}

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(BookActivity.this, BookSynActivity.class);
				intent.putExtra("bookId", bookid);
				intent.putExtra("bookName", bookName);
				intent.putExtra("image_url", image_url);
				intent.putExtra("bookSaveName", bookSaveName);
				startActivity(intent);
				// }
			}

		}

		/**
		 * 长按
		 * 
		 * @author Administrator 事件
		 */
		class MyLongClick implements OnLongClickListener {
			Button btn_delete;
			// Button btn_cancel;
			Context context;
			int location;

			public MyLongClick(Button btn_delete, /* Button btn_cancel, */
					Context context, int location) {
				// this.btn_cancel = btn_cancel;
				this.btn_delete = btn_delete;
				this.context = context;
				this.location = location;
			}

			@Override
			public boolean onLongClick(View v) {
				// btn_cancel.setVisibility(View.VISIBLE);
				// btn_cancel.setOnClickListener(new OnClickListener() {

				// @Override
				// public void onClick(View v) {
				// btn_delete.setVisibility(View.GONE);
				// btn_cancel.setVisibility(View.GONE);
				// }
				// });
				btn_delete.setVisibility(View.VISIBLE);
				btn_delete.setOnClickListener(new android.view.View.OnClickListener() {
					@Override
					public void onClick(View v) {
						btn_delete.setVisibility(View.GONE);
						// btn_cancel.setVisibility(View.GONE);
						DialogUtils.showMyDialog(context, MyPreferences.SHOW_CONFIRM_DIALOG, "删除书籍", "你确定永久删除此书籍,删除后将不可恢复？", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								DialogUtils.dismissMyDialog();
								deleteBook(location);
							}
						});
					}
				});
				return true;
			}

		}

	}

	/**
	 * 查询书架列表
	 */
	public void findBooks() {
		Map<String, String> params = new HashMap<String, String>();

		// if (null == UserUtil.getInstance(this)) {
		//
		// DialogUtils.showMyDialog(this, MyPreferences.SHOW_ERROR_DIALOG,
		// "获取书籍失败", "网络连接失败，请更换网络后登录！", new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(BookActivity.this,
		// LoginActivity.class);
		// SPUtility.putSPString(BookActivity.this,
		// "username", "");
		// SPUtility.putSPString(BookActivity.this, "userPwd",
		// "");
		// startActivity(intent);
		// finish();
		// }
		// });
		// return;
		// }
		String userId = UserUtil.getInstance(this).getUserId();
		params.put("userId", userId);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		tm.createNewTask(new TaskListener() {
			@Override
			public String getName() {
				return null;
			}

			@Override
			public void onPreExecute(BaseTask task) {
				// DialogUtils.showMyDialog(BookActivity.this,
				// MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在查找中...", null);
			}

			@Override
			public void onPostExecute(BaseTask task, String errorMsg) {

				// DialogUtils.dismissMyDialog();
				if (!ActivityUtils.isNetworkAvailable(getBaseContext())) {

				} else {
					if (errorMsg != null) {// 获取数据出现异常
						ActivityUtils.showToastForSuccess(BookActivity.this, "加载失败," + errorMsg);
						Message msg = new Message();
						msg.what = 2;
						mHandler.sendMessage(msg);
						// DialogUtils.showMyDialog(BookActivity.this,
						// MyPreferences.SHOW_ERROR_DIALOG, "添加书籍失败",errorMsg ,
						// null);
					} else {
						BookJson bookJson = bookController.getModel();
						if (null == bookJson) {
							ActivityUtils.showToastForSuccess(BookActivity.this, "加载失败," + "无法获取书籍信息");
							Message msg = new Message();
							msg.what = 2;
							mHandler.sendMessage(msg);
							// DialogUtils.showMyDialog(BookActivity.this,
							// MyPreferences.SHOW_ERROR_DIALOG,
							// "添加书籍失败","获取书籍信息失败" , null);
							return;
						} else {
							Log.d(TAG, "bookJson.getCode()=========" + bookJson.getCode());
							if (bookJson.getCode().equals(MyPreferences.SUCCESS) && null != bookJson.getBookVOs()) {
								Log.d(TAG, "------bookJson.getCode()=" + bookJson.getCode() + "----------books size=" + books.size());
								books.clear();
								books.addAll(bookJson.getBookVOs());
								Log.d(TAG, "----------books size=" + books.size());
								Collections.reverse(books);// 反向
								updateBooksData(books);
								Message msg = new Message();
								msg.what = 2;
								mHandler.sendMessage(msg);

							}
							if (books != null) {
								for (BookVO book : books) {
									DbManager db = MyDbUtils.getBookVoDb(BookActivity.this);
									try {
										db.save(book);
									} catch (DbException e) {
										e.printStackTrace();
									}

								}
								return;
							} else if (bookJson.getCode().equals(MyPreferences.FAIL)) {
								ActivityUtils.showToastForSuccess(BookActivity.this, "加载失败," + bookJson.getCodeInfo());
								Message msg = new Message();
								msg.what = 2;
								mHandler.sendMessage(msg);
								// DialogUtils.showMyDialog(BookActivity.this,
								// MyPreferences.SHOW_ERROR_DIALOG,
								// "添加书籍失败",bookJson.getCodeInfo() , null);
								return;
							}
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
					errorMsg = bookController.findBooks(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return errorMsg;
			}
		}, false).execute(params);
	}

	/**
	 * 删除图书
	 * 
	 * @param location
	 */
	public void deleteBook(final int location) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", UserUtil.getInstance(this).getUserId());
		params.put("bookId", books.get(location).getBookId());
		tm.createNewTask(new TaskListener() {
			@Override
			public String getName() {
				return null;
			}

			@Override
			public void onPreExecute(BaseTask task) {

				DialogUtils.showMyDialog(BookActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在删除中...", null);
			}

			@Override
			public void onPostExecute(BaseTask task, String errorMsg) {

				DialogUtils.dismissMyDialog();
				if (errorMsg != null) {// 获取数据出现异常
					DialogUtils.showMyDialog(BookActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "删除失败", errorMsg, null);
					return;
				} else {
					BookJson bookJson = bookController.getModel();
					if (null == bookJson) {
						DialogUtils.showMyDialog(BookActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "删除失败", "服务器未响应", null);
						return;
					} else {
						if (bookJson.getCode().equals(MyPreferences.SUCCESS)) {
							ActivityUtils.deletefile(books.get(location).getBookId());
							books.remove(location);
							adapter.notifyDataSetChanged();
							lv_books.postInvalidate();
							if (books.size() > 0 && books != null) {
								updateBooksData(books);
							} else {
								SPUtility.putSPString(BookActivity.this, "booksData", "");
							}

							return;
						} else if (bookJson.getCode().equals(MyPreferences.FAIL)) {
							DialogUtils.showMyDialog(BookActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "删除失败", bookJson.getCodeInfo(), null);
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
					errorMsg = bookController.deleteBook(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return errorMsg;
			}
		}).execute(params);
	}

	@Override
	protected void onDestroy() {
		getApplicationContext().removeActivity(this);
		super.onDestroy();
	}

	/**
	 * 更新图书数据
	 * 
	 * @param
	 */
	public void updateBooksData(List<BookVO> books) {
		String booksData = "";
		if (null == books || books.size() <= 0) {
			SPUtility.putSPString(BookActivity.this, "booksData", booksData);

			UserVO userVO = UserUtil.getInstance(BookActivity.this);
			userVO.setBooksData(booksData);
			dao.updateBooksData(userVO);
			return;
		}
		for (BookVO bookVO : books) {
			booksData = booksData + bookVO.toStringBook() + ";";
		}

		booksData = booksData.substring(0, booksData.length() - 2);
		Log.d(TAG, "booksData======="+booksData);
		SPUtility.putSPString(BookActivity.this, "booksData", booksData);
		UserVO userVO = UserUtil.getInstance(BookActivity.this);

		userVO.setBooksData(booksData);
		dao.updateBooksData(userVO);
	}

	@Override
	protected void onResume() {
		
		MobclickAgent.onPageStart(TAG);
		onRefresh();
		if (SPUtility.getSPString(this, "isPlay").equals("true")) {
			ll_music.setVisibility(View.VISIBLE);
			music.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(BookActivity.this, MediaPlayerActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(intent);

				}
			});
		} else {
			ll_music.setVisibility(View.GONE);
		}
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd(TAG);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MyPreferences.ADD_ACTIVITY) {
			findBooks();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
