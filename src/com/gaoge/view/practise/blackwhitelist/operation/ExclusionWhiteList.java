
package com.gaoge.view.practise.blackwhitelist.operation;

import android.content.Context;

import com.gaoge.view.practise.blackwhitelist.databaseObject.ListObject;
import com.gaoge.view.practise.blackwhitelist.databaseObject.WhiteListExclusiveObject;
import com.gaoge.view.practise.blackwhitelist.json.JSonList;
import com.gaoge.view.practise.blackwhitelist.json.JSonListObject;
import com.gaoge.view.practise.blackwhitelist.json.JSonWhiteBlackListParser;

class ExclusionWhiteList extends AbsList<ListObject> {

    @Override
    public Class<? extends ListObject> getListObjectClass() {
        return WhiteListExclusiveObject.class;
    }

    @Override
    public ListObject createListObject(Context context) {
        return new WhiteListExclusiveObject(context);
    }

    @Override
    public JSonListObject[] pickJSonObjects(JSonList jsonWl) {
        if (jsonWl == null) {
            return null;
        }

        return jsonWl.exclusion;
    }

    @Override
    public String getVersionKey() {
        return WhiteBlackList.WHITELIST_VERSION;
    }
}
