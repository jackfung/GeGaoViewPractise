package com.gaoge.view.webview.reflect;

import android.webkit.WebSettings;

public class MyInvokeWebSettingsMethod {
    public static void setPageCacheCapacity(WebSettings ws , int capacity){
        Invoker.invoke(ws, "setPageCacheCapacity", new Class[]{int.class}, new Object[]{capacity});
    }

    public static void setWorkersEnabled(WebSettings ws, boolean b){
        Invoker.invoke(ws, "setWorkersEnabled", new Class[]{boolean.class}, new Object[]{b});
    }
}

