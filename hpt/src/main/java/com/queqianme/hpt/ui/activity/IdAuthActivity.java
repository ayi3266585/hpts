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
import com.queqianme.hpt.utils.SPUtils;
import com.queqianme.hpt.view.EditTextWithDel;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * 实名认证界面
 */
public class IdAuthActivity extends BaseActivity {
    /**
     * 身份证姓名
     */
    @Bind(R.id.identity_name)
    EditTextWithDel name;
    /**
     * 身份证号码
     */
    @Bind(R.id.identityNum)
    EditTextWithDel number;
    /**
     * 提交按钮
     */
    @Bind(R.id.identity_submit)
    Button submit;
    /**
     * 请求参数体
     */
    private RequestParams params;
    /**
     * 非空验证
     */
    private EmptyValidator validator;
    /**
     * 请求返回JSON
     */
    private JSONObject responseJSON = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_auth);
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        params = new RequestParams(this);
        //初始化输入框列表
        validator = new EmptyValidator();
        validator.add(name);
        validator.add(number);
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

    /**
     * 点击事件
     */
    @OnClick({R.id.identity_submit})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.identity_submit:
                submit();
                break;
        }
    }

    /**
     * 实名认证请求
     */
    private void submit() {
        AbDialogUtil.showProgressDialog(this, 0, "认证中...");
        JSONObject object = new JSONObject();
        try {
            object.put("userId", SPUtils.get(this, "userId", 0L));
            object.put("token", SPUtils.get(this, "token", ""));
            object.put("name", name.getText().toString().trim());
            object.put("identityNum", number.getText().toString().trim());
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.authIdentityInfo, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                AbDialogUtil.removeDialog(IdAuthActivity.this);
                try {
                    responseJSON = new JSONObject(s);
                    int status = responseJSON.getInt("status");
                    switch (status) {
                        case 0:
                            break;
                        default:
                            AbToastUtil.showToast(IdAuthActivity.this, responseJSON.getString("des"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbDialogUtil.removeDialog(IdAuthActivity.this);
                AbToastUtil.showToast(IdAuthActivity.this, R.string.http_failure);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}