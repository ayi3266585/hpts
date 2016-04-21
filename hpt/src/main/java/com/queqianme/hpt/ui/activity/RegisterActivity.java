package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

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
 * 注册界面
 */
public class RegisterActivity extends BaseActivity {
    /**
     * 注册按钮
     */
    @Bind(R.id.register_btn)
    Button register;
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
     * 验证码
     */
    @Bind(R.id.register_captcha)
    EditText captcha;
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
        setContentView(R.layout.activity_register);
        init();
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
        validator.add(captcha);


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
    }

    /**
     * 非空监听
     */
    public void onEventMainThread(ValidatorEvent event) {
        if (event.getMsg()) {
            if (validator.isEmpty()) {
                register.setEnabled(true);
                register.setTextColor(getResources().getColor(R.color.white));
            }
        } else {
            register.setEnabled(false);
            register.setTextColor(getResources().getColor(R.color.btn_hint));
        }
    }


    @OnClick({R.id.register_btn, R.id.login, R.id.register_auth_code})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                register();
                break;
            case R.id.login:
                Utils.intnet(this, LoginActivity.class);
                finish();
                break;
            case R.id.register_auth_code:
                getAuthCode();
                break;
        }
    }

    //获取验证码
    private void getAuthCode() {
        JSONObject object = new JSONObject();
        try {
            object.put("phone", phone.getText().toString().trim());
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.getRegisterCaptcha, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                JSONObject object;
                try {
                    object = new JSONObject(s);
                    int status = object.getInt("status");
                    switch (status) {
                        case 0:
                            AbToastUtil.showToast(RegisterActivity.this, "验证码已发送");
                            break;
                        default:
                            AbToastUtil.showToast(RegisterActivity.this, object.getString("des"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbToastUtil.showToast(RegisterActivity.this, R.string.http_failure);
            }
        });


    }

    //提交注册
    private void register() {
        AbDialogUtil.showProgressDialog(this, 0, "提交中...");
        JSONObject object = new JSONObject();
        try {
            object.put("phone", phone.getText().toString().trim());
            object.put("password", password.getText().toString().trim());
            object.put("captcha", captcha.getText().toString().trim());
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.register, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                AbDialogUtil.removeDialog(RegisterActivity.this);
                JSONObject object;
                try {
                    object = new JSONObject(s);
                    int status = object.getInt("status");
                    switch (status) {
                        case 0:
                            AbToastUtil.showToast(RegisterActivity.this, "注册成功");
                            Utils.intnet(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.finish();
                            break;
                        default:
                            AbToastUtil.showToast(RegisterActivity.this, object.getString("des"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbDialogUtil.removeDialog(RegisterActivity.this);
                AbToastUtil.showToast(RegisterActivity.this, R.string.http_failure);
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
