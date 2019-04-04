package com.pndoo.grown123_new.soap;

import java.util.Observer;

import com.google.inject.Singleton;
import com.pndoo.grown123_new.dto.base.SMS;
import com.pndoo.grown123_new.dto.base.UserVO;

/**
 * 登录
 * 
 * @author
 * 
 */
@Singleton
public class LoginJson extends java.util.Observable implements Observer {

	private UserVO userVO;
	private String code;
	private String codeInfo;
	private SMS sms;
	public SMS getSms() {
		return sms;
	}

	public void setSms(SMS sms) {
		this.sms = sms;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeInfo() {
		return codeInfo;
	}

	public void setCodeInfo(String codeInfo) {
		this.codeInfo = codeInfo;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	@Override
	public void update(java.util.Observable observable, Object o) {
		// To change body of implemented methods use File | Settings | File
		// Templates.
		setChanged();
		notifyObservers();
	}

}
