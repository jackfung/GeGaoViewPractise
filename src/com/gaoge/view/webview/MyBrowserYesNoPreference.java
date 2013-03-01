package com.gaoge.view.webview;

import android.content.Context;
import android.util.AttributeSet;

public class MyBrowserYesNoPreference extends MyYesNoPreference {

    public MyBrowserYesNoPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
    }

}
