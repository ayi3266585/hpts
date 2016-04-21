package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 贷款资料 学校或公司
 * Author:zhaojaiyu
 */
public class PublishSchoolOrCompanyActivity extends BaseActivity {
    @Bind(R.id.submit)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_school_or_company);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @OnClick({R.id.submit})
    public void Click(View view) {
        Utils.intnet(this, MyShopActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
