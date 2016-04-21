package com.queqianme.hpt.utils;

import android.os.Environment;
import android.provider.ContactsContract;

import java.io.File;

/**
 * 配置类
 */
public class Config {
    /**
     * 拍照标签
     */
    public static final int PHOTO_REQUEST_TAKEPHOTO = 1001;
    /**
     * 从相册中选择标签
     */
    public static final int PHOTO_REQUEST_GALLERY = 1002;
    /**
     * 拍照结果标签
     */
    public static final int PHOTO_REQUEST_CUT = 1003;
    /**
     * 创建照片存储路径
     */
    public static File tempFile = new File(Environment.getExternalStorageDirectory(), APPUtils .getPhotoFileName());


    public static final String[] PHONES_PROJECTION = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    public static final int PHONES_NUMBER_INDEX = 2001;
    /** 联系人显示名称 **/
    public static final int PHONES_DISPLAY_NAME_INDEX = 2002;
}
