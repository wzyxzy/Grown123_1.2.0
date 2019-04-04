package com.pndoo.grown123_new;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.dto.base.GartenBean;
import com.pndoo.grown123_new.dto.base.UserVO;
import com.pndoo.grown123_new.model.UserUtil;
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
public class BabyInfoActivity extends BaseActivity {
    private TextView tv_header_title;
    private TextView tv_born, tv_city, tv_class, tv_garten;
    private EditText et_name, et_parents;
    // et_momname, et_momjob, et_fathername, et_fatherjob, et_address;
    private RadioGroup rg_sex, rg_class;
    private RadioButton rb_boy, rb_girl, rb_class1, rb_class2, rb_class3;
    private String username, password, email, phoneId, surname, childname, born, garten, childclass = "01", parents, city;
    // momname, monjob, fathername, fatherjob, address;
    private int sex = 1, gartenclass = 1;
    private final String TAG = getClass().getSimpleName();
    private String city_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_baby);

        initView();
    }

    private void initView() {
        // TODO Auto-generated method stub

        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_header_title.setText(getString(R.string.xiugai));

        UserVO userinfo = UserUtil.getInstance(this);
        et_name = (EditText) findViewById(R.id.editText1);
        et_name.setText(userinfo.getRealName());
        rg_sex = (RadioGroup) findViewById(R.id.radioGroup1);
        rb_boy = (RadioButton) findViewById(R.id.radio0);
        rb_boy.setId(0);
        rb_girl = (RadioButton) findViewById(R.id.radio1);
        rb_girl.setId(1);
        if (userinfo.getSex() == 1)
            rb_boy.setChecked(true);
        else
            rb_girl.setChecked(true);
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

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
        tv_garten = (TextView) findViewById(R.id.tv_garten);
        tv_garten.setTextColor(Color.BLACK);
        tv_garten.setText(userinfo.getKindergarten());
        city_code = userinfo.getCity_code();
        rb_class1 = (RadioButton) findViewById(R.id.radio20);
        rb_class1.setId(20);
        rb_class2 = ((RadioButton) findViewById(R.id.radio21));
        rb_class2.setId(21);
        rb_class3 = ((RadioButton) findViewById(R.id.radio22));
        rb_class3.setId(22);
        String[] s = userinfo.getLevel().split("-");
        Log.d(TAG, s[0] + "--------" + s[1]);
        if (s[0].equals("1")) {
            rb_class1.setChecked(true);
            gartenclass = 1;
        } else if (s[0].equals("2")) {
            rb_class2.setChecked(true);
            gartenclass = 2;
        } else if (s[0].equals("3")) {
            rb_class3.setChecked(true);
            gartenclass = 3;
        }
        rg_class = (RadioGroup) findViewById(R.id.radioGroup2);
        rg_class.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

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
        et_parents = (EditText) findViewById(R.id.editText6);
        et_parents.setText(userinfo.getParents());

        tv_born = (TextView) findViewById(R.id.tv_born);
        tv_born.setTextColor(Color.BLACK);
        tv_born.setText(userinfo.getBirthdayShow());

        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_city.setTextColor(Color.BLACK);
        tv_city.setText(userinfo.getAddress1());

        tv_class = (TextView) findViewById(R.id.textView1);
        tv_city.setTextColor(Color.BLACK);
        tv_class.setText(s[1]);
    }

    public void btnBack(View v) {
        this.finish();
    }

    public void onClickBorn(View view) {
        ChangeDateDialog mChangeDateDialog = new ChangeDateDialog(BabyInfoActivity.this);
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
        ChangeAddressDialog mChangeAddressDialog = new ChangeAddressDialog(BabyInfoActivity.this);
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
            }
        });
    }

    public void onClassClick(View view) {
        ChangeClassDialog mChangeDateDialog = new ChangeClassDialog(BabyInfoActivity.this);
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
            ActivityUtils.showToast(BabyInfoActivity.this, "请先选择城市信息，谢谢");
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
//                    if (list == null || list.size() == 0)
//                        ActivityUtils.showToastForFail(BabyInfoActivity.this, "未收录幼儿园信息");
//                    else {
                    ChangeGartenDialog mChangeDateDialog = new ChangeGartenDialog(BabyInfoActivity.this, list);
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
                    ActivityUtils.showToastForFail(BabyInfoActivity.this, bean.getCodeInfo());
                    return;
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ActivityUtils.showToastForFail(BabyInfoActivity.this, "网络连接失败");
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
        parents = et_parents.getText().toString();
        city = tv_city.getText().toString();
        if (TextUtils.isEmpty(childname)) {
            DialogUtils.showMyDialog(BabyInfoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "修改失败", "请填写孩子姓名", null);
            return;
        }
        if (TextUtils.isEmpty(born) || born.equals(getString(R.string.born))) {
            DialogUtils.showMyDialog(BabyInfoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "修改失败", "请填写出生时间", null);
            return;
        }
        if (city.equals(getString(R.string.city)) || TextUtils.isEmpty(city)) {
            DialogUtils.showMyDialog(BabyInfoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "修改失败", "请填写地址信息", null);
            return;
        }
        if (TextUtils.isEmpty(garten) || garten.equals(getString(R.string.youeryuan))) {
            DialogUtils.showMyDialog(BabyInfoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "修改失败", "请填写幼儿园信息", null);
            return;
        }

        if (!TextUtils.isEmpty(tv_class.getText().toString()) && !tv_class.getText().toString().equals("00"))
            childclass = tv_class.getText().toString();
        else {
            DialogUtils.showMyDialog(BabyInfoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "修改失败", "请填写班级信息", null);
            return;
        }
        if (TextUtils.isEmpty(parents)) {
            DialogUtils.showMyDialog(BabyInfoActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "修改失败", "请填家长姓名", null);
            return;
        }

        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.UPDATE_BABYINFO);
        params.addQueryStringParameter("userDetail.userId", UserUtil.getInstance(this).getUserId());
        params.addQueryStringParameter("userDetail.sex", sex+"");
        params.addQueryStringParameter("userDetail.kindergarten", garten);
        params.addQueryStringParameter("userDetail.realName", childname);
        params.addQueryStringParameter("userDetail.birthdayShow", born);
        params.addQueryStringParameter("userDetail.level", gartenclass + "-" + childclass);
        params.addQueryStringParameter("userDetail.address1", city);
        params.addQueryStringParameter("userDetail.parents", parents);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<Bean>() {
                }.getType();
                Bean bean = gson.fromJson(s, type);
                // Bean bean = JSON.parseObject(s, Bean.class);
                if (bean.getCode().equals("SUCCESS")) {
                    ActivityUtils.showToastForSuccess(BabyInfoActivity.this,
                            bean.getCodeInfo());
                    SPUtility.putSPInteger(BabyInfoActivity.this,
                            "userDetail.sex", sex);
                    SPUtility.putSPString(BabyInfoActivity.this,
                            "userDetail.kindergarten", garten);
                    SPUtility.putSPString(BabyInfoActivity.this,
                            "userDetail.realName", childname);
                    SPUtility.putSPString(BabyInfoActivity.this,
                            "userDetail.birthdayShow", born);
                    SPUtility.putSPString(BabyInfoActivity.this,
                            "userDetail.level", gartenclass + "-" + childclass);
                    SPUtility.putSPString(BabyInfoActivity.this,
                            "userDetail.address1", city);
                    SPUtility.putSPString(BabyInfoActivity.this,
                            "userDetail.parents", parents);
                    SPUtility.putSPString(BabyInfoActivity.this,
                            "citycode", city_code);
                    finish();
                    return;
                } else if (bean.getCode().equals("FAIL")) {
                    DialogUtils.showMyDialog(BabyInfoActivity.this,
                            MyPreferences.SHOW_ERROR_DIALOG, "修改失败", bean.getCodeInfo(),
                            null);
                    return;
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ActivityUtils.showToastForFail(BabyInfoActivity.this,
                        "修改失败,");
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
