package com.queqianme.hpt.ui.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ab.view.sliding.AbSlidingPlayView;
import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.BaseFragment;
import com.queqianme.hpt.view.PrograssView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目首页
 * Created by jerry on 2016/1/21.
 */
public class FragmentMainFirst extends BaseFragment {
    /**
     * 自定义圆环进度条
     */
    @Bind(R.id.first_prograssview)
    PrograssView mPrograssview;
    /**
     * 广告位
     */
    @Bind(R.id.frist_ad)
    AbSlidingPlayView viewPager;
    /**
     * 圆环最大值
     */
    private int max = 100;
    /**
     * 圆环进度值
     */
    private int progress = 0;
    private View view;

    private Handler mUpdateHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    startAddProgress();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_frist, container, false);
        ButterKnife.bind(this, view);
        startAddProgress();
        startAd();
        return view;
    }


    /**
     * 广告位
     */
    private void startAd() {
        viewPager.setNavHorizontalGravity(Gravity.CENTER);
        viewPager.setPageLineImage(BitmapFactory.decodeResource(this.getResources(), R.mipmap.round1)
                , BitmapFactory.decodeResource(this.getResources(), R.mipmap.round2));
        final View mPlayView = LayoutInflater.from(getActivity()).inflate(R.layout.item_play_view1, null);
        ImageView mPlayImage = (ImageView) mPlayView.findViewById(R.id.mPlayImage);
        mPlayImage.setBackgroundResource(R.mipmap.head_icon100);

        final View mPlayView1 = LayoutInflater.from(getActivity()).inflate(R.layout.item_play_view1, null);
        ImageView mPlayImage1 = (ImageView) mPlayView1.findViewById(R.id.mPlayImage);
        mPlayImage1.setBackgroundResource(R.mipmap.head_icon100);

        final View mPlayView2 = LayoutInflater.from(getActivity()).inflate(R.layout.item_play_view1, null);
        ImageView mPlayImage2 = (ImageView) mPlayView2.findViewById(R.id.mPlayImage);
        mPlayImage2.setBackgroundResource(R.mipmap.head_icon100);

        viewPager.addView(mPlayView);
        viewPager.addView(mPlayView1);
        viewPager.addView(mPlayView2);

        viewPager.startPlay();
    }

    /**
     * 圆环进度
     */
    private void startAddProgress() {
        mPrograssview.setMax(max);
        mPrograssview.setProgress(progress);
        progress = progress + 1;
        mPrograssview.setProgress(progress);
        if (progress < 80) {
            mUpdateHandler.sendEmptyMessageDelayed(1, 30);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mUpdateHandler.removeMessages(1);
    }
}
