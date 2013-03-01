
package com.gaoge.view.practise.provider.act;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gaoge.view.practise.provider.GaoGeProviderConstants;
import com.gaoge.view.practise.provider.MyProviderContentObserver;
import com.gaoge.view.practise.provider.GaoGeProviderConstants.Impl;
import com.gaoge.view.practise.utils.LogHelper;
import com.orange.test.textImage.R;

import java.io.UnsupportedEncodingException;

public class TestProviderActivity extends Activity {
    Button bt_insert;
    Button bt_insert_china;
    Button bt_update;
    Button bt_query;
    Button bt_query_china;
    Button bt_delete;
    TextView tv_query_result;

    ContentResolver mContentResolver;
    String enName = "gaoge";
    String zhName = "http://大侯/";

    String enName_china = "gaoge_china";
    String zhName_china = "http://大侯_china/";

    String encode = "gbk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_provider);
        mContentResolver = getContentResolver();

        mContentResolver.registerContentObserver(GaoGeProviderConstants.TABLE1_CONTENT_URI, true,
                new MyProviderContentObserver());
        initControls();
    }

    private void initControls() {
        bt_insert = (Button) this.findViewById(R.id.bt_insert);
        bt_insert_china = (Button) this.findViewById(R.id.bt_insert_china);

        bt_update = (Button) this.findViewById(R.id.bt_update);

        bt_query = (Button) this.findViewById(R.id.bt_query);
        bt_query_china = (Button) this.findViewById(R.id.bt_query_china);

        bt_delete = (Button) this.findViewById(R.id.bt_delete);

        bt_insert.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                insert();
            }
        });

        bt_insert_china.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    insertChinese();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        bt_update.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                update();
            }
        });

        bt_query.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                query();
            }
        });

        bt_query_china.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                queryChinese();
            }
        });

        bt_delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                delete();
            }
        });

        tv_query_result = (TextView) findViewById(R.id.tv_query_result);
    }

    private void insert() {
        ContentValues values = new ContentValues();
        values.put(GaoGeProviderConstants.Impl.COLUMN_DATA_1, enName);

        mContentResolver.insert(GaoGeProviderConstants.TABLE1_CONTENT_URI, values);

        values.put(GaoGeProviderConstants.Impl.COLUMN_DATA_1, zhName);

        mContentResolver.insert(GaoGeProviderConstants.TABLE1_CONTENT_URI, values);

        LogHelper.d(LogHelper.TAG_PROVIDER, "################## Insert Succesfully!");
    }

    private void insertChinese() throws UnsupportedEncodingException {
        ContentValues values = new ContentValues();
        values.put(GaoGeProviderConstants.Impl.COLUMN_DATA_1, enName_china);

        mContentResolver.insert(GaoGeProviderConstants.TABLE1_CONTENT_URI, values);

        values.put(GaoGeProviderConstants.Impl.COLUMN_DATA_1, new String(zhName_china.getBytes(),
                encode));

        mContentResolver.insert(GaoGeProviderConstants.TABLE1_CONTENT_URI, values);

        LogHelper.d(LogHelper.TAG_PROVIDER, "################## Insert China Succesfully!");
    }

    private void queryChinese() {

        Cursor c = mContentResolver.query(GaoGeProviderConstants.TABLE1_CONTENT_URI,
                GaoGeProviderConstants.PROJECTIONS, null, null, null);

        LogHelper.d(LogHelper.TAG_PROVIDER, "################## Query China Result");
        StringBuilder sb = new StringBuilder();

        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex(GaoGeProviderConstants.Impl._ID));
            byte[] bt = c.getBlob(c.getColumnIndex(GaoGeProviderConstants.Impl.COLUMN_DATA_1));
            String data1 = "";

            String str = "\n id: " + id + ",data1: " + data1;
            

            try {
                data1 = new String(bt, encode);
                sb.append(data1);
                LogHelper.d(LogHelper.TAG_PROVIDER, str);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        tv_query_result.setText(sb.toString());
    }

    private void query() {

        Cursor c = mContentResolver.query(GaoGeProviderConstants.TABLE1_CONTENT_URI,
                GaoGeProviderConstants.PROJECTIONS, null, null, null);

        LogHelper.d(LogHelper.TAG_PROVIDER, "################## Query Result");
        StringBuilder sb = new StringBuilder();

        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex(GaoGeProviderConstants.Impl._ID));
            String data1 = c.getString(c.getColumnIndex(GaoGeProviderConstants.Impl.COLUMN_DATA_1));
            String str = "\n id: " + id + ",data1: " + data1;
            sb.append(str);
            LogHelper.d(LogHelper.TAG_PROVIDER, str);
        }
        tv_query_result.setText(sb.toString());
    }

    private void update() {
        ContentValues values = new ContentValues();
        values.put(GaoGeProviderConstants.Impl.COLUMN_DATA_1, "gaoge_change");
        String where = GaoGeProviderConstants.Impl.COLUMN_DATA_1 + " = ? ";
        String[] selectionArgs = new String[] {
                "gaoge"
        };
        mContentResolver.update(GaoGeProviderConstants.TABLE1_CONTENT_URI, values, where,
                selectionArgs);

        LogHelper.d(LogHelper.TAG_PROVIDER, "################## Update Succesfully!");
    }

    private void delete() {

        mContentResolver.delete(GaoGeProviderConstants.TABLE1_CONTENT_URI,
                GaoGeProviderConstants.Impl.COLUMN_DATA_1 + "='" + enName + "'", null);
        mContentResolver.delete(GaoGeProviderConstants.TABLE1_CONTENT_URI,
                GaoGeProviderConstants.Impl.COLUMN_DATA_1 + "='" + zhName + "'", null);
        LogHelper.d(LogHelper.TAG_PROVIDER, "################## Delete Succesfully!");
    }
}
