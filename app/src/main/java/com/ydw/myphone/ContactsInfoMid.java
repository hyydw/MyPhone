package com.ydw.myphone;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2017/7/28.
 * 作为数据中转
 */

public class ContactsInfoMid extends BaseObservable implements Comparable<ContactsInfoMid>{
    private String contactsName;//联系人名称
    private String contactsNumber;//联系人号码
    private int contactsPhotoId;//联系人头像
    private Long contactsID;//联系人ID
    private Long contactsLastTimeLong;//最后通话时间long型
    private String contactsLastTimeString;//最后通话时间String型

    public ContactsInfoMid(){}


    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsNumber() {
        return contactsNumber;
    }

    public void setContactsNumber(String contactsNumber) {
        this.contactsNumber = contactsNumber;
    }

    public int getContactsPhotoId() {
        return contactsPhotoId;
    }

    public void setContactsPhotoId(int contactsPhotoId) {
        this.contactsPhotoId = contactsPhotoId;
    }

    public Long getContactsID() {
        return contactsID;
    }

    public void setContactsID(Long contactsID) {
        this.contactsID = contactsID;
    }

    public Long getContactsLastTimeLong() {
        return contactsLastTimeLong;
    }

    public void setContactsLastTimeLong(Long contactsLastTimeLong) {
        this.contactsLastTimeLong = contactsLastTimeLong;
    }

    public String getContactsLastTimeString() {
        return contactsLastTimeString;
    }

    public void setContactsLastTimeString(String contactsLastTimeString) {
        this.contactsLastTimeString = contactsLastTimeString;
    }

    @Override
    public int compareTo(@NonNull ContactsInfoMid o) {
        /**
         * compareTo()：大于0表示前一个数据比后一个数据大， 0表示相等，小于0表示前一个数据小于后一个数据
         */
        return contactsLastTimeLong.compareTo(o.contactsLastTimeLong);
    }
}
