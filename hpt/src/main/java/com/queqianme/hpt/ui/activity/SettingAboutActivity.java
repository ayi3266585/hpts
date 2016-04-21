package com.queqianme.hpt.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.view.AlertDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于我们界面
 */
public class SettingAboutActivity extends BaseActivity {
    @Bind(R.id.tell_phone)
    TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
    }

    @OnClick({R.id.linearVersion, R.id.tell_phone})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.linearVersion:
                new AlertDialog(this).builder().setMsg("已是最新版本!")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
                break;
            case R.id.tell_phone:
                new AlertDialog(this).builder().setMsg("拨打客服电话:  " + phone.getText().toString().trim())
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone.getText().toString().trim()));
                                startActivity(phoneIntent);
                            }
                        })
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
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