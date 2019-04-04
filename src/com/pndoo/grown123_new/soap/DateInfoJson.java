package com.pndoo.grown123_new.soap;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by IntelliJ IDEA. User: http Date: 11-6-29 Time: 下午4:49 To change
 * this template use File | Settings | File Templates.
 */
public class DateInfoJson extends Observable implements Observer {
	private String strInfo;
	public String getStrInfo() {
		return strInfo;
	}

	public void setStrInfo(String strInfo) {
		this.strInfo = strInfo;
	}

	@Override
	public void update(Observable observable, Object o) {
		setChanged();
		notifyObservers();
	}
}
