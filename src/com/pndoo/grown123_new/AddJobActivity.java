package com.pndoo.grown123_new;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pndoo.grown123_new.adapter.AddJobAdapter;
import com.pndoo.grown123_new.constants.Preferences;
import com.pndoo.grown123_new.dto.base.Bean;
import com.pndoo.grown123_new.model.UserUtil;
import com.pndoo.grown123_new.util.ActivityUtils;
import com.pndoo.grown123_new.util.MyPreferences;
import com.pndoo.grown123_new.util.UriUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddJobActivity extends BaseActivity {

    private TextView tv_jobcontent;
    private EditText et_content;
    private GridView gridView;
    private List<File> imageFiles = new ArrayList<>();
    private int homeworkId;
    private String jobContent;
    private AddJobAdapter adapter;
    private int delete_position = -1;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_addjob);

        jobContent = getIntent().getStringExtra("jobcontent");
        homeworkId = getIntent().getIntExtra("homeworkId", 0);

        initView();
    }

    private void initView() {
        tv_jobcontent = (TextView) findViewById(R.id.textView27);
        tv_jobcontent.setText(jobContent);
        et_content = (EditText) findViewById(R.id.et_text);
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new AddJobAdapter(this, imageFiles);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < imageFiles.size()) {
                    delete_position = position;
                    Intent intent = new Intent(AddJobActivity.this, JobPictureActivity.class);
                    intent.putExtra("filePath", imageFiles.get(position).getAbsolutePath());
                    intent.putExtra("layout","AddJobActivity");
                    startActivityForResult(intent, 0);
                } else {
                    openPhotoAlbum();
                }
            }
        });


    }

    /**
     * 打开相册
     */
    private final int REQUEST_CODE = 0x01;
    private String photo_path;

    private void openPhotoAlbum() {
        Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
        if (Build.VERSION.SDK_INT < 19) {
            innerIntent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, "选择上传图片");
        startActivityForResult(wrapperIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

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

                    File file = new File(photo_path);
                    imageFiles.add(file);
                    adapter.setList(imageFiles);
                    adapter.notifyDataSetChanged();
                    break;
            }
        } else if (resultCode == JobPictureActivity.DELETE) {
            if (delete_position != -1) {
                imageFiles.remove(delete_position);
                delete_position = -1;
                adapter.setList(imageFiles);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void btnSubmit(View view) {
        if(imageFiles!=null&&imageFiles.size()==0){
            ActivityUtils.showToast(AddJobActivity.this, "请上传作业图片");
            return;
        }



        org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(Preferences.JOB_SUBMITHOMEWORK);
        params.addQueryStringParameter("user.userId", UserUtil.getInstance(this).getUserId());
        params.addQueryStringParameter("homework.id",homeworkId+"");
        params.addBodyParameter("content",et_content.getText().toString());
        params.setMultipart(true);
        List<KeyValue> list = new ArrayList<KeyValue>();
        for (File f : imageFiles)
            list.add(new KeyValue("image", f));
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);

        x.http().post(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<Bean>() {
                }.getType();
                Bean bookJson = gson.fromJson(s, type);

                if (bookJson.getCode().equals(MyPreferences.SUCCESS)) {
                    ActivityUtils.showToast(AddJobActivity.this, "发布成功");
                    setResult(0);
                    finish();
                } else if (bookJson.getCode().equals(MyPreferences.FAIL)) {
                    ActivityUtils.showToast(AddJobActivity.this, "发布失败," + bookJson.getCodeInfo());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.d(TAG,throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void btnBack(View view) {
        finish();
    }
}
