package com.pndoo.grown123_new;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.view.ProgressWebview;

@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
public class BrowerActivity extends BaseActivity {
	private TextView tv_header_title;
	private String filepath;
	private ProgressWebview mWebView;
	int sum = 0;// 记录进去页面次数，为1时，退出
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.brower);
		if (getIntent().getStringExtra("filePath") != null && (!getIntent().getStringExtra("filePath").equals(""))) {
			filepath = getIntent().getStringExtra("filePath");
		}
		if (getIntent().getStringExtra("name") != null && (!getIntent().getStringExtra("name").equals(""))) {
			name = getIntent().getStringExtra("name");
		}
		initView();
	}

	public void initView() {
		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
		tv_header_title.setText(name);

		int wv_brower = getResources().getIdentifier("wv_brower", "id", getPackageName());
		mWebView = (ProgressWebview) findViewById(wv_brower);
		WebSettings ws = mWebView.getSettings();
		// ws.setPluginState(PluginState.ON);
		if (!filepath.startsWith("http")) {
			mWebView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		}

		// mWebView.setWebChromeClient(new WebChromeClient());
		// mWebView.setWebViewClient(new WebViewClient() {
		// @Override
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
		// view.loadUrl(url);
		// return false;
		// }
		// });
		ws.setSupportMultipleWindows(true);
		ws.setJavaScriptEnabled(true);
		ws.setJavaScriptCanOpenWindowsAutomatically(true);
		ws.setSaveFormData(false);
		ws.setAppCacheEnabled(true);
		ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		// 是否允许缩放
		ws.setBuiltInZoomControls(true);
		ws.setSupportZoom(true);

		mWebView.setWebViewClient(new WebViewClient() {
			// 这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				// Log.d(TAG, "url====" + url);
				// ActivityUtils.showToast(JDWebViewActivity.this,
				// webView.getUrl().equals(URL)+url);
				if (mWebView.getUrl().equals(filepath)) {
					if (sum == 1) {
						finish();
						return true;
					}
					sum++;
				}
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				handler.proceed();// 处理https
			}

			// 接收到Http请求的事件
			@Override
			public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
				// TODO Auto-generated method stub
				super.onReceivedHttpAuthRequest(view, handler, host, realm);
			}

			// 载入页面完成的事件
			public void onPageFinished(WebView view, String url) {
				DialogUtils.dismissMyDialog();
			}

			// 同样道理，我们知道一个页面载入完成，于是我们可以关闭loading条，切换程序动作。

			// 载入页面开始的事件
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// Log.d(TAG, "onPageStarted--------url====" + url);
				if (DialogUtils.dialog == null || !DialogUtils.dialog.isShowing())
					DialogUtils.showMyDialog(BrowerActivity.this, MyPreferences.SHOW_PROGRESS_DIALOG, null, "正在加载中...", null);
			}
			// 这个事件就是开始载入页面调用的，通常我们可以在这设定一个loading的页面，告诉用户程序在等待网络响应。
		});

		mWebView.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				if (url != null && url.startsWith("http://"))
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			}
		});

		mWebView.loadUrl(filepath);

	}

	public void btnBack(View v) {
		if (mWebView.canGoBack()) {
			mWebView.goBack();// 返回上一页面
			// Log.d(TAG, mWebView.getUrl());
		} else {
			// System.exit(0);//退出程序
			finish();
		}
	}

	public void BtnFinish(View view) {
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mWebView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mWebView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWebView.canGoBack()) {
				mWebView.goBack();// 返回上一页面
				// Log.d(TAG, mWebView.getUrl());
			} else {
				// System.exit(0);//退出程序
				finish();
			}
		}
		return true;
	}
}
