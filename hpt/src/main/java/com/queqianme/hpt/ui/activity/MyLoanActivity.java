package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.queqianme.hpt.R;
import com.queqianme.hpt.adapter.LoanListAdapter;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.model.InvestVO;
import com.queqianme.hpt.utils.ActivityCollector;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的投资
 */
public class MyLoanActivity extends BaseActivity {

    @Bind(R.id.my_loan_recycler)
    RecyclerView secondRecycler;
    private LoanListAdapter adapter;
    /**
     * 借款列表
     */
    private ArrayList<InvestVO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_loan);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        init();
    }

    private void init() {

        //设置layoutManager
        secondRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL) );
        //设置adapter
        adapter = new LoanListAdapter(list);
        secondRecycler.setAdapter(adapter);
        //设置item之间的间隔

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
