package com.pndoo.grown123_new.constants;

import android.annotation.SuppressLint;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Enumeration;

@SuppressLint("NewApi")
public class Preferences {

	// activity 常量
	public static final int ENTITY_ACTIVITY = 101;// 实体推送
	public static final int NET_ACTIVITY = 102;// 网络推送
	// 测试服务器
	// public static final String BASE_URL_HTTP =
	// "http://192.168.1.128:8080/pndoo_server";
	// 成长123测试服务器
//	public static final String BASE_URL_HTTP = "http://182.92.103.51:8080/pndoo_server";
	// 成长123服务器
	public static final String BASE_URL_HTTP = "http://101.200.74.204:8080/pndoo_server";
//	public static final String BASE_URL_HTTP = "http://101.200.179.47:8080/pndoo_server";

	public static final String UPDATE_URL = BASE_URL_HTTP + "/update.xml";
	public static final String LOGIN_URL = BASE_URL_HTTP + "/login.json?_method=login";// 登录
	public static final String LOGOUT_URL = BASE_URL_HTTP + "/login.json?_method=logout";
	public static final String SENDEMAIL_URL = BASE_URL_HTTP + "/user.json?";// 忘记密码
	public static final String REGINST_URL = BASE_URL_HTTP + "/user.json?_method=register";// 注册
	public static final String CHECKNAME = BASE_URL_HTTP + "/user.json?_method=checkUserName";// 检测用户名
	public static final String ADDBOOK_URL = BASE_URL_HTTP + "/book.json?_method=addBookToShelf";// 添加书籍
	public static final String DELETE_BOOK_URL = BASE_URL_HTTP + "/book.json?_method=deleteBookShelf";// 删除书籍
	public static final String BOOKLIST_URL = BASE_URL_HTTP + "/book.json?_method=findBookShelfList";// 书架书籍列表
	public static final String BOOK_URL = BASE_URL_HTTP + "/book.json?";// bookAction相关的接口
	public static final String A_BOOK_URL = BASE_URL_HTTP + "/book.json?_method=getBookinfoById";// 书籍同步数据信息
	// public static final String
	// A_BOOK_URL=BASE_URL_HTTP+"/book.json?_method=getBookinfo";//书籍同步数据信息
	public static final String IMAGE_HTTP_LOCATION = BASE_URL_HTTP + "/book.json?_method=getImg&imagePath=";// 图片地址
	// public static final String FILE_DOWNLOAD_MATCH_URL
	// =BASE_URL_HTTP+"/dFile?_method=getMatchImage&imageMatchId=";//下载文件
	// public static final String
	// FILE_DOWNLOAD=BASE_URL_HTTP+"/dFile?_method=getFileAttach&filePath=";//下载文件
//	public static final String FILE_DOWNLOAD_FILE_URL = BASE_URL_HTTP + "/dFile?_method=getBookinfoById&bookId=";// 下载文件
	public static final String VIP_BOOKS_LIST = BASE_URL_HTTP + "/book.json?_method=getNumberList";// 会员中心列表
	public static final String FILE_DOWNLOAD_ATTACH_URL1 = BASE_URL_HTTP + "/book.json?_method=findAllAttachOne";// 一级附件列表
	public static final String FILE_DOWNLOAD_ATTACH_URL2 = BASE_URL_HTTP + "/book.json?_method=findAllAttachTwo";// 一级附件列表
	public static final String FILE_DOWNLOAD_URL = BASE_URL_HTTP + "/dFile?_method=downLoadFile&filePath=";// 下载
	public static final String VERISON_UPDATE_URL = BASE_URL_HTTP + "/login.json?_method=checkVersion";// 更新
	public static final String RESET_PASSWORD = BASE_URL_HTTP + "/user.json?_method=resetPwd";// 重置密码
	public static final String SEND_SMS = BASE_URL_HTTP + "/message.json?_method=message";// 发送短信
	public static final String AUTHOR_COMMENT = BASE_URL_HTTP + "/comment.json?_method=communicate";// 与作者或读者沟通接口
	public static final String AUTHOR_POST_QUESTION = BASE_URL_HTTP + "/comment.json?_method=postQuestion";// 与作者沟通中的发表问题
	public static final String AUTHOR_POST_LIST = BASE_URL_HTTP + "/comment.json?_method=getMainSubContents";// 与作者，读者沟通的列表接口
	public static final String AUTHOR_ANSWER_QUESTION = BASE_URL_HTTP + "/comment.json?_method=answerQuestion";// 与作者，读者沟通的回复接口
	// 考试系统
	public static final String GET_TEA_CLA_TES_TEXTBOOK = BASE_URL_HTTP + "/classtest.json?_method=getTeaClaTesTextbook";// 获取书籍名称
	public static final String GET_TEXT_ONE_DIRS = BASE_URL_HTTP + "/classtest.json?_method=getTextBookOneDirs";// 获取一级目录
	public static final String GET_TEXT_TWO_DIRS = BASE_URL_HTTP + "/classtest.json?_method=getTextBookTwoDirs";// 获取二级目录
	public static final String GET_TWO_DIR_PAPERS = BASE_URL_HTTP + "/classtest.json?_method=getTwoDirPapers";// 获取试卷名称
	public static final String GET_PAPER_INFO = BASE_URL_HTTP + "/classtest.json?_method=getPaperInfo";// 获取试卷内容
	public static final String INSERT_PAPER_INFO = BASE_URL_HTTP + "/classtest.json?_method=insertPaperInfo";// 获取试卷内容
	public static final String GET_COURSES = BASE_URL_HTTP + "/classtest.json?_method=getCourses";// 获取课程
	public static final String ADD_COURSE = BASE_URL_HTTP + "/classtest.json?_method=addCourse";// 添加课程
	public static final String DELETE_COURSES = BASE_URL_HTTP + "/classtest.json?_method=deleteCourses";// 删除课程
	public static final String CONFIRM_SEND = BASE_URL_HTTP + "/classtest.json?_method=confirmSend";// 确认发出

