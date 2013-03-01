package com.gaoge.view.practise.provider;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;

import com.gaoge.view.practise.utils.LogHelper;

public class MyProviderContentObserver extends ContentObserver {

    public MyProviderContentObserver() {
        super(new Handler(Looper.getMainLooper()));
    }
    
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
//        LogHelper.d(LogHelper.TAG_PROVIDER, "MyProviderContentObserver");
    }

}
