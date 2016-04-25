package com.queqianme.hpt.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;

import com.ab.activity.AbActivity;
import com.bugtags.library.Bugtags;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.queqianme.hpt.R;

import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpTaskHandler;

/**
 * Desction:
 * Author:zhaojaiyu
 * Date:15/9/26 下午5:59
 */
public class BaseActivity extends AbActivity implements MyHttpCycleContext {

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    protected DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示图片的配置
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.head_icon140)
                .showImageForEmptyUri(R.mipmap.head_icon140)
                .showImageOnFail(R.mipmap.head_icon140)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

}
