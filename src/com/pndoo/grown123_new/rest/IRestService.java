package com.pndoo.grown123_new.rest;

import org.springframework.util.MultiValueMap;

import com.pndoo.grown123_new.condition.AbstractCondtion;

public interface IRestService {
	public void sendRequest(String remoteMethod, AbstractCondtion request,
			IAsyncCallback callback);

	// public void sendRequest(String remoteMethod, Request request,
	// IAsyncCallback callback);
	public void sendRequest(String remoteMethod,
			MultiValueMap<String, String> request, IAsyncCallback callback);
}
