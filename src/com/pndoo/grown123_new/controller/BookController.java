package com.pndoo.grown123_new.controller;

import java.util.List;

import org.springframework.util.MultiValueMap;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.controller.base.AbstractController;
import com.pndoo.grown123_new.dto.base.AttachOne;
import com.pndoo.grown123_new.dto.base.AttachTwo;
import com.pndoo.grown123_new.dto.base.BookVO;
import com.pndoo.grown123_new.dto.base.ImageMatch;
import com.pndoo.grown123_new.dto.base.PublishVO;
import com.pndoo.grown123_new.dto.base.ShopListDataBean;
import com.pndoo.grown123_new.dto.base.Update;
import com.pndoo.grown123_new.dto.base.VipBookPage;
import com.pndoo.grown123_new.rest.IAsyncCallback;
import com.pndoo.grown123_new.rest.IRestService;
import com.pndoo.grown123_new.rest.Response;
import com.pndoo.grown123_new.rest.RestException;
import com.pndoo.grown123_new.rest.RestFault;
import com.pndoo.grown123_new.soap.BookJson;
import com.pndoo.grown123_new.util.ObjectHelper;

public class BookController extends AbstractController<BookJson> {

	private IRestService restService;
	private ObjectHelper objectHelper;// 同步对象，把第二个参数对象同步到第一个参数对象中
	private BookJson bookJson = new BookJson();
	String mErrorMsg = null;

	public BookController() {
		super();
	}

