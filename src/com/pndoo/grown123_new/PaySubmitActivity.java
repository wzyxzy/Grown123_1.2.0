package com.pndoo.grown123_new;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.adapter.PaySubmitAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.OrderBean;
import com.pndoo.grown123_new.dto.base.ShopListBean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.MyPreferences;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class PaySubmitActivity extends BaseActivity{
	private Button mBtn_Submit;
	private List<ShopListBean> list;
	private ListView mListView;
	private PaySubmitAdapter mAdapter;
	private TextView tv_price;
	public static final String ORDER_PRICE = "ORDER_PRICE";
	public static final String ORDER_CODE = "ORDER_CODE";
	public static final String ORDER_URL = "ORDER_URL";
	public static final String ORDER_NAME = "ORDER_NAME";
	public static final String ORDER_BODY = "ORDER_BODY";
	private final String TAG = getClass().getSimpleName();
	
	public static final String ORDER = "ORDER";
	
	private final int CMD_ORDER = 0X01;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case CMD_ORDER:
					OrderBean bean = (OrderBean) msg.obj;
					Log.d(TAG, "bean------------=="+bean.getCodeInfo()+"============data==="+bean.getData());
//					String data1 = bean.getData();
//					Gson gson = new Gson();
//					java.lang.reflect.Type type = new TypeToken<OrderDataBean>() {}.getType();
//					OrderDataBean data = gson.fromJson(data1, type);
////					OrderDataBean data = JSON.parseObject(data1, OrderDataBean.class);
					String name = "";
					String body = "";
					for (int i = 0; i < list.size(); i++) {
						name += list.get(i).getBookName()+",";
						body += list.get(i).getBookIntro();
					}
					name = name.substring(0, name.length()-1);
					
					Intent intent = new Intent(PaySubmitActivity.this, PayChooseActivity.class);
					Bundle b = new Bundle();
					Log.d(TAG, bean.getData().toString()+"------name==="+name+"--------body==="+body);
					b.putString(ORDER_PRICE, bean.getData().getTotalPrice());
					b.putString(ORDER_CODE, bean.getData().getOrderCode());
					b.putString(ORDER_URL, bean.getData().getNotify_url());
					b.putString(ORDER_NAME, name);
					b.putString(ORDER_BODY, body);
					intent.putExtra(ORDER, b);
					startActivityForResult(intent, 99);
					break;

				default:
					break;
			}
		};
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
			case 999:
				Intent intent = new Intent(MyPreferences.MAIN_BROADCAST);
				intent.putExtra("menu", 1);
				sendBroadcast(intent);
				finish();
				break;

			default:
				break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_submit);
	
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = (ArrayList<ShopListBean>) getIntent().getSerializableExtra(PayTotalActivity.PAY_LIST);
		float price = getIntent().getFloatExtra(PayTotalActivity.PAY_PRICE, 0.00f);
		
		tv_price = (TextView) findViewById(R.id.textView1);
		tv_price.setText(String.format(getString(R.string.sumprice), price));
		
		mBtn_Submit = (Button) findViewById(R.id.btn_submit);
		mBtn_Submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submitOrder();
			}

		});
		mListView = (ListView) findViewById(R.id.listView1);
		mAdapter = new PaySubmitAdapter(PaySubmitActivity.this, list);
		mListView.setAdapter(mAdapter);
	}
	
	//提交订单获取订单号
	private void submitOrder() {
		// TODO Auto-generated method stub
		String bookIds = "";
		for (int i = 0; i < list.size(); i++) {
			bookIds += list.get(i).getBookId()+",";
		}
		bookIds = bookIds.substring(0, bookIds.length()-1);
		org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.SUBMIT_ORDER);
		params.addQueryStringParameter("bookIds", bookIds);
		params.addQueryStringParameter("userId", UserUtil.getInstance(PaySubmitActivity.this).getUserId());

		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String s) {
				Gson gson = new Gson();
				java.lang.reflect.Type type = new TypeToken<OrderBean>() {}.getType();
				OrderBean bean = gson.fromJson(s, type);

				//				OrderBean bean = JSON.parseObject(s, OrderBean.class);
				Message msg = new Message();
				msg.obj = bean;
				msg.what = CMD_ORDER;
				mHandler.sendMessage(msg);
			}

			@Override
			public void onError(Throwable throwable, boolean b) {
				ActivityUtils.showToastForFail(PaySubmitActivity.this, "订单提交失败");
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
		
	}
	
	public void btnBack(View v){
		finish();
	}
}
