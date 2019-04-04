package com.pndoo.grown123_new;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pndoo.grown123_new.controller.LoginController;
import com.pndoo.grown123_new.main.IoC;
import com.pndoo.grown123_new.soap.LoginJson;
import com.pndoo.grown123_new.task.BaseTask;
import com.pndoo.grown123_new.task.TaskListener;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;

public class UserResetActivity extends BaseActivity {

	private EditText et_phone;
	private EditText et_code;
	private EditText et_password;
	private EditText et_confirm;
	private LoginController loginController;
	private TaskManager tm;
	TimeCount time = new TimeCount(60000, 1000);
	private TextView tv_send;
	private TextView tv_second;
	private LinearLayout ll_code;
	private String phone;
	private String confirm;
	private String verifCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tm = getApplicationContext().getTaskManager();
		loginController = IoC.getInstance(LoginController.class);
		setContentView(R.layout.reset_password);
		initView();
	}

	boolean flag;

	private String code;

	public void initView() {
		et_phone = (EditText) findViewById(R.id.et_phone1);
		et_code = (EditText) findViewById(R.id.et_code);
		et_password = (EditText) findViewById(R.id.et_password);
		et_confirm = (EditText) findViewById(R.id.et_confirm);
		tv_send = (TextView) findViewById(R.id.tv_send);
		tv_second = (TextView) findViewById(R.id.tv_second);
		ll_code = (LinearLayout) findViewById(R.id.ll_code);
	}

	public void btn_send(View v) {
		phone = et_phone.getText().toString().trim();
		if (!TextUtils.isEmpty(phone) && phone.matches("^[1][34578][0-9]{9}$")) {
			System.out.println("phone=>>" + phone);
			Map<String, String> params = new HashMap<String, String>();
			params.put("phone", phone);
			tm.createNewTask(new TaskListener() {

				@Override
				public String getName() {
					return null;
				}

				@Override
				public void onPreExecute(BaseTask task) {
					DialogUtils.showMyDialog(UserResetActivity.this,
							MyPreferences.SHOW_PROGRESS_DIALOG, null,
							"正在发送中...", null);
				}

				@Override
				public void onPostExecute(BaseTask task, String errorMsg) {
					DialogUtils.dismissMyDialog();
					if (errorMsg != null) {// 获取数据出现异常
						DialogUtils.showMyDialog(UserResetActivity.this,
								MyPreferences.SHOW_ERROR_DIALOG, "注册失败",
								errorMsg, null);
					} else {
						LoginJson loginJson = loginController.getModel();
						if (null == loginJson) {
							DialogUtils.showMyDialog(UserResetActivity.this,
									MyPreferences.SHOW_ERROR_DIALOG, "注册失败",
									"获取信息失败", null);
							return;
						} else {
							if (loginJson.getCode()!=null&&loginJson.getCode().equals("SUCCESS")) {
								verifCode = loginJson.getSms().getVerifCode();
								Log.i("verifCode", verifCode);
								return;
							} else if (loginJson.getCode().equals("FAIL")) {
								DialogUtils.showMyDialog(
										UserResetActivity.this,
										MyPreferences.SHOW_ERROR_DIALOG,
										"注册失败", loginJson.getCodeInfo(), null);
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
						errorMsg = loginController.sendSMS(param);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return errorMsg;
				}
			}).execute(params);
			time.start();
		} else {
			ActivityUtils.showToast(this, "您输入的手机号格式不正确！");
		}
	}

	public void Confirm(View v) {
		
		code = et_code.getText().toString().trim();
		if(TextUtils.isEmpty(phone) || !phone.matches("^[1][34578][0-9]{9}$")){
			ActivityUtils.showToastForFail(this, "手机号码为空或格式不正确");
			return;
		}
		String password = et_password.getText().toString().trim();
		if (!TextUtils.isEmpty(password) && password.length() >= 6
				|| password.length() <= 16) {
			et_password.setText(password);

		} else if (password.matches("[\u4E00-\u9FA5a]+")) {
			ActivityUtils.showToast(UserResetActivity.this, "请不要输入中文!");
			return;
		} else if (TextUtils.isEmpty(password)) {
			ActivityUtils.showToastForFail(getApplication(), "请输入密码！");
			return;
		} else {
			ActivityUtils.showToastForFail(UserResetActivity.this,
					"密码请输入6-16位!");
			return;
		}
		confirm = et_confirm.getText().toString().trim();
		if (!TextUtils.isEmpty(confirm) && confirm.length() >= 6
				|| confirm.length() <= 16) {
			et_confirm.setText(password);
		} else if (confirm.matches("[\u4E00-\u9FA5a]+")) {
			ActivityUtils.showToast(UserResetActivity.this, "请不要输入中文!");
			return;
		} else if (TextUtils.isEmpty(confirm)) {
			ActivityUtils.showToastForFail(getApplication(), "请输入确认密码！");
			return;
		} else {
			ActivityUtils.showToastForFail(UserResetActivity.this,
					"密码请输入6-16位!");
			return;
		}
		if (!password.equals(confirm)) {
			ActivityUtils
					.showToastForFail(getApplication(), "密码与确认密码不一致，请重新输入");
			return;
		}
		if (!ActivityUtils.isNetworkAvailable(this)) {
			ActivityUtils.showToastForFail(this, "无法连接网络");
			return;
		}

		if (code.equals(verifCode)) {
			reset();
		} else {
			ActivityUtils.showToast(UserResetActivity.this, "验证码错误");
		}
	}

	public void reset() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", phone);
		params.put("userPwd", confirm);
		Log.i("UserResetAvtivity", phone + "：：" + confirm);
		tm.createNewTask(new TaskListener() {
			@Override
			public String getName() {
				return null;
			}

			@Override
			public void onPreExecute(BaseTask task) {
				DialogUtils.showMyDialog(UserResetActivity.this,
						MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在发送中...",
						null);
			}

			@Override
			public void onPostExecute(BaseTask task, String errorMsg) {
				DialogUtils.dismissMyDialog();
				if (errorMsg != null) {// 获取数据出现异常
					DialogUtils.showMyDialog(UserResetActivity.this,
							MyPreferences.SHOW_ERROR_DIALOG, "密码重置失败",
							errorMsg, null);
				} else {
					LoginJson loginJson = loginController.getModel();
					if (null == loginJson) {
						DialogUtils.showMyDialog(UserResetActivity.this,
								MyPreferences.SHOW_ERROR_DIALOG, "密码重置失败",
								"获取信息失败", null);
						return;
					} else {
						if (loginJson.getCode().equals("SUCCESS")) {
							ActivityUtils.showToastForSuccess(
									UserResetActivity.this, "密码重置成功！");
							finish();
							return;
						} else if (loginJson.getCode().equals("FAIL")) {
							DialogUtils.showMyDialog(UserResetActivity.this,
									MyPreferences.SHOW_ERROR_DIALOG, "密码重置失败",
									loginJson.getCodeInfo(), null);
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
					errorMsg = loginController.reset(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return errorMsg;
			}
		}).execute(params);

	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			tv_send.setText("重新验证");
			tv_second.setText("(60秒)");
			ll_code.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			ll_code.setClickable(false);
			tv_second.setText("(" + millisUntilFinished / 1000 + "秒)");
		}
	}

	public void btnBack(View v) {

		this.finish();
	}
}
