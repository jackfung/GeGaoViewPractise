package com.gaoge.view.webview;

import android.os.Build;


public class MyBrowserGlobals {
    private static MyBrowserGlobals sInstance;
    private MyBrowserActivity mActivity;
    private static boolean sFirstTime = true;
    
    public MyBrowserGlobals(MyBrowserActivity browserActivity) {
        // TODO Auto-generated constructor stub
        mActivity = browserActivity;
    }

    public static void initialize(MyBrowserActivity browserActivity) {
        sInstance = new MyBrowserGlobals(browserActivity);
    }
    
    public static MyBrowserGlobals getInstance() {
        return sInstance;
    }
    
    public boolean isHomePage(){
        MyController controller = mActivity.getController();
        if (controller != null){
            MyTab t = controller.getTabControl().getCurrentTab();
//            if (t != null && !MyBrowserSettings.getInstance().getHomePage().equals(t.getUrl()))
//                return false;
        }
        return true;
    }
    
    public static boolean isPlatformHoneycombAndAbove(){
        return Build.VERSION.SDK_INT >= Constants.HONEYCOMB ;
    }
    public static boolean isPlatformICSAndAbove(){
        return Build.VERSION.SDK_INT >= Constants.ICE_CREAM_SANDWICH ;
    }
    
    public static boolean isFirstTime(){
        return sFirstTime;
    }
    
    public static void removeFirstTime(){
        sFirstTime = false;
    }
}
