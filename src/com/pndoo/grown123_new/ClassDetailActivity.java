package com.pndoo.grown123_new;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.dto.base.ClassDetailBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.SPUtility;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class ClassDetailActivity extends BaseActivity {

    int userStatus;
    int isHaveGroup;
    private LinearLayout view_layout;
    private View mRootView;
    private TextView tv_num, tv_name, tv_teacher, tv_groupNum, tv_school, tv_class;
    private EditText et_name, et_teacher, et_school, et_class;
    private Button btn_exit,btn_changeTea,btn_changeInfo;
    private GridView gridview;
    private ClassDetailBean.Data data;
    private List<ClassDetailBean.Data.Users> list = new ArrayList<>();
    private GridViewAdapter adapter;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_classdetail);

        userStatus = UserUtil.getInstance(this).getUserStatus();//用户状态0 普通用户 1 作业老师 2直播老师
        isHaveGroup = UserUtil.getInstance(this).getIsHaveGroup();//群组状态 0没有 1 有

        initView(isHaveGroup, userStatus);
    }


    private void initView(int isHaveGroup, int userStatus) {
        view_layout = (LinearLayout) findViewById(R.id.layout_content);
        if (isHaveGroup == 0) {
            if (userStatus == 1) {
                initView_Tea_No();
            } else {
                initView_Stu_No();
            }
        } else {
            if (userStatus == 1) {
                initView_Tea_Have();
            } else {
                initView_Stu_Have();
            }
        }
    }

    private void initView_Tea_Have() {
        view_layout.removeAllViews();
        mRootView = LayoutInflater.from(this).inflate(R.layout.view_classdetail_tea, null);
        view_layout.addView(mRootView);
        tv_num = (TextView) mRootView.findViewById(R.id.textView41);
        et_name = (EditText) mRootView.findViewById(R.id.textView43);
        et_teacher = (EditText) mRootView.findViewById(R.id.textView45);
        tv_groupNum = (TextView) mRootView.findViewById(R.id.textView47);
        et_school = (EditText) mRootView.findViewById(R.id.textView49);
        et_class = (EditText) mRootView.findViewById(R.id.textView50);
        gridview = (GridView) mRootView.findViewById(R.id.gridView);
        adapter = new GridViewAdapter(this);
        gridview.setAdapter(adapter);
        loadData();

        (mRootView.findViewById(R.id.button10)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassDetailActivity.this,ChangeTeaActivity.class);
                intent.putExtra("groupId",data.getGroupId());
                startActivityForResult(intent,0);
            }
        });

        (mRootView.findViewById(R.id.button11)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_name.getText().toString().trim())||TextUtils.isEmpty(et_teacher.getText().toString().trim())||TextUtils.isEmpty(et_school.getText().toString().trim())||TextUtils.isEmpty(et_class.getText().toString().trim()))
                {
                    ActivityUtils.showToast(ClassDetailActivity.this,"信息不可为空");
                }else{
                    org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_UPDATEGROUP);
                    params.addQueryStringParameter("groupName", et_name.getText().toString().trim());
                    params.addQueryStringParameter("groupCity", et_teacher.getText().toString().trim());
                    params.addQueryStringParameter("groupKindergarten", et_school.getText().toString().trim());
                    params.addQueryStringParameter("groupClass", et_class.getText().toString().trim());
                    params.addQueryStringParameter("groupId", data.getGroupId()+"");

                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Gson gson = new Gson();
                            java.lang.reflect.Type type = new TypeToken<Bean>() {
                            }.getType();
                            Bean bean = gson.fromJson(s, type);
                            if (bean != null && bean.getCode().equals("SUCCESS")) {
                                ActivityUtils.showToast(ClassDetailActivity.this, "信息修改成功");
                            } else {
                                ActivityUtils.showToast(ClassDetailActivity.this, bean.getCodeInfo());
                            }
                        }

                        @Override
                        public void onError(Throwable throwable, boolean b) {
                            ActivityUtils.showToast(ClassDetailActivity.this, throwable.getMessage());
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
        });
    }

    private void initView_Tea_No() {
        view_layout.removeAllViews();
        mRootView = LayoutInflater.from(this).inflate(R.layout.view_creatclass, null);
        view_layout.addView(mRootView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        mRootView.findViewById(R.id.title).setVisibility(View.GONE);
        final EditText name = (EditText) mRootView.findViewById(R.id.textView43);
        final EditText city = (EditText) mRootView.findViewById(R.id.textView45);
        final EditText school = (EditText) mRootView.findViewById(R.id.textView49);
        final EditText et_class = (EditText) mRootView.findViewById(R.id.textView50);
        ((Button) findViewById(R.id.button10)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString().trim()) || TextUtils.isEmpty(et_class.getText().toString().trim()) || TextUtils.isEmpty(school.getText().toString().trim()) || TextUtils.isEmpty(city.getText().toString().trim())) {
                    ActivityUtils.showToast(ClassDetailActivity.this, "请填写所有信息");
                    return;
                }
                org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_CREATECLASS);
                params.addQueryStringParameter("userId", UserUtil.getInstance(ClassDetailActivity.this).getUserId());
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
                            SPUtility.putSPInteger(ClassDetailActivity.this, "isHaveGroup", 1);//更新保存数据
                            initView(isHaveGroup, userStatus);
                        } else if (bean.getCode().equals(MyPreferences.FAIL)) {
                            ActivityUtils.showToast(ClassDetailActivity.this, bean.getCodeInfo());
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

    private void initView_Stu_No() {
        view_layout.removeAllViews();
        mRootView = LayoutInflater.from(this).inflate(R.layout.job_addclass, null);
        view_layout.addView(mRootView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        mRootView.findViewById(R.id.title).setVisibility(View.GONE);
        final EditText et_ClassNum = (EditText) findViewById(R.id.editText);
        ((Button) findViewById(R.id.button9)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = et_ClassNum.getText().toString().trim();
                if (num.length() != 6 && !num.matches("[0-9]*")) {
                    ActivityUtils.showToast(ClassDetailActivity.this, "班群号输入格式有误");
                    return;
                }
                org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_ADD_CLASS);
                params.addQueryStringParameter("userId", UserUtil.getInstance(ClassDetailActivity.this).getUserId());
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
                            SPUtility.putSPInteger(ClassDetailActivity.this, "isHaveGroup", 1);//更新保存数据
                            initView(isHaveGroup, userStatus);
                        } else {
                            ActivityUtils.showToast(ClassDetailActivity.this, bean.getCodeInfo());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        DialogUtils.dismissMyDialog();
                        ActivityUtils.showToast(ClassDetailActivity.this, throwable.getMessage());
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

    private void initView_Stu_Have() {
        view_layout.removeAllViews();
        mRootView = LayoutInflater.from(this).inflate(R.layout.view_classdetail_stu, null);
        view_layout.addView(mRootView);
        tv_num = (TextView) mRootView.findViewById(R.id.textView41);
        tv_name = (TextView) mRootView.findViewById(R.id.textView43);
        tv_teacher = (TextView) mRootView.findViewById(R.id.textView45);
        tv_groupNum = (TextView) mRootView.findViewById(R.id.textView47);
        tv_school = (TextView) mRootView.findViewById(R.id.textView49);
        tv_class = (TextView) mRootView.findViewById(R.id.textView50);
        gridview = (GridView) mRootView.findViewById(R.id.gridView);
        adapter = new GridViewAdapter(this);
        gridview.setAdapter(adapter);
        btn_exit = (Button) mRootView.findViewById(R.id.button10);
        loadData();

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_ADD_CLASS);
                params.addQueryStringParameter("userId", UserUtil.getInstance(ClassDetailActivity.this).getUserId());
                params.addQueryStringParameter("groupId", data.getGroupId()+"");

                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<Bean>() {
                        }.getType();
                        Bean bean = gson.fromJson(s, type);
                        if (bean != null && bean.getCode().equals("SUCCESS")) {
                            isHaveGroup = 0;
                            SPUtility.putSPInteger(ClassDetailActivity.this, "isHaveGroup", 0);//更新保存数据
                            initView(isHaveGroup, userStatus);
                        } else {
                            ActivityUtils.showToast(ClassDetailActivity.this, bean.getCodeInfo());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        ActivityUtils.showToast(ClassDetailActivity.this, throwable.getMessage());
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

    private void loadData() {
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_CLASSDETAIL);
        params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<ClassDetailBean>() {
                }.getType();
                ClassDetailBean bean = gson.fromJson(s, type);

                if (bean.getCode().equals(MyPreferences.SUCCESS)) {
                    data = bean.getData();
                    list = data.getUsers();
                    setData();
                } else if (bean.getCode().equals(MyPreferences.FAIL)) {
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

    private void setData() {
        if (userStatus == 1) {
            tv_num.setText(data.getGroupCode());
            et_name.setText(data.getGroupName());
            tv_teacher.setText(data.getGroupCity());
            tv_groupNum.setText(data.getUsers().size() + "");
            et_school.setText(data.getGroupKindergarten());
            et_class.setText(data.getGroupClass());
        } else {
            tv_num.setText(data.getGroupCode());
            tv_name.setText(data.getGroupName());
            tv_teacher.setText(data.getGroupCity());
            tv_groupNum.setText(data.getUsers().size() + "");
            tv_school.setText(data.getGroupKindergarten());
            tv_class.setText(data.getGroupClass());
        }
        adapter.notifyDataSetChanged();
    }

    public void onGroupClick(View view) {

    }

    public void btnBack(View v) {
        this.finish();
    }

    private class GridViewAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private DisplayImageOptions options;

        public GridViewAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img)
                    // 设置图片下载期间显示的图片  //zhibo_default
                    .showImageForEmptyUri(R.drawable.default_img)
                    // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.default_img)
                    // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)
                    // 设置下载的图片是否缓存在内存中
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size() <= 3 ? list.size() : 3;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_addjob, null);
                holder.iv_photo = (ImageView) convertView.findViewById(R.id.iv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ViewGroup.LayoutParams params = holder.iv_photo.getLayoutParams();
            params.width = 148;
            params.height = 148;
            holder.iv_photo.setLayoutParams(params);
            String url = Preferences.IMAGE_HTTP_LOCATION + list.get(position).getUserPortraits();
            ImageLoader.getInstance().displayImage(url, holder.iv_photo, options);

            return convertView;
        }

        private class ViewHolder {
            private ImageView iv_photo;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 0x01){
            isHaveGroup = 0;
            SPUtility.putSPInteger(ClassDetailActivity.this, "isHaveGroup", 0);//更新保存数据
            initView(isHaveGroup, userStatus);
        }
    }
}
