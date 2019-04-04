package com.pndoo.grown123_new.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.AddJobActivity;
import com.pndoo.grown123_new.JobDoneActivity;
import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.adapter.NoAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.NoBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAO on 2017-07-10.
 */

public class Fragment_No extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private final String TAG = getClass().getSimpleName();
    private View mRootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listview;
    private NoAdapter adapter;
    private Context context;
    private List<NoBean.Data> list = new ArrayList<>();
    public static final int Fragment_No_REQUESTCODE = 54321;
    private int userStatus;//用户状态0 普通用户 1 作业老师 2直播老师
    private boolean isDone;//是否已审批

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_nodone, null);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userStatus = UserUtil.getInstance(getActivity()).getUserStatus();//用户状态0 普通用户 1 作业老师 2直播老师
        try {
            isDone = getArguments().getBoolean("isDone");
        }catch (Exception e){
            isDone = false;
        }
        initView();
        loadData();
        setListener();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setListener() {
        if (userStatus != 1) {
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (System.currentTimeMillis() > list.get(position).getEndTime().getTime()) {
                        Dialog dialog = new Dialog(context, R.style.my_dialog);
                        dialog.setContentView(R.layout.dialog_job_timeout);
                        dialog.setCancelable(true);
                        dialog.show();
                    } else {
                        Intent intent = new Intent(getActivity(), AddJobActivity.class);
                        intent.putExtra("jobcontent", list.get(position).getContent());
                        intent.putExtra("homeworkId", list.get(position).getHomeworkId());
                        getActivity().startActivityForResult(intent, Fragment_No_REQUESTCODE);
                    }
                }
            });
        } else {
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), JobDoneActivity.class);
                    intent.putExtra("isDone",isDone);
                    intent.putExtra("homeworkId", list.get(position).getHomeworkId());
                    getActivity().startActivity(intent);
                }
            });
        }
    }

    private void loadData() {
        if (userStatus != 1) {
            org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_UNHOMEWORK);
            params.addQueryStringParameter("userId", UserUtil.getInstance(context).getUserId());

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<NoBean>() {
                    }.getType();
                    NoBean bean = gson.fromJson(s, type);

                    if (bean != null && bean.getCode().equals("SUCCESS")) {
                        list.clear();
                        list.addAll(bean.getData());
                        adapter.setList(list);
                        adapter.notifyDataSetChanged();
                    } else {
                        ActivityUtils.showToast(context, bean.getCodeInfo());
                    }
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    ActivityUtils.showToast(context, throwable.getMessage());
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
        } else {
            org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_JOBLIST);
            params.addQueryStringParameter("userId", UserUtil.getInstance(context).getUserId());

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<NoBean>() {
                    }.getType();
                    NoBean bean = gson.fromJson(s, type);

                    if (bean != null && bean.getCode().equals("SUCCESS")) {
                        list.clear();
                        list.addAll(bean.getData());
                        adapter.setList(list);
                        adapter.notifyDataSetChanged();
                    } else {
                        ActivityUtils.showToast(context, bean.getCodeInfo());
                    }
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    ActivityUtils.showToast(context, throwable.getMessage());
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
    }

    private void initView() {
        context = getActivity();
        swipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.refresh_view);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        listview = (ListView) mRootView.findViewById(R.id.listView);
        adapter = new NoAdapter(getActivity(), null);
        listview.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
