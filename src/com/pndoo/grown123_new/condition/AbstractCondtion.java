package com.pndoo.grown123_new.condition;
public abstract class AbstractCondtion {
	// --以下为必填信息
	public Integer optUserSid;
	public String userName;
	public String password;
	public String ipAddress;

	public Integer shopSid; // 门店SID
	public Integer saleCodeSid; // 销售编码SID
	public String macAddress; // mac地址
	// --业务备用
	protected String optRealName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getShopSid() {
		return shopSid;
	}

	public void setShopSid(Integer shopSid) {
		this.shopSid = shopSid;
	}

	public Integer getSaleCodeSid() {
		return saleCodeSid;
	}

	public void setSaleCodeSid(Integer saleCodeSid) {
		this.saleCodeSid = saleCodeSid;
	}

	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getOptUserSid() {
		return optUserSid;
	}
	public void setOptUserSid(Integer optUserSid) {
		this.optUserSid = optUserSid;
	}
	public String getOptRealName() {
		if (optRealName == null || "".equals(optRealName))
			this.optRealName = this.userName;
		return optRealName;
	}
	public void setOptRealName(String optRealName) {
		this.optRealName = optRealName;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	// @Override
	// public String toString() {
	// return "[optUserSid=" + optUserSid + ", userName=" + userName +
	// ", password=" + password + ", ipAddress=" + ipAddress +
	// ", shopSid="+shopSid+", saleCodeSid="+saleCodeSid+"]\r\n"
	// + "[pageSize="+this.pageSize+", currentPage="+this.currentPage+"]\r\n"
	// + "["+ BeanUtil.Obj2UrlParams(this)+"]";
	// }

}
