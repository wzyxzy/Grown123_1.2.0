package com.pndoo.grown123_new.task;

import java.util.Observable;
import java.util.Observer;

import android.util.Log;

public class TaskManager extends Observable {
	private static final String TAG = "TaskManager";

	public static final Integer CANCEL_ALL = 1;

	public void cancelAll() {
		Log.d(TAG, "All task Cancelled.");
		setChanged();
		notifyObservers(CANCEL_ALL);
	}

	public void addTask(Observer task) {
		super.addObserver(task);
	}

	public BaseTask createNewTask(TaskListener taskListener) {
		BaseTask task = new BaseTask();
		task.setListener(taskListener);
		addTask(task);
		return task;
	}
	
	public BaseTask createNewTask(TaskListener taskListener,boolean isCancel) {
		BaseTask task = new BaseTask();
		task.setCancelable(isCancel);
		task.setListener(taskListener);
		addTask(task);
		return task;
	}
}