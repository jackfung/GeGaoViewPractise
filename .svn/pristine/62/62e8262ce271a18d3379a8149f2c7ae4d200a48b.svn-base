package com.gaoge.view.practise.bitmap.fun.test.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MyImageGridActivity extends FragmentActivity {
    private static final String TAG = "ImageGridFragment";
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        if(null  == getSupportFragmentManager().findFragmentByTag(TAG)){
            final FragmentTransaction ft = (FragmentTransaction)getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new MyImageGridFragment(),TAG);
            ft.commit();
        }
    }

}
