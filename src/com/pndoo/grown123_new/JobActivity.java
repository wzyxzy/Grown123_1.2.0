package com.pndoo.grown123_new;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.adapter.MyViewPagerAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.fragment.Fragment_Done;
import com.pndoo.grown123_new.fragment.Fragment_No;
import com.pndoo.grown123_new.fragment.Fragment_Rate;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.SPUtility;
import com.pndoo.grown123_new.view.CustomViewpager;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class JobActivity extends FragmentActivity implements View.OnClickListener {
    private LinearLayout kong_layout, content_layout, btn_no, btn_done, btn_rate;
    private EditText et_ClassNum;
    private ImageView iv_no, iv_done, iv_rate;
    private TextView tv_no, tv_done;
    private final String TAG = getClass().getSimpleName();
    private Context context = JobActivity.this;
    int userStatus;
    int isHaveGroup;
    private CustomViewpager viewpager;
    private MyViewPagerAdapter myAdapter;
    Fragment f_no;
    Fragment f_done;
    Fragment f_rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_job);

        userStatus = UserUtil.getInstance(this).getUserStatus();//用户状态0 普通用户 1 作业老师 2直播老师
        isHaveGroup = UserUtil.getInstance(this).getIsHaveGroup();//群组状态 0没有 1 有

        initView(isHaveGroup, userStatus);
    }


    private void initView(int isHaveGroup, int userStatus) {
        kong_layout = (LinearLayout) findViewById(R.id.kong_layout);
        content_layout = (LinearLayout) findViewById(R.id.content_layout);

        if (isHaveGroup == 0) {
            if (userStatus == 1) {
                initView_Tea_No();
            } else {
                initView_Stu_No();
            }
        } else {
            initView_StuOrTea_Have();
        }
    }

    //学生端有群组
    private void initView_StuOrTea_Have() {
        kong_layout.setVisibility(View.GONE);
        content_layout.setVisibility(View.VISIBLE);
        setTitleContent(userStatus);
    }

    private void setTitleContent(int userStatus) {
        btn_no = (LinearLayout) findViewById(R.id.btn_no);
        btn_done = (LinearLayout) findViewById(R.id.btn_done);
        btn_rate = (LinearLayout) findViewById(R.id.btn_rate);
        iv_no = (ImageView) findViewById(R.id.imageView1);
        iv_done = (ImageView) findViewById(R.id.imageView5);
        iv_rate = (ImageView) findViewById(R.id.imageView2);
        tv_no = (TextView) findViewById(R.id.tv_no);
        tv_done = (TextView) findViewById(R.id.tv_done);

        tv_no.setText(userStatus != 1 ? "未完成" : "未打分");
        tv_done.setText(userStatus != 1 ? "已发布" : "已打分");

        btn_no.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        btn_rate.setOnClickListener(this);

        viewpager = (CustomViewpager) findViewById(R.id.viewpager);
        List<Fragment> list = new ArrayList<>();
        if (userStatus != 1) {
            f_no = new Fragment_No();
            f_done = new Fragment_Done();
            Bundle b = new Bundle();
            b.putBoolean("isHeader", true);
            f_done.setArguments(b);
            f_rate = new Fragment_Rate();

            list.add(f_no);
            list.add(f_done);
            list.add(f_rate);
        }else{
            f_no = new Fragment_No();
            Bundle b1 = new Bundle();
            b1.putBoolean("isDone", false);
            f_no.setArguments(b1);
            list.add(f_no);

            f_done = new Fragment_No();
            Bundle b2 = new Bundle();
            b2.putBoolean("isDone", true);
            f_done.setArguments(b2);
            list.add(f_done);

            f_rate = new Fragment_Rate();
            list.add(f_rate);
        }
        myAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(myAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

//        ((Fragment_Done)f_no).onRefresh();
    }

    //学生端未有群组界面
    private void initView_Stu_No() {
        kong_layout.setVisibility(View.VISIBLE);
        content_layout.setVisibility(View.GONE);

        et_ClassNum = (EditText) findViewById(R.id.editText);
        ((Button) findViewById(R.id.button9)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = et_ClassNum.getText().toString().trim();
                if (num.length() != 6 && !num.matches("[0-9]*")) {
                    ActivityUtils.showToast(context, "班群号输入格式有误");
                    return;
                }
                org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_ADD_CLASS);
                params.addQueryStringParameter("userId", UserUtil.getInstance(context).getUserId());
                params.addQueryStringParameter("groupCode", num);

                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        DialogUtils.dismissMyDialog();
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<Bean>() {
                        }.getType();
                        Bean bean = gson.fromJson(s, type);
                        if (bean != null && bean.getCode().equals("SUCCESS")) {
                            isHaveGroup = 1;
                            SPUtility.putSPInteger(context, "isHaveGroup", 1);//更新保存数据
                            initView(isHaveGroup, userStatus);
                        } else {
                            ActivityUtils.showToast(context, bean.getCodeInfo());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        DialogUtils.dismissMyDialog();
                        ActivityUtils.showToast(context, throwable.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }

    //教师端未有群组界面
    private void initView_Tea_No() {
        kong_layout.setVisibility(View.VISIBLE);
        content_layout.setVisibility(View.GONE);
        kong_layout.removeAllViews();
        View view = LayoutInflater.from(this).inflate(R.layout.view_creatclass, null);
        kong_layout.addView(view, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        final EditText name = (EditText) view.findViewById(R.id.textView43);
        final EditText city = (EditText) view.findViewById(R.id.textView45);
        final EditText school = (EditText) view.findViewById(R.id.textView49);
        final EditText et_class = (EditText) view.findViewById(R.id.textView50);
        ((Button) findViewById(R.id.button10)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString().trim()) || TextUtils.isEmpty(et_class.getText().toString().trim()) || TextUtils.isEmpty(school.getText().toString().trim()) || TextUtils.isEmpty(city.getText().toString().trim())) {
                    ActivityUtils.showToast(context, "请填写所有信息");
                    return;
                }
                org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_CREATECLASS);
                params.addQueryStringParameter("userId", UserUtil.getInstance(JobActivity.this).getUserId());
                params.addQueryStringParameter("groupName", name.getText().toString().trim());
                params.addQueryStringParameter("groupCity", city.getText().toString().trim());
                params.addQueryStringParameter("groupKindergarten", school.getText().toString().trim());
                params.addQueryStringParameter("groupClass", et_class.getText().toString().trim());

                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<Bean>() {
                        }.getType();
                        Bean bean = gson.fromJson(s, type);
                        if (bean.getCode().equals(MyPreferences.SUCCESS)) {
                            isHaveGroup = 1;
                            SPUtility.putSPInteger(context, "isHaveGroup", 1);//更新保存数据
                            initView(isHaveGroup, userStatus);
                        } else if (bean.getCode().equals(MyPreferences.FAIL)) {
                            ActivityUtils.showToast(JobActivity.this, bean.getCodeInfo());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.d(TAG, throwable.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }

    private void viewChange(int i) {
        iv_no.setVisibility(i == 0 ? View.VISIBLE : View.INVISIBLE);
        iv_done.setVisibility(i == 1 ? View.VISIBLE : View.INVISIBLE);
        iv_rate.setVisibility(i == 2 ? View.VISIBLE : View.INVISIBLE);
        viewpager.setCurrentItem(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_no:
                viewChange(0);
                break;
            case R.id.btn_done:
                viewChange(1);
                break;
            case R.id.btn_rate:
                viewChange(2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Fragment_No.Fragment_No_REQUESTCODE:
                ((Fragment_No) f_no).onRefresh();
                break;
        }
    }
}
