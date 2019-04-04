package com.pndoo.grown123_new;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pndoo.grown123_new.controller.LoginController;
import com.pndoo.grown123_new.main.IoC;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.soap.LoginJson;
import com.pndoo.grown123_new.task.BaseTask;
import com.pndoo.grown123_new.task.TaskListener;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.SPUtility;

import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateUserInfoActivity extends BaseActivity {
	EditText et_username;
	LinearLayout ll_username;
	ImageView iv_delete;

	EditText et_password;
	LinearLayout ll_password;

	EditText et_password01;
	LinearLayout ll_password01;
	ImageView iv_delete01;

	EditText et_password02;
	LinearLayout ll_password02;
	ImageView iv_delete02;

	EditText et_code;
	LinearLayout ll_code;

	EditText et_content;
	TextView tv_header_title;
	int upadteflag;

	TaskManager tm;
	LoginController loginController;
	private int inputbox_click;
	private int inputbox_normal;
	private ImageButton btn_header_right;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getApplicationContext().addActivity(this);
		tm = getApplicationContext().getTaskManager();
		loginController = IoC.getInstance(LoginController.class);
		inputbox_click = getResources().getIdentifier("inputbox_click", "drawable", getPackageName());
		inputbox_normal = getResources().getIdentifier("inputbox_normal", "drawable", getPackageName());
		initPassWordView();
	}

	/**
	 * 初始化修改密码界面
	 */
	public void initPassWordView() {
		setContentView(R.layout.update_password);
		int btn_right = getResources().getIdentifier("btn_right", "drawable", getPackageName());
		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
		tv_header_title.setText("修改密码");
		btn_header_right = (ImageButton) findViewById(R.id.btn_header_right);
		btn_header_right.setVisibility(View.VISIBLE);
		btn_header_right.setBackgroundResource(btn_right);
		btn_header_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (et_password == null || et_password.getText().toString().trim().equals("")) {
					ActivityUtils.showToastForFail(getApplication(), "请输入当前密码");
					return;
				}
				if (et_password01 == null || et_password01.getText().toString().trim().equals("")) {
					ActivityUtils.showToastForFail(getApplication(), "请输入新密码");
					return;
				}
				String password1 = et_password01.getText().toString().trim();
				if (!TextUtils.isEmpty(password1) && password1.length() >= 6 || password1.length() <= 16) {
					et_password01.setText(password1);

				} else if (password1.matches("[\u4E00-\u9FA5a]+")) {
					ActivityUtils.showToast(UpdateUserInfoActivity.this, "请不要输入中文!");
					return;
				} else if (TextUtils.isEmpty(password1)) {
					ActivityUtils.showToastForFail(getApplication(), "请输入新密码！");
					return;
				} else {
					ActivityUtils.showToastForFail(UpdateUserInfoActivity.this, "密码请输入6-16位!");
					return;
				}

				String password2 = et_password02.getText().toString().trim();
				if (!TextUtils.isEmpty(password1) && password1.length() >= 6 || password1.length() <= 16) {
					et_password01.setText(password1);

				} else if (password1.matches("[\u4E00-\u9FA5a]+")) {
					ActivityUtils.showToast(UpdateUserInfoActivity.this, "请不要输入中文!");
					return;
				} else if (TextUtils.isEmpty(password1)) {
					ActivityUtils.showToastForFail(getApplication(), "请输入确认密码！");
					return;
				} else {
					ActivityUtils.showToastForFail(UpdateUserInfoActivity.this, "密码请输入6-16位!");
					return;
				}
				if (!password1.equals(password2)) {
					ActivityUtils.showToastForFail(getApplication(), "密码与确认密码不一致，请重新输入");
					return;
				}
				updatePassword();

			}
		});
		et_password = (EditText) findViewById(R.id.et_password);
		ll_password = (LinearLayout) findViewById(R.id.ll_password);
		iv_delete = (ImageView) findViewById(R.id.iv_delete);

		et_password.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;

			@Override
			public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				temp = s;
			}

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (temp.length() <= 0) {
					iv_delete.setVisibility(View.GONE);
				} else {
					iv_delete.setVisibility(View.VISIBLE);
				}
			}
		});

		et_password01 = (EditText) findViewById(R.id.et_password01);
		ll_password01 = (LinearLayout) findViewById(R.id.ll_password01);
		iv_delete01 = (ImageView) findViewById(R.id.iv_delete01);

		et_password01.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;

			@Override
			public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				temp = s;
			}

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (temp.length() <= 0) {
					iv_delete01.setVisibility(View.GONE);
				} else {
					iv_delete01.setVisibility(View.VISIBLE);
				}
			}
		});

		et_password02 = (EditText) findViewById(R.id.et_password02);
		ll_password02 = (LinearLayout) findViewById(R.id.ll_password02);
		iv_delete02 = (ImageView) findViewById(R.id.iv_delete02);

		et_password02.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;

			@Override
			public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				temp = s;
			}

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (temp.length() <= 0) {
					iv_delete02.setVisibility(View.GONE);
				} else {
					iv_delete02.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	/**
	 * 初始化修改用户名页面
	 */
	public void initUserNameView() {
		setContentView(R.layout.update_user_info);
		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
		tv_header_title.setText("修改用户名");
		et_username = (EditText) findViewById(R.id.et_username);
		ll_username = (LinearLayout) findViewById(R.id.ll_username);
		iv_delete = (ImageView) findViewById(R.id.iv_delete);
		et_username.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					ll_username.setBackgroundResource(inputbox_click);
					if (!et_username.getText().toString().equals("")) {
						iv_delete.setVisibility(View.VISIBLE);
					}
				} else {
					ll_username.setBackgroundResource(inputbox_normal);
					iv_delete.setVisibility(View.GONE);
				}
			}
		});
		et_username.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;

			@Override
			public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				temp = s;
			}

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (temp.length() <= 0) {
					iv_delete.setVisibility(View.GONE);
				} else {
					iv_delete.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	public void btnBack(View v) {
		this.finish();
	}

	/**
	 * 修改密码
	 */
	private void updatePassword() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("_method", "updatePassword");
		params.put("userId", UserUtil.getInstance(UpdateUserInfoActivity.this).getUserId());
		params.put("userPwd", et_password01.getText().toString().trim());

		tm.createNewTask(new TaskListener() {
			@Override
			public String getName() {
				return null;
			}

			@Override
			public void onPreExecute(BaseTask task) {
				DialogUtils.showMyDialog(UpdateUserInfoActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在处理中...", null);
			}

			@Override
			public void onPostExecute(BaseTask task, String errorMsg) {
				DialogUtils.dismissMyDialog();
				if (errorMsg != null) {// 获取数据出现异常
					DialogUtils.showMyDialog(UpdateUserInfoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "操作失败", errorMsg, null);
				} else {
					LoginJson loginJson = loginController.getModel();
					if (null == loginJson) {
						DialogUtils.showMyDialog(UpdateUserInfoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "操作失败", "获取信息失败", null);
						return;
					} else {
						if (loginJson.getCode().equals(MyPreferences.SUCCESS)) {
							SPUtility.putSPString(UpdateUserInfoActivity.this, "userPwd", et_password01.getText().toString().trim());
							DialogUtils.showMyDialog(UpdateUserInfoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "操作成功", "密码修改成功，需要重新登录！", new OnClickListener() {

								@Override
								public void onClick(View v) {
									DialogUtils.dismissMyDialog();
									Intent intent = new Intent(UpdateUserInfoActivity.this, LoginActivity.class);
									SPUtility.putSPString(UpdateUserInfoActivity.this, "username", "");
									SPUtility.putSPString(UpdateUserInfoActivity.this, "userPwd", "");
									startActivity(intent);
									UpdateUserInfoActivity.this.finish();

								}
							});
							return;
						} else if (loginJson.getCode().equals(MyPreferences.FAIL)) {
							DialogUtils.showMyDialog(UpdateUserInfoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "操作失败", loginJson.getCodeInfo(), null);
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
					errorMsg = loginController.sendEmail(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return errorMsg;
			}
		}).execute(params);
	}

	/**
	 * 清除用户名输入框
	 * 
	 * @param v
	 */
	public void deleteUsername(View v) {
		et_username.setText("");
		v.setVisibility(View.GONE);
	}

	/**
	 * 清除用户名输入框
	 * 
	 * @param v
	 */
	public void deletepassword(View v) {
		int id = v.getId();
		if (id == R.id.iv_delete) {
			et_password.setText("");
			v.setVisibility(View.GONE);
		} else if (id == R.id.iv_delete01) {
			et_password01.setText("");
			v.setVisibility(View.GONE);
		} else if (id == R.id.iv_delete02) {
			et_password02.setText("");
			v.setVisibility(View.GONE);
		} else {
		}

	}

	/**
	 * 清除用户名输入框
	 * 
	 * @param v
	 */
	public void deleteCode(View v) {
		et_code.setText("");
		v.setVisibility(View.GONE);
	}

	/**
	 * 验证输入的邮箱格式是否符合
	 * 
	 * @param email
	 * @return 是否合法
	 */
	public static boolean emailFormat(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	@Override
	protected void onDestroy() {
		getApplicationContext().removeActivity(this);
		super.onDestroy();
	}

}
