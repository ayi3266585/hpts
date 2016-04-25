package com.queqianme.hpt.ui.activity;

import android.os.Bundle;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;

import butterknife.ButterKnife;

/**
 * 企业认证
 * Author:zhaojaiyu
 */
public class GetCompanyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_company);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
