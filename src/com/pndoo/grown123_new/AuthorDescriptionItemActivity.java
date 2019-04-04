package com.pndoo.grown123_new;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pndoo.grown123_new.adapter.CommonAdapter;
import com.pndoo.grown123_new.adapter.ViewHolder;
import com.pndoo.grown123_new.controller.CommentController;
import com.pndoo.grown123_new.dto.base.AuthorReply;
import com.pndoo.grown123_new.main.IoC;
import com.pndoo.grown123_new.soap.CommentJson;
import com.pndoo.grown123_new.task.BaseTask;
import com.pndoo.grown123_new.task.TaskListener;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;

/**
 * ------------------------------------------------------------------
 * 创建时间：2015-9-9 上午11:44:18 项目名称：jiayue
 * 
 * @author Ping Wang
 * @version 1.0
 * @since JDK 1.6.0_21 文件名称：ReaderDescriptionItemActivity.java 类说明：
 *        ------------------------------------------------------------------
 */
public class AuthorDescriptionItemActivity extends BaseActivity implements OnRefreshListener {

	private ListView listview;
	private TextView tv_content;
	private TextView tv_question;
	private TextView tv_time;
	private TaskManager tm;
	private CommentController commentController;
	private CommonAdapter<AuthorReply> adapter;
	private List<AuthorReply> authorReplyLists;
	private String id;
	private String addTime;
	private String title;
	private String content;
	private BroadcastReceiver broadcastReciver;
	private TextView tv_header_title;
	private SwipeRefreshLayout refresh_view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.author_desc);
		tm = getApplicationContext().getTaskManager();
		commentController = IoC.getInstance(CommentController.class);
		content = getIntent().getStringExtra("content");
		id = getIntent().getStringExtra("id");
		addTime = getIntent().getStringExtra("addTime");
		title = getIntent().getStringExtra("title");
		loadData();
		initViews();

	}

	private void initViews() {
		refresh_view = (SwipeRefreshLayout) findViewById(R.id.refresh_view);
		refresh_view.setOnRefreshListener(this);
		refresh_view.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
		
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_question = (TextView) findViewById(R.id.tv_question);
		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
		tv_header_title.setText("作者沟通");
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_content.setText(content);
		tv_question.setText(title);
		tv_time.setText(addTime);
		listview = (ListView) findViewById(R.id.listview);
		initBroadcast();
	}

	private void initBroadcast() {
		broadcastReciver = new BroadcastReceiver() {

			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals("android.intent.action.author.reply")) {
					loadData();
				}
			};
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.author.reply");
		filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		registerReceiver(broadcastReciver, filter);

	}

	private ListAdapter initListViewAdapter() {
		adapter = new CommonAdapter<AuthorReply>(this, authorReplyLists, R.layout.author_desc_item) {
			@Override
			public int getCount() {
				return authorReplyLists.size();
			}

			@Override
			public void convert(ViewHolder helper, final AuthorReply item, int position) {

				helper.setText(R.id.tv_content_item, item.getSubContent());
				helper.setText(R.id.tv_answer_item, item.getSubTitle());
				helper.setText(R.id.tv_time_item, item.getAddTime());

			}
		};
		return adapter;
	}

	private void loadData() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("Id", id);
		tm.createNewTask(new TaskListener() {

			@Override
			public String getName() {
				return null;
			}

			@Override
			public void onPreExecute(BaseTask task) {
				DialogUtils.showMyDialog(AuthorDescriptionItemActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...", null);
			}

			@Override
			public void onPostExecute(BaseTask task, String errorMsg) {
				DialogUtils.dismissMyDialog();
				if (errorMsg != null) {// 获取数据出现异常
					DialogUtils.showMyDialog(AuthorDescriptionItemActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg, null);
				} else {
					CommentJson commentJson = commentController.getModel();
					if (null == commentJson || null == commentJson.getCode()) {
						DialogUtils.showMyDialog(AuthorDescriptionItemActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "数据为空", "获取信息失败", null);
						return;
					} else {
						if (commentJson.getCode().equals("SUCCESS")) {

							authorReplyLists = commentJson.getAuthorReplyLists();
							updata();
							Intent intent = new Intent();
							intent.setAction("android.intent.action.author.desc");
							sendBroadcast(intent);
							Log.i("msg", authorReplyLists.toString());

							return;
						} else if (commentJson.getCode().equals("FAIL")) {
							DialogUtils.showMyDialog(AuthorDescriptionItemActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "获取失败", commentJson.getCodeInfo(), null);
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
					errorMsg = commentController.authorPostReply(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return errorMsg;
			}
		}).execute(params);

	}

	private void updata() {
		listview.setAdapter(initListViewAdapter());
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(AuthorDescriptionItemActivity.this, AuthorCommentInfoActivity.class);
				intent.putExtra("title", authorReplyLists.get(position).getSubTitle());
				intent.putExtra("content", authorReplyLists.get(position).getSubContent());
				intent.putExtra("time", authorReplyLists.get(position).getAddTime());
				startActivity(intent);
			}
		});
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

	public void btn_reply(View v) {
		Intent intent = new Intent(AuthorDescriptionItemActivity.this, AuthorReplyActivity.class);
		intent.putExtra("title", "回复");
		intent.putExtra("id", id);
		startActivity(intent);

	}

	@Override
	protected void onDestroy() {

		if (broadcastReciver != null) {
			unregisterReceiver(broadcastReciver);
		}
		super.onDestroy();
	}

}
