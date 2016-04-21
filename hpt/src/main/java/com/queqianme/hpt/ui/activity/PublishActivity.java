package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布借条界面
 */
public class PublishActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.submit})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.btn1://借款信息认证
                Utils.intnet(this, PublishInfoActivity.class);
                break;
            case R.id.btn2://上传照片认证信息
                Utils.intnet(this, PublishPictureActivity.class);
                break;
            case R.id.btn3://个人信息认证
                Utils.intnet(this, PublishPersonActivity.class);
                break;
            case R.id.btn4://学校公司认证
                Utils.intnet(this, PublishSchoolOrCompanyActivity.class);
                break;
            case R.id.submit://发布借条
                Utils.intnet(this, PublishSuccessActivity.class);
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
