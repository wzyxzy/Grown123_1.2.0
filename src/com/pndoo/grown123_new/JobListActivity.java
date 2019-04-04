package com.pndoo.grown123_new;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.adapter.JobListAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.JobListBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.MyPreferences;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class JobListActivity extends BaseActivity{

    private final String TAG = getClass().getSimpleName();
    private ListView listview;
    private JobListAdapter adapter;
    private ImageView iv_photo;
    private TextView tv_name;
    private List<JobListBean.Data> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_joblist);

        initView();
        loadData();
    }

    private void loadData() {
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_ALLOWNWORK);
        params.addQueryStringParameter("user.userId", UserUtil.getInstance(this).getUserId());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<JobListBean>() {
                }.getType();
                JobListBean bean = gson.fromJson(s, type);

                if (bean.getCode().equals(MyPreferences.SUCCESS)) {
                    list.clear();
                    list.addAll(bean.getData());
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                } else if (bean.getCode().equals(MyPreferences.FAIL)) {
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

    private void initView() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img)
                // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_img)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_img)
                // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();


        listview = (ListView) findViewById(R.id.listView);
        View view = LayoutInflater.from(this).inflate(R.layout.header_joblist,null);
        iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
        tv_name = (TextView) view.findViewById(R.id.textView48);
        String url = "file://"+ ActivityUtils.getSDPath()+"/photo.png";
        ImageLoader.getInstance().displayImage(url, iv_photo, options);

        listview.addHeaderView(view,null,false);
        adapter = new JobListAdapter(this,null);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(JobListActivity.this, JobDetailActivity.class);
                intent.putExtra("SubmissionId", list.get(position-1).getSubmissionId());
                startActivity(intent);
            }
        });
    }

    public void btnBack(View v) {
        this.finish();
    }
}
