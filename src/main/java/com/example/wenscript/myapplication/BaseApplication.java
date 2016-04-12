package com.example.wenscript.myapplication;

import android.app.Application;

/**
 * Created by wenscript on 2016/4/11.
 */
public class BaseApplication extends Application {
    private boolean isDayMode=true;//ture表示日间模式，false表示夜间模式

    public boolean isDayMode() {
        return isDayMode;
    }

    public void setIsDayMode(boolean mode) {
        this.isDayMode = mode;
    }
}
