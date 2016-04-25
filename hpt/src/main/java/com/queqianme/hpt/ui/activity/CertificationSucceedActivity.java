package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建店铺成功界面
 * Author:zhaojaiyu
 */
public class CertificationSucceedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_succeed);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @OnClick(R.id.certification_btn)
    public void click(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
