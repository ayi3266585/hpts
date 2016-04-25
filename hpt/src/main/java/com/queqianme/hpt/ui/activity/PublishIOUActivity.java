package com.queqianme.hpt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.controller.MyWheelView;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Utils;
import com.queqianme.hpt.view.AlertDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布借条
 * Author:zhaojaiyu
 */
public class PublishIOUActivity extends BaseActivity implements MyWheelView.WheelViewGetData {
    /**
     * 借多少钱
     */
    @Bind(R.id.iou_setMoney)
    TextView setMoney;
    /**
     * 借多久
     */
    @Bind(R.id.iou_setRefundLong)
    TextView setRefundLong;
    /**
     * 多少利息
     */
    @Bind(R.id.iou_setInterestList)
    TextView setInterestList;
    /**
     * 弹窗
     */
    private MyWheelView wheelView;

    private String[] moneys = new String[]{"2000", "1600", "1200", "800", "400", "200"};
    private String[] refundLongs = new String[]{"30天(1期)", "60天(2期)", "90天(3期)", "180天(6期)"};
    private String[] interestLists = new String[]{"700", "600", "500", "400", "300", "200"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_iou);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        wheelView = new MyWheelView(this, this);
    }

    @OnClick({R.id.iou_getMoney, R.id.iou_getRefundLong, R.id.iou_getInterestList,R.id.iou_submit})
    public void click(View view) {
        switch (view.getId()) {
            //选择接多少
            case R.id.iou_getMoney:
                wheelView.getInstance(view, moneys, "", 1);
                break;
            //选择借多久
            case R.id.iou_getRefundLong:
                wheelView.getInstance(view, refundLongs, "", 2);
                break;
            //选择利息
            case R.id.iou_getInterestList:
                wheelView.getInstance(view, interestLists, "", 3);
                break;
            //下一步
            case R.id.iou_submit:
                Utils.intnet(this,PublishActivity.class);
                break;

        }
    }

    @Override
    public void getData(String data, int flag) {
        switch (flag) {
            case 1://多少钱
                setMoney.setText(data);
                break;
            case 2://借多久
                setRefundLong.setText(data);
                break;
            case 3://利息
                setInterestList.setText(data);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog(this).builder().setMsg("借条还没有完成,确定退出吗?")
                .setPositiveButton("  退出  ", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PublishIOUActivity.this.finish();
                    }
                })
                .setNegativeButton("我在想想", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
