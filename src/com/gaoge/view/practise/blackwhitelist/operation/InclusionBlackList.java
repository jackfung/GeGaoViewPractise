
package com.gaoge.view.practise.blackwhitelist.operation;

import android.content.Context;

import com.gaoge.view.practise.blackwhitelist.databaseObject.BlackListInclusiveObject;
import com.gaoge.view.practise.blackwhitelist.databaseObject.ListObject;
import com.gaoge.view.practise.blackwhitelist.json.JSonList;
import com.gaoge.view.practise.blackwhitelist.json.JSonListObject;
import com.gaoge.view.practise.blackwhitelist.json.JSonWhiteBlackListParser;

class InclusionBlackList extends AbsList<ListObject> {

    @Override
    public Class<? extends ListObject> getListObjectClass() {
        return BlackListInclusiveObject.class;
    }

    @Override
    public ListObject createListObject(Context context) {
        return new BlackListInclusiveObject(context);
    }

    @Override
    public JSonListObject[] pickJSonObjects(JSonList jsonWl) {
        if (jsonWl == null) {
            return null;
        }

        return jsonWl.list;
    }

    @Override
    public String getVersionKey() {
        return WhiteBlackList.BLACKLIST_VERSION;
    }
}
