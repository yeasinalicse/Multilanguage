package com.yeasin.multilanguage;

import android.app.Application;

/**
 * Created by yeasin
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiLanguageUtil.init(this);
    }
}
