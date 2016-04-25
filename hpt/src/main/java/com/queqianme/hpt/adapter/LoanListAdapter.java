package com.queqianme.hpt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.OnRecyclerItemCLickListener;
import com.queqianme.hpt.model.InvestVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private int[] heads = new int[]{R.mipmap.apply_bg1,R.mipmap.apply_bg2,R.mipmap.apply_bg3};

    /*构造函数*/
    public LoanListAdapter(List<InvestVO> list) {
//        this.list = list;
        this.list = new ArrayList<>();
        for (int i = 0;i<7;i++) {
            InvestVO investVO = new InvestVO();
            this.list.add(investVO);
        }
        this.list.get(0).setReason("爽肤水的粉丝的粉色发是粉色发斯蒂芬爽肤水的范德萨水电费爽肤水");
        this.list.get(1).setReason("爽肤水的粉丝的粉色发是粉色发斯蒂芬爽肤21312水的范德萨水电费爽肤水");
        this.list.get(2).setReason("爽肤水的粉丝的粉色发是粉色发斯蒂芬爽肤水的范德萨水电费爽肤水");
        this.list.get(3).setReason("爽肤色发斯蒂芬爽肤水的范德萨水电费爽肤水");
        this.list.get(4).setReason("爽肤水的粉丝的粉色发是粉色发斯蒂芬爽肤水的范德萨水电费爽肤水");
        this.list.get(6).setReason("爽肤的粉色发是粉色发斯蒂芬爽水");
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

//        holder.tv.setText(new Random().nextInt(999999999)+"RecyclerViewRecyclerView钱吃饭了");
        holder.head_bg.setImageResource(heads[new Random().nextInt(3)]);
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
        ImageView head_bg;
        ImageView head;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.loan_list_tv);
            head = (ImageView) itemView.findViewById(R.id.loan_list_head);
            head_bg = (ImageView) itemView.findViewById(R.id.loan_list_head_bg);
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
