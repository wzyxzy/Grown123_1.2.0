package com.pndoo.grown123_new.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.JobListActivity;
import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.adapter.DoneAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.dto.base.DoneBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.MyPreferences;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.tencent.qalsdk.service.QalService.context;

/**
 * Created by BAO on 2017-07-10.
 */

public class Fragment_Done extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View mRootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listview;
    private TextView name;
    private ImageView iv_photo;
    private DoneAdapter adapter;
    private final String TAG = getClass().getSimpleName();
    private List<DoneBean.Data> list = new ArrayList<>();
    private View headerView;
    private boolean isHeader = false;
    private int userStatus;//用户状态0 普通用户 1 作业老师 2直播老师
    private boolean isDone;//是否已审批
    private int homeworkId;
    private LinearLayout input_layout;
    private RelativeLayout layout;
    private PopupWindow pop;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_done, null);
        return mRootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle b = getArguments();
        isHeader = b.getBoolean("isHeader");
        userStatus = UserUtil.getInstance(getActivity()).getUserStatus();
        isDone = b.getBoolean("isDone");
        homeworkId = b.getInt("homeworkId");
        Log.d(TAG, isHeader + "-------------homeworkId==" + homeworkId);
        initView();
        loadData();
    }

    private void loadData() {
        if (userStatus != 1) {
            org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_ALLSUBMITWORK);
            params.addQueryStringParameter("user.userId", UserUtil.getInstance(getActivity()).getUserId());

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<DoneBean>() {
                    }.getType();
                    DoneBean bean = gson.fromJson(s, type);

                    if (bean.getCode().equals(MyPreferences.SUCCESS)) {
                        list.clear();
                        list.addAll(bean.getData());
                        adapter.setList(list);
                        adapter.notifyDataSetChanged();
                    } else if (bean.getCode().equals(MyPreferences.FAIL)) {
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
        } else {
            org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_TEAJOBLIST);
            params.addQueryStringParameter("user.userId", UserUtil.getInstance(getActivity()).getUserId());
            params.addQueryStringParameter("homework.id", homeworkId + "");
            params.addQueryStringParameter("state", isDone ? (1 + "") : (0 + ""));
            Log.d(TAG, "url====" + Preferences.JOB_TEAJOBLIST + "&user.userId=" + UserUtil.getInstance(getActivity()).getUserId() + "&homework.id=" + homeworkId + "&state=" + "1");

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Log.d(TAG, s);
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<DoneBean>() {
                    }.getType();
                    DoneBean bean = gson.fromJson(s, type);

                    if (bean.getCode().equals(MyPreferences.SUCCESS)) {
                        list.clear();
                        list.addAll(bean.getData());
                        if(list != null&&list.size() == 0){
                            ActivityUtils.showToast(getActivity(),"没有符合要求的作业");
                            return;
                        }
                        adapter.setList(list);
                        adapter.notifyDataSetChanged();
                    } else if (bean.getCode().equals(MyPreferences.FAIL)) {
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

    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.refresh_view);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        layout = (RelativeLayout) mRootView.findViewById(R.id.layout);
        input_layout = (LinearLayout) mRootView.findViewById(R.id.input_layout);


        listview = (ListView) mRootView.findViewById(R.id.listView);
        if (isHeader) {
            DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img)
                    // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.default_img)
                    // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.default_img)
                    // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)
                    // 设置下载的图片是否缓存在内存中
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();

            headerView = LayoutInflater.from(getActivity()).inflate(R.layout.done_fg_header, null);
            name = (TextView) headerView.findViewById(R.id.name);
            iv_photo = (ImageView) headerView.findViewById(R.id.pic);

            String url = "file://"+ActivityUtils.getSDPath()+"/photo.png";
            ImageLoader.getInstance().displayImage(url, iv_photo, options);

            iv_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), JobListActivity.class);
                    getActivity().startActivity(intent);
                }
            });
            listview.addHeaderView(headerView);
        }
        adapter = new DoneAdapter(getActivity(), null);
        adapter.setOnRefreshAdapterListener(new DoneAdapter.OnRefreshAdapterListener() {
            @Override
            public void onListViewRefresh() {
                onRefresh();
            }

            @Override
            public void onShowInput(int position) {
                showInputLayout(position);
            }
        });
        listview.setAdapter(adapter);
    }
    private void showInputLayout(final int position) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.pop_input, null);
        final EditText et = (EditText) view.findViewById(R.id.input);
        Button btn_send = (Button) view.findViewById(R.id.send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et.getText().toString().trim())) {
                    RequestParams params = new RequestParams(Preferences.JOB_PING);
                    params.addQueryStringParameter("user.userId", UserUtil.getInstance(getActivity()).getUserId());
                    params.addQueryStringParameter("submissionId", list.get(position).getSubmissionId() + "");
                    params.addQueryStringParameter("content", et.getText().toString().trim());

                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Gson gson = new Gson();
                            java.lang.reflect.Type type = new TypeToken<Bean>() {
                            }.getType();
                            Bean bean = gson.fromJson(s, type);

                            if (bean.getCode().equals(MyPreferences.SUCCESS)) {
                                onRefresh();
                                pop.dismiss();
                            } else if (bean.getCode().equals(MyPreferences.FAIL)) {
                                ActivityUtils.showToast(context, bean.getCodeInfo());
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
        });
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop.setBackgroundDrawable(null);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        pop.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
        et.requestFocus();
    }

    @Override
    public void onRefresh() {
        loadData();
    }

}
