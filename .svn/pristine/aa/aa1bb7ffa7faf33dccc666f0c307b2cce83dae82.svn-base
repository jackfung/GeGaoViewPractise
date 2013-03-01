
package com.gaoge.view.webview;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.gaoge.view.practise.utils.LogHelper;
import com.orange.test.textImage.R;

public class MyBrowserPreferencesPage extends PreferenceActivity implements
        Preference.OnPreferenceChangeListener {
    public static String TAG = "MyBrowserPreferencesPage"; 
    
    static final String CURRENT_PAGE = "currentPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.my_browser_preferences);
        initAndRegistPreference();

    }

    private void initAndRegistPreference() {
        Preference e = findPreference(MyBrowserSettings.PREF_TEXT_SIZE);
        e.setOnPreferenceChangeListener(this);
        e.setSummary(getVisualTextSizeName(
                getPreferenceScreen().getSharedPreferences()
                .getString(MyBrowserSettings.PREF_TEXT_SIZE, null)) );
        
        e = findPreference(MyBrowserSettings.PREF_DEFAULT_ZOOM);
        e.setOnPreferenceChangeListener(this);
        e.setSummary(getVisualDefaultZoomName(
                getPreferenceScreen().getSharedPreferences()
                .getString(MyBrowserSettings.PREF_DEFAULT_ZOOM, null)) );
    }

    @Override
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        LogHelper.d(TAG,"######### onPreferenceChange,pref.getKey(): " + pref.getKey()
                + ",newValue: " + newValue);
        if (pref.getKey().equals(MyBrowserSettings.PREF_TEXT_SIZE)) {
            pref.setSummary(getVisualTextSizeName((String) newValue));
            return true;
        } else if (pref.getKey().equals(MyBrowserSettings.PREF_DEFAULT_ZOOM)) {
            pref.setSummary(getVisualDefaultZoomName((String) newValue));
            return true;
        }
        return false;
    }
    
    private CharSequence getVisualTextSizeName(String enumName) {
        CharSequence[] visualNames = getResources().getTextArray(
                R.array.pref_text_size_choices);
        CharSequence[] enumNames = getResources().getTextArray(
                R.array.pref_text_size_values);

        // Sanity check
        if (visualNames.length != enumNames.length) {
            return "";
        }

        for (int i = 0; i < enumNames.length; i++) {
            if (enumNames[i].equals(enumName)) {
                return visualNames[i];
            }
        }

        return "";
    }

    private CharSequence getVisualDefaultZoomName(String enumName) {
        CharSequence[] visualNames = getResources().getTextArray(
                R.array.pref_default_zoom_choices);
        CharSequence[] enumNames = getResources().getTextArray(
                R.array.pref_default_zoom_values);

        // Sanity check
        if (visualNames.length != enumNames.length) {
            return "";
        }

        for (int i = 0; i < enumNames.length; i++) {
            if (enumNames[i].equals(enumName)) {
                return visualNames[i];
            }
        }
        return "";
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        MyBrowserSettings.getInstance(this).syncSharedPreferences(
                getApplicationContext(),
                getPreferenceScreen().getSharedPreferences());
    }

}
