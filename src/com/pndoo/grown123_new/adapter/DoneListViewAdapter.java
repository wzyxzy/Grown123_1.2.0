package com.pndoo.grown123_new.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.dto.base.DoneBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAO on 2017-07-10.
 */

public class DoneListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private final String TAG = getClass().getSimpleName();
    private List<DoneBean.Data.JobComments> list = new ArrayList<>();

    public DoneListViewAdapter(Context context , List<DoneBean.Data.JobComments> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public List<DoneBean.Data.JobComments> getList() {
        return list;
    }

    public void setList(List<DoneBean.Data.JobComments> list) {
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
            convertView = inflater.inflate(R.layout.item_done_list,null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.textView34);
            holder.tv_content = (TextView) convertView.findViewById(R.id.textView35);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        DoneBean.Data.JobComments bean = list.get(position);
        holder.tv_name.setText(bean.getCommentUser()+":");
        holder.tv_content.setText(bean.getCommentContent());
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_name,tv_content;
    }
}
