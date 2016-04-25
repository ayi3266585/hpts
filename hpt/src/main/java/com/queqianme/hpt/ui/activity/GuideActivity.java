package com.queqianme.hpt.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ab.view.sliding.AbSlidingPlayView;
import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首次进入ViewPage广告
 */
public class GuideActivity extends BaseActivity {
    private SharedPreferences settings;
    private SharedPreferences.Editor localEditor;

    @Bind(R.id.abSlidingPlayView)
    AbSlidingPlayView viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);


        settings = this.getSharedPreferences("login", 0);
        localEditor = settings.edit();
        initView();
    }

    private void initView() {
        viewPager.setNavHorizontalGravity(Gravity.CENTER);
        viewPager.setPageLineImage(null,null);
        final View mPlayView = mInflater.inflate(R.layout.item_play_view1, null);
        ImageView mPlayImage = (ImageView) mPlayView.findViewById(R.id.mPlayImage);
        mPlayImage.setBackgroundResource(R.mipmap.head_icon100);

        final View mPlayView1 = mInflater.inflate(R.layout.item_play_view1, null);
        ImageView mPlayImage1 = (ImageView) mPlayView1.findViewById(R.id.mPlayImage);
        mPlayImage1.setBackgroundResource(R.mipmap.head_icon100);

        final View mPlayView2 = mInflater.inflate(R.layout.item_play_view2, null);
        ImageView mPlayImage2 = (ImageView) mPlayView2.findViewById(R.id.mPlayImage);
        mPlayImage2.setBackgroundResource(R.mipmap.head_icon100);

        viewPager.addView(mPlayView);
        viewPager.addView(mPlayView1);
        viewPager.addView(mPlayView2);

        Button next = (Button) (mPlayView2).findViewById(R.id.guide_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Utils.intnet(GuideActivity.this, MainActivity.class);
                finish();
            }
        });

        viewPager.setOnPageChangeListener(new AbSlidingPlayView.AbOnChangeListener() {
            @Override
            public void onChange(int position) {
                if (position == 2) {
                    localEditor.putBoolean("isFirstIn", false);
                    localEditor.commit();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


}
