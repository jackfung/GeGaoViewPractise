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



import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orange.test.textImage.R;

/**
 *  Custom layout for an item representing a bookmark in the browser.
 */
class MyBookmarkItem extends LinearLayout {

    protected TextView    mTextView;
    protected TextView    mUrlText;
    protected ImageView   mImageView;
    protected String      mUrl;
    protected String      mTitle;

    /**
     *  Instantiate a bookmark item, including a default favicon.
     *
     *  @param context  The application context for the item.
     */
    MyBookmarkItem(Context context) {
        super(context);

        LayoutInflater factory = LayoutInflater.from(context);
        factory.inflate(R.layout.history_item, this);
        mTextView = (TextView) findViewById(R.id.title);
        mUrlText = (TextView) findViewById(R.id.url);
        mImageView = (ImageView) findViewById(R.id.favicon);
        View star = findViewById(R.id.star);
        star.setVisibility(View.GONE);
    }

    /**
     *  Copy this MyBookmarkItem to item.
     *  @param item MyBookmarkItem to receive the info from this MyBookmarkItem.
     */
    /* package */ void copyTo(MyBookmarkItem item) {
        item.mTextView.setText(mTextView.getText());
        item.mUrlText.setText(mUrlText.getText());
        item.mImageView.setImageDrawable(mImageView.getDrawable());
    }

    /**
     * Return the name assigned to this bookmark item.
     */
    /* package */ String getName() {
        return mTitle;
    }

    /**
     * Return the TextView which holds the name of this bookmark item.
     */
    /* package */ TextView getNameTextView() {
        return mTextView;
    }

    /* package */ String getUrl() {
        return mUrl;
    }

    /**
     *  Set the favicon for this item.
     *
     *  @param b    The new bitmap for this item.
     *              If it is null, will use the default.
     */
    /* package */ void setFavicon(Bitmap b) {
        if (b != null) {
            mImageView.setImageBitmap(b);
        } else {
            mImageView.setImageResource(R.drawable.bookmark_small);
        }
    }

    /**
     *  Set the new name for the bookmark item.
     *
     *  @param name The new name for the bookmark item.
     */
    /* package */ void setName(String name) {
        if (name == null) {
            return;
        }

        mTitle = name;

        if (name.length() > MyBrowserSettings.MAX_TEXTVIEW_LEN) {
            name = name.substring(0, MyBrowserSettings.MAX_TEXTVIEW_LEN);
        }

        mTextView.setText(name);
    }
    
    /**
     *  Set the new url for the bookmark item.
     *  @param url  The new url for the bookmark item.
     */
    /* package */ void setUrl(String url) {
        if (url == null) {
            return;
        }

        mUrl = url;

        if (url.length() > MyBrowserSettings.MAX_TEXTVIEW_LEN) {
            url = url.substring(0, MyBrowserSettings.MAX_TEXTVIEW_LEN);
        }

        mUrlText.setText(url);
    }
}
