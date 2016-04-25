package com.queqianme.hpt.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.queqianme.hpt.R;
import com.queqianme.hpt.adapter.widget.ArrayWheelAdapter;
import com.queqianme.hpt.view.widget.WheelView;

/**
 * 自定义滚轮弹窗
 * Created by zhaojiayu on 16/2/22.
 */
public class MyWheelView {
    private Context context;
    private View pop;
    private WheelViewGetData getData;
    private WheelView wheelView;
    private PopupWindow popCompensation;

    public interface WheelViewGetData{
        void getData(String data, int flag);
    }

    public MyWheelView(Context context, WheelViewGetData getData) {
        this.context = context;
        this.getData = getData;
    }

    public void getInstance(View view, final String[] list, String title, final int flag) {
        pop = LayoutInflater.from(context).inflate(R.layout.pop_wheel_view, null);
        ((TextView) pop.findViewById(R.id.pop_whell_title)).setText(title);
        wheelView = (WheelView) pop.findViewById(R.id.pop_whell_wheelview);
        wheelView.setVisibleItems(5);
        wheelView.setViewAdapter(new ArrayWheelAdapter<>(context, list));
        wheelView.setCurrentItem(0);
        (pop.findViewById(R.id.pop_whell_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popCompensation.dismiss();
            }
        });
        (pop.findViewById(R.id.pop_whell_sure)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData.getData(list[wheelView.getCurrentItem()],flag);
                popCompensation.dismiss();
            }
        });


        popCompensation = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popCompensation.setTouchable(true);
        popCompensation.setTouchInterceptor(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility") @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        popCompensation.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
        popCompensation.showAtLocation(view, Gravity.BOTTOM, 0, 0);

    }
}
