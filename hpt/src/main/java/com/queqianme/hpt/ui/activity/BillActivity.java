package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账单界面
 */
public class BillActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @OnClick({})
    public void Click(View view) {
        switch (view.getId()) {
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
