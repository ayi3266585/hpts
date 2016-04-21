package com.queqianme.hpt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.OnRecyclerItemCLickListener;
import com.queqianme.hpt.model.InvestVO;

import java.util.List;

/**
 * 首页RecyclerView适配器
 * Created by zhaojiayu on 16/3/23.
 */
public class LoanListAdapter extends RecyclerView.Adapter<LoanListAdapter.ViewHolder> implements View.OnClickListener {
    /*Item点击标记*/
    public static int ITEM_CLICK = 1;
    /*View点击标记*/
    public static int VIEW_CLICK = 2;
    /*数据列表*/
    private List<InvestVO> list;

    /*构造函数*/
    public LoanListAdapter(List<InvestVO> list) {
        this.list = list;
    }

    /*点击事件接口*/
    private static OnRecyclerItemCLickListener mOnRecyclerClick = null;

    /*自定义布局*/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loan_list, parent, false);
        //注册Item点击事件
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    /*绑定数据*/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(list.get(position).getReason());
    }

    /*获取数据数量*/
    @Override
    public int getItemCount() {
        return list.size();
    }

    /*点击事件*/
    @Override
    public void onClick(View v) {
        if (mOnRecyclerClick != null) {
            mOnRecyclerClick.onItemClick(v, ITEM_CLICK);
        }
    }

    /*点击事件设置*/
    public void setOnItemClickListener(OnRecyclerItemCLickListener listener) {
        this.mOnRecyclerClick = listener;
    }

    /*ViewHolder初始化控件,持有每个Item的所有界面元素*/
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView head;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.loan_list_tv);
            head = (ImageView) itemView.findViewById(R.id.loan_list_head);
            //控件点击事件
            head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecyclerClick.onItemClick(v, VIEW_CLICK);
                }
            });
        }

    }
}
