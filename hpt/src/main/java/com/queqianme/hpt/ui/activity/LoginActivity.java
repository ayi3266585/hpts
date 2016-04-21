package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.bean.ValidatorEvent;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.EmptyValidator;
import com.queqianme.hpt.utils.HttpURL;
import com.queqianme.hpt.utils.LogUtils;
import com.queqianme.hpt.utils.SPUtils;
import com.queqianme.hpt.utils.Utils;
import com.queqianme.hpt.view.CircleImageView;
import com.queqianme.hpt.view.EditTextWithDel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import de.greenrobot.event.EventBus;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    /**
     * 头像
     */
    @Bind(R.id.login_head)
    CircleImageView head;
    /**
     * 登录按钮
     */
    @Bind(R.id.login_btn)
    Button login;
    /**
     * 手机号
     */
    @Bind(R.id.login_phone)
    EditTextWithDel phone;
    /**
     * 密码
     */
    @Bind(R.id.login_password)
    EditTextWithDel password;
    /**
     * 密码类型
     */
    @Bind(R.id.eye)
    CheckBox eye;
    /**
     * 请求参数体
     */
    private RequestParams params;
    /**
     * 请求返回JSON
     */
    private JSONObject responseJSON = null;
    /**
     * 非空验证
     */
    private EmptyValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    /**
     * 非空监听
     */
    public void onEventMainThread(ValidatorEvent event) {
        if (event.getMsg()) {
            if (validator.isEmpty()) {
                login.setEnabled(true);
                login.setTextColor(getResources().getColor(R.color.white));
            }
        } else {
            login.setEnabled(false);
            login.setTextColor(getResources().getColor(R.color.btn_hint));
        }
    }

    /**
     * 初始化数据
     */
    private void init() {
        //注册注解框架
        ButterKnife.bind(this);
        //添加全局Finish
        ActivityCollector.addActivity(this);
        //注册EventBus
        EventBus.getDefault().register(this);
        params = new RequestParams(this);
        //初始化输入框列表
        validator = new EmptyValidator();
        validator.add(phone);
        validator.add(password);

        String iconUrl = (String) SPUtils.get(this, "icon", "");
        ImageLoader.getInstance().displayImage(iconUrl, head, options);

        eye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setInputType(0x90);
                } else {
                    password.setInputType(0x81);
                }
            }
        });

        String name = SPUtils.get(this, "phone", "") + "";
        String pwd = SPUtils.get(this, "password", "") + "";

        phone.setText(name);
        password.setText(pwd);

    }

    @OnClick({R.id.login_btn, R.id.register, R.id.forget_pwd})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.register:
                Utils.intnet(this, RegisterActivity.class);
                finish();
                break;
            case R.id.forget_pwd:
                Utils.intnet(this, ForgetPasswordStepOneActivity.class);
                break;
        }
    }

    /**
     * 登录请求
     */
    private void login() {
        AbDialogUtil.showProgressDialog(this, 0, "登录中...");
        JSONObject object = new JSONObject();
        try {
            object.put("phone", phone.getText().toString().trim());
            object.put("password", password.getText().toString().trim());
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.login, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                AbDialogUtil.removeDialog(LoginActivity.this);
                try {
                    responseJSON = new JSONObject(s);
                    int status = responseJSON.getInt("status");
                    switch (status) {
                        case 0:
                            saveUserInfo();
                            getUserAuthInfo();
                            Utils.intnet(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.finish();
                            break;
                        default:
                            AbToastUtil.showToast(LoginActivity.this, responseJSON.getString("des"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbDialogUtil.removeDialog(LoginActivity.this);
                AbToastUtil.showToast(LoginActivity.this, R.string.http_failure);
            }
        });
    }

    /**
     * 保存登录信息
     */
    private void saveUserInfo() {
        try {
            SPUtils.put(this, "phone", phone.getText().toString().trim());
            SPUtils.put(this, "password", password.getText().toString().trim());
            SPUtils.put(this, "userId", responseJSON.getLong("userId"));
            SPUtils.put(this, "token", responseJSON.getString("token"));

            JSONObject userinfo = responseJSON.getJSONObject("userInfo");
            SPUtils.put(this, "userId", responseJSON.getLong("userId"));
            SPUtils.put(this, "icon", userinfo.getString("icon"));
            SPUtils.put(this, "name", userinfo.getString("name"));
            SPUtils.put(this, "phone", userinfo.getString("phone"));
            SPUtils.put(this, "gender", userinfo.getInt("gender"));
            SPUtils.put(this, "officialLimit", userinfo.getInt("officialLimit"));
            SPUtils.put(this, "useableLimit", userinfo.getInt("useableLimit"));
            SPUtils.put(this, "msgCount", userinfo.getInt("msgCount"));
            SPUtils.put(this, "applyingCount", userinfo.getInt("applyingCount"));
            SPUtils.put(this, "repayingCount", userinfo.getInt("repayingCount"));
            SPUtils.put(this, "identityInfoStatus", userinfo.getInt("identityInfoStatus"));

            JSONArray flags = userinfo.getJSONArray("flag");
            long callFlag = (long) flags.get(0);
            long smsFlag = (long) flags.get(1);
            SPUtils.put(this, "callFlag", callFlag);
            SPUtils.put(this, "smsFlag", smsFlag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取投资列表
     */
    private void getUserAuthInfo() {
        JSONObject object = new JSONObject();
        try {
            object.put("phone", SPUtils.get(this, "phone", ""));
            object.put("password", SPUtils.get(this, "password", ""));
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.getUserAuthInfo, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                LogUtils.i(s);
                AbDialogUtil.removeDialog(LoginActivity.this);
                try {
                    responseJSON = new JSONObject(s);
                    int status = responseJSON.getInt("status");
                    switch (status) {
                        case 0:
                            SPUtils.put(LoginActivity.this, "personalPercent", responseJSON.getInt("personalPercent"));
                            SPUtils.put(LoginActivity.this, "careerPercent", responseJSON.getInt("careerPercent"));
                            SPUtils.put(LoginActivity.this, "photoInfoStatus", responseJSON.getInt("photoInfoStatus"));
                            SPUtils.put(LoginActivity.this, "personalStatus", responseJSON.getInt("personalStatus"));
                            SPUtils.put(LoginActivity.this, "careerInfoStatus", responseJSON.getInt("careerInfoStatus"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbDialogUtil.removeDialog(LoginActivity.this);
                AbToastUtil.showToast(LoginActivity.this, R.string.http_failure);
            }

        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销注解框架
        ButterKnife.unbind(this);
        //清空输入框列表
        validator.clear();
        //注销EventBus
        EventBus.getDefault().unregister(this);
        //移除全局Finish
        ActivityCollector.removeActivity(this);
    }
}
