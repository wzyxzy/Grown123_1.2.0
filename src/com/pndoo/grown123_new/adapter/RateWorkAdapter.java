package com.pndoo.grown123_new.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.RateBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAO on 2017-07-10.
 */

public class RateWorkAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private final String TAG = getClass().getSimpleName();
    private List<RateBean.Data.DoneUser> list = new ArrayList<>();

    public RateWorkAdapter(Context context , List<RateBean.Data.DoneUser> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img)
                // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_img)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_img)
                // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();

        this.list = list;
    }

    public List<RateBean.Data.DoneUser> getList() {
        return list;
    }

    public void setList(List<RateBean.Data.DoneUser> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null?0:list.size();
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
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_activity_rate,null);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_name = (TextView) convertView.findViewById(R.id.textView34);
            holder.iv_rate = (ImageView) convertView.findViewById(R.id.iv_pai);
            holder.iv_photo = (ImageView) convertView.findViewById(R.id.imageView22);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        RateBean.Data.DoneUser bean = list.get(position);
        holder.tv_num.setText((position+1)+"");
        holder.tv_name.setText(bean.getUserName());
        String url = Preferences.IMAGE_HTTP_LOCATION + bean.getUserPortraits();
        ImageLoader.getInstance().displayImage(url, holder.iv_photo, options);
        Log.d("bean.getJobGrade()","grade======"+bean.getJobGrade());
        switch (bean.getJobGrade()){
            case -1:
                holder.iv_rate.setBackgroundResource(R.drawable.m_zero);
                break;
            case 0:
                holder.iv_rate.setBackgroundResource(R.drawable.m_medal);
                break;
            case 1:
                holder.iv_rate.setBackgroundResource(R.drawable.m_bronze);
                break;
            case 2:
                holder.iv_rate.setBackgroundResource(R.drawable.m_silverl);
                break;
            case 3:
                holder.iv_rate.setBackgroundResource(R.drawable.m_gold);
                break;
        }

        return convertView;
    }

    private class ViewHolder{
        private TextView tv_num,tv_name;
        private ImageView iv_photo,iv_rate;
    }
}
