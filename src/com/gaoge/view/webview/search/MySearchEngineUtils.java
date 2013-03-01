package com.gaoge.view.webview.search;


import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.gaoge.view.webview.reflect.MyInvokeSearchManagerMethod;
import com.orange.test.textImage.R;

import java.util.ArrayList;
import java.util.List;

public class MySearchEngineUtils {

    private static final String TAG = "SearchEngines";

    public static MySearchEngine getDefaultSearchEngine(Context context) {
        return MyDefaultSearchEngine.getInstance(context);
    }

    public static List<MySearchEngineInfo> getSearchEngineInfos(Context context) {
        ArrayList<MySearchEngineInfo> searchEngineInfos = new ArrayList<MySearchEngineInfo>();
        Resources res = context.getResources();
        String[] searchEngines = res.getStringArray(R.array.search_engines);
        for (int i = 0; i < searchEngines.length; i++) {
            String name = searchEngines[i];
            MySearchEngineInfo info = new MySearchEngineInfo(context, name);
            searchEngineInfos.add(info);
        }
        return searchEngineInfos;
    }

    public static MySearchEngine get(Context context, String name) {
        // TODO: cache
        MySearchEngine defaultSearchEngine = getDefaultSearchEngine(context);
        if (TextUtils.isEmpty(name)
                || (defaultSearchEngine != null && name.equals(defaultSearchEngine.getName()))) {
            return defaultSearchEngine;
        }
        MySearchEngineInfo searchEngineInfo = getSearchEngineInfo(context, name);
        if (searchEngineInfo == null) return defaultSearchEngine;
        return new MyOpenSearchSearchEngine(context, searchEngineInfo);
    }

    public static MySearchEngineInfo getSearchEngineInfo(Context context, String name) {
        try {
            return new MySearchEngineInfo(context, name);
        } catch (IllegalArgumentException exception) {
            Log.e(TAG, "Cannot load search engine " + name, exception);
            return null;
        }
    }
    
 

}
