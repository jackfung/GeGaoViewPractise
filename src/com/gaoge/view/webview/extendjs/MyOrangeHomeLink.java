package com.gaoge.view.webview.extendjs;


import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Message;

import dep.android.net.WebAddress;
import dep.android.provider.Browser;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.gaoge.view.practise.utils.UrlUtils;
import com.gaoge.view.webview.Constants;
import com.gaoge.view.webview.MyBookmarks;
import com.gaoge.view.webview.MyBrowserActivity;
import com.gaoge.view.webview.MyBrowserGlobals;
import com.gaoge.view.webview.MyCombinedBookmarkHistoryActivity;
import com.gaoge.view.webview.MyController;
import com.gaoge.view.webview.MyCustomDialog;
import com.gaoge.view.webview.MyHomeLinks;
import com.gaoge.view.webview.MyHomeLinks.HomeLinkActions;
import com.orange.test.textImage.R;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyOrangeHomeLink {
    
    private final Context mContext;
    private String mOldTitle;
    private String mOldUrl;
    private String mNewUrl;
    final static int COMBO_VIEW = 1; //This should be same as the one in Controller.java
    private JSONObject mJson;
    private static final String LOGTAG = "OrangeHomeLink";
    
    public MyOrangeHomeLink(Context context) {
        mContext = context;
    }
    
    public String getHomeLinkPositions(){
        //return MyHomeLinks.getHomeLinkPositions(mContext);
        String positions = "";
        Cursor cursor = null;
        try {
            final String[] projection = new String[] { "_id" };

            cursor = mContext.getContentResolver().query(
                    Browser.HOMELINKS_URI,
                    projection,
                    null,
                    null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    positions += String.valueOf(cursor.getInt(0)) + ";";
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
         }
        return positions;
    }
    
    public static class LinkInfo{
        String mPosition;
        String mUrl;
        String mTitle;
        String mIcon;
        
        public LinkInfo(String position, String title, String url, String icon){
            mPosition = position;
            mUrl = url;
            mTitle = title;
            mIcon = icon;
        }
        
        public String getUrl(){
            return mUrl;
        }
        
        public String getTitle(){
            return mTitle;
        }
        
        public String getIcon(){
            return mIcon;
        }
    }
    
    public String getLinkInfo(String position){
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(
                    Browser.HOMELINKS_URI,
                    null,
                    "_id =" + position,
                    null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String url = cursor.getString(cursor.getColumnIndex("url"));

                JSONObject json = new JSONObject();
                json.put("title", title);
                json.put("url", url);
                return json.toString();
            }
        } catch (Exception e) {
            Log.e(LOGTAG, "The parameter position" + position + " is not right?", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        //We return "null" string in case of error occurs.
        return "null";
        //return MyHomeLinks.getLinkInfo(position, mContext);
    }
    
    public String getBase64Image(String position){
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(
                    Browser.HOMELINKS_URI,
                    null,
                    "_id = '" + position + "'",
                    null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                String iconfile = cursor.getString(cursor.getColumnIndex("icon"));
                
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String icon = null;

                if (iconfile != null){
                    Drawable draw = Drawable.createFromStream(
                            mContext.getAssets().open(iconfile), null);
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    Bitmap bmp = MyHomeLinks.createIconBitmap(draw, mContext);
                    bmp.compress(Bitmap.CompressFormat.PNG, 90, os);
                    icon = Base64.encodeToString(os.toByteArray(), Base64.DEFAULT);
                } else{
                    icon = getIconFromBookmark(url);
                }
                return icon;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        //We return "null" string in case of error occurs.
        return "null";
    }
    
    private String getIconFromBookmark(String url) {
        Cursor cursor = Browser.getVisitedLike(mContext.getContentResolver(), url);
        cursor.moveToFirst();
        byte[] touchIcon = cursor.getBlob(Browser.HISTORY_PROJECTION_TOUCH_ICON_INDEX);
        
        String icon = "null";
        if (touchIcon != null) {
            icon = Base64.encodeToString(touchIcon, Base64.DEFAULT);
        } else {
            byte[] thumbnail = cursor.getBlob(Browser.HISTORY_PROJECTION_THUMBNAIL_INDEX);
            if (thumbnail != null) {
                icon = Base64.encodeToString(thumbnail, Base64.DEFAULT);
            }
        }
        return icon;
    }
    
    public void addHomeLink(){
        Intent intent = new Intent(mContext, MyCombinedBookmarkHistoryActivity.class);

        intent.putExtra(MyCombinedBookmarkHistoryActivity.STARTING_TAB,
                MyCombinedBookmarkHistoryActivity.BOOKMARKS_TAB);
        intent.putExtra(MyHomeLinks.POSITION, "notused");
        intent.putExtra("is_home", true);
        
        ((Activity) mContext).startActivityForResult(intent, COMBO_VIEW);
    }

    public void editHomeLink(JSONObject jsonObject) {
        final JSONObject json = jsonObject;

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.homelink_dialog, null);
        final MyCustomDialog dialog = new MyCustomDialog(mContext);
        dialog.setIcon(R.drawable.bookmark_normal);
        dialog.setTitle(R.string.homelink_edit_title);
        dialog.setCustomView(layout);

        try {
            mOldTitle = json.getString("title");
            mOldUrl = json.getString("url");
        } catch (JSONException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        final EditText titleView = (EditText) layout.findViewById(R.id.homelink_title_input);
        titleView.setText(mOldTitle);
        final EditText urlView = (EditText) layout.findViewById(R.id.homelink_url_input);
        urlView.setText(mOldUrl);
        TypedArray a = mContext.getTheme().obtainStyledAttributes(new int[] { R.attr.colorFocused });
        int highlightColor = a.getColor(0, 0);
        a.recycle();
        titleView.setHighlightColor(highlightColor);
        urlView.setHighlightColor(highlightColor);

        dialog.setConfirmButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String fixedUrl = UrlUtils.fixUrl(urlView.getText().toString()).trim();
                String fixedTitle = titleView.getText().toString().trim();
                if (urlAndTitleValid(fixedUrl, fixedTitle, mOldUrl, mOldTitle)) {
                    try {
                        json.put("title", fixedTitle);
                        json.put("url", mNewUrl);
                        MyHomeLinks.editHomeLink(mContext, mOldUrl, mNewUrl, fixedTitle);
                        MyController.manageHomeLink(json, HomeLinkActions.Edit);
                        dialog.dismiss();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            private boolean urlAndTitleValid(String fixedUrl, String fixedTitle,
                    String oldUrl, String oldTitle) {
                boolean emptyTitle = fixedTitle.length() == 0;
                boolean emptyUrl = fixedUrl.length() == 0;
                Resources r = mContext.getResources();
                if (emptyTitle || emptyUrl) {
                    if (emptyTitle) {
                        titleView.setError(r.getText(R.string.homelinks_needs_title));
                    }
                    if (emptyUrl) {
                        urlView.setError(r.getText(R.string.homelinks_needs_url));
                    }
                    return false;
                }
                // If no change, no need to update.
                if (oldUrl.equals(fixedUrl) && oldTitle.equals(fixedTitle)) {
                    Toast.makeText(mContext, R.string.homelinks_no_change, Toast.LENGTH_LONG)
                            .show();
                    return false;
                }

                try {
                    if (!fixedUrl.toLowerCase().startsWith("javascript:")) {
                        URI uriObj = new URI(fixedUrl);
                        String scheme = uriObj.getScheme();
                        if (true) {
                            // If the scheme was non-null, let the user know
                            // that we
                            // can't save their bookmark. If it was null, we'll
                            // assume
                            // they meant http when we parse it in the
                            // WebAddress class.
                            if (!MyBookmarks.urlHasAcceptableScheme(fixedUrl) && scheme != null) {
                                // TODO: internationalize
                                Toast.makeText(mContext, R.string.homelinks_cannot_save_url,
                                        Toast.LENGTH_LONG).show();
                                return false;
                            }
                            WebAddress address;
                            try {
                                address = new WebAddress(fixedUrl);
                            } catch (ParseException e) {
                                throw new URISyntaxException("", "");
                            }
                            if (address.mHost.length() == 0) {
                                throw new URISyntaxException("", "");
                            }
                            fixedUrl = address.toString();
                        }
                    }
                } catch (URISyntaxException e) {
                    Toast.makeText(mContext, R.string.homelinks_url_not_valid, Toast.LENGTH_LONG)
                            .show();
                    return false;
                }
                // If url changed, check if it exist.
                if (!oldUrl.equals(fixedUrl) &&
                        MyHomeLinks.checkIsExisted(fixedUrl, mContext.getContentResolver())) {
                    // TODO: internationalize
                    Toast.makeText(mContext, R.string.homelinks_err_exist, Toast.LENGTH_LONG)
                            .show();
                    return false;
                }
                mNewUrl = fixedUrl;
                return true;
            }
        });
        dialog.setCancleButton(null);
        dialog.show();
    }

    public void clearHomeLink(JSONObject json) {
        try {
            String url = json.getString("url");
            MyHomeLinks.deleteHomeLink(mContext, json.getString("url"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        MyController.manageHomeLink(json, HomeLinkActions.Clear);
    }
    
    public void contextMenu(String jsonStr){
        //String str = "{'url':'http://www.baidu.com/', 'title':'test', 'position':'0'}";
        try {
            mJson = new JSONObject(jsonStr);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String[] homelinkContextMenu = new String[] {
                mContext.getString(R.string.homelinks_contextmenu_edit),
                mContext.getString(R.string.homelinks_contextmenu_clear)
        };
        
        final MyCustomDialog dialog = new MyCustomDialog(mContext);
        dialog.setItems(homelinkContextMenu, new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                dialog.dismiss();
                switch(arg2){
                    case 0://Edit
                        editHomeLink(mJson);
                        break;
                    case 1:
                        clearHomeLink(mJson);
                        break;
                }
            }
            
        });
        dialog.show();
    }
    
    public void viewHistory(){
        Intent intent = new Intent(mContext, MyCombinedBookmarkHistoryActivity.class);
        boolean isHome = MyBrowserGlobals.getInstance().isHomePage();
        intent.putExtra("is_home", isHome);     
        ((Activity) mContext).startActivityForResult(intent, COMBO_VIEW);
    }
    
    public void viewBookmark(){
        Intent intent = new Intent(mContext, MyCombinedBookmarkHistoryActivity.class);
        
        boolean isHome = MyBrowserGlobals.getInstance().isHomePage();
        intent.putExtra("is_home", isHome);        
        intent.putExtra(MyCombinedBookmarkHistoryActivity.STARTING_TAB,
                MyCombinedBookmarkHistoryActivity.BOOKMARKS_TAB);
        
        ((Activity) mContext).startActivityForResult(intent, COMBO_VIEW);
    }
    
    public void viewReading(){
        Intent intent = new Intent(Constants.ACTION_READING_LIST);
        
        final ComponentName source = 
            new ComponentName(mContext, MyBrowserActivity.class);
        
        intent.putExtra(Constants.EXTRA_LAUNCH_SOURCE, source.flattenToString());
        intent.putExtra(Constants.EXTRA_BROWSER_TASKID, ((Activity)mContext).getTaskId());
        
        mContext.startActivity(intent);
    }
    
    public String getMostVisited(){
        Cursor cursor = null;
        JSONArray jsonarray = new JSONArray();
        try {
            final String[] projection = new String[] { "_id", "title", "url" };
            String whereClause = Browser.BookmarkColumns.VISITS + " != 0";
            String orderBy = Browser.BookmarkColumns.VISITS + " DESC";
            cursor = mContext.getContentResolver().query(
                    Browser.BOOKMARKS_URI,
                    projection,
                    whereClause,
                    null,
                    orderBy);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String url = cursor.getString(cursor.getColumnIndex("url"));

                    JSONObject json = new JSONObject();
                    json.put("title", title);
                    json.put("url", url);
                    jsonarray.put(json);
                } while(cursor.moveToNext());
                return jsonarray.toString();
            }
        } catch (Exception e) {
            Log.e(LOGTAG, "getMostVisited", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        //We return "null" string in case of error occurs.
        return "null";
    }
}
