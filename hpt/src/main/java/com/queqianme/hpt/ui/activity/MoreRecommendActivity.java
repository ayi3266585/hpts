package com.queqianme.hpt.ui.activity;

import android.os.Bundle;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;

import butterknife.ButterKnife;

/**
 * 首页推荐列表
 */
public class MoreRecommendActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_recommend);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        initData();
    }

    private void initData() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
