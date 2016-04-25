package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 贷款资料 学校或公司
 * Author:zhaojaiyu
 */
public class PublishSchoolOrCompanyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_school_or_company);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @OnClick({R.id.publish_school_company_submit})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.publish_school_company_submit:

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
