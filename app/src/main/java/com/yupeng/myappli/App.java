package com.yupeng.myappli;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    public Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
