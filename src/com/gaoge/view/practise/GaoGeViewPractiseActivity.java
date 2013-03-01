package com.gaoge.view.practise;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.Window;

import com.orange.test.textImage.R;


public class GaoGeViewPractiseActivity extends Activity {
    String TAG = "GaoGeViewPractiseActivity";
    /** Called when the activity is first created. */
    MyViewGroup mMyViewGroup;
    HelloView mHelloView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        mHelloView = new HelloView(this);
//        setContentView(mHelloView);
        
        
        
//        mMyViewGroup = new MyViewGroup(this);
        setContentView(R.layout.main);
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.settings_titlebar);
//        setContentView(mMyViewGroup);
        registerForContextMenu(mHelloView);
        printPhoneMetrics();
          
    }
    void printPhoneMetrics(){
        DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
          
        
        Log.d("metric", "widthPixels: " + dm.widthPixels + ",heightPixels: " + dm.heightPixels
                + ",dm.density: " + dm.density );
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(TAG, "############## GaoGeViewPractiseActivity onCreateContextMenu ");
    }
    
    
}