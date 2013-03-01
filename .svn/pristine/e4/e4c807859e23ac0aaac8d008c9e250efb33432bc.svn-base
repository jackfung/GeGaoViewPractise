package com.gaoge.view.practise.activitytest.loaders;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class TestContactLoaderAct extends FragmentActivity {
    String TAG = "TestContactLoaderFragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if(null == getSupportFragmentManager().findFragmentByTag(TAG)){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(new TestContactLoaderFragment(), TAG);
            ft.commit();
        }
        
    
    }

}
