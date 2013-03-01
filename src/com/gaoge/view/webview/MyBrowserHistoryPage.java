package com.gaoge.view.webview;

/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.webkit.WebIconDatabase.IconListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.FrameLayout;

import com.gaoge.view.webview.MyHomeLinks.HomeLinkActions;
import com.orange.test.textImage.R;

import dep.android.provider.Browser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity for displaying the browser's history, divided into days of viewing.
 */
public class MyBrowserHistoryPage extends ExpandableListActivity {
    private HistoryAdapter mAdapter;
    private boolean mDisableNewWindow;
    private MyHistoryItem mContextHeader;
    private String mHomeLinkPosition;
    private final static String LOGTAG = "browser";

    // Implementation of WebIconDatabase.IconListener
    private class IconReceiver implements IconListener {
        public void onReceivedIcon(String url, Bitmap icon) {
            setListAdapter(mAdapter);
        }
    }

    // Instance of IconReceiver
    private final IconReceiver mIconReceiver = new IconReceiver();
    private MyHistoryItem historyItem;

    /**
     * Report back to the calling activity to load a site.
     * 
     * @param url Site to load.
     * @param newWindow True if the URL should be loaded in a new window
     */
    private void loadUrl(String url, boolean newWindow) {
        Intent intent = new Intent().setAction(url);
        if (newWindow) {
            Bundle b = new Bundle();
            b.putBoolean("new_window", true);
            intent.putExtras(b);
        }
        setResultToParent(RESULT_OK, intent);
        finish();
    }

    private void copy(CharSequence text) {
        // try {
        // IClipboard clip =
        // IClipboard.Stub.asInterface(ServiceManager.getService("clipboard"));
        ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clip != null) {
            // clip.setClipboardText(text);
            clip.setText(text);
        }
        // } catch (android.os.RemoteException e) {
        // Log.e(LOGTAG, "Copy failed", e);
        // }
    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setTitle(R.string.browser_history);

        final String whereClause = Browser.BookmarkColumns.VISITS + " > 0"
                // In AddBookmarkPage, where we save new bookmarks, we add
                // three visits to newly created bookmarks, so that
                // bookmarks that have not been visited will show up in the
                // most visited, and higher in the goto search box.
                // However, this puts the site in the history, unless we
                // ignore sites with a DATE of 0, which the next line does.
                + " AND " + Browser.BookmarkColumns.DATE + " > 0";
        final String orderBy = Browser.BookmarkColumns.DATE + " DESC";

        Cursor cursor = managedQuery(
                Browser.BOOKMARKS_URI,
                Browser.HISTORY_PROJECTION,
                whereClause, null, orderBy);

