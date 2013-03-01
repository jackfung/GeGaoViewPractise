package com.gaoge.view.practise.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;

import com.gaoge.view.practise.utils.LogHelper;

public class TestKeyUrlInputView1 extends AutoCompleteTextView {

    public TestKeyUrlInputView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public TestKeyUrlInputView1(Context context){
        this(context,null);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        LogHelper.d(LogHelper.TAG_KEYCODE, "11111111111 TestKeyUrlInputView1,onKeyDown()");
        return false;
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        LogHelper.d(LogHelper.TAG_KEYCODE, "11111111111 TestKeyUrlInputView1,onKeyUp()");
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	LogHelper.d(LogHelper.TAG_KEYCODE,"111111111111 dispatchKeyEvent");
    	return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
    	LogHelper.d(LogHelper.TAG_KEYCODE,"111111111111 dispatchKeyEventPreIme");
    	return super.dispatchKeyEventPreIme(event);
    }
}
