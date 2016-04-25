package com.queqianme.hpt.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.queqianme.hpt.R;
import com.queqianme.hpt.base.BaseActivity;
import com.queqianme.hpt.utils.ActivityCollector;

import java.io.File;
import java.io.FileInputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 上传照片认证信息
 */
public class PublishPictureActivity extends BaseActivity {
    private int FLAG = 1;
    /**
     * 身份证正面
     */
    @Bind(R.id.picture_from)
    ImageView from;
    /**
     * 身份证反面
     */
    @Bind(R.id.picture_back)
    ImageView back;
    /**
     * 账单添加按钮
     */
    @Bind(R.id.recentBills_add)
    ImageView recentBills_add;
    /**
     * 生活照添加按钮
     */
    @Bind(R.id.recentPersonals_add)
    ImageView recentPersonals_add;
    /**
     * 账单图片容器
     */
    @Bind(R.id.picture_horizontal_recentBills)
    LinearLayout recentBills;
    /**
     * 生活照图片容器
     */
    @Bind(R.id.picture_horizontal_recentPersonals)
    LinearLayout recentPersonals;
    /**
     * 屏幕宽
     */
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_picture);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        //获取屏幕宽度
        WindowManager wm = this.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
    }

    @OnClick({R.id.picture_submit, R.id.picture_from, R.id.picture_back, R.id.recentBills_add, R.id.recentPersonals_add})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.picture_submit://提交
                break;
            case R.id.picture_from: //添加身份证正面照
                FLAG = 1;
                getPicture("/sdcard/");
                break;
            case R.id.picture_back://添加身份证反面照
                FLAG = 2;
                getPicture("/sdcard/");
                break;
            case R.id.recentBills_add:  //添加账单
                if (recentBills.getChildCount() < 3) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(R.mipmap.head_icon140);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(width / 3, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    recentBills.addView(imageView);
                    if (recentBills.getChildCount() == 3) {
                        recentBills_add.setVisibility(View.GONE);
                    }
                } else {
                    recentBills_add.setVisibility(View.GONE);
                }
                break;
            case R.id.recentPersonals_add:  //添加生活照
                if (recentPersonals.getChildCount() < 3) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(R.mipmap.head_icon140);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(width / 3, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    recentPersonals.addView(imageView);
                    if (recentPersonals.getChildCount() == 3) {
                        recentPersonals_add.setVisibility(View.GONE);
                    }
                } else {
                    recentPersonals_add.setVisibility(View.GONE);
                }
                break;
        }
    }

    //打开身份证照拍摄界面
    private void getPicture(String path) {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra("path", path);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String path = extras.getString("path");
            String type = extras.getString("type");
            Toast.makeText(getApplicationContext(), "path:" + path + " type:" + type, Toast.LENGTH_LONG).show();
            File file = new File(path);
            FileInputStream inStream = null;
            switch (FLAG) {
                case 1: //正面照
                    try {
                        inStream = new FileInputStream(file);
                        Bitmap bitmap = BitmapFactory.decodeStream(inStream);
                        from.setImageBitmap(bitmap);
                        inStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2: //反面照
                    try {
                        inStream = new FileInputStream(file);
                        Bitmap bitmap = BitmapFactory.decodeStream(inStream);
                        back.setImageBitmap(bitmap);
                        inStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.removeActivity(this);
    }
}
