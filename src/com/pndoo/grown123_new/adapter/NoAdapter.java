package com.pndoo.grown123_new.adapter;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.pndoo.grown123_new.dto.base.NoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAO on 2017-07-10.
 */

public class NoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private final String TAG = getClass().getSimpleName();
    private List<NoBean.Data> list = new ArrayList<>();

    public NoAdapter(Context context , List<NoBean.Data> list) {
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

    public List<NoBean.Data> getList() {
        return list;
    }

    public void setList(List<NoBean.Data> list) {
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
            convertView = inflater.inflate(R.layout.item_fg_not,null);
            holder.tv_title = (TextView) convertView.findViewById(R.id.textView24);
            holder.tv_content = (TextView) convertView.findViewById(R.id.textView25);
            holder.tv_time = (TextView) convertView.findViewById(R.id.textView26);
            holder.iv_photo = (ImageView) convertView.findViewById(R.id.imageView13);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        NoBean.Data bean = list.get(position);
        holder.tv_title.setText(String.format(context.getString(R.string.classname),bean.getTitle()));
        holder.tv_content.setText(bean.getIntroduction());
        holder.tv_time.setText("20" + bean.getEndTime().getYear() % 100 + "年" + (bean.getEndTime().getMonth() + 1) + "月" + bean.getEndTime().getDate()+"日");
        String url = Preferences.IMAGE_HTTP_LOCATION + bean.getImagePath();
        ImageLoader.getInstance().displayImage(url, holder.iv_photo, options);
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_title,tv_content,tv_time;
        private ImageView iv_photo;
    }
}
