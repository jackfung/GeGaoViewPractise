package com.gaoge.view.practise.event.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.gaoge.view.practise.utils.LogHelper;

public class ParentView extends RelativeLayout {

    public ParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogHelper.d(LogHelper.TAG_TOUCH,"############# RarentView onTouchEvent");
        return super.onTouchEvent(event);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogHelper.d(LogHelper.TAG_TOUCH,"############# RarentView dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogHelper.d(LogHelper.TAG_TOUCH,"############# RarentView onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

}
