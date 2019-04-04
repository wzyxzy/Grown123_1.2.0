package com.pndoo.grown123_new.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.dto.base.NoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAO on 2017-07-10.
 */

public class RateAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private final String TAG = getClass().getSimpleName();
    private List<NoBean.Data> list = new ArrayList<>();
    private String[] color = new String[]{"#f9b8d9","#feffba","#ace1fa","#fecdb9","#d1d2fc","#ccffbc"};

    public RateAdapter(Context context , List<NoBean.Data> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.item_rate,null);
            holder.tv_title = (TextView) convertView.findViewById(R.id.textView35);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        NoBean.Data bean = list.get(position);
        holder.tv_title.setText(String.format(context.getString(R.string.classname),bean.getTitle()));
        holder.layout.setBackgroundColor(Color.parseColor(color[position%6]));
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_title;
        private LinearLayout layout;
    }
}
