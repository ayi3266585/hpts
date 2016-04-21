package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.queqianme.hpt.R;
import com.queqianme.hpt.adapter.DatailShopAdapter;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Utils;
import com.queqianme.hpt.view.PullToRefreshView;
import com.queqianme.hpt.view.ScrollListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 店铺详情界面
 * Author:zhaojaiyu
 */
public class DetailShopActivity extends BaseActivity implements
        PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
    /**
     * 商品列表
     */
    @Bind(R.id.detail_shop_listview)
    ScrollListView listview;
    /**
     * 上拉 下拉控件
     */
    @Bind(R.id.mPullRefreshView)
    ScrollView scrollView;

    /**
     * 商品列表数据
     */
    private ArrayList<String> list;
    private DatailShopAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shop);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        initData();

    }

    @OnClick({R.id.detail_shop_rl1})
    public void Click(View view) {
        switch (view.getId()) {
            //店铺详细信息
            case R.id.detail_shop_rl1:
                Utils.intnet(this,LoanUserInfoActivity.class);
                break;
        }
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add("dd"+i);
        }
        adapter = new DatailShopAdapter(this, list);
        listview.setAdapter(adapter);
        scrollView.smoothScrollTo(0,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        //TODO 上拉加载
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        //TODO 下拉刷新
        scrollView.smoothScrollTo(0,0);
    }
}