	public static final String ADD_SHOP = BASE_URL_HTTP + "/cart.json?_method=addToCart";// 加入购物车
	public static final String SHOP_LIST = BASE_URL_HTTP + "/cart.json?_method=getCart";// 购物车列表
	public static final String CANCEL_SHOP = BASE_URL_HTTP + "/cart.json?_method=remove";// 删除购物车物品
	public static final String SUBMIT_ORDER = BASE_URL_HTTP + "/order.json?_method=submitOrder";// 提交订单
	public static final String SUBMIT_COURSE_ORDER = BASE_URL_HTTP + "/lcourseorder.json?_method=submitOrder";
	public static final String GET_ZHIBO_LIST = BASE_URL_HTTP + "/livingcourse.json?_method=getLivingCourses";// 获取直播列表
	public static final String GET_ZHIBO_INFO = BASE_URL_HTTP + "/livingcourse.json?_method=getChannelInfo";// 获取直播频道
	public static final String SUBMIT_ZHIBO_CONTENT = BASE_URL_HTTP + "/interation.json?_method=save";// 提交直播建议
	public static final String UPDATE_SURNAME = BASE_URL_HTTP + "/user.json?_method=updateAllUserD";// 修改昵称
	public static final String GET_CHATLIST = BASE_URL_HTTP + "/interation.json?_method=getInterations";// 获取直播交互列表
	public static final String JIESUO = BASE_URL_HTTP + "/user.json?_method=unbind";// 解锁绑定
	public static final String GET_YUGAO_LIST = BASE_URL_HTTP + "/livingcourse.json?_method=getRLCourses";// 获取预告列表
	public static final String GET_DINGYUE_LIST = BASE_URL_HTTP + "/lcourseorder.json?_method=getCourseOrderByUser";// 获取订阅列表
	public static final String CANCEL_DINGYUE = BASE_URL_HTTP + "/lcourseorder.json?_method=CancelOrder";// 取消订阅
	public static final String UPDATE_BABYINFO = BASE_URL_HTTP + "/user.json?_method=updataUserDetail";//修改宝宝信息
	public static final String ZHIBO_GOODSLIST = BASE_URL_HTTP + "/livinggoods.json?_method=getLCGoodsPubList";//商品列表
	public static final String ZHIBO_GOODS_BUY = BASE_URL_HTTP + "/livinggoodsorder.json?_method=createLGOrder";//购买商品
	public static final String ZHIBO_GOODS_SETTINGLIST = BASE_URL_HTTP + "/livinggoods.json?_method=getLCGoodsList";//讲师推送列表
	public static final String ZHIBO_GOODS_GETADDRESS = BASE_URL_HTTP + "/receiverinfo.json?_method=getReceiverInfo";//获取默认地址数据
	public static final String ZHIBO_GOODS_GETFAPIAO = BASE_URL_HTTP + "/invoice.json?_method=getInvoice";//获取默认发票数据
	public static final String ZHIBO_GOODS_SETADDRESS = BASE_URL_HTTP + "/receiverinfo.json?_method=saveReceiverInfo";//设置默认地址数据
	public static final String ZHIBO_GOODS_SETFAPIAO = BASE_URL_HTTP + "/invoice.json?_method=saveInvoice";//设置默认发票数据
	public static final String ZHIBO_GOODS_PAY = BASE_URL_HTTP + "/livinggoodsorder.json?_method=submitLGOrder";//商品支付
	public static final String ZHIBO_GOODS_SEND = BASE_URL_HTTP + "/livinggoods.json?_method=pushLGoods";//推送商品
	public static final String ZHIBO_GOODS_CANCEL = BASE_URL_HTTP + "/livinggoods.json?_method=cancellPush";//收回商品
	public static final String ZHIBO_GOODS_BUYLIST = BASE_URL_HTTP + "/livinggoodsorder.json?_method=getLGOrders";//商品购买清单
	public static final String ZHIBO_GOODS_CANCELBUYLIST = BASE_URL_HTTP + "/livinggoodsorder.json?_method=deleteLGOrder";//删除商品购买清单
	public static final String GARTEN_LIST = BASE_URL_HTTP + "/syscode.json?_method=getKGS";//幼儿园列表
	public static final String GET_SOURCE_LIST = BASE_URL_HTTP + "/livingcopy.json?_method=getAll";//资源列表
	public static final String GET_VIP_LIST = BASE_URL_HTTP + "/vipmember.json?_method=getVMs";//会员功能列表
	public static final String YANZHENG_VIP = BASE_URL_HTTP + "/vipmember.json?_method=update";//会员验证
	public static final String GET_VIDEO_LIST = BASE_URL_HTTP + "/bunchplant.json?_method=getVideoList";
	public static final String GET_VIDEO_URL = BASE_URL_HTTP + "/bunchplant.json?_method=getVideoViewById";
	public static final String GET_VIDEO_GIFT = BASE_URL_HTTP + "/appraise.json?_method=getChoosePresent";
	public static final String SUBMIT_VIDEO_FEEDBACK = BASE_URL_HTTP + "/appraise.json?_method=insertAppraise";
	public static final String LIVE_RECORD = BASE_URL_HTTP + "/livingcourse.json?_method=AddLivingCourseRecord";
	public static final String JOB_ADD_CLASS = BASE_URL_HTTP + "/group.json?_method=joinGroup";
	public static final String JOB_UNHOMEWORK = BASE_URL_HTTP + "/homework.json?_method=unfinishedHomework";
	public static final String JOB_SUBMITHOMEWORK = BASE_URL_HTTP + "/jobsubmission.json?_method=commitJobsubmission";
	public static final String JOB_ALLHOMEWORK = BASE_URL_HTTP + "/homework.json?_method=getHomeworkList";
	public static final String JOB_HOMEWORKRATE = BASE_URL_HTTP + "/jobsubmission.json?_method=getJobSubmissionGradeTop";
	public static final String JOB_ALLSUBMITWORK = BASE_URL_HTTP + "/jobsubmission.json?_method=getGroupJobSubmission";
	public static final String JOB_ALLOWNWORK = BASE_URL_HTTP + "/jobsubmission.json?_method=getOwnJobSubmission";
	public static final String JOB_SUBMITIDWORK = BASE_URL_HTTP + "/jobsubmission.json?_method=getJobSubmissionDetail";
	public static final String JOB_CLASSDETAIL = BASE_URL_HTTP + "/group.json?_method=GroupDetail";
	public static final String JOB_CREATECLASS = BASE_URL_HTTP + "/group.json?_method=creatGroup";
	public static final String JOB_JOBLIST = BASE_URL_HTTP + "/homework.json?_method=getHomeworkList";
	public static final String JOB_TEAJOBLIST = BASE_URL_HTTP + "/jobsubmission.json?_method=getGradeJobSubmissionList";
	public static final String JOB_ZAN = BASE_URL_HTTP + "/jobsubmission.json?_method=insertLikeButton";
	public static final String JOB_PING = BASE_URL_HTTP + "/jobsubmission.json?_method=insertJobComment";
	public static final String JOB_TEAGRADE = BASE_URL_HTTP + "/jobsubmission.json?_method=insertGradeToUser";
	public static final String JOB_EXITTEAM = BASE_URL_HTTP + "/group.json?_method=quitGroup";
	public static final String JOB_UPDATEGROUP = BASE_URL_HTTP + "/group.json?_method=updateGroup";
	public static final String JOB_CHANGETEA = BASE_URL_HTTP + "/group.json?_method=updateGroupAdmin";
	public static final String JOB_CHANGEPHOTO = BASE_URL_HTTP + "/user.json?_method=updataUserPortraits";


