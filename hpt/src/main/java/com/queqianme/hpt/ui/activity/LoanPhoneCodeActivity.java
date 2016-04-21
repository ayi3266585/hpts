package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.view.EditTextWithDel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 付款手机验证
 */
public class LoanPhoneCodeActivity extends BaseActivity {
    /**
     * 手机号
     */
    @Bind(R.id.loan_phone_phone)
    EditTextWithDel phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_phone_code);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }


    @OnClick({R.id.register_auth_code, R.id.loan_phone_submit})
    public void Click(View view) {
        switch (view.getId()) {
            //验证码
            case R.id.register_auth_code:
                getAuthCode();
                break;
            //提交
            case R.id.loan_phone_submit:
                submit();
                break;
        }
    }

    /*重新获取验证码*/
    private void getAuthCode() {
        //TODO
    }

    /*提交*/
    private void submit() {
        //TODO
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }



}
