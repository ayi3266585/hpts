package com.queqianme.hpt.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.queqianme.hpt.ui.activity.LoginActivity;
import com.queqianme.hpt.view.AlertDialog;

import java.text.DecimalFormat;

/**
 * 工具类
 * Created by zhaojiayu on 2016/1/25.
 */
public class Utils {

    /**
     * 界面跳转
     *
     * @param context  上下文
     * @param activity 目标界面
     */
    public static void intnet(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }
    /**
     * 界面跳转
     *
     * @param context  上下文
     * @param activity 目标界面
     */
    public static void intnet(Context context, Class<?> activity,String text) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("text", text);
        context.startActivity(intent);
    }

    /**
     * 数字加逗号保留小数位
     *
     * @param data 需要格式化的参数
     */
    public static String formatToseparaDecimals(float data) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(data);
    }

    /**
     * 数字加逗号保留小数位
     *
     * @param data 需要格式化的参数
     */
    public static String formatTosepara(float data) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(data);
    }


    /**
     * 登出账户
     *
     * @param context 上下文
     */
    public static void logOut(final Context context) {
        new AlertDialog(context).builder().setMsg("您的账号超时,请重新登录!")
                .setPositiveButton("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCollector.finishAll();
                        Utils.intnet(context, LoginActivity.class);
                    }
                }).show();
    }




}
