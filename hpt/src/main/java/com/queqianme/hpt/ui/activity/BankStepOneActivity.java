package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Utils;
import com.queqianme.hpt.view.EditTextWithDel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加银行卡第一步
 */
public class BankStepOneActivity extends BaseActivity {
    /**
     * 姓名
     */
    @Bind(R.id.cardName)
    EditTextWithDel cardName;
    /**
     * 银行卡卡号
     */
    @Bind(R.id.cardId)
    EditTextWithDel cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_step_one);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @OnClick({R.id.bank_step1_next})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.bank_step1_next:
                Utils.intnet(this,BankStepTwoActivity.class);
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
