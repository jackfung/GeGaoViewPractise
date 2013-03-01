package com.gaoge.view.webview.interfaces;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;

import com.gaoge.view.webview.MyTab;
import com.gaoge.view.webview.MyTabControl;

import java.util.List;


public interface MyWebViewController {
    Context getContext();
    void onSetWebView(MyTab tab, WebView view);
    void onDownloadStart(MyTab tab, String url, String useragent, String contentDisposition,
            String mimeType, long contentLength);
    void activateVoiceSearchMode(String title, List<String> results);
    MyTabControl getTabControl();
    void bookmarkedStatusHasChanged(MyTab tab);
    Activity getActivity();
    public void onPageFinished(MyTab tab);
    public void onProgressChanged(MyTab tab);
    void revertVoiceSearchMode(MyTab tab);
}
