package com.queqianme.hpt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.OnRecyclerItemCLickListener;
import com.queqianme.hpt.model.MessageVO;

import java.util.List;

/**
 * 消息中心适配器
 * Created by zhaojaiyu on 2016/1/22.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> implements View.OnClickListener {
    /*Item点击标记*/
    public static int ITEM_CLICK = 1;
    private List<MessageVO> list;
    /*点击事件接口*/
    private static OnRecyclerItemCLickListener mOnRecyclerClick = null;

    public MessageListAdapter(List<MessageVO> list) {
        this.list = list;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_list,parent,false);
        //注册Item点击事件
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*点击事件设置*/
    public void setOnItemClickListener(OnRecyclerItemCLickListener listener) {
        this.mOnRecyclerClick = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnRecyclerClick != null) {
            mOnRecyclerClick.onItemClick(v, ITEM_CLICK);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
