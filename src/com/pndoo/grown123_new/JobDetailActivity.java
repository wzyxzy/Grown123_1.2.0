package com.pndoo.grown123_new;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.adapter.DoneGridViewAdapter;
import com.pndoo.grown123_new.adapter.DoneListViewAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.DoneBean;
import com.pndoo.grown123_new.dto.base.JobDetailBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.view.GridViewForScrollView;
import com.pndoo.grown123_new.view.ListViewForScrollView;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;

public class JobDetailActivity extends BaseActivity {
    private TextView tv_name, tv_content, tv_time, tv_zan, tv_ping, tv_zan_names;
    private ImageView iv_photo, iv_pai;
    private LinearLayout btn_zan, btn_ping, layout, layout_zan, devider, devider_all;
    private GridViewForScrollView gridview;
    private ListViewForScrollView listview;
    private int submissionId = 0;
    private JobDetailBean.Data data;
    private DisplayImageOptions options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jobdetail);
        submissionId = getIntent().getIntExtra("SubmissionId",0);
        initView();
        loadData();
    }

    private void loadData() {
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_SUBMITIDWORK);
        params.addQueryStringParameter("user.userId", UserUtil.getInstance(this).getUserId());
        params.addQueryStringParameter("submissionId", submissionId+"");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<JobDetailBean>() {
                }.getType();
                JobDetailBean bean = gson.fromJson(s, type);

                if (bean.getCode().equals(MyPreferences.SUCCESS)) {
                    data = bean.getData();
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
    private void initView() {
        iv_photo = (ImageView) findViewById(R.id.imageView16);
        tv_name = (TextView) findViewById(R.id.textView28);
        tv_content = (TextView) findViewById(R.id.textView29);
        tv_time = (TextView) findViewById(R.id.textView30);
        gridview = (GridViewForScrollView) findViewById(R.id.gridView);
        iv_pai = (ImageView) findViewById(R.id.iv_pai);
        btn_zan = (LinearLayout) findViewById(R.id.dianzan);
        tv_zan = (TextView) findViewById(R.id.textView32);
        btn_ping = (LinearLayout) findViewById(R.id.pinglun);
        tv_ping = (TextView) findViewById(R.id.textView31);
        layout = (LinearLayout) findViewById(R.id.layout);
        layout_zan = (LinearLayout) findViewById(R.id.layout_zan);
        devider = (LinearLayout) findViewById(R.id.devider);
        tv_zan_names = (TextView) findViewById(R.id.textView33);
        listview = (ListViewForScrollView) findViewById(R.id.listview);
        devider_all = (LinearLayout) findViewById(R.id.devider_all);
        devider_all.setVisibility(View.GONE);
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.zhibo_default)
                // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.zhibo_default)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.zhibo_default)
                // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();

    }

    private void setData() {
        String url = Preferences.IMAGE_HTTP_LOCATION + data.getUserPortraits();
        ImageLoader.getInstance().displayImage(url, iv_photo, options);

        tv_name.setText(data.getUserName());
        tv_content.setText(data.getJobContent());
        final String time = "20" + data.getAddTime().getYear() % 100 + "/" + (data.getAddTime().getMonth() + 1) + "/" + data.getAddTime().getDate() + "日 " + data.getAddTime().getHours() + ":" + data.getAddTime().getMinutes();
        tv_time.setText(time);
        setGridView(data, gridview);

        switch (data.getJobGrade()) {
            case -1:
                iv_pai.setBackgroundResource(R.drawable.m_zero);
                break;
            case 0:
                iv_pai.setBackgroundResource(R.drawable.m_medal);
                break;
            case 1:
                iv_pai.setBackgroundResource(R.drawable.m_bronze);
                break;
            case 2:
                iv_pai.setBackgroundResource(R.drawable.m_silverl);
                break;
            case 3:
                iv_pai.setBackgroundResource(R.drawable.m_gold);
                break;
        }

        tv_zan.setText(data.getLikeCount() + "");
        tv_ping.setText(data.getCommentCount() + "");

        layout.setVisibility(View.VISIBLE);
        layout.setBackgroundResource(R.drawable.release_121);
        layout_zan.setVisibility(View.VISIBLE);
        devider.setVisibility(View.VISIBLE);
        if (data.getLikeCount() == 0 && data.getCommentCount() == 0) {
            layout_zan.setVisibility(View.GONE);
            devider.setVisibility(View.GONE);
            layout.setBackgroundColor(getResources().getColor(R.color.transparent));
//            layout.setVisibility(View.GONE);
        } else if (data.getLikeCount() == 0) {
            layout_zan.setVisibility(View.GONE);
            devider.setVisibility(View.GONE);
        } else if (data.getCommentCount() == 0) {
            devider.setVisibility(View.GONE);
        }
        if (data.getLikeCount() != 0) {
            StringBuffer names = new StringBuffer();
            for (DoneBean.Data.LikeUsers name : data.getLikeUsers()) {
                names.append("," + name);
            }
            tv_zan_names.setText(names.substring(1, names.length() - 1));
        }
        if (data.getCommentCount() != 0) {
            DoneListViewAdapter adapter = new DoneListViewAdapter(this, data.getJobComments());
            listview.setAdapter(adapter);
        }
    }

    private void setGridView(final JobDetailBean.Data bean, GridViewForScrollView gridview) {
        DoneGridViewAdapter adapter = new DoneGridViewAdapter(this, bean.getJobPictures());
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String time = "20" + bean.getAddTime().getYear() % 100 + "/" + (bean.getAddTime().getMonth() + 1) + "/" + bean.getAddTime().getDate() + "日 " + bean.getAddTime().getHours() + ":" + bean.getAddTime().getMinutes();
                Intent intent = new Intent(JobDetailActivity.this, JobPictureActivity.class);
                Bundle b = new Bundle();
                b.putString("time", time);
                b.putInt("position", position);
                b.putString("content", bean.getJobContent());
                b.putInt("likecount", bean.getLikeCount());
                b.putInt("commentcount", bean.getCommentCount());
                ArrayList<String> ss = new ArrayList<String>();
                for (DoneBean.Data.JobPictures pic : bean.getJobPictures()) {
                    ss.add(pic.getJobPicture());
                }
                b.putStringArrayList("list", ss);
                intent.putExtras(b);
                intent.putExtra("layout", "DoneAdapter");
                startActivity(intent);
            }
        });
    }

    public void btnBack(View v) {
        this.finish();
    }

}
