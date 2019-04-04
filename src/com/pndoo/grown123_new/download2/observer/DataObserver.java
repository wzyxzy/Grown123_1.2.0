package com.pndoo.grown123_new.download2.observer;

import com.pndoo.grown123_new.download2.entity.DocInfo;

public abstract class DataObserver {

	public void onChanged(DocInfo info) {
		// Do nothing
	}

	public void onInvalidated(DocInfo info) {
		// Do nothing
	}
}
