package com.queqianme.hpt.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 *  管理Activity,随时随地退出程序
 */
public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();

    /**
     * 绑定Activity
     * @param activity
     */
    public static void addActivity(Activity activity) {  
        activities.add(activity);  
    }

    /**
     * 解除绑定Activity
     * @param activity
     */
    public static void removeActivity(Activity activity) {  
        activities.remove(activity);  
    }

    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }  
    }
}
