package com.pndoo.grown123_new.task;

import org.springframework.util.MultiValueMap;

public abstract class TaskListener {
	public abstract String getName();

	public abstract void onPreExecute(BaseTask task);

	public abstract void onPostExecute(BaseTask task, String errorMsg);

	public abstract void onProgressUpdate(BaseTask task, Object param);

	public abstract void onCancelled(BaseTask task);

	public abstract String onDoInBackground(BaseTask task,
			MultiValueMap<String, String> param);
}
