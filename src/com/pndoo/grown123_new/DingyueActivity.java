package com.pndoo.grown123_new;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pndoo.grown123_new.dto.base.DingyueDataBean;
import com.pndoo.grown123_new.dto.base.ZhiboTimeBean;
import com.pndoo.grown123_new.util.TimeFormate;

public class DingyueActivity extends BaseActivity {

    private TextView tv_header_title;
    private TextView title, content, teacher, teacher_content, time;
    private DingyueDataBean bean;
    private final String TAG = getClass().getSimpleName();
    private RelativeLayout layout_dingyue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_yugao);
        initView();
        initData();
    }

    private void initView() {
        // TODO Auto-generated method stub
        bean = (DingyueDataBean) getIntent().getSerializableExtra("bean");
        Log.d(TAG, bean.toString());
        tv_header_title = (TextView) findViewById(R.id.tv_header_title);
        tv_header_title.setText("订阅信息");

        title = (TextView) findViewById(R.id.textView1);
        content = (TextView) findViewById(R.id.textView4);
        teacher = (TextView) findViewById(R.id.textView7);
        teacher_content = (TextView) findViewById(R.id.textView6);
        time = (TextView) findViewById(R.id.textView10);
        layout_dingyue = (RelativeLayout) findViewById(R.id.layout_dingyue);
        layout_dingyue.setVisibility(View.GONE);
        findViewById(R.id.layout_button).setVisibility(View.GONE);
    }

    private void initData() {
        // TODO Auto-generated method stub
        title.setText(bean.getCourseName());
        content.setText(bean.getCourseDisc());
        teacher.setText(bean.getExpert());
        teacher_content.setText(bean.getExpertInfo());

        ZhiboTimeBean start = bean.getStartTime();
        ZhiboTimeBean end = bean.getEndTime();
        String minutes = start.getMinutes() > 9 ? start.getMinutes() + "" : "0" + start.getMinutes();
        String date = String
                .format(getResources().getString(R.string.yugao_time), "20" + start.getYear() % 100 + "-" + (start.getMonth() + 1) + "-" + start.getDate(), "20" + end.getYear() % 100 + "-" + (end
                        .getMonth() + 1) + "-" + end.getDate(), TimeFormate.getDay(start.getDay()), start.getHours() + ":" + minutes);
        time.setText(date);

    }

    public void btnBack(View v) {
        this.finish();
    }

}
