package com.pndoo.grown123_new.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.ShopListBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import io.vov.vitamio.utils.Log;

public class PayTotalAdapter extends BaseAdapter {

	private Context context;
	private List<ShopListBean> mList = new ArrayList<ShopListBean>();
	private LayoutInflater inflater;
	// 用来控制选中购买，保存未选中的
	private TreeMap<Integer, Boolean> selectedMap;
	// 用来控制删除，保存删除的
	private TreeMap<Integer, Boolean> cancelMap;
	private final String TAG = getClass().getSimpleName();
	// 是否是删除模式
	private boolean isCancelMode = false;
	private OnPriceChangeLisener OnPriceChangeLisener;
	private DisplayImageOptions options;

	private Comparator<Integer> comparator = new Comparator<Integer>() {

		@Override
		public int compare(Integer lhs, Integer rhs) {
			// TODO Auto-generated method stub
			return rhs.compareTo(lhs);// 降序排列
		}
	};

	public void setOnPriceChangeLisener(OnPriceChangeLisener OnPriceChangeLisener) {
		this.OnPriceChangeLisener = OnPriceChangeLisener;
	}

	public interface OnPriceChangeLisener {
		void change(TreeMap<Integer, Boolean> selectedMap);
	}

	public PayTotalAdapter(Context context, List<ShopListBean> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		mList = list;
		selectedMap = new TreeMap<Integer, Boolean>(comparator);
		cancelMap = new TreeMap<Integer, Boolean>(comparator);
		// 默认一张图片
		int cover_normal = context.getResources().getIdentifier("cover_normal", "drawable", context.getPackageName());
		options = new DisplayImageOptions.Builder().showStubImage(cover_normal)
		// 设置图片下载期间显示的图片
				.showImageForEmptyUri(cover_normal)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(cover_normal)
				// 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();
	}

	public TreeMap<Integer, Boolean> getIsSelected() {
		return selectedMap;
	}

	public TreeMap<Integer, Boolean> getCancle() {
		return cancelMap;
	}

	public void setCancelMap(TreeMap<Integer, Boolean> cancelMap) {
		this.cancelMap = cancelMap;
	}

	public void setIsSelected(TreeMap<Integer, Boolean> isSelected) {
		this.selectedMap = isSelected;
	}

	public boolean isCancelMode() {
		return isCancelMode;
	}

	public void setCancelMode(boolean isCancelMode) {
		this.isCancelMode = isCancelMode;
		clearMap();
	}

	public void clearMap() {
		if (isCancelMode) {
			selectedMap.clear();
		} else {
			cancelMap.clear();
		}
	}

	public void clearTreeMap() {
		cancelMap.clear();
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
		ImageLoader.getInstance().displayImage(url, holder.cover, options,new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				// TODO Auto-generated method stub
				android.util.Log.d(TAG, "fffffffffffffffff");
				// int cover_normal =
				// context.getResources().getIdentifier("cover_normal",
				// "drawable", context.getPackageName());
				// view.setBackgroundResource(cover_normal);
			}

			@SuppressWarnings("deprecation")
			@SuppressLint("NewApi")
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				// TODO Auto-generated method stub
				android.util.Log.d(TAG, "sssssssssssssssssssssss");
				view.setBackground(new BitmapDrawable(context.getResources(), loadedImage));
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub
				android.util.Log.d(TAG, "cccccccccccccccccccccc");
				// int cover_normal =
				// context.getResources().getIdentifier("cover_normal",
				// "drawable", context.getPackageName());
				// view.setBackgroundResource(cover_normal);
			}
		});
		holder.tvName.setText(mList.get(position).getBookName());
		holder.tvAbstract.setText(mList.get(position).getBookIntro());
		holder.tvPrice.setText("¥" + mList.get(position).getBookPrice());

		if (!isCancelMode) {
			holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked) {
						Log.d(TAG, "ischecked----------------------" + isChecked);
						// 取消选中的则剔除
						selectedMap.remove(position);
					} else {
						selectedMap.put(position, isChecked);
					}
					OnPriceChangeLisener.change(selectedMap);
				}
			});
			// 找到需要选中的条目
			if (selectedMap != null && selectedMap.containsKey(position)) {
				holder.cb.setChecked(selectedMap.get(position));
			} else {
				holder.cb.setChecked(true);
			}
		} else {
			holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked) {
						cancelMap.put(position, isChecked);
					} else {
						// 取消选中的则剔除
						cancelMap.remove(position);
					}
				}
			});
			// 找到需要选中的条目
			if (cancelMap != null && cancelMap.containsKey(position)) {
				holder.cb.setChecked(cancelMap.get(position));
			} else {
				holder.cb.setChecked(false);
			}
		}

		return convertView;
	}

	class ViewHolder {
		CheckBox cb;
		ImageView cover;
		TextView tvName, tvAbstract, tvPrice;
	}

}
