package com.gaoge.view.webview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.webview.MyUrlInputView.UrlInputListener;
import com.gaoge.view.webview.interfaces.MyUiController;
import com.orange.test.textImage.R;

public class MyNavigationBarBase extends LinearLayout implements OnClickListener,
        OnFocusChangeListener, TextWatcher,UrlInputListener {

    protected MyTitleBar mTitleBar;
    protected MyBaseUi mBaseUi;
    protected MyUiController mUiController;
    protected MyUrlInputView mUrlInput;
    protected LinearLayout mUrlContainer;
    
    public MyNavigationBarBase(Context context) {
        super(context);
    }

    public MyNavigationBarBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mUrlInput = (MyUrlInputView) findViewById(R.id.url);
        mUrlContainer = (LinearLayout)findViewById(R.id.url_container);
        mUrlInput.setUrlInputListener(this);
        mUrlInput.setOnFocusChangeListener(this);
        mUrlInput.setSelectAllOnFocus(true);
        mUrlInput.addTextChangedListener(this);
    }
    
    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            
        }else{
            mUrlInput.hideIME();
        }
    }

    @Override
    public void onClick(View v) {

    }
    
    public void setTitleBar(MyTitleBar titleBar) {
        mTitleBar = titleBar;
        mBaseUi = mTitleBar.getUi();
        mUiController = mTitleBar.getUiController();
        mUrlInput.setController(mUiController);
    }
    public void updateBookmarkIcons(){}

    @Override
    public void onDismiss() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onAction(String text, String extra, String source) {
        WebView currentTopWebView = mUiController.getCurrentTopWebView();
        if (currentTopWebView != null) {
            currentTopWebView.requestFocus();
        }
        if (MyUrlInputView.TYPED.equals(source)) {
            String url = MyUrlUtils.smartUrlFilter(text, false);
            MyTab t = mBaseUi.getActiveTab();
            // Only shortcut javascript URIs for now, as there is special
            // logic in UrlHandler for other schemas
//            if (url != null && t != null && url.startsWith("javascript:")) {
            if (url != null && t != null ) {
                mUiController.loadUrl(t, url);
//                setDisplayTitle(text);
                return;
            }
        }
        setDisplayTitle(text);
    }
    
    void setDisplayTitle(String title) {
        if (!isEditingUrl()) {
//            mUrlInput.setText(title, false);
            mUrlInput.setText(title);
        }
    }
    
    public boolean isEditingUrl() {
        return mUrlInput.hasFocus();
    }

    @Override
    public void onCopySuggestion(String text) {
        mUrlInput.setText(text);
        if (text != null) {
            mUrlInput.setSelection(text.length());
        }
    }

    @Override
    public void onFaviconClicked() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onComboButtonClicked() {
        // TODO Auto-generated method stub
        
    };
    
    public void setCurrentUrlIsBookmark(boolean isBookmark) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogHelper.d(LogHelper.TAG_KEYCODE,"GGGGGGGGGGGGGGG MyNavigationBarBase onKeyDown");
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
    	 LogHelper.d(LogHelper.TAG_KEYCODE,"GGGGGGGGGGGGGGG MyNavigationBarBase onKeyUp");
    	return super.onKeyUp(keyCode, event);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	LogHelper.d(LogHelper.TAG_KEYCODE,"GGGGGGGGGGGGGGG MyNavigationBarBase dispatchKeyEvent");
    	return super.dispatchKeyEvent(event);
    }
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
    	if(KeyEvent.KEYCODE_BACK == event.getKeyCode()){
    		mUrlInput.clearFocus();
    		return true;
    	}
    	return super.dispatchKeyEventPreIme(event);
    }
    
    public void onProgressStopped() {
        
    }
    
    
}
