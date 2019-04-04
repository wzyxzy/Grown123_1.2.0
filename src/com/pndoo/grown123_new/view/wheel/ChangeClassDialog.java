package com.pndoo.grown123_new.view.wheel;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pndoo.grown123_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class ChangeClassDialog extends Dialog implements android.view.View.OnClickListener,OnWheelChangedListener{

	//省市区控件
	private WheelView wvProvince;

	private TextView btnSure;//确定按钮
	private TextView btnCancel;//取消按钮
	private TextView tv_title;

	private Context context;//上下文对象
	
	private JSONObject mJsonObj;//存放地址信息的json对象
	
	private String[] mProvinceDatas;

	private ArrayList<String> arrProvinces = new ArrayList<String>();

	private AddressTextAdapter provinceAdapter;

	//选中的省市区信息
	private String strProvince;

	//回调方法
	private OnSingleWheelListener onAddressCListener;

	//显示文字的字体大小
	private int maxsize = 24;
	private int minsize = 14;

	public ChangeClassDialog(Context context) {
		super(context, R.style.ShareDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_myinfo_changeclass);

		wvProvince = (WheelView) findViewById(R.id.wv_address_province);

		btnSure = (TextView) findViewById(R.id.btn_myinfo_sure);
		btnCancel = (TextView) findViewById(R.id.btn_myinfo_cancel);
		tv_title = (TextView) findViewById(R.id.tv_share_title);
		tv_title.setText("选择孩子班级");
		
		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		wvProvince.addChangingListener(this);

		initJsonData();
		initDatas();
		initProvinces();
		provinceAdapter = new AddressTextAdapter(context, arrProvinces, getProvinceItem(strProvince), maxsize, minsize);
		wvProvince.setVisibleItems(5);
		wvProvince.setViewAdapter(provinceAdapter);
		wvProvince.setCyclic(true);//设置内容循环
		wvProvince.setCurrentItem(getProvinceItem(strProvince));

		wvProvince.addScrollingListener(new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, provinceAdapter);
			}
		});
	}

	/**
	 * 初始化省会
	 */
	public void initProvinces() {
		int length = mProvinceDatas.length;
		for (int i = 0; i < length; i++) {
			arrProvinces.add(mProvinceDatas[i]);
		}
	}

	/**
	 * 初始化地点
	 * 
	 * @param province
	 */
	public void setAddress(String province) {
		if (province != null && province.length() > 0) {
			this.strProvince = province;
		}
	}

	/**
	 * 返回省会索引
	 */
	public int getProvinceItem(String province) {
		int size = arrProvinces.size();
		int provinceIndex = 0;
		boolean noprovince = true;
		for (int i = 0; i < size; i++) {
			if (province.equals(arrProvinces.get(i))) {
				noprovince = false;
				return provinceIndex;
			} else {
				provinceIndex++;
			}
		}
		if (noprovince) {
			strProvince = "01";
			return 0;
		}
		return provinceIndex;
	}


	//根据省来更新wheel的状态
	private void updateCities()
	{
		String currentText = (String) provinceAdapter.getItemText(wvProvince.getCurrentItem());
		strProvince = currentText;
		setTextviewSize(currentText, provinceAdapter);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == wvProvince)
		{
			//切换省份的操作
			updateCities();
		}
	}
	
////////////////////////////////////////////////////华丽的分界线	
	private void initJsonData() {
		try {
			StringBuffer sb = new StringBuffer();
			InputStream is = context.getAssets().open("class.json");
			int len = -1;
			byte[] buf = new byte[is.available()];
			while ((len = is.read(buf)) != -1) {
				sb.append(new String(buf, 0, len, "gbk"));
			}
			is.close();
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initDatas()
	{
		try
		{
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			mProvinceDatas = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
				String province = jsonP.getString("p");// 省名字

				mProvinceDatas[i] = province;
			}

		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		mJsonObj = null;
	}

	private class AddressTextAdapter extends AbstractWheelTextAdapter {
		ArrayList<String> list;

		protected AddressTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
			super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
			this.list = list;
			setItemTextResource(R.id.tempValue);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return list.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index) + "";
		}
	}
	
	public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
		ArrayList<View> arrayList = adapter.getTestViews();
		int size = arrayList.size();
		String currentText;
		for (int i = 0; i < size; i++) {
			TextView textvew = (TextView) arrayList.get(i);
			currentText = textvew.getText().toString();
			if (curriteItemText.equals(currentText)) {
				textvew.setTextSize(24);
			} else {
				textvew.setTextSize(14);
			}
		}
	}


	public void setAddresskListener(OnSingleWheelListener onAddressCListener) {
		this.onAddressCListener = onAddressCListener;
	}

	@Override
	public void onClick(View v) {
		if (v == btnSure) {
			if (onAddressCListener != null) {
				onAddressCListener.onClick(strProvince);
				dismiss();
			}
		}else if(v == btnCancel){
			dismiss();
		}
	}
}