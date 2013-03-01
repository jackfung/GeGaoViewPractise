package com.gaoge.view.webview;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orange.test.textImage.R;


public class MyOWindow extends LinearLayout {
    private ImageView mThumbnail;
    private ImageView mClose;
    private String mTitle;
    private String mUrl;
    private int mIndex;
    
    

    public MyOWindow(Context context) {
        super(context);
        LayoutInflater flater = LayoutInflater.from(context);
        flater.inflate(R.layout.my_window_layout, this);
        mClose = (ImageView) findViewById(R.id.window_close);
        mThumbnail = (ImageView) findViewById(R.id.window_thumbnail);
        mClose.setVisibility(View.INVISIBLE);
        
        
    }
    
    public void setTab(MyTab tab) {
        mTitle = tab.getTitle();
        mUrl = tab.getUrl();
//        if (BrowserSettings.getInstance().getHomePage().equals(tab.getUrl()))
//            mUrl = getResources().getString(R.string.search_hint);
    }
    public void setBitmap(Bitmap bitmap){
            mThumbnail.setImageBitmap(bitmap);
    }
    private boolean isLandscape() {
        return getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT;
    }

    public ImageView getCloseBt() {
        return mClose;
    }

    public void setCloseBtVisibility(int visibility) {
        mClose.setVisibility(visibility);
    }

    public String getTitle() {
        if (mTitle != null && mTitle.length() > 0)
            return mTitle;
        return "No Title";
    }

    public String getUrl() {
        if (mUrl != null)
            return mUrl;
        return null;
    }

    public void setAlpha(int value) {
        mClose.setAlpha(value);
    }


    public void setIndex(int index) {
        mIndex = index;
    }
    public int getIndex() {
        return mIndex ;
    }
    public ImageView getThumnail(){
        return mThumbnail;
    }

}
