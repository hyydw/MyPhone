package com.ydw.myphone;

/**
 * Created by Administrator on 2017/3/7.
 */

import android.view.View;

public interface MyItemClickListener {
    void onItemClick(View view, int position, Edge edge);
    //void onItemClick(View view, int position, int out);
}
