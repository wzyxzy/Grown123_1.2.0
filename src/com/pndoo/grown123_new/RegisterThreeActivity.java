//package com.pndoo.grown123_new;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//import com.pndoo.grown123_new.constants.Preferences;
//import com.pndoo.grown123_new.dto.base.Bean;
//import com.pndoo.grown123_new.util.ActivityUtils;
//import com.pndoo.grown123_new.util.DialogUtils;
//import com.pndoo.grown123_new.util.HttpUtil;
//import com.pndoo.grown123_new.util.MyPreferences;
//import com.pndoo.grown123_new.util.SPUtility;
//
//import org.apache.http.Header;
//
//public class RegisterThreeActivity extends BaseActivity {
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.register_three);
//	}
//
//	public void btnCancel(View v){
//		register(0);
//	}
//
//	public void btnOK(View v){
//		register(1);
//	}
//
////	@Override
////	public boolean onKeyDown(int keyCode, KeyEvent event) {
////		// TODO Auto-generated method stub
////		if(keyCode == event.KEYCODE_BACK){
////			startActivity(new Intent(this, LoginActivity.class));
////			return true;
////		}
////		return super.onKeyDown(keyCode, event);
////	}
//
//	//1 dingyue 0不订阅
//	private void register(final int describe){
//		final Intent intent = getIntent();
//
//		RequestParams params = new RequestParams();
//		params.put("userName", intent.getStringExtra("userName"));
//		params.put("userPwd", intent.getStringExtra("userPwd"));
//		params.put("userEmail", intent.getStringExtra("userEmail"));
//		params.put("phoneId", intent.getStringExtra("phoneId"));
//		params.put("userDetail.sex", intent.getIntExtra(("userDetail.sex"),1));
//		params.put("userDetail.kindergarten", intent.getStringExtra("userDetail.kindergarten"));
//		params.put("userDetail.realName", intent.getStringExtra("userDetail.realName"));
//		params.put("userDetail.birthdayShow", intent.getStringExtra("userDetail.birthdayShow"));
//		params.put("userDetail.level", intent.getStringExtra("userDetail.level"));
//		params.put("userDetail.surname", intent.getStringExtra("userDetail.surname"));
//		params.put("userDetail.parents", intent.getStringExtra("userDetail.parents"));
//		params.put("userDetail.address1", intent.getStringExtra("userDetail.address1"));
//		params.put("userDetail.subscibed", describe);
//		params.put("userStatus", 1);
//
//		HttpUtil.post(Preferences.REGINST_URL, params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				// TODO Auto-generated method stub
//				String s = new String(arg2);
//				Gson gson = new Gson();
//				java.lang.reflect.Type type = new TypeToken<Bean>() {}.getType();
//				Bean bean = gson.fromJson(s, type);
////				Bean bean = JSON.parseObject(s, Bean.class);
//				if (bean.getCode().equals("SUCCESS")) {
//					ActivityUtils.showToastForSuccess(RegisterThreeActivity.this, bean.getCodeInfo());
//					SPUtility.putSPString(RegisterThreeActivity.this, "username", intent.getStringExtra("userName"));
//					SPUtility.putSPString(RegisterThreeActivity.this, "userPwd", intent.getStringExtra("userPwd"));
//					SPUtility.putSPString(RegisterThreeActivity.this, "userEmail", intent.getStringExtra("userEmail"));
//					SPUtility.putSPString(RegisterThreeActivity.this, "phoneId", intent.getStringExtra("phoneId"));
//					SPUtility.putSPInteger(RegisterThreeActivity.this, "userDetail.sex", intent.getIntExtra(("userDetail.sex"),1));
//					SPUtility.putSPString(RegisterThreeActivity.this, "userDetail.kindergarten", intent.getStringExtra("userDetail.kindergarten"));
//					SPUtility.putSPString(RegisterThreeActivity.this, "userDetail.realName", intent.getStringExtra("userDetail.realName"));
//					SPUtility.putSPString(RegisterThreeActivity.this, "userDetail.birthdayShow", intent.getStringExtra("userDetail.birthdayShow"));
//					SPUtility.putSPString(RegisterThreeActivity.this, "userDetail.level", intent.getStringExtra("userDetail.level"));
//					SPUtility.putSPString(RegisterThreeActivity.this, "userDetail.surname", intent.getStringExtra("userDetail.surname"));
//					SPUtility.putSPString(RegisterThreeActivity.this, "userDetail.parents", intent.getStringExtra("userDetail.parents"));
//					SPUtility.putSPString(RegisterThreeActivity.this, "userDetail.address1", intent.getStringExtra("userDetail.address1"));
//					SPUtility.putSPInteger(RegisterThreeActivity.this, "userDetail.subscibed", describe);
//					SPUtility.putSPString(RegisterThreeActivity.this, "citycode", intent.getStringExtra("citycode"));
//					Intent intent = new Intent(RegisterThreeActivity.this, LoginActivity.class);
//					startActivity(intent);
//					finish();
//					return;
//				} else if (bean.getCode().equals("FAIL")) {
//					DialogUtils.showMyDialog(RegisterThreeActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", bean.getCodeInfo(), null);
//					return;
//				}
//			}
//
//			@Override
//			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//				// TODO Auto-generated method stub
//				ActivityUtils.showToastForFail(RegisterThreeActivity.this, "注册失败");
//			}
//		});
//	}
//}
