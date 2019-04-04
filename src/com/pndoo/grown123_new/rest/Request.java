package com.pndoo.grown123_new.rest;

/**
 * Created by IntelliJ IDEA. To change this template use File | Settings | File
 * Templates.
 */
public class Request {
	private Object tag;
	private Object data;
	private String name;
	private String token;
	private int activityID;

	public Request() {
	}

	public Request(Object tag, Object data) {
		this.tag = tag;
		this.data = data;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getActivityID() {
		return activityID;
	}

	public void setActivityID(int activityID) {
		this.activityID = activityID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
