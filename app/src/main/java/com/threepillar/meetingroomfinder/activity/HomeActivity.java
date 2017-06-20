package com.threepillar.meetingroomfinder.activity;

import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.base.BaseActivity;
import com.threepillar.meetingroomfinder.enums.FragmentTag;
import com.threepillar.meetingroomfinder.fragment.RoomsFragment;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        showFragment(FragmentTag.CalenderFragment, null, true);
        showFragment(FragmentTag.SingleDayFragment, null, true);
    }

    @Override
    public void onBackPressed() {
        if (getCurrentFragment() instanceof RoomsFragment) {
            super.onBackPressed();
        }
    }

    private Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
        return currentFragment;
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
