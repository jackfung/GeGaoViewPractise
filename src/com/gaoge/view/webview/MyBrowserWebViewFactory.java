package com.gaoge.view.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

import com.gaoge.view.practise.utils.Utils;
import com.gaoge.view.webview.extendjs.MyOrangeHomeLink;
import com.gaoge.view.webview.interfaces.MyWebViewFactory;

public class MyBrowserWebViewFactory implements MyWebViewFactory {

    private final Context mContext;

    public MyBrowserWebViewFactory(Context context) {
        mContext = context;
    }
    
    @Override
    public WebView createWebView(boolean privateBrowsing) {
        WebView w = instantiateWebView(null, android.R.attr.webViewStyle, privateBrowsing);
        initWebViewSettings(w);
        return w;
    }

    @Override
    public WebView createSubWebView(boolean privateBrowsing) {
        return createWebView(privateBrowsing);
    }
    
    protected WebView instantiateWebView(AttributeSet attrs, int defStyle,
            boolean privateBrowsing) {
        return new MyBrowserWebView(mContext, attrs, defStyle, privateBrowsing);
    }
    
    @TargetApi(11)
    protected void initWebViewSettings(WebView w) {
        if (Utils.isPlatformHoneycombAndAbove()) {
            if (MyBrowserGlobals.isFirstTime()) {
                w.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                MyBrowserGlobals.removeFirstTime();
            } else if (w.getLayerType() == View.LAYER_TYPE_SOFTWARE)
                w.setLayerType(View.LAYER_TYPE_NONE, null);
        }
        w.setScrollbarFadingEnabled(true);
        w.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        w.setMapTrackballToArrowKeys(false);
        
        w.getSettings().setBuiltInZoomControls(true);
        if (MyBrowserGlobals.isPlatformHoneycombAndAbove()) {
            w.getSettings().setDisplayZoomControls(false);
        }
        
        final MyBrowserSettings s = MyBrowserSettings.getInstance(mContext);
        s.startManagingSettings(w.getSettings());
        s.addObserver(w.getSettings()).update(s, null);
        
        w.getSettings().setJavaScriptEnabled(true);
        
        MyOrangeHomeLink homeLink = new MyOrangeHomeLink(mContext);
        w.addJavascriptInterface(homeLink, "orangeHomeLink");
        
    }


}
