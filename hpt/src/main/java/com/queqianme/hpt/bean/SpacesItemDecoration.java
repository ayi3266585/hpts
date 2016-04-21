package com.queqianme.hpt.bean;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 首页瀑布流间距
 * Created by zhaojiayu on 16/4/12.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space*2;
        if(parent.getChildAdapterPosition(view)==0||parent.getChildAdapterPosition(view)==1){
            outRect.top=space*2;
        }
    }
}
