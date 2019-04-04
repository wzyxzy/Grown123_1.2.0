package com.pndoo.grown123_new;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pndoo.grown123_new.adapter.PayTotalAdapter;
import com.pndoo.grown123_new.adapter.PayTotalAdapter.OnPriceChangeLisener;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.controller.BookController;
import com.pndoo.grown123_new.dto.base.ShopListAllBean;
import com.pndoo.grown123_new.dto.base.ShopListBean;
import com.pndoo.grown123_new.main.IoC;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.soap.BookJson;
import com.pndoo.grown123_new.task.BaseTask;
import com.pndoo.grown123_new.task.TaskListener;
import com.pndoo.grown123_new.task.TaskManager;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;

import org.springframework.util.MultiValueMap;
import org.xutils.common.Callback;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class PayTotalActivity extends BaseActivity implements OnClickListener {
	private Button mBtn_Edit, mBtn_Pay, mBtn_Cancel;
	private ListView mListView;
	private PayTotalAdapter mAdapter;
	private final String TAG = getClass().getSimpleName();
	private LinearLayout mLayout_Pay, mLayout_Cancel;
	private boolean isCancelMode = false;
	private List<ShopListBean> list;
	public static final String PAY_LIST = "PAY_LIST";
	public static final String PAY_PRICE = "PAY_PRICE";
	private TaskManager tm;
	private BookController bookController;
	private DisplayImageOptions options;
	private Context context;
	private TextView tv_price;

	private final int CMD_REFRESH = 0x01;
	private final int CMD_CANCEL = 0x02;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case CMD_REFRESH:
					mAdapter.setmList(list);
					mAdapter.notifyDataSetChanged();
					
					getPrice(mAdapter.getIsSelected());
					break;
				case CMD_CANCEL:
					for (Map.Entry<Integer, Boolean> me : mAdapter.getCancle().entrySet()) {
						int index = -1;
						Iterator<ShopListBean> sListIterator = list.iterator();
						while (sListIterator.hasNext()) {
							sListIterator.next();
							index++;
							if (index == (int) me.getKey()) {
								sListIterator.remove();
								break;
							}
						}
					}
					mAdapter.setmList(list);
					mAdapter.clearTreeMap();
					mAdapter.notifyDataSetChanged();
					break;
				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_pay);

		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		context = this;
		tm = getApplicationContext().getTaskManager();
		bookController = IoC.getInstance(BookController.class);
		// 默认一张图片
		int cover_normal = getResources().getIdentifier("cover_normal", "drawable", getPackageName());
		options = new DisplayImageOptions.Builder().showStubImage(cover_normal)
		// 设置图片下载期间显示的图片
				.showImageForEmptyUri(cover_normal)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(cover_normal)
				// 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();

		mBtn_Edit = (Button) findViewById(R.id.btn_edit);
		mBtn_Edit.setOnClickListener(this);
		mBtn_Pay = (Button) findViewById(R.id.btn_pay);
		mBtn_Pay.setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.listView1);
		list = new ArrayList<ShopListBean>();
		mAdapter = new PayTotalAdapter(this, list);
		mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mListView.setAdapter(mAdapter);
		mLayout_Cancel = (LinearLayout) findViewById(R.id.layout_cancel);
		mLayout_Pay = (LinearLayout) findViewById(R.id.layout_pay);
		mBtn_Cancel = (Button) findViewById(R.id.button2);
		mBtn_Cancel.setOnClickListener(this);

		tv_price = (TextView) findViewById(R.id.textView1);
		getShopList();
	}

	// 获取购物车列表
	private void getShopList() {
		// TODO Auto-generated method stub
		org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.SHOP_LIST);
		params.addQueryStringParameter("userId", UserUtil.getInstance(PayTotalActivity.this).getUserId());

		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String s) {
				Gson gson = new Gson();
				java.lang.reflect.Type type = new TypeToken<ShopListAllBean>() {}.getType();
				ShopListAllBean bean = gson.fromJson(s, type);

//				ShopListAllBean bean = JSON.parseObject(s, ShopListAllBean.class);
				Log.d(TAG, "ssssssssss=====" + bean.getData().getCart().getItems().size());

				list = bean.getData().getCart().getItems();
				mHandler.sendEmptyMessage(CMD_REFRESH);
			}

			@Override
			public void onError(Throwable throwable, boolean b) {
				ActivityUtils.showToastForFail(context, "加载失败,");
			}

			@Override
			public void onCancelled(CancelledException e) {

			}

			@Override
			public void onFinished() {

			}
		});


	}

	private void initListener() {
		// TODO Auto-generated method stub
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				// if(mAdapter.getIsSelected().get(position)){
				// mAdapter.getIsSelected().put(position, false);
				// }else {
				// mAdapter.getIsSelected().put(position, true);
				// }
				// mAdapter.notifyDataSetChanged();
			}
		});

		mAdapter.setOnPriceChangeLisener(new OnPriceChangeLisener() {

			@Override
			public void change(TreeMap<Integer, Boolean> selectedMap) {
				// TODO Auto-generated method stub
				getPrice(selectedMap);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btn_pay:
				List<ShopListBean> ll = new LinkedList<ShopListBean>();
				ll.addAll(list);
				for (Map.Entry<Integer, Boolean> me : mAdapter.getIsSelected().entrySet()) {
					int index = -1;
					Iterator<ShopListBean> sListIterator = ll.iterator();
					while (sListIterator.hasNext()) {
						sListIterator.next();
						index++;
						if (index == (int) me.getKey()) {
							sListIterator.remove();
							break;
						}
					}
				}
				if(ll.size() == 0){
					DialogUtils.showMyDialog(PayTotalActivity.this, MyPreferences.SHOW_CONFIRM_DIALOG, getString(R.string.tishi), getString(R.string.checkbooks), new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							DialogUtils.dismissMyDialog();
						}
					});
				}else {
					Intent intent = new Intent(PayTotalActivity.this, PaySubmitActivity.class);
					intent.putExtra(PAY_LIST, (Serializable) ll);
					intent.putExtra(PAY_PRICE, price);
					startActivity(intent);
				}
				break;
			case R.id.btn_edit:
				if (isCancelMode) {
					mBtn_Edit.setText(R.string.edit);
					isCancelMode = false;
					getPrice(mAdapter.getIsSelected());
				} else {
					isCancelMode = true;
					mBtn_Edit.setText(R.string.done);
				}
				showLayout(isCancelMode);
				break;
			case R.id.button2:
				DialogUtils.showMyDialog(PayTotalActivity.this, MyPreferences.SHOW_CONFIRM_DIALOG, getString(R.string.querenshanchu), getString(R.string.shifouqueding), new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						cancelShop( mAdapter.getCancle());//上报服务器
						DialogUtils.dismissMyDialog();
					}
				});

				break;
			default:
				break;
		}
	}

	private void showLayout(boolean b) {
		if (b) {
			mLayout_Cancel.setVisibility(View.VISIBLE);
			mLayout_Pay.setVisibility(View.GONE);
			mAdapter.setCancelMode(b);
		} else {
			mLayout_Pay.setVisibility(View.VISIBLE);
			mLayout_Cancel.setVisibility(View.GONE);
			mAdapter.setCancelMode(b);
		}
		mAdapter.notifyDataSetChanged();
	}
	
	
	private void cancelShop(TreeMap<Integer, Boolean> cancelMap){
		String bookIds = "";
		for(Entry<Integer, Boolean> me : cancelMap.entrySet()){
				bookIds += list.get(me.getKey()).getBookId()+",";
		}
		
		if(bookIds.length() == 0)
			return;
		bookIds = bookIds.substring(0, bookIds.length()-1);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("bookIds", bookIds);
		params.put("userId", UserUtil.getInstance(PayTotalActivity.this).getUserId());

		tm.createNewTask(new TaskListener() {
			@Override
			public String getName() {
				return null;
			}

			@Override
			public void onPreExecute(BaseTask task) {
			}

			@Override
			public void onPostExecute(BaseTask task, String errorMsg) {

				if (errorMsg != null) {
					ActivityUtils.showToastForFail(context, "删除失败," + errorMsg);
				} else {
					BookJson bookJson = bookController.getModel();
					if (null == bookJson) {
						ActivityUtils.showToastForFail(context, "删除失败," + "返回空数据");
						return;
					} else {
						if (!TextUtils.isEmpty(bookJson.getCode()) && bookJson.getCode().equals(MyPreferences.SUCCESS)) {
							mHandler.sendEmptyMessage(CMD_CANCEL);
							return;
						} else if (bookJson.getCode().equals(MyPreferences.FAIL)) {
							ActivityUtils.showToastForFail(context, "删除失败," + bookJson.getCodeInfo());
							return;
						}
					}
				}

			}

			@Override
			public void onProgressUpdate(BaseTask task, Object param) {
			}

			@Override
			public void onCancelled(BaseTask task) {
			}

			@Override
			public String onDoInBackground(BaseTask task, MultiValueMap<String, String> param) {
				try {
					String str = PayTotalActivity.this.bookController.cancelShop(param);
					return str;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		},false).execute(params);
	}

	private float price = 0.00f;
	// 更改总计金额
	private void getPrice(TreeMap<Integer, Boolean> selectedMap) {
		HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
		map.putAll(selectedMap);
		price = 0.00f;
		for (int i = list.size()-1; i >=0 ; i--) {
			boolean isAdd = true;
			Log.d(TAG, "selectedMap  size===="+map.size());
			for (Map.Entry<Integer, Boolean> me : map.entrySet()) {
				Log.d(TAG, "me.getKey()====" + me.getKey()+"----i==="+i);
				if (me.getKey() == i) {
					map.remove(i);
					isAdd = false;
					break;
				}
			}
			Log.d(TAG, "111111111111111111-----isAdd="+isAdd);
			if (isAdd){
				Log.d(TAG, "---------isAdd-------");
				price += list.get(i).getBookPrice();
			}
				
		}
		tv_price.setText(String.format(getString(R.string.sumprice), price));
	}

}
