package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.ab.util.AbToastUtil;
import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.net.UserInfo;
import com.queqianme.hpt.utils.ActivityCollector;
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

/**
 * 修改昵称界面
 */
public class ChangeNameActivity extends BaseActivity {
    /**
     * 提交按钮
     */
    @Bind(R.id.submit)
    Button submit;
    /**
     * 昵称
     */
    @Bind(R.id.nick_name)
    EditTextWithDel nickName;
    private RequestParams params;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        params = new RequestParams(this);
        userInfo = new UserInfo(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendName();
            }
        });
        //输入框监听
        nickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    submit.setEnabled(true);
                    submit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    submit.setEnabled(false);
                    submit.setTextColor(getResources().getColor(R.color.text_hint));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 修改昵称
     */
    private void sendName() {
        JSONObject object = new JSONObject();
        try {
            object.put("userId", SPUtils.get(this, "userId", 0L));
            object.put("token", SPUtils.get(this, "token", ""));
            object.put("nickname", nickName.getText().toString().trim());
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.editNickname, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                try {
                    JSONObject responseJSON = new JSONObject(s);
                    int status = responseJSON.getInt("status");
                    switch (status) {
                        case 0:
                            userInfo.getUserInfo();
                            SPUtils.put(ChangeNameActivity.this, "name", nickName.getText().toString().trim());
                            AbToastUtil.showToast(ChangeNameActivity.this, "修改成功");
                            ChangeNameActivity.this.finish();
                            break;
                        default:
                            AbToastUtil.showToast(ChangeNameActivity.this, responseJSON.getString("des"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbToastUtil.showToast(ChangeNameActivity.this, R.string.http_failure);
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
