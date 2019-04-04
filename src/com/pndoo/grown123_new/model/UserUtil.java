package com.pndoo.grown123_new.model;

import android.content.Context;

import com.pndoo.grown123_new.R;
import com.pndoo.grown123_new.dto.base.UserVO;
import com.pndoo.grown123_new.util.SPUtility;

public class UserUtil {

	private static UserVO instance;
	public static Boolean isLogin = false;

	// 当前登录用户信息
	public static synchronized UserVO getInstance(Context context) {
		instance = loadUser(context);
		return instance;
	}
	public static UserVO loadUser(Context context) {
		isLogin = SPUtility.getSPBoolean(context, R.string.islogin);
		if (isLogin) {
			UserVO userVO = new UserVO();
			userVO.setUserId(SPUtility.getSPString(context, "userid"));
			userVO.setUserName(SPUtility.getSPString(context, "username"));
			userVO.setPassword(SPUtility.getSPString(context, "password"));
			userVO.setEmail(SPUtility.getSPString(context, "email"));
			userVO.setBooksData(SPUtility.getSPString(context, "booksData"));
			
			userVO.setSurname(SPUtility.getSPString(context, "booksData"));

			userVO.setSex(SPUtility.getSPInteger(context,"userDetail.sex"));
			userVO.setKindergarten(SPUtility.getSPString(context, "userDetail.kindergarten"));
			userVO.setRealName(SPUtility.getSPString(context, "userDetail.realName"));
			userVO.setBirthdayShow(SPUtility.getSPString(context, "userDetail.birthdayShow"));
			userVO.setLevel(SPUtility.getSPString(context, "userDetail.level"));
			userVO.setParents(SPUtility.getSPString(context, "userDetail.parents"));
			userVO.setAddress1(SPUtility.getSPString(context, "userDetail.address1"));
			userVO.setSurname(SPUtility.getSPString(context, "userDetail.surname"));
			userVO.setSubscibed(SPUtility.getSPInteger(context,"userDetail.subscibed"));
			userVO.setUserStatus(SPUtility.getSPInteger(context,"userStatus"));
			userVO.setCity_code(SPUtility.getSPString(context, "citycode"));
			userVO.setIsHaveGroup(SPUtility.getSPInteger(context,"isHaveGroup"));
			userVO.setUserPortraits(SPUtility.getSPString(context, "userPortraits"));
			
			// userVO.setPublishName(SPUtility.getSPString(context,
			// "publishName"));
			// userVO.setPublishId(SPUtility.getSPString(context, "publishId"));
			return userVO;
		} else {
			return null;
		}
	}
}
