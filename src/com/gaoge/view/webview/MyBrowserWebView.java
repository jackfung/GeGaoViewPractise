package com.gaoge.view.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import java.util.Map;

public class MyBrowserWebView extends WebView {
    public MyBrowserWebView(Context context, AttributeSet attrs, int defStyle,
            Map<String, Object> javascriptInterfaces, boolean privateBrowsing) {
        super(context);
    }
    
    public MyBrowserWebView(
            Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        super(context, attrs, defStyle);
    }
    
    public MyBrowserWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public MyBrowserWebView(Context context) {
        super(context);
    }
}
