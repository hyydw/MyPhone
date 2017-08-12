package com.ydw.myphone;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/7/28.
 */

public class SortComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        ContactsInfoMid a = (ContactsInfoMid) o1;
        ContactsInfoMid b = (ContactsInfoMid) o2;
        return b.getContactsLastTimeLong().compareTo(a.getContactsLastTimeLong());
    }
}
