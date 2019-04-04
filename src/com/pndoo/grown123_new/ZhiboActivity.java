package com.pndoo.grown123_new;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.adapter.DingYueAdapter;
import com.pndoo.grown123_new.adapter.WangqiAdapter;
import com.pndoo.grown123_new.adapter.ZhiBoYuGaoAdapter;
import com.pndoo.grown123_new.adapter.ZhiboAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.DingyueBean;
import com.pndoo.grown123_new.dto.base.DingyueDataBean;
import com.pndoo.grown123_new.dto.base.WangQiDataBean;
import com.pndoo.grown123_new.dto.base.WangQiListBean;
import com.pndoo.grown123_new.dto.base.YuGaoDataBean;
import com.pndoo.grown123_new.dto.base.YuGaoListBean;
import com.pndoo.grown123_new.dto.base.ZhiboBean;
import com.pndoo.grown123_new.dto.base.ZhiboInfoBean;
import com.pndoo.grown123_new.dto.base.ZhiboListBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.rest.OnRefreshAdapterListener;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.umeng.analytics.MobclickAgent;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ZhiboActivity extends BaseActivity implements OnClickListener, OnRefreshListener/*
																							 * ,
																							 * TIMCallBack
																							 */{
    private LinearLayout mBtn_zhibo, mBtn_yugao, mLayout_zhibo, mLayout_kong, mBtn_dingyue, mBtn_wangqi,header_layout;
    private ImageView mImg_zhibo, mImg_yugao, mImg_kong, mImg_dingyue, mImg_wangqi;
    private ZhiboAdapter adapter;
    private ZhiBoYuGaoAdapter adapter2;
    private DingYueAdapter adapter3;
    private WangqiAdapter adapter4;
    private final String TAG = getClass().getSimpleName();
    private List<ZhiboListBean> list_zhibo = new ArrayList<>();
    private List<WangQiDataBean> list_wangqi = new ArrayList<>();
    private List<YuGaoDataBean> list_yugao = new ArrayList<>();
    private List<DingyueDataBean> list_dingyue = new ArrayList<>();
    private final int isZhibo = 0;
    private final int isYugao = 1;
    private final int isDingyue = 2;
    private final int isWangqi = 3;
    private int status = isZhibo;
    // private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView listview;
    private ZhiboInfoBean bean;
    private String groupId;
    private boolean isShunxu = false;
    private TextView shunxu,daoxu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zhibo);

        initView();
        initData();
        requestData();
    }

    private void initData() {
        // TODO Auto-generated method stub

        // InitBusiness.start(getApplicationContext(), 0);
        // TlsBusiness.init(getApplicationContext());

        sanjiaoshow(status);
        adapter = new ZhiboAdapter(this, list_zhibo);
        adapter2 = new ZhiBoYuGaoAdapter(this, list_yugao);
        adapter3 = new DingYueAdapter(this, list_dingyue, new OnRefreshAdapterListener() {
            @Override
            public void onRefreshAdapter() {
                requestData();
            }
        });
        adapter4 = new WangqiAdapter(this, list_wangqi);
    }

    private void initView() {
        // TODO Auto-generated method stub

        mBtn_zhibo = (LinearLayout) findViewById(R.id.btn_zhibo);
        mBtn_yugao = (LinearLayout) findViewById(R.id.btn_yugao);
        mBtn_dingyue = (LinearLayout) findViewById(R.id.btn_me);
        mBtn_wangqi = (LinearLayout) findViewById(R.id.btn_wangqi);

        mBtn_zhibo.setOnClickListener(this);
        mBtn_yugao.setOnClickListener(this);
        mBtn_dingyue.setOnClickListener(this);
        mBtn_wangqi.setOnClickListener(this);

        mImg_zhibo = (ImageView) findViewById(R.id.imageView1);
        mImg_yugao = (ImageView) findViewById(R.id.imageView2);
        mImg_dingyue = (ImageView) findViewById(R.id.imageView4);
        mImg_wangqi = (ImageView) findViewById(R.id.imageView5);

        mLayout_kong = (LinearLayout) findViewById(R.id.kong_layout);
        mLayout_zhibo = (LinearLayout) findViewById(R.id.zhibo_layout);
        mImg_kong = (ImageView) findViewById(R.id.imageView3);

        header_layout = (LinearLayout) findViewById(R.id.header_layout);
        shunxu = (TextView) findViewById(R.id.shunxu);
        daoxu = (TextView) findViewById(R.id.daoxu);
        shunxu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(isShunxu)
                    return;
                shunxu(true);
            }
        });
        daoxu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(!isShunxu)
                    return;
                shunxu(false);
            }
        });
        // mSwipeRefreshLayout = (SwipeRefreshLayout)
        // findViewById(R.id.swipeRefreshLayout);
        // mSwipeRefreshLayout1 = (SwipeRefreshLayout)
        // findViewById(R.id.swipeRefreshLayout1);
        // mSwipeRefreshLayout.setOnRefreshListener(this);
        // mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
        // android.R.color.holo_red_light, android.R.color.holo_orange_light,
        // android.R.color.holo_green_light);
        // mSwipeRefreshLayout1.setOnRefreshListener(this);
        // mSwipeRefreshLayout1.setColorSchemeResources(android.R.color.holo_blue_light,
        // android.R.color.holo_red_light, android.R.color.holo_orange_light,
        // android.R.color.holo_green_light);

        listview = (ListView) findViewById(R.id.listView1);
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long idl) {
                // TODO Auto-generated method stub
                if (status == isZhibo) {
                    DialogUtils.showMyDialog(ZhiboActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "加载中...", null);
                    final String id = UserUtil.getInstance(ZhiboActivity.this).getUserId();

                    org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.GET_ZHIBO_INFO);
                    params.addQueryStringParameter("courseId", list_zhibo.get(position).getCourseId()+"");
                    params.addQueryStringParameter("userId", id);

                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Gson gson = new Gson();
                            java.lang.reflect.Type type = new TypeToken<ZhiboInfoBean>() {
                            }.getType();
                            bean = gson.fromJson(s, type);
                            if (bean != null && bean.getData() != null && bean.getCode().equals("SUCCESS")) {
                                // LoginBusiness.loginIm(name,
                                // bean.getData().getSig(), ZhiboActivity.this);

                                Intent intent = new Intent(ZhiboActivity.this, PlayActivity.class);
                                intent.putExtra("bean", bean);
                                intent.putExtra("groudId", list_zhibo.get(position).getGroupId());
                                intent.putExtra("expert", list_zhibo.get(position).getExpert());
                                startActivity(intent);
                            } else {
                                mHandler.sendMessage(mHandler.obtainMessage(CMD_TOAST, bean.getCodeInfo()));
                            }
                            DialogUtils.dismissMyDialog();
                        }

                        @Override
                        public void onError(Throwable throwable, boolean b) {
                            mHandler.sendMessage(mHandler.obtainMessage(CMD_TOAST, getString(R.string.zhiboshibai)));
                        }

                        @Override
                        public void onCancelled(CancelledException e) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });


                } else if (status == isDingyue) {
                    // Intent intent = new Intent(ZhiboActivity.this,
                    // DingyueActivity.class);
                    // intent.putExtra("bean", list_dingyue.get(position));
                    // startActivity(intent);
                }
            }
        });

    }

    private void shunxu(boolean isshunxu){
        isShunxu = isshunxu;
        shunxu.setTextColor(!isshunxu?getResources().getColor(R.color.text_black2):getResources().getColor(R.color.background));
        daoxu.setTextColor(isshunxu?getResources().getColor(R.color.text_black2):getResources().getColor(R.color.background));
        adapter4.reverse();
        adapter4.notifyDataSetChanged();
    }

    private void sanjiaoshow(int status) {
        mImg_zhibo.setVisibility(status == isZhibo ? View.VISIBLE : View.INVISIBLE);
        mImg_yugao.setVisibility(status == isYugao ? View.VISIBLE : View.INVISIBLE);
        mImg_dingyue.setVisibility(status == isDingyue ? View.VISIBLE : View.INVISIBLE);
        mImg_wangqi.setVisibility(status == isWangqi ? View.VISIBLE : View.INVISIBLE);

        header_layout.setVisibility(status == isWangqi ? View.VISIBLE : View.GONE);
    }

    private void requestData() {
        if (status == isZhibo) {
            org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.GET_ZHIBO_LIST);
            params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<ZhiboBean>() {
                    }.getType();
                    ZhiboBean bean = gson.fromJson(s, type);
                    if (bean != null) {
                        mHandler.sendMessage(mHandler.obtainMessage(CMD_REFRESH, bean));
                    } else {
                        mHandler.sendEmptyMessageDelayed(CMD_REQUEST, 1000);
                    }
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    refreshList();
                    mHandler.sendMessage(mHandler.obtainMessage(CMD_TOAST, "加载失败"));
                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });

        } else if (status == isYugao) {
            org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.GET_YUGAO_LIST);
            params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<YuGaoListBean>() {
                    }.getType();
                    YuGaoListBean bean = gson.fromJson(s, type);
                    Log.d(TAG, "--------bean=" + bean.getCodeInfo());
                    if (bean != null) {
                        mHandler.sendMessage(mHandler.obtainMessage(CMD_REFRESH_YUGAO, bean));
                    } else {
                        mHandler.sendEmptyMessageDelayed(CMD_REQUEST, 1000);
                    }
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    refreshList();
                    mHandler.sendMessage(mHandler.obtainMessage(CMD_TOAST, "加载失败"));
                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
        } else if (status == isWangqi) {
            org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.GET_VIDEO_LIST);
            params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<WangQiListBean>() {
                    }.getType();
                    WangQiListBean bean = gson.fromJson(s, type);
                    if (bean != null) {
                        mHandler.sendMessage(mHandler.obtainMessage(CMD_REFRESH_WANGQI, bean));
                    } else {
                        mHandler.sendEmptyMessageDelayed(CMD_REQUEST, 1000);
                    }
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    refreshList();
                    mHandler.sendMessage(mHandler.obtainMessage(CMD_TOAST, "加载失败"));
                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });

        } else {

            org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.GET_DINGYUE_LIST);
            params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<DingyueBean>() {
                    }.getType();
                    DingyueBean bean = gson.fromJson(s, type);
                    if (bean != null && bean.getCode().equals("SUCCESS")) {
                        mHandler.sendMessage(mHandler.obtainMessage(CMD_REFRESH_DINGYUE, bean));
                    } else {
                        mHandler.sendEmptyMessageDelayed(CMD_REQUEST, 1000);
                    }
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    refreshList();
                    mHandler.sendMessage(mHandler.obtainMessage(CMD_TOAST, "加载失败"));
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

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//		requestData();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    private final int CMD_REFRESH = 0x01;
    private final int CMD_REQUEST = 0x02;
    private final int CMD_REFRESH_YUGAO = 0x03;
    private final int CMD_TOAST = 0x04;
    private final int CMD_REFRESH_DINGYUE = 0x05;
    private final int CMD_REFRESH_WANGQI = 0x06;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CMD_REFRESH:
                    list_zhibo.clear();
                    ZhiboBean bean = (ZhiboBean) msg.obj;
                    List<ZhiboListBean> list = bean.getData();
                    if (list == null || list.size() == 0) {
                        showListView(false);
                    } else {
                        showListView(true);
                        list_zhibo.addAll(list);
                        // for (int i = 0; i < list.size(); i++) {
                        // /**
                        // * 值 状态 0 无输入流 1 直播中 2 异常 3 关闭
                        // */
                        // if (list.get(i).getChannel_status().equals("1")) {
                        // list_zhibo.add(list.get(i));
                        // }
                        // }
                    }
                    refreshList();
                    // if (mSwipeRefreshLayout.isRefreshing())
                    // mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case CMD_REFRESH_YUGAO:
                    list_yugao.clear();
                    YuGaoListBean bean1 = (YuGaoListBean) msg.obj;
                    List<YuGaoDataBean> list1 = bean1.getData();
                    List<YuGaoDataBean> list_show = new LinkedList<>();
                    if (list1 == null || list1.size() == 0) {
                        showListView(false);
                    } else {
                        for (int i = 0; i < list1.size(); i++) {
                            if (!list1.get(i).isPay())
                                list_show.add(list1.get(i));
                        }
                    }
                    if (list_show == null || list_show.size() == 0) {
                        showListView(false);
                    } else {
                        showListView(true);
                        list_yugao.addAll(list_show);
                    }
                    refreshList();
                    break;
                case CMD_REFRESH_WANGQI:
                    list_wangqi.clear();
                    WangQiListBean bean3 = (WangQiListBean) msg.obj;
                    List<WangQiDataBean> list3 = bean3.getData();
                    if (list3 == null || list3.size() == 0) {
                        showListView(false);
                    } else {
                        showListView(true);
                        list_wangqi.addAll(list3);
                        // for (int i = 0; i < list.size(); i++) {
                        // /**
                        // * 值 状态 0 无输入流 1 直播中 2 异常 3 关闭
                        // */
                        // if (list.get(i).getChannel_status().equals("1")) {
                        // list_zhibo.add(list.get(i));
                        // }
                        // }
                    }
                    refreshList();
                    break;
                case CMD_REFRESH_DINGYUE:
                    list_dingyue.clear();
                    DingyueBean bean2 = (DingyueBean) msg.obj;
                    List<DingyueDataBean> list2 = bean2.getData();
                    if (list2 == null || list2.size() == 0) {
                        showListView(false);
                    } else {
                        showListView(true);
                        list_dingyue.addAll(list2);
                    }
                    refreshList();
                    break;
                case CMD_REQUEST:
                    // requestData();
                    break;
                case CMD_TOAST:
                    String s = (String) msg.obj;
                    if (s.contains("没有订阅")) {
                        DialogUtils.showMyDialog(ZhiboActivity.this, MyPreferences.SHOW_ERROR_DIALOG, "失败", s, new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                gotoYugao();
                            }
                        });
                    } else {
                        ActivityUtils.showToastForFail(ZhiboActivity.this, s);
                        DialogUtils.dismissMyDialog();
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };

    private void refreshList() {
        if (status == isZhibo) {
            if (list_zhibo.size() == 0)
                showListView(false);
            else {
                showListView(true);
                adapter.setList(list_zhibo);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        } else if (status == isYugao) {
            if (list_yugao.size() == 0)
                showListView(false);
            else {
                showListView(true);
                adapter2.setList(list_yugao);
                listview.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();
            }
        } else if (status == isWangqi) {
            if (list_wangqi.size() == 0)
                showListView(false);
            else {
                showListView(true);
                adapter4.setList(list_wangqi);
                listview.setAdapter(adapter4);
//				adapter4.notifyDataSetChanged();
                shunxu(false);
            }
        } else {
            if (list_dingyue.size() == 0)
                showListView(false);
            else {
                showListView(true);
                adapter3.setList(list_dingyue);
                listview.setAdapter(adapter3);
                adapter3.notifyDataSetChanged();
            }
        }
        DialogUtils.dismissMyDialog();
    }
    private void showListView(boolean isshowlist) {
        mLayout_kong.setVisibility(!isshowlist ? View.VISIBLE : View.GONE);
        mLayout_zhibo.setVisibility(isshowlist ? View.VISIBLE : View.GONE);
        if (!isshowlist) {
            if (status == isZhibo)
                mImg_kong.setBackgroundResource(R.drawable.sad_zhibo);
            else if (status == isYugao) {
                mImg_kong.setBackgroundResource(R.drawable.sad_yugao);
            } else if (status == isWangqi) {
                mImg_kong.setBackgroundResource(R.drawable.sad_yugao);
            } else {
                mImg_kong.setBackgroundResource(R.drawable.sad_yuding);
            }
        }
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        // requestData();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_zhibo:
                if (status == isZhibo)
                    return;
                DialogUtils.showMyDialog(ZhiboActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在努力加载...", null);
                status = isZhibo;
                sanjiaoshow(isZhibo);
                requestData();
                break;
            case R.id.btn_yugao:
                gotoYugao();
                break;
            case R.id.btn_me:
                if (status == isDingyue)
                    return;
                DialogUtils.showMyDialog(ZhiboActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在努力加载...", null);
                status = isDingyue;
                sanjiaoshow(isDingyue);
                requestData();
                break;
            case R.id.btn_wangqi:
                if (status == isWangqi)
                    return;
                DialogUtils.showMyDialog(ZhiboActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在努力加载...", null);
                status = isWangqi;
                sanjiaoshow(isWangqi);
                requestData();
                break;
            default:
                break;
        }
    }

    private void gotoYugao() {
        if (status == isYugao)
            return;
        DialogUtils.showMyDialog(ZhiboActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在努力加载...", null);
        status = isYugao;
        sanjiaoshow(isYugao);
        requestData();
    }

    // @Override
    // public void onError(int arg0, String arg1) {
    // // TODO Auto-generated method stub
    // DialogUtils.dismissMyDialog();
    // }
    //
    // @Override
    // public void onSuccess() {
    // // TODO Auto-generated method stub
    // // 初始化消息监听
    // MessageEvent.getInstance();
    //
    // TIMGroupManager.getInstance().applyJoinGroup(groupId, "some reason", new
    // TIMCallBack() {
    // @java.lang.Override
    // public void onError(int code, String desc) {
    // // 接口返回了错误码code和错误描述desc，可用于原因
    // // 错误码code列表请参见错误码表
    // Log.e(TAG, "disconnected   code===" + code + "------desc=" + desc);
    // // 已添加
    // if (code == 10013) {
    // TIMGroupManager.getInstance().quitGroup(groupId, new TIMCallBack() {
    //
    // @Override
    // public void onSuccess() {
    // // TODO Auto-generated method stub
    // Log.i(TAG, "quit group");
    // DialogUtils.dismissMyDialog();
    // }
    //
    // @Override
    // public void onError(int arg0, String arg1) {
    // // TODO Auto-generated method stub
    // Log.e(TAG, "disconnected   code===" + arg0 + "------desc=" + arg1);
    // DialogUtils.dismissMyDialog();
    // }
    // });
    // }
    // }
    //
    // @java.lang.Override
    // public void onSuccess() {
    // Log.i(TAG, "join group");
    //
    // Intent intent = new Intent(ZhiboActivity.this, PlayActivity.class);
    // intent.putExtra("bean", bean);
    // intent.putExtra("identify", groupId);
    // intent.putExtra("type", TIMConversationType.Group);
    // startActivity(intent);
    // DialogUtils.dismissMyDialog();
    // }
    // });
    //
    // }
}
