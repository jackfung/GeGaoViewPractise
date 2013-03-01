
package com.gaoge.view.practise.blackwhitelist.json;

import android.content.Context;

import com.gaoge.view.practise.blackwhitelist.databaseObject.ListObject;
import com.google.gson.Gson;

public class JSonWhiteBlackListParser {

    public static JSonList parseFromString(Context context, String content,
            Class<? extends ListObject> cls) {
        if (context == null) {
            return null;
        }

        if (content == null) {
            return null;
        }

        Gson gson = new Gson();

        JSonList jsonList = null;
        try {
            jsonList = gson.fromJson(content, JSonList.class);
        } catch (Exception e) {
            jsonList = null;
        }

        return jsonList;
    }

}
