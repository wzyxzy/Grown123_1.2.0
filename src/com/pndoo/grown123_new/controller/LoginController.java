package com.pndoo.grown123_new.controller;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import com.google.inject.Inject;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.controller.base.AbstractController;
import com.pndoo.grown123_new.dto.base.SMS;
import com.pndoo.grown123_new.dto.base.UserVO;
import com.pndoo.grown123_new.rest.IAsyncCallback;
import com.pndoo.grown123_new.rest.IRestService;
import com.pndoo.grown123_new.rest.Response;
import com.pndoo.grown123_new.rest.RestException;
import com.pndoo.grown123_new.rest.RestFault;
import com.pndoo.grown123_new.soap.LoginJson;
import com.pndoo.grown123_new.util.ObjectHelper;

import org.springframework.util.MultiValueMap;

import java.util.Map;

public class LoginController extends AbstractController<LoginJson> {

	private IRestService restService;
	private ObjectHelper objectHelper;// 同步对象，把第二个参数对象同步到第一个参数对象中
	private LoginJson loginJson = new LoginJson();
	String mErrorMsg = null;

	public LoginController() {
		super();
	}

	/**
	 * 登录
	 * 
	 * @param request
	 */
	public String login(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };

		restService.sendRequest(Preferences.LOGIN_URL, request, new IAsyncCallback() {

			public void onSuccess(Response result) {
				try {
					if (result.getData() != null) {
						Log.i("=======result==========", result.getData().toString());
						Map<String, Object> data = (Map<String, Object>) result.getData();
						String userId = (String) data.get("userId");
						String userName = (String) data.get("userName");
						String userEmail = (String) data.get("userEmail");
						int userStatus = (int)data.get("userStatus");
						String userPortraits = (String) data.get("userPortraits");
						int isHaveGroup = (int)data.get("isHaveGroup");
						Object o = (Object)(((Map<String, String>) result.getData()).get("userDetail"));
						Log.d("1111111111111111", o.toString());
						Map<String, Object> data1 = (Map<String, Object>)o;
						int sex = (int) data1.get("sex");
						String kindergarten = (String) data1.get("kindergarten");
						String realName = (String) data1.get("realName");
						String birthdayShow = (String) data1.get("birthdayShow");
						String parents = (String) data1.get("parents");
						String address1 = (String) data1.get("address1");
						String surname = (String) data1.get("surname");
						String level = (String) data1.get("level");
						int subscibed = (int) data1.get("subscibed");

						// String userPwd = (String)
						// data.get("userPwd");
						UserVO bj = new UserVO();
						bj.setUserId(userId);
						Log.i("TTT============", "phone" + userId + result.toString());
						bj.setUserName(userName);
						bj.setEmail(userEmail);
						bj.setIsHaveGroup(isHaveGroup);
						bj.setUserPortraits(userPortraits);

						bj.setAddress1(address1);
						bj.setBirthdayShow(birthdayShow);
						bj.setRealName(realName);
						bj.setKindergarten(kindergarten);
						bj.setSex(sex);
						bj.setSurname(surname);
						bj.setLevel(level);
						bj.setParents(parents);
						bj.setSubscibed(subscibed);
						bj.setUserStatus(userStatus);
						

						// objectHelper.syncObjectGraph(bj,result.getData());
						// List<BrandVO> list =
						// objectHelper.syncListObject(result.getData(),
						// BrandVO.class);

						loginJson.setUserVO(bj);
						setLoginJson(loginJson);

					} else {
						exceptionInfo[0] = "未连上服务器！";
					}
				} catch (Exception e) {

					setLoginJson(loginJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				// notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {

				setLoginJson(loginJson);
				Log.e(LoginController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 退出登录
	 * 
	 */
	public String logout(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };

		restService.sendRequest(Preferences.LOGOUT_URL, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (result != null) {
						Log.i("=======result==========", result.getCode() + "  " + result.getCodeInfo());
						loginJson.setCode(result.getCode());
						loginJson.setCodeInfo(result.getCodeInfo());
						Log.i("$$$$$$$$$============", "phone" + result.toString());
						// List<BrandVO> list =
						// objectHelper.syncListObject(result.getData(),
						// BrandVO.class);
						setLoginJson(loginJson);

					} else {

						exceptionInfo[0] = "未连上服务器！";
					}
				} catch (Exception e) {
					setLoginJson(loginJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setLoginJson(loginJson);
				Log.e(LoginController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 发送邮件获取密码/修改密码
	 * 
	 * @param request
	 */
	public String sendEmail(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };

		restService.sendRequest(Preferences.SENDEMAIL_URL, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (result != null) {
						Log.i("=======result==========", result.getCode() + "  " + result.getCodeInfo());
						loginJson.setCode(result.getCode());
						loginJson.setCodeInfo(result.getCodeInfo());
						// List<BrandVO> list =
						// objectHelper.syncListObject(result.getData(),
						// BrandVO.class);
						setLoginJson(loginJson);

					} else {

						exceptionInfo[0] = "未连上服务器！";
					}
				} catch (Exception e) {
					setLoginJson(loginJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setLoginJson(loginJson);
				Log.e(LoginController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 注册
	 * 
	 * @param request
	 */
	public String reginst(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.REGINST_URL, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (result != null) {
						Log.i("=======result==========", result.getCode() + "  " + result.getCodeInfo());
						loginJson.setCode(result.getCode());
						loginJson.setCodeInfo(result.getCodeInfo());
						// List<BrandVO> list =
						// objectHelper.syncListObject(result.getData(),
						// BrandVO.class);
						setLoginJson(loginJson);

					} else {
						exceptionInfo[0] = "未连上服务器！";
					}
				} catch (Exception e) {
					setLoginJson(loginJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setLoginJson(loginJson);
				Log.e(LoginController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 重置密码
	 * 
	 * @param request
	 */
	public String reset(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.RESET_PASSWORD, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (result != null) {
						Log.i("=======result==========", result.getCode() + "  " + result.getCodeInfo());
						loginJson.setCode(result.getCode());
						loginJson.setCodeInfo(result.getCodeInfo());
						// List<BrandVO> list =
						// objectHelper.syncListObject(result.getData(),
						// BrandVO.class);
						setLoginJson(loginJson);

					} else {
						exceptionInfo[0] = "未连上服务器！";
					}
				} catch (Exception e) {
					setLoginJson(loginJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setLoginJson(loginJson);
				Log.e(LoginController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 发送短信
	 * 
	 * @param request
	 */
	public String sendSMS(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.SEND_SMS, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (result != null) {
						Log.i("=======result==========", result.getCode() + "  " + result.getCodeInfo());
						loginJson.setCode(result.getCode());
						loginJson.setCodeInfo(result.getCodeInfo());
						SMS sms = new SMS();
						Map<String, String> data = (Map<String, String>) result.getData();
						String verifCode = data.get("checkCode");
						sms.setVerifCode(verifCode);
						loginJson.setSms(sms);
						setLoginJson(loginJson);

					} else {
						exceptionInfo[0] = "未连上服务器！";
					}
				} catch (Exception e) {
					setLoginJson(loginJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setLoginJson(loginJson);
				Log.e(LoginController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseAdapter getAdapter(Context context) {
		return null;
	}

	@Inject
	public void setRestService(IRestService restService) {
		this.restService = restService;
	}

	@Inject
	public void setObjectHelper(ObjectHelper objectHelper) {
		this.objectHelper = objectHelper;
	}

	@Inject
	public void setLoginJson(LoginJson loginJson) {
		this.loginJson = loginJson;
		this.registerModel(this.loginJson);
	}

}
