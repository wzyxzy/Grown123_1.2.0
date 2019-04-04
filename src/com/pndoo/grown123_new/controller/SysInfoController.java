package com.pndoo.grown123_new.controller;

import android.content.Context;
import android.widget.BaseAdapter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pndoo.grown123_new.controller.base.AbstractController;
import com.pndoo.grown123_new.rest.IRestService;
import com.pndoo.grown123_new.soap.DateInfoJson;
import com.pndoo.grown123_new.util.ObjectHelper;

/**
 * Created by IntelliJ IDEA. User: http Date: 11-6-29 Time: 下午4:48 To change
 * this template use File | Settings | File Templates.
 */
@Singleton
public class SysInfoController extends AbstractController<DateInfoJson> {
	private IRestService restService;
	private ObjectHelper objectHelper;
	private DateInfoJson sysInfoJson;
	private String mErrorMsg;

	

	@SuppressWarnings("unchecked")
	@Override
	public <X extends BaseAdapter> X getAdapter(Context context) {
		return null; // To change body of implemented methods use File |
						// Settings | File Templates.
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
	public void setSysInfoJson(DateInfoJson sysInfoJson) {
		this.sysInfoJson = sysInfoJson;
		this.registerModel(this.sysInfoJson);
	}
}
