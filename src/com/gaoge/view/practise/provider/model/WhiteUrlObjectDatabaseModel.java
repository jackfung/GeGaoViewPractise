
package com.gaoge.view.practise.provider.model;

import android.content.Context;

import com.dailystudio.GlobalContextWrapper;
import com.dailystudio.dataobject.DatabaseObject;
import com.dailystudio.dataobject.database.DatabaseConnectivity;
import com.dailystudio.dataobject.query.ExpressionToken;
import com.dailystudio.dataobject.query.Query;
import com.gaoge.view.practise.provider.Constants;
import com.gaoge.view.practise.provider.object.WhiteUrlObject;

import java.util.List;

public class WhiteUrlObjectDatabaseModel {

    public WhiteUrlObject createItem(Context context,String type, String url
            ,  String host){
        if (context == null || url == null) {
            return null;
        }

        WhiteUrlObject item = new WhiteUrlObject(context, type,url, host);

        return item;
    }

    public synchronized void insertItem(Context context,String type, String url
            ,  String host) {
        if (context == null || url == null) {
            return;
        }

        WhiteUrlObject item = createItem(context, type,url, host);

        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        Constants.WHITE_URL_DATABASE_AUTHORITY,
                        WhiteUrlObject.class);

        connectivity.insert(item);

    }

    public void deleteItem(Context context, String url) {
        if (context == null || url == null) {
            throw new RuntimeException("parameter can't be null!");
        }

        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        Constants.WHITE_URL_DATABASE_AUTHORITY,
                        WhiteUrlObject.class);

        Query query = new Query(WhiteUrlObject.class);

        ExpressionToken selToken =
                WhiteUrlObject.COLUMN_URL_PATTERN.eq(url);
        if (selToken != null) {
            query.setSelection(selToken);
        }

        connectivity.delete(query);
    }

    public void updateItem(Context context,String type, String url
            ,  String host) {
        if (context == null || url == null) {
            throw new RuntimeException("parameter can't be null!");
        }
        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        Constants.WHITE_URL_DATABASE_AUTHORITY,
                        WhiteUrlObject.class);
        Query query = new Query(WhiteUrlObject.class);
        ExpressionToken selToken =
                WhiteUrlObject.COLUMN_URL_PATTERN.eq(url);
        if (selToken != null) {
            query.setSelection(selToken);
        }
        WhiteUrlObject object = createItem(context, type,url, host);
        connectivity.update(query, object);
    }

    /**
     * always can't query the result
     * 
     * @param context
     * @param url
     * @return
     */
    public WhiteUrlObject getItem(Context context, String url) {
        GlobalContextWrapper.bindContext(context);
        if (context == null || url == null) {
            throw new RuntimeException("parameter can't be null!");
        }
        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        Constants.WHITE_URL_DATABASE_AUTHORITY,
                        WhiteUrlObject.class);
        Query query = new Query(WhiteUrlObject.class);
        ExpressionToken selToken =
                WhiteUrlObject.COLUMN_URL_PATTERN.eq(url);
        if (selToken != null) {
            query.setSelection(selToken);
        }
        List<DatabaseObject> objects = connectivity.query(query);
        if (objects == null || objects.size() <= 0) {
            throw new RuntimeException("query result is null!");
        }

        GlobalContextWrapper.unbindContext(context);
        return (WhiteUrlObject) objects.get(0);
    }
}
