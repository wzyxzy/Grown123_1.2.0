package com.pndoo.grown123_new;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.adapter.CommonAdapter;
import com.pndoo.grown123_new.adapter.ViewHolder;
import com.pndoo.grown123_new.controller.CommentController;
import com.pndoo.grown123_new.dto.base.ReaderComment;
import com.pndoo.grown123_new.dto.base.ReaderMainContent;
import com.pndoo.grown123_new.main.IoC;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.soap.CommentJson;
import com.pndoo.grown123_new.task.BaseTask;
import com.pndoo.grown123_new.task.TaskListener;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;

/**
 * ------------------------------------------------------------------
 * 创建时间：2015-9-8 下午4:07:37 项目名称：jiayue
 * 
 * @author Ping Wang
 * @version 1.0
 * @since JDK 1.6.0_21 文件名称：ReaderCommunication.java 类说明：
 *        ------------------------------------------------------------------
 */
public class ReaderCommunicationActivity extends BaseActivity
		implements
			OnRefreshListener {
	DisplayImageOptions options;
	private int cover_normal;

	private String bookName;
	private String image_url;
//	private BookVO bookVO;
	private String bookId;
	private ImageView iv_splash;
	private TextView tv_bookname;
	private TextView tv_desc;
	private ListView listview;
	private TextView tv_header_title;
	private Button btn_post;
	private TaskManager tm;
	private CommentController commentController;
	private ReaderComment comment;
	private TextView tv_author;
	private List<ReaderMainContent> mainContents;
	private BroadcastReceiver broadcastReciver;
	private SwipeRefreshLayout refresh_view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reader_communication);
		tm = getApplicationContext().getTaskManager();
		commentController = IoC.getInstance(CommentController.class);
		cover_normal = getResources().getIdentifier("cover_normal", "drawable",
				getPackageName());

		options = new DisplayImageOptions.Builder().showStubImage(cover_normal)
				// 设置图片下载期间显示的图片
				.showImageForEmptyUri(cover_normal)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(cover_normal)
				// 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();
		image_url = getIntent().getStringExtra("image_url");
		bookId = getIntent().getStringExtra("bookId");
		bookName = getIntent().getStringExtra("bookname");
//		bookVO = (BookVO) (getIntent().getBundleExtra("bundle")
//				.getSerializable("bookVO"));
		initViews();
		loadData();
		

	}
	public void btn_send(View v) {
		Intent intent = new Intent(this, ReaderSendActivity.class);
		intent.putExtra("title", "发表话题");
		intent.putExtra("bookId", bookId);
		startActivity(intent);

	}
	private void initViews() {
		
		refresh_view = (SwipeRefreshLayout) findViewById(R.id.refresh_view);
		refresh_view.setOnRefreshListener(this);
		refresh_view.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		
		btn_post = (Button) findViewById(R.id.btn_post);
		btn_post.setText("发表话题");
		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
		tv_header_title.setText("读者沟通");
		tv_author = (TextView) findViewById(R.id.tv_author);
		iv_splash = (ImageView) findViewById(R.id.iv_splash);
		imageLoader.displayImage(image_url, iv_splash, options);
		tv_bookname = (TextView) findViewById(R.id.tv_bookname);
		tv_bookname.setText(bookName);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		listview = (ListView) findViewById(R.id.listview);
		initBroadcast();
	}
	private void initBroadcast() {
		broadcastReciver = new BroadcastReceiver() {

			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals("android.intent.action.reader")||action.equals("android.intent.action.reader.desc")) {
					loadData();
				}
			};
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.reader");
		filter.addAction("android.intent.action.reader.desc");
		filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		registerReceiver(broadcastReciver, filter);
	}
	private void loadData() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", bookId);
		params.put("userId", UserUtil.getInstance(this).getUserId());
		params.put("isAuthorReader", "0");
		tm.createNewTask(new TaskListener() {

			@Override
			public String getName() {
				return null;
			}

			@Override
			public void onPreExecute(BaseTask task) {
//				DialogUtils.showMyDialog(ReaderCommunicationActivity.this,
//						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
//						null);
			}

			@Override
			public void onPostExecute(BaseTask task, String errorMsg) {
				DialogUtils.dismissMyDialog();
				if (errorMsg != null) {// 获取数据出现异常
					DialogUtils.showMyDialog(ReaderCommunicationActivity.this,
							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
							null);
				} else {
					CommentJson commentJson = commentController.getModel();
					if (null == commentJson || null == commentJson.getCode()) {
						DialogUtils.showMyDialog(
								ReaderCommunicationActivity.this,
								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
								"获取信息失败", null);
						return;
					} else {
						if (commentJson.getCode().equals("SUCCESS")) {
							comment = commentJson.getReaderComment();
							Log.i("msg", comment.toString());
							updata();

							return;
						} else if (commentJson.getCode().equals("FAIL")) {
							DialogUtils.showMyDialog(
									ReaderCommunicationActivity.this,
									MyPreferences.SHOW_ERROR_DIALOG, "获取失败",
									commentJson.getCodeInfo(), null);
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
			public String onDoInBackground(BaseTask task,
					MultiValueMap<String, String> param) {
				String errorMsg = null;
				try {
					errorMsg = commentController.readerComment(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return errorMsg;
			}
		}).execute(params);

	}
	private void updata() {
		if (comment != null) {
			tv_author.setText(comment.getAuthor());
			tv_desc.setText(comment.getAuthorInfo());
			mainContents = comment.getMainContents();
			listview.setAdapter(new CommonAdapter<ReaderMainContent>(this,
					mainContents, R.layout.communication_item) {

				@Override
				public void convert(ViewHolder helper, ReaderMainContent item,
						int position) {
					helper.setText(R.id.tv_question, item.getTitle());
					helper.setText(R.id.tv_time, item.getAddTime());
					helper.setText(R.id.tv_content, item.getContent());
					helper.setText(R.id.tv_comment_count, item.getQACount());
					if (Integer.valueOf(item.getHasNewReply()) == 0) {
						helper.setInVisibility(R.id.iv_new);
					}
				}
			});
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(
							ReaderCommunicationActivity.this,
							ReaderDescriptionItemActivity.class);
					String content = mainContents.get(position)
							.getContent();
					intent.putExtra("content", content);
					intent.putExtra("id", mainContents.get(position)
							.getId());
					intent.putExtra("addTime", mainContents.get(position)
							.getAddTime());
					intent.putExtra("title", mainContents.get(position)
							.getTitle());
					startActivity(intent);
				}
			});
		}
	}

	public void btnBack(View v) {
		this.finish();
	}

	private Handler mHandler = new Handler();
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				loadData();
				refresh_view.setRefreshing(false);
			}
		}, 2000);
	}

	@Override
	protected void onDestroy() {
		if (broadcastReciver != null) {
			unregisterReceiver(broadcastReciver);
		}
		super.onDestroy();
	}
}
