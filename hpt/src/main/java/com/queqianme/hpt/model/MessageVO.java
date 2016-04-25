package com.queqianme.hpt.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 消息实体
 * Created by zhaojiayu on 16/3/11.
 */
public class MessageVO implements Parcelable {
    private long messageId;  //消息编号 修改、删除会用到
    private String icon;    //消息icon地址
    private String createDate;  //创建时间，格式(yyyy-MM-dd HH:mm:ss)
    private String title;   //标题
    private String content; //内容
    private int isRead; //是否已读,0:未读、1:已读
    private String readTime;    //阅读时间，格式(yyyy-MM-dd HH:mm:ss) isRead为1时有值

    public long getMessageId() {
        return messageId;
    }

    public String getIcon() {
        return icon;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getIsRead() {
        return isRead;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.messageId);
        dest.writeString(this.icon);
        dest.writeString(this.createDate);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeInt(this.isRead);
        dest.writeString(this.readTime);
    }

    public MessageVO() {
    }

    protected MessageVO(Parcel in) {
        this.messageId = in.readLong();
        this.icon = in.readString();
        this.createDate = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.isRead = in.readInt();
        this.readTime = in.readString();
    }

    public static final Creator<MessageVO> CREATOR = new Creator<MessageVO>() {
        public MessageVO createFromParcel(Parcel source) {
            return new MessageVO(source);
        }

        public MessageVO[] newArray(int size) {
            return new MessageVO[size];
        }
    };
}
