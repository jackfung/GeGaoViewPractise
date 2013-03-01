package com.gaoge.view.webview.interfaces;

import android.webkit.WebView;

import com.gaoge.view.webview.MyTab;
import com.gaoge.view.webview.MyTabControl;


public interface MyUiController {
    
    MyTabControl getTabControl();
    void attachSubWindow(MyTab tab);
    void openSetting();
    void exitBrowser();
    void openFindDialog();
    public boolean isFindDialogShowing();
    public WebView getCurrentTopWebView();
    
    void loadUrl(MyTab tab, String url);

}
