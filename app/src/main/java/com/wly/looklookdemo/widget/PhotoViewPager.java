package com.wly.looklookdemo.widget;

/**
 * Created by Candy on 2016/9/20.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author 咖枯
 * @version 1.0 2016/7/9
 */
public class PhotoViewPager extends ViewPager {

    public static final String TAG = PhotoViewPager.class.getSimpleName();

    public PhotoViewPager(Context context) {
        super(context);
    }

    public PhotoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
            Log.e(TAG , e.toString());
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG , ex.toString());
        }
        return false;
    }
}
