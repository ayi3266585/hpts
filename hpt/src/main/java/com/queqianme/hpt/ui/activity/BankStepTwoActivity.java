package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Utils;
import com.queqianme.hpt.view.EditTextWithDel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加银行卡第二步
 */
public class BankStepTwoActivity extends BaseActivity {
    /**
     * 银行名字
     */
    @Bind(R.id.cardName)
    TextView cardName;
    /**
     * 银行卡预留手机号
     */
    @Bind(R.id.cardPhone)
    EditTextWithDel cardPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_step_two);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @OnClick({R.id.bank_step2_next})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.bank_step2_next:
                Utils.intnet(this, BankStepThreeActivity.class);
                finish();
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
