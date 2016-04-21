package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.bean.ValidatorEvent;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.EmptyValidator;
import com.queqianme.hpt.utils.HttpURL;
import com.queqianme.hpt.utils.Utils;
import com.queqianme.hpt.view.EditTextWithDel;

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
 * 忘记密码手机验证界面
 */
public class ForgetPasswordStepOneActivity extends BaseActivity {
    /**
     * 下一步
     */
    @Bind(R.id.forget_step1_next)
    Button next;
    /**
     * 手机号
     */
    @Bind(R.id.forget_step1_phone)
    EditTextWithDel phone;
    /**
     * 验证码
     */
    @Bind(R.id.forget_step1_captcha)
    EditTextWithDel captcha;
    /**
     * 请求参数体
     */
    private RequestParams params;
    /**
     * 非空验证
     */
    private EmptyValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_step_one);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        //注册EventBus
        EventBus.getDefault().register(this);
        params = new RequestParams(this);
        validator = new EmptyValidator();
        validator.add(phone);
        validator.add(captcha);
    }

    /**
     * 非空监听
     */
    public void onEventMainThread(ValidatorEvent event) {
        if (event.getMsg()) {
            if (validator.isEmpty()) {
                next.setEnabled(true);
                next.setTextColor(getResources().getColor(R.color.white));
            }
        } else {
            next.setEnabled(false);
            next.setTextColor(getResources().getColor(R.color.btn_hint));
        }
    }

    @OnClick({R.id.forget_step1_next, R.id.forget_step1_auth_code})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.forget_step1_next:
                checkResetPasswordCaptcha();
                break;
            case R.id.forget_step1_auth_code:
                getAuthCode();
                break;
        }
    }

    //获取重置登陆密码验证码
    private void getAuthCode() {
        JSONObject object = new JSONObject();
        try {
            object.put("phone", phone.getText().toString().trim());
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.getResetPasswordCaptcha, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                JSONObject object;
                try {
                    object = new JSONObject(s);
                    int status = object.getInt("status");
                    switch (status) {
                        case 0:
                            AbToastUtil.showToast(ForgetPasswordStepOneActivity.this, "验证码已发送");
                            break;
                        default:
                            AbToastUtil.showToast(ForgetPasswordStepOneActivity.this, object.getString("des"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbToastUtil.showToast(ForgetPasswordStepOneActivity.this, R.string.http_failure);
            }
        });
    }

    //验证重置登陆密码验证码
    private void checkResetPasswordCaptcha() {
        AbDialogUtil.showProgressDialog(this, 0, "验证中...");
        JSONObject object = new JSONObject();
        try {
            object.put("phone", phone.getText().toString().trim());
            object.put("captcha", captcha.getText().toString().trim());
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.checkResetPasswordCaptcha, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                AbDialogUtil.removeDialog(ForgetPasswordStepOneActivity.this);
                JSONObject object;
                try {
                    object = new JSONObject(s);
                    int status = object.getInt("status");
                    switch (status) {
                        case 0:
                            Utils.intnet(ForgetPasswordStepOneActivity.this, ForgetPasswordStepTwoActivity.class, captcha.getText().toString().trim());
                            ForgetPasswordStepOneActivity.this.finish();
                            break;
                        default:
                            AbToastUtil.showToast(ForgetPasswordStepOneActivity.this, object.getString("des"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbDialogUtil.removeDialog(ForgetPasswordStepOneActivity.this);
                AbToastUtil.showToast(ForgetPasswordStepOneActivity.this, R.string.http_failure);
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
