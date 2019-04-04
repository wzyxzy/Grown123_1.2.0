package com.pndoo.grown123_new.rest;

public interface IAsyncCallback {

	/**
	 * On successful outcome
	 * 
	 * @param result
	 *            the result object
	 */
	public void onSuccess(Response result);

	/**
	 * In case of an error
	 * 
	 * @param throwable
	 *            the exception
	 */
	public void onFailure(Throwable throwable);
}