	private static final int DEFAULT_NETWORK_READ_TIMEOUT = 20 * 1000;
	// private static final int DEFAULT_NETWORK_READ_TIMEOUT = 30 * 1000;
	private static final int DEFAULT_CLIENT_WAIT_TIMEOUT = 2 * 1000;

	public static final String SET_NETWORK_READ_TIMEOUT = "NETWORK_READ_TIMEOUT";
	public static final String SET_CLIENT_WAIT_TIMEOUT = "CLIENT_WAIT_TIMEOUT";

	private static String macAdd;
	private static int sNetworkReadTimeout = DEFAULT_NETWORK_READ_TIMEOUT;
	private static int sClientWaitTimeout = DEFAULT_CLIENT_WAIT_TIMEOUT;
	private static int[] serverTimeArr = new int[6];

	public static void setNetworkReadTimeout(int value) {
		sNetworkReadTimeout = value;
	}

	public static int getNetworkReadTimeout() {
		return sNetworkReadTimeout;
	}

	public static void setClientWaitTimeout(int value) {
		sClientWaitTimeout = value;
	}

	public static int getClientWaitTimeout() {
		return sClientWaitTimeout;
	}

	public static String getMacAdd() {
		return macAdd;
	}

	public static void setMacAdd(String macAdd) {
		Preferences.macAdd = macAdd;
	}

