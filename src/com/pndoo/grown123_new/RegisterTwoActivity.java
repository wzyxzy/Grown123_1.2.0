package com.pndoo.grown123_new;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.dto.base.GartenBean;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.SPUtility;
import com.pndoo.grown123_new.view.wheel.ChangeAddressDialog;
import com.pndoo.grown123_new.view.wheel.ChangeClassDialog;
import com.pndoo.grown123_new.view.wheel.ChangeDateDialog;
import com.pndoo.grown123_new.view.wheel.ChangeGartenDialog;
import com.pndoo.grown123_new.view.wheel.OnAddressCListener;
import com.pndoo.grown123_new.view.wheel.OnAddressCodeCListener;
import com.pndoo.grown123_new.view.wheel.OnSingleWheelListener;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.List;
@SuppressWarnings("ResourceType")
public class RegisterTwoActivity extends BaseActivity {
    private TextView tv_header_title;
    private TextView tv_born, tv_city, tv_class, tv_garten;
    private EditText et_name, et_parents;
    //	et_momname, et_momjob, et_fathername, et_fatherjob, et_address;
    private RadioGroup rg_sex, rg_class;
    private RadioButton rb_boy, rb_girl, rb_class1;
    private String username, password, email, phoneId, surname, childname, born, garten, childclass = "01", parents, city;
    //	momname, monjob, fathername, fatherjob, address;
    private int sex = 1, gartenclass = 1;
    private final String TAG = getClass().getSimpleName();
    private String city_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_two);

        initView();
    }

    private void initView() {
        // TODO Auto-generated method stub
        Intent intent = getIntent();
        username = intent.getStringExtra("userName");
        password = intent.getStringExtra("userPwd");
        email = intent.getStringExtra("userEmail");
        phoneId = intent.getStringExtra("phoneId");
        surname = intent.getStringExtra("surname");

        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.jibenxinxi));

        et_name = (EditText) findViewById(R.id.editText1);
        rg_sex = (RadioGroup) findViewById(R.id.radioGroup1);
        rb_boy = (RadioButton) findViewById(R.id.radio0);
        rb_boy.setId(0);
        rb_boy.setChecked(true);
        rb_girl = (RadioButton) findViewById(R.id.radio1);
        rb_girl.setId(1);
        rg_sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == 0) {// 男孩
                    sex = 1;
                } else if (checkedId == 1) {
                    sex = 2;
                }
            }
        });
//        et_garten = (EditText) findViewById(R.id.editText3);
        tv_garten = (TextView) findViewById(R.id.tv_garten);
        rb_class1 = (RadioButton) findViewById(R.id.radio20);
        rb_class1.setId(20);
        rb_class1.setChecked(true);
        ((RadioButton) findViewById(R.id.radio21)).setId(21);
        ((RadioButton) findViewById(R.id.radio22)).setId(22);
        rg_class = (RadioGroup) findViewById(R.id.radioGroup2);
        rg_class.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == 20) {// 大班
                    gartenclass = 1;
                } else if (checkedId == 21) {
                    gartenclass = 2;
                } else if (checkedId == 22) {
                    gartenclass = 3;
                }
            }
        });
