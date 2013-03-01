package com.gaoge.view.webview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class MyFullscreenSwitcher extends View {
    private int mMenuHeight;

    public MyFullscreenSwitcher(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyFullscreenSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyFullscreenSwitcher(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        setMeasuredDimension(w, h - mMenuHeight);
    }

    public void setMenuHeight(int height) {
        mMenuHeight = height;
        requestLayout();
    }
}
