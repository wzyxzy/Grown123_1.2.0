package com.pndoo.grown123_new;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.adapter.SourceAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.SourceBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAO on 2016-09-20.
 */
public class SourceActivity extends BaseActivity {

    private TextView tv_publishName;
    private ListView listview;
    private SourceAdapter adapter;
    private List<SourceBean.Data> list = new ArrayList<>();
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source);
        initView();
        initData();
    }

    private void initData() {
        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.GET_SOURCE_LIST);
        params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<SourceBean>() {
                }.getType();
                SourceBean bean = gson.fromJson(s, type);
                if (bean != null && bean.getCode().equals("SUCCESS")) {
                    list = bean.getData();
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                } else {
                    ActivityUtils.showToastForFail(context, bean.getCodeInfo());
                }
                DialogUtils.dismissMyDialog();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ActivityUtils.showToastForFail(context, "加载失败");
                DialogUtils.dismissMyDialog();
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
        tv_publishName = (TextView) findViewById(R.id.tv_publishName);
        tv_publishName.setText("资源");
        listview = (ListView) findViewById(R.id.listView1);
        adapter = new SourceAdapter(this,null);
        listview.setAdapter(adapter);

    }
}
