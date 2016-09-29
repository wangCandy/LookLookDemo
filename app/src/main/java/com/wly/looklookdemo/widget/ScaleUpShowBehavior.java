package com.wly.looklookdemo.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;

/**
 * Created by Candy on 2016/9/29.
 */
public class ScaleUpShowBehavior extends FloatingActionButton.Behavior {

    public ScaleUpShowBehavior(Context context , AttributeSet attr){
        super();
    }

    //开始滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    //正在滑动
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

//        if(dyConsumed > 0 && dyUnconsumed == 0){
//            //上滑
//        }
//
//        if(dyConsumed == 0 && dyUnconsumed > 0){
//            //达到边界还在上滑
//        }
//
//        if(dyConsumed < 0 && dyUnconsumed == 0){
//            //下滑中
//        }
//
//        if(dyConsumed == 0 && dyUnconsumed < 0){
//            //达到边界还在下滑
//        }

        if(((dyConsumed > 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed > 0)) && child.getVisibility() != View.VISIBLE){
            child.show();//显示
        }else if(((dyConsumed < 0 && dyUnconsumed == 0) || (dyConsumed == 0 && dyUnconsumed < 0)) && child.getVisibility() == View.VISIBLE){
            child.hide();//隐藏
        }
    }

    //停止滑动
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

//    private boolean isAnimatingOut = false;
//
//    ViewPropertyAnimatorListener viewPropertyAnimatorListener = new ViewPropertyAnimatorListener() {
//
//        @Override
//        public void onAnimationStart(View view) {
//            isAnimatingOut = true;
//        }
//
//        @Override
//        public void onAnimationEnd(View view) {
//            isAnimatingOut = false;
//            view.setVisibility(View.GONE);
//        }
//
//        @Override
//        public void onAnimationCancel(View arg0) {
//            isAnimatingOut = false;
//        }
//    };
}
