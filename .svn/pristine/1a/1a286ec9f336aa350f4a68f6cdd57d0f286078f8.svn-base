package com.gaoge.view.webview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;

import com.gaoge.view.practise.utils.LogHelper;


public class MyBrowserActivity extends Activity implements OnLongClickListener{
    private MyController mController;
    private MyBaseUi mUi;
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        MyBrowserGlobals.initialize(this);
        
        mController = new MyController(this, icicle == null);
        mUi = new MyHelpEnabledPhoneUi(this, mController);
//        mUi = new MyBaseUi(this, mController);
//        mUi = new TestUi(this, mController);
        mUi.showTitleBar();
        mController.setUi(mUi);
       
        
        mController.start(icicle, getIntent());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	LogHelper.d(LogHelper.TAG_KEYCODE,"AAAAAAAAAAAAAAAA MyBrowserActivity onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_BACK) { 
            if(mController.getUi().onkeyDown()){
                return true;
            }
        }  
        return mController.onKeyDown(keyCode, event) ||
                super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	LogHelper.d(LogHelper.TAG_KEYCODE,"AAAAAAAAAAAAAAAAA MyBrowserActivity onKeyUp");
        return mController.onKeyUp(keyCode, event) ||
            super.onKeyUp(keyCode, event);
    }
    
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        return mController.dispatchKeyEvent(event)
//                || super.dispatchKeyEvent(event);
//    }
    
    public MyController getCoroller() {
        return mController;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mController != null) {
            mController.onResume();
        }
        printWebSettings();
      
    }

    private void printWebSettings() {
        boolean openWindowAuto = mController.getTabControl().getCurrentTab().getWebView().getSettings().getJavaScriptCanOpenWindowsAutomatically();
        boolean javascriptEnabled = mController.getTabControl().getCurrentTab().getWebView().getSettings().getJavaScriptEnabled();
        LogHelper.d(LogHelper.TAG_FLASH, "$$$$$$$$$$$$$$$ openWindowAuto: " + openWindowAuto + ",javascriptEnabled: " + javascriptEnabled);
    }
    
    public MyController getController() {
        return mController;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mController != null) {
            mController.onWindowFocusChanged(hasFocus);
        }
//        mUi.invokeBeforeStartEfficientRead();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
            Intent intent) {
        mController.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public boolean onLongClick(View v) {
        return mController.onLongClick(v);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        mController.onCreateContextMenu(menu, v, menuInfo);
    }
}
