package com.pndoo.grown123_new;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.adapter.RateWorkAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.RateBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.MyPreferences;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class RateActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listview;
    private final String TAG = getClass().getSimpleName();
    private List<RateBean.Data.DoneUser> list = new ArrayList<>();
    private RateWorkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rate);
        initView();
        loadData();
    }

    private void loadData() {
        int homeworkId = getIntent().getIntExtra("homework.id",0);
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_HOMEWORKRATE);
        params.addQueryStringParameter("user.userId", UserUtil.getInstance(this).getUserId());
        params.addQueryStringParameter("homework.id", homeworkId+"");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG,s);
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<RateBean>() {
                }.getType();
                RateBean bookJson = gson.fromJson(s, type);

                if (bookJson.getCode().equals(MyPreferences.SUCCESS)) {
                    list.clear();
                    list.addAll(bookJson.getData().getDoneUser());
                    for(RateBean.Data.UndoneUser bean:bookJson.getData().getUndoneUser()){
                        list.add(new RateBean.Data.DoneUser(bean.getUserName(),bean.getUserPortraits(),-1));
                    }
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                }
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.d(TAG, throwable.getMessage());
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
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
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_view);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        listview = (ListView) findViewById(R.id.listView);
        adapter = new RateWorkAdapter(this, null);
        listview.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    public void btnBack(View view) {
        finish();
    }
}
