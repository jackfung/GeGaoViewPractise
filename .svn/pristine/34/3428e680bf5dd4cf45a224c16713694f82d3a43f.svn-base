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

 
package com.gaoge.view.webview;


import dep.android.provider.Browser;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.orange.test.textImage.R;

/**
 *  Layout representing a history item in the classic history viewer.
 */
/* package */ class MyHistoryItem extends MyBookmarkItem {

    private CompoundButton  mStar;      // Star for bookmarking
    private CompoundButton.OnCheckedChangeListener  mListener;
    private Context mContext;
    /**
     *  Create a new HistoryItem.
     *  @param context  Context for this HistoryItem.
     */
    MyHistoryItem(Context context) {
        super(context);
        mContext = context;

        mStar = (CompoundButton) findViewById(R.id.star);
        mStar.setVisibility(View.VISIBLE);
        mListener = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                if (isChecked) {
                    MyBookmarks.addBookmark(mContext,
                            mContext.getContentResolver(), mUrl, getName(), null, true);
                } else {
                    MyBookmarks.removeFromBookmarks(mContext,
                            mContext.getContentResolver(), mUrl, getName());
                }
            }
        };
    }
    
    /* package */ void copyTo(MyHistoryItem item) {
        item.mTextView.setText(mTextView.getText());
        item.mUrlText.setText(mUrlText.getText());
        // No star for UI to coincide with other context UI like most visited.
        // Or else, we should do more to avoid crash.
        //item.setIsBookmark(mStar.isChecked());
        item.mStar.setVisibility(View.GONE);
        item.mImageView.setImageDrawable(mImageView.getDrawable());
    }

    /**
     * Whether or not this item represents a bookmarked site
     */
    /* package */ boolean isBookmark() {
        return mStar.isChecked();
    }

    /**
     *  Set whether or not this represents a bookmark, and make sure the star
     *  behaves appropriately.
     */
    /* package */ void setIsBookmark(boolean isBookmark) {
        mStar.setOnCheckedChangeListener(null);
        mStar.setChecked(isBookmark);
        mStar.setOnCheckedChangeListener(mListener);
    }
}
