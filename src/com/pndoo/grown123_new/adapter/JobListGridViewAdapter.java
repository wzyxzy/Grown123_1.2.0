package com.pndoo.grown123_new.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.JobListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAO on 2017-07-10.
 */

public class JobListGridViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private final String TAG = getClass().getSimpleName();
    private List<JobListBean.Data.JobPictures> list = new ArrayList<>();

    public JobListGridViewAdapter(Context context, List<JobListBean.Data.JobPictures> list) {
        this.context = context;
        this.context = context;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img)
                // 设置图片下载期间显示的图片  //zhibo_default
                .showImageForEmptyUri(R.drawable.default_img)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_img)
                // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();

        this.list = list;

    }

    public List<JobListBean.Data.JobPictures> getList() {
        return list;
    }

    public void setList(List<JobListBean.Data.JobPictures> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null?0:list.size()<=4?list.size():4;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_addjob, null);
            holder.iv_photo = (ImageView) convertView.findViewById(R.id.iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ViewGroup.LayoutParams params = holder.iv_photo.getLayoutParams();
        params.width = 109;
        params.height = 109;
        holder.iv_photo.setLayoutParams(params);
        String url = Preferences.IMAGE_HTTP_LOCATION + list.get(position).getJobPicture();
        ImageLoader.getInstance().displayImage(url, holder.iv_photo, options);

        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_photo;
    }
}
