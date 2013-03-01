
package com.gaoge.view.practise.provider.object;

import android.content.Context;

import com.dailystudio.dataobject.Column;
import com.dailystudio.dataobject.DatabaseObject;
import com.dailystudio.dataobject.IntegerColumn;
import com.dailystudio.dataobject.Template;
import com.dailystudio.dataobject.TextColumn;
import com.gaoge.view.practise.provider.Constants;

public class BlackWhiteListObject extends DatabaseObject {
    public static final Column COLUMN_URL = new TextColumn("url", false, true);
    public static final Column COLUMN_ITEM_TYPE = new IntegerColumn("item_type", false);

    private final static Column[] sColumns = {
            COLUMN_URL,
            COLUMN_ITEM_TYPE,
    };

    public BlackWhiteListObject(Context context) {
        this(context, null, Constants.ITEM_UNKNOWN);
    }

    public BlackWhiteListObject(Context context, String url) {
        this(context, url, Constants.ITEM_UNKNOWN);
    }

    public BlackWhiteListObject(Context context, String url, int itemType) {
        super(context);

        final Template templ = getTemplate();

        templ.addColumns(sColumns);

        setUrl(url);
        setItemType(itemType);
    }

    public void setUrl(String url) {
        setValue(COLUMN_URL, url);
    }

    public String getUrl() {
        return getTextValue(COLUMN_URL);
    }

    public void setItemType(int type) {
        setValue(COLUMN_ITEM_TYPE, type);
    }

    public int getItemType() {
        return getIntegerValue(COLUMN_ITEM_TYPE);
    }

}
