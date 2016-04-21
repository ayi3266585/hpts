package com.queqianme.hpt.view;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.queqianme.hpt.R;

/**
 * 自定义标题返回键
 *
 * @author Jerry Zhao
 */
public class TitleBackView extends RelativeLayout {

    private final TextView titleText;

    public TitleBackView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_title_back, this);

        RelativeLayout titleBack = (RelativeLayout) findViewById(R.id.title_back);
        titleText = (TextView) findViewById(R.id.title_text);

        titleBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ((Activity) getContext()).onBackPressed();
            }
        });
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBackView);
            String title = ta.getString(R.styleable.TitleBackView_titleText);
            titleText.setText(title);
        }
    }

    public void setText(String text) {
        titleText.setText(text);
    }




}
