package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ab.view.pullview.AbPullToRefreshView;
import com.queqianme.hpt.R;
import com.queqianme.hpt.adapter.SecondRecommendAdapter;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页推荐列表
 */
public class MoreRecommendActivity extends BaseActivity implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    /**
     * 店铺列表
     */
    @Bind(R.id.listview)
    ListView listview;
    /**
     * 下拉刷新,上拉加载
     */
    @Bind(R.id.mPullRefreshView)
    AbPullToRefreshView mPullRefreshView;

    /**
     * 店铺列表
     */
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_recommend);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        initData();
    }

    private void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("a"+i);
        }
        SecondRecommendAdapter adapter = new SecondRecommendAdapter(this, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.intnet(MoreRecommendActivity.this, DetailShopActivity.class);
            }
        });
    }

    /**
     * 上拉加载更多
     * @param abPullToRefreshView
     */
    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {

    }

    /**
     * 下拉刷新
     * @param abPullToRefreshView
     */
    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
