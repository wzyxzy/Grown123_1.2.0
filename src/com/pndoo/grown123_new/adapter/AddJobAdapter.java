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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAO on 2017-07-10.
 */

public class AddJobAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private final String TAG = getClass().getSimpleName();
    private List<File> list = new ArrayList<>();

    public AddJobAdapter(Context context, List<File> list) {
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

    public List<File> getList() {
        return list;
    }

    public void setList(List<File> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 1;
        } else if (list.size() >= 9) {
            return 9;
        } else {
            return list.size() + 1;
        }
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
        if (position == 8&&list.size() == 9) {
            File bean = list.get(position);
            ImageLoader.getInstance().displayImage("file://" + bean.getAbsolutePath(), holder.iv_photo, options);
        } else if (position == getCount() - 1) {
//            holder.iv_photo.setBackgroundResource(R.drawable.upload_job_04);
            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.upload_job_04, holder.iv_photo, options);
        } else {
            File bean = list.get(position);
            ImageLoader.getInstance().displayImage("file://" + bean.getAbsolutePath(), holder.iv_photo, options);
        }

        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_photo;
    }
}
