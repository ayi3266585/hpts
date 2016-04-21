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
 * 上传照片认证信息
 */
public class PublishPictureActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_picture);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @OnClick({R.id.submit})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.submit:
                Utils.intnet(this, MyShopActivity.class);
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