package com.pndoo.grown123_new.rest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pndoo.grown123_new.MyApplication;
import com.pndoo.grown123_new.condition.AbstractCondtion;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.main.IShopinApplication;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.HttpUtil;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 */
@Singleton
public class RestService implements IRestService {

	private static final String TAG = "RestService";
	private static final String TAG_FAULT = "FAIL";
	private static final String TAG_SUCCESS = "SUCCESS";
	private static final String TAG_EXCEPTION = "EXCEPTION";
	private long mActionStartTime;
	private long mActionEndTime;
	private String baseUrl = "";
	private RestException exception;

	@SuppressWarnings("unused")
	private IShopinApplication application;

	// private RestTemplate restTemplate = new RestTemplate();
	private RestTemplate restTemplate;
	private HttpUtil mHttpUtil;
	public void sendRequest(String remoteMethod, AbstractCondtion request,
			IAsyncCallback callback) {
		if (!ActivityUtils.isNetworkAvailable(MyApplication.getContext())) {
			return;
		}
		Log.i("---------TAG", "remoteMethod: [" + remoteMethod + "]");
		Log.i("---------TAG", "request: [" + request.toString() + "]");

		/**
		 * <pre>
		 * if (!Preferences.setRemoteMethod(remoteMethod)) {
		 * 	// Rest Excessive Invocation Exception
		 * 	Log.w(TAG, &quot;Rest Excessive Invocation Exception!&quot;);
		 * 	callback.onFailure(new RestExcessiveInvocationException(
		 * 			&quot;Rest Excessive Invocation Exception!&quot;, String
		 * 					.valueOf(Preferences.ERROR_LOCAL_REST_EXCESSIVE_INVOCATION)));
		 * 	return;
		 * }
		 * </pre>
		 */
		// mHttpUtil=new HttpUtil();
		try {
			String url = baseUrl + remoteMethod;
			Log.i("TAG", "url: [" + url + "]");
			HttpHeaders requestHeaders = new HttpHeaders();

			requestHeaders.setContentType(MediaType.APPLICATION_JSON);

			@SuppressWarnings({"rawtypes", "unchecked"})
			HttpEntity requestEntity = new HttpEntity(request, requestHeaders);

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setReadTimeout(Preferences.getNetworkReadTimeout());
			restTemplate = new RestTemplate(requestFactory);
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.excludeFieldsWithoutExposeAnnotation();
			Gson gson = gsonBuilder.create();
			String str = gson.toJson(request);
			Log.i("AbstractCondtion", "[" + str + "]");
			mActionStartTime = System.currentTimeMillis();
			ResponseEntity<Response> responseData = restTemplate.exchange(url,
					HttpMethod.POST, requestEntity, Response.class);
			mActionEndTime = System.currentTimeMillis();
			Log.i("@@@",
					"cost time in milliseconds:"
							+ String.valueOf(mActionEndTime - mActionStartTime));
			Response response = responseData.getBody();

			String tag = response.getCode();
			if (tag.equals(TAG_SUCCESS)) {
				callback.onSuccess(response);
			} else if (tag.equals(TAG_EXCEPTION) || tag.equals(TAG_FAULT)) {
				// 失败
				Log.e(TAG, "Code: [" + response.getCode() + "], CodeInfo: ["
						+ response.getCodeInfo() + "]");
				callback.onFailure(new RestFault(response.getCodeInfo(),
						response.getCode()));
			} else {
				// 未知异常
				Log.e(TAG, "Code: [" + response.getCode() + "], CodeInfo: ["
						+ response.getCodeInfo() + "]");
				callback.onFailure(new RestException("服务器端出现未知异常"));
			}
		} catch (Exception ex) {
			mActionEndTime = System.currentTimeMillis();
			Log.i("@@@",
					"cost time in milliseconds:"
							+ String.valueOf(mActionEndTime - mActionStartTime));
			if (ex.getMessage() != null)
				Log.i(TAG + "===", ex.getMessage());
			else if (ex.getLocalizedMessage() != null)
				Log.i(TAG + "===",
						ex.getLocalizedMessage());
			else if (ex.getStackTrace() != null)
				Log.i(TAG + "===ex", ex.getStackTrace()
						.toString());
			else
				Log.i(TAG + "===exception", " null null !");

			// exception = new RestException("网络连接失败，请重试");
			// callback.onFailure(exception);
			// callback.onFailure(ex);
		}

	}

