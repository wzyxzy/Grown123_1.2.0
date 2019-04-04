package com.pndoo.grown123_new;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.MyPreferences;

import org.xutils.common.Callback;
import org.xutils.x;

public class ChangeTeaActivity extends BaseActivity {

    private EditText et_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_tea);

        et_phone = (EditText) findViewById(R.id.et_phone1);
    }

    public void btnBack(View v) {
        this.finish();
    }
    public void onSaveClick(View v) {
        String phone = et_phone.getText().toString().trim();
        if (!TextUtils.isEmpty(phone) && phone.matches("^[1][34578][0-9]{9}$")) {

        } else if (phone.matches("[\u4E00-\u9FA5a]+")) {
            ActivityUtils.showToast(this, "请不要输入中文!");
            return;
        } else if (TextUtils.isEmpty(phone)) {
            ActivityUtils.showToast(getApplication(), "请输入手机号码！");
            return;
        } else {
            ActivityUtils.showToast(getApplication(), "手机号码格式不正确！");
            return;
        }
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_CHANGETEA);
        params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());
        params.addQueryStringParameter("userName", phone);
        params.addQueryStringParameter("groupId", getIntent().getStringExtra("groupId"));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<Bean>() {
                }.getType();
                Bean bean = gson.fromJson(s, type);

                if (bean.getCode().equals(MyPreferences.SUCCESS)) {
                    setResult(0x01);
                    finish();
                } else if (bean.getCode().equals(MyPreferences.FAIL)) {
                    ActivityUtils.showToast(ChangeTeaActivity.this,bean.getCodeInfo());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
