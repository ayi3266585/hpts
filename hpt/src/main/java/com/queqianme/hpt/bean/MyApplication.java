package com.queqianme.hpt.bean;

import android.app.Application;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

/**
 * Created by zhaojiayu on 16/2/22.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        builder.setTimeout(8000);
        OkHttpFinal.getInstance().init(builder.build());
    }
}
