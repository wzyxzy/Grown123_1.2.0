package com.pndoo.grown123_new;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.SPUtility;

import org.xutils.common.Callback;
import org.xutils.x;

public class UpdateSurnameActivity extends BaseActivity {
	EditText et_surname;
	String surname;
	TextView tv_header_title;

	private ImageButton btn_header_right;
	private final String TAG = getClass().getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getApplicationContext().addActivity(this);
		initPassWordView();
	}

	public void initPassWordView() {
		setContentView(R.layout.update_surname);
		et_surname = (EditText) findViewById(R.id.et_surname);
		int btn_right = getResources().getIdentifier("btn_right", "drawable", getPackageName());
		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
		tv_header_title.setText("修改昵称");
		btn_header_right = (ImageButton) findViewById(R.id.btn_header_right);
		btn_header_right.setVisibility(View.VISIBLE);
		btn_header_right.setBackgroundResource(btn_right);
		btn_header_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				surname = et_surname.getText().toString().trim();
				Log.d(TAG, "------click----=surname="+surname);
				if (TextUtils.isEmpty(surname)) {
					ActivityUtils.showToast(UpdateSurnameActivity.this, "昵称不可为空!");
				} else {
					DialogUtils.showMyDialog(UpdateSurnameActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在处理中...", null);
					updateSurname();
				}
			}
		});
	}

	public void btnBack(View v) {
		this.finish();
	}

	private void updateSurname() {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("_method", "updatePassword");
//		params.put("userId", UserUtil.getInstance(UpdateSurnameActivity.this).getUserId());
//		params.put("surname", surname);
		org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.UPDATE_SURNAME);
		params.addQueryStringParameter("userId", UserUtil.getInstance(UpdateSurnameActivity.this).getUserId());
		params.addQueryStringParameter("surname",surname);

		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String s) {
				Gson gson = new Gson();
				java.lang.reflect.Type type = new TypeToken<Bean>() {
				}.getType();
				Bean bean = gson.fromJson(s, type);

				if(bean.getCode().equals("SUCCESS")){
					DialogUtils.dismissMyDialog();
					SPUtility.putSPString(UpdateSurnameActivity.this, "userDetail.surname", surname);
					ActivityUtils.showToastForSuccess(UpdateSurnameActivity.this, "昵称修改成功");
					Intent intent = new Intent();
					intent.putExtra("surname", surname);
					setResult(1, intent);
					finish();
				}else if(bean.getCodeInfo().equals("FAIL")){
					ActivityUtils.showToastForFail(UpdateSurnameActivity.this, "昵称修改失败");
					DialogUtils.dismissMyDialog();
				}
			}

			@Override
			public void onError(Throwable throwable, boolean b) {
				ActivityUtils.showToastForFail(UpdateSurnameActivity.this, "昵称修改失败");
				DialogUtils.dismissMyDialog();
			}

			@Override
			public void onCancelled(CancelledException e) {

			}

			@Override
			public void onFinished() {

			}
		});

//		DialogUtils.showMyDialog(UpdateSurnameActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在处理中...", null);
//		DialogUtils.showMyDialog(UpdateSurnameActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "操作失败", errorMsg, null);
//		DialogUtils.showMyDialog(UpdateSurnameActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "操作失败", "获取信息失败", null);
//		DialogUtils.showMyDialog(UpdateSurnameActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "操作成功", "密码修改成功，需要重新登录！", new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				DialogUtils.dismissMyDialog();
//				Intent intent = new Intent(UpdateSurnameActivity.this, LoginActivity.class);
//				SPUtility.putSPString(UpdateSurnameActivity.this, "username", "");
//				SPUtility.putSPString(UpdateSurnameActivity.this, "userPwd", "");
//				startActivity(intent);
//				UpdateSurnameActivity.this.finish();
//
//			}
//		});
//		DialogUtils.showMyDialog(UpdateSurnameActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "操作失败", loginJson.getCodeInfo(), null);
	}

	@Override
	protected void onDestroy() {
		getApplicationContext().removeActivity(this);
		super.onDestroy();
	}

}
