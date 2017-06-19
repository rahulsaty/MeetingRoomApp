package com.threepillar.meetingroomfinder.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.base.BaseActivity;
import com.threepillar.meetingroomfinder.enums.FragmentTag;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        showFragment(FragmentTag.RoomsFragment, null, true);
    }


    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    protected int getResourceId() {
        return R.layout.content_home;
    }

    @Override
    protected int getMainFragmentContainerId() {
        return R.id.frame_layout;
    }

    @Override
    protected void initView() {

    }
}
