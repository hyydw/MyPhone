package com.ydw.myphone;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/8/2.
 */

public class ContactInterfaceState extends BaseObservable {
    private boolean canSelect = false;
    private String contactName;
    private String contactPhone;
    private int contactPhotoId;

    public ContactInterfaceState(String contactName, String contactPhone, int contactPhoto){
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactPhotoId = contactPhoto;
    }

    public ContactInterfaceState(){}

    @Bindable
    public boolean isCanSelect() {
        return canSelect;
    }

    public void setCanSelect(boolean canSelect) {
        this.canSelect = canSelect;
        notifyPropertyChanged(BR.canSelect);
    }

    @Bindable
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
        notifyPropertyChanged(BR.contactName);
    }

    @Bindable
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
        notifyPropertyChanged(BR.contactPhone);
    }

    @Bindable
    public int getContactPhotoId() {
        return contactPhotoId;
    }

    public void setContactPhotoId(int contactPhotoId) {
        this.contactPhotoId = contactPhotoId;
        notifyPropertyChanged(BR.contactPhotoId);
    }
}
