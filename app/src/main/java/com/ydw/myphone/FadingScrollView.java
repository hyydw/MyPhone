package com.ydw.myphone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Administrator on 2017/8/1.
 */

public class FadingScrollView extends ScrollView {

    private static String TAG = "-----------FadingScrollView----------";
    //渐变view
    private View fadingView;
    //滑动view的高度，如果这里fadingHeightView是一张图片，
    // 那么就是这张图片上滑至完全消失时action bar 完全显示，
    // 过程中透明度不断增加，直至完全显示
    private View fadingHeightView;
    private TextView placeTv, titleLocTv;
    private LinearLayout navLayout;
    private int oldY;
    //滑动距离，默认设置滑动500 时完全显示，根据实际需求自己设置
    private int fadingHeight = 100;
    private int notifyBarHeight = 0;
    public FadingScrollView(Context context) {
        super(context);
    }

    public FadingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FadingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFadingView(View view){this.fadingView = view;}
    public void setFadingHeightView(View v){this.fadingHeightView = v;}
    public void setFadingTextview(TextView v ,TextView titleLocTv, LinearLayout navLayout, int barheight){
        this.placeTv = v;
        this.titleLocTv = titleLocTv;
        this.navLayout = navLayout;
        this.notifyBarHeight = barheight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(fadingHeightView != null)
            fadingHeight = fadingHeightView.getMeasuredHeight()-notifyBarHeight;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        l,t  滑动后 xy位置，
//        oldl lodt 滑动前 xy 位置-----
        float fading = t>(fadingHeight )? fadingHeight : (t > 30 ? t : 0);
        updateActionBarAlpha( fading / (fadingHeight));
//        processStickyTranslateY(t);
    }

    void updateActionBarAlpha(float alpha){
        try {
            setActionBarAlpha(alpha);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setActionBarAlpha(float alpha) throws Exception{
        if(fadingView==null){
            throw new Exception("fadingView is null...");
        }
        fadingView.setAlpha(alpha);
    }


    private int downSelect = 0;
    private int lastProcessStickyTranslateY = 0;
    private int navBottomPos, locTvTopPosX, locTvTopPosY, titleHeight;
    public static int STICKY_HEIGHT1; // height1是代表从顶部到tab的距离
    public static int STICKY_HEIGHT2; // height2是代表从顶部到viewpager的距离
    @SuppressLint("NewApi")
    private void processStickyTranslateY(int translateY) {
        if (translateY == Integer.MIN_VALUE
                || translateY == lastProcessStickyTranslateY) {
            return;
        }

//        if (translateY < -STICKY_HEIGHT1 + 10) {
//            bottomLayout.setVisibility(View.VISIBLE);
//        }
//        else {
//            bottomLayout.setVisibility(View.GONE);
//        }

        lastProcessStickyTranslateY = translateY;

//        stickyView.setTranslationY(translateY);
        int[] location = new int[2];
        placeTv.getLocationInWindow(location);

        if (navBottomPos == 0 || locTvTopPosY == 0) {
            locTvTopPosX = titleLocTv.getLeft();
            locTvTopPosY = titleLocTv.getTop();
            navBottomPos = navLayout.getBottom();
            titleHeight = titleLocTv.getMeasuredHeight();
        }

        int locationX = location[0];

        int locationY = location[1] - PixValue.dip.valueOf(50)
                + notifyBarHeight;
        if (locationY < locTvTopPosY) {
            locationY = locTvTopPosY;
        }

        if (locationY < navBottomPos - titleHeight) {
            locationX = locationX
                    - ((navBottomPos - titleHeight - locationY) * 2);
            if (locationX < locTvTopPosX) {
                locationX = locTvTopPosX;
            }
        }

        ViewHelper.setX(titleLocTv, locationX);
        ViewHelper.setY(titleLocTv, locationY);
    }

    public enum PixValue
    {
        dip
                {
                    @Override
                    public int valueOf(float value)
                    {
                        return Math.round(value * m.density);
                    }
                };

        public static DisplayMetrics m = Resources.getSystem().getDisplayMetrics();

        public abstract int valueOf(float value);
    }
}
