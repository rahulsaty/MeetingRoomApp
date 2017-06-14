package com.threepillar.meetingroomfinder.common.utils;

import android.util.Log;

import com.threepillar.meetingroomfinder.BuildConfig;

public class LogUtil {
    public static final boolean DEBUG_MODE = BuildConfig.DEBUG;

    public static void  LOGI(String tag, String message){
        if(DEBUG_MODE)
            Log.i(tag,message);
    }

    public static void  LOGD(String tag, String message){
        if(DEBUG_MODE)
            Log.d(tag,message);
    }

    public static void  LOGE(String tag, String message){
        if(DEBUG_MODE)
            Log.e(tag,message);
    }

}
