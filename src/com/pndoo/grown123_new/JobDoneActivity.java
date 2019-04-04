package com.pndoo.grown123_new;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pndoo.grown123_new.fragment.Fragment_Done;

public class JobDoneActivity extends FragmentActivity {

    private final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_job_done);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment_Done fragment = new Fragment_Done();
        Bundle b = new Bundle();
        b.putBoolean("isHeader", false);
        b.putBoolean("isDone", getIntent().getBooleanExtra("isDone",false));
        b.putInt("homeworkId", getIntent().getIntExtra("homeworkId",0));
        fragment.setArguments(b);
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void btnBack(View v){
        finish();
    }
}
