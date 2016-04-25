package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.view.widget.ProgressDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户信息界面
 */
public class LoanUserInfoActivity extends BaseActivity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_userinfo);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        dialog = new ProgressDialog(this, "努力中...");
        dialog.builder();
    }

    @OnClick({R.id.head_img})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.head_img:
                dialog.showDialog();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
