package com.pndoo.grown123_new.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.ShopListBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PaySubmitAdapter extends BaseAdapter {

	private Context context;
	private List<ShopListBean> mList = new ArrayList<ShopListBean>();
	private LayoutInflater inflater;
	private final String TAG = getClass().getSimpleName();

	public PaySubmitAdapter(Context context, List<ShopListBean> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		mList = list;
	}

	public List<ShopListBean> getmList() {
		return mList;
	}

	public void setmList(List<ShopListBean> mList) {
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		 return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		inflater = LayoutInflater.from(context);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_pay, null);
			holder = new ViewHolder();
			holder.cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
			holder.cover = (ImageView) convertView.findViewById(R.id.imageView1);
			holder.tvName = (TextView) convertView.findViewById(R.id.textView1);
			holder.tvAbstract = (TextView) convertView.findViewById(R.id.textView2);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.textView3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String url = Preferences.IMAGE_HTTP_LOCATION + mList.get(position).getBookImgPath() + File.separator + mList.get(position).getBookImg();
		ImageLoader.getInstance().displayImage(url, holder.cover,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				// TODO Auto-generated method stub
				
			}
			
			@SuppressWarnings("deprecation")
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				// TODO Auto-generated method stub
				view.setBackgroundDrawable(new BitmapDrawable(loadedImage));
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub
				
			}
		});
		holder.cb.setVisibility(View.GONE);
		holder.tvName.setText(mList.get(position).getBookName());
		holder.tvAbstract.setText(mList.get(position).getBookIntro());
		holder.tvPrice.setText("¥"+mList.get(position).getBookPrice());
		return convertView;
	}

	class ViewHolder {
		CheckBox cb;
		ImageView cover;
		TextView tvName, tvAbstract, tvPrice;
	}

}
