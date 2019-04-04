package com.pndoo.grown123_new;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pndoo.grown123_new.adapter.MyTextWatcher;
import com.pndoo.grown123_new.controller.CommentController;
import com.pndoo.grown123_new.main.IoC;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.soap.CommentJson;
import com.pndoo.grown123_new.task.BaseTask;
import com.pndoo.grown123_new.task.TaskListener;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;

/**
 * ------------------------------------------------------------------
 * 创建时间：2015-9-10 上午10:08:26 项目名称：wyst
 * 
 * @author Ping Wang
 * @version 1.0
 * @since JDK 1.6.0_21 文件名称：ReaderSendActivity.java 类说明：
 *        ------------------------------------------------------------------
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("ResourceAsColor")
public class AuthorSendActivity extends BaseActivity {
	private EditText tv_title;
	private EditText tv_content;
	private TextView tv_header_title;
	private Button btn_send_message;
	private TaskManager tm;
	private CommentController commentController;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reader_send_join);
		tm = getApplicationContext().getTaskManager();
		commentController = IoC.getInstance(CommentController.class);
		initViews();
	}
	private void loadData(String title, String content) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", getIntent().getStringExtra("bookId"));
		params.put("userId", UserUtil.getInstance(this).getUserId());
		params.put("isAuthorReader", "1");
		params.put("title", title);
		params.put("content", content);

		tm.createNewTask(new TaskListener() {

			@Override
			public String getName() {
				return null;
			}

			@Override
			public void onPreExecute(BaseTask task) {
				DialogUtils.showMyDialog(AuthorSendActivity.this,
						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
						null);
			}

			@Override
			public void onPostExecute(BaseTask task, String errorMsg) {
				DialogUtils.dismissMyDialog();
				if (errorMsg != null) {// 获取数据出现异常
					DialogUtils.showMyDialog(AuthorSendActivity.this,
							MyPreferences.SHOW_ERROR_DIALOG, "出现异常", errorMsg,
							null);
				} else {
					CommentJson commentJson = commentController.getModel();
					if (null == commentJson) {
						DialogUtils.showMyDialog(AuthorSendActivity.this,
								MyPreferences.SHOW_ERROR_DIALOG, "数据为空",
								"获取信息失败", null);
						return;
					} else {
						if (commentJson.getCode().equals("SUCCESS")) {
							ActivityUtils.showToastForSuccess(
									AuthorSendActivity.this, "发表成功");
							Intent intent = new Intent();
							intent.setAction("android.intent.action.author");
							sendBroadcast(intent);
							finish();
							return;
						} else if (commentJson.getCode().equals("FAIL")) {
							DialogUtils.showMyDialog(AuthorSendActivity.this,
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
					errorMsg = commentController.authorPostQuest(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return errorMsg;
			}
		}).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);

	}
	private void initViews() {
		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
		tv_header_title.setText(getIntent().getStringExtra("title"));
		tv_title = (EditText) findViewById(R.id.tv_title);
		tv_content = (EditText) findViewById(R.id.tv_content);
		btn_send_message = (Button) findViewById(R.id.btn_send_message);
		MyTextWatcher textWatcher = new MyTextWatcher(){

			@Override
			public void changeClick() {
				if (!TextUtils.isEmpty(tv_content.getText().toString().trim())
						&& !TextUtils.isEmpty(tv_title.getText().toString().trim())) {
					btn_send_message.setBackgroundColor(getResources().getColor(
							R.color.background));
				} else {
					btn_send_message.setBackgroundColor(getResources().getColor(
							R.color.login_line));
				}
			}
			
		};
		tv_content.addTextChangedListener(textWatcher);
		tv_title.addTextChangedListener(textWatcher);
	}
	public void btn_send(View v) {
		String title = tv_title.getText().toString().trim();
		String content = tv_content.getText().toString().trim();
		if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
			loadData(title, content);
		} else if (TextUtils.isEmpty(title)) {
			ActivityUtils.showToastForFail(this, "请填写标题");
		} else if (TextUtils.isEmpty(content)) {
			ActivityUtils.showToastForFail(this, "请填写内容");
		}
	}

	public void btnBack(View v) {
		this.finish();
	}
}
