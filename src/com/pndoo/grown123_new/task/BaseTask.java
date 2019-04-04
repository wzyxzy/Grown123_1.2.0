package com.pndoo.grown123_new.task;

import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.springframework.util.LinkedMultiValueMap;

import com.pndoo.grown123_new.model.UserUtil;

import android.os.AsyncTask;
import android.util.Log;

public class BaseTask extends AsyncTask<Map, String, String> implements Observer {
	private static final String TAG = "BaseTask";

	private TaskListener mListener = null;
	private boolean isCancelable = true;

	public void setListener(TaskListener taskListener) {
		mListener = taskListener;
	}

	public TaskListener getListener() {
		return mListener;
	}

	public void doPublishProgress(String... values) {
		super.publishProgress(values);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();

		if (mListener != null) {
			mListener.onCancelled(this);
		}
		Log.d(TAG, mListener.getName() + " has been Cancelled.");
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		if (mListener != null) {
			mListener.onPostExecute(this, result);
		}

		/*
		 * Toast.makeText(TwitterApplication.mContext, mListener.getName() +
		 * " completed", Toast.LENGTH_SHORT);
		 */
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		if (mListener != null) {
			mListener.onPreExecute(this);
		}

	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);

		if (mListener != null) {
			if (values != null && values.length > 0) {
				mListener.onProgressUpdate(this, values[0]);
			}
		}

	}

	public void update(Observable o, Object arg) {
		if (TaskManager.CANCEL_ALL == (Integer) arg && isCancelable) {
			if (getStatus() == BaseTask.Status.RUNNING) {
				cancel(true);
			}
		}
	}

	public void setCancelable(boolean flag) {
		isCancelable = flag;
	}

	@Override
	protected String doInBackground(Map... arg0) {
		String result = null;
		if (mListener != null) {
			LinkedMultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
			Map map = arg0[0];
			if (map != null) {
				Set<Map.Entry<String, String>> set = map.entrySet();
				for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
					request.add(entry.getKey(), entry.getValue());
					// System.out.println(entry.getKey() + "--->" +
					// entry.getValue());
				}
			}
			if (UserUtil.isLogin) {
				// request.add("cartId", UserUtil.getInstance().getId()+"");
				// request.add("memberSid", UserUtil.getInstance().getId()+"");
			}
			result = mListener.onDoInBackground(this, request);
		}
		return result;
	}
}
