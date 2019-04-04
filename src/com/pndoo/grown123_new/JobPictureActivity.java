package com.pndoo.grown123_new;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.util.DialogUtils;
import com.pndoo.grown123_new.util.MyPreferences;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

public class JobPictureActivity extends BaseActivity {
    private String filepath;
    private FileInputStream fis;
    private final String TAG = getClass().getSimpleName();
    private LinearLayout layout1, layout2, layout3, layout4;
    private ImageView picture_palyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jobpic);
        picture_palyer = (ImageView) findViewById(R.id.picture_palyer);

        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout4 = (LinearLayout) findViewById(R.id.layout4);

        String layoutname = getIntent().getStringExtra("layout");
        setLayout(layoutname);

    }

    private void setLayout(String layoutname) {
        if (layoutname.equals("AddJobActivity")) {
            layout1.setVisibility(View.INVISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.GONE);
            layout4.setVisibility(View.GONE);

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
        } else {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.INVISIBLE);
            layout3.setVisibility(View.VISIBLE);
            layout4.setVisibility(View.VISIBLE);

            TextView tv_time = (TextView) findViewById(R.id.tv_header_title);
            TextView tv_num = (TextView) findViewById(R.id.tv_header_num);
            tv_num.setVisibility(View.GONE);
            TextView tv_content = (TextView) findViewById(R.id.tv_content);
            TextView tv_zan = (TextView) findViewById(R.id.tv_zan);
            TextView tv_pinglun = (TextView) findViewById(R.id.tv_pinglun);

            Bundle data = new Bundle();
            data = getIntent().getExtras();
            tv_time.setText(data.getString("time"));
            tv_content.setText(data.getString("content"));
            tv_zan.setText(data.getInt("likecount")+"");
            tv_pinglun.setText(data.getInt("commentcount")+"");
            int position = data.getInt("position");
            ArrayList<String> list = data.getStringArrayList("list");

            DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.zhibo_default)
                    // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.zhibo_default)
                    // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.zhibo_default)
                    // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)
                    // 设置下载的图片是否缓存在内存中
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();
            ImageLoader.getInstance().displayImage(Preferences.IMAGE_HTTP_LOCATION + list.get(position), picture_palyer, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    new PhotoViewAttacher(picture_palyer).update();
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });

        }
    }

    public static final int DELETE = 1234;

    public void btnDelete(View v) {
        DialogUtils.showMyDialog(this, MyPreferences.SHOW_CONFIRM_DIALOG, "确认删除", "是否确认删除？", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(DELETE);
                finish();
            }
        });
    }


    public void btnBack(View v) {
        this.finish();
    }

}
