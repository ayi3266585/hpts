package com.queqianme.hpt.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 聊天
 * Created by jerry on 2016/1/21.
 */
public class FragmentMainThird extends BaseFragment {
    private View view;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_third, container, false);
        init();
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        context = getActivity();
        ButterKnife.bind(this, view);
    }

    /**
     * 点击事件
     */
    @OnClick({})
    public void Click(View view) {
        switch (view.getId()) {
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
