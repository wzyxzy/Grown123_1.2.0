package com.pndoo.grown123_new.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.dto.base.JobListBean;
import com.pndoo.grown123_new.view.GridViewForScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAO on 2017-07-10.
 */

public class JobListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private final String TAG = getClass().getSimpleName();
    private List<JobListBean.Data> list = new ArrayList<>();


    public JobListAdapter(Context context, List<JobListBean.Data> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.list = list;
    }

    public List<JobListBean.Data> getList() {
        return list;
    }

    public void setList(List<JobListBean.Data> list) {
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
            convertView = inflater.inflate(R.layout.item_joblist, null);
            holder.tv_date = (TextView) convertView.findViewById(R.id.textView37);
            holder.tv_month = (TextView) convertView.findViewById(R.id.textView36);
            holder.tv_num = (TextView) convertView.findViewById(R.id.textView39);
            holder.tv_content = (TextView) convertView.findViewById(R.id.textView38);
            holder.gridview = (GridViewForScrollView) convertView.findViewById(R.id.gridView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JobListBean.Data bean = list.get(position);
        holder.tv_month.setText((bean.getAddTime().getMonth() + 1) + "月");
        holder.tv_date.setText(bean.getAddTime().getDate() + "");
        holder.tv_content.setText(bean.getJobContent());
        holder.tv_num.setText("共" + bean.getJobPictures().size() + "张");
        setGridView(bean, holder.gridview);

        return convertView;
    }

    private void setGridView(final JobListBean.Data bean, GridViewForScrollView gridview) {
        JobListGridViewAdapter adapter = new JobListGridViewAdapter(context, bean.getJobPictures());
        gridview.setAdapter(adapter);
        gridview.setClickable(false);
    }

    private class ViewHolder {
        private TextView tv_date, tv_month, tv_content, tv_num;
        private GridViewForScrollView gridview;
    }
}
