package com.queqianme.hpt.ui.activity;

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
import com.queqianme.hpt.utils.Utils;
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
 * 重置密码界面
 */
public class ChangePasswordActivity extends BaseActivity {
    /**
     * 旧密码
     */
    @Bind(R.id.oldPassword)
    EditTextWithDel oldPassword;
    /**
     * 新密码
     */
    @Bind(R.id.newPassword)
    EditTextWithDel newPassword;
    /**
     * 确认新密码
     */
    @Bind(R.id.repeat)
    EditTextWithDel repeat;
    /**
     * 显示/隐藏密码
     */
    @Bind(R.id.eye)
    CheckBox eye;
    /**
     * 提交
     */
    @Bind(R.id.submit)
    Button submit;
    /**
     * 非空验证
     */
    private EmptyValidator validator;
    private RequestParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        params = new RequestParams(this);
        //注册EventBus
        EventBus.getDefault().register(this);

        validator = new EmptyValidator();
        validator.add(oldPassword);
        validator.add(newPassword);
        validator.add(repeat);

        eye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    oldPassword.setInputType(0x90);
                    newPassword.setInputType(0x90);
                    repeat.setInputType(0x90);
                } else {
                    oldPassword.setInputType(0x81);
                    newPassword.setInputType(0x81);
                    repeat.setInputType(0x81);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd1 = newPassword.getText().toString().trim();
                String pwd2 = repeat.getText().toString().trim();
                if (pwd1.equals(pwd2)) {
                    editPassword();
                } else {
                    AbToastUtil.showToast(ChangePasswordActivity.this, "两次密码不一致,请重新输入!");
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
    private void editPassword() {
        JSONObject object = new JSONObject();
        try {
            object.put("userId", SPUtils.get(this, "userId", 0L));
            object.put("token", SPUtils.get(this, "token", ""));
            object.put("oldPassword", oldPassword.getText().toString().trim());
            object.put("newPassword", newPassword.getText().toString().trim());
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.editPassword, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                JSONObject object;
                try {
                    object = new JSONObject(s);
                    int status = object.getInt("status");
                    switch (status) {
                        case 0:
                            SPUtils.remove(ChangePasswordActivity.this, "password");
                            AbToastUtil.showToast(ChangePasswordActivity.this, "修改成功,请重新登录");
                            ActivityCollector.finishAll();
                            Utils.intnet(ChangePasswordActivity.this, LoginActivity.class);
                            break;
                        default:
                            AbToastUtil.showToast(ChangePasswordActivity.this, object.getString("des"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbToastUtil.showToast(ChangePasswordActivity.this, R.string.http_failure);
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
