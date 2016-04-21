package com.queqianme.hpt.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.queqianme.hpt.R;

import cn.finalteam.okhttpfinal.HttpTaskHandler;

/**
 * Desction:
 * Author:zhaojaiyu
 * Date:15/9/26 下午7:26
 */
public class BaseFragment extends Fragment implements MyHttpCycleContext {

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    protected DisplayImageOptions options;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //显示图片的配置
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.head_icon140)
                .showImageForEmptyUri(R.mipmap.head_icon140)
                .showImageOnFail(R.mipmap.head_icon140)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
    }

}
