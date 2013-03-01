package com.gaoge.view.webview;

import android.os.Bundle;
import android.webkit.WebView;



import java.util.ArrayList;
import java.util.HashMap;



public class MyTabControl {
    
    private final MyController mController;
    private ArrayList<MyTab> mTabs;
    private int mMaxTabs;
    private ArrayList<MyTab> mTabQueue;
    private final MyBrowserActivity mActivity;
    private int mCurrentTab = -1;
    
    MyTabControl(MyController controller) {
        mController = controller;
        mMaxTabs = mController.getMaxTabs();
        mTabs = new ArrayList<MyTab>(mMaxTabs);
        mTabQueue = new ArrayList<MyTab>(mMaxTabs);
        mActivity = (MyBrowserActivity)controller.getActivity();
    }
    
    void restoreState(Bundle inState, long currentId,
            boolean restoreIncognitoTabs, boolean restoreAll) {
        MyTab t = createNewTab(null, false);
        HashMap<Long, MyTab> tabMap = new HashMap<Long, MyTab>();
        if (t == null) {
            // We could "break" at this point, but we want
            // sNextId to be set correctly.
        }
        tabMap.put(1l, t);
    }
    
    MyTab createNewTab(Bundle state, boolean privateBrowsing) {
        int size = mTabs.size();

        final WebView w = createNewWebView(privateBrowsing);

        // Create a new tab and add it to the tab list
        MyTab t = new MyTab(mController, w, state);
        mTabs.add(t);
        mCurrentTab++;
        // Initially put the tab in the background.
        return t;
    }
    
    private WebView createNewWebView(boolean privateBrowsing) {
        return mController.getWebViewFactory().createWebView(privateBrowsing);
    }
    
    
    MyTab getCurrentTab() {
        return getTab(mCurrentTab);
    }
    
    public boolean setCurrentTab(MyTab newTab) {
        return setCurrentTab(newTab, false);
    }
    
    private boolean setCurrentTab(MyTab newTab, boolean force) {
        MyTab current = getTab(mCurrentTab);
        if (current == newTab && !force) {
            return true;
        }
        if (current != null) {
            current.putInBackground();
            mCurrentTab = -1;
        }
        if (newTab == null) {
            return false;
        }

        // Move the newTab to the end of the queue
        int index = mTabQueue.indexOf(newTab);
        if (index != -1) {
            mTabQueue.remove(index);
        }
        mTabQueue.add(newTab);

        // Display the new current tab
        mCurrentTab = mTabs.indexOf(newTab);
        WebView mainView = newTab.getWebView();
        boolean needRestore = !newTab.isSnapshot() && (mainView == null);
        if (needRestore) {
            // Same work as in createNewTab() except don't do new Tab()
            mainView = createNewWebView();
            newTab.setWebView(mainView);
        }
        newTab.putInForeground();
        return true;
    }
    
    private WebView createNewWebView() {
        return createNewWebView(false);
    }
    
    MyTab getTab(int position) {
        if (position >= 0 && position < mTabs.size()) {
            return mTabs.get(position);
        }
        return null;
    }
    
    boolean canCreateNewTab() {
        return true;
    }
    
    MyTab createNewTab(boolean privateBrowsing) {
        return createNewTab(null, privateBrowsing);
    }
    
    WebView getCurrentSubWindow() {
        MyTab t = getTab(mCurrentTab);
        if (t == null) {
            return null;
        }
        return t.getSubWebView();
    }
    
    public static interface OnThumbnailUpdatedListener {
        void onThumbnailUpdated(MyTab t);
    }
    private OnThumbnailUpdatedListener mOnThumbnailUpdatedListener;
    
    public OnThumbnailUpdatedListener getOnThumbnailUpdatedListener() {
        return mOnThumbnailUpdatedListener;
    }
    
    public void setOnThumbnailUpdatedListener(OnThumbnailUpdatedListener listener) {
        mOnThumbnailUpdatedListener = listener;
        for (MyTab t : mTabs) {
            WebView web = t.getWebView();
            if (web != null) {
                web.setPictureListener(listener != null ? t : null);
            }
        }
    }
    
    WebView getCurrentTopWebView() {
        MyTab t = getTab(mCurrentTab);
        if (t == null) {
            return null;
        }
        return t.getTopWindow();
    }
    
    int getTabCount() {
        return mTabs.size();
    }

}
