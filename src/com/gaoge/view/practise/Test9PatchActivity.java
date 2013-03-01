package com.gaoge.view.practise;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.orange.test.textImage.R;

public class Test9PatchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.nine_patch);
        
    }

}
