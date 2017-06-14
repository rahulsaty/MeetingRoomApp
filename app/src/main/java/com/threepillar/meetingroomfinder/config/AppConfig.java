package com.threepillar.meetingroomfinder.config;

import android.content.Context;

public class AppConfig {

    private static AppConfig appConfig;

    private Context context;

    public Context getContext() {
        return context;
    }

    private AppConfig() {

    }

    public static AppConfig getInstance() {

        if (appConfig == null) {
            synchronized (AppConfig.class) {
                if (appConfig == null)
                    appConfig = new AppConfig();

            }
        }
        return appConfig;
    }

    public void init(Context context) {
        this.context = context;
    }
}