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
 * 设置界面
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @OnClick({R.id.feedback,R.id.about,R.id.score})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.feedback:
                Utils.intnet(this,SettingFeedbackActivity.class);
                break;
            case R.id.about:
                Utils.intnet(this,SettingAboutActivity.class);
                break;
            case R.id.score:
                Utils.intnet(this,SettingScoreActivity.class);
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
