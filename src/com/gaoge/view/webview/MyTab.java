
package com.gaoge.view.webview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.speech.RecognizerResultsIntent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebBackForwardList;
import android.webkit.WebBackForwardListClient;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gaoge.view.practise.utils.UrlUtils;
import com.gaoge.view.webview.interfaces.MyWebViewController;
import com.gaoge.view.webview.reflect.InvokeWebviewMethod;
import com.orange.test.textImage.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

public class MyTab implements PictureListener {

    protected MyWebViewController mWebViewController;
    Context mContext;
    private RelativeLayout mContainer;
    private Vector<MyTab> mChildren;
    private MyTab mParent;
    private WebView mMainView;
    private WebView mSubView;
    private View mSubViewContainer;

    MyTab(MyWebViewController wvcontroller, WebView w) {
        this(wvcontroller, w, null);
    }

    MyTab(MyWebViewController wvcontroller, Bundle state) {
        this(wvcontroller, null, state);
    }

    private Pattern mClearHistoryUrlPattern;

    MyTab(MyWebViewController wvcontroller, WebView w, Bundle state) {
        mWebViewController = wvcontroller;
        mContext = mWebViewController.getContext();
        mDownloadListener = new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                    String contentDisposition, String mimetype,
                    long contentLength) {
                mWebViewController.onDownloadStart(MyTab.this, url, userAgent, contentDisposition,
                        mimetype, contentLength);
            }
        };
        mWebBackForwardListClient = new WebBackForwardListClient() {
            @Override
            public void onNewHistoryItem(WebHistoryItem item) {
                if (isInVoiceSearchMode()) {
                    item.setCustomData(mVoiceSearchData.mVoiceSearchIntent);
                }
                if (mClearHistoryUrlPattern != null) {
                    boolean match =
                            mClearHistoryUrlPattern.matcher(item.getOriginalUrl()).matches();
                    if (match) {
                        if (mMainView != null) {
                            mMainView.clearHistory();
                        }
                    }
                    mClearHistoryUrlPattern = null;
                }
            }

            @Override
            public void onIndexChanged(WebHistoryItem item, int index) {
                Object data = item.getCustomData();
                if (data != null && data instanceof Intent) {
                    activateVoiceSearchMode((Intent) data);
                }
            }
        };

        setWebView(w);
    }

    private boolean mInForeground;

    void activateVoiceSearchMode(Intent intent) {
        int index = 0;
        ArrayList<String> results = intent.getStringArrayListExtra(
                RecognizerResultsIntent.EXTRA_VOICE_SEARCH_RESULT_STRINGS);
        if (results != null) {
            ArrayList<String> urls = intent.getStringArrayListExtra(
                    RecognizerResultsIntent.EXTRA_VOICE_SEARCH_RESULT_URLS);
            ArrayList<String> htmls = intent.getStringArrayListExtra(
                    RecognizerResultsIntent.EXTRA_VOICE_SEARCH_RESULT_HTML);
            ArrayList<String> baseUrls = intent.getStringArrayListExtra(
                    RecognizerResultsIntent
                    .EXTRA_VOICE_SEARCH_RESULT_HTML_BASE_URLS);
            // This tab is now entering voice search mode for the first time, or
            // a new voice search was done.
            int size = results.size();
            if (urls == null || size != urls.size()) {
                throw new AssertionError("improper extras passed in Intent");
            }
            if (htmls == null || htmls.size() != size || baseUrls == null ||
                    (baseUrls.size() != size && baseUrls.size() != 1)) {
                // If either of these arrays are empty/incorrectly sized, ignore
                // them.
                htmls = null;
                baseUrls = null;
            }
            mVoiceSearchData = new VoiceSearchData(results, urls, htmls,
                    baseUrls);
            mVoiceSearchData.mHeaders = intent.getParcelableArrayListExtra(
                    RecognizerResultsIntent
                    .EXTRA_VOICE_SEARCH_RESULT_HTTP_HEADERS);
            mVoiceSearchData.mSourceIsGoogle = intent.getBooleanExtra(
                    VoiceSearchData.SOURCE_IS_GOOGLE, false);
            mVoiceSearchData.mVoiceSearchIntent = new Intent(intent);
        }
        String extraData = intent.getStringExtra(
                SearchManager.EXTRA_DATA_KEY);
        if (extraData != null) {
            index = Integer.parseInt(extraData);
            if (index >= mVoiceSearchData.mVoiceSearchResults.size()) {
                throw new AssertionError("index must be less than "
                        + "size of mVoiceSearchResults");
            }
            if (mVoiceSearchData.mSourceIsGoogle) {
                // Intent logIntent = new Intent(
                // LoggingEvents.ACTION_LOG_EVENT);
                // logIntent.putExtra(LoggingEvents.EXTRA_EVENT,
                // LoggingEvents.VoiceSearch.N_BEST_CHOOSE);
                // logIntent.putExtra(
                // LoggingEvents.VoiceSearch.EXTRA_N_BEST_CHOOSE_INDEX,
                // index);
                // mContext.sendBroadcast(logIntent);
            }
            if (mVoiceSearchData.mVoiceSearchIntent != null) {
                // Copy the Intent, so that each history item will have its own
                // Intent, with different (or none) extra data.
                Intent latest = new Intent(mVoiceSearchData.mVoiceSearchIntent);
                latest.putExtra(SearchManager.EXTRA_DATA_KEY, extraData);
                mVoiceSearchData.mVoiceSearchIntent = latest;
            }
        }
        mVoiceSearchData.mLastVoiceSearchTitle = mVoiceSearchData.mVoiceSearchResults.get(index);
        if (mInForeground) {
            mWebViewController.activateVoiceSearchMode(
                    mVoiceSearchData.mLastVoiceSearchTitle,
                    mVoiceSearchData.mVoiceSearchResults);
        }
        if (mVoiceSearchData.mVoiceSearchHtmls != null) {
            // When index was found it was already ensured that it was valid
            String uriString = mVoiceSearchData.mVoiceSearchHtmls.get(index);
            if (uriString != null) {
                Uri dataUri = Uri.parse(uriString);
                if (RecognizerResultsIntent.URI_SCHEME_INLINE.equals(
                        dataUri.getScheme())) {
                    // If there is only one base URL, use it. If there are
                    // more, there will be one for each index, so use the base
                    // URL corresponding to the index.
                    String baseUrl = mVoiceSearchData.mVoiceSearchBaseUrls.get(
                            mVoiceSearchData.mVoiceSearchBaseUrls.size() > 1 ?
                                    index : 0);
                    mVoiceSearchData.mLastVoiceSearchUrl = baseUrl;
                    mMainView.loadDataWithBaseURL(baseUrl,
                            uriString.substring(RecognizerResultsIntent
                                    .URI_SCHEME_INLINE.length() + 1), "text/html",
                            "utf-8", baseUrl);
                    return;
                }
            }
        }
        mVoiceSearchData.mLastVoiceSearchUrl = mVoiceSearchData.mVoiceSearchUrls.get(index);
        if (null == mVoiceSearchData.mLastVoiceSearchUrl) {
            mVoiceSearchData.mLastVoiceSearchUrl = UrlUtils.smartUrlFilter(
                    mVoiceSearchData.mLastVoiceSearchTitle);
        }
        Map<String, String> headers = null;
        if (mVoiceSearchData.mHeaders != null) {
            int bundleIndex = mVoiceSearchData.mHeaders.size() == 1 ? 0
                    : index;
            Bundle bundle = mVoiceSearchData.mHeaders.get(bundleIndex);
            if (bundle != null && !bundle.isEmpty()) {
                Iterator<String> iter = bundle.keySet().iterator();
                headers = new HashMap<String, String>();
                while (iter.hasNext()) {
                    String key = iter.next();
                    headers.put(key, bundle.getString(key));
                }
            }
        }
        mMainView.loadUrl(mVoiceSearchData.mLastVoiceSearchUrl, headers);
    }

    private VoiceSearchData mVoiceSearchData;

    public boolean isInVoiceSearchMode() {
        return mVoiceSearchData != null;
    }

    private final DownloadListener mDownloadListener;
    private final WebBackForwardListClient mWebBackForwardListClient;
    private Bundle mSavedState;

    void setWebView(WebView w) {
        if (mMainView == w) {
            return;
        }

        mWebViewController.onSetWebView(this, w);

        if (mMainView != null) {
            mMainView.setPictureListener(null);
            if (w != null) {
                syncCurrentState(w, null);
            } else {
                mCurrentState = new PageState(mContext, false);
            }
        }
        mMainView = w;

        if (mMainView != null) {
            mMainView.setWebViewClient(mWebViewClient);
            mMainView.setWebChromeClient(mWebChromeClient);
            mMainView.setDownloadListener(mDownloadListener);
            InvokeWebviewMethod.setWebBackForwardListClient(mMainView, mWebBackForwardListClient);
            MyTabControl tc = mWebViewController.getTabControl();
            if (tc != null && tc.getOnThumbnailUpdatedListener() != null) {
                mMainView.setPictureListener(this);
            }
            if (mSavedState != null) {
                WebBackForwardList restoredState = mMainView.restoreState(mSavedState);
                if (restoredState == null || restoredState.getSize() == 0) {
                    loadUrl(mCurrentState.mOriginalUrl, null);
                }
                mSavedState = null;
                // NOTE: Android WebView restoreState will not restore
                // Javascript
                // interface added by application. So we added it here.
                // Be carefull!!!
                // OrangeHomeLink homeLink = new OrangeHomeLink(mContext);
                // mMainView.addJavascriptInterface(homeLink, "orangeHomeLink");
            }
        }

    }

    private void syncCurrentState(WebView view, String url) {
        // Sync state (in case of stop/timeout)
        mCurrentState.mUrl = view.getUrl();
        if (mCurrentState.mUrl == null) {
            mCurrentState.mUrl = "";
        }
        mCurrentState.mOriginalUrl = view.getOriginalUrl();
        mCurrentState.mTitle = view.getTitle();
        mCurrentState.mFavicon = view.getFavicon();
        if (!URLUtil.isHttpsUrl(mCurrentState.mUrl)) {
            // In case we stop when loading an HTTPS page from an HTTP page
            // but before a provisional load occurred
            mCurrentState.mSecurityState = SecurityState.SECURITY_STATE_NOT_SECURE;
            mCurrentState.mSslCertificateError = null;
        }
        mCurrentState.mIncognito = false;
    }

    public void loadUrl(String url, Map<String, String> headers) {
        if (mMainView != null) {
            // mPageLoadProgress = INITIAL_PROGRESS;
            // mInPageLoad = true;
            // mCurrentState = new PageState(mContext, false, url, null);
            // mWebViewController.onPageStarted(this, mMainView, null);
            mMainView.loadUrl(url, headers);
        }
    }

    /* package */static class VoiceSearchData {
        public VoiceSearchData(ArrayList<String> results,
                ArrayList<String> urls, ArrayList<String> htmls,
                ArrayList<String> baseUrls) {
            mVoiceSearchResults = results;
            mVoiceSearchUrls = urls;
            mVoiceSearchHtmls = htmls;
            mVoiceSearchBaseUrls = baseUrls;
        }

        /*
         * ArrayList of suggestions to be displayed when opening the
         * SearchManager
         */
        public ArrayList<String> mVoiceSearchResults;
        /*
         * ArrayList of urls, associated with the suggestions in
         * mVoiceSearchResults.
         */
        public ArrayList<String> mVoiceSearchUrls;
        /*
         * ArrayList holding content to load for each item in
         * mVoiceSearchResults.
         */
        public ArrayList<String> mVoiceSearchHtmls;
        /*
         * ArrayList holding base urls for the items in mVoiceSearchResults. If
         * non null, this will either have the same size as mVoiceSearchResults
         * or have a size of 1, in which case all will use the same base url
         */
        public ArrayList<String> mVoiceSearchBaseUrls;
        /*
         * The last url provided by voice search. Used for comparison to see if
         * we are going to a page by some method besides voice search.
         */
        public String mLastVoiceSearchUrl;
        /**
         * The last title used for voice search. Needed to update the title bar
         * when switching tabs.
         */
        public String mLastVoiceSearchTitle;
        /**
         * Whether the Intent which turned on voice search mode contained the
         * String signifying that Google was the source.
         */
        public boolean mSourceIsGoogle;
        /**
         * List of headers to be passed into the WebView containing location
         * information
         */
        public ArrayList<Bundle> mHeaders;
        /**
         * The Intent used to invoke voice search. Placed on the WebHistoryItem
         * so that when coming back to a previous voice search page we can again
         * activate voice search.
         */
        public Intent mVoiceSearchIntent;
        /**
         * String used to identify Google as the source of voice search.
         */
        public static String SOURCE_IS_GOOGLE = "android.speech.extras.SOURCE_IS_GOOGLE";
    }

    protected PageState mCurrentState;

    public enum SecurityState {
        // The page's main resource does not use SSL. Note that we use this
        // state irrespective of the SSL authentication state of sub-resources.
        SECURITY_STATE_NOT_SECURE,
        // The page's main resource uses SSL and the certificate is good. The
        // same is true of all sub-resources.
        SECURITY_STATE_SECURE,
        // The page's main resource uses SSL and the certificate is good, but
        // some sub-resources either do not use SSL or have problems with their
        // certificates.
        SECURITY_STATE_MIXED,
        // The page's main resource uses SSL but there is a problem with its
        // certificate.
        SECURITY_STATE_BAD_CERTIFICATE,
    }

    // All the state needed for a page
    protected static class PageState {
        String mUrl;
        String mOriginalUrl;
        String mTitle;
        SecurityState mSecurityState;
        // This is non-null only when mSecurityState is
        // SECURITY_STATE_BAD_CERTIFICATE.
        SslError mSslCertificateError;
        Bitmap mFavicon;
        boolean mIsBookmarkedSite;
        boolean mIncognito;

        PageState(Context c, boolean incognito) {
            mIncognito = incognito;
            if (mIncognito) {
                mOriginalUrl = mUrl = "browser:incognito";
                mTitle = "MyTab-Title1";
            } else {
                mOriginalUrl = mUrl = "";
                mTitle = "MyTab-Title2";
            }
            mSecurityState = SecurityState.SECURITY_STATE_NOT_SECURE;
        }

        PageState(Context c, boolean incognito, String url, Bitmap favicon) {
            mIncognito = incognito;
            mOriginalUrl = mUrl = url;
            if (URLUtil.isHttpsUrl(url)) {
                mSecurityState = SecurityState.SECURITY_STATE_SECURE;
            } else {
                mSecurityState = SecurityState.SECURITY_STATE_NOT_SECURE;
            }
            mFavicon = favicon;
        }

    }

    String getUrl() {
        return UrlUtils.filteredUrl(mCurrentState.mUrl);
    }

    WebView getWebView() {
        return mMainView;
    }

    RelativeLayout getViewContainer() {
        return mContainer;
    }

    void setViewContainer(RelativeLayout container) {
        mContainer = container;
    }

    void addChildTab(MyTab child) {
        if (mChildren == null) {
            mChildren = new Vector<MyTab>();
        }
        mChildren.add(child);
        child.setParent(this);
    }

    void setParent(MyTab parent) {
        if (parent == this) {
            throw new IllegalStateException("Cannot set parent to self!");
        }
        mParent = parent;
    }

    View getSubViewContainer() {
        return mSubViewContainer;
    }

    void setSubViewContainer(View subViewContainer) {
        mSubViewContainer = subViewContainer;
    }

    WebView getSubWebView() {
        return mSubView;
    }

    void setSubWebView(WebView subView) {
        mSubView = subView;
    }

    WebView showDialog(MyWebDialog dialog) {

        RelativeLayout container = null;
        WebView view;
        if (mSubView != null) {
            view = mSubView;
            // container = (LinearLayout) mSubViewContainer.findViewById(
            // R.id.inner_container);
        } else {
            view = mMainView;
            container = mContainer;
        }
        //
        if (dialog.isVisible())
            return view;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
        container.addView(dialog, 1, params);

//        View view_wrapper = container.findViewById(R.id.webview_wrapper);
//        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                view_wrapper.getHeight());
//        
//        view_wrapper.setLayoutParams(params2);
        dialog.setWebView(view);
        dialog.show();
        return view;
    }

    void closeDialog(MyWebDialog dialog) {
        RelativeLayout parent = (RelativeLayout) dialog.getParent();
        if (parent != null)
            parent.removeView(dialog);
    }

    public boolean canGoBack() {
        return mMainView != null ? mMainView.canGoBack() : false;
    }

    public boolean canGoForward() {
        return mMainView != null ? mMainView.canGoForward() : false;
    }

    public void goBack() {
        if (mMainView != null) {
            mMainView.goBack();
        }
    }

    @Override
    public void onNewPicture(WebView arg0, Picture arg1) {

    }

    void putInForeground() {
        if (mInForeground) {
            return;
        }
        mInForeground = true;
        resume();
        MyBrowserActivity activity = (MyBrowserActivity) mWebViewController.getActivity();
        mMainView.setOnLongClickListener(activity);
        if (mSubView != null) {
            mSubView.setOnLongClickListener(activity);
        }
        // Show the pending error dialog if the queue is not empty
        // if (mQueuedErrors != null && mQueuedErrors.size() > 0) {
        // showError(mQueuedErrors.getFirst());
        // }
        mWebViewController.bookmarkedStatusHasChanged(this);
    }

    void resume() {
        if (mMainView != null) {
            // setupHwAcceleration(mMainView);
            // mMainView.onResume();
            InvokeWebviewMethod.onResume(mMainView);
            if (mSubView != null) {
                // mSubView.onResume();
                InvokeWebviewMethod.onResume(mSubView);
            }
        }
    }

    void putInBackground() {
        if (!mInForeground) {
            return;
        }
        // capture();
        mInForeground = false;
        pause();
        mMainView.setOnLongClickListener(null);
        if (mSubView != null) {
            mSubView.setOnLongClickListener(null);
        }
    }

    void pause() {
        if (mMainView != null) {
            InvokeWebviewMethod.onPause(mMainView);
            if (mSubView != null) {
                InvokeWebviewMethod.onPause(mSubView);
            }
        }
    }

    public boolean isSnapshot() {
        return false;
    }

    boolean inForeground() {
        return true;
    }

    public boolean isBookmarkedSite() {
        return mCurrentState.mIsBookmarkedSite;
    }

    int getLoadProgress() {
        if (mInPageLoad) {
            return mPageLoadProgress;
        }
        return 100;
    }

    private boolean mInPageLoad;
    private int mPageLoadProgress;

    private final WebViewClient mWebViewClient = new WebViewClient() {

        public void onReceivedError(WebView view, int errorCode, String description,
                String failingUrl) {
            Toast.makeText(mContext, "Oh no! " + description, Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mInPageLoad = true;
            
            mCurrentState = new PageState(mContext,
                    false, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            mInPageLoad = false;
            mWebViewController.onPageFinished(MyTab.this);
        }
    };
    
    private final WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mPageLoadProgress = newProgress;
            mWebViewController.onProgressChanged(MyTab.this);
        }
    };
    
    WebView getTopWindow() {
        if (mSubView != null) {
            return mSubView;
        }
        return mMainView;
    }
    
    public void revertVoiceSearchMode() {
        if (mVoiceSearchData != null) {
            mVoiceSearchData = null;
            if (mInForeground) {
                mWebViewController.revertVoiceSearchMode(this);
            }
        }
    }
    
    String getTitle() {
        if (mCurrentState.mTitle == null && mInPageLoad) {
            return mContext.getString(R.string.title_bar_loading);
        }
        return mCurrentState.mTitle;
    }

}
