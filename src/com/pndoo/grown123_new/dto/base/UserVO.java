package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

public class UserVO implements Serializable {
	private String userName;
	private String userPwd;
	private String userEmail;
	private String userId;
	private String booksData;
	private String isRememberPd;
	// private String publishName;//绑定的出版社
	// private String publishId;
	
	private int sex;
	private String kindergarten;
	private String realName;
	private String birthdayShow;
	private String parents;
	private String address1;
	private String surname;
	private String level;
	private int subscibed;
	private int userStatus;
	private String city_code;
	private String userPortraits;//头像地址
	private int isHaveGroup;//0没有群组


	public String getUserPortraits() {
		return userPortraits;
	}

	public void setUserPortraits(String userPortraits) {
		this.userPortraits = userPortraits;
	}

	public int getIsHaveGroup() {
		return isHaveGroup;
	}

	public void setIsHaveGroup(int isHaveGroup) {
		this.isHaveGroup = isHaveGroup;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public UserVO() {
		super();
	}
	public UserVO(String userName, String password, String email,
			String userId, String booksData, String isRememberPd) {
		super();
		this.userName = userName;
		this.userPwd = password;
		this.userEmail = email;
		this.userId = userId;
		this.booksData = booksData;
		this.isRememberPd = isRememberPd;
		// this.publishName = publishName;
		// this.publishId = publishId;
	}
	
	

	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getKindergarten() {
		return kindergarten;
	}
	public void setKindergarten(String kindergarten) {
		this.kindergarten = kindergarten;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getBirthdayShow() {
		return birthdayShow;
	}
	public void setBirthdayShow(String birthdayShow) {
		this.birthdayShow = birthdayShow;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getParents() {
		return parents;
	}
	public void setParents(String parents) {
		this.parents = parents;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getSubscibed() {
		return subscibed;
	}
	public void setSubscibed(int subscibed) {
		this.subscibed = subscibed;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	// public String getPublishId() {
	// return publishId;
	// }
	// public void setPublishId(String publishId) {
	// this.publishId = publishId;
	// }
	// public String getPublishName() {
	// return publishName;
	// }
	// public void setPublishName(String publishName) {
	// this.publishName = publishName;
	// }
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return userPwd;
	}
	public void setPassword(String password) {
		this.userPwd = password;
	}
	public String getEmail() {
		return userEmail;
	}
	public void setEmail(String email) {
		this.userEmail = email;
	}
	public String getBooksData() {
		return booksData;
	}
	public void setBooksData(String booksData) {
		this.booksData = booksData;
	}

	public String getIsRememberPd() {
		return isRememberPd;
	}

	public void setIsRememberPd(String isRememberPd) {
		this.isRememberPd = isRememberPd;
	}

}
