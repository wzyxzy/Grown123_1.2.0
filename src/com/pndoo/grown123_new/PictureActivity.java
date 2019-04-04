package com.pndoo.grown123_new;

import java.io.FileInputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.pndoo.grown123_new.util.ActivityUtils;

public class PictureActivity extends BaseActivity {
	private String filepath;
	private TextView tv_header_title;
	private FileInputStream fis;
	private final String TAG = getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.picture_player);
		ImageView picture_palyer = (ImageView) findViewById(R.id.picture_palyer);
		tv_header_title = (TextView) findViewById(R.id.tv_header_title);
		tv_header_title.setText(getIntent().getStringExtra("bookName"));
		if (getIntent().getStringExtra("filePath") != null && (!getIntent().getStringExtra("filePath").equals(""))) {
			filepath = getIntent().getStringExtra("filePath");

			try {
				fis = new FileInputStream(filepath);
				Bitmap bitmap = BitmapFactory.decodeStream(fis);
				picture_palyer.setImageBitmap(bitmap);
				new PhotoViewAttacher(picture_palyer).update();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void btnBack(View v) {
		this.finish();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "------filepath=" + filepath);
		ActivityUtils.deleteBookFormSD(filepath);
		super.onDestroy();
	}
}
