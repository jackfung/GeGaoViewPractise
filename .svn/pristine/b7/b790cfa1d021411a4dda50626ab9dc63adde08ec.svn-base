package com.gaoge.view.webview;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gaoge.view.webview.interfaces.MyUI;
import com.gaoge.view.webview.interfaces.MyUiController;
import com.orange.test.textImage.R;

public class MyBaseUi implements MyUI {
    Activity mActivity;
    MyUiController mMyUiController;
    LayoutInflater inflater;
    private InputMethodManager mInputManager;
    FrameLayout mContent;
    protected FrameLayout mContentView;
    MyTabControl mTabControl;
    protected FrameLayout mBottomBarContainer;
    protected MyBottomBar mBottomBar;
    protected FrameLayout mTitleBarContainer;
    protected MyTitleBar mTitleBar;
    protected MyBottomBarMenu mMenu;
    private MyNavigationBarBase mNavigationBar;
    protected MyTab mActiveTab;
    protected MyWindowsManager mWindowsManager;
    private boolean isFullscreen;
    private MyCloseFullscreenListener mCloseFullscreenListener;
    protected RelativeLayout mWebViewContainer;
    protected FrameLayout mWindowManagerContainer;
    
    static final String LOGTAG = "MyBaseUi";
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS =
            new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    
    public MyBaseUi(Activity activity, MyUiController controller){
        this.mActivity = activity;
        mMyUiController = controller;
        mTabControl = controller.getTabControl();
        inflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources res = mActivity.getResources();
        mInputManager = (InputMethodManager)
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        
        FrameLayout frameLayout = (FrameLayout) mActivity.getWindow()
                .getDecorView().findViewById(android.R.id.content);
        mContent = frameLayout;
        LayoutInflater.from(mActivity).inflate(R.layout.my_custom_screen, frameLayout);
        initControls(frameLayout);
    }
    
    public FrameLayout getAndroidRootView(){
        return mContent;
    }

    private void initControls(FrameLayout frameLayout) {
        mContentView = (FrameLayout) frameLayout.findViewById(
                R.id.main_content);
        mTitleBarContainer=(FrameLayout) frameLayout.findViewById(
                R.id.title);
        
        mTitleBar = new MyTitleBar(mActivity, mMyUiController, this,
                mTitleBarContainer);
        
        mBottomBarContainer=(FrameLayout) frameLayout.findViewById(
                R.id.bottom);
        
        mBottomBar=new MyBottomBar(mActivity, mMyUiController, this,
                mBottomBarContainer);
        
        mMenu = new MyBottomBarMenu(mActivity, mMyUiController, this, mContentView);
        
        mNavigationBar = mTitleBar.getNavigationBar();
        
        mFullscreenSwitcher = (MyFullscreenSwitcher)frameLayout.findViewById(R.id.fullscreen_switcher);
        mCloseFullscreenListener = new MyCloseFullscreenListener(mActivity, this);
        
        mWebViewContainer=(RelativeLayout) frameLayout.findViewById(
                R.id.vertical_layout);
        
        mWindowManagerContainer=(FrameLayout) frameLayout.findViewById(
                R.id.window_manager_container);
        
        mWindowsManager=new MyWindowsManager(mActivity, mMyUiController, this, mWindowManagerContainer);
    }
    
    public void attachTab(MyTab tab) {
        attachTabToContentView(tab);
    }
    
    protected void attachTabToContentView(MyTab tab) {
        if ((tab == null) || (tab.getWebView() == null)) {
            return;
        }
        View container = tab.getViewContainer();
        WebView mainView  = tab.getWebView();
        // Attach the WebView to the container and then attach the
        // container to the content view.
        FrameLayout wrapper =
                (FrameLayout) container.findViewById(R.id.webview_wrapper);
        ViewGroup parent = (ViewGroup) mainView.getParent();
        if (parent != wrapper) {
            if (parent != null) {
                Log.w(LOGTAG, "mMainView already has a parent in"
                        + " attachTabToContentView!");
                parent.removeView(mainView);
            }
            wrapper.addView(mainView);
        } else {
            Log.w(LOGTAG, "mMainView is already attached to wrapper in"
                    + " attachTabToContentView!");
        }
        parent = (ViewGroup) container.getParent();
        if (parent != mContentView) {
            if (parent != null) {
                Log.w(LOGTAG, "mContainer already has a parent in"
                        + " attachTabToContentView!");
                parent.removeView(container);
            }
            mContentView.addView(container, COVER_SCREEN_PARAMS);
        } else {
            Log.w(LOGTAG, "mContainer is already attached to content in"
                    + " attachTabToContentView!");
        }
        mMyUiController.attachSubWindow(tab);
    }

    @Override
    public void onSetWebView(MyTab tab, WebView webView) {
        RelativeLayout container = tab.getViewContainer();
        if (container == null) {
            // The tab consists of a container view, which contains the main
            // WebView, as well as any other UI elements associated with the tab.
            container = (RelativeLayout)mActivity.getLayoutInflater().inflate(R.layout.tab,
                    mContentView, false);
            tab.setViewContainer(container);
        }
        if (tab.getWebView() != webView) {
            // Just remove the old one.
            FrameLayout wrapper =
                    (FrameLayout) container.findViewById(R.id.webview_wrapper);
            wrapper.removeView(tab.getWebView());
        }
    }
    
