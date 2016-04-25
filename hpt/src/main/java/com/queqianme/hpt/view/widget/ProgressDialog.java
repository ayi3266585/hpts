package com.queqianme.hpt.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.queqianme.hpt.R;
import com.queqianme.hpt.view.WhorlView;

/**
 * 自定义progress
 * Created by zhaojiayu on 16/4/24.
 */
public class ProgressDialog {
    private final Display display;
    private Context context;
    private String content;
    private WhorlView whorlview;
    private TextView text;
    private Dialog dialog;
    private LinearLayout bg;

    public ProgressDialog(Context context, String content) {
        this.context = context;
        this.content = content;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ProgressDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_progress_dialog, null);

        // 获取自定义Dialog布局中的控件
        whorlview = (WhorlView) view.findViewById(R.id.whorlview);
        bg = (LinearLayout) view.findViewById(R.id.dialog_bg);
        text = (TextView) view.findViewById(R.id.dialog_text);

        // 定义Dialog布局和参数
        dialog = new Dialog(context,R.style.add_dialog);
        dialog.setContentView(view);

        // 调整dialog背景大小
        dialog.setContentView(bg,new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        setCancelable(true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                whorlview.stop();
            }
        });
        return this;
    }


    public ProgressDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ProgressDialog setMsg(String text) {
        content = text;
        return this;
    }

    public void showDialog() {
        setLayout();
        dialog.show();
        whorlview.start();
    }



    private void setLayout() {
        if (!"".equals(content)) {
            text.setText(content);
        }
    }

    public void dismiss() {
        whorlview.stop();
        dialog.dismiss();
    }
}
