
package com.gaoge.view.practise.provider.model;

import android.content.Context;

import com.dailystudio.GlobalContextWrapper;
import com.dailystudio.dataobject.DatabaseObject;
import com.dailystudio.dataobject.database.DatabaseConnectivity;
import com.dailystudio.dataobject.query.ExpressionToken;
import com.dailystudio.dataobject.query.Query;
import com.gaoge.view.practise.provider.Constants;
import com.gaoge.view.practise.provider.object.BlackWhiteListObject;
import com.gaoge.view.practise.utils.LogHelper;

import java.util.List;

public class BlackWhiteListObjectDatabaseModel {

    public BlackWhiteListObject createItem(Context context, String url
            , int type) {
        if (context == null || url == null) {
            return null;
        }

        BlackWhiteListObject item = new BlackWhiteListObject(context, url, type);

        return item;
    }

    public synchronized void insertItem(Context context, String url
            , int type) {
        if (context == null || url == null) {
            return;
        }

        BlackWhiteListObject item = createItem(context, url, type);

        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        Constants.BLACK_WHITE_LIST_DATABASE_AUTHORITY,
                        BlackWhiteListObject.class);

        connectivity.insert(item);

    }

    public void deleteItem(Context context, String url) {
        if (context == null || url == null) {
            throw new RuntimeException("parameter can't be null!");
        }

        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        Constants.BLACK_WHITE_LIST_DATABASE_AUTHORITY,
                        BlackWhiteListObject.class);

        Query query = new Query(BlackWhiteListObject.class);

        ExpressionToken selToken =
                BlackWhiteListObject.COLUMN_URL.eq(url);
        if (selToken != null) {
            query.setSelection(selToken);
        }

        connectivity.delete(query);
    }

    public void updateItem(Context context, String url, int type) {
        if (context == null || url == null) {
            throw new RuntimeException("parameter can't be null!");
        }
        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        Constants.BLACK_WHITE_LIST_DATABASE_AUTHORITY,
                        BlackWhiteListObject.class);
        Query query = new Query(BlackWhiteListObject.class);
        ExpressionToken selToken =
                BlackWhiteListObject.COLUMN_URL.eq(url);
        String str = selToken.toString();
        LogHelper.d(LogHelper.TAG_PROVIDER,
                "UUUUUUUUUUUUUUUUUU updateItem selToken string: " + str);
        if (selToken != null) {
            query.setSelection(selToken);
        }
        BlackWhiteListObject object = createItem(context, url, type);
        connectivity.update(query, object);
    }

    /**
     * always can't query the result
     * 
     * @param context
     * @param url
     * @return
     */
    public BlackWhiteListObject getItem(Context context, String url) {
        GlobalContextWrapper.bindContext(context);
        if (context == null || url == null) {
            throw new RuntimeException("parameter can't be null!");
        }
        DatabaseConnectivity connectivity =
                new DatabaseConnectivity(context,
                        Constants.BLACK_WHITE_LIST_DATABASE_AUTHORITY,
                        BlackWhiteListObject.class);
        Query query = new Query(BlackWhiteListObject.class);
        ExpressionToken selToken =
                BlackWhiteListObject.COLUMN_URL.eq(url);
        String str = selToken.toString();
        LogHelper.d(LogHelper.TAG_PROVIDER,
                "GGGGGGGGGGGGGGGGGG getItem selToken string: " + str);
        if (selToken != null) {
            query.setSelection(selToken);
        }
        List<DatabaseObject> objects = connectivity.query(query);
        if (objects == null || objects.size() <= 0) {
            throw new RuntimeException("query result is null!");
        }

        GlobalContextWrapper.unbindContext(context);
        return (BlackWhiteListObject) objects.get(0);
    }
}