    public void setActiveTab(final MyTab tab) {
        attachTabToContentView(tab);
        mActiveTab = tab;
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        final MyTab ct = mTabControl.getCurrentTab();
        if (ct != null) {
            setActiveTab(ct);
        }
    }
    
    private View.OnTouchListener mOpenFullscreenListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean handled = false;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    if (mMenu.isShown()) {
                        mMenu.hide();
                        handled = true;
                    }
//                    if(mTitleBar.getNavigationBar().isEditingUrl()){
//                        mTitleBar.getNavigationBar().stopEditingUrl();
//                        setUrlTitle(getActiveTab());
//                        handled=true;
//                    }
                    if (isTitleBarShowing() && canHideTitleBar()) {
                        hideTitleBar();
                        handled = true;
                    }
            }
            return handled;
        }
    };
    
    
    @Override
    public void showTitleBar() {
        if (canShowTitleBar()) {
            mTitleBar.show();
            mBottomBar.show();
            hideStatusBar(false);
//            mFullscreenSwitcher.setOnTouchListener(mOpenFullscreenListener);
//            mFullscreenSwitcher.bringToFront();
        }
    }
    
    boolean canShowTitleBar() {
        return true;
    }
    
    public MyFullscreenSwitcher mFullscreenSwitcher;
    protected void hideTitleBar() {
//        if(mUiController.isCollectorMode()) {
//            mFullscreenSwitcher.setOnTouchListener(null);
//            return;
//        }
//        if (mTitleBar.isShowing()) {
            hideStatusBar(true);
            mTitleBar.hide();
            mBottomBar.hide();
//            mMenu.hide();
            mFullscreenSwitcher.setOnTouchListener(mCloseFullscreenListener);
            mFullscreenSwitcher.bringToFront();
//            if( null != mNavigationBar){
//                mNavigationBar.hideReadingBar();
//            }
    }
    
    
   
    public void hideStatusBar(boolean enabled) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (enabled) {
            winParams.flags |=  bits;
        } else {
            winParams.flags &= ~bits;
        }
        isFullscreen=enabled;
        win.setAttributes(winParams);
    }

    @Override
    public boolean onkeyDown() {
        return false;
    }

    @Override
    public void suggestHideTitleBar() {
        if (canHideTitleBar()) {
            hideTitleBar();
        }
    }
    
    boolean canHideTitleBar() {
//        return !isLoading() && !isEditingUrl() && !mTitleBar.wantsToBeVisible()
//                && !mNavigationBar.isMenuShowing() && !isHomePageShowing()
//                && BrowserSettings.getInstance().useFullscreen() && isMainViewShowing() || mMyUiController.isFindDialogShowing();
        return mMyUiController.isFindDialogShowing() || (MyBrowserSettings.getInstance(mActivity).useFullscreen() && mTitleBar.isShown());
    }

    @Override
    public boolean dispatchKey(int code, KeyEvent event) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean onBackKeyUp() {
        if(mWindowsManager.mWindowManagerIsShowing){
            mWindowsManager.hidden();
            attachTab(mActiveTab);
            return true;
        }
        if(mMenu.isShown()){
            mMenu.hide();
            return true;
        }
        if (isTitleBarShowing() && canHideTitleBar()) {
            hideTitleBar();
            return true;
        } else{
            showTitleBar();
        }
        return false;
    }

    @Override
    public void setShouldShowErrorConsole(MyTab tab, boolean show) {
        
    }

    @Override
    public void bookmarkedStatusHasChanged(MyTab tab) {
        if (tab.inForeground()) {
            boolean isBookmark = tab.isBookmarkedSite();
            mNavigationBar.setCurrentUrlIsBookmark(isBookmark);
        }
    }
    
    @Override
    public void openWindowManager() {
        mWindowsManager.show();
        detachTab(mActiveTab);
    }
    
    @Override
    public void detachTab(MyTab tab) {
//        removeTabFromContentView(tab);
    }
    public void onConfigurationChanged(Configuration config) {
        
    }
    public boolean onBackKey() {
        return false;
    }
    
    public void invokeShowEfficientReadTipOrNot(){
        
    }
    
    public void invokeBeforeStartEfficientRead(){
        
    }
    
    public void onQuitFullScreen(){
        
    }

    @Override
    public void startSelectArticlesAnimation() {
        
    }

    @Override
    public void setFullscreen(boolean enabled) {
        if (enabled) {
            suggestHideTitleBar();
        } else {
            showTitleBar();
        }
    }
    
    @Override
    public boolean onMenuKey() {
        if(MyBrowserSettings.getInstance(mActivity).useFullscreen()){
            if (isTitleBarShowing()) {
                suggestHideTitleBar();
            } else {
                showTitleBar();
                onQuitFullScreen();
            }
        }
        return true;
    }
    
    protected boolean isTitleBarShowing() {
        return mTitleBar.isShowing();
    }

    @Override
    public void onTabDataChanged(MyTab tab) {
        onProgressChanged(tab);
    }

    @Override
    public void onProgressChanged(MyTab tab) {
        if (tab.inForeground()) {
            int progress = tab.getLoadProgress();
            mTitleBar.setProgress(progress);
        }
    }
    private boolean mInPageLoad;
    
    MyTab getActiveTab() {
        return mActiveTab;
    }

}
