package com.queqianme.hpt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ab.util.AbToastUtil;
import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.base.ValidatorEvent;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.EmptyValidator;
import com.queqianme.hpt.utils.HttpURL;
import com.queqianme.hpt.utils.SPUtils;
import com.queqianme.hpt.view.EditTextWithDel;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import de.greenrobot.event.EventBus;

/**
 * 忘记密码设置密码界面
 */
public class ForgetPasswordStepTwoActivity extends BaseActivity {
    /**
     * 密码
     */
    @Bind(R.id.forget_step2_pwd)
    EditTextWithDel password;
    /**
     * 确认密码
     */
    @Bind(R.id.repeat)
    EditTextWithDel repeat;
    /**
     * 提交
     */
    @Bind(R.id.forget_step2_submit)
    Button submit;
    /**
     * 显示/隐藏 密码
     */
    @Bind(R.id.eye)
    CheckBox eye;
    /**
     * 验证码
     */
    private String captcha;
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
        setContentView(R.layout.activity_forget_password_step_two);
        ButterKnife.bind(this);
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

        Intent intent = getIntent();
        captcha = intent.getStringExtra("text");

        validator = new EmptyValidator();
        validator.add(password);
        validator.add(repeat);

        eye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setInputType(0x90);
                    repeat.setInputType(0x90);
                } else {
                    password.setInputType(0x81);
                    repeat.setInputType(0x81);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd1 = password.getText().toString().trim();
                String pwd2 = repeat.getText().toString().trim();
                if (pwd1.equals(pwd2)) {
                    resetPassword();
                } else {
                    AbToastUtil.showToast(ForgetPasswordStepTwoActivity.this, "两次密码不一致,请重新输入!");
                }
            }
        });
    }

    /**
     * 非空监听
     */
    public void onEventMainThread(ValidatorEvent event) {
        if (event.getMsg()) {
            if (validator.isEmpty()) {
                submit.setEnabled(true);
                submit.setTextColor(getResources().getColor(R.color.white));
            }
        } else {
            submit.setEnabled(false);
            submit.setTextColor(getResources().getColor(R.color.btn_hint));
        }
    }

    //提交新密码
    private void resetPassword() {
        JSONObject object = new JSONObject();
        try {
            object.put("phone", SPUtils.get(this, "phone", ""));
            object.put("password", password.getText().toString().trim());
            object.put("captcha", captcha);
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpRequest.post(HttpURL.resetPassword, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                JSONObject object;
                try {
                    object = new JSONObject(s);
                    int status = object.getInt("status");
                    switch (status) {
                        case 0:
                            AbToastUtil.showToast(ForgetPasswordStepTwoActivity.this, "修改成功,请重新登录");
                            ForgetPasswordStepTwoActivity.this.finish();
                            break;
                        default:
                            AbToastUtil.showToast(ForgetPasswordStepTwoActivity.this, object.getString("des"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbToastUtil.showToast(ForgetPasswordStepTwoActivity.this, R.string.http_failure);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销注解框架
        ButterKnife.unbind(this);
        //注销EventBus
        EventBus.getDefault().unregister(this);
        //移除全局Finish
        ActivityCollector.removeActivity(this);
    }
}
