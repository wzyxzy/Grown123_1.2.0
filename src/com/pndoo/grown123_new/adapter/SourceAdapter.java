package com.pndoo.grown123_new.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.dto.base.SourceBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.List;

/**
 * Created by BAO on 2016-08-02.
 */
public class SourceAdapter extends BaseAdapter {
    private Context context;
    private List<SourceBean.Data> list;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private ViewHolder holder = null;
//    private Map<Integer,Boolean> cancelmap = new HashMap<>();

    public SourceAdapter(Context context, List<SourceBean.Data> list) {
        this.context = context;
        this.list = list;
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
    }

    public List<SourceBean.Data> getList() {
        return list;
    }

    public void setList(List<SourceBean.Data> list) {
        this.list = list;
//        cancelmap.clear();
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_mydingyue, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.imageView6);
            holder.title = (TextView) convertView.findViewById(R.id.textView14);
            holder.expert = (TextView) convertView.findViewById(R.id.textView22);
            holder.time = (TextView) convertView.findViewById(R.id.textView16);
            holder.diyueshijian = (TextView) convertView.findViewById(R.id.textView19);
            holder.addtime = (TextView) convertView.findViewById(R.id.textView20);
            holder.btn = (Button) convertView.findViewById(R.id.button);
            holder.divider = (LinearLayout) convertView.findViewById(R.id.divider);
            holder.date = (TextView) convertView.findViewById(R.id.textView18);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SourceBean.Data bean = list.get(position);
        String url = Preferences.IMAGE_HTTP_LOCATION + bean.getPhotourl();
        ImageLoader.getInstance().displayImage(url, holder.iv, options);

        holder.diyueshijian.setVisibility(View.INVISIBLE);
        holder.addtime.setVisibility(View.INVISIBLE);
        holder.btn.setText("获取资源");

        holder.title.setText(bean.getTitle());
        holder.expert.setText(bean.getLecturer());

        holder.date.setText(bean.getStartDate());
        holder.time.setText(bean.getStartTime());


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean.getAvailable() == 0) {
                    ActivityUtils.showToastForFail(context, "您没有权限");
                    return;
                }
                DialogUtils.showMyDialog(context, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在查找中...", null);
                org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.ADDBOOK_URL);
                params.addQueryStringParameter("userId", UserUtil.getInstance(context).getUserId());
                params.addQueryStringParameter("code", bean.getCode());

                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<Bean>() {
                        }.getType();
                        Bean bean = gson.fromJson(s, type);
                        if (bean != null && bean.getCode().equals("SUCCESS")) {
                            ActivityUtils.showToastForSuccess(context, bean.getCodeInfo());
                        } else {
                            ActivityUtils.showToastForFail(context, bean.getCodeInfo());
                        }
                        DialogUtils.dismissMyDialog();
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        ActivityUtils.showToastForFail(context, "添加失败");
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
        });
//        if (position == list.size() - 1)
//            holder.divider.setVisibility(View.INVISIBLE);
//        else
//            holder.divider.setVisibility(View.VISIBLE);
        return convertView;
    }

    private class ViewHolder {
        private ImageView iv;
        private TextView title, expert, time, diyueshijian, addtime, date;
        private Button btn;
        private LinearLayout divider;
    }
}
