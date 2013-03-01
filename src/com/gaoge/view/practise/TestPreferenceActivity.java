package com.gaoge.view.practise;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;

import com.orange.test.textImage.R;


public class TestPreferenceActivity extends PreferenceActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        

        addPreferencesFromResource(R.xml.my_browser_preferences);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.settings_titlebar);
        
//        getListView().setBackgroundColor(R.color.settings_background);
        getListView().setCacheColorHint(Color.TRANSPARENT);
        getListView().setDivider(null);
    }

}
