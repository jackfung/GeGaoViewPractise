package com.gaoge.view.practise.utils;

import android.util.Log;

public class LogHelper {

    private static final boolean LOGD_ENABLED = true;
    public static final String TAG_REFLACTOR = "reflactor";
    public static final String TAG_STARBTN = "starBtn";
    public static final String TAG_STARBTN_SPPED = "starBtn_speed";
    public static final String TAG_SPEEDDIAL = "speedDial";
    public static final String TAG_PROGRESS = "progress";
    public static final String TAG_TITLEBAR = "titleBar";
    public static final String TAG_BOTTOMBAR = "bottomBar";
    public static final String TAG_HELP = "help";
    public static final String TAG_KEYCODE = "keycode";
    public static final String TAG_FIND = "FindDialog";
    public static final String TAG_WEBSETTINGS = "WebSettings";
    public static final String TAG_FLASH = "flash";
    public static final String TAG_STATUS_BAR = "statusBar";
    public static final String TAG_TOUCH = "touch";
    public static final String URLINPUTVIEW = "urlInputView";
    public static final String TAG_DOWNLOAD = "dowanloadGao";
    public static final String TAG_PROVIDER = "provider";
    public static final String TAG_SCROLL = "scroll";
    public static final String TAG_WM = "window";
    public static final String TAG_TAB = "tab";
    
    public static void d(String TAG, String msg) {
        if (LOGD_ENABLED) {
            Log.d(TAG, msg);
        }
    }
    
    

}