        mAdapter = new HistoryAdapter(this, cursor,
                Browser.HISTORY_PROJECTION_DATE_INDEX);
        setListAdapter(mAdapter);
        final ExpandableListView list = getExpandableListView();
        FrameLayout.LayoutParams params= new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, Gravity.CENTER_HORIZONTAL);
        params.setMargins(getResources().getDimensionPixelSize(R.dimen.autocompletetextview_compound_drawable_rightoffset), 0, getResources().getDimensionPixelSize(R.dimen.autocompletetextview_compound_drawable_rightoffset), 0);
        list.setLayoutParams(params);
        list.setVerticalScrollBarEnabled(false);
        list.setGroupIndicator(getResources().getDrawable(R.drawable.expander_group));
        list.setDivider(null);
        list.setChildDivider(null);
        list.setCacheColorHint(0);
        list.setSelector(R.drawable.list_selector_transparent);
        list.setOnCreateContextMenuListener(this);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.autocompletetextview_compound_drawable_rightoffset);
        Animation animation = null;
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.3f);
        list.setLayoutAnimation(controller);
        // list.setPadding(getResources().getDimensionPixelSize(R.dimen.reading_list_padding_hori),
        // 0,
        // getResources().getDimensionPixelSize(R.dimen.reading_list_padding_hori),
        // 0);
        View v = new ViewStub(this, R.layout.empty_history);
        addContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        list.setEmptyView(v);
        if (list.getExpandableListAdapter().getGroupCount() > 0) {
            list.expandGroup(0);
        }
        // Do not post the runnable if there is nothing in the list.
        // if (list.getExpandableListAdapter().getGroupCount() > 0) {
        // list.post(new Runnable() {
        // public void run() {
        // // In case the history gets cleared before this event
        // // happens.
        // if (list.getExpandableListAdapter().getGroupCount() > 0) {
        // list.expandGroup(0);
        // }
        // }
        // });
        // }
        mDisableNewWindow = getIntent().getBooleanExtra("disable_new_window",
                false);

        mHomeLinkPosition = getIntent().getStringExtra(MyHomeLinks.POSITION);
        // Register to receive icons in case they haven't all been loaded.
        MyCombinedBookmarkHistoryActivity.getIconListenerSet()
                .addListener(mIconReceiver);

        Activity parent = getParent();
        if (null == parent
                || !(parent instanceof MyCombinedBookmarkHistoryActivity)) {
            throw new AssertionError("history page can only be viewed as a tab"
                    + "in MyCombinedBookmarkHistoryActivity");
        }
        // initialize the result to canceled, so that if the user just presses
        // back then it will have the correct result
        setResultToParent(RESULT_CANCELED, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyCombinedBookmarkHistoryActivity.getIconListenerSet()
                .removeListener(mIconReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.clear_history_menu_id).setVisible(
                Browser.canClearHistory(this.getContentResolver()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_history_menu_id:
                Browser.clearHistory(getContentResolver());
                // MyBrowserHistoryPage is always a child of
                // MyCombinedBookmarkHistoryActivity
                ((MyCombinedBookmarkHistoryActivity) getParent())
                        .removeParentChildRelationShips();
                mAdapter.refreshData();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        if (mHomeLinkPosition != null) {
            return;
        }
        ExpandableListContextMenuInfo mMenuInfo =
                (ExpandableListContextMenuInfo) menuInfo;
        // Do not allow a context menu to come up from the group views.
        if (!(mMenuInfo.targetView instanceof MyHistoryItem)) {
            return;
        }

        historyItem = (MyHistoryItem) mMenuInfo.targetView;

        String[] meunus;
        meunus = getResources().getStringArray(R.array.historycontext);
        if (historyItem.isBookmark()) {
            meunus[2] = getString(R.string.remove_from_bookmarks);
        }
        final MyCustomDialog dialog = new MyCustomDialog(this);

        dialog.setItems(meunus, new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String url = historyItem.getUrl();
                String title = historyItem.getName();
                dialog.dismiss();
                switch (arg2) {
                    case 0:
                        loadUrl(url, false);
                        break;
                    case 1:
                        loadUrl(url, true);
                        break;
                    case 2:
                        if (historyItem.isBookmark()) {
                            MyBookmarks.removeFromBookmarks(MyBrowserHistoryPage.this,
                                    getContentResolver(),
                                    url, title);
                        } else {
                            Browser.saveBookmark(MyBrowserHistoryPage.this, title, url);
                        }
                        break;
                    case 3:
                        Browser.sendString(MyBrowserHistoryPage.this, url,
                                getText(R.string.choosertitle_sharevia).toString());
                        break;
                    case 4:
                        copy(url);
                        break;
                    case 5:
                        Browser.deleteFromHistory(getContentResolver(), url);
                        mAdapter.refreshData();
                        break;

                }
            }
        });
        dialog.show();
        // Inflate the menu
        // MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.historycontext, menu);
        // // Setup the header
        // if (mContextHeader == null) {
        // mContextHeader = new MyHistoryItem(this);
        // } else if (mContextHeader.getParent() != null) {
        // ((ViewGroup) mContextHeader.getParent()).removeView(mContextHeader);
        // }
        // historyItem.copyTo(mContextHeader);
        // menu.setHeaderView(mContextHeader);
        //
        // // Only show open in new window if it was not explicitly disabled
        // if (mDisableNewWindow) {
        // menu.findItem(R.id.new_window_context_menu_id).setVisible(false);
        // }
        // // For a bookmark, provide the option to remove it from bookmarks
        // if (historyItem.isBookmark()) {
        // MenuItem item = menu.findItem(R.id.save_to_bookmarks_menu_id);
        // item.setTitle(R.string.remove_from_bookmarks);
        // }
        // // decide whether to show the share link option
        // PackageManager pm = getPackageManager();
        // Intent send = new Intent(Intent.ACTION_SEND);
        // send.setType("text/plain");
        // ResolveInfo ri = pm.resolveActivity(send,
        // PackageManager.MATCH_DEFAULT_ONLY);
        // menu.findItem(R.id.share_link_context_menu_id).setVisible(ri !=
        // null);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo i =
                (ExpandableListContextMenuInfo) item.getMenuInfo();
        MyHistoryItem historyItem = (MyHistoryItem) i.targetView;
        String url = historyItem.getUrl();
        String title = historyItem.getName();
        switch (item.getItemId()) {
//            case R.id.open_context_menu_id:
//                loadUrl(url, false);
//                return true;
//            case R.id.new_window_context_menu_id:
//                loadUrl(url, true);
//                return true;
//            case R.id.save_to_bookmarks_menu_id:
//                if (historyItem.isBookmark()) {
//                    MyBookmarks.removeFromBookmarks(this, getContentResolver(),
//                            url, title);
//                } else {
//                    Browser.saveBookmark(this, title, url);
//                }
//                return true;
//            case R.id.share_link_context_menu_id:
//                Browser.sendString(this, url,
//                        getText(R.string.choosertitle_sharevia).toString());
//                return true;
//            case R.id.copy_url_context_menu_id:
//                copy(url);
//                return true;
//            case R.id.delete_context_menu_id:
//                Browser.deleteFromHistory(getContentResolver(), url);
//                mAdapter.refreshData();
//                return true;
                // case R.id.homepage_context_menu_id:
                // BrowserSettings.getInstance().setHomePage(url);
                // Toast.makeText(this, R.string.homepage_set,
                // Toast.LENGTH_LONG).show();
                // return true;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
            int groupPosition, int childPosition, long id) {
        if (mHomeLinkPosition != null) {
            Context context = MyBrowserHistoryPage.this;
            String url = ((MyHistoryItem) v).getUrl();
            String title = ((MyHistoryItem) v).getName();
            if (MyHomeLinks.saveHomeLink(context, url, title)) {
                JSONObject json = new JSONObject();
                try {
                    json.put("position", mHomeLinkPosition);
                    json.put("url", url);
                    json.put("title", title);
                    /*
                     * byte[] touchIcon = getByteArrayOfTouchIcon(position);
                     * byte[] screenShot = getByteArrayOfScreenshot(position);
                     * String icon = null; if (touchIcon != null ){ icon =
                     * Base64.encodeToString(touchIcon, Base64.DEFAULT); } else
                     * if (screenShot != null) { icon =
                     * Base64.encodeToString(screenShot, Base64.DEFAULT); } else
                     * { Drawable draw = context.getResources().getDrawable(
                     * R.drawable.browser_thumbnail); ByteArrayOutputStream os =
                     * new ByteArrayOutputStream(); Bitmap bmp =
                     * MyHomeLinks.createIconBitmap(draw, context);
                     * bmp.compress(Bitmap.CompressFormat.PNG, 90, os); icon =
                     * Base64.encodeToString(os.toByteArray(), Base64.DEFAULT);
                     * } json.put("img", icon);
                     */
                    MyController.manageHomeLink(json, HomeLinkActions.Add);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            finish();
            return true;
        }
        if (v instanceof MyHistoryItem) {
            loadUrl(((MyHistoryItem) v).getUrl(), false);
            return true;
        }
        return false;
    }

    // This Activity is always a sub-Activity of
    // MyCombinedBookmarkHistoryActivity. Therefore, we need to pass our
    // result code up to our parent.
    private void setResultToParent(int resultCode, Intent data) {
        ((MyCombinedBookmarkHistoryActivity) getParent()).setResultFromChild(
                resultCode, data);
    }

    private class HistoryAdapter extends MyDateSortedExpandableListAdapter {
        HistoryAdapter(Context context, Cursor cursor, int index) {
            super(context, cursor, index);

        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            MyHistoryItem item;
            if (null == convertView || !(convertView instanceof MyHistoryItem)) {
                item = new MyHistoryItem(MyBrowserHistoryPage.this);
                // Add padding on the left so it will be indented from the
                // arrows on the group views.
                item.setPadding(item.getPaddingLeft() + 10,
                        item.getPaddingTop(),
                        item.getPaddingRight(),
                        item.getPaddingBottom());
            } else {
                item = (MyHistoryItem) convertView;
            }
            // Bail early if the Cursor is closed.
            if (!moveCursorToChildPosition(groupPosition, childPosition)) {
                return item;
            }
            item.setName(getString(Browser.HISTORY_PROJECTION_TITLE_INDEX));
            String url = getString(Browser.HISTORY_PROJECTION_URL_INDEX);
            item.setUrl(url);
            byte[] data = getBlob(Browser.HISTORY_PROJECTION_FAVICON_INDEX);
            if (data != null) {
                item.setFavicon(BitmapFactory.decodeByteArray(data, 0,
                        data.length));
            } else {
                item.setFavicon(MyCombinedBookmarkHistoryActivity
                        .getIconListenerSet().getFavicon(url));
            }
            item.setIsBookmark(1 ==
                    getInt(Browser.HISTORY_PROJECTION_BOOKMARK_INDEX));
            return item;
        }
    }
}
