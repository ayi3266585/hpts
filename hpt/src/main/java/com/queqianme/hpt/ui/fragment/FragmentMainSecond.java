package com.queqianme.hpt.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.queqianme.hpt.R;
import com.queqianme.hpt.adapter.LoanListAdapter;
import com.queqianme.hpt.bean.BaseFragment;
import com.queqianme.hpt.bean.OnRecyclerItemCLickListener;
import com.queqianme.hpt.bean.SpacesItemDecoration;
import com.queqianme.hpt.model.InvestVO;
import com.queqianme.hpt.ui.activity.LoanUserInfoActivity;
import com.queqianme.hpt.ui.activity.LoaningActivity;
import com.queqianme.hpt.ui.activity.PublishActivity;
import com.queqianme.hpt.utils.HttpURL;
import com.queqianme.hpt.utils.LogUtils;
import com.queqianme.hpt.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * 投资页
 * Created by jerry on 2016/1/21.
 */
public class FragmentMainSecond extends BaseFragment {
    private static final int COUNT = 20;
    private static final int PAGE = 1;

    @Bind(R.id.second_recycler)
    RecyclerView secondRecycler;
    private Context context;
    private View view;
    private LoanListAdapter adapter;
    /**
     * 请求参数体
     */
    private RequestParams params;
    /**
     * 请求返回JSON
     */
    private JSONObject responseJSON = null;
    /**
     * 借款列表
     */
    private ArrayList<InvestVO> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_second, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }


    /**
     * 初始化数据
     */
    private void init() {
        context = getActivity();
        AbDialogUtil.showProgressDialog(context, 0, "加载中");
        params = new RequestParams(this);
        list = new ArrayList<>();
        /*获取投资列表*/
        getInvestList();

        //设置layoutManager
        secondRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL) );
        //设置adapter
        adapter = new LoanListAdapter(list);
        secondRecycler.setAdapter(adapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration=new SpacesItemDecoration((int) getResources().getDimension(R.dimen.x10));
        secondRecycler.addItemDecoration(decoration);
        adapter.setOnItemClickListener(new OnRecyclerItemCLickListener() {
            @Override
            public void onItemClick(View view, int flag) {
                switch (flag) {
                    case 1:
                        Utils.intnet(context, LoaningActivity.class);
                        break;
                    case 2:
                        Utils.intnet(context, LoanUserInfoActivity.class);
                        break;

                }
            }
        });

    }

    /**
     * 获取投资列表
     */
    private void getInvestList() {
        JSONObject object = new JSONObject();
        try {
            object.put("count", COUNT);
            object.put("page", PAGE);
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.investList, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                LogUtils.i(s);
                AbDialogUtil.removeDialog(context);
                try {
                    responseJSON = new JSONObject(s);
                    int status = responseJSON.getInt("status");
                    switch (status) {
                        case 0:
                            JSONArray infos = responseJSON.getJSONArray("infos");
                            for (int i = 0;i<infos.length();i++) {
                                JSONObject voObject = infos.getJSONObject(i);
                                InvestVO vo = new InvestVO();
                                vo.setBillId(voObject.getLong("billd"));
                                vo.setBillStatus(voObject.getString("billStatus"));
                                vo.setTitle(voObject.getString("title"));
                                vo.setReason(voObject.getString("reason"));
                                vo.setMoney(voObject.getInt("money"));
                                vo.setDays(voObject.getInt("days"));
                                vo.setInterest(voObject.getInt("interest"));
                                vo.setAvatar(voObject.getString("avatar"));
                                vo.setGender(voObject.getInt("gender"));
                                list.add(vo);
                            }
                            adapter.notifyDataSetChanged();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbDialogUtil.removeDialog(context);
                AbToastUtil.showToast(context, R.string.http_failure);
            }

        });
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.publish})
    public void Click(View view) {
        switch (view.getId()) {
            //发布
            case R.id.publish:
                Utils.intnet(context, PublishActivity.class);
                break;
        }
    }


    /**
     * 销毁Fragment
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
