package com.pndoo.grown123_new.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.RateActivity;
import com.pndoo.grown123_new.adapter.RateAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.NoBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.MyPreferences;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAO on 2017-07-10.
 */

public class Fragment_Rate extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View mRootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listview;
    private RateAdapter adapter;
    private List<NoBean.Data> list = new ArrayList<>();
    private final String TAG = getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_rate, null);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        loadData();
    }

    private void loadData() {
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_ALLHOMEWORK);
        params.addQueryStringParameter("userId", UserUtil.getInstance(getActivity()).getUserId());

        x.http().post(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String s) {
                Log.d(TAG,"sssssssss="+s);
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<NoBean>() {
                }.getType();
                NoBean bookJson = gson.fromJson(s, type);

                if (bookJson.getCode().equals(MyPreferences.SUCCESS)) {
                    list.clear();
                    list.addAll(bookJson.getData());
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                }
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.d(TAG,throwable.getMessage());
                if(swipeRefreshLayout.isRefreshing())
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
        swipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.refresh_view);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        listview = (ListView) mRootView.findViewById(R.id.listView);
        adapter = new RateAdapter(getActivity(),null);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(getActivity(), RateActivity.class);
                intent.putExtra("homework.id",list.get(position).getHomeworkId());
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
