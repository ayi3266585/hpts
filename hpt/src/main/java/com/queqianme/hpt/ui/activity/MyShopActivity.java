package com.queqianme.hpt.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的店铺界面
 */
public class MyShopActivity extends BaseActivity {
    /**
     * gridview表
     */
    @Bind(R.id.my_shop_gridview)
    GridView gridView;
    /**
     * gridview图标
     */
    private int[] gdIcons = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    /**
     * gridview名称
     */
    private String[] gdNames = new String[]{"资金库", "新建商品", "x积分", "放款账单", "商品管理", "申请审核", "数据统计"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        initData();
        startAd();
    }

    private void initData() {
        MyGridView myGridView = new MyGridView(this,gdIcons,gdNames);
        gridView.setAdapter(myGridView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }

    /**
     * 广告位
     */
    private void startAd() {
//        viewPager.setNavHorizontalGravity(Gravity.CENTER);
//        viewPager.setPageLineImage(BitmapFactory.decodeResource(this.getResources(), R.mipmap.round1)
//                , BitmapFactory.decodeResource(this.getResources(), R.mipmap.round2));
//        final View mPlayView = LayoutInflater.from(this).inflate(R.layout.item_play_view1, null);
//        ImageView mPlayImage = (ImageView) mPlayView.findViewById(R.id.mPlayImage);
//        mPlayImage.setBackgroundResource(R.mipmap.head_icon100);
//
//        final View mPlayView1 = LayoutInflater.from(this).inflate(R.layout.item_play_view1, null);
//        ImageView mPlayImage1 = (ImageView) mPlayView1.findViewById(R.id.mPlayImage);
//        mPlayImage1.setBackgroundResource(R.mipmap.head_icon100);
//
//        final View mPlayView2 = LayoutInflater.from(this).inflate(R.layout.item_play_view1, null);
//        ImageView mPlayImage2 = (ImageView) mPlayView2.findViewById(R.id.mPlayImage);
//        mPlayImage2.setBackgroundResource(R.mipmap.head_icon100);
//
//        viewPager.addView(mPlayView);
//        viewPager.addView(mPlayView1);
//        viewPager.addView(mPlayView2);
//
//        viewPager.startPlay();
    }

    class MyGridView extends BaseAdapter {

        private Context context;
        /**
         * gridview图标
         */
        private int[] gdIcons;
        /**
         * gridview名称
         */
        private String[] gdNames;

        public MyGridView(Context context,int[] gdIcons,String[] gdNames) {
            this.context = context;
            this.gdIcons = gdIcons;
            this.gdNames = gdNames;
        }

        @Override
        public int getCount() {
            return gdIcons.length;
        }

        @Override
        public Object getItem(int position) {
            return gdIcons[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_my_shop_gridview, null);
                viewHolder.gdIcon = (ImageView) convertView.findViewById(R.id.my_shop_gridview_icon);
                viewHolder.gdName = (TextView) convertView.findViewById(R.id.my_shop_gridview_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.gdIcon.setImageDrawable(getResources().getDrawable(gdIcons[position]));
            viewHolder.gdName.setText(gdNames[position]);


            return convertView;
        }

        class ViewHolder {
            ImageView gdIcon;
            TextView gdName;
        }
    }
}
