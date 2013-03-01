
package com.gaoge.view.practise.provider.model;

import android.content.Context;

import com.dailystudio.GlobalContextWrapper;
import com.dailystudio.dataobject.DatabaseObject;
import com.dailystudio.dataobject.database.DatabaseConnectivity;
import com.dailystudio.dataobject.query.ExpressionToken;
import com.dailystudio.dataobject.query.Query;
import com.gaoge.view.practise.provider.object.UrlObject;
import com.gaoge.view.practise.utils.LogHelper;

import java.util.List;

public class UrlDatabaseModel {
    
    String mAuthority;
    Class<? extends DatabaseObject> mClass;
    
    public UrlDatabaseModel(String authority,Class<? extends DatabaseObject> vClass){
        mAuthority = authority;
        mClass = vClass;
    }

    public UrlObject createItem(Context context, String type,String urlPattern
            , String urlHost) {
        if (context == null || urlPattern == null) {
            return null;
        }

        UrlObject item = new UrlObject(context,type, urlPattern,urlHost );

        return item;
    }

    public synchronized void insertItem(Context context, String type,String urlPattern
            , String urlHost) {
        if (context == null || urlPattern == null) {
            return;
        }

        UrlObject item = createItem(context, type,urlPattern,urlHost);

        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        mAuthority,
                        mClass);

        long id = connectivity.insert(item);
        LogHelper.d("provider", "id = " + id);

    }

    public void deleteItem(Context context, String url) {
        if (context == null || url == null) {
            throw new RuntimeException("parameter can't be null!");
        }

        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        mAuthority,
                        mClass);

        Query query = new Query(mClass);

        ExpressionToken selToken =
                UrlObject.COLUMN_URL_PATTERN.eq(url);
        if (selToken != null) {
            query.setSelection(selToken);
        }

        connectivity.delete(query);
    }

    public void updateItem(Context context, String type ,String urlPattern,String urlHost) {
        if (context == null || urlPattern == null) {
            throw new RuntimeException("parameter can't be null!");
        }
        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        mAuthority,
                        mClass);
        Query query = new Query(mClass);
        ExpressionToken selToken =
                UrlObject.COLUMN_URL_PATTERN.eq(urlPattern);
        if (selToken != null) {
            query.setSelection(selToken);
        }
        UrlObject object = createItem(context,type,urlPattern,urlHost);
        connectivity.update(query, object);
    }

    /**
     * always can't query the result
     * 
     * @param context
     * @param url
     * @return
     */
    public UrlObject getItem(Context context, String url) {
        GlobalContextWrapper.bindContext(context);
        if (context == null || url == null) {
            throw new RuntimeException("parameter can't be null!");
        }
        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        mAuthority,
                        mClass);
        Query query = new Query(mClass);
        ExpressionToken selToken =
                UrlObject.COLUMN_URL_PATTERN.eq(url);
        String str = selToken.toString();
        if (selToken != null) {
            query.setSelection(selToken);
        }
        List<DatabaseObject> objects = connectivity.query(query);
        if (objects == null || objects.size() <= 0) {
            throw new RuntimeException("query result is null!");
        }

        GlobalContextWrapper.unbindContext(context);
        return (UrlObject) objects.get(0);
    }
}
