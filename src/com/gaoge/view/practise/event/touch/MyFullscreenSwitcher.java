package com.gaoge.view.practise.event.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gaoge.view.practise.utils.LogHelper;

public class MyFullscreenSwitcher extends View {
    private int mMenuHeight;

    public MyFullscreenSwitcher(Context context) {
        super(context);
    }

    public MyFullscreenSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFullscreenSwitcher(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        setMeasuredDimension(w, h - mMenuHeight);
    }

    public void setMenuHeight(int height) {
        mMenuHeight = height;
        requestLayout();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogHelper.d(LogHelper.TAG_TOUCH,"############# MyFullscreenSwitcher onTouchEvent");
        return super.onTouchEvent(event);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogHelper.d(LogHelper.TAG_TOUCH,"############# MyFullscreenSwitcher dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
    
}
