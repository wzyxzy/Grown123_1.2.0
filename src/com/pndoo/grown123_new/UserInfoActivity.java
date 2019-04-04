package com.pndoo.grown123_new;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.FileUtil;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.UriUtils;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class UserInfoActivity extends BaseActivity {
    TextView tv_username;
    TextView tv_email;//昵称
    private TextView tv_header_title;
    private final int REQUEST_CODE = 0x01;
    private String photo_path;
    private ImageView iv_photo;
    private DisplayImageOptions options;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationContext().addActivity(this);
        setContentView(R.layout.user_info);
        initView();
    }

    public void initView() {
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_img)
                // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_img)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_img)
                // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).cacheOnDisc(true).build();
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_header_title.setText("我的信息");
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_username.setText(UserUtil.getInstance(getApplicationContext()).getUserName());

        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_email.setText(UserUtil.getInstance(getApplicationContext())
                .getSurname());
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        setPhoto();
    }

    private void setPhoto() {
        String url = "file://"+ActivityUtils.getSDPath()+"/photo.png";
        DiskCacheUtils.removeFromCache(url, imageLoader.getDiskCache());
        MemoryCacheUtils.removeFromCache(url, imageLoader.getMemoryCache());
        ImageLoader.getInstance().displayImage(url, iv_photo, options);
    }

    public void btnBack(View v) {
        this.finish();
    }

    public void updateInfo(View v) {
        Intent intent;
        int id = v.getId();
        if (id == R.id.ll_password) {
            intent = new Intent(this, UpdateUserInfoActivity.class);
            intent.putExtra("updateFlag", MyPreferences.UPDATE_PASSWORD);
            startActivityForResult(intent, MyPreferences.UPDATE_PASSWORD);
        } else {
        }
    }

    public void updateSurname(View v) {
        Intent intent = new Intent(this, UpdateSurnameActivity.class);
        startActivityForResult(intent, MyPreferences.UPDATE_SURNAME);
    }

    public void updatePhoto(View v) {
        Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
        if (Build.VERSION.SDK_INT < 19) {
            innerIntent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
        startActivityForResult(wrapperIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) {
            return;
        }
        switch (requestCode) {
            case MyPreferences.UPDATE_USERNAME:
                tv_username.setText(data.getStringExtra("username"));
                break;

            case MyPreferences.UPDATE_SURNAME:
                tv_email.setText(data.getStringExtra("surname"));
                break;
            case REQUEST_CODE:

                String[] proj = {MediaStore.Images.Media.DATA};
                // 获取选中图片的路径
                Cursor cursor = getContentResolver().query(data.getData(), proj, null, null, null);

                if (cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    photo_path = cursor.getString(column_index);
                    if (photo_path == null) {
                        photo_path = UriUtils.getPath(getApplicationContext(), data.getData());
                        Log.i("123path  Utils", photo_path);
                    }
                    Log.i("123path", photo_path);
                }
                cursor.close();
                FileUtil.CopySdcardFile(photo_path,ActivityUtils.getSDPath()+"/photo.png");
                File file = new File(photo_path);
                RequestParams params = new RequestParams(Preferences.JOB_CHANGEPHOTO);
                params.setMultipart(true);
                params.addQueryStringParameter("userId", UserUtil.getInstance(this).getUserId());
                params.addBodyParameter("Image", file);

                x.http().post(params, new org.xutils.common.Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.e("Search responseInfo", "responseInfo=====" + s);
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<Bean>() {
                        }.getType();
                        Bean bean = gson.fromJson(s, type);
                        if (bean.getCode().equals("SUCCESS")) {
                            setPhoto();
                        } else {
                            ActivityUtils.showToast(UserInfoActivity.this, bean.getCodeInfo());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        getApplicationContext().removeActivity(this);
        super.onDestroy();
    }

}
