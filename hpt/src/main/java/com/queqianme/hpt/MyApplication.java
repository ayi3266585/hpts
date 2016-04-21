package com.queqianme.hpt;

import android.app.Application;
import android.content.Context;

import com.bugtags.library.Bugtags;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.queqianme.hpt.utils.HttpURL;

import java.util.Map;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

/**
 * Created by zhaojiayu on 16/2/22.
 */
public class MyApplication extends Application {
    public static Context context;
    // 用于存放倒计时时间
    public static Map<String, Long> map;

    @Override
    public void onCreate() {
        super.onCreate();


        if (BuildConfig.BUILD_VERSION.equals("debug")) {
            HttpURL.IP = "http://192.168.1.140:8082";
        } else if (BuildConfig.BUILD_VERSION.equals("release")) {
            HttpURL.IP = "http://192.168.1.140:8083";
        }

        //初始化Bugtags
        Bugtags.start("273129c39f430e9919f4a22767f1cadc", this, Bugtags.BTGInvocationEventBubble);

        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());
        context = getApplicationContext();

        initImageLoader(getApplicationContext());
    }

    /**
     * 图片加载
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3) //线程池内线程的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024)  // SD卡缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        //全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

    public static Context getContext() {
        return context;
    }



}
