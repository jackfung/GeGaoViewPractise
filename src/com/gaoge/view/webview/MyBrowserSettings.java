
package com.gaoge.view.webview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebSettings.ZoomDensity;

import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.webview.interfaces.MyPreferenceKeys;
import com.gaoge.view.webview.reflect.MyInvokeWebSettingsMethod;
import com.gaoge.view.webview.search.MySearchEngine;
import com.gaoge.view.webview.search.MySearchEngineUtils;
import com.orange.test.textImage.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.WeakHashMap;

public class MyBrowserSettings extends Observable implements OnSharedPreferenceChangeListener,
        MyPreferenceKeys {

    private final static String TAG = "BrowserSettings";
    private boolean useWideViewPort = true;
    private int userAgent = 0;
    private boolean tracing = false;
    private boolean lightTouch = false;
    private boolean navDump = false;
    private boolean loadsImagesAutomatically;
    private boolean javaScriptEnabled;
    private WebSettings.PluginState pluginState;
    private boolean javaScriptCanOpenWindowsAutomatically;
    private boolean showSecurityWarnings;
    private boolean rememberPasswords;
    private boolean saveFormData;
    private boolean openInBackground;
    private String defaultTextEncodingName;
    private String homeUrl = "";
    // private MySearchEngine searchEngine;
    private boolean autoFitPage;
    private boolean landscapeOnly;
    private boolean loadsPageInOverviewMode;
    // HTML5 API flags
    private boolean appCacheEnabled;
    private boolean databaseEnabled;
    private boolean domStorageEnabled;
    private boolean geolocationEnabled;
    private boolean workersEnabled;
    private boolean showDebugSettings;

    private static MyBrowserSettings mInstance;
    private Context mContext;
    private SharedPreferences mPrefs;
    private LinkedList<WeakReference<WebSettings>> mManagedSettings;
    private WeakHashMap<WebSettings, String> mCustomUserAgents;
    
    private MyWebStorageSizeManager mWebStorageSizeManager;
    
    private MyController mController;
    private static boolean sInitialized = false;
    
    private float mFontSizeMult = 1.0f;
    
    public final static int MAX_TEXTVIEW_LEN = 80;
    
    
    public void setController(MyController controller) {
        mController = controller;
        if (sInitialized) {
            syncSharedSettings();
        }
    }
    
    public MyBrowserSettings(){
        
    }

    public static MyBrowserSettings getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new MyBrowserSettings(context);
        }
        return mInstance;
    }

    private MyBrowserSettings(Context context) {
        mContext = context.getApplicationContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        // mAutofillHandler = new AutofillHandler(mContext);
        mManagedSettings = new LinkedList<WeakReference<WebSettings>>();
        mCustomUserAgents = new WeakHashMap<WebSettings, String>();
        // mAutofillHandler.asyncLoadFromDb();
        MyBackgroundHandler.execute(mSetup);
        init();
    }
    private String mAppCachePath;
    private String getAppCachePath() {
        if (mAppCachePath == null) {
            mAppCachePath = mContext.getDir("appcache", 0).getPath();
        }
        return mAppCachePath;
    }
    public void setDebugEnabled(boolean value) {
        Editor edit = mPrefs.edit();
        edit.putBoolean(PREF_DEBUG_MENU, value);
        if (!value) {
            edit.putBoolean(PREF_ENABLE_HARDWARE_ACCEL_SKIA, false);
        }
        edit.commit();
    }
    
    private TextSize getTextSize() {
        String textSize = mPrefs.getString(PREF_TEXT_SIZE, "NORMAL");
        return TextSize.valueOf(textSize);
    }
    
    public void setTextZoom(int percent) {
      mPrefs.edit().putInt(PREF_TEXT_ZOOM, getRawTextZoom(percent)).commit();
    }
    static int getRawTextZoom(int percent) {
        return (percent - 100) / TEXT_ZOOM_STEP + TEXT_ZOOM_START_VAL;
    }
    
    private static final int TEXT_ZOOM_STEP = 5;
    private static final int TEXT_ZOOM_START_VAL = 10;
    private static String sFactoryResetUrl;
    
    private Runnable mSetup = new Runnable() {

        @Override
        public void run() {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            mFontSizeMult = metrics.scaledDensity / metrics.density;
            mWebStorageSizeManager = new MyWebStorageSizeManager(mContext,
                    new MyWebStorageSizeManager.StatFsDiskInfo(getAppCachePath()),
                    new MyWebStorageSizeManager.WebKitAppCacheInfo(getAppCachePath()));
            // Workaround b/5253777
//            CookieManager.getInstance().acceptCookie();
            // Workaround b/5254577
            mPrefs.registerOnSharedPreferenceChangeListener(MyBrowserSettings.this);
            if (Build.VERSION.CODENAME.equals("REL")) {
                // This is a release build, always startup with debug disabled
                setDebugEnabled(false);
            }
            if (mPrefs.contains(PREF_TEXT_SIZE)) {
                /*
                 * Update from TextSize enum to zoom percent
                 * SMALLEST is 50%
                 * SMALLER is 75%
                 * NORMAL is 100%
                 * LARGER is 150%
                 * LARGEST is 200%
                 */
                switch (getTextSize()) {
                case SMALLEST:
                    setTextZoom(50);
                    break;
                case SMALLER:
                    setTextZoom(75);
                    break;
                case LARGER:
                    setTextZoom(150);
                    break;
                case LARGEST:
                    setTextZoom(200);
                    break;
                }
                mPrefs.edit().remove(PREF_TEXT_SIZE).commit();
//                mPrefs.edit().remove(PREF_TEXT_SIZE).apply();
            }

            sFactoryResetUrl = mContext.getResources().getString(R.string.homepage_base_base);

            synchronized (MyBrowserSettings.class) {
                sInitialized = true;
                MyBrowserSettings.class.notifyAll();
            }
        }
    };

    private void init() {
        // Private variables for settings
        // NOTE: these defaults need to be kept in sync with the XML
        // until the performance of PreferenceManager.setDefaultValues()
        // is improved.
        loadsImagesAutomatically = true;
        javaScriptEnabled = true;
        pluginState = WebSettings.PluginState.ON;
        javaScriptCanOpenWindowsAutomatically = false;
        showSecurityWarnings = true;
        rememberPasswords = true;
        saveFormData = true;
        openInBackground = false;
        autoFitPage = true;
        landscapeOnly = false;
        loadsPageInOverviewMode = true;
        showDebugSettings = false;
        // HTML5 API flags
        appCacheEnabled = true;
        databaseEnabled = true;
        domStorageEnabled = true;
        geolocationEnabled = true;
        workersEnabled = true; // only affects V8. JSC does not have a similar
                               // setting
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PREF_FULLSCREEN.equals(key)) {
            if (mController.getUi() != null) {
                mController.getUi().setFullscreen(useFullscreen());
            }
        } 
    }

    private static WebSettings.TextSize textSize =
            WebSettings.TextSize.NORMAL;
    private static WebSettings.ZoomDensity zoomDensity =
            WebSettings.ZoomDensity.MEDIUM;

    void syncSharedPreferences(Context ctx, SharedPreferences p) {
        textSize = WebSettings.TextSize.valueOf(
                p.getString(PREF_TEXT_SIZE, textSize.name()));
        zoomDensity = WebSettings.ZoomDensity.valueOf(
                p.getString(PREF_DEFAULT_ZOOM, zoomDensity.name()));
        
        update();
    }
    
    void update() {
        setChanged();
        notifyObservers();
    }
    
    private HashMap<WebSettings,Observer> mWebSettingsToObservers =
            new HashMap<WebSettings,Observer>();
    
    public Observer addObserver(WebSettings s) {
        Observer old = mWebSettingsToObservers.get(s);
        if (old != null) {
            super.deleteObserver(old);
        }
        Observer o = new Observer(s);
        mWebSettingsToObservers.put(s, o);
        super.addObserver(o);
        return o;
    }
    
    static class Observer implements java.util.Observer {

        private WebSettings mSettings;

        Observer(WebSettings w) {
            mSettings = w;
        }
        
        @Override
        public void update(Observable observable, Object data) {
            MyBrowserSettings b = (MyBrowserSettings)observable;
            WebSettings s = mSettings;
            s.setTextSize(b.textSize);
            s.setDefaultZoom(b.zoomDensity);
            LogHelper.d(TAG,"@@@@@@@@@@@@@@@@ Observer update(),b.textSize: "
                    + b.textSize + ",b.zoomDensity: " + b.zoomDensity);
        }
        
    }
    private boolean mNeedsSharedSync = true;
    
    public void startManagingSettings(WebSettings settings) {
        if (mNeedsSharedSync) {
            syncSharedSettings();
        }
        synchronized (mManagedSettings) {
            syncStaticSettings(settings);
            syncSetting(settings);
            mManagedSettings.add(new WeakReference<WebSettings>(settings));
        }
    }
    
    public boolean enableGeolocation() {
        return mPrefs.getBoolean(PREF_ENABLE_GEOLOCATION, true);
    }
    
    public String getSearchEngineName() {
        return mPrefs.getString(PREF_SEARCH_ENGINE, MySearchEngine.GOOGLE);
    }

    public boolean openInBackground() {
        return mPrefs.getBoolean(PREF_OPEN_IN_BACKGROUND, false);
    }

    public boolean enableJavascript() {
        return mPrefs.getBoolean(PREF_ENABLE_JAVASCRIPT, true);
    }
    
    public boolean enableLightTouch() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_ENABLE_LIGHT_TOUCH, false);
    }

    public boolean enableNavDump() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_ENABLE_NAV_DUMP, false);
    }

    public String getJsEngineFlags() {
        if (!isDebugEnabled()) {
            return "";
        }
        return mPrefs.getString(PREF_JS_ENGINE_FLAGS, "");
    }
    
    public String getDefaultTextEncoding() {
        return mPrefs.getString(PREF_DEFAULT_TEXT_ENCODING, null);
    }

    // -----------------------------
    // getter/setters for general_preferences.xml
    // -----------------------------

    public String getHomePage() {
        return mPrefs.getString(PREF_HOMEPAGE, getFactoryResetHomeUrl(mContext));
    }
    
    public static String getFactoryResetHomeUrl(Context context) {
        requireInitialization();
        return sFactoryResetUrl;
    }
    
    public ZoomDensity getDefaultZoom() {
        String zoom = mPrefs.getString(PREF_DEFAULT_ZOOM, "MEDIUM");
        return ZoomDensity.valueOf(zoom);
    }
    
    public int getMinimumFontSize() {
        int minFont = mPrefs.getInt(PREF_MIN_FONT_SIZE, 0);
        return getAdjustedMinimumFontSize(minFont);
    }
    private static final int MIN_FONT_SIZE_OFFSET = 5;
    
    public static int getAdjustedMinimumFontSize(int rawValue) {
        rawValue++; // Preference starts at 0, min font at 1
        if (rawValue > 1) {
            rawValue += (MIN_FONT_SIZE_OFFSET - 2);
        }
        return rawValue;
    }
    
    public PluginState getPluginState() {
        String state = mPrefs.getString(PREF_PLUGIN_STATE, "ON");
        return PluginState.valueOf(state);
    }
    
    public boolean autofitPages() {
        return mPrefs.getBoolean(PREF_AUTOFIT_PAGES, true);
    }
    
    public boolean isSmallScreen() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_SMALL_SCREEN, false);
    }
    
    public boolean isNormalLayout() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_NORMAL_LAYOUT, false);
    }
    
    public LayoutAlgorithm getLayoutAlgorithm() {
        LayoutAlgorithm layoutAlgorithm = LayoutAlgorithm.NORMAL;
        if (autofitPages()) {
            layoutAlgorithm = LayoutAlgorithm.NARROW_COLUMNS;
        }
        if (isDebugEnabled()) {
            if (isSmallScreen()) {
                layoutAlgorithm = LayoutAlgorithm.SINGLE_COLUMN;
            } else {
                if (isNormalLayout()) {
                    layoutAlgorithm = LayoutAlgorithm.NORMAL;
                } else {
                    layoutAlgorithm = LayoutAlgorithm.NARROW_COLUMNS;
                }
            }
        }
        return layoutAlgorithm;
    }
    
    public boolean blockPopupWindows() {
        return mPrefs.getBoolean(PREF_BLOCK_POPUP_WINDOWS, true);
    }

    public boolean loadImages() {
        return mPrefs.getBoolean(PREF_LOAD_IMAGES, true);
    }
    
    public boolean loadPageInOverviewMode() {
        return mPrefs.getBoolean(PREF_LOAD_PAGE, true);
    }
    
    public boolean rememberPasswords() {
        return mPrefs.getBoolean(PREF_REMEMBER_PASSWORDS, true);
    }
    
    public boolean saveFormdata() {
        return mPrefs.getBoolean(PREF_SAVE_FORMDATA, true);
    }
    
    public boolean isWideViewport() {
        if (!isDebugEnabled()) {
            return true;
        }
        return mPrefs.getBoolean(PREF_WIDE_VIEWPORT, true);
    }
    
    private static final String DESKTOP_USERAGENT = "Mozilla/5.0 (X11; " +
            "Linux x86_64) AppleWebKit/534.24 (KHTML, like Gecko) " +
            "Chrome/11.0.696.34 Safari/534.24";

        private static final String IPHONE_USERAGENT = "Mozilla/5.0 (iPhone; U; " +
            "CPU iPhone OS 4_0 like Mac OS X; en-us) AppleWebKit/532.9 " +
            "(KHTML, like Gecko) Version/4.0.5 Mobile/8A293 Safari/6531.22.7";

        private static final String IPAD_USERAGENT = "Mozilla/5.0 (iPad; U; " +
            "CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 " +
            "(KHTML, like Gecko) Version/4.0.4 Mobile/7B367 Safari/531.21.10";

        private static final String FROYO_USERAGENT = "Mozilla/5.0 (Linux; U; " +
            "Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 " +
            "(KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

        private static final String HONEYCOMB_USERAGENT = "Mozilla/5.0 (Linux; U; " +
            "Android 3.1; en-us; Xoom Build/HMJ25) AppleWebKit/534.13 " +
            "(KHTML, like Gecko) Version/4.0 Safari/534.13";
        
    private static final String USER_AGENTS[] = { null,
        DESKTOP_USERAGENT,
        IPHONE_USERAGENT,
        IPAD_USERAGENT,
        FROYO_USERAGENT,
        HONEYCOMB_USERAGENT,
    };
    
    public int getUserAgent() {
        if (!isDebugEnabled()) {
            return 0;
        }
        return Integer.parseInt(mPrefs.getString(PREF_USER_AGENT, "0"));
    }
    
    private void syncSetting(WebSettings settings) {
        LogHelper.d(LogHelper.TAG_FIND, "$$$$$$$$$$$$$$$$$$ MyBrowserSettings,syncSetting(WebSettings settings)");
        settings.setGeolocationEnabled(enableGeolocation());
        settings.setJavaScriptEnabled(enableJavascript());
        settings.setLightTouchEnabled(enableLightTouch());
        settings.setNavDump(enableNavDump());
//        settings.setHardwareAccelSkiaEnabled(isSkiaHardwareAccelerated());
//        settings.setShowVisualIndicator(enableVisualIndicator());
        settings.setDefaultTextEncodingName(getDefaultTextEncoding());
        // TODO the setDefaultZoom method will cause system crash Intermittently
        try{
            settings.setDefaultZoom(getDefaultZoom());
        }catch(Exception e){
            e.printStackTrace();
        }
        settings.setMinimumFontSize(getMinimumFontSize());
        settings.setMinimumLogicalFontSize(getMinimumFontSize());
//        settings.setForceUserScalable(forceEnableUserScalable());
        settings.setPluginState(getPluginState());
        //TODO: settings.setTextZoom(getTextZoom());
//        settings.setDoubleTapZoom(getDoubleTapZoom());
//        settings.setAutoFillEnabled(isAutofillEnabled());
        settings.setLayoutAlgorithm(getLayoutAlgorithm());
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        LogHelper.d(LogHelper.TAG_FLASH, "setJavaScriptCanOpenWindowsAutomatically(false) " );
        settings.setLoadsImagesAutomatically(loadImages());
        settings.setLoadWithOverviewMode(loadPageInOverviewMode());
        settings.setSavePassword(rememberPasswords());
        settings.setSaveFormData(saveFormdata());
        settings.setUseWideViewPort(isWideViewport());
//        settings.setAutoFillProfile(getAutoFillProfile());

        String ua = mCustomUserAgents.get(settings);
        if (ua != null) {
            settings.setUserAgentString(ua);
        } else {
            settings.setUserAgentString(USER_AGENTS[getUserAgent()]);
        }

//        settings.setProperty(WebViewProperties.gfxInvertedScreen,
//                useInvertedRendering() ? "true" : "false");
//
//        settings.setProperty(WebViewProperties.gfxInvertedScreenContrast,
//                Float.toString(getInvertedContrast()));
//
//        settings.setProperty(WebViewProperties.gfxEnableCpuUploadPath,
//                enableCpuUploadPath() ? "true" : "false");
    }
    
    private void syncSharedSettings() {
        mNeedsSharedSync = false;
        CookieManager.getInstance().setAcceptCookie(acceptCookies());
        if (mController != null) {
            mController.setShouldShowErrorConsole(enableJavascriptConsole());
        }
    }
    
    public boolean acceptCookies() {
        return mPrefs.getBoolean(PREF_ACCEPT_COOKIES, true);
    }
    
    public boolean enableJavascriptConsole() {
        if (!isDebugEnabled()) {
            return false;
        }
        return mPrefs.getBoolean(PREF_JAVASCRIPT_CONSOLE, true);
    }
    
    public boolean isDebugEnabled() {
        requireInitialization();
        return mPrefs.getBoolean(PREF_DEBUG_MENU, false);
    }
    
    private static void requireInitialization() {
        synchronized (MyBrowserSettings.class) {
            while (!sInitialized) {
                try {
                    MyBrowserSettings.class.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }
    private int mPageCacheCapacity = 1;
    
    public int getPageCacheCapacity() {
        requireInitialization();
        return mPageCacheCapacity;
    }
    
    public MyWebStorageSizeManager getWebStorageSizeManager() {
        requireInitialization();
        return mWebStorageSizeManager;
    }
    
    private void syncStaticSettings(WebSettings settings) {
        settings.setDefaultFontSize(16);
        settings.setDefaultFixedFontSize(13);
//        settings.setPageCacheCapacity(getPageCacheCapacity());
        MyInvokeWebSettingsMethod.setPageCacheCapacity(settings, getPageCacheCapacity());

        // WebView inside Browser doesn't want initial focus to be set.
        settings.setNeedInitialFocus(false);
        // Browser supports multiple windows
        settings.setSupportMultipleWindows(true);
        // enable smooth transition for better performance during panning or
        // zooming
        if (MyBrowserGlobals.isPlatformHoneycombAndAbove())
            settings.setEnableSmoothTransition(true);
        // disable content url access
//        settings.setAllowContentAccess(false);

        // HTML5 API flags
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
//        settings.setWorkersEnabled(true);  // This only affects V8.

        // HTML5 configuration parametersettings.
        settings.setAppCacheMaxSize(getWebStorageSizeManager().getAppCacheMaxSize());
        settings.setAppCachePath(getAppCachePath());
        settings.setDatabasePath(mContext.getDir("databases", 0).getPath());
        settings.setGeolocationDatabasePath(mContext.getDir("geolocation", 0).getPath());
    }
    
    public boolean useFullscreen() {
        return mPrefs.getBoolean(PREF_FULLSCREEN, false);
    }
    
    public void setFullscreen(boolean enable) {
        mPrefs.edit().putBoolean(PREF_FULLSCREEN, enable).commit();
    }
    private MySearchEngine mSearchEngine;
    public MySearchEngine getSearchEngine() {
        if (mSearchEngine == null) {
            updateSearchEngine(false);
        }
        return mSearchEngine;
    }
    
    private void updateSearchEngine(boolean force) {
        String searchEngineName = getSearchEngineName();
        if (force || mSearchEngine == null ||
                !mSearchEngine.getName().equals(searchEngineName)) {
            if (mSearchEngine != null) {
                if (mSearchEngine.supportsVoiceSearch()) {
                     for (int i = 0; i < mController.getTabControl().getTabCount(); i++) {
                         mController.getTabControl().getTab(i).revertVoiceSearchMode();
                     }
                 }
                mSearchEngine.close();
             }
            mSearchEngine = MySearchEngineUtils.get(mContext, searchEngineName);

         }
    }
}
