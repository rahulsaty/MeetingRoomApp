package com.threepillar.meetingroomfinder.common.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mohd.irfan on 6/14/2017.
 */

public class AppPrefrence {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context ctx;

    private final String PREF_NAME = "mypref";
    private final String ACCOUNT_NAME = "PREF_ACCOUNT_NAME";
    private final String ROOM_TYPE = "PREF_ROOM_TYPE";

    public AppPrefrence(Context context) {
        this.ctx = context;
        preferences = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setAccountName(String value) {
        editor.putString(ACCOUNT_NAME, value);
        editor.commit();
    }

    public String getAccountName() {
        return preferences.getString(ACCOUNT_NAME, null);
    }

    public String getRoomType() {
        return preferences.getString(ROOM_TYPE, null);
    }


    public void setRoomType(String value) {
        editor.putString(ROOM_TYPE, value);
        editor.commit();
    }


}
