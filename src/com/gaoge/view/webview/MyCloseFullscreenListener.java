package com.gaoge.view.webview;



import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.orange.test.textImage.R;

public class MyCloseFullscreenListener implements View.OnTouchListener {
    private static final String TAG = "MyCloseFullscreenListener";
    private MyBaseUi mBaseUi;
    private float mSlop;
    private float mTouchStartY;
    private boolean mOpen;

    public MyCloseFullscreenListener(Context context, MyBaseUi MyBaseUi) {
        mBaseUi = MyBaseUi;
        mSlop = context.getResources().getDimension(R.dimen.qc_slop);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float y = event.getY();
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if ((y < mSlop)) {
                    mTouchStartY = y;
                    mOpen = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mOpen) {
                    float dy = y - mTouchStartY;
                    if (dy > mSlop) {
                        mOpen = false;
                        onQuitFullscreen();
                        /**
                         * this can't  be executed in MyBaseUi's showTitleBar method,
                         * because every time load a url,the system should invoke 
                         * showTitleBar method,which will cause a bug on efficient read tip  
                         */
                        mBaseUi.showTitleBar();
                        mBaseUi.onQuitFullScreen();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mOpen) {
                    mOpen = false;
                    return true;
                }
        }
        return false;
    }

    protected void onQuitFullscreen() {
    }

}
