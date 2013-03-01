
package com.gaoge.view.practise.blackwhitelist.operation;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.util.LruCache;

import com.dailystudio.dataobject.DatabaseObject;
import com.dailystudio.dataobject.database.DatabaseConnectivity;
import com.dailystudio.dataobject.query.ExpressionToken;
import com.dailystudio.dataobject.query.Query;
import com.dailystudio.development.Logger;
import com.gaoge.view.practise.blackwhitelist.databaseObject.ListObject;
import com.gaoge.view.practise.blackwhitelist.json.JSonList;
import com.gaoge.view.practise.blackwhitelist.json.JSonListObject;
import com.gaoge.view.practise.blackwhitelist.json.JSonWhiteBlackListParser;
import com.gaoge.view.practise.provider.Constants;
import com.gaoge.view.practise.utils.LogHelper;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

abstract class AbsList<T extends ListObject> {

    public static final String MATCH_TYPE_EXACTLY = "exactly";
    public static final String MATCH_TYPE_PREFIX = "prefix";
    public static final String MATCH_TYPE_REGEX = "regex";

    private final static int CACHE_SIZE = 50;

    private LruCache<String, List<T>> sUrlListCache =
            new LruCache<String, List<T>>(CACHE_SIZE);

    public boolean contains(Context context, URL url) {
        if (context == null || url == null) {
            return false;
        }

        final String host = url.getHost();

        List<T> matherCandidates = findInDatabase(context, host);

        if (matherCandidates != null) {
            final String urlstr = url.toString();
            if (urlstr == null) {
                return false;
            }

            for (T candidate : matherCandidates) {
                if (matchUrl(candidate, urlstr)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matchUrl(T candidate, String url) {
        if (candidate == null || url == null) {
            return false;
        }

        final String matchType = candidate.getMatchType();
        final String urlPattern = candidate.getUrlPattern();
        if (matchType == null || urlPattern == null) {
            return false;
        }

        boolean ret = false;
        if (MATCH_TYPE_EXACTLY.equals(matchType)) {
            ret = url.equals(urlPattern);
        } else if (MATCH_TYPE_PREFIX.equals(matchType)) {
            ret = url.startsWith(urlPattern);
        } else if (MATCH_TYPE_REGEX.equals(matchType)) {
            ret = Pattern.matches(urlPattern, url);
        }

        return ret;
    }

    public List<T> findInMemory(String host) {
        if (host == null) {
            return null;
        }

        List<T> databaseObjectArray = null;
        synchronized (sUrlListCache) {
            databaseObjectArray = sUrlListCache.get(host);
        }

        // printCacheStatistics();

        return databaseObjectArray;
    }

    public void cachePrefixesInMemory(String host, List<T> databaseObjectArray) {
        if (host == null || databaseObjectArray == null) {
            return;
        }

        synchronized (sUrlListCache) {
            sUrlListCache.put(host, databaseObjectArray);
        }
    }

    public List<T> findInDatabase(Context context, String host) {
        if (context == null || host == null) {
            return null;
        }

        return queryPrefixInDatabase(context, host);
    }

    @SuppressWarnings("unchecked")
    private List<T> queryPrefixInDatabase(Context context, String host) {
        if (context == null || host == null) {
            return null;
        }

        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        Constants.WHITE_URL_DATABASE_AUTHORITY,
                        getListObjectClass());

        Query query = new Query(getListObjectClass());

        ExpressionToken selToken =
                ListObject.COLUMN_HOST.eq(host);
        if (selToken == null) {
            return null;
        }

        query.setSelection(selToken);

        List<DatabaseObject> objects = connectivity.query(query);
        if (objects == null || objects.size() <= 0) {
            return null;
        }

        List<T> databaseObjectArray = new ArrayList<T>();

        for (DatabaseObject o : objects) {
            databaseObjectArray.add((T) o);
        }

        /*
         * Logger.debug("host:%s, cached[%s]", host, databaseObjectArray);
         */
        return databaseObjectArray;
    }

    private void cachePrefixesInDatabase(Context context,
            String host, List<T> matherCandidates) {
        if (context == null || host == null || matherCandidates == null) {
            return;
        }

        final int N = matherCandidates.size();

        if (N <= 0) {
            return;
        }

        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        Constants.WHITE_URL_DATABASE_AUTHORITY,
                        getListObjectClass());

        connectivity.insert(matherCandidates.toArray(new ListObject[0]));
    }

    public void printCacheStatistics() {
        final int hit = sUrlListCache.hitCount();
        final int missed = sUrlListCache.missCount();
        final int eviction = sUrlListCache.evictionCount();
        final int created = sUrlListCache.createCount();
        final int put = sUrlListCache.putCount();

        Logger.debug("[%s]: hit(%.2f, %d / %d): m(%d), e(%d), c(%d), p(%d)",
                getListObjectClass().getSimpleName(),
                ((float) hit / (hit + missed)),
                hit,
                (hit + missed),
                missed,
                eviction,
                created,
                put);
    }

    private StringBuilder getDatabasePath(Context context, StringBuilder builder) {
        return builder.append("/data/data/").append(context.getPackageName())
                .append("/databases/");
    }

    
    private void removeDatabaseFileIfExist(Context context){
        StringBuilder builder = new StringBuilder();
        String dir = getDatabasePath(context, builder)
                .append(getListObjectClass().getName())
                .append(".db")
                .toString();
        File file = new File(dir);
        if (file.exists()) {
            file.delete();
            LogHelper.d("db", file.toString() + ",removed");
        }
    }
    
    private void saveBlackWhiteListVersion(Context context, JSonList jsonList) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(getVersionKey(), jsonList.version).commit();
    }
    
    public void saveBlackWhiteListToDatabase(Context context,String content) {
        removeDatabaseFileIfExist(context);
        JSonList jsonWl = parseFromString(context,content,getListObjectClass());
        if (jsonWl == null) {
            return;
        }
        saveBlackWhiteListVersion(context, jsonWl);
        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        Constants.WHITE_URL_DATABASE_AUTHORITY,
                        getListObjectClass());

        JSonListObject[] jsonObjects =
                pickJSonObjects(jsonWl);
        if (jsonObjects == null) {
            return;
        }

        URL jsonObjectUrl;
        String jsonObjectHost;
        T databaseObject;
        for (JSonListObject jsonListObject : jsonObjects) {
            jsonObjectHost = jsonListObject.url_host;

            if (jsonObjectHost == null) {
                try {
                    jsonObjectUrl = new URL(jsonListObject.url_pattern);
                } catch (MalformedURLException e) {
                    Logger.warnning("string to URL failure: %s",
                            e.toString());
                    jsonObjectUrl = null;
                }

                if (jsonObjectUrl != null) {
                    jsonObjectHost = jsonObjectUrl.getHost();
                }
            }

            if (jsonObjectHost == null) {
                continue;
            }

            databaseObject = createListObject(context);

            databaseObject.setHost(jsonObjectHost);
            databaseObject.setMatchType(jsonListObject.match_type);
            databaseObject.setUrlPattern(jsonListObject.url_pattern);

            connectivity.insert(databaseObject);
        }

    }
    public JSonList parseFromString(Context context, String content,Class<? extends ListObject> cls) {
        return JSonWhiteBlackListParser.parseFromString(context,
                content,cls);
    }
    
    abstract public Class<? extends ListObject> getListObjectClass();

    abstract public T createListObject(Context context);

    abstract public JSonListObject[] pickJSonObjects(JSonList jsonWl);
    
    abstract public String getVersionKey();

}
