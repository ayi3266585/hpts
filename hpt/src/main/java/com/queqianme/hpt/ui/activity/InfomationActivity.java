package com.queqianme.hpt.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.queqianme.hpt.R;
import com.queqianme.hpt.bean.BaseActivity;
import com.queqianme.hpt.utils.APPUtils;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Config;
import com.queqianme.hpt.utils.HttpURL;
import com.queqianme.hpt.utils.SPUtils;
import com.queqianme.hpt.utils.Utils;
import com.queqianme.hpt.view.AlertDialog;
import com.queqianme.hpt.view.CircleImageView;
import com.queqianme.hpt.view.CropImageActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * 个人信息界面
 */
public class InfomationActivity extends BaseActivity {
    /**
     * 图片路径
     */
    private String currentFilePath;
    /**
     * 头像
     */
    @Bind(R.id.infomation_head)
    CircleImageView head;
    /**
     * 昵称
     */
    @Bind(R.id.nick_name)
    TextView nickName;
    /**
     * 手机号
     */
    @Bind(R.id.login_phone)
    TextView phone;
    private RequestParams params;
    private JSONObject responseJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        params = new RequestParams(this);

        nickName.setText(SPUtils.get(this, "name", "") + "");
        phone.setText(SPUtils.get(this, "phone", "") + "");
        String iconUrl = (String) SPUtils.get(this, "icon", "");
        ImageLoader.getInstance().displayImage(iconUrl, head, options);

    }


    @OnClick({R.id.linearLayout1, R.id.linearLayout2, R.id.linearLayout4, R.id.login_out_btn})
    public void Click(View view) {
        switch (view.getId()) {
            //设置头像
            case R.id.linearLayout1:
                APPUtils.getPicture(this);
                break;
            //修改昵称
            case R.id.linearLayout2:
                Utils.intnet(this, ChangeNameActivity.class);
                break;
            //修改登录密码
            case R.id.linearLayout4:
                Utils.intnet(this, ChangePasswordActivity.class);
                break;
            //登出
            case R.id.login_out_btn:
                new AlertDialog(this).builder().setMsg("您确定要退出当前账号吗?")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SPUtils.remove(InfomationActivity.this, "password");
                                logout();
                                ActivityCollector.finishAll();
                                Utils.intnet(InfomationActivity.this, LoginActivity.class);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
        }
    }

    /**
     * 注销
     */
    private void logout() {
        JSONObject object = new JSONObject();
        try {
            object.put("userId", SPUtils.get(this, "userId", 0L));
            object.put("token", SPUtils.get(this, "token", ""));
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.logout, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
            }

            @Override
            public void onFailure(int errorCode, String msg) {
            }
        });
    }

    /**
     * 上传头像
     */
    private void sendImage(Bitmap bm) {
        AbDialogUtil.showProgressDialog(this, 0, "上传中...");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] bytes = stream.toByteArray();
        String icon = new String(Base64.encodeToString(bytes, Base64.DEFAULT));

        JSONObject object = new JSONObject();
        try {
            object.put("userId", SPUtils.get(this, "userId", 0L));
            object.put("token", SPUtils.get(this, "token", ""));
            object.put("icon", icon);
            params.setRequestBodyString(object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpRequest.post(HttpURL.uploadIcon, params, new BaseHttpRequestCallback<String>() {
            @Override
            protected void onSuccess(String s) {
                try {
                    responseJSON = new JSONObject(s);
                    int status = responseJSON.getInt("status");
                    switch (status) {
                        case 0:
                            SPUtils.put(InfomationActivity.this, "icon", responseJSON.getString("icon"));
                            AbToastUtil.showToast(InfomationActivity.this, "上传成功");
                            break;
                        default:
                            AbToastUtil.showToast(InfomationActivity.this, responseJSON.getString("des"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AbDialogUtil.removeDialog(InfomationActivity.this);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                AbDialogUtil.removeDialog(InfomationActivity.this);
                AbToastUtil.showToast(InfomationActivity.this, R.string.http_failure);
            }
        });
    }

    /**
     * 调用系统工具返回
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Config.PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
                if (Config.tempFile.getPath() == null) {
                    break;
                }
                currentFilePath = Config.tempFile.getPath();
                Intent intent2 = new Intent(this, CropImageActivity.class);
                intent2.putExtra("PATH", currentFilePath);
                startActivityForResult(intent2, Config.PHOTO_REQUEST_CUT);
                break;

            case Config.PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常
                if (data.getData() == null) {
                    break;
                }
                Uri uri = data.getData();
                currentFilePath = APPUtils.getPath(uri, this);
                if (!AbStrUtil.isEmpty(currentFilePath)) {
                    Intent intent1 = new Intent(this, CropImageActivity.class);
                    intent1.putExtra("PATH", currentFilePath);
                    startActivityForResult(intent1, Config.PHOTO_REQUEST_CUT);
                } else {
                    AbToastUtil.showToast(InfomationActivity.this, "未在存储卡中找到这个文件");
                }
                break;
            case Config.PHOTO_REQUEST_CUT:// 返回的结果
                if (APPUtils.chooseView != null) {
                    AbDialogUtil.removeDialog(APPUtils.chooseView);
                }
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    head.setImageBitmap(bitmap);
                    sendImage(bitmap);
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        nickName.setText(SPUtils.get(this, "name", "") + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
