package com.gaoge.view.practise.resource;

import android.app.Activity;
import android.os.Bundle;

import com.gaoge.view.practise.utils.LogHelper;
import com.gaoge.view.practise.utils.Utils;
import com.orange.test.textImage.R;

import java.util.Locale;

public class TestStringCDATA extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String homepage_base = this.getString(R.string.homepage_base);
        String homepage_url = Utils.parseHomepage(this);
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
//        LogHelper.d("strings", "homepage_base: " + homepage_base);
        LogHelper.d("language", "language: " + language + ",country: " + country);
    }

}
