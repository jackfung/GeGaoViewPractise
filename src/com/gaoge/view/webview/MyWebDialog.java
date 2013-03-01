package com.gaoge.view.webview;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.orange.test.textImage.R;




public class MyWebDialog extends LinearLayout {

    protected MyController mController;
    protected WebView         mWebView;
    private boolean           mIsVisible;
    
    public MyWebDialog(MyController mController) {
        super(mController.getContext());
        this.mController = mController;
    }
    
    
    protected void registCancelEvent() {
        View button = findViewById(R.id.done);
        if (button != null) button.setOnClickListener(mCancelListener);
    }
    
    private View.OnClickListener mCancelListener = new View.OnClickListener() {
        public void onClick(View v) {
            mController.closeDialogs();
        }
    };
    
    protected boolean isVisible() {
        return mIsVisible;
    }

    /* package */ void setWebView(WebView webview) {
        mWebView = webview;
    }

    protected void show() {
        startAnimation(AnimationUtils.loadAnimation(mController.getContext(),
            R.anim.dialog_enter));
        mIsVisible = true;
    }
    
    protected void dismiss() {
        startAnimation(AnimationUtils.loadAnimation(mController.getContext(),
                R.anim.dialog_exit));
        mIsVisible = false;
    }

}
