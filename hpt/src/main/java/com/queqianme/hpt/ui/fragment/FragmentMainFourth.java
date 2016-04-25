package com.queqianme.hpt.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ab.util.AbToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseFragment;
import com.queqianme.hpt.ui.activity.BankCardActivity;
import com.queqianme.hpt.ui.activity.IdAuthActivity;
import com.queqianme.hpt.ui.activity.InfomationActivity;
import com.queqianme.hpt.ui.activity.MessageListActivity;
import com.queqianme.hpt.ui.activity.MyLoanActivity;
import com.queqianme.hpt.ui.activity.MyLoanOverActivity;
import com.queqianme.hpt.ui.activity.MyLoanPublishActivity;
import com.queqianme.hpt.ui.activity.MyLoanRepayActivity;
import com.queqianme.hpt.ui.activity.SettingAboutActivity;
import com.queqianme.hpt.ui.activity.SettingActivity;
import com.queqianme.hpt.utils.SPUtils;
import com.queqianme.hpt.utils.Utils;
import com.queqianme.hpt.view.AlertDialog;
import com.queqianme.hpt.view.CircleImageView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * 个人中心
 * Created by jerry on 2016/1/21.
 */
public class FragmentMainFourth extends BaseFragment {
    /**
     * 头像
     */
    @Bind(R.id.fragment_fourth_img)
    CircleImageView head;
    /**
     * 昵称
     */
    @Bind(R.id.fourth_title)
    TextView name;
    /**
     * 请求参数体
     */
    private RequestParams params;
    /**
     * 请求返回JSON
     */
    private JSONObject responseJSON = null;
    private View view;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_fourth, container, false);
        init();
        return view;
    }

    /**
     * 初始化
     */
    private void init() {
        context = getActivity();
        params = new RequestParams(this);
        ButterKnife.bind(this, view);
        String iconUrl = (String) SPUtils.get(context, "icon", "");
        if (iconUrl != "") {
            ImageLoader.getInstance().displayImage(iconUrl, head, options);
            name.setText(SPUtils.get(context, "name", "") + "");
        } else {
            head.setImageResource(R.mipmap.head_icon140);
        }
        if (SPUtils.get(context, "name", "") != "")
            name.setText(SPUtils.get(context, "name", "") + "");

    }

    /**
     * 点击事件
     */
    @OnClick({R.id.head_setting, R.id.my_invest, R.id.id_auth, R.id.bank,
            R.id.loan_data, R.id.message, R.id.setting,R.id.about,R.id.my_loan_publish,
            R.id.my_loan_repay,R.id.my_loan_over})
    public void Click(View view) {
        AbToastUtil.showToast(context, "未登录");
        switch (view.getId()) {
            //个人信息
            case R.id.head_setting:
                Utils.intnet(getActivity(), InfomationActivity.class);
                break;
            //我的借条,发布中
            case R.id.my_loan_publish:
                Utils.intnet(getActivity(), MyLoanPublishActivity.class);
                break;
            //我的借条,还款中
            case R.id.my_loan_repay:
                Utils.intnet(getActivity(), MyLoanRepayActivity.class);
                break;
            //我的借条,已完成
            case R.id.my_loan_over:
                Utils.intnet(getActivity(), MyLoanOverActivity.class);
                break;
            //我的投资
            case R.id.my_invest:
                Utils.intnet(getActivity(), MyLoanActivity.class);
                break;
            //实名认证
            case R.id.id_auth:
                Utils.intnet(getActivity(), IdAuthActivity.class);
                break;
            //银行卡列表
            case R.id.bank:
                new AlertDialog(context).builder().setMsg("\n实名认证后才能添加银行卡\n")
                        .setPositiveButton("实名认证", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Utils.intnet(getActivity(), BankCardActivity.class);
                            }
                        })
                        .setNegativeButton("     取消     ", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
                break;
            //借款资料
            case R.id.loan_data:
//                Utils.intnet(getActivity(), BillActivity.class);
                break;
            //我的信息
            case R.id.message:
                Utils.intnet(getActivity(), MessageListActivity.class);
                break;
            //设置
            case R.id.setting:
                Utils.intnet(getActivity(), SettingActivity.class);
                break;
            //设置
            case R.id.about:
                Utils.intnet(getActivity(), SettingAboutActivity.class);
                break;
        }
    }


    @Override
    public void onStart() {
        super.onResume();
        String iconUrl = (String) SPUtils.get(context, "icon", "");
        if (iconUrl != "") {
            ImageLoader.getInstance().displayImage(iconUrl, head, options);
            name.setText(SPUtils.get(context, "name", "") + "");
        } else {
            head.setImageResource(R.mipmap.head_icon140);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
