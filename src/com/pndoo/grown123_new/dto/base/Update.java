package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

public class Update implements Serializable {

	private String isUpdate;
	private String url;
	private String desc;

	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "Update [isUpdate=" + isUpdate + ", url=" + url + ", desc="
				+ desc + "]";
	}

}
