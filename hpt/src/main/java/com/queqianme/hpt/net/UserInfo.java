package com.queqianme.hpt.net;

import android.content.Context;

import com.ab.util.AbToastUtil;
import com.queqianme.hpt.R;
import com.queqianme.hpt.utils.HttpURL;
import com.queqianme.hpt.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by zhaojiayu on 16/3/9.
 */
public class UserInfo {
    private Context context;
    private RequestParams params;

    public UserInfo(Context context) {
        this.context = context;
        params = new RequestParams((HttpCycleContext) context);
    }

    public void getUserInfo() {

        JSONObject object = new JSONObject();
        try {
            object.put("userId", SPUtils.get(context, "userId", 0L));
            object.put("token", SPUtils.get(context, "token", ""));
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.getUserInfo, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                JSONObject object;
                try {
                    object = new JSONObject(s);
                    int status = object.getInt("status");
                    switch (status) {
                        case 0:
                            saveUserInfo(object.getJSONObject("userInfo"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbToastUtil.showToast(context, R.string.http_failure);
            }
        });
    }


    /**
     * 保存登录信息
     */
    private void saveUserInfo(JSONObject responseJSON) {
        try {
            JSONObject userinfo = responseJSON.getJSONObject("userInfo");
            SPUtils.put(context, "userId", responseJSON.getLong("userId"));
            SPUtils.put(context, "icon", userinfo.getString("icon"));
            SPUtils.put(context, "name", userinfo.getString("name"));
            SPUtils.put(context, "phone", userinfo.getString("phone"));
            SPUtils.put(context, "gender", userinfo.getInt("gender"));
            SPUtils.put(context, "officialLimit", userinfo.getInt("officialLimit"));
            SPUtils.put(context, "useableLimit", userinfo.getInt("useableLimit"));
            SPUtils.put(context, "msgCount", userinfo.getInt("msgCount"));
            SPUtils.put(context, "applyingCount", userinfo.getInt("applyingCount"));
            SPUtils.put(context, "repayingCount", userinfo.getInt("repayingCount"));
            SPUtils.put(context, "identityInfoStatus", userinfo.getInt("identityInfoStatus"));

            JSONArray flags = userinfo.getJSONArray("flag");
            long callFlag = (long) flags.get(0);
            long smsFlag = (long) flags.get(1);
            SPUtils.put(context, "callFlag", callFlag);
            SPUtils.put(context, "smsFlag", smsFlag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
