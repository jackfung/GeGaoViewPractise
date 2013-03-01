package com.gaoge.view.practise.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class GaoGeProviderConstants {

    public static final String DB_TABLE1 = "gaoge_table1";
    public static final String AUTHORITIE = "com.gaoge.test.provider";
    
    public static final String[] PROJECTIONS = new String[]{
      GaoGeProviderConstants.Impl._ID,
      GaoGeProviderConstants.Impl.COLUMN_DATA_1
    };
    
    public static final Uri TABLE1_CONTENT_URI =
            Uri.parse("content://" + AUTHORITIE + "/" + DB_TABLE1);
    
    public static final class Impl implements BaseColumns {
        public static final String COLUMN_DATA_1 = "data1";
        public static final String COLUMN_DATA_2 = "data2";
    }
}
