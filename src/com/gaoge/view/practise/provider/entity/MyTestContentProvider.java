package com.gaoge.view.practise.provider.entity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Config;
import android.util.Log;

import com.gaoge.view.practise.provider.GaoGeProviderConstants;
import com.gaoge.view.practise.provider.GaoGeProviderConstants.Impl;

import java.util.HashMap;



public class MyTestContentProvider extends ContentProvider {

    private SQLiteOpenHelper mOpenHelper = null;
    private static final String DB_NAME = "gaoge_test_provider.db";
    private static final int DB_VERSION = 100;
    
    
    private static final int DB_VERSION_NOP_UPGRADE_FROM = 31;
    private static final int DB_VERSION_NOP_UPGRADE_TO = 100;
    
    private static final int MATCH_TABLE1 = 1;
    /** URI matcher constant for the URI of an individual download */
    private static final int MATCH_TABLE1_ID = 2;
    
    private static HashMap<String, String> sNotesProjectionMap;
    
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(GaoGeProviderConstants.AUTHORITIE, GaoGeProviderConstants.DB_TABLE1, MATCH_TABLE1);
        sURIMatcher.addURI(GaoGeProviderConstants.AUTHORITIE, GaoGeProviderConstants.DB_TABLE1 + "/#",MATCH_TABLE1_ID);
        
        sNotesProjectionMap = new HashMap<String, String>();
        sNotesProjectionMap.put(GaoGeProviderConstants.Impl._ID, GaoGeProviderConstants.Impl._ID);
        sNotesProjectionMap.put(GaoGeProviderConstants.Impl.COLUMN_DATA_1, GaoGeProviderConstants.Impl.COLUMN_DATA_1);
    }
    
    private void createTable(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + GaoGeProviderConstants.DB_TABLE1 + "(" +
                    GaoGeProviderConstants.Impl._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GaoGeProviderConstants.Impl.COLUMN_DATA_1 + " TEXT, " +
                    GaoGeProviderConstants.Impl.COLUMN_DATA_2 + " TEXT);");
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private final class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(final Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        
        @Override
        public void onCreate(final SQLiteDatabase db) {
            createTable(db);
        }
        
        @Override
        public void onUpgrade(final SQLiteDatabase db, int oldV, final int newV) {
            if (oldV == DB_VERSION_NOP_UPGRADE_FROM) {
                if (newV == DB_VERSION_NOP_UPGRADE_TO) { // that's a no-op upgrade.
                    return;
                }
                oldV = DB_VERSION_NOP_UPGRADE_TO;
            }
            dropTable(db);
            createTable(db);
        }
    }
    
    private void dropTable(SQLiteDatabase db) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + GaoGeProviderConstants.DB_TABLE1);
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        
        switch(sURIMatcher.match(uri)){
            case MATCH_TABLE1:
                count = db.delete(GaoGeProviderConstants.DB_TABLE1, selection, selectionArgs);
                break;
            case MATCH_TABLE1_ID:
                count = db.delete(GaoGeProviderConstants.DB_TABLE1, 
                        GaoGeProviderConstants.Impl._ID + " = " + uri.getPathSegments().get(1)
                        + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ')' ), selectionArgs);
                break;
                
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        notifyProviderDataChanged(uri);
        return count;
    }
    
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        
        if (sURIMatcher.match(uri) != MATCH_TABLE1) {
            throw new IllegalArgumentException("Unknown/Invalid URI " + uri);
        }
        long rowID = db.insert(GaoGeProviderConstants.DB_TABLE1, null, values);
        Uri  ret = Uri.parse(GaoGeProviderConstants.TABLE1_CONTENT_URI + "/" + rowID);
        notifyProviderDataChanged(uri);
        return ret;
    }
    
    private void notifyProviderDataChanged(Uri uri) {
        this.getContext().getContentResolver().notifyChange(uri, null);
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(GaoGeProviderConstants.DB_TABLE1);
        
        switch(sURIMatcher.match(uri)){
            case MATCH_TABLE1:
                qb.setProjectionMap(sNotesProjectionMap);
                break;
                
            case MATCH_TABLE1_ID:
                qb.setProjectionMap(sNotesProjectionMap);
                qb.appendWhere(GaoGeProviderConstants.Impl._ID + " = " + uri.getPathSegments().get(1));
                break;
                
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return c;
    }
    
    @Override
    public int update(Uri uri, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        int match = sURIMatcher.match(uri);
        switch(match){
            case MATCH_TABLE1_ID:
                String segment = uri.getPathSegments().get(1);
                long rowId = Long.parseLong(segment);
                whereClause =  whereClause + " AND " + GaoGeProviderConstants.Impl._ID + " = " + rowId;
            case MATCH_TABLE1:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        count = db.update(GaoGeProviderConstants.DB_TABLE1, values, whereClause, whereArgs);
        notifyProviderDataChanged(uri);
        return count;
    }
    
}
