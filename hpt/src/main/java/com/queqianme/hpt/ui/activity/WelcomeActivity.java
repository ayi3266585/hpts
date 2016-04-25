package com.queqianme.hpt.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ab.util.AbToastUtil;
import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.HttpURL;
import com.queqianme.hpt.utils.LogUtils;
import com.queqianme.hpt.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * 进入广告页
 */
public class WelcomeActivity extends BaseActivity {
    /**
     * 是否是首次进入
     */
    private boolean isFirstIn = false;
    /**
     * 广告时间
     */
    private static final int TIME = 2000;
    /**
     * 进入首页标记
     */
    private static final int GO_HOME = 1000;
    /**
     * 进入广告标记
     */
    private static final int GO_GUIDE = 1001;


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //进入首页
                case GO_HOME:
                    goHome();
                    if (SPUtils.get(WelcomeActivity.this, "password", "") != "") {
                        login();
                    }
                    break;
                //进入广告页
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }

        ;
    };
    /**
     * 请求参数体
     */
    private RequestParams params;
    /**
     * 请求返回参数体
     */
    private JSONObject responseJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    /**
     * 判断是否是第一次进入
     */
    private void init() {
        ButterKnife.bind(this);
        params = new RequestParams(this);
        SharedPreferences perPreferences = getSharedPreferences("login", MODE_PRIVATE);
        isFirstIn = perPreferences.getBoolean("isFirstIn", true);

        if (!isFirstIn) {
            mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
            SharedPreferences.Editor editor = perPreferences.edit();
            editor.putBoolean("isFirstIn", false);
            editor.commit();
        }

    }

    private void goHome() {
        Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goGuide() {
        Intent i = new Intent(WelcomeActivity.this, GuideActivity.class);
        startActivity(i);
        finish();
    }

    private void login() {
        JSONObject object = new JSONObject();
        try {
            object.put("phone", SPUtils.get(this, "phone", ""));
            object.put("password", SPUtils.get(this, "password", ""));
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.login, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                LogUtils.i(s);
                try {
                    responseJSON = new JSONObject(s);
                    int status = responseJSON.getInt("status");
                    switch (status) {
                        case 0:
                            saveUserInfo();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbToastUtil.showToast(WelcomeActivity.this, R.string.http_failure);
            }
        });
    }

    /**
     * 保存登录信息
     */
    private void saveUserInfo() {
        try {
            JSONObject userinfo = responseJSON.getJSONObject("userInfo");
            SPUtils.put(this, "userId", responseJSON.getLong("userId"));
            SPUtils.put(this, "token", responseJSON.getString("token"));
            SPUtils.put(this, "iconUrl", userinfo.getString("iconUrl"));
            SPUtils.put(this, "name", userinfo.getString("name"));
            SPUtils.put(this, "phone", userinfo.getString("phone"));
            SPUtils.put(this, "gender", userinfo.optInt("gender"));
            SPUtils.put(this, "identityInfoStatus", userinfo.getInt("identityInfoStatus"));
            SPUtils.put(this, "userLimitStatus", userinfo.getInt("userLimitStatus"));
            SPUtils.put(this, "estimate", userinfo.optInt("estimate"));
            SPUtils.put(this, "officialLimit", userinfo.getInt("officialLimit"));
            SPUtils.put(this, "useableLimit", userinfo.getInt("useableLimit"));
            SPUtils.put(this, "msgCount", userinfo.getInt("msgCount"));
            SPUtils.put(this, "applyingCount", userinfo.getInt("applyingCount"));
            SPUtils.put(this, "repayingCount", userinfo.getInt("repayingCount"));
            JSONArray flag = userinfo.getJSONArray("flag");
            SPUtils.put(this, "flag1", flag.get(0));
            SPUtils.put(this, "flag2", flag.get(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
