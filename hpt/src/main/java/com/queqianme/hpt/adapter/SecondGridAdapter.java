package com.queqianme.hpt.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.queqianme.hpt.R;

import java.util.ArrayList;

/**
 * 热门推荐店铺列表适配器
 * Created by zhaojaiyu on 2016/1/22.
 */
public class SecondGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;

    public SecondGridAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_second_horizontal, null);
        return convertView;
    }
}
