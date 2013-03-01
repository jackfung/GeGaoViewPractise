package com.gaoge.view.webview.search;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.gaoge.view.webview.reflect.MyInvokeSearchManagerMethod;

import dep.android.provider.Browser;

public class MyDefaultSearchEngine implements MySearchEngine {

    private static final String TAG = "MyDefaultSearchEngine";

    private final SearchableInfo mSearchable;

    private final CharSequence mLabel;
    
    private MyDefaultSearchEngine(Context context, SearchableInfo searchable) {
        mSearchable = searchable;
        mLabel = loadLabel(context, mSearchable.getSearchActivity());
    }
    
    public static MyDefaultSearchEngine getInstance(Context context) {
        SearchManager searchManager =
                (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
//        ComponentName name = searchManager.getWebSearchActivity();
        ComponentName name = (ComponentName) MyInvokeSearchManagerMethod.getWebSearchActivity(searchManager);

        if (name == null) return null;
        SearchableInfo searchable = searchManager.getSearchableInfo(name);
        if (searchable == null) return null;
        return new MyDefaultSearchEngine(context, searchable);
    }
    
    private CharSequence loadLabel(Context context, ComponentName activityName) {
        PackageManager pm = context.getPackageManager();
        try {
            ActivityInfo ai = pm.getActivityInfo(activityName, 0);
            return ai.loadLabel(pm);
        } catch (PackageManager.NameNotFoundException ex) {
            Log.e(TAG, "Web search activity not found: " + activityName);
            return null;
        }
    }
    
    @Override
    public String getName() {
        String packageName = mSearchable.getSearchActivity().getPackageName();
        if ("com.google.android.googlequicksearchbox".equals(packageName)) {
            return MySearchEngine.GOOGLE;
        } else if ("com.android.quicksearchbox".equals(packageName)) {
            return MySearchEngine.GOOGLE;
        } else {
            return packageName;
        }
    }

    @Override
    public CharSequence getLabel() {
        return mLabel;
    }

    @Override
    public void startSearch(Context context, String query, Bundle appData, String extraData) {
        MySearchEngineInfo mSearchEngineInfo=MySearchEngineUtils.getSearchEngineInfo(context, MySearchEngine.GOOGLE);
        String uri = mSearchEngineInfo.getSearchUriForQuery(query);
        if (uri == null) {
            Log.e(TAG, "Unable to get search URI for " + mSearchEngineInfo);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            // Make sure the intent goes to the Browser itself
            intent.setPackage(context.getPackageName());
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(SearchManager.QUERY, query);
            if (appData != null) {
                intent.putExtra(SearchManager.APP_DATA, appData);
            }
            if (extraData != null) {
                intent.putExtra(SearchManager.EXTRA_DATA_KEY, extraData);
            }
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
            context.startActivity(intent);
        }
    }

    
    
    @Override
    public Cursor getSuggestions(Context context, String query) {
        SearchManager searchManager =
                (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
//        return searchManager.getSuggestions(mSearchable, query);
        return (Cursor) MyInvokeSearchManagerMethod.getSuggestions(searchManager, mSearchable, query);
    }

    @Override
    public boolean supportsSuggestions() {
        return !TextUtils.isEmpty(mSearchable.getSuggestAuthority());
    }

    @Override
    public void close() {

    }

    @Override
    public boolean supportsVoiceSearch() {
        return getName().equals(MySearchEngine.GOOGLE);
    }

    @Override
    public boolean wantsEmptyQuery() {
        return false;
    }
    
    @Override
    public String toString() {
        return "ActivitySearchEngine{" + mSearchable + "}";
    }

}