	/*
	 * public void sendRequest(String remoteMethod, Request request,
	 * IAsyncCallback callback) { Log.i(RestService.class.getCanonicalName(),
	 * "Invoking remote method '" + remoteMethod + "'"); try { String url =
	 * baseUrl + remoteMethod; Log.i(RestService.class.getCanonicalName(), url);
	 * HttpHeaders requestHeaders = new HttpHeaders();
	 * 
	 * requestHeaders.setContentType(MediaType.APPLICATION_JSON); if
	 * (application.getApplicationModel() != null) {
	 * request.setName(application.
	 * getApplicationModel().getSysUserDTO().getUserName());
	 * request.setToken(application.getApplicationModel().getToken()); }
	 * HttpEntity<Request> requestEntity = new HttpEntity<Request>(request,
	 * requestHeaders);
	 * 
	 * ResponseEntity<Response> responseData = restTemplate.exchange(url,
	 * HttpMethod.POST, requestEntity, Response.class);
	 * 
	 * Response response = responseData.getBody(); String tag =
	 * response.getCode(); if (tag.equals(TAG_SUCCESS)) {
	 * callback.onSuccess(response); } else if
	 * (tag.equals(TAG_EXCEPTION)||tag.equals(TAG_FAULT)) { //失败 String
	 * faultString = response.getCodeInfo(); String faultCode =
	 * response.getCode(); throw new RestFault(faultString, faultCode); } else {
	 * //异常 throw new RestException(response.getCode(),response.getCodeInfo());
	 * } } catch (Exception ex) { ex.printStackTrace();
	 * Log.e(RestService.class.getCanonicalName(), ex.getMessage());
	 * callback.onFailure(ex); } }
	 */
	public void sendRequest(String remoteMethod,
			MultiValueMap<String, String> request, IAsyncCallback callback) {
		Log.i("---------TAG", "remoteMethod: [" + remoteMethod + "]");
		Log.i("---------TAG", "request: [" + request.toString() + "]");

		/**
		 * <pre>
		 * if (!Preferences.setRemoteMethod(remoteMethod)) {
		 * 	// Rest Excessive Invocation Exception
		 * 	Log.w(TAG, &quot;Rest Excessive Invocation Exception!&quot;);
		 * 	callback.onFailure(new RestExcessiveInvocationException(
		 * 			&quot;Rest Excessive Invocation Exception!&quot;, String
		 * 					.valueOf(Preferences.ERROR_LOCAL_REST_EXCESSIVE_INVOCATION)));
		 * 	return;
		 * }
		 * </pre>
		 */

		try {
			String url = baseUrl + remoteMethod;
			Log.i("TAG", "url: [" + url + "]");
			HttpHeaders requestHeaders = new HttpHeaders();

			requestHeaders
					.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			/*
			 * if (application.getApplicationModel() != null) {
			 * request.add("userName"
			 * ,application.getApplicationModel().getName());
			 * request.add("token"
			 * ,application.getApplicationModel().getToken()); }
			 */
			@SuppressWarnings("rawtypes")
			HttpEntity<MultiValueMap> requestEntity = new HttpEntity<MultiValueMap>(
					request, requestHeaders);
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setReadTimeout(Preferences.getNetworkReadTimeout());

			restTemplate = new RestTemplate(requestFactory);

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.excludeFieldsWithoutExposeAnnotation();
			Gson gson = gsonBuilder.create();
			String str = gson.toJson(request);

			Log.i("+", "[" + str + "]");

			mActionStartTime = System.currentTimeMillis();

			// mHttpUtil=new HttpUtil();
			Response response = restTemplate.postForObject(url, requestEntity,
					Response.class);
			// Response
			// response=restTemplate.getForObject(url,Response.class,requestEntity);
			mActionEndTime = System.currentTimeMillis();
			Log.i("@@@==bject",
					"cost time in milliseconds:"
							+ String.valueOf(mActionEndTime - mActionStartTime));
			String tag = response.getCode();
			if (tag.equals(TAG_SUCCESS)) {
				callback.onSuccess(response);
			} else if (tag.equals(TAG_EXCEPTION) || tag.equals(TAG_FAULT)) {
				// 失败
				Log.e(TAG, "Code: [" + response.getCode() + "], CodeInfo: ["
						+ response.getCodeInfo() + "]");
				callback.onFailure(new RestFault(response.getCodeInfo(),
						response.getCode()));
			} else {
				// 未知异常
				Log.e(TAG, "Code: [" + response.getCode() + "], CodeInfo: ["
						+ response.getCodeInfo() + "]");
				callback.onFailure(new RestException("服务器端出现未知异常"));
			}
		} catch (Exception ex) {
			mActionEndTime = System.currentTimeMillis();
			Log.i("@@@=rObject",
					"cost time in milliseconds:"
							+ String.valueOf(mActionEndTime - mActionStartTime));
			if (ex != null && ex.getMessage() != null)
				Log.e("@@@========", ex.getMessage());
			// exception = new RestException("网络连接失败，请重试");
			// callback.onFailure(exception);
		}
	}

	@Inject
	public void setApplication(IShopinApplication application) {
		this.application = application;
	}

	// @Inject
	// public void setFriendConnectURL(@ConnectURL String url) {
	// this.baseUrl = url;
	// }

}
