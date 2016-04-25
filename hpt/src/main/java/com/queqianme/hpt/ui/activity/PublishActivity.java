package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布借条,资料认证
 */
public class PublishActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @OnClick({R.id.publish_picture,R.id.publish_person,R.id.publish_school_company,R.id.publish_submit})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.publish_picture:
                Utils.intnet(this,PublishPictureActivity.class);
                break;
            case R.id.publish_person:
                Utils.intnet(this,PublishPersonActivity.class);
                break;
            case R.id.publish_school_company:
                Utils.intnet(this,PublishSchoolOrCompanyActivity.class);
                break;
            case R.id.publish_submit:
                Utils.intnet(this,BankCardActivity.class);
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
