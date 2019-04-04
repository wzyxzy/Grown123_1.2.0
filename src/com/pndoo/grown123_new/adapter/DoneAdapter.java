package com.pndoo.grown123_new.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.JobPictureActivity;
import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.dto.base.DoneBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.view.GridViewForScrollView;
import com.pndoo.grown123_new.view.ListViewForScrollView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.pndoo.grown123_new.R.id.iv_pai;

/**
 * Created by BAO on 2017-07-10.
 */

public class DoneAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private final String TAG = getClass().getSimpleName();
    private List<DoneBean.Data> list = new ArrayList<>();
    private OnRefreshAdapterListener onRefreshAdapterListener;
    private int i = 1;

    public void setOnRefreshAdapterListener(OnRefreshAdapterListener onRefreshAdapterListener) {
        this.onRefreshAdapterListener = onRefreshAdapterListener;
    }

    public interface OnRefreshAdapterListener {
        void onListViewRefresh();

        void onShowInput(int position);
    }


    public DoneAdapter(Context context, List<DoneBean.Data> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.zhibo_default)
                // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.zhibo_default)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.zhibo_default)
                // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();

        this.list = list;
    }

    public List<DoneBean.Data> getList() {
        return list;
    }

    public void setList(List<DoneBean.Data> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_fg_done, null);
            holder.iv_photo = (ImageView) convertView.findViewById(R.id.imageView16);
            holder.tv_name = (TextView) convertView.findViewById(R.id.textView28);
            holder.tv_content = (TextView) convertView.findViewById(R.id.textView29);
            holder.tv_time = (TextView) convertView.findViewById(R.id.textView30);
            holder.gridview = (GridViewForScrollView) convertView.findViewById(R.id.gridView);
            holder.iv_pai = (ImageView) convertView.findViewById(iv_pai);
            holder.btn_zan = (LinearLayout) convertView.findViewById(R.id.dianzan);
            holder.tv_zan = (TextView) convertView.findViewById(R.id.textView32);
            holder.btn_ping = (LinearLayout) convertView.findViewById(R.id.pinglun);
            holder.tv_ping = (TextView) convertView.findViewById(R.id.textView31);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
            holder.layout_zan = (LinearLayout) convertView.findViewById(R.id.layout_zan);
            holder.devider = (LinearLayout) convertView.findViewById(R.id.devider);
            holder.tv_zan_names = (TextView) convertView.findViewById(R.id.textView33);
            holder.listview = (ListViewForScrollView) convertView.findViewById(R.id.listview);
            holder.devider_all = (LinearLayout) convertView.findViewById(R.id.devider_all);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final DoneBean.Data bean = list.get(position);
        String url = Preferences.IMAGE_HTTP_LOCATION + bean.getUserPortraits();
        ImageLoader.getInstance().displayImage(url, holder.iv_photo, options);

        holder.tv_name.setText(bean.getUserName());
        holder.tv_content.setText(bean.getJobContent());
        final String time = "20" + bean.getAddTime().getYear() % 100 + "/" + (bean.getAddTime().getMonth() + 1) + "/" + bean.getAddTime().getDate() + "日 " + bean.getAddTime().getHours() + ":" + bean.getAddTime().getMinutes();
        holder.tv_time.setText(time);
        setGridView(bean, holder.gridview);

        switch (bean.getJobGrade()) {
            case -1:
                holder.iv_pai.setBackgroundResource(R.drawable.m_zero);
                break;
            case 0:
                holder.iv_pai.setBackgroundResource(R.drawable.m_medal);
                break;
            case 1:
                holder.iv_pai.setBackgroundResource(R.drawable.m_bronze);
                break;
            case 2:
                holder.iv_pai.setBackgroundResource(R.drawable.m_silverl);
                break;
            case 3:
                holder.iv_pai.setBackgroundResource(R.drawable.m_gold);
                break;
        }

        holder.tv_zan.setText(bean.getLikeCount() + "");
        holder.tv_ping.setText(bean.getCommentCount() + "");

        holder.layout.setVisibility(View.VISIBLE);
        holder.layout.setBackgroundResource(R.drawable.release_121);
        holder.layout_zan.setVisibility(View.VISIBLE);
        holder.devider.setVisibility(View.VISIBLE);
        if (bean.getLikeCount() == 0 && bean.getCommentCount() == 0) {
            holder.layout_zan.setVisibility(View.GONE);
            holder.devider.setVisibility(View.GONE);
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.transparent));
//            holder.layout.setVisibility(View.GONE);
        } else if (bean.getLikeCount() == 0) {
            holder.layout_zan.setVisibility(View.GONE);
            holder.devider.setVisibility(View.GONE);
        } else if (bean.getCommentCount() == 0) {
            holder.devider.setVisibility(View.GONE);
        }
        if (bean.getLikeCount() != 0) {
            StringBuffer names = new StringBuffer();
            for (DoneBean.Data.LikeUsers name : bean.getLikeUsers()) {
                names.append("," + name.getLikeUser());
            }
            holder.tv_zan_names.setText(names.substring(1, names.length()).toString());
        }
        if (bean.getCommentCount() != 0) {
            DoneListViewAdapter adapter = new DoneListViewAdapter(context, bean.getJobComments());
            holder.listview.setAdapter(adapter);
        }

        holder.btn_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams(Preferences.JOB_ZAN);
                params.addQueryStringParameter("user.userId", UserUtil.getInstance(context).getUserId());
                params.addQueryStringParameter("submissionId", bean.getSubmissionId() + "");

                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<Bean>() {
                        }.getType();
                        Bean bean = gson.fromJson(s, type);

                        if (bean.getCode().equals(MyPreferences.SUCCESS)) {
                            onRefreshAdapterListener.onListViewRefresh();
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
        });

        holder.btn_ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefreshAdapterListener.onShowInput(position);
            }
        });

        if (UserUtil.getInstance(context).getUserStatus() == 1) {
            holder.iv_pai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPaiLayout(v,bean.getSubmissionId());
                }
            });
        }

        return convertView;
    }
    PopupWindow pop;
    private void showPaiLayout(View v, final int id){
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.pop_pai, null);
        pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        view.findViewById(R.id.imageButton1).setOnClickListener(new MyOnClickListener(id,1));
        view.findViewById(R.id.imageButton2).setOnClickListener(new MyOnClickListener(id,2));
        view.findViewById(R.id.imageButton3).setOnClickListener(new MyOnClickListener(id,3));
        pop.setBackgroundDrawable(null);
        pop.showAsDropDown(v);
