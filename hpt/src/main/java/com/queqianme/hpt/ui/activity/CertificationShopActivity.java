package com.queqianme.hpt.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.ab.util.AbToastUtil;
import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.APPUtils;
import com.queqianme.hpt.utils.ActivityCollector;
import com.queqianme.hpt.utils.Config;
import com.queqianme.hpt.utils.Utils;
import com.queqianme.hpt.view.CircleImageView;
import com.queqianme.hpt.view.CropImageActivity;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 店铺认证
 * Author:zhaojaiyu
 */
public class CertificationShopActivity extends BaseActivity {
    /**
     * 个人认证CheckBox
     */
    @Bind(R.id.register_persion_check)
    CheckBox checkPersion;
    /**
     * 企业认证CheckBox
     */
    @Bind(R.id.register_company_check)
    CheckBox checkCompany;
    /**
     * 提交认证
     */
    @Bind(R.id.shop_certification_btn)
    Button shopcertificationbtn;
    /**
     * 头像
     */
    @Bind(R.id.shop_certification_get_head)
    CircleImageView head;
    /**
     * 图片路径
     */
    private String currentFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_shop);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);


    }

    @OnClick({R.id.register_persion, R.id.register_company, R.id.shop_certification_btn, R.id.register_persion_check, R.id.register_company_check, R.id.shop_certification_get_head})
    public void shopClick(View view) {
        switch (view.getId()) {
            case R.id.register_persion://个人认证
                checkPersion.setChecked(true);
                checkCompany.setChecked(false);
                break;
            case R.id.register_company://企业认证
                checkPersion.setChecked(false);
                checkCompany.setChecked(true);
                break;
            case R.id.shop_certification_btn://提交
                if (checkPersion.isChecked() == true) {
                    Utils.intnet(this, PublishPersonActivity.class);
                } else if (checkCompany.isChecked() == true) {
                    Utils.intnet(this, PublishSchoolOrCompanyActivity.class);
                } else {
                    AbToastUtil.showToast(this, "请选择店铺规模");
                }
                break;
            //设置头像
            case R.id.shop_certification_get_head:
                APPUtils.getPicture(this);
                break;
        }
    }

    /**
     * 上传头像
     */
    private void sendImage(Bitmap bm) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] bytes = stream.toByteArray();
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));

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
//
//            case Config.PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
//                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常
//                if (data.getData() == null) {
//                    break;
//                }
//                Uri uri = data.getData();
//                currentFilePath = APPUtils.getPath(uri, this);
//                if (!AbStrUtil.isEmpty(currentFilePath)) {
//                    Intent intent1 = new Intent(this, CropImageActivity.class);
//                    intent1.putExtra("PATH", currentFilePath);
//                    startActivityForResult(intent1, Config.PHOTO_REQUEST_CUT);
//                } else {
//                    AbToastUtil.showToast(CertificationShopActivity.this, "未在存储卡中找到这个文件");
//                }
//                break;
//            case Config.PHOTO_REQUEST_CUT:// 返回的结果
//                if (APPUtils.chooseView != null) {
//                    AbDialogUtil.removeDialog(APPUtils.chooseView);
//                }
//                if (data != null) {
//                    Bitmap bitmap = data.getParcelableExtra("data");
//                    head.setImageBitmap(bitmap);
//                }
//                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
