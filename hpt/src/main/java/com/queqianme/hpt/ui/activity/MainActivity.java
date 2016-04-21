package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;

import com.ab.util.AbToastUtil;
import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.ui.fragment.FragmentMainFourth;
import com.queqianme.hpt.ui.fragment.FragmentMainSecond;
import com.queqianme.hpt.ui.fragment.FragmentMainThird;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.HttpURL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目主Activity,所有主Fragment都嵌入在这里
 */
public class MainActivity extends BaseActivity {
    /**
     * 导航首页
     */
    @Bind(R.id.main_ll)
    RadioGroup rb;
    /**
     * 导航按钮
     */
    Fragment  mTab02, mTab03, mTab04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        setSelect(R.id.main_second);

        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setSelect(checkedId);
            }
        });

        Log.i("ip", "ip="+HttpURL.IP);
    }

    /**
     * 页面点击切换
     * @param checkedId
     */
    private void setSelect(int checkedId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (checkedId) {
            //投资
            case R.id.main_second:
                if (mTab02 == null) {
                    mTab02 = new FragmentMainSecond();
                    transaction.add(R.id.main_fl, mTab02);
                } else {
                    transaction.show(mTab02);
                }
                break;
            //聊天
            case R.id.main_third:
                if (mTab03 == null) {
                    mTab03 = new FragmentMainThird();
                    transaction.add(R.id.main_fl, mTab03);
                } else {
                    transaction.show(mTab03);
                }
                break;
            //我的
            case R.id.main_fourth:
                if (mTab04 == null) {
                    mTab04 = new FragmentMainFourth();
                    transaction.add(R.id.main_fl, mTab04);
                } else {
                    transaction.show(mTab04);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏fragment中的view
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
        if (mTab04 != null) {
            transaction.hide(mTab04);
        }
    }


    long outDate = 0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - outDate > 2000) {
            outDate = System.currentTimeMillis();
            AbToastUtil.showToast(this, "再按一次退出程序");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

