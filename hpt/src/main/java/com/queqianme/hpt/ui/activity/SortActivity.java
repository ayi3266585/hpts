package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.queqianme.hpt.R;
import com.queqianme.hpt.adapter.ListDropDownAdapter;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.view.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 分类界面
 */
public class SortActivity extends BaseActivity {
    /**
     * 自定义下拉弹窗
     */
    @Bind(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    /**
     * 下拉Listview列表
     */
    private List<View> popupViews = new ArrayList<View>();
    private ListDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;

    private String headers[] = {"类型", "金额", "利息"};
    private String stype[] = {"类型不限", "个人", "企业"};
    private String money[] = {"金额不限", "1000以下", "1000-2000元", "3000-4000元", "4000-5000元", "5000以上"};
    private String interest[] = {"利息不限", "0%-3%", "3%-6%", "6%-10%", "10%以上"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        initView();
    }

    private void initView() {
        //类型列表
        final ListView stypeView = new ListView(this);
        cityAdapter = new ListDropDownAdapter(this, Arrays.asList(stype));
        stypeView.setDividerHeight(0);
        stypeView.setAdapter(cityAdapter);

        //金额列表
        final ListView moneyView = new ListView(this);
        moneyView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(money));
        moneyView.setAdapter(ageAdapter);

        //利息列表
        final ListView interestView = new ListView(this);
        interestView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(this, Arrays.asList(interest));
        interestView.setAdapter(sexAdapter);

        //popupViews
        popupViews.add(stypeView);
        popupViews.add(moneyView);
        popupViews.add(interestView);

        //item 点击事件
        stypeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : stype[position]);
                mDropDownMenu.closeMenu();
            }
        });

        moneyView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : money[position]);
                mDropDownMenu.closeMenu();
            }
        });

        interestView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : interest[position]);
                mDropDownMenu.closeMenu();
            }
        });

        //内容显示区域
        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("暂无数据");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        //显示 dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