//        pop.showAtLocation(v, Gravity.LEFT|Gravity.BOTTOM, 0, 0);
    }

    private class MyOnClickListener implements View.OnClickListener{
        int id;
        int grading;
        public MyOnClickListener(int id,int grading) {
            this.id = id;
            this.grading = grading;
        }

        @Override
        public void onClick(View v) {
            RequestParams params = new RequestParams(Preferences.JOB_TEAGRADE);
            params.addQueryStringParameter("user.userId", UserUtil.getInstance(context).getUserId());
            params.addQueryStringParameter("submissionId",id+"");
            params.addQueryStringParameter("grade", grading+"");

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<Bean>() {
                    }.getType();
                    Bean bean = gson.fromJson(s, type);

                    if (bean.getCode().equals(MyPreferences.SUCCESS)) {
                        onRefreshAdapterListener.onListViewRefresh();
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

    private void setGridView(final DoneBean.Data bean, GridViewForScrollView gridview) {
        DoneGridViewAdapter adapter = new DoneGridViewAdapter(context, bean.getJobPictures());
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String time = "20" + bean.getAddTime().getYear() % 100 + "/" + (bean.getAddTime().getMonth() + 1) + "/" + bean.getAddTime().getDate() + "日 " + bean.getAddTime().getHours() + ":" + bean.getAddTime().getMinutes();
                Intent intent = new Intent(context, JobPictureActivity.class);
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
                context.startActivity(intent);
            }
        });
    }

    private class ViewHolder {
        private TextView tv_name, tv_content, tv_time, tv_zan, tv_ping, tv_zan_names;
        private ImageView iv_photo, iv_pai;
        private LinearLayout btn_zan, btn_ping, layout, layout_zan, devider, devider_all;
        private GridViewForScrollView gridview;
        private ListViewForScrollView listview;
    }
}