	public static int[] getCalendarProp(String sysTimeStr) {
		int[] calendarProps = new int[6];

		String[] lStrings = new String[3];
		String[] rStrings = new String[3];
		if (null == sysTimeStr) {
			return null;
		}
		sysTimeStr = sysTimeStr.trim();
		if (sysTimeStr.isEmpty())
			return null;

		if (sysTimeStr.split(" ").length < 2)
			return null;

		lStrings = sysTimeStr.split(" ")[0].split("-");
		if (lStrings.length < 3)
			return null;

		rStrings = sysTimeStr.split(" ")[1].split(":");
		if (rStrings.length < 3)
			return null;

		for (int i = 0; i < 6; i++) {
			if (i < 3) {
				calendarProps[i] = Integer.parseInt(lStrings[i]);
			} else {
				calendarProps[i] = Integer.parseInt(rStrings[i - 3]);
			}
		}
		return calendarProps;
	}

	public static void setServerTimeArr(int[] _serverTimeArr) {
		serverTimeArr = _serverTimeArr;
	}

	public static int[] getLocalDateTime() {
		int[] result = new int[6];
		Calendar calendar = Calendar.getInstance();
		result[0] = calendar.get(Calendar.YEAR);
		result[1] = calendar.get(Calendar.MONTH) + 1;
		result[2] = calendar.get(Calendar.DAY_OF_MONTH);
		result[3] = calendar.get(Calendar.HOUR_OF_DAY);
		result[4] = calendar.get(Calendar.MINUTE);
		result[5] = calendar.get(Calendar.SECOND);
		return result;
	}

	public static int[] getServerDateTime() {
		if (serverTimeArr == null)
			return getLocalDateTime();
		else
			return serverTimeArr;
	}

	/**
	 * 获取本机IP地址
	 * 
	 * @return
	 */
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}

}