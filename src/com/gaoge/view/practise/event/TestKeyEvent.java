package com.gaoge.view.practise.event;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.gaoge.view.practise.utils.LogHelper;
import com.orange.test.textImage.R;

public class TestKeyEvent extends Activity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_keyevent);
        
        tv = (TextView)this.findViewById(R.id.tv_input);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogHelper.d(LogHelper.TAG_KEYCODE,"AAAAAAAAAAAAAAAAAAAAA onKeyDown");
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        LogHelper.d(LogHelper.TAG_KEYCODE,"AAAAAAAAAAAAAAAAAAAA onKeyUp");
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	LogHelper.d(LogHelper.TAG_KEYCODE,"AAAAAAAAAAAAAAAAAAAA dispatchKeyEvent");
    	return super.dispatchKeyEvent(event);
    }
    
}
