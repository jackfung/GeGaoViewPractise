package com.gaoge.view.practise.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;

import com.gaoge.view.practise.utils.LogHelper;

public class TestKeyUrlInputView2 extends AutoCompleteTextView {

    public TestKeyUrlInputView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public TestKeyUrlInputView2(Context context){
        this(context,null);
    }
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        LogHelper.d(LogHelper.TAG_KEYCODE, "2222222222 TestKeyUrlInputView2,onKeyDown()");
        return false;
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        LogHelper.d(LogHelper.TAG_KEYCODE, "222222222 TestKeyUrlInputView2,onKeyUp()");
        return false;
    }


}
