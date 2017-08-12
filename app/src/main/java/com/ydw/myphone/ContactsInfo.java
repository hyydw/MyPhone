package com.ydw.myphone;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/7/28.
 */

public class ContactsInfo extends BaseObservable {
    private String leftContactsName;//联系人名称
    private String leftContactsNumber;//联系人号码
    private int leftContactsPhotoId;//联系人头像
    private Long leftContactsID;//联系人ID
    private Long leftContactsLastTimeLong;//最后通话时间long型
    private String leftContactsLastTimeString;//最后通话时间String型

    private String rightContactsName;
    private String rightContactsNumber;//联系人号码
    private int rightContactsPhotoId;//联系人头像
    private Long rightContactsID;//联系人ID
    private Long rightContactsLastTimeLong;//最后通话时间long型
    private String rightContactsLastTimeString;//最后通话时间String型

    public ContactsInfo(){}

    public ContactsInfo(String leftContactsName, String rightContactsName){
        this.leftContactsName = leftContactsName;
        this.rightContactsName = rightContactsName;
    }

    @Bindable
    public String getLeftContactsName() {
        return leftContactsName;
    }

    public void setLeftContactsName(String leftContactsName) {
        this.leftContactsName = leftContactsName;
        notifyPropertyChanged(BR.leftContactsName);
    }

    @Bindable
    public String getRightContactsName() {
        return rightContactsName;
    }

    public void setRightContactsName(String rightContactsName) {
        this.rightContactsName = rightContactsName;
        notifyPropertyChanged(BR.rightContactsName);
    }

    @Bindable
    public String getLeftContactsNumber() {
        return leftContactsNumber;
    }

    public void setLeftContactsNumber(String leftContactsNumber) {
        this.leftContactsNumber = leftContactsNumber;
    }

    @Bindable
    public int getLeftContactsPhotoId() {
        return leftContactsPhotoId;
    }

    public void setLeftContactsPhotoId(int leftContactsPhotoId) {
        this.leftContactsPhotoId = leftContactsPhotoId;
        notifyPropertyChanged(BR.leftContactsPhotoId);
    }

    @Bindable
    public Long getLeftContactsID() {
        return leftContactsID;
    }

    public void setLeftContactsID(Long leftContactsID) {
        this.leftContactsID = leftContactsID;
    }

    @Bindable
    public String getRightContactsNumber() {
        return rightContactsNumber;
    }

    public void setRightContactsNumber(String rightContactsNumber) {
        this.rightContactsNumber = rightContactsNumber;
    }

    @Bindable
    public int getRightContactsPhotoId() {
        return rightContactsPhotoId;
    }

    public void setRightContactsPhotoId(int rightContactsPhotoId) {
        this.rightContactsPhotoId = rightContactsPhotoId;
        notifyPropertyChanged(BR.rightContactsPhotoId);
    }

    @Bindable
    public Long getRightContactsID() {
        return rightContactsID;
    }

    public void setRightContactsID(Long rightContactsID) {
        this.rightContactsID = rightContactsID;
    }

    @Bindable
    public Long getLeftContactsLastTimeLong() {
        return leftContactsLastTimeLong;
    }

    public void setLeftContactsLastTimeLong(Long leftContactsLastTimeLong) {
        this.leftContactsLastTimeLong = leftContactsLastTimeLong;
    }

    @Bindable
    public String getLeftContactsLastTimeString() {
        return leftContactsLastTimeString;
    }

    public void setLeftContactsLastTimeString(String leftContactsLastTimeString) {
        this.leftContactsLastTimeString = leftContactsLastTimeString;
    }

    @Bindable
    public Long getRightContactsLastTimeLong() {
        return rightContactsLastTimeLong;
    }

    public void setRightContactsLastTimeLong(Long rightContactsLastTimeLong) {
        this.rightContactsLastTimeLong = rightContactsLastTimeLong;
    }

    @Bindable
    public String getRightContactsLastTimeString() {
        return rightContactsLastTimeString;
    }

    public void setRightContactsLastTimeString(String rightContactsLastTimeString) {
        this.rightContactsLastTimeString = rightContactsLastTimeString;
    }
}
