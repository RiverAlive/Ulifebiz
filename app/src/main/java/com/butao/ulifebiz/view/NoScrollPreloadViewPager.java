package com.butao.ulifebiz.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 创建时间 ：2017/5/12.
 * 编写人 ：bodong
 * 功能描述 ：
 */
public class NoScrollPreloadViewPager extends NoPreloadViewPager {
    public NoScrollPreloadViewPager(Context context) {
        super(context);
    }

    public NoScrollPreloadViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public void setCurrentItem(int item) {
        //false 去除滚动效果
        super.setCurrentItem(item, false);
    }
}