	/**
	 * 添加书籍
	 * 
	 * @param request
	 */
	public String findBook(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.ADDBOOK_URL, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
						bookJson.setNotice(result.getCodeInfo().toString());
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 删除书籍
	 * 
	 * @param request
	 */
	public String deleteBook(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.DELETE_BOOK_URL, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 书架界面书籍
	 * 
	 * @param request
	 */
	public String findBooks(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.BOOKLIST_URL, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
						if (result.getData() != null) {
							Log.d("BOOKLIST_URL", result.getData().toString());
							List<BookVO> bookVOs = objectHelper.syncListObject(result.getData(), BookVO.class);
							Log.i("BookControl", "result.getData()=" + result.getData().toString());
							Log.i("BookControl", "bookVOs=" + bookVOs.toString());
							bookJson.setBookVOs(bookVOs);
							bookJson.setBooksData(result.getData().toString());
							setBookJson(bookJson);
						}
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 书籍同步界面
	 * 
	 * @param request
	 */
	public String getBookIntroduction(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.A_BOOK_URL, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
						if (result.getData() != null) {
							BookVO bookVO = new BookVO();
							Log.i("BookController", result.getData().toString());
							objectHelper.syncObjectGraph(bookVO, result.getData());

							Log.i("BookController", bookVO.toString());
							bookJson.setBookVO(bookVO);
							setBookJson(bookJson);
						}
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}
	
	/**
	 * 加入购物车
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String addShop(MultiValueMap<String, String> request) throws RestFault, RestException {
		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.ADD_SHOP, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
//					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
//				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}
	
	/**
	 * 购物车列表
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String ShopList(MultiValueMap<String, String> request) throws RestFault, RestException {
		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.SHOP_LIST, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
						if (result.getData() != null) {
//							Log.i("BookController", result.getData().toString());
//							String s = {"code":"SUCCESS","codeInfo":"操作成功!",
//									"data":{"cart":
//									{"addTime":{"date":25,"day":4,"hours":13,"minutes":39,"month":1,"seconds":44,"time":1456378784827,"timezoneOffset":-480,"year":116},
//								"cartCode":"4801184710","id":8,"items":[],
//								"updateTime":{"date":25,"day":4,"hours":13,"minutes":39,"month":1,"seconds":44,"time":1456378784827,"timezoneOffset":-480,"year":116},
//								"userId":0}}};
							String s = result.getData().toString().replace("=", ":");
							Log.d("aaaaaaaaaaaaa",s);
							
							Gson gson = new Gson();
							java.lang.reflect.Type type = new TypeToken<ShopListDataBean>() {}.getType();
							ShopListDataBean bean = gson.fromJson(s, type);
							
//							ShopListDataBean bean = JSON.parseObject(s, ShopListDataBean.class);
							Log.d("aaaaaaaaaaaaa", bean.getCart().getItems().size()+"--------------");
							
							bookJson.setShoplist(bean.getCart().getItems());
							setBookJson(bookJson);
						}
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}
	
	/**
	 * 删除购物车
	 * @param request
	 * @return
	 * @throws RestFault
	 * @throws RestException
	 */
	public String cancelShop(MultiValueMap<String, String> request) throws RestFault, RestException {
		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.CANCEL_SHOP, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 请求一级附件
	 * 
	 * @param request
	 */
	public String getAttachOne(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.FILE_DOWNLOAD_ATTACH_URL1, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
						if (result.getData() != null) {
							Log.i("BookController", result.getData().toString());
							List<AttachOne> attachOnes = objectHelper.syncListObject(result.getData(), AttachOne.class);
							Log.i("AttachOne", "AttachOne=" + attachOnes.toString());
							bookJson.setAttachOnes(attachOnes);
							setBookJson(bookJson);

						}
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 会员中心界面
	 * 
	 * @param request
	 */
	public String getBookForVip(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.VIP_BOOKS_LIST, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
						if (result.getData() != null) {
							VipBookPage vipBookPage = new VipBookPage();
							objectHelper.syncObjectGraph(vipBookPage, result.getData());
							bookJson.setVipBookPage(vipBookPage);
							setBookJson(bookJson);
						}
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				bookJson.setCodeInfo(throwable.getMessage());
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 书籍附件2
	 * 
	 * @param request
	 */
	public String getBookAttachTwo(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.FILE_DOWNLOAD_ATTACH_URL2, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
						if (result.getData() != null) {
							Log.i("AttachTwo", result.getData().toString());
							List<AttachTwo> attachTwos = objectHelper.syncListObject(result.getData(), AttachTwo.class);
							Log.i("AttachTwo", attachTwos.toString());
							bookJson.setAttachTwos(attachTwos);
							setBookJson(bookJson);
						}
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 书籍识别文件
	 * 
	 * @param request
	 */
	public String getBookImageMatch(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.BOOK_URL, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
						if (result.getData() != null) {
							List<ImageMatch> im = objectHelper.syncListObject(result.getData(), ImageMatch.class);
							bookJson.setImageMatchs(im);
							setBookJson(bookJson);
						}
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileErrorMsg);
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

	/**
	 * 数字社区列表
	 * 
	 * @param request
	 */
	public String getPublishes(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.BOOK_URL, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
						if (result.getData() != null) {
							Log.i("other", result.getData().toString());
							List<PublishVO> publishVOs = objectHelper.syncListObject(result.getData(), PublishVO.class);
							bookJson.setPublishVOs(publishVOs);
							setBookJson(bookJson);
						}
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
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
	public void setBookJson(BookJson bookJson) {
		this.bookJson = bookJson;
		this.registerModel(this.bookJson);
	}

	/**
	 * 更新请求接口
	 * 
	 * @param request
	 */
	public String getUpdate(MultiValueMap<String, String> request) throws RestFault, RestException {

		final String[] exceptionInfo = { null };
		restService.sendRequest(Preferences.VERISON_UPDATE_URL, request, new IAsyncCallback() {
			public void onSuccess(Response result) {
				try {
					if (null != result) {
						bookJson.setCode(result.getCode());
						bookJson.setCodeInfo(result.getCodeInfo());
						if (result.getData() != null) {
							Log.i("BookController", "update=" + result.getData().toString());
							Update update = new Update();
							objectHelper.syncObjectGraph(update, result.getData());
							Log.i("BookController", "update=" + update.toString());
							bookJson.setUpdate(update);
							setBookJson(bookJson);
						}
					} else {
						exceptionInfo[0] = "返回的数据为空！";
					}
				} catch (Exception e) {
					setBookJson(bookJson);
					e.printStackTrace();
					exceptionInfo[0] = e.getMessage();
				}

				// model.setLoginResult(Result.SUCCESS);
				notifyStopProgress();
				// notifyShowMessage(R.string.uiMessageEditProfileSuccessMsg);
			}

			public void onFailure(Throwable throwable) {
				setBookJson(bookJson);
				Log.e(BookController.class.getCanonicalName(), throwable.getMessage());
				notifyStopProgress();
				exceptionInfo[0] = throwable.getMessage();
			}

		});
		return exceptionInfo[0];
	}

}
