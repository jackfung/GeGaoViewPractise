package com.gaoge.view.webview.interfaces;

import android.view.KeyEvent;
import android.webkit.WebView;

import com.gaoge.view.webview.MyTab;


public interface MyUI {
    public void onSetWebView(MyTab tab, WebView view);
    public void onResume();
    public void showTitleBar();
    public boolean onkeyDown();
    public void suggestHideTitleBar();
    public boolean dispatchKey(int code, KeyEvent event);
    public boolean onBackKeyUp();
    public void setShouldShowErrorConsole(MyTab tab, boolean show);
    void bookmarkedStatusHasChanged(MyTab tab);
    public void openWindowManager();
    public void detachTab(MyTab tab);
    public void startSelectArticlesAnimation();
    public void setFullscreen(boolean enabled);
    public boolean onMenuKey();
    public void onTabDataChanged(MyTab tab);
    public void onProgressChanged(MyTab tab);

}
