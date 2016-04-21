package com.queqianme.hpt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ab.util.AbToastUtil;
import com.queqianme.hpt.R;
import com.queqianme.hpt.adapter.MessageListAdapter;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.bean.OnRecyclerItemCLickListener;
import com.queqianme.hpt.bean.RecycleViewDivider;
import com.queqianme.hpt.model.MessageVO;
import com.queqianme.hpt.utils.ActivityCollector;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * 消息中心
 */
public class MessageListActivity extends BaseActivity {

    /**
     * 请求参数体
     */
    private RequestParams params;
    /**
     * 请求返回JSON
     */
    private JSONObject responseJSON = null;
    @Bind(R.id.message_listview)
    RecyclerView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        params = new RequestParams(this);

        ArrayList<MessageVO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MessageVO messageVO = new MessageVO();
            list.add(messageVO);
        }
        // 创建一个线性布局管理器
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        MessageListAdapter adapter = new MessageListAdapter(list);
        listview.setAdapter(adapter);
        listview.setLayoutManager(mLayoutManager);
        //RecyclerView分割线
        listview.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gray)));

        adapter.setOnItemClickListener(new OnRecyclerItemCLickListener() {
            @Override
            public void onItemClick(View view, int flag) {
                switch (flag) {
                    case 1:
                        Intent intent = new Intent(MessageListActivity.this,WebActivity.class);
                        intent.putExtra("title", "消息内容");
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.message_delete})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.message_delete:
                AbToastUtil.showToast(this, "删除");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }

}
