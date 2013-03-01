package com.gaoge.view.webview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.gaoge.view.webview.interfaces.MyUiController;
import com.gaoge.view.webview.MyPageProgressView;
import com.orange.test.textImage.R;

public class MyTitleBar extends RelativeLayout implements OnClickListener {

    private MyPageProgressView mProgress;
    
    private MyUiController mUiController;
    private MyBaseUi mBaseUi;
    private FrameLayout mParent;
    private MyNavigationBarBase mNavBar;
    private boolean mShowing;
    private boolean mInLoad;
    
    public MyTitleBar(Context context) {
        super(context);
    }
    
    public MyTitleBar(Context context, MyUiController controller, MyBaseUi ui,
            FrameLayout parent) {
        super(context, null);
        mUiController = controller;
        mBaseUi = ui;
        mParent = parent;
        initLayout(context);
    }
    
    private void initLayout(Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        factory.inflate(R.layout.my_title_bar, this);
        mProgress = (MyPageProgressView) findViewById(R.id.progress);
        mNavBar = (MyNavigationBarBase) findViewById(R.id.taburlbar);
        mNavBar.setTitleBar(this);
        mNavBar.updateBookmarkIcons();
//        mBatchModeTitleBar = findViewById(R.id.batchmode_title_bar);
//        mSelectedTextView = (TextView) this.findViewById(R.id.already_selected);
//        mConfirmLayout = (LinearLayout) this.findViewById(R.id.confirm_layout);
//        mConfirmTextView = (TextView) this.findViewById(R.id.confirm_read);
//        mConfirmBtn = (ImageView) this.findViewById(R.id.confirm_btn);
//        mConfirmLayout.setOnClickListener(this);
//        setConfirmText(false);
    }

    @Override
    public void onClick(View v) {

    }
    
    public MyBaseUi getUi() {
        return mBaseUi;
    }
    
    public MyUiController getUiController() { 
        return mUiController;
    }
    
    void show() {
        // TODO: add animation
        if (getParent() == null) {
            mParent.addView(this);
        }
        setVisibility(View.VISIBLE);
        mShowing = true;
    }

    void hide() {
        // TODO: add animation
        setVisibility(View.GONE);
        mShowing = false;
    }
    public boolean isInLoad() {
        return mInLoad;
    }
    
    public MyNavigationBarBase getNavigationBar() {
        return mNavBar;
    }
    
    boolean isShowing() {
        return mShowing;
    }
    
    private static final int PROGRESS_MAX = 100;
    
    public void setProgress(int newProgress) {
        if (newProgress >= PROGRESS_MAX) {
            mNavBar.onProgressStopped();
            mProgress.setProgress(MyPageProgressView.MAX_PROGRESS);
            mProgress.setVisibility(View.GONE);
            mInLoad = false;
        }else{
            if (!mInLoad) {
                mProgress.setVisibility(View.VISIBLE);
                mInLoad = true;
//                mNavBar.onProgressStarted();
            }
            mProgress.setProgress(newProgress * MyPageProgressView.MAX_PROGRESS
                    / PROGRESS_MAX);
        }
    }

}
