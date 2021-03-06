
package com.gaoge.view.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;

import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.practise.utils.Utils;
import com.gaoge.view.webview.MyHomeLinks.HomeLinkActions;
import com.gaoge.view.webview.interfaces.MyUI;
import com.gaoge.view.webview.interfaces.MyUiController;
import com.gaoge.view.webview.interfaces.MyWebViewController;
import com.gaoge.view.webview.interfaces.MyWebViewFactory;
import com.gaoge.view.webview.reflect.InvokeWebviewMethod;
import com.orange.test.textImage.R;

import dep.android.provider.Browser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class MyController implements MyUiController, MyWebViewController {

    private Activity mActivity;
    private MyUI mUi;
    private static MyTabControl mTabControl;
    private MyWebViewFactory mFactory;
    private static Handler mHandler;
    
 // public message ids
    public final static int LOAD_URL = 1001;
    public final static int STOP_LOAD = 1002;
    public final static int LOAD_BACK = 1003;
    public final static int LOAD_JAVASCRIPT = 1004;

    // Message Ids
    private static final int FOCUS_NODE_HREF = 102;
    private static final int RELEASE_WAKELOCK = 107;

    static final int UPDATE_BOOKMARK_THUMBNAIL = 108;

    private static final int OPEN_BOOKMARKS = 201;

    private static final int EMPTY_MENU = -1;
    private static final int SAVE_SUCCESS=0;
    private static final int SAVE_FAIL=1;
    private MyBrowserSettings mSettings;


    final static int PREFERENCES_PAGE = 3;

    public MyController(Activity act) {
        this(act, false);
    }

    public MyController(Activity act, boolean preloadCrashState) {
        mActivity = act;
        mTabControl = new MyTabControl(this);
        mFactory = new MyBrowserWebViewFactory(act);
        mSettings = MyBrowserSettings.getInstance(act);
        mSettings.setController(this);
        
        startHandler();
    }

    private void startHandler() {
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
//                    case OPEN_BOOKMARKS:
//                        bookmarksOrHistoryPicker(ComboViews.Bookmarks);
//                        break;
//                    case FOCUS_NODE_HREF:
//                    {
//                        String url = (String) msg.getData().get("url");
//                        String title = (String) msg.getData().get("title");
//                        String src = (String) msg.getData().get("src");
//                        if (url == "") url = src; // use image if no anchor
//                        if (TextUtils.isEmpty(url)) {
//                            break;
//                        }
//                        HashMap focusNodeMap = (HashMap) msg.obj;
//                        WebView view = (WebView) focusNodeMap.get("webview");
//                        // Only apply the action if the top window did not change.
//                        if (getCurrentTopWebView() != view) {
//                            break;
//                        }
//                        switch (msg.arg1) {
//                            case R.id.open_context_menu_id:
//                                loadUrlFromContext(url);
//                                break;
//                            case R.id.bookmark_context_menu_id:
//                                Intent intent = new Intent(mActivity,
//                                        AddBookmarkPage.class);
//                                intent.putExtra("url", url);
//                                intent.putExtra("title", title);
//                                mActivity.startActivity(intent);
//                                break;
//                            case R.id.share_link_context_menu_id:
//                                // See if this site has been visited before
//                                StringBuilder sb = new StringBuilder(
//                                        Browser.BookmarkColumns.URL + " = ");
//                                DatabaseUtils.appendEscapedSQLString(sb, url);
//                                Cursor c = mActivity.getContentResolver().query(Browser.BOOKMARKS_URI,
//                                        Browser.HISTORY_PROJECTION,
//                                        sb.toString(),
//                                        null,
//                                        null);
//                                if (c.moveToFirst()) {
//                                    // The site has been visited before, so grab the
//                                    // info from the database.
//                                    Bitmap favicon = null;
//                                    Bitmap thumbnail = null;
//                                    String linkTitle = c.getString(Browser.
//                                            HISTORY_PROJECTION_TITLE_INDEX);
//                                    byte[] data = c.getBlob(Browser.
//                                            HISTORY_PROJECTION_FAVICON_INDEX);
//                                    if (data != null) {
//                                        favicon = BitmapFactory.decodeByteArray(
//                                                data, 0, data.length);
//                                    }
//                                    data = c.getBlob(Browser.
//                                            HISTORY_PROJECTION_THUMBNAIL_INDEX);
//                                    if (data != null) {
//                                        thumbnail = BitmapFactory.decodeByteArray(
//                                                data, 0, data.length);
//                                    }
//                                    sharePage(mActivity,
//                                            linkTitle, url, favicon, thumbnail);
//                                } else {
//                                    Browser.sendString(mActivity, url,
//                                            mActivity.getString(
//                                            R.string.choosertitle_sharevia));
//                                }
//                                break;
//                            case R.id.view_image_context_menu_id:
//                                loadUrlFromContext(src);
//                                break;
//                            case R.id.open_newtab_context_menu_id:
//                                final Tab parent = mTabControl.getCurrentTab();
//                                openTab(url, parent,
//                                        !mSettings.openInBackground(), true);
//                                break;
//                            case R.id.copy_link_context_menu_id:
//                                copy(url);
//                                break;
//                            case R.id.save_link_context_menu_id:
//                            case R.id.download_context_menu_id:
//                                DownloadHandler.onDownloadStartNoStream(
//                                        mActivity, url, null, null, null,
//                                        false);
//                                break;
//                            case R.id.home_edit_id:
//                            case R.id.home_clear_id:
//                                //no need
//                                break;
//                        }
//                        break;
//                    }
//
//                    case LOAD_URL:
//                        loadUrlFromContext((String) msg.obj);
//                        break;
                        
                    case LOAD_BACK:
                        MyTab currentTab = (MyTab) msg.obj;
                        if (currentTab != null) {
                            currentTab.goBack();
                        }
                        break;
//
//                    case LOAD_JAVASCRIPT:
//                        String js = (String) msg.obj;
//                        mTabControl.getCurrentTab().loadJavaScript(js);
//                        break;
//                        
//                    case STOP_LOAD:
//                        stopLoading();
//                        break;
//
//                    case RELEASE_WAKELOCK:
//                        if (mWakeLock != null && mWakeLock.isHeld()) {
//                            mWakeLock.release();
//                            // if we reach here, Browser should be still in the
//                            // background loading after WAKELOCK_TIMEOUT (5-min).
//                            // To avoid burning the battery, stop loading.
//                            mTabControl.stopAllLoading();
//                        }
//                        break;
//
//                    case UPDATE_BOOKMARK_THUMBNAIL:
//                        Tab tab = (Tab) msg.obj;
//                        if (tab != null) {
//                            updateScreenshot(tab);
//                        }
//                        break;
                }
            }
        };

    }

    @Override
    public MyTabControl getTabControl() {
        // TODO Auto-generated method stub
        return mTabControl;
    }

    public void setUi(MyUI ui) {
        mUi = ui;
    }

    void start(final Bundle icicle, final Intent intent) {
        boolean noCrashRecovery = intent.getBooleanExtra(Constants.NO_CRASH_RECOVERY, false);
        if (icicle != null || noCrashRecovery) {
            doStart(icicle, intent, false);
        } else {
            doStart(icicle, intent, false);
        }
    }

    void doStart(final Bundle icicle, final Intent intent, final boolean fromCrash) {
        final boolean restoreIncognitoTabs = false;

        final long currentTabId = -1;

        onPreloginFinished(icicle, intent, currentTabId, restoreIncognitoTabs,
                fromCrash);
    }

    private void onPreloginFinished(Bundle icicle, Intent intent, long currentTabId,
            boolean restoreIncognitoTabs, boolean fromCrash) {
        if (currentTabId == -1) {
            MyTab t = null;
            t = openTabToHomePage(false);
        } else {

        }

    }

    public MyTab openTabToHomePage(boolean useCurrentTab) {
        if (!useCurrentTab)
            return openTab(Constants.URL, false, true, false);
        else {
            MyTab t = mTabControl.getCurrentTab();
            loadUrl(t, Constants.URL);
            return t;
        }
        
    }
    
    private void setExtraWebViewSettings(WebView webview){
        LogHelper.d(LogHelper.TAG_WEBSETTINGS,"@@@@@@@@@@@@@@@ MyController,setExtraWebViewSettings");
        webview.setOnLongClickListener((MyBrowserActivity)mActivity);
    }

    public MyTab openTab(String url, boolean incognito, boolean setActive,
            boolean useCurrent) {
        return openTab(url, incognito, setActive, useCurrent, null);
    }

    public MyTab openTab(String url, boolean incognito, boolean setActive,
            boolean useCurrent, MyTab parent) {
        MyTab tab = createNewTab(incognito, setActive, useCurrent);
        if (tab != null) {
            if (parent != null && parent != tab) {
                parent.addChildTab(tab);
            }
            if (url != null) {
                loadUrl(tab, url);
            }
        }
        return tab;
    }

    public void setActiveTab(MyTab tab) {
        // monkey protection against delayed start
        if (tab != null) {
            mTabControl.setCurrentTab(tab);
            // mUi.setActiveTab(tab);
        }
        // mUi.updateBookmarkIcons();
    }

    private MyTab createNewTab(boolean incognito, boolean setActive,
            boolean useCurrent) {
        MyTab tab = null;
        if (mTabControl.canCreateNewTab()) {
            tab = mTabControl.createNewTab(incognito);
            if (setActive) {
                setActiveTab(tab);
            }
        } else {
            if (useCurrent) {
                tab = mTabControl.getCurrentTab();
                reuseTab(tab);
            } else {
                // mUi.showMaxTabsWarning();
            }
        }
        return tab;
    }

    protected void reuseTab(MyTab appTab) {

    }

    public void loadUrl(MyTab tab, String url) {
        loadUrl(tab, url, null);
    }

    int getMaxTabs() {
        return Constants.MAX_TAB_NUM;
    }

    public Activity getActivity() {
        return mActivity;
    }

    protected void loadUrl(MyTab tab, String url, Map<String, String> headers) {
        if (tab != null) {
            setExtraWebViewSettings(tab.getWebView());
            // dismissSubWindow(tab);
            tab.loadUrl(url, headers);
            // mUi.onProgressChanged(tab);
        }
    }

    public MyWebViewFactory getWebViewFactory() {
        return mFactory;
    }

    @Override
    public Context getContext() {
        // TODO Auto-generated method stub
        return mActivity;
    }

    // public boolean onKeyDown(int keyCode, KeyEvent event) {
    // if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
    // mWebView.goBack();
    // return true;
    // }
    // return super.onKeyDown(keyCode, event);
    // }

    @Override
    public void onSetWebView(MyTab tab, WebView view) {
        mUi.onSetWebView(tab, view);
    }

    @Override
    public void attachSubWindow(MyTab tab) {

    }

    public void onResume() {
        mUi.onResume();
        if(null != mFindDialog && mFindDialog.isVisible()){
            mFindDialog.getEditText().requestFocus();
        }
    }

    protected void onWindowFocusChanged(boolean hasFocus) {
//      if (hasFocus) {
//            mUi.suggestHideTitleBar();
//        } else {
//            mUi.showTitleBar();
//        }
    }

    @Override
    public void openSetting() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(mActivity, MyBrowserPreferencesPage.class);
        // intent.putExtra(MyBrowserPreferencesPage.CURRENT_PAGE,
        // getCurrentTopWebView().getUrl());
        mActivity.startActivityForResult(intent, PREFERENCES_PAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode,
            Intent intent) {

    }

    @Override
    public void exitBrowser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setIcon(R.drawable.df_popup_dialog_alert);
        builder.setTitle(R.string.exit_browser_title);
        builder.setMessage(R.string.exit_browser_msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mActivity.finish();
                        // android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog exitDialog = builder.create();
        exitDialog.show();
    }

    public MyUI getUi() {
        return mUi;
    }

    private boolean mBlockEvents;
    
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mBlockEvents;
    }
    
    private boolean mMenuIsDown;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(mFindDialog != null && mFindDialog.isVisible()){
            closeDialogs();
            return true;
        }
        if (KeyEvent.KEYCODE_MENU == keyCode) {
            mMenuIsDown = true;
            if (event.getRepeatCount() == 0) {
                event.startTracking();
                return true;
            }
            return false;
        }
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (event.getRepeatCount() == 0) {
                event.startTracking();
                return true;
            }
        }
        
        return mUi.dispatchKey(keyCode, event);
    }

    protected boolean onMenuKey() {
        return mUi.onMenuKey();
    }
    
    boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_MENU == keyCode) {
            mMenuIsDown = false;
            if (event.isTracking() && !event.isCanceled()) {
                return onMenuKey();
            }
        }
        
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (event.isTracking() && !event.isCanceled()) {
                    onBackKeyUp();
                    return true;
                }
                break;
        }
        return false;
    }
    protected void onBackKeyUp() {
        if (!mUi.onBackKeyUp()) {
            WebView subwindow = mTabControl.getCurrentSubWindow();
            if (subwindow != null) {
                if (subwindow.canGoBack()) {
                    subwindow.goBack();
                } else {
//                    dismissSubWindow(mTabControl.getCurrentTab());
                }
            } else {
                goBackOnePageOrQuit();
            }
        }
    }
    
    void goBackOnePageOrQuit() {
        MyTab current = mTabControl.getCurrentTab();
        if (current == null) {
            mActivity.moveTaskToBack(true);
            return;
        }
        if (current.canGoBack()) {
            mHandler.sendMessageDelayed(mHandler.obtainMessage(
                    LOAD_BACK, 0, 0, current),
                    100);
        } else {
//            MyTab parent = current.getParent();
//            if (parent != null) {
//                switchToTab(parent);
//                closeTab(current);
//            } else {
//                if ((current.getAppId() != null) || current.closeOnBack()) {
//                    closeCurrentTab(true);
//                }
//                mActivity.moveTaskToBack(true);
//            }
            exitBrowser();
        }
        
    }

    @Override
    public void openFindDialog() {
        showFindDialogInDiffSDK();
    }

    public void showFindDialogInDiffSDK() {
        mUi.suggestHideTitleBar();
        if (Utils.isPlatformHoneycombAndAbove()) {
            showFindDialogOnHoneycombAndAbove();
        } else {
            showFindDialogOnFroyoAndGingerbread();
        }
    }

    public void showFindDialogOnHoneycombAndAbove() {
         InvokeWebviewMethod.showFindDialog(getCurrentTopWebView(), null,
         true);
    }

    private MyFindDialog mFindDialog;

    WebView wv;

    public void showFindDialogOnFroyoAndGingerbread() {
        if (null == mFindDialog) {
            mFindDialog = new MyFindDialog(this);
        }
        wv = showDialog(mFindDialog);
        InvokeWebviewMethod.setFindIsUp(wv, true);
    }

    private WebView showDialog(MyWebDialog dialog) {
        MyTab tab = mTabControl.getCurrentTab();
        MyBaseUi ui = (MyBaseUi)mUi;
        return tab.showDialog(dialog);
    }

    public void closeDialogs() {
        // if (!(closeDialog(mFindDialog) || closeDialog(mSelectDialog)))
        // return;
        if (!(closeDialog(mFindDialog)))
            return;
        // If the Find was being performed in the main WebView, replace the
        // embedded title bar.
        MyTab currentTab = mTabControl.getCurrentTab();
        if (currentTab.getSubWebView() == null) {
            WebView mainView = currentTab.getWebView();
            if (mainView != null) {
                // mainView.setEmbeddedTitleBar(mTitleBar);
                // when close the FindDialog,remove the highlight
                try {
                    Method m = WebView.class.getDeclaredMethod("setFindIsUp", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(mainView, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
//        if (mUi.isHomePageShowing() && mUi.isFullscreen()) {
            mUi.showTitleBar();
//        }
    }
    
    private boolean closeDialog(MyWebDialog dialog) {
        if (null == dialog || !dialog.isVisible()) return false;
        MyTab currentTab = mTabControl.getCurrentTab();
        dialog.dismiss();
        currentTab.closeDialog(dialog);
        //add by gaoge 2012-03-21,for fix bug which when in No-full-screen mode,when close Find on page,should show the titlebar immediately
//        if(!BrowserSettings.getInstance().useFullscreen()){
//            mUi.showTitleBar();
//        }
        return true;
    }

    @Override
    public boolean isFindDialogShowing() {
        if(null != mFindDialog){
            return mFindDialog.isVisible();
        }
       return false;
    }
    private boolean mShouldShowErrorConsole;
    protected void setShouldShowErrorConsole(boolean show) {
        if (show == mShouldShowErrorConsole) {
            // Nothing to do.
            return;
        }
        mShouldShowErrorConsole = show;
        MyTab t = mTabControl.getCurrentTab();
        if (t == null) {
            return;
        }
        mUi.setShouldShowErrorConsole(t, show);
    }

    @Override
    public void onDownloadStart(MyTab tab, String url, String useragent, String contentDisposition,
            String mimeType, long contentLength) {
        
    }

    @Override
    public void activateVoiceSearchMode(String title, List<String> results) {
        
    }
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void bookmarkedStatusHasChanged(MyTab tab) {
        mUi.bookmarkedStatusHasChanged(tab);
    }

    @Override
    public void onPageFinished(MyTab tab) {
        mUi.onTabDataChanged(tab);
    }
    
    @Override
    public void onProgressChanged(MyTab tab) {
        mUi.onProgressChanged(tab);
    }
    
    public static void manageHomeLink(JSONObject json, HomeLinkActions action) {
        StringBuilder buf=new StringBuilder("javascript:");
        
        try {
            if (action == HomeLinkActions.Clear) {
                json.put("action", "clear");
            } else if (action == HomeLinkActions.Edit) {
                json.put("action", "edit");
            } else if (action == HomeLinkActions.Add) {
                json.put("action", "add");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        buf.append("managehomelink(");
        buf.append(String.valueOf(json.toString()));
        buf.append(")");

//        postMessage(LOAD_JAVASCRIPT, 0, 0, buf.toString(), 0);
    }
    
    protected void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        if (v instanceof MyTitleBar) {
            return;
        }
        if (!(v instanceof WebView)) {
            return;
        }
        
        MenuInflater inflater = mActivity.getMenuInflater();
        inflater.inflate(R.menu.browsercontext, menu);
        
    }
    
    static final void sharePage(Context c, String title, String url,
            Bitmap favicon, Bitmap screenshot) {
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, url);
        send.putExtra(Intent.EXTRA_SUBJECT, title);
        send.putExtra(Browser.EXTRA_SHARE_FAVICON, favicon);
        send.putExtra(Browser.EXTRA_SHARE_SCREENSHOT, screenshot);
        try {
            c.startActivity(Intent.createChooser(send, c.getString(
                    R.string.choosertitle_sharevia)));
        } catch(android.content.ActivityNotFoundException ex) {
            // if no app handles it, do nothing
        }
    }
    
    static int getDesiredThumbnailWidth(Context context) {
        return context.getResources().getDimensionPixelOffset(
                R.dimen.bookmarkThumbnailWidth);
    }

    @Override
    public WebView getCurrentTopWebView() {
       return mTabControl.getCurrentTopWebView();
    }

    @Override
    public void revertVoiceSearchMode(MyTab tab) {
        
    }
    
}