//		et_momname = (EditText) findViewById(R.id.editText4);
//		et_momjob = (EditText) findViewById(R.id.editText5);
//		et_fathername = (EditText) findViewById(R.id.editText6);
//		et_fatherjob = (EditText) findViewById(R.id.editText7);
//		et_address = (EditText) findViewById(R.id.editText8);
        et_parents = (EditText) findViewById(R.id.editText6);
        tv_born = (TextView) findViewById(R.id.tv_born);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_class = (TextView) findViewById(R.id.textView1);
    }

    public void btnBack(View v) {
        this.finish();
    }

    public void onClickBorn(View view) {
        ChangeDateDialog mChangeDateDialog = new ChangeDateDialog(RegisterTwoActivity.this);
        mChangeDateDialog.setAddress("2010", "5", "5");
        mChangeDateDialog.show();
        mChangeDateDialog.setAddresskListener(new OnAddressCListener() {

            @Override
            public void onClick(String province, String city, String area) {
                tv_born.setTextColor(Color.BLACK);
                tv_born.setText(province + "-" + city + "-" + area);
            }
        });
    }

    public void onClickCity(View view) {
        ChangeAddressDialog mChangeAddressDialog = new ChangeAddressDialog(RegisterTwoActivity.this);
        mChangeAddressDialog.setAddress("北京市", "北京市", "东城区");
        mChangeAddressDialog.show();
        mChangeAddressDialog.setAddresskListener(new OnAddressCodeCListener() {
            @Override
            public void onClick(String province, String city, String area, String code) {
                tv_city.setTextColor(Color.BLACK);
                if (city.endsWith("市") || city.endsWith("区") || city.endsWith("县")) {
                    if (area == null || area.equals("")) {
                        tv_city.setText(province + "-" + city);
                    } else {
                        tv_city.setText(province + "-" + city + "-" + area);
                    }
                } else {
                    if (area == null || area.equals("")) {
                        tv_city.setText(province + "-" + city);
                    } else {
                        tv_city.setText(province + "-" + city + "-" + area);
                    }
                }
                city_code = code;
                tv_garten.setTextColor(getResources().getColor(R.color.login_line));
                tv_garten.setText(getString(R.string.youeryuan));
                Log.d(TAG, province + "-" + city + "-" + area + "-" + code);
            }
        });
    }

    public void onClassClick(View view) {
        ChangeClassDialog mChangeDateDialog = new ChangeClassDialog(RegisterTwoActivity.this);
        mChangeDateDialog.setAddress("01");
        mChangeDateDialog.show();
        mChangeDateDialog.setAddresskListener(new OnSingleWheelListener() {
            @Override
            public void onClick(String province) {
                tv_class.setTextColor(Color.BLACK);
                tv_class.setText(province);
            }
        });
    }

    public void onClickGarten(View view) {
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.GARTEN_LIST);
        if (TextUtils.isEmpty(city_code)) {
            ActivityUtils.showToast(RegisterTwoActivity.this, "请先选择城市信息，谢谢");
            return;
        }
        params.addQueryStringParameter("code", city_code);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<GartenBean>() {
                }.getType();
                GartenBean bean = gson.fromJson(s, type);
                if (bean.getCode().equals("SUCCESS")) {
                    List<GartenBean.Data.SystemCodes> list = bean.getData().getSystemCodes();
//                    if(list == null||list.size() == 0)
//                        ActivityUtils.showToastForFail(RegisterTwoActivity.this, "未收录幼儿园信息");
//                    else{
                    ChangeGartenDialog mChangeDateDialog = new ChangeGartenDialog(RegisterTwoActivity.this, list);
                    mChangeDateDialog.setAddress("中国人民解放军政治部66400部队幼儿园");
                    mChangeDateDialog.show();
                    mChangeDateDialog.setAddresskListener(new OnSingleWheelListener() {
                        @Override
                        public void onClick(String province) {
                            tv_garten.setTextColor(Color.BLACK);
                            tv_garten.setText(province);
                        }
                    });
//                    }
                } else if (bean.getCode().equals("FAIL")) {
                    ActivityUtils.showToastForFail(RegisterTwoActivity.this, bean.getCodeInfo());
                    return;
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ActivityUtils.showToastForFail(RegisterTwoActivity.this, "网络连接失败");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void btnConfirm(View view) {
        childname = et_name.getText().toString();
        born = tv_born.getText().toString();
        garten = tv_garten.getText().toString();
//		momname = et_momname.getText().toString();
//		monjob = et_momjob.getText().toString();
//		fathername = et_fathername.getText().toString();
//		fatherjob = et_fatherjob.getText().toString();
//		address = et_address.getText().toString();
        parents = et_parents.getText().toString();
        city = tv_city.getText().toString();
        if (TextUtils.isEmpty(childname)) {
            DialogUtils.showMyDialog(RegisterTwoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", "请填写孩子姓名", null);
            return;
        }
        if (TextUtils.isEmpty(born) || born.equals(getString(R.string.born))) {
            DialogUtils.showMyDialog(RegisterTwoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", "请填写出生时间", null);
            return;
        }
        if (city.equals(getString(R.string.city)) || TextUtils.isEmpty(city)) {
            DialogUtils.showMyDialog(RegisterTwoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", "请填写地址信息", null);
            return;
        }
        if (TextUtils.isEmpty(garten) || garten.equals(getString(R.string.youeryuan))) {
            DialogUtils.showMyDialog(RegisterTwoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", "请填写幼儿园信息", null);
            return;
        }

        if (!TextUtils.isEmpty(tv_class.getText().toString()) && !tv_class.getText().toString().equals("00"))
            childclass = tv_class.getText().toString();
        else {
            DialogUtils.showMyDialog(RegisterTwoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", "请填写班级信息", null);
            return;
        }
        if (TextUtils.isEmpty(parents)) {
            DialogUtils.showMyDialog(RegisterTwoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", "请填家长姓名", null);
            return;
        }
//        Intent intent = new Intent(RegisterTwoActivity.this, RegisterThreeActivity.class);
//        intent.putExtra("userName", username);
//        intent.putExtra("userPwd", password);
//        intent.putExtra("userEmail", email);
//        intent.putExtra("phoneId", phoneId);
//        intent.putExtra("userDetail.sex", sex);
//        intent.putExtra("userDetail.kindergarten", garten);
//        intent.putExtra("userDetail.realName", childname);
//        intent.putExtra("userDetail.birthdayShow", born);
//        intent.putExtra("userDetail.level", gartenclass + "-" + childclass);
//        intent.putExtra("userDetail.address1", city);
//        intent.putExtra("userDetail.surname", surname);
//        intent.putExtra("userDetail.parents", parents);
//        intent.putExtra("citycode", city_code);
//        startActivity(intent);
//        finish();
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.REGINST_URL);
        params.addQueryStringParameter("userName", username);
        params.addQueryStringParameter("userPwd", password);
        params.addQueryStringParameter("userEmail", email);
        params.addQueryStringParameter("phoneId", phoneId);
        params.addQueryStringParameter("userDetail.sex", sex+"");
        params.addQueryStringParameter("userDetail.kindergarten", garten);
        params.addQueryStringParameter("userDetail.realName", childname);
        params.addQueryStringParameter("userDetail.birthdayShow", born);
        params.addQueryStringParameter("userDetail.level", gartenclass + "-" + childclass);
        params.addQueryStringParameter("userDetail.address1", city);
        params.addQueryStringParameter("userDetail.surname", surname);
        params.addQueryStringParameter("userDetail.parents", parents);
//        params.put("citycode", city_code);
        params.addQueryStringParameter("userDetail.subscibed", 0+"");
        params.addQueryStringParameter("userStatus", 0+"");
        DialogUtils.showMyDialog(this,MyPreferences.SHOW_PROGRESS_DIALOG,null,"加载中...",null);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<Bean>() {
                }.getType();
                Bean bean = gson.fromJson(s, type);
                if (bean.getCode().equals("SUCCESS")) {
                    ActivityUtils.showToastForSuccess(RegisterTwoActivity.this, bean.getCodeInfo());
                    SPUtility.putSPString(RegisterTwoActivity.this, "username", username);
                    SPUtility.putSPString(RegisterTwoActivity.this, "userPwd", password);
                    SPUtility.putSPString(RegisterTwoActivity.this, "userEmail", email);
                    SPUtility.putSPString(RegisterTwoActivity.this, "phoneId", phoneId);
                    SPUtility.putSPInteger(RegisterTwoActivity.this, "userDetail.sex", sex);
                    SPUtility.putSPString(RegisterTwoActivity.this, "userDetail.kindergarten", garten);
                    SPUtility.putSPString(RegisterTwoActivity.this, "userDetail.realName", childname);
                    SPUtility.putSPString(RegisterTwoActivity.this, "userDetail.birthdayShow", born);
                    SPUtility.putSPString(RegisterTwoActivity.this, "userDetail.level", gartenclass + "-" + childclass);
                    SPUtility.putSPString(RegisterTwoActivity.this, "userDetail.address1", city);
                    SPUtility.putSPString(RegisterTwoActivity.this, "userDetail.parents", parents);
                    SPUtility.putSPString(RegisterTwoActivity.this, "userDetail.surname", surname);
                    SPUtility.putSPInteger(RegisterTwoActivity.this, "userDetail.subscibed", 0);
                    SPUtility.putSPString(RegisterTwoActivity.this, "citycode", city_code);
                    DialogUtils.dismissMyDialog();
                    Intent intent = new Intent(RegisterTwoActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else if (bean.getCode().equals("FAIL")) {
                    DialogUtils.dismissMyDialog();
                    DialogUtils.showMyDialog(RegisterTwoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "注册失败", bean.getCodeInfo(), null);
                    return;
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                DialogUtils.dismissMyDialog();
                ActivityUtils.showToastForFail(RegisterTwoActivity.this, "注册失败,");
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }

        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
