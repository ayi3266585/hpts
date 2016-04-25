package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.queqianme.hpt.R;
import com.queqianme.hpt.adapter.LoanListAdapter;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.base.OnRecyclerItemCLickListener;
import com.queqianme.hpt.model.InvestVO;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的借条,发布中
 */
public class MyLoanPublishActivity extends BaseActivity {
    /**
     * 列表
     */
    @Bind(R.id.my_loan_publish_recycler)
    RecyclerView mRecycler;
    /**
     * 数据列表
     */
    private ArrayList<InvestVO> list;
    private LoanListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_loan_publish);
        init();
    }

    public void init() {
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        list = new ArrayList<>();
        //设置layoutManager
        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL) );
        //设置adapter
        adapter = new LoanListAdapter(list);
        mRecycler.setAdapter(adapter);
        //设置item之间的间隔(decoration);
        adapter.setOnItemClickListener(new OnRecyclerItemCLickListener() {
            @Override
            public void onItemClick(View view, int flag) {
                switch (flag) {
                    case 1:
                        Utils.intnet(MyLoanPublishActivity.this, LoaningActivity.class);
                        break;
                    case 2:
                        Utils.intnet(MyLoanPublishActivity.this, LoanUserInfoActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
